import java.util.ArrayList;

public class Licitatie {
    private int id;
    private int nrParticipanti;
    private int idProdus;
    private int nrPasiMaxim;

    // retine oferta castigatoare la fiecare pas al licitatiei
    private Oferta ofertaCastigatoare;

    /*
        Design Pattern-ul BUILDER este folosit pentru a permite generarea usoara a
        obiectelor de tip Licitatie, cu caracteristicile dorite.
     */
    public static class Builder {
        private Licitatie licitatie;

        public Builder() {
            this.licitatie = new Licitatie();
            this.licitatie.ofertaCastigatoare = new Oferta(this.licitatie.idProdus, -1);
            this.licitatie.getOfertaCastigatoare().setOfertaCurenta(0.1);
        }

        public Builder(Licitatie licitatie) {
            this.licitatie = licitatie;
        }

        public Builder withId(int id) {
            licitatie.setId(id);
            return this;
        }

        public Builder withNrParticipanti(int NrParticipanti) {
            licitatie.setNrParticipanti(NrParticipanti);
            return this;
        }

        public Builder withIdProdus(int id) {
            licitatie.setIdProdus(id);
            return this;
        }

        public Builder withNrPasiMaxim(int nrPasiMaxim) {
            licitatie.setNrPasiMaxim(nrPasiMaxim);
            return this;
        }

        public Licitatie build() {
            return licitatie;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrParticipanti() {
        return nrParticipanti;
    }

    public void setNrParticipanti(int nrParticipanti) {
        this.nrParticipanti = nrParticipanti;
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getNrPasiMaxim() {
        return nrPasiMaxim;
    }

    public void setNrPasiMaxim(int nrPasiMaxim) {
        this.nrPasiMaxim = nrPasiMaxim;
    }

    public Oferta getOfertaCastigatoare() {
        return ofertaCastigatoare;
    }

    // se apeleaza la fiecare pas al licitatiei pentru a actualiza oferta castigatoare
    public void actualizeazaOfertaCastigatoare(Oferta oferta) {
        if (oferta.compareTo(getOfertaCastigatoare()) > 0) ofertaCastigatoare = oferta;
    }

    /*
        La fiecare pas al licitatiei se deschide cate un thread pentru fiecare broker.
        Brokerul primeste oferta castigatoare curenta si isi notifica toti clientii care
        participa la licitatie, iar clientii isi actualizeaza oferta proprie. Toate ofertele
        noi sunt comunicate inapoi casei prin intermediul parametrului "listaContraOferte".
     */
    public void ruleazaLicitatie() throws InterruptedException {
        for (int p = 0; p < getNrPasiMaxim(); p++) {
            double ofertaCurenta = getOfertaCastigatoare().getOfertaCurenta();
            ArrayList<Oferta> listaContraOferte = new ArrayList<>();
            ArrayList<Thread> listaThreaduri = new ArrayList<>();
            for (Broker broker : CasaDeLicitatii.getInstance().getBrokeri()) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        broker.actualizeazaClienti(getId(), ofertaCurenta, listaContraOferte);
                    }
                });
                t.start();
                listaThreaduri.add(t);
            }
            for (Thread t : listaThreaduri) t.join();
            for (Oferta o : listaContraOferte) actualizeazaOfertaCastigatoare(o);
        }
        System.out.println(ofertaCastigatoare);
        vindeProdus();
        for (Broker broker : CasaDeLicitatii.getInstance().getBrokeri()) {
            broker.removeAll(id);
        }
    }

    // Vinde produsul clientului castigator, prin intermediul brokerului
    public synchronized void vindeProdus() {
        Broker brokerCastigator = null;
        Client clientCastigator = null;
        if (CasaDeLicitatii.getInstance().getBrokeri() != null) {
            for (Broker broker : CasaDeLicitatii.getInstance().getBrokeri()) {
                if (broker.getClienti() != null && broker.getClienti().get(id) != null) {
                    for (Client client : broker.getClienti().get(id)) {
                        if (client.getOferta() == getOfertaCastigatoare()) {
                            brokerCastigator = broker;
                            clientCastigator = client;
                        }
                    }
                }
            }
        }
        double pret = ofertaCastigatoare.getOfertaCurenta();
        Produs produs = CasaDeLicitatii.getInstance().getProduse().get(idProdus);
        double pretMinim = produs.getPretMinim();
        if (pret >= pretMinim) {
            if (brokerCastigator != null && clientCastigator != null) {
                brokerCastigator.vindeProdus(clientCastigator, produs, pret);
                brokerCastigator.stergeProdus(produs);
            }
        } else System.out.println("Pretul minim pentru produsul cu id " + idProdus + " nu a fost atins!");
    }

    @Override
    public String toString() {
        return "Licitatie{" +
                "id=" + id +
                ", nrParticipanti=" + nrParticipanti +
                ", idProdus=" + idProdus +
                ", nrPasiMaxim=" + nrPasiMaxim +
                '}';
    }
}

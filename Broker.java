import java.util.ArrayList;
import java.util.HashMap;

public class Broker extends Angajat {
    // clientii brokerului sunt organizati dupa id-ul licitatiei la care participa
    private HashMap<Integer, ArrayList<Client>> clienti;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Angajat si metodele specifice, fara
        a scrie cod redundant in subclase.

        In cazul clasei Angajat si subclaselor, Design Pattern-ul BUILDER a fost ales pentru
        a pastra uniformitatea in raport cu celelalte clase importante (Produs, Client) si in
        ideea dezvoltarii ulterioare.
     */
    public static class Builder extends Angajat.Builder<Broker, Builder> {
        public Builder() {
            super(new Broker());
        }

        public Builder withClienti(HashMap<Integer, ArrayList<Client>> clienti) {
            angajat.setClienti(clienti);
            return this;
        }
    }

    // adauga un client, pe baza licitatiei la care participa
    public synchronized void addClient(int idLicitatie, Client client) {
        if (clienti == null) clienti = new HashMap<>();
        if (!clienti.containsKey(idLicitatie)) clienti.put(idLicitatie, new ArrayList<>());
        clienti.get(idLicitatie).add(client);
    }

    // sterge un client, pe baza licitatiei la care participa
    public synchronized void removeClient(int idLicitatie, Client client) {
        if (clienti != null && clienti.containsKey(idLicitatie) && clienti.get(idLicitatie).contains(client))
            clienti.get(idLicitatie).remove(client);
    }

    // adauga toti clientii, pe baza licitatiei la care participa
    public synchronized void addAll(int idLicitatie, ArrayList<Client> licitatori) {
        if (clienti == null) clienti = new HashMap<>();
        if (!clienti.containsKey(idLicitatie)) clienti.put(idLicitatie, licitatori);
        clienti.get(idLicitatie).addAll(licitatori);
    }

    // sterge toti clientii, pe baza licitatiei la care participa
    public synchronized void removeAll(int idLicitatie) {
        if (clienti != null && clienti.containsKey(idLicitatie)) clienti.remove(idLicitatie);
    }

    public HashMap<Integer, ArrayList<Client>> getClienti() {
        return clienti;
    }

    public void setClienti(HashMap<Integer, ArrayList<Client>> clienti) {
        this.clienti = clienti;
    }

    /*
        Design Pattern-ul MEDIATOR este folosit, casa de licitatii fiind un mediator
        care permite brokerului sa stearga un produs din lista de produse a casei
     */
    public void stergeProdus(Produs produs) {
        CasaDeLicitatii.getInstance().stergeProdus(produs);
    }

    /*
        Design Pattern-ul MEDIATOR este folosit, casa de licitatii fiind un mediator
        care permite brokerului sa stearga un produs din lista de produse a casei
     */
    public void stergeProdus(int id) {
        CasaDeLicitatii.getInstance().stergeProdus(id);
    }

    /*
        Design Pattern-ul OBSERVER este folosit, subiectul fiind brokerul, iar
        observatorii fiind clientii, care isi actualizeaza ofertele

        Noile oferte sunt trimise inapoi casei de licitatii, prin parametrul "oferte".

        Cuvantul cheie "synchronized" este folosit pentru a asigura un rezultat corect
        atunci cand operatiile se desfasoara pe thread-uri diferite
     */
    public synchronized void actualizeazaClienti(int idLicitatie, double ofertaAltora, ArrayList<Oferta> oferte) {
        if (getClienti() != null && getClienti().containsKey(idLicitatie) && !getClienti().get(idLicitatie).isEmpty()) {
            for (Client client : getClienti().get(idLicitatie)) {
                client.actualizeazaOferta(ofertaAltora);
                synchronized (oferte) {
                    oferte.add(client.getOferta());
                }
            }
        }
    }

    // Brokerul vinde produsul unui client, aplicand un comision
    public void vindeProdus(Client client, Produs produs, double pretOriginal) {
        double pretComisionat = pretOriginal;
        if (client instanceof PersoanaFizica) {
            if (client.getNrParticipari() < 5) pretComisionat *= 1.20;
            else pretComisionat *= 1.15;
        } else if (client instanceof PersoanaJuridica) {
            if (client.getNrParticipari() < 25) pretComisionat *= 1.25;
            else pretComisionat *= 1.10;
        }
        client.plateste(produs, pretComisionat);
    }
}

public abstract class Client {
    protected int id;
    protected String nume;
    protected String adresa;
    protected int nrParticipari;
    protected int nrLicitatiiCastigate;

    // retine ultima oferta facuta de client
    protected Oferta oferta;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Client si metodele specifice, fara
        a scrie cod redundant in subclase.
     */
    @SuppressWarnings("unchecked")
    protected static abstract class Builder<C extends Client, B extends Builder> {
        protected C client;

        protected Builder(C client) {
            this.client = client;
        }

        protected B withId(int id) {
            client.setId(id);
            return (B) this;
        }

        protected B withNume(String nume) {
            client.setNume(nume);
            return (B) this;
        }

        protected B withAdresa(String adresa) {
            client.setAdresa(adresa);
            return (B) this;
        }

        protected B withNrParticipari(int NrParticipari) {
            client.setNrParticipari(NrParticipari);
            return (B) this;
        }

        protected B withNrLicitatiiCastigate(int an) {
            client.setNrLicitatiiCastigate(an);
            return (B) this;
        }

        protected B withOferta(Oferta oferta) {
            client.setOferta(oferta);
            return (B) this;
        }

        protected C build() {
            return client;
        }
    }

    protected int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    protected String getNume() {
        return nume;
    }

    protected void setNume(String nume) {
        this.nume = nume;
    }

    protected String getAdresa() {
        return adresa;
    }

    protected void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    protected int getNrParticipari() {
        return nrParticipari;
    }

    protected void setNrParticipari(int nrParticipari) {
        this.nrParticipari = nrParticipari;
    }

    protected int getNrLicitatiiCastigate() {
        return nrLicitatiiCastigate;
    }

    protected void setNrLicitatiiCastigate(int nrLicitatiiCastigate) {
        this.nrLicitatiiCastigate = nrLicitatiiCastigate;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    /*
        Design Pattern-ul MEDIATOR este folosit, casa de licitatii fiind un mediator
        intre client si produsul pe care clientul doreste sa-l vada
     */
    public void veziProdus(int id) {
        CasaDeLicitatii.getInstance().veziProdus(this, id);
    }

    /*
        Design Pattern-ul MEDIATOR este folosit, casa de licitatii fiind un mediator
        care permite clientului sa inregistreze o cerere pentru un produs
     */
    public void inregistreazaCerere(int id) {
        CasaDeLicitatii.getInstance().adaugaCerere(this, id);
        setOferta(new Oferta(id, this.id));
    }

    /*
        Metoda realizeaza actualizarea ofertei pentru produsul dorit de client, pe baza
        ofertei curente si ofertei maxime stabilite de client la inregistrarea cererii.
     */
    public void actualizeazaOferta(double ofertaAltora) {
        getOferta().stabilesteOfertaCurenta(ofertaAltora);
    }

    /*
        Design Pattern-ul MEDIATOR este folosit, casa de licitatii fiind un mediator
        care permite clientului sa retraga o cerere pentru un produs
     */
    public void retrageCerere(int id) {
        CasaDeLicitatii.getInstance().stergeCerere(this, id);
        oferta = null;
    }

    // Clientul plateste pentru produsul castigat la licitatie
    public void plateste(Produs produs, double pret) {
        System.out.println("Clientul cu id " + id + " a platit " + pret + " pentru produsul cu id " + produs.getId());
    }
}

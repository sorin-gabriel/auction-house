public class Oferta implements Comparable<Oferta> {
    private int idProdus;
    private int idClient;
    private double ofertaCurenta;
    private double ofertaMaxima;

    public Oferta(int idProdus, int idClient) {
        this.idProdus = idProdus;
        this.idClient = idClient;
        this.ofertaCurenta = 0;
        this.stabilesteOfertaMaxima();
    }

    // actualizeaza oferta clientului, avand in vedere oferta castigatoare curenta
    public void stabilesteOfertaCurenta(double ofertaAltora) {
        if (ofertaCurenta < ofertaMaxima && ofertaCurenta < ofertaAltora) {
            ofertaCurenta = Math.round(ofertaAltora * (1 + Math.random()) + Math.random() * 1000);
            if (ofertaCurenta >= ofertaMaxima) ofertaCurenta = ofertaMaxima;
        }
    }

    // stabileste oferta maxima pe care o va face clientul pentru produsul ales
    public void stabilesteOfertaMaxima() {
        ofertaMaxima = Math.round(5000 + Math.random() * 95000);
    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public double getOfertaCurenta() {
        return ofertaCurenta;
    }

    public void setOfertaCurenta(double ofertaCurenta) {
        this.ofertaCurenta = ofertaCurenta;
    }

    public double getOfertaMaxima() {
        return ofertaMaxima;
    }

    public void setOfertaMaxima(double ofertaMaxima) {
        this.ofertaMaxima = ofertaMaxima;
    }

    @Override
    public int compareTo(Oferta o) {
        if (getOfertaCurenta() > o.getOfertaCurenta()) return 1;
        else if (getOfertaCurenta() < o.getOfertaCurenta()) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "idProdus=" + idProdus +
                ", idClient=" + idClient +
                ", ofertaCurenta=" + ofertaCurenta +
                ", ofertaMaxima=" + ofertaMaxima +
                '}';
    }
}

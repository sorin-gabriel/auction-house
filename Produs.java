public abstract class Produs {
    protected int id;
    protected String nume;
    protected double pretVanzare;
    protected double pretMinim;
    protected int an;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Produs si metodele specifice, fara
        a scrie cod redundant in subclase.
     */
    @SuppressWarnings("unchecked")
    protected static abstract class Builder<P extends Produs, B extends Builder> {
        protected P produs;

        protected Builder(P produs) {
            this.produs = produs;
        }

        protected B withId(int id) {
            produs.setId(id);
            return (B) this;
        }

        protected B withNume(String nume) {
            produs.setNume(nume);
            return (B) this;
        }

        protected B withPretVanzare(double pretVanzare) {
            produs.setPretVanzare(pretVanzare);
            return (B) this;
        }

        protected B withPretMinim(double pretMinim) {
            produs.setPretMinim(pretMinim);
            return (B) this;
        }

        protected B withAn(int an) {
            produs.setAn(an);
            return (B) this;
        }

        protected P build() {
            return produs;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPretVanzare() {
        return pretVanzare;
    }

    public void setPretVanzare(double pretVanzare) {
        this.pretVanzare = pretVanzare;
    }

    public double getPretMinim() {
        return pretMinim;
    }

    public void setPretMinim(double pretMinim) {
        this.pretMinim = pretMinim;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        this.an = an;
    }
}

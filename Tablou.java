public class Tablou extends Produs {
    enum Culori {
        ulei,
        tempera,
        acrilic
    }

    private String numePictor;
    private Culori culori;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Produs si metodele specifice, fara
        a scrie cod redundant in subclase.
     */
    public static class Builder extends Produs.Builder<Tablou, Builder> {
        public Builder() {
            super(new Tablou());
        }

        public Builder withNumePictor(String numePictor) {
            produs.setNumePictor(numePictor);
            return this;
        }

        public Builder withCulori(Culori culori) {
            produs.setCulori(culori);
            return this;
        }
    }

    public String getNumePictor() {
        return numePictor;
    }

    public void setNumePictor(String numePictor) {
        this.numePictor = numePictor;
    }

    public Culori getCulori() {
        return culori;
    }

    public void setCulori(Culori culori) {
        this.culori = culori;
    }

    @Override
    public String toString() {
        return "Tablou{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", pretVanzare=" + pretVanzare +
                ", pretMinim=" + pretMinim +
                ", an=" + an +
                ", numePictor='" + numePictor + '\'' +
                ", culori=" + culori +
                '}';
    }
}
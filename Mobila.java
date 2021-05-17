public class Mobila extends Produs {
    private String tip;
    private String material;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Produs si metodele specifice, fara
        a scrie cod redundant in subclase.
     */
    public static class Builder extends Produs.Builder<Mobila, Builder> {
        public Builder() {
            super(new Mobila());
        }

        public Builder withTip(String tip) {
            produs.setTip(tip);
            return this;
        }

        public Builder withMaterial(String material) {
            produs.setMaterial(material);
            return this;
        }
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Mobila{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", pretVanzare=" + pretVanzare +
                ", pretMinim=" + pretMinim +
                ", an=" + an +
                ", tip='" + tip + '\'' +
                ", material=" + material +
                '}';
    }
}

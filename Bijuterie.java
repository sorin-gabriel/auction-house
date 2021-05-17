public class Bijuterie extends Produs {
    private String material;
    private boolean piatraPretioasa;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Produs si metodele specifice, fara
        a scrie cod redundant in subclase.
     */
    public static class Builder extends Produs.Builder<Bijuterie, Builder> {
        public Builder() {
            super(new Bijuterie());
        }

        public Builder withMaterial(String material) {
            produs.setMaterial(material);
            return this;
        }

        public Builder isPiatraPretioasa(boolean piatraPretioasa) {
            produs.setPiatraPretioasa(piatraPretioasa);
            return this;
        }
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public boolean isPiatraPretioasa() {
        return piatraPretioasa;
    }

    public void setPiatraPretioasa(boolean piatraPretioasa) {
        this.piatraPretioasa = piatraPretioasa;
    }

    @Override
    public String toString() {
        return "Bijuterie{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", pretVanzare=" + pretVanzare +
                ", pretMinim=" + pretMinim +
                ", an=" + an +
                ", material='" + material + '\'' +
                ", piatraPretioasa=" + piatraPretioasa +
                '}';
    }
}

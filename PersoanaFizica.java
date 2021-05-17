public class PersoanaFizica extends Client {
    private String dataNastere;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Client si metodele specifice, fara
        a scrie cod redundant in subclase.
     */
    public static class Builder extends Client.Builder<PersoanaFizica, Builder> {
        public Builder() {
            super(new PersoanaFizica());
        }

        public Builder withDataNastere(String dataNastere) {
            client.setDataNastere(dataNastere);
            return this;
        }
    }

    public String getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(String dataNastere) {
        this.dataNastere = dataNastere;
    }

    @Override
    public String toString() {
        return "PersoanaFizica{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", nrParticipari=" + nrParticipari +
                ", nrLicitatiiCastigate=" + nrLicitatiiCastigate +
                ", dataNastere='" + dataNastere + '\'' +
                '}';
    }
}

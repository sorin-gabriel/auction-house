public class PersoanaJuridica extends Client {
    enum Companie {
        SRL,
        SA
    }

    private double capitalSocial;
    private Companie companie;

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Client si metodele specifice, fara
        a scrie cod redundant in subclase.
     */
    public static class Builder extends Client.Builder<PersoanaJuridica, Builder> {
        public Builder() {
            super(new PersoanaJuridica());
        }

        public Builder withCapitalSocial(double capitalSocial) {
            client.setCapitalSocial(capitalSocial);
            return this;
        }

        public Builder withCompanie(Companie companie) {
            client.setCompanie(companie);
            return this;
        }
    }

    public double getCapitalSocial() {
        return capitalSocial;
    }

    public void setCapitalSocial(double capitalSocial) {
        this.capitalSocial = capitalSocial;
    }

    public Companie getCompanie() {
        return companie;
    }

    public void setCompanie(Companie companie) {
        this.companie = companie;
    }

    @Override
    public String toString() {
        return "PersoanaJuridica{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", adresa='" + adresa + '\'' +
                ", nrParticipari=" + nrParticipari +
                ", nrLicitatiiCastigate=" + nrLicitatiiCastigate +
                ", capitalSocial=" + capitalSocial +
                ", companie=" + companie +
                '}';
    }
}

public class Administrator extends Angajat {
    /*
        Design Pattern-ul SINGLETON a fost ales pentru clasa Administrator deoarece se
        doreste instantierea unui singur administrator per test.
     */
    private static final Administrator instanta = new Administrator.Builder().build();

    public static Administrator getInstance() {
        return instanta;
    }

    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Angajat si metodele specifice, fara
        a scrie cod redundant in subclase.

        In cazul clasei Angajat si subclaselor, Design Pattern-ul BUILDER a fost ales pentru
        a pastra uniformitatea in raport cu celelalte clase importante (Produs, Client) si in
        ideea dezvoltarii ulterioare.
     */
    private static class Builder extends Angajat.Builder<Administrator, Builder> {
        public Builder() {
            super(new Administrator());
        }
    }

    /*
        Design Pattern-ul MEDIATOR este folosit, casa de licitatii fiind un mediator
        care permite administratorului sa adauge un produs in lista de produse a casei
     */
    public void adaugaProdus(Produs produs) {
        CasaDeLicitatii.getInstance().adaugaProdus(produs);
    }
}

public abstract class Angajat {
    /*
        Design Pattern-ul BUILDER este folosit impreuna cu Genericitatea pentru a permite
        subclaselor sa foloseasca builder-ul superclasei Angajat si metodele specifice, fara
        a scrie cod redundant in subclase.

        In cazul clasei Angajat si subclaselor, Design Pattern-ul BUILDER a fost ales pentru
        a pastra uniformitatea in raport cu celelalte clase importante (Produs, Client) si in
        ideea dezvoltarii ulterioare.
     */
    protected static abstract class Builder<A extends Angajat, B extends Builder> {
        protected A angajat;

        protected Builder(A angajat) {
            this.angajat = angajat;
        }

        protected A build() {
            return angajat;
        }
    }
}

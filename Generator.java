import java.util.Random;

/*
    Clasa Generator extinde clasa Random si permite generarea unor obiecte de tip:
    Produs (cu toate subclasele), Client (cu toate subclasele), Licitatie; toate
    obiectele generate au caracteristici random (cu anumite limite - specifice implementarii).
    Se foloseste Design Pattern-ul BUILDER pentru a facilita generarea obiectelor.
 */
public class Generator extends Random {
    public Generator() {
    }

    public Generator(long seed) {
        super(seed);
    }

    // folosit pentru a genera obiecte cu id unic, indiferent de tipul obiectului generat
    private int idCurent;

    // returneaza o instanta a unei subclase de Produs, cu id-ul specificat
    Produs nextProdus(int id) {
        if (this.nextBoolean() && this.nextBoolean()) return nextBijuterie(id);
        else if (this.nextBoolean()) return nextMobila(id);
        else return nextTablou(id);
    }

    // returneaza o instanta a unei subclase de Produs, cu id unic
    synchronized Produs nextProdus() {
        if (this.nextBoolean() && this.nextBoolean()) return nextBijuterie();
        else if (this.nextBoolean()) return nextMobila();
        else return nextTablou();
    }

    // returneaza o instanta a unui Tablou, cu id-ul specificat
    Tablou nextTablou(int id) {
        return new Tablou.Builder()
                .withId(id)
                .withNume("Tablou_" + id)
                .withPretVanzare(-1)
                .withPretMinim(1000 + Math.round(this.nextDouble() * 9000))
                .withAn(1200 + this.nextInt(2020 - 1200))
                .withNumePictor("Pictor_" + id)
                .withCulori(this.nextCuloare())
                .build();
    }

    // returneaza o instanta a unui Tablou, cu id unic
    synchronized Tablou nextTablou() {
        idCurent++;
        return new Tablou.Builder()
                .withId(idCurent)
                .withNume("Tablou_" + idCurent)
                .withPretVanzare(-1)
                .withPretMinim(1000 + Math.round(this.nextDouble() * 9000))
                .withAn(1200 + this.nextInt(2020 - 1200))
                .withNumePictor("Pictor_" + idCurent)
                .withCulori(this.nextCuloare())
                .build();
    }

    // returneaza o instanta a unei Culori
    Tablou.Culori nextCuloare() {
        if (this.nextBoolean() && this.nextBoolean()) return Tablou.Culori.ulei;
        else if (this.nextBoolean()) return Tablou.Culori.tempera;
        else return Tablou.Culori.acrilic;
    }

    // returneaza o instanta a unei Bijuterii, cu id-ul specificat
    Bijuterie nextBijuterie(int id) {
        return new Bijuterie.Builder()
                .withId(id)
                .withNume("Bijuterie_" + id)
                .withPretVanzare(-1)
                .withPretMinim(1000 + Math.round(this.nextDouble() * 9000))
                .withAn(1500 + this.nextInt(2020 - 1500))
                .withMaterial("Material_" + id)
                .isPiatraPretioasa(this.nextBoolean())
                .build();
    }

    // returneaza o instanta a unei Bijuterii, cu id unic
    synchronized Bijuterie nextBijuterie() {
        idCurent++;
        return new Bijuterie.Builder()
                .withId(idCurent)
                .withNume("Bijuterie_" + idCurent)
                .withPretVanzare(-1)
                .withPretMinim(1000 + Math.round(this.nextDouble() * 9000))
                .withAn(1500 + this.nextInt(2020 - 1500))
                .withMaterial("Material_" + idCurent)
                .isPiatraPretioasa(this.nextBoolean())
                .build();
    }

    // returneaza o instanta a unei Mobile, cu id-ul specificat
    Mobila nextMobila(int id) {
        return new Mobila.Builder()
                .withId(id)
                .withNume("Mobila_" + id)
                .withPretVanzare(-1)
                .withPretMinim(1000 + Math.round(this.nextDouble() * 9000))
                .withAn(1800 + this.nextInt(2020 - 1800))
                .withTip("Tip_" + id)
                .withMaterial("Material_" + id)
                .build();
    }

    // returneaza o instanta a unei Mobile, cu id unic
    synchronized Mobila nextMobila() {
        idCurent++;
        return new Mobila.Builder()
                .withId(idCurent)
                .withNume("Mobila_" + idCurent)
                .withPretVanzare(-1)
                .withPretMinim(1000 + Math.round(this.nextDouble() * 9000))
                .withAn(1800 + this.nextInt(2020 - 1800))
                .withTip("Tip_" + idCurent)
                .withMaterial("Material_" + idCurent)
                .build();
    }

    // returneaza o instanta a unei subclase de Client, cu id-ul specificat
    Client nextClient(int id) {
        if (this.nextBoolean()) return nextPersoanaFizica(id);
        else return nextPersoanaJuridica(id);
    }

    // returneaza o instanta a unei subclase de Produs, cu id unic
    synchronized Client nextClient() {
        if (this.nextBoolean()) return nextPersoanaFizica();
        else return nextPersoanaJuridica();
    }

    // returneaza o instanta a unei PersoaneFizice, cu id-ul specificat
    PersoanaFizica nextPersoanaFizica(int id) {
        return new PersoanaFizica.Builder()
                .withId(id)
                .withNume("PersoanaFizica_" + id)
                .withAdresa("Adresa_" + id)
                .withNrParticipari(Math.abs(this.nextInt(10)))
                .withNrLicitatiiCastigate(0)
                .withDataNastere("DataNastere_" + id)
                .build();
    }

    // returneaza o instanta a unei PersoaneFizice, cu id unic
    synchronized PersoanaFizica nextPersoanaFizica() {
        idCurent++;
        return new PersoanaFizica.Builder()
                .withId(idCurent)
                .withNume("PersoanaFizica_" + idCurent)
                .withAdresa("Adresa_" + idCurent)
                .withNrParticipari(Math.abs(this.nextInt(10)))
                .withNrLicitatiiCastigate(0)
                .withDataNastere("DataNastere_" + idCurent)
                .build();
    }

    // returneaza o instanta a unei PersoaneJuridice, cu id-ul specificat
    PersoanaJuridica nextPersoanaJuridica(int id) {
        return new PersoanaJuridica.Builder()
                .withId(id)
                .withNume("PersoanaJuridica_" + id)
                .withAdresa("Adresa_" + id)
                .withNrParticipari(Math.abs(this.nextInt(50)))
                .withNrLicitatiiCastigate(0)
                .withCapitalSocial(50000 + Math.round(this.nextDouble() * 950000))
                .withCompanie(this.nextCompanie())
                .build();
    }

    // returneaza o instanta a unei PersoaneJuridice, cu id unic
    synchronized PersoanaJuridica nextPersoanaJuridica() {
        idCurent++;
        return new PersoanaJuridica.Builder()
                .withId(idCurent)
                .withNume("PersoanaJuridica_" + idCurent)
                .withAdresa("Adresa_" + idCurent)
                .withNrParticipari(Math.abs(this.nextInt(50)))
                .withNrLicitatiiCastigate(0)
                .withCapitalSocial(50000 + Math.round(this.nextDouble() * 950000))
                .withCompanie(this.nextCompanie())
                .build();
    }

    // returneaza o instanta a unei Companii
    PersoanaJuridica.Companie nextCompanie() {
        if (this.nextBoolean()) return PersoanaJuridica.Companie.SA;
        else return PersoanaJuridica.Companie.SRL;
    }

    // returneaza o instanta a unei Licitatii, cu id-ul specificat
    Licitatie nextLicitatie(int id) {
        return new Licitatie.Builder()
                .withId(id)
                .withNrParticipanti(2 + Math.abs(this.nextInt(10)))
                .withIdProdus(-1)
                .withNrPasiMaxim(5 + Math.abs(this.nextInt(10)))
                .build();
    }

    // returneaza o instanta a unei Licitatii, cu id unic
    synchronized Licitatie nextLicitatie() {
        idCurent++;
        return new Licitatie.Builder()
                .withId(idCurent)
                .withNrParticipanti(2 + Math.abs(this.nextInt(10)))
                .withIdProdus(-1)
                .withNrPasiMaxim(5 + Math.abs(this.nextInt(10)))
                .build();
    }
}

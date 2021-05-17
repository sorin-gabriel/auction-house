import java.util.ArrayList;
import java.util.Collection;

public class TestCasaDeLicitatii {
    private int nrProduse;
    private int nrClienti;
    private int nrBrokeri;
    private int nrLicitatii;

    CasaDeLicitatii casa = CasaDeLicitatii.getInstance();
    Administrator admin = Administrator.getInstance();

    Generator generator = new Generator();

    public TestCasaDeLicitatii() {
        this.nrProduse = 25;
        this.nrClienti = 100;
        this.nrBrokeri = 3;
        this.nrLicitatii = 5;
    }

    public TestCasaDeLicitatii(int nrProduse, int nrClienti, int nrBrokeri, int nrLicitatii) {
        this.nrProduse = nrProduse;
        this.nrClienti = nrClienti;
        this.nrBrokeri = nrBrokeri;
        this.nrLicitatii = nrLicitatii;
    }

    public Thread genereazaBrokeri() {
        Thread threadBrokeri = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < nrBrokeri; i++) {
                    casa.adaugaBroker(new Broker.Builder().build());
                }
            }
        });
        threadBrokeri.start();
        return threadBrokeri;
    }

    public Thread genereazaProduse() {
        Thread threadProduse = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < nrProduse; i++) {
                    admin.adaugaProdus(generator.nextProdus());
                }
            }
        });
        threadProduse.start();
        return threadProduse;
    }

    public Thread genereazaClienti() {
        Thread threadClienti = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < nrClienti; i++) {
                    casa.adaugaClient(generator.nextClient());
                }
            }
        });
        threadClienti.start();
        return threadClienti;
    }

    public void vizualizeazaProduse(Collection<Client> clienti) throws InterruptedException {
        ArrayList<Thread> threaduriAlegereProdus = new ArrayList<>();
        for (Client client : clienti) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean hasChosen = false;
                    while (!hasChosen) {
                        for (int idProdus : casa.getProduse().keySet()) {
                            if (generator.nextBoolean()) {
                                client.veziProdus(idProdus);
                                if (generator.nextBoolean() && generator.nextBoolean() && generator.nextBoolean()) {
                                    hasChosen = true;
                                    client.inregistreazaCerere(idProdus);
                                    // vizualizare alegeri - true/false
                                    if (false) {
                                        System.out.println(client.getNume() + " alege produsul cu id " + idProdus);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                }
            });
            t.start();
            threaduriAlegereProdus.add(t);
        }
        for (Thread t : threaduriAlegereProdus) t.join();
    }

    public Thread pornesteLicitatie(int idProdus) {
        Licitatie licitatie = generator.nextLicitatie();
        licitatie.setIdProdus(idProdus);
        licitatie.setNrParticipanti(casa.getCereri().get(idProdus).size());
        casa.adaugaLicitatie(licitatie);

        for (Client c : casa.getCereri().get(idProdus)) {
            Broker broker = casa.getBrokeri().get(generator.nextInt(casa.getBrokeri().size()));
            broker.addClient(licitatie.getId(), c);
        }

        Thread threadlicitatie = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Incepe licitatia pentru produsul cu id " + idProdus + ", nrParticipanti: " + licitatie.getNrParticipanti());
                    licitatie.ruleazaLicitatie();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadlicitatie.start();
        return threadlicitatie;
    }

    public void ruleazaTest(int nrTest) throws InterruptedException {
        System.out.println("Incepe testul " + nrTest);

        Thread threadBrokeri = genereazaBrokeri();
        Thread threadProduse = genereazaProduse();
        Thread threadClienti = genereazaClienti();

        threadBrokeri.join();
        threadProduse.join();
        threadClienti.join();

        vizualizeazaProduse(casa.getClienti().values());

        ArrayList<Thread> threaduriLicitatii = new ArrayList<>();

        for (int i = 0; i < nrLicitatii; i++) {
            boolean idValid = false;
            int idProdusLicitat = -1;
            for (Integer id : casa.getProduse().keySet()) {
                if (casa.getCereri().get(id).size() >= 2) {
                    idValid = true;
                    for (Licitatie l :casa.getLicitatii().values()) {
                        if (l.getIdProdus() == id) {
                            idValid = false;
                            break;
                        }
                    }
                }
                if (idValid) {
                    idProdusLicitat = id;
                    break;
                }
            }
            threaduriLicitatii.add(pornesteLicitatie(idProdusLicitat));
        }
        for (Thread t : threaduriLicitatii) t.join();
        System.out.println("Testul " + nrTest + " s-a terminat cu succes\n");
    }

    public static void main(String[] args) throws InterruptedException {
        int nrTeste = 15;

        for (int i = 0; i < nrTeste; i++) {
            // exemplu:
//            new TestCasaDeLicitatii(100, 1000, 10, 20).ruleazaTest(i);
            new TestCasaDeLicitatii().ruleazaTest(i);
        }
    }
}

import java.util.*;

public class CasaDeLicitatii {
    /*
        Design Pattern-ul SINGLETON a fost ales pentru clasa CasaDeLicitatii deoarece se
        doreste instantierea unei singure case de licitatii per test.
     */
    private static final CasaDeLicitatii instanta = new CasaDeLicitatii();

    private CasaDeLicitatii() {}

    public static CasaDeLicitatii getInstance() {
        return instanta;
    }

    // produsele sunt organizate dupa id-ul produsului
    private HashMap<Integer, Produs> produse = new HashMap<>();

    // clientii sunt organizati dupa id-ul clientului
    private HashMap<Integer, Client> clienti = new HashMap<>();

    // licitatiile sunt organizate dupa id-ul licitatiei
    private HashMap<Integer, Licitatie> licitatii = new HashMap<>();

    // retine lista de brokeri ai casei de licitatii
    private ArrayList<Broker> brokeri = new ArrayList<>();

    // cererile pentru un anumit produs sunt organizate dupa id-ul produsului
    private HashMap<Integer, ArrayList<Client>> cereri = new HashMap<>();

    /*
        Toate metodele care modifica listele de: produse, clienti, licitatii, brokeri, cereri
        sunt implementate avand in vedere accesul mai multor thread-uri la aceste liste.
        Prin folosirea cuvantului cheie "synchronized" se garanteaza corectitudiunea operatiilor.
     */

    // adauga un produs in lista de produse
    public void adaugaProdus(Produs produs) {
        synchronized (produse) {
            if (produse == null) produse = new HashMap<>();
            produse.put(produs.getId(), produs);
        }
    }

    // sterge un produs din lista de produse
    public void stergeProdus(Produs produs) {
        synchronized (produse) {
            if (produse != null && produse.containsKey(produs.getId())) produse.remove(produs.getId());
        }
    }

    // sterge un produs din lista de produse
    public void stergeProdus(int id) {
        synchronized (produse) {
            if (produse != null && produse.containsKey(id)) produse.remove(id);
        }
    }

    // permite clientului apelant sa vada produsul cu un anumit id
    public void veziProdus(Client client, int id) {
        synchronized (produse) {
            if (produse != null && produse.containsKey(id)) {
                // vizualizare alegeri - true/false
                if (false) {
                    System.out.println("Client cu id " + client.getId() + " vede produsul " + produse.get(id));
                }
            }
            else System.out.println("Produsul cu id " + id + " nu a fost gasit!");
        }
    }

    // adauga un client in lista de clienti
    public void adaugaClient(Client client) {
        synchronized (clienti) {
            if (clienti == null) clienti = new HashMap<>();
            clienti.put(client.getId(), client);
        }
    }

    // sterge un client din lista de clienti
    public void stergeClient(Client client) {
        synchronized (clienti) {
            if (clienti != null && clienti.containsKey(client.getId()))
                clienti.remove(client.getId());
        }
    }

    // adauga o licitatie in lista de licitatii
    public void adaugaLicitatie(Licitatie licitatie) {
        synchronized (licitatii) {
            if (licitatii == null) licitatii = new HashMap<>();
            licitatii.put(licitatie.getId(), licitatie);
        }
    }

    // sterge o licitatie din lista de licitatii
    public void stergeLicitatie(Licitatie licitatie) {
        synchronized (licitatii) {
            if (licitatii != null && licitatii.containsKey(licitatie.getId()))
                licitatii.remove(licitatie.getId());
        }
    }

    // adauga un broker in lista de brokeri
    public void adaugaBroker(Broker broker) {
        synchronized (brokeri) {
            if (brokeri == null) brokeri = new ArrayList<>();
            brokeri.add(broker);
        }
    }

    // sterge un broker din lista de brokeri
    public void stergeBroker(Broker broker) {
        synchronized (brokeri) {
            if (brokeri != null && brokeri.contains(broker))
                brokeri.remove(broker);
        }
    }

    // adauga cererea inregistrata de clientul apelant pentru produsul cu un anumit id
    public void adaugaCerere(Client client, int id) {
        synchronized (cereri) {
            if (cereri == null) cereri = new HashMap<>();
            if (!cereri.containsKey(id)) cereri.put(id, new ArrayList<>());
            cereri.get(id).add(client);
        }
    }

    // sterge cererea inregistrata de clientul apelant pentru produsul cu un anumit id
    public void stergeCerere(Client client, int id) {
        synchronized (cereri) {
            if (cereri != null && cereri.containsKey(id) && cereri.get(id).contains(client))
                cereri.get(id).remove(client);
        }
    }

    public HashMap<Integer, Produs> getProduse() {
        return produse;
    }

    public void setProduse(HashMap<Integer, Produs> produse) {
        this.produse = produse;
    }

    public HashMap<Integer, Client> getClienti() {
        return clienti;
    }

    public void setClienti(HashMap<Integer, Client> clienti) {
        this.clienti = clienti;
    }

    public HashMap<Integer, Licitatie> getLicitatii() {
        return licitatii;
    }

    public void setLicitatii(HashMap<Integer, Licitatie> licitatii) {
        this.licitatii = licitatii;
    }

    public ArrayList<Broker> getBrokeri() {
        return brokeri;
    }

    public void setBrokeri(ArrayList<Broker> brokeri) {
        this.brokeri = brokeri;
    }

    public HashMap<Integer, ArrayList<Client>> getCereri() {
        return cereri;
    }

    public void setCereri(HashMap<Integer, ArrayList<Client>> cereri) {
        this.cereri = cereri;
    }
}

Design Patterns folosite:
	SINGLETON (casa de licitatii, administrator)
	BUILDER (Produs si subclase, Client si subclase, Angajat si subclase)
	MEDIATOR (casa de licitatii mediaza interactiunile dintre
client/broker/administrator si lista de produse)
	OBSERVER (clientii sunt observatori, brokerul este subiectul)

Genericitate:
	Pentru a reutiliza codul, toti builderii superclaselor sunt generici.
De exemplu, clasa abstracta Produs contine clasa abstracta Builder, care
primeste 2 tipuri de obiecte: P extends Produs si B extends Builder. Clasa
concreta Tablou contine clasa concreta (Tablou.)Builder, care extinde
Produs.Builder<Tablou, (Tablou.)Builder>. In felul acesta, builder-ul pentru
Tablou poate utiliza atat metodele clasei proprii, cat si metodele clasei
Produs.Builder, fara conflict generat de tipul de date returnat la
inlantuirea metodelor.

Utilizare multithreading:
	Metodele care lucreaza cu listele de produse, clienti, licitatii,
cereri pentru produse sunt sincronizate pentru a garanta corectitudinea
rezultatelor. Mai multe licitatii ruleaza in paralel, pe thread-uri diferite.
La fiecare pas al unei licitatii, se aloca un thread pentru fiecare broker,
care este folosit pentru a actualiza ofertele clientilor intermediati de
broker la licitatia respectiva. La rularea unui test, se folosesc thread-uri
diferite pentru generarea clientilor, produselor, respectiv brokerilor.
Clientii vad produsele din lista si le aleg in paralel.

[!] Generarea Obiectelor:
	Pentru generarea obiectelor complexe (Produs, Client, Licitatie), am
extins clasa java.util.Random si am creat metode care folosesc builderii
specifici si genereaza fiecare tip de obiect, cu diferite atribute random.
Metodele au un comportament asemanator cu cele din clasa java.util.Random
si returneaza obiectele cerute: nextProdus(), nextTablou(), nextMobila(),
nextBijuterie(), nextPersoanaFizica() etc.

Testarea aplicatiei:
	In clasa TestCasaDeLicitatii exista diferite metode care controleaza
parametrii unui test. In metoda main se specifica numarul testelor si se
ruleaza teste independente cu parametrii doriti. In toate clasele exista
comentarii pentru partile importante din cod.

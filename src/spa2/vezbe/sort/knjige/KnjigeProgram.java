package spa2.vezbe.sort.knjige;

import java.util.Arrays;

import org.svetovid.Svetovid;
import org.svetovid.io.SvetovidReader;
import org.svetovid.io.SvetovidWriter;

// Klasa koja opisuje jednu knjigu
class Knjiga implements Comparable<Knjiga> {

    // Osobine jednom kreirane knjige se ne mogu menjati
    private final int id;
    private final String naslov;
    private final String autor;

    public Knjiga(int id, String naslov, String autor) {
        this.id = id;
        this.naslov = naslov;
        this.autor = autor;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getAutor() {
        return autor;
    }

    public int getId() {
        return id;
    }

    public String toString() {

        // Tekstualna reprezentacija knjige oblika:
        // 9785 J.R.R. Tolkien: The Lord of the Rings
        return id + " " + autor + ": " + naslov;

    }

    public int compareTo(Knjiga that) {

        // Najjednostavnije poredjenje je po "id" broju
        // return this.id - that.id;

        // No mi ovde poredimo prvo po autoru
        int rezultat = this.autor.compareTo(that.autor);

        // A potom po naslovu, ako je sutor isti
        if (rezultat == 0) {
            rezultat = this.naslov.compareTo(that.naslov);
        }

        // Vracamo rezultat poredjenja
        return rezultat;

    }
}

// Glavni program
public class KnjigeProgram {

    public static void main(String[] arguments) {

        // Ucitavanje knjiga iz fajla
        String fajl = Svetovid.in.readLine("Unesite ime fajla sa knjigama:");
        Knjiga[] niz = ucitajKnjige(fajl);

        // Dalje radimo samo ako smo uspeli da ucitamo knjige
        if (niz != null) {

            // Stampanje ucitanog niza
            Svetovid.out.println();
            Svetovid.out.println("Nesortirani niz:");
            Svetovid.out.println();
            stampajNiz(niz);

            // Sortiranje niza
            Arrays.sort(niz);

            // Stampanje sortiranog niza
            Svetovid.out.println();
            Svetovid.out.println("Sortirani niz:");
            Svetovid.out.println();
            stampajNiz(niz);

            // Ispis knjiga u fajl
            String izlazni = Svetovid.in.readLine("Unesite ime fajla za ispis:");
            snimiKnjige(niz, izlazni);

        // Prijavljujemo gresku ako nismo uspeli da ucitamo niz
        } else {
            System.err.println("Greska pri ucitavanju niza, kraj rada");
        }

    }

    public static Knjiga[] ucitajKnjige(String fajl) {

        // Ako ne mozemo da otvorimo fajl, ne ucitavamo knjige
        if (!Svetovid.testIn(fajl)) {
            return null;
        }

        // Ako mozemo, napravimo precicu 'in' da ne bismo morali
        // svaki put da kucamo 'Svetovid.in(fajl)'
        SvetovidReader in = Svetovid.in(fajl);

        // Koliko ima knjiga u fajlu?
        int br = Svetovid.in(fajl).readInt();

        // Napravimo niz odgovarajuce velicine
        Knjiga[] rez = new Knjiga[br];

        // Ucitamo knjige
        for (int i = 0; i < br; i++) {

            // Ucitamo podatke o knjizi
            int id = in.readInt();
            String naslov = in.readLine();
            String autor = in.readLine();

            // Napravimo knjigu
            Knjiga knjiga = new Knjiga(id, naslov, autor);

            // Dodamo knjigu u niz
            rez[i] = knjiga;

        }

        // Zatvorimo fajl
        Svetovid.closeIn(fajl);

        // Vratimo ucitani niz
        return rez;

    }

    public static void stampajNiz(Knjiga[] niz) {
        for (Knjiga knjiga : niz) {
            System.out.println(knjiga);
        }
    }

    public static void snimiKnjige(Knjiga[] niz, String ime) {

        // Ako ne mozemo da otvorimo fajl, vracamo se u glavni program
        if (!Svetovid.testOut(ime)) {
            System.err.println("Snimanje u fajl nije moguce!");
            return;
        }

        // Napravimo precicu 'out' da ne bismo morali
        // svaki put da kucamo 'Svetovid.out(ime)'
        SvetovidWriter out = Svetovid.out(ime);

        // Upisemo ukupan broj knjiga, kao u originalnom fajlu
        out.println(niz.length);

        // Za svaku knjigu upisemo redom sve podatke
        for (Knjiga knjiga : niz) {
            out.println(knjiga.getId());
            out.println(knjiga.getNaslov());
            out.println(knjiga.getAutor());
        }

        // Zatvorimo izlaz
        out.close();

    }
}

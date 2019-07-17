package Gra.Swiat;

import Gra.Swiat.Organizm.*;
import Gra.Swiat.Organizm.Rosliny.Gatunki.Guarana;
import Gra.Swiat.Organizm.Rosliny.Gatunki.Mlecz;
import Gra.Swiat.Organizm.Rosliny.Gatunki.Trawa;
import Gra.Swiat.Organizm.Rosliny.Gatunki.WilczeJagody;
import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Organizm.Zwierzeta.Gatunki.*;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;

import java.util.*;
import java.util.stream.*;


public class Swiat {

    static protected final int POLAX = 17;
    static protected final int POLAY = 24;
    static protected final Set<Lokalizacja> niemozliweDoPrzejscia =
            Stream.of(new Lokalizacja(8,7), new Lokalizacja(9,7), new Lokalizacja(8,8), new Lokalizacja(9,8)
                    , new Lokalizacja(10,8), new Lokalizacja(11, 8)
                    , new Lokalizacja(7, 9), new Lokalizacja(8, 9), new Lokalizacja(9, 9), new Lokalizacja(10, 9)
                    , new Lokalizacja(11, 9), new Lokalizacja(12, 9), new Lokalizacja(6, 10), new Lokalizacja(7, 10)
                    , new Lokalizacja(8, 10), new Lokalizacja(9, 10), new Lokalizacja(10, 10), new Lokalizacja(11, 10)
                    , new Lokalizacja(7, 11), new Lokalizacja(8, 11), new Lokalizacja(9, 11), new Lokalizacja(10, 11)
                    , new Lokalizacja(11, 11), new Lokalizacja(7, 12), new Lokalizacja(8, 12), new Lokalizacja(9, 12)
                    , new Lokalizacja(10, 12), new Lokalizacja(11, 12), new Lokalizacja(12, 12), new Lokalizacja(7, 13)
                    , new Lokalizacja(8, 13), new Lokalizacja(9, 13), new Lokalizacja(10, 13), new Lokalizacja(11, 13)
                    , new Lokalizacja(8, 14), new Lokalizacja(9, 14), new Lokalizacja(10, 14), new Lokalizacja(11, 14))
                    .collect(Collectors.toSet());
    static protected final int MAX_ZALUDNIENIE = (POLAX*POLAY) - niemozliweDoPrzejscia.size();

    private Map<Lokalizacja, Organizm> mapaobiektow = new HashMap<>();

    private static final Map<String, Integer> zaludnienie =
            Map.of("Zolw", 2, "Lis", 2, "Owca", 2,
                    "Wilk", 2, "Antylopa", 2, "Guarana", 4, "Mlecz", 2, "Trawa", 10,
                    "WilczeJagody", 4);


    public Swiat() {
        zaludnijSwiat();
        System.out.println("Start Gry");
        int tura = 0;

        do {
            wykonajTure();
            System.out.println("Tura: " + tura++);

        } while (czyOstatniaTura() == false);
    }


    public boolean czyKolizja(Lokalizacja pole) {
        return (this.getMapaobiektow().containsKey(pole)) ? true : false;
    }

    public Map<Lokalizacja, Organizm> getMapaobiektow() {
        return mapaobiektow;
    }

    protected Swiat getSwiat() {
        return this;
    }

    private boolean czyOstatniaTura() {
        boolean ostatnia = true;
        List<Organizm> pozostajacyPrzyZyciu = getMapaobiektow().values().stream().collect(Collectors.toList());
        for (Organizm organizm : pozostajacyPrzyZyciu) {
            if(organizm.getClass().getSuperclass().equals(Zwierze.class)) {
                ostatnia = false;
                break;
            }
        }

        if (iloscOrganizmow() > MAX_ZALUDNIENIE) {
            ostatnia = true;
        }
        return ostatnia;
    }

    private int iloscOrganizmow() {
        int zwierzeta = this.getMapaobiektow().values().stream()
                .filter(organizm -> organizm.getClass().getSuperclass().equals(Zwierze.class))
                .collect(Collectors.toList()).size();
        int rosliny = this.getMapaobiektow().values().stream()
                .filter(organizm -> organizm.getClass().getSuperclass().equals(Roslina.class))
                .collect(Collectors.toList()).size();

        return rosliny+zwierzeta;
    }

    private void wykonajTure() {
        List<Organizm> listaOrganizmow = this.getMapaobiektow()
                .values()
                .stream()
                .sorted(Comparator.comparing(Organizm::getInicjatywa, Comparator.reverseOrder())
                .thenComparing(Organizm::getWiek, Comparator.reverseOrder())
                )
                .collect(Collectors.toCollection(LinkedList::new));
        for (int i = 0; i < listaOrganizmow.size(); i++) {
            listaOrganizmow.get(i).akcja();
            listaOrganizmow.get(i).setWiek(listaOrganizmow.get(i).getWiek() + 1);
        }
    }

    private void rysujSwiat() {

    }

    private Lokalizacja znajdzPoleDoZaludnienia() {
        boolean zajetePole = true;
        Lokalizacja lokalizacja = null;
        while (zajetePole) {
            lokalizacja = new Lokalizacja(new Random().nextInt(POLAX + 1),new Random().nextInt(POLAY + 1));
            if (!(this.mapaobiektow.containsKey(lokalizacja)) && czyMoznaWejscNaPole(lokalizacja)) {
                zajetePole = false;
            }
        }
        return lokalizacja;
    }

    private void zaludnijSwiat() {
        for (Map.Entry<String, Integer> keySet : zaludnienie.entrySet()) {
            for (int i = 0; i < keySet.getValue(); i++) {
                Lokalizacja pole = znajdzPoleDoZaludnienia();
                this.mapaobiektow.put(pole, instanceCreator(keySet.getKey(), pole));
            }
        }
    }


    public Organizm instanceCreator(String klasa, Lokalizacja pole) {
        Organizm organizm = null;
        switch (klasa) {
            case "Trawa":
                organizm = new Trawa(pole, getSwiat());
                break;
            case "Guarana":
                organizm = new Guarana(pole, getSwiat());
                break;
            case "Mlecz":
                organizm = new Mlecz(pole, getSwiat());
                break;
            case "WilczeJagody":
                organizm = new WilczeJagody(pole, getSwiat());
                break;
            case "Antylopa":
                organizm = new Antylopa(pole, getSwiat());
                break;
            case "Czlowiek":
                organizm = new Czlowiek(pole, getSwiat());
                break;
            case "Lis":
                organizm = new Lis(pole, getSwiat());
                break;
            case "Owca":
                organizm = new Owca(pole, getSwiat());
                break;
            case "Wilk":
                organizm = new Wilk(pole, getSwiat());
                break;
            case "Zolw":
                organizm = new Zolw(pole, getSwiat());
                break;
        }
        return organizm;
    }

    private boolean czyMoznaWejscNaPole(Lokalizacja pole) {

        boolean mozna = true;

        for (Lokalizacja ints : Swiat.niemozliweDoPrzejscia) {
            if (ints.equals(pole)) {
                mozna = false;
                break;
            }
        }

        return mozna;
    }


}

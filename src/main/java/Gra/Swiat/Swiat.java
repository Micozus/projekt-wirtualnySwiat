package Gra.Swiat;

import Gra.Swiat.Organizm.*;
import Gra.Swiat.Organizm.Rosliny.Gatunki.Guarana;
import Gra.Swiat.Organizm.Rosliny.Gatunki.Mlecz;
import Gra.Swiat.Organizm.Rosliny.Gatunki.Trawa;
import Gra.Swiat.Organizm.Rosliny.Gatunki.WilczeJagody;
import Gra.Swiat.Organizm.Zwierzeta.Gatunki.*;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;

import java.util.*;
import java.util.stream.*;


public class Swiat {

    static protected final int POLAX = 17;
    static protected final int POLAY = 24;
    static protected final Set<int[]> niemozliweDoPrzejscia =
            Stream.of(new int[]{8, 7}, new int[]{9, 7}
                    , new int[]{8, 8}, new int[]{9, 8}, new int[]{10, 8}, new int[]{11, 8}
                    , new int[]{7, 9}, new int[]{8, 9}, new int[]{9, 9}, new int[]{10, 9}
                    , new int[]{11, 9}, new int[]{12, 9}, new int[]{6, 10}, new int[]{7, 10}
                    , new int[]{8, 10}, new int[]{9, 10}, new int[]{10, 10}, new int[]{11, 10}
                    , new int[]{7, 11}, new int[]{8, 11}, new int[]{9, 11}, new int[]{10, 11}
                    , new int[]{11, 11}, new int[]{7, 12}, new int[]{8, 12}, new int[]{9, 12}
                    , new int[]{10, 12}, new int[]{11, 12}, new int[]{12, 12}, new int[]{7, 13}
                    , new int[]{8, 13}, new int[]{9, 13}, new int[]{10, 13}, new int[]{11, 13}
                    , new int[]{8, 14}, new int[]{9, 14}, new int[]{10, 14}, new int[]{11, 14})
                    .collect(Collectors.toSet());

    private Map<int[], Organizm> mapaobiektow = new HashMap<>();

    private static final Map<String, Integer> zaludnienie =
            Map.of("Zolw", 5, "Lis", 4, "Owca", 10, "Wilk", 3,
                    "Antylopa", 7, "Guarana", 4, "Mlecz", 4, "Trawa", 21,
                    "WilczeJagody", 2);

    public Swiat() {
        zaludnijSwiat();
        System.out.println("Start Gry");
        int tura = 0;
        int zwierzeta;
        int rosliny;
        do {
            wykonajTure();
            System.out.println("Tura: " + tura++);
            zwierzeta = 0;
            rosliny = 0;
            int mapa = this.getMapaobiektow().size();
            System.out.println("Liczba organizmow: \n" +
                    "Zwierzeta: " + zwierzeta + "\n" +
                    "Rosliny: " + rosliny);
        } while (czyOstatniaTura() == false);
    }

    public void removeObiekt(int[] pole) {
        Set<Map.Entry<int[], Organizm>> mapaObiektow = this.getMapaobiektow().entrySet();

        int[] key = null;
        for (Map.Entry<int[], Organizm> organizmEntry : mapaObiektow) {
            if (Arrays.equals(organizmEntry.getKey(), pole)) {
                key = organizmEntry.getKey();
            }
        }
        this.getMapaobiektow().remove(key);
    }

    public Organizm getObiekt(int[] pole) {
        Set<Map.Entry<int[], Organizm>> mapaObiektow = this.getMapaobiektow().entrySet();
        for (Map.Entry<int[], Organizm> organizmEntry : mapaObiektow) {
            if (Arrays.equals(organizmEntry.getKey(), pole)) {
                return organizmEntry.getValue();
            }
        }
        return null;
    }

    public boolean czyKolizja(int[] pole) {
        Set<int[]> zajetePola = this.getMapaobiektow().keySet();
        for (int[] ints : zajetePola) {
            if (Arrays.equals(ints, pole)) {
                return true;
            }
        }
        return false;
    }

    public Map<int[], Organizm> getMapaobiektow() {
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
        return ostatnia;
    }

    private void wykonajTure() {
        List<Organizm> listaOrganizmow = this.getMapaobiektow().values().stream().collect(Collectors.toList());
        for (int i = 0; i < listaOrganizmow.size(); i++) {
            listaOrganizmow.get(i).akcja();
            listaOrganizmow.get(i).setWiek(listaOrganizmow.get(i).getWiek() + 1);
        }
    }

    private void rysujSwiat() {

    }

    private int[] znajdzPoleDoZaludnienia() {
        boolean zajetePole = true;
        int[] lokalizacja = new int[2];
        while (zajetePole) {
            lokalizacja[0] = new Random().nextInt(POLAX + 1);
            lokalizacja[1] = new Random().nextInt(POLAY + 1);
            if (!(this.mapaobiektow.containsKey(lokalizacja)) && czyMoznaWejscNaPole(lokalizacja)) {
                zajetePole = false;
            }
        }
        return lokalizacja;
    }

    private void zaludnijSwiat() {
        for (Map.Entry<String, Integer> keySet : zaludnienie.entrySet()) {
            for (int i = 0; i < keySet.getValue(); i++) {
                int[] pole = znajdzPoleDoZaludnienia();
                this.mapaobiektow.put(pole, instanceCreator(keySet.getKey(), pole));
            }
        }
    }


    public Organizm instanceCreator(String klasa, int[] pole) {
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

    private boolean czyMoznaWejscNaPole(int[] pole) {

        boolean mozna = true;

        for (int[] ints : Swiat.niemozliweDoPrzejscia) {
            if (Arrays.equals(ints, pole)) {
                mozna = false;
                break;
            }
        }

        return mozna;
    }


}

package Gra.Swiat;

import Gra.GUI.InstanceImage;
import Gra.Gra;
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


public class Swiat implements IZyje {

    private Gra gra;
    static final int POLAX = 17;
    static final int POLAY = 24;
    static final Set<Lokalizacja> niemozliweDoPrzejscia =
            Stream.of(new Lokalizacja(8,7), new Lokalizacja(9,7), new Lokalizacja(8,8), new Lokalizacja(9,8)
                    , new Lokalizacja(10,8), new Lokalizacja(11, 8)
                    , new Lokalizacja(7, 9), new Lokalizacja(8, 9), new Lokalizacja(9, 9), new Lokalizacja(10, 9)
                    , new Lokalizacja(11, 9), new Lokalizacja(12, 9), new Lokalizacja(6, 10), new Lokalizacja(7, 10)
                    , new Lokalizacja(8, 10), new Lokalizacja(9, 10), new Lokalizacja(10, 10), new Lokalizacja(11, 10)
                    , new Lokalizacja(7, 11), new Lokalizacja(8, 11), new Lokalizacja(9, 11), new Lokalizacja(10, 11)
                    , new Lokalizacja(11, 11), new Lokalizacja(7, 12), new Lokalizacja(8, 12), new Lokalizacja(9, 12)
                    , new Lokalizacja(10, 12), new Lokalizacja(11, 12), new Lokalizacja(12, 12), new Lokalizacja(7, 13)
                    , new Lokalizacja(8, 13), new Lokalizacja(9, 13), new Lokalizacja(10, 13), new Lokalizacja(11, 13)
                    , new Lokalizacja(8, 14), new Lokalizacja(9, 14), new Lokalizacja(10, 14), new Lokalizacja(11, 14)
                    , new Lokalizacja(15, 10))
                    .collect(Collectors.toSet());
    private static final int MAX_ZALUDNIENIE = (POLAX*POLAY) - niemozliweDoPrzejscia.size();
    private Organizm humanPlayer;
    private Map<Lokalizacja, Organizm> mapaobiektow = new HashMap<>();
    private static final Map<String, Integer> zaludnienie =
            Stream.of(new Object[][] {{"Zolw", 3} , {"Lis", 3}, {"Owca", 3},
                    {"Wilk", 3}, {"Antylopa", 3}, {"Guarana", 3}, {"Mlecz", 3}, {"Trawa", 10},
                    {"WilczeJagody", 3}, {"Czlowiek" , 1}})
            .collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));


    public Swiat(Gra gra) {
        this.gra = gra;
        zaludnijSwiat();
        iloscOrganizmow(gra);
    }

    public Gra getGra() {
        return gra;
    }

    public Organizm getHumanPlayer() {
        return humanPlayer;
    }

    public Map<Lokalizacja, Organizm> getMapaobiektow() {
        return mapaobiektow;
    }

    public Swiat getSwiat() {
        return this;
    }

    public boolean czyOstatniaTura(Gra gra) {
        boolean ostatnia = true;
        List<Organizm> pozostajacyPrzyZyciu = new ArrayList<>(getMapaobiektow().values());
        for (Organizm organizm : pozostajacyPrzyZyciu) {
            if(organizm.getClass().equals(Czlowiek.class) && iloscOrganizmow(gra) != 1) {
                ostatnia = false;
                break;
            }
        }

        if (iloscOrganizmow(gra) > MAX_ZALUDNIENIE) {
            ostatnia = true;
        }
        return ostatnia;
    }

    public int iloscOrganizmow(Gra gra) {
        int zwierzeta = (int) this.getMapaobiektow().values().stream()
                .filter(organizm -> organizm.getClass().getSuperclass().equals(Zwierze.class)).count();
        int rosliny = (int) this.getMapaobiektow().values().stream()
                .filter(organizm -> organizm.getClass().getSuperclass().equals(Roslina.class)).count();
        gra.setIloscOrganizmowZwierzecych(zwierzeta);
        gra.setIloscOrganizmowRoslinnych(rosliny);
        return rosliny+zwierzeta;
    }

    private Lokalizacja znajdzPoleDoZaludnienia() {
        boolean zajetePole = true;
        Lokalizacja lokalizacja = null;
        while (zajetePole) {
            lokalizacja = new Lokalizacja(new Random().nextInt(POLAX )+1,new Random().nextInt(POLAY )+1);
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
                this.mapaobiektow.put(pole, instanceCreator(keySet.getKey(), pole, 1f));
            }
        }
    }


    public Organizm instanceCreator(String klasa, Lokalizacja pole, float alpha) {
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
                this.humanPlayer = organizm;
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
        InstanceImage iconToSet = new InstanceImage(organizm, alpha);
        organizm.setInstanceImage(iconToSet);
        getGra().getMapaObrazow().put(organizm,iconToSet);

        return organizm;
    }

}

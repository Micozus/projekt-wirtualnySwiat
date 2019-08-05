package Gra.Swiat;

import Gra.GUI.TypAnimacji;
import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Zdarzenie;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IZyje {

    default boolean deathIsComing(Organizm organizm) {
        if (organizm.getWiek() >= organizm.getMAXAGE()) {
            int deathProbability = organizm.getDeathProbability();
            int szansa = new Random().nextInt(100 + 1);
            if (szansa < deathProbability) {
                organizm.getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.FADEOUT, organizm);
                organizm.getJakiSwiat().getMapaobiektow().remove(organizm.getPolozenie());
                organizm.getJakiSwiat().getGra().getLogSet().add(new Logi(organizm.getJakiSwiat().getGra().getTura(), Zdarzenie.SMIERC, organizm.getPolozenie(), organizm));
                return true;
            } else {
                organizm.setDeathProbability(organizm.getDeathProbability() + 10);

            }
        }
        return false;
    }

    default boolean czyKolizja(Lokalizacja pole, Map<Lokalizacja, Organizm> mapa) {
        return mapa.containsKey(pole);
    }

    default boolean czyWewnatrzMapy(Lokalizacja lokalizacja) {

        return (((lokalizacja.getxValue() > 0) && (lokalizacja.getxValue() <= Swiat.POLAX))) && (((lokalizacja.getYvalue() > 0) && (lokalizacja.getYvalue() <= Swiat.POLAY)));
    }

    default boolean czyMoznaWejscNaPole(Lokalizacja lokalizacja) {

        return Swiat.niemozliweDoPrzejscia.contains(lokalizacja) ? false : true;
    }

    default List<Lokalizacja> obszaryWokol(Lokalizacja obecnePole, Map<Lokalizacja, Organizm> mapa) {
        List<Lokalizacja> obszaryWokol =

                Stream.of(
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() - 1),
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue() - 1),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue() - 1))
                        .collect(Collectors.toList());

        List<Lokalizacja> obszaryWokolReturn = new ArrayList<>();
        for (Lokalizacja lokalizacja : obszaryWokol) {
            if(czyWewnatrzMapy(lokalizacja) && czyMoznaWejscNaPole(lokalizacja) && !mapa.containsKey(lokalizacja)) {
                obszaryWokolReturn.add(lokalizacja);
            }
        }
        return obszaryWokolReturn;
    }

    default List<Lokalizacja> mozliweSciezki(Lokalizacja obecnePole) {
        List<Lokalizacja> mozliweSciezki =
                Stream.of(
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() - 1))
                        .collect(Collectors.toList());
        List<Lokalizacja> mozliweSciezkiReturn = new ArrayList<>();

        for (Lokalizacja lokalizacja : mozliweSciezki) {
            if (czyWewnatrzMapy(lokalizacja) && czyMoznaWejscNaPole(lokalizacja)) {
                mozliweSciezkiReturn.add(lokalizacja);
            }
        }
        return mozliweSciezkiReturn;
    }
}

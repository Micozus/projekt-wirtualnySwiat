package Gra.Swiat;

import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Zdarzenie;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IZyje {

    default boolean deathIsComing(Organizm organizm) {
        if (organizm.getWiek() >= organizm.getMAXAGE()) {
            int deathProbability = organizm.getDeathProbability();
            int szansa = new Random().nextInt(100 + 1);
            if (szansa < deathProbability) {
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

        return ((((lokalizacja.getxValue() > 0) && (lokalizacja.getxValue() < Swiat.POLAX))) && (((lokalizacja.getYvalue() > 0) && (lokalizacja.getYvalue() < Swiat.POLAY))));
    }

    default boolean czyMoznaWejscNaPole(Lokalizacja lokalizacja) {

        return (Swiat.niemozliweDoPrzejscia.contains(lokalizacja)) ? false : true;
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
        for (int i = 0; i < obszaryWokol.size(); i++) {
            if (!(czyWewnatrzMapy(obszaryWokol.get(i)) && czyMoznaWejscNaPole(obszaryWokol.get(i))) || mapa.containsKey(obszaryWokol.get(i))) {
                obszaryWokol.remove(obszaryWokol.get(i));
            }
        }
        return (obszaryWokol.size() != 0) ? obszaryWokol : null;
    }

    default List<Lokalizacja> mozliweSciezki(Lokalizacja obecnePole) {
        List<Lokalizacja> mozliweSciezki =
                Stream.of(
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() - 1))
                        .collect(Collectors.toList());
        for (int i = 0; i < mozliweSciezki.size(); i++) {
            if (!(czyWewnatrzMapy(mozliweSciezki.get(i)) && czyMoznaWejscNaPole(mozliweSciezki.get(i)))) {
                mozliweSciezki.remove(i);
            }
        }
        return mozliweSciezki;
    }
}

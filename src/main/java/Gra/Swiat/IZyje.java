package Gra.Swiat;

import Gra.Swiat.Organizm.Organizm;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IZyje {


    default boolean czyWewnatrzMapy(Lokalizacja lokalizacja) {

        return ((lokalizacja.getxValue() > 0) && (lokalizacja.getxValue() < Swiat.POLAX)) && (((lokalizacja.getYvalue() > 0) && (lokalizacja.getYvalue() < Swiat.POLAY))) ? true : false;
    }

    default boolean czyMoznaWejscNaPole(Lokalizacja lokalizacja) {

        boolean mozna = true;
        for (Lokalizacja ints : Swiat.niemozliweDoPrzejscia) {
            if (ints.equals(lokalizacja)) {
                mozna = false;
                break;
            }
        }
        return mozna;
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
            if(!(czyWewnatrzMapy(obszaryWokol.get(i)) || !(czyMoznaWejscNaPole(obszaryWokol.get(i))) || mapa.containsKey(obszaryWokol.get(i)))) {
                obszaryWokol.remove(obszaryWokol.get(i));
            }
        }
        return obszaryWokol;
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
            if (!czyWewnatrzMapy(mozliweSciezki.get(i)) || !czyMoznaWejscNaPole(mozliweSciezki.get(i))) {
                mozliweSciezki.remove(i);
            }
        }
        return mozliweSciezki;
    }
}

package Gra.Swiat;

import Gra.Swiat.Organizm.Organizm;

import java.util.Arrays;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface IZyje {


    default boolean czyWewnatrzMapy(int[] pole) {

        return ((pole[0] > 0) && (pole[0] < Swiat.POLAX)) && (((pole[1] > 0) && (pole[1] < Swiat.POLAY))) ? true : false;
    }

    default boolean czyMoznaWejscNaPole(int[] pole) {

        boolean mozna = true;

        for (int[] ints : Swiat.niemozliweDoPrzejscia) {
            if (Arrays.equals(ints, pole)) {
                mozna = false;
                break;
            }
        }

        return mozna;
    }

    default List<int[]> obszaryWokol(int[] obecnePole, Map<int[], Organizm> mapa) {
        List<int[]> obszaryWokol =
                Stream.of(
                        new int[]{obecnePole[0] + 1, obecnePole[1]},
                        new int[]{obecnePole[0] - 1, obecnePole[1]},
                        new int[]{obecnePole[0], obecnePole[1] + 1},
                        new int[]{obecnePole[0], obecnePole[1] - 1},
                        new int[]{obecnePole[0] + 1, obecnePole[1] + 1},
                        new int[]{obecnePole[0] - 1, obecnePole[1] - 1},
                        new int[]{obecnePole[0] - 1, obecnePole[1] + 1},
                        new int[]{obecnePole[0] + 1, obecnePole[1] - 1})
                        .collect(Collectors.toList());
        for (int i = 0; i < obszaryWokol.size(); i++) {
            if(!(czyWewnatrzMapy(obszaryWokol.get(i)) || !(czyMoznaWejscNaPole(obszaryWokol.get(i))) || mapa.containsKey(obszaryWokol.get(i)))) {
                obszaryWokol.remove(obszaryWokol.get(i));
            }
        }
        return obszaryWokol;
    }

    default List<int[]> mozliweSciezki(int[] obecnePole) {
        List<int[]> mozliweSciezki =
                Stream.of(
                        new int[]{obecnePole[0] + 1, obecnePole[1]},
                        new int[]{obecnePole[0] - 1, obecnePole[1]},
                        new int[]{obecnePole[0], obecnePole[1] + 1},
                        new int[]{obecnePole[0], obecnePole[1] - 1})
                        .collect(Collectors.toList());
        for (int i = 0; i < mozliweSciezki.size(); i++) {
            if (!czyWewnatrzMapy(mozliweSciezki.get(i)) || !czyMoznaWejscNaPole(mozliweSciezki.get(i))) {
                mozliweSciezki.remove(i);
            }
        }
        return mozliweSciezki;
    }
}

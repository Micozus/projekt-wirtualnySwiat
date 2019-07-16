package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Antylopa extends Zwierze {

    private String typeName = "Antylopa";
    private int sila = 4;
    private int inicjatywa = 4;

    public Antylopa(int[] polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public void akcja() {
    // Zasieg ruchu wynosi 2 pola
        for (int i = 0; i < 2; i++) {
            super.akcja();
        }
    }


    @Override
    public void kolizja(int[] pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        int[] poprzedniePole = organizmAtakujacy.getPolozenie();
    // 50% szans na ucieczke przed walka, przesuwa sie wtedy na sasiednie, niezajete pole
        int szansa = new Random().nextInt(100 + 1);
        if (organizmBroniacy == this && szansa > 50) {
            List<int[]> obszaryWokol = obszaryWokol(pole, this.getJakiSwiat().getMapaobiektow());
            int[] miejsceUcieczki = obszaryWokol.get(new Random().nextInt(obszaryWokol.size()));
            organizmBroniacy.setPolozenie(miejsceUcieczki);
            getJakiSwiat().getMapaobiektow().put(miejsceUcieczki, organizmBroniacy);
            organizmAtakujacy.setPolozenie(pole);
            getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
            getJakiSwiat().removeObiekt(poprzedniePole);
        } else {
            super.kolizja(pole, organizmAtakujacy, organizmBroniacy);
        }
    }


    protected void rysowanie() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antylopa antylopa = (Antylopa) o;
        return sila == antylopa.sila &&
                inicjatywa == antylopa.inicjatywa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sila, inicjatywa);
    }
}

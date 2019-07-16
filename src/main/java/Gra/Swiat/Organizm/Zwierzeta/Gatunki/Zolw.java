package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;

import java.util.Objects;
import java.util.Random;

public class Zolw extends Zwierze {

    private String typeName = "Zolw";
    private int sila = 2;
    private int inicjatywa = 1;

    @Override
    public String getTypeName() {
        return typeName;
    }

    public Zolw(int[] polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }


    protected void rysowanie() {

    }

    @Override
    public void akcja() {
        // W 75% przypadków nie zmienia swojego położenia
        int szansa = new Random().nextInt(100 + 1);
        if (szansa > 75) {
            super.akcja();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zolw zolw = (Zolw) o;
        return sila == zolw.sila &&
                inicjatywa == zolw.inicjatywa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sila, inicjatywa);
    }

    @Override
    public void kolizja(int[] pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        // Odpiera ataki zwierzat o sile
        // <5 Napastnik musi wrocic na swoje poprzednie miejsce
        int[] poprzedniePole = organizmAtakujacy.getPolozenie();
        if (((organizmAtakujacy.getClass().getSuperclass().equals(Zwierze.class))) && organizmBroniacy == this) {
            if ((organizmAtakujacy.getSila() > organizmBroniacy.getSila()) && (organizmAtakujacy.getSila() >= 5)) {
                organizmAtakujacy.setPolozenie(pole);
                getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
                getJakiSwiat().removeObiekt(poprzedniePole);
            } else {
                return;
            }
        }
    }
}

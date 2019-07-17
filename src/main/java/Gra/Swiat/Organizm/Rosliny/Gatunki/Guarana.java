package Gra.Swiat.Organizm.Rosliny.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Swiat;

import java.util.Objects;

public class Guarana extends Roslina {

    private String typeName = "Guarana";

    @Override
    public String getTypeName() {
        return typeName;
    }

    public Guarana(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    protected void rysowanie() {

    }

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
    // Zwieksza sile zwierzecia o 3
        organizmAtakujacy.setSila(organizmAtakujacy.getSila() + 3);
        organizmAtakujacy.setPolozenie(pole);
        getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guarana guarana = (Guarana) o;
        return Objects.equals(typeName, guarana.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }
}

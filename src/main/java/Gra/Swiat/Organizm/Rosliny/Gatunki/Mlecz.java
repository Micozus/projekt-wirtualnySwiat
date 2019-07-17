package Gra.Swiat.Organizm.Rosliny.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Swiat;

import java.util.Objects;

public class Mlecz extends Roslina {

    private String typeName = "Mlecz";

    private final int sila = 0;

    public Mlecz(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public int getSila() {
        return sila;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    protected void rysowanie() {

    }

    @Override
    public void akcja() {
    // 3 proby rozprzestrzeniania na ture
        for (int i = 0; i < 3; i++) {
            super.akcja();
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mlecz mlecz = (Mlecz) o;
        return Objects.equals(typeName, mlecz.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }
}

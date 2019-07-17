package Gra.Swiat.Organizm.Rosliny.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Swiat;

import java.util.Objects;

public class WilczeJagody extends Roslina {



    private String typeName = "WilczeJagody";

    private int sila = 99;

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public int getSila() {
        return sila;
    }

    public WilczeJagody(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    protected void rysowanie() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WilczeJagody that = (WilczeJagody) o;
        return Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }
}

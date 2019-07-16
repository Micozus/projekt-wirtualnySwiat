package Gra.Swiat.Organizm.Rosliny.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Swiat;

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

}

package Gra.Swiat.Organizm.Rosliny.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Rosliny.*;
import Gra.Swiat.Swiat;

public class Trawa extends Roslina {

    private String typeName = "Trawa";

    private int sila = 0;

    @Override
    public int getSila() {
        return sila;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    public Trawa(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    protected void rysowanie() {

    }



}

package Gra.Swiat.Organizm.Rosliny.Gatunki;

import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Swiat;

public class Mlecz extends Roslina {

    private String typeName = "Mlecz";

    private final int sila = 0;

    public Mlecz(int[] polozenie, Swiat jakiSwiat) {
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

}

package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.*;

import java.util.Objects;

public class Wilk extends Zwierze {

    private String typeName = "Wilk";
    private final int MAXAGE = 46;

    private int inicjatywa = 5;
    private int sila = 9;

    public int getInicjatywa() {
        return inicjatywa;
    }

    @Override
    public int getSila() {
        return sila;
    }

    public int getMAXAGE() {
        return MAXAGE;
    }


    public String getTypeName() {
        return typeName;
    }

    public Wilk(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wilk wilk = (Wilk) o;
        return Objects.equals(typeName, wilk.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }

    @Override
    protected void rysowanie() {

    }

}

package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.*;

import java.util.Objects;

public class Wilk extends Zwierze {

    private String typeName = "Wilk";
    private int sila = 9;
    private int inicjatywa = 5;

    @Override
    public String getTypeName() {
        return typeName;
    }

    public Wilk(int[] polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wilk wilk = (Wilk) o;
        return sila == wilk.sila &&
                inicjatywa == wilk.inicjatywa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sila, inicjatywa);
    }

    @Override
    protected void rysowanie() {

    }

}

package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;

import java.util.Objects;

public class Owca extends Zwierze {

    private String typeName = "Owca";
    private int sila = 4;
    private int inicjatywa = 4;

    public Owca(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public int getInicjatywa() {
        return inicjatywa;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owca owca = (Owca) o;
        return Objects.equals(typeName, owca.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }

    @Override
    protected void rysowanie() {

    }

}

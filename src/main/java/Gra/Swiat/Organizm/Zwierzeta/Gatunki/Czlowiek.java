package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.*;

import java.util.Objects;

public class Czlowiek extends Zwierze {

    private String typeName = "Czlowiek";
    private int sila = 5;
    private int inicjatywa = 4;

    public Czlowiek(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    public void akcja() {
    // Czlowiek porusza sie w taki sam sposob jak zwierzeta, ale kierunek jego ruchu definiowany jest przez uzytkownik
    // Strzalki itp.

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Czlowiek czlowiek = (Czlowiek) o;
        return sila == czlowiek.sila &&
                inicjatywa == czlowiek.inicjatywa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sila, inicjatywa);
    }

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {

    // Specjalna umiejetnosc, osobny przycisk, inne zachowanie metody kolizja na 5 tur, 5 tur cooldown

    }

    @Override
    protected void rysowanie() {

    }
}

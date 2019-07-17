package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Lis extends Zwierze {

    private String typeName = "Lis";
    private int sila = 3;
    private int inicjatywa = 7;

    public Lis(Lokalizacja polozenie, Swiat jakiSwiat) {
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
    public void akcja() {
        Lokalizacja poprzedniePole = this.getPolozenie();
    // Dobry wech: lis nigdy nie ruszy sie na pole zajmowane przez organizm silniejszy niz on
        List<Lokalizacja> mozliweSciezki = mozliweSciezki(poprzedniePole);
        Lokalizacja nowePolozenie = mozliweSciezki.get(new Random().nextInt(mozliweSciezki.size()));
        if((getJakiSwiat().getMapaobiektow().containsKey(nowePolozenie)) && (getJakiSwiat().getMapaobiektow().get(nowePolozenie).getSila() < this.getSila())) {
            jakaKolizja(nowePolozenie, this, getJakiSwiat().getMapaobiektow().get(nowePolozenie));
        } else if((getJakiSwiat().getMapaobiektow().containsKey(nowePolozenie)) && (getJakiSwiat().getMapaobiektow().get(nowePolozenie).getSila() > this.getSila())) {
            boolean istniejeDostepneWolnePole = false;
            for (Lokalizacja ints : mozliweSciezki) {
                if(getJakiSwiat().getMapaobiektow().containsKey(ints) == false) {
                    istniejeDostepneWolnePole = true;
                    nowePolozenie = ints;
                    break;
                }
            }
            if (istniejeDostepneWolnePole) {
                this.setPolozenie(nowePolozenie);
                getJakiSwiat().getMapaobiektow().put(nowePolozenie, this);
                getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
            } else {
                return;
            }
        } else {
            this.setPolozenie(nowePolozenie);
            getJakiSwiat().getMapaobiektow().put(nowePolozenie, this);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lis lis = (Lis) o;
        return Objects.equals(typeName, lis.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }

    @Override
    protected void rysowanie() {

    }

}

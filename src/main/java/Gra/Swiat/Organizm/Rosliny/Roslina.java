package Gra.Swiat.Organizm.Rosliny;

import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.*;

import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {

    final int inicjatywa = 0;




    public Roslina(int[] polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public void kolizja(int[] pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        int[] poprzedniePole = organizmAtakujacy.getPolozenie();
        if(organizmAtakujacy.getSila() < organizmBroniacy.getSila()) {
            getJakiSwiat().getMapaobiektow().remove(organizmAtakujacy.getPolozenie());
        } else {
            organizmAtakujacy.setPolozenie(pole);
            getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
        }
    }

    @Override
    public void akcja() {
        int szansa = new Random().nextInt(100 + 1);
        if (szansa > 95) {
            List<int[]> możliweSianie = obszaryWokol(this.getPolozenie(), getJakiSwiat().getMapaobiektow());
            int[] nowePolozenie = możliweSianie.get(new Random().nextInt(możliweSianie.size()));
            getJakiSwiat().getMapaobiektow().put(nowePolozenie, getJakiSwiat().instanceCreator(this.getTypeName(), nowePolozenie));
        }
    }


    protected abstract void rysowanie();

}

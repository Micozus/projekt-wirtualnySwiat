package Gra.Swiat.Organizm.Rosliny;

import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.*;

import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {

    final int inicjatywa = 0;

    @Override
    public int getInicjatywa() {
        return inicjatywa;
    }


    public Roslina(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
        if(organizmAtakujacy.getSila() < organizmBroniacy.getSila()) {
            getJakiSwiat().getMapaobiektow().remove(organizmAtakujacy.getPolozenie());
        } else {
            organizmAtakujacy.setPolozenie(pole);
            getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
        }
    }

    public void decayPregnancy() {
        if (this.getReproductionCooldown() > 0) {
            this.setReproductionCooldown(this.getReproductionCooldown() - 1);
        } else if (this.getReproductionCooldown() == 1) {
            this.setReproductionCooldown(0);
            this.setCzyCiaza(false);
        }
    }

    public void setPregnancy(Organizm organizm) {
        organizm.setReproductionCooldown(9);
        organizm.setCzyCiaza(true);
    }

    @Override
    public void akcja() {
        if (this.getReproductionCooldown() == 0) {
            int szansa = new Random().nextInt(10000 + 1);
            if (szansa > 9900) {
                List<Lokalizacja> możliweSianie = obszaryWokol(this.getPolozenie(), getJakiSwiat().getMapaobiektow());
                Lokalizacja nowePolozenie = możliweSianie.get(new Random().nextInt(możliweSianie.size()));
                getJakiSwiat().getMapaobiektow().put(nowePolozenie, getJakiSwiat().instanceCreator(this.getTypeName(), nowePolozenie));
                setPregnancy(this);
            }
        } else {
            this.decayPregnancy();
        }
    }


    protected abstract void rysowanie();

}

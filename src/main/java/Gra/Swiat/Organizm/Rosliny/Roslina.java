package Gra.Swiat.Organizm.Rosliny;

import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.*;
import Gra.Zdarzenie;

import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {

    protected int inicjatywa = 0;

    private final int MAXAGE = 24;

    public int getMAXAGE() {
        return MAXAGE;
    }

    public Roslina(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
        if(organizmAtakujacy.getSila() < organizmBroniacy.getSila()) {
            getJakiSwiat().getMapaobiektow().remove(organizmAtakujacy.getPolozenie());
            getJakiSwiat().getMapaobiektow().remove(organizmBroniacy.getPolozenie());
            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.ZJEDZENIE, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
        } else {
            organizmAtakujacy.setPolozenie(pole);
            getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.ZJEDZENIE, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
        }
    }

    private void decayPregnancy() {
        if (this.getReproductionCooldown() > 0) {
            this.setReproductionCooldown(this.getReproductionCooldown() - 1);
        } else if (this.getReproductionCooldown() == 1) {
            this.setReproductionCooldown(0);
            this.setCzyCiaza(false);
        }
    }

    private void setPregnancy(Organizm organizm) {
        organizm.setReproductionCooldown(9);
        organizm.setCzyCiaza(true);
    }

    @Override
    public void akcja() {
        if(this.getWiek() >= this.getMAXAGE()) {
            deathIsComing();
        }
        if (this.getReproductionCooldown() == 0) {
            int szansa = new Random().nextInt(10000 + 1);
            if (szansa > 9900) {
                List<Lokalizacja> mozliweSianie = obszaryWokol(this.getPolozenie(), getJakiSwiat().getMapaobiektow());
                Lokalizacja nowePolozenie = mozliweSianie.get(new Random().nextInt(mozliweSianie.size()));
                getJakiSwiat().getMapaobiektow().put(nowePolozenie, getJakiSwiat().instanceCreator(this.getTypeName(), nowePolozenie));
                setPregnancy(this);
                getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.REPRODUKCJA, this.getPolozenie(), this));
            }
        } else {
            this.decayPregnancy();
        }
    }


    protected abstract void rysowanie();

}

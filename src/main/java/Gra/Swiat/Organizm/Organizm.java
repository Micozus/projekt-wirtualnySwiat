package Gra.Swiat.Organizm;

import Gra.Logi;
import Gra.Swiat.*;
import Gra.Zdarzenie;

import java.util.Random;

public abstract class Organizm implements IZyje {

    private int deathProbability = 50;

    public int getDeathProbability() {
        return deathProbability;
    }

    public void setDeathProbability(int deathProbability) {
        this.deathProbability = deathProbability;
    }

    private int reproductionCooldown = 0;

    public boolean isCzyCiaza() {
        return czyCiaza;
    }

    public void setCzyCiaza(boolean czyCiaza) {
        this.czyCiaza = czyCiaza;
    }

    private boolean czyCiaza = false;

    public int getMAXAGE() {
        return MAXAGE;
    }

    private int MAXAGE;

    private Lokalizacja polozenie;
    private int wiek = 0;
    private int sila;
    private int inicjtywa;
    private Swiat jakiSwiat;

    private String typeName;

    public int getInicjtywa() {
        return inicjtywa;
    }

    public int getReproductionCooldown() {
        return reproductionCooldown;
    }

    public void setReproductionCooldown(int reproductionCooldown) {
        this.reproductionCooldown = reproductionCooldown;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setSila(int sila) {
        this.sila = sila;
    }

    protected abstract void rysowanie();
    public abstract void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy);
    public abstract void akcja();

    public void setPolozenie(Lokalizacja polozenie) {
        this.polozenie = polozenie;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public Lokalizacja getPolozenie() {
        return polozenie;
    }

    public int getSila() {
        return sila;
    }

    public void makeOlder(Organizm o) {
        o.setWiek(o.getWiek() + 1);
    }


    public Swiat getJakiSwiat() {
        return jakiSwiat;
    }

    public Organizm(Lokalizacja polozenie, Swiat jakiSwiat) {
        this.polozenie = polozenie;
        this.jakiSwiat = jakiSwiat;
    }

    protected void deathIsComing() {
        int deathProbability = this.getDeathProbability();
        int szansa = new Random().nextInt(100 + 1);
        if (szansa > deathProbability) {
            getJakiSwiat().getMapaobiektow().remove(this.getPolozenie());
            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.SMIERC, this.getPolozenie(), this));
        } else {
            this.setDeathProbability(this.getDeathProbability() + 25);
        }
    }


}

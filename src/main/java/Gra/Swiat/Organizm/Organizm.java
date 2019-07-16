package Gra.Swiat.Organizm;

import Gra.Swiat.*;

public abstract class Organizm implements IZyje {

    Lokalizacja polozenie;
    int wiek = 0;
    int sila;
    int inicjatywa;
    Swiat jakiSwiat;

    String typeName;

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

    protected int getInicjatywa() {
        return inicjatywa;
    }

    public Swiat getJakiSwiat() {
        return jakiSwiat;
    }

    public Organizm(Lokalizacja polozenie, Swiat jakiSwiat) {
        this.polozenie = polozenie;
        this.jakiSwiat = jakiSwiat;
    }

}

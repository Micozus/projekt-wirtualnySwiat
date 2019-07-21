package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Logi;
import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;
import Gra.Zdarzenie;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Antylopa extends Zwierze {

    private int wiek = 0;
    private final int MAXAGE = 85;
    private int inicjatywa = 4;
    private int sila = 4;
    private Lokalizacja polozenie;
    private int deathProbability = 50;
    private int reproductionCooldown = 0;
    private boolean czyCiaza = false;
    private Swiat swiat;
    private String typeName = "Antylopa";

    public Antylopa(Lokalizacja polozenie, Swiat jakiSwiat) {
        this.swiat = jakiSwiat;
        this.polozenie = polozenie;
    }

    @Override
    public int getWiek() {
        return wiek;
    }
    @Override
    public int getDeathProbability() {
        return this.deathProbability;
    }
    @Override
    public Swiat getJakiSwiat() {
        return this.swiat;
    }
    @Override
    public Lokalizacja getPolozenie() {
        return this.polozenie;
    }
    @Override
    public boolean isCzyCiaza() {
        return this.czyCiaza;
    }
    @Override
    public void setCzyCiaza(boolean czyCiaza) {
        this.czyCiaza = czyCiaza;
    }
    @Override
    public void setDeathProbability(int deathProbability) {
        this.deathProbability = deathProbability;
    }
    @Override
    public void makeOlder() {
        this.wiek++;
    }
    @Override
    public void setPolozenie(Lokalizacja polozenie) {
        this.polozenie = polozenie;
    }
    @Override
    public int getSila() {
        return sila;
    }
    @Override
    public void setSila(int sila) {
        this.sila = sila;
    }
    @Override
    public int getMAXAGE() {
        return MAXAGE;
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
    protected void decayPregnancy(Organizm organizm) {
        if (organizm.getReproductionCooldown() > 0) {
            organizm.setReproductionCooldown(organizm.getReproductionCooldown() - 1);
        } else if (organizm.getReproductionCooldown() == 1) {
            organizm.setReproductionCooldown(0);
            organizm.setCzyCiaza(false);
        }
    }
    @Override
    protected void setPregnancy(Organizm organizm) {
        organizm.setReproductionCooldown(9);
        organizm.setCzyCiaza(true);
    }
    @Override
    public int getReproductionCooldown() {
        return this.reproductionCooldown;
    }
    @Override
    public void setReproductionCooldown(int reproductionCooldown) {
        this.reproductionCooldown = reproductionCooldown;
    }


    @Override
    public void akcja() {
    // Zasieg ruchu wynosi 2 pola
        for (int i = 0; i < 2; i++) {
            super.akcja();
        }
    }

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
    // 50% szans na ucieczke przed walka, przesuwa sie wtedy na sasiednie, niezajete pole
        int szansa = new Random().nextInt(100 + 1);
        if (szansa > 50 && (obszaryWokol(this.getPolozenie(), this.getJakiSwiat().getMapaobiektow()) != null)) {
            List<Lokalizacja> obszaryWokol = obszaryWokol(pole, this.getJakiSwiat().getMapaobiektow());
            if (obszaryWokol.size() != 0) {
                Lokalizacja miejsceUcieczki = obszaryWokol.get(new Random().nextInt(obszaryWokol.size()));
                organizmBroniacy.setPolozenie(miejsceUcieczki);
                getJakiSwiat().getMapaobiektow().put(miejsceUcieczki, organizmBroniacy);
                organizmAtakujacy.setPolozenie(pole);
                getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
                getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
                getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.UCIECZKA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
            } else {
                getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
                getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
                getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.POTYCZKA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
            }
        } else {
            super.kolizja(pole, organizmAtakujacy, organizmBroniacy);
        }
    }


    protected void rysowanie() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Antylopa antylopa = (Antylopa) o;
        return wiek == antylopa.wiek &&
                sila == antylopa.sila &&
                Objects.equals(polozenie, antylopa.polozenie) &&
                Objects.equals(typeName, antylopa.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wiek, polozenie, typeName, sila);
    }
}

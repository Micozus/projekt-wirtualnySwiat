package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Lis extends Zwierze {

    private int wiek = 0;
    private final int MAXAGE = 58;
    private int inicjatywa = 7;
    private int sila = 3;
    private Lokalizacja polozenie;
    private int deathProbability = 50;
    private int reproductionCooldown = 0;
    private boolean czyCiaza = false;
    private Swiat swiat;
    private String typeName = "Lis";

    public Lis(Lokalizacja polozenie, Swiat jakiSwiat) {
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
        Lokalizacja poprzedniePole = this.getPolozenie();
    // Dobry wech: lis nigdy nie ruszy sie na pole zajmowane przez organizm silniejszy niz on
        List<Lokalizacja> mozliweSciezki = mozliweSciezki(poprzedniePole);
        Lokalizacja nowePolozenie = mozliweSciezki.get(new Random().nextInt(mozliweSciezki.size()));
        if((getJakiSwiat().getMapaobiektow().containsKey(nowePolozenie)) && (getJakiSwiat().getMapaobiektow().get(nowePolozenie).getSila() < this.getSila())) {
            jakaKolizja(nowePolozenie, this, getJakiSwiat().getMapaobiektow().get(nowePolozenie));
        } else if((getJakiSwiat().getMapaobiektow().containsKey(nowePolozenie)) && (getJakiSwiat().getMapaobiektow().get(nowePolozenie).getSila() > this.getSila())) {
            boolean istniejeDostepneWolnePole = false;
            for (Lokalizacja ints : mozliweSciezki) {
                if(!getJakiSwiat().getMapaobiektow().containsKey(ints)) {
                    istniejeDostepneWolnePole = true;
                    nowePolozenie = ints;
                    break;
                }
            }
            if (istniejeDostepneWolnePole) {
                this.setPolozenie(nowePolozenie);
                getJakiSwiat().getMapaobiektow().put(nowePolozenie, this);
                getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
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
        return sila == lis.sila &&
                wiek == lis.wiek &&
                Objects.equals(typeName, lis.typeName) &&
                Objects.equals(polozenie, lis.polozenie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, sila, polozenie, wiek);
    }


}

package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.GUI.InstanceImage;
import Gra.Logi;
import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;
import Gra.Zdarzenie;

import java.util.Objects;
import java.util.Random;

public class Owca extends Zwierze {

    private int wiek = 0;
    private final int MAXAGE = 60;
    private int inicjatywa = 4;
    private int sila = 4;
    private Lokalizacja polozenie;
    private int deathProbability = 50;
    private int reproductionCooldown = 0;
    private boolean czyCiaza = false;
    private Swiat swiat;
    private String typeName = "Owca";
    private InstanceImage instanceImage;

    public Owca(Lokalizacja polozenie, Swiat jakiSwiat) {
        this.swiat = jakiSwiat;
        this.polozenie = polozenie;
    }

    public Owca(Lokalizacja polozenie, Swiat jakiSwiat, int wiek, int sila, int reproductionCooldown, boolean czyCiaza) {
        this.swiat = jakiSwiat;
        this.polozenie = polozenie;
        this.wiek = wiek;
        this.sila = sila;
        this.reproductionCooldown = reproductionCooldown;
        this.czyCiaza = czyCiaza;
    }

    @Override
    public InstanceImage getInstanceImage() {
        return instanceImage;
    }
    @Override
    public void setInstanceImage(InstanceImage instanceImage) {
        this.instanceImage = instanceImage;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owca owca = (Owca) o;
        return sila == owca.sila &&
                wiek == owca.wiek &&
                Objects.equals(typeName, owca.typeName) &&
                Objects.equals(polozenie, owca.polozenie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, sila, wiek, polozenie);
    }


}

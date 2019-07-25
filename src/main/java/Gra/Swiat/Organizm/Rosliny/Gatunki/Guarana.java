package Gra.Swiat.Organizm.Rosliny.Gatunki;

import Gra.GUI.InstanceImage;
import Gra.GUI.TypAnimacji;
import Gra.Logi;
import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Rosliny.Roslina;
import Gra.Swiat.Swiat;
import Gra.Zdarzenie;

import java.util.Objects;

public class Guarana extends Roslina {

    private int wiek = 0;
    private final int MAXAGE = 24;
    private int inicjatywa = 0;
    private int sila = 0;
    private Lokalizacja polozenie;
    private int deathProbability = 50;
    private int reproductionCooldown = 0;
    private boolean czyCiaza = false;
    private Swiat swiat;
    private String typeName = "Guarana";
    private InstanceImage instanceImage;

    public Guarana(Lokalizacja polozenie, Swiat jakiSwiat) {
        this.swiat = jakiSwiat;
        this.polozenie = polozenie;
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
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
    // Zwieksza sile zwierzecia o 3
        getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.FADEOUT, organizmBroniacy);
        organizmAtakujacy.setSila(organizmAtakujacy.getSila() + 3);
        organizmAtakujacy.setPolozenie(pole);
        getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.MOVE, organizmAtakujacy, pole);
        getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
        getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.ZJEDZENIE, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guarana guarana = (Guarana) o;
        return wiek == guarana.wiek &&
                sila == guarana.sila &&
                Objects.equals(typeName, guarana.typeName) &&
                Objects.equals(polozenie, guarana.polozenie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, polozenie, wiek, sila);
    }
}

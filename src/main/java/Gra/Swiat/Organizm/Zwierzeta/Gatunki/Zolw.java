package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.GUI.InstanceImage;
import Gra.GUI.TypAnimacji;
import Gra.Logi;
import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.Swiat;
import Gra.Zdarzenie;

import java.util.Objects;
import java.util.Random;

public class Zolw extends Zwierze {


    private int wiek = 0;
    private final int MAXAGE = 100;
    private int inicjatywa = 1;
    private int sila = 2;
    private Lokalizacja polozenie;
    private int deathProbability = 50;
    private int reproductionCooldown = 0;
    private boolean czyCiaza = false;
    private Swiat swiat;
    private String typeName = "Zolw";
    private InstanceImage instanceImage;


    public Zolw(Lokalizacja polozenie, Swiat jakiSwiat) {
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
    public void akcja() {
        // W 75% przypadków nie zmienia swojego położenia
        int szansa = new Random().nextInt(100 + 1);
        if (szansa > 75) {
            super.akcja();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zolw zolw = (Zolw) o;
        return sila == zolw.sila &&
                wiek == zolw.wiek &&
                Objects.equals(polozenie, zolw.polozenie) &&
                Objects.equals(typeName, zolw.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sila, polozenie, wiek, typeName);
    }

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        // Odpiera ataki zwierzat o sile
        // <5 Napastnik musi wrocic na swoje poprzednie miejsce
        if (organizmAtakujacy.getClass().equals(organizmBroniacy.getClass())) {
            super.jakaKolizja(pole, organizmAtakujacy, organizmBroniacy);
        } else {
            Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
            if (((organizmAtakujacy.getClass().getSuperclass().equals(Zwierze.class))) && organizmBroniacy.equals(this)) {
                if ((organizmAtakujacy.getSila() > organizmBroniacy.getSila()) && (organizmAtakujacy.getSila() >= 5)) {
                    organizmAtakujacy.setPolozenie(pole);
                    getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.MOVE, organizmAtakujacy, pole);
                    getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.FADEOUT, organizmBroniacy);
                    getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
                    getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
                    getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.POTYCZKA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
                } else {
                    getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.OBRONA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
                }
            }
        }
    }

}

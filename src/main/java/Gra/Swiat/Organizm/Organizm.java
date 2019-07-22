package Gra.Swiat.Organizm;

import Gra.Swiat.*;

public abstract class Organizm implements IZyje {

    public void checkAction() {
        this.doTura();
    }

    private void doTura() {
        boolean isDead = deathIsComing(this);
        if (this.getWiek() > this.getMAXAGE() && this.getTypeName().equals("Antylopa")) {
            System.out.println("Coś się dzieje");
        }
        if (isDead == false) {
            this.akcja();
            this.makeOlder();
        }
    }

    public abstract String getTypeName();

    public abstract int getSila();

    public abstract int getMAXAGE();

    public abstract void setSila(int sila);

    public abstract int getInicjatywa();

    public abstract int getWiek();

    public abstract int getDeathProbability();

    public abstract Swiat getJakiSwiat();

    public abstract Lokalizacja getPolozenie();

    public abstract void setDeathProbability(int deathProbability);

    public abstract boolean isCzyCiaza();

    public abstract void setCzyCiaza(boolean czyCiaza);

    protected abstract void decayPregnancy(Organizm organizm);

    protected abstract void setPregnancy(Organizm organizm);

    public abstract int getReproductionCooldown();

    public abstract void setReproductionCooldown(int reproductionCooldown);

    public abstract void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy);

    protected abstract void akcja();

    public abstract void makeOlder();

    public abstract void setPolozenie(Lokalizacja polozenie);

}

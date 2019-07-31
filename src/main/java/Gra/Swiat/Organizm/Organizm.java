package Gra.Swiat.Organizm;

import Gra.GUI.InstanceImage;
import Gra.GUI.SpecialAbbility;
import Gra.GUI.TypAnimacji;
import Gra.Swiat.*;
import Gra.Swiat.Organizm.Zwierzeta.Gatunki.Czlowiek;

public abstract class Organizm implements IZyje {

    public void checkAction() {
        this.doTura();
    }

    protected void checkLastTurn(Organizm organizm) {
        if(organizm.getClass().equals(Czlowiek.class)) {
            organizm.getJakiSwiat().getGra().getAppGui().checkIfEndGame();
        }
    }

    private void doTura() {
//        boolean isDead = deathIsComing(this);
//        if (this.getWiek() > this.getMAXAGE() && this.getTypeName().equals("Antylopa")) {
//            System.out.println("Coś się dzieje");
//        }
//        if (isDead == false) {
            this.akcja();
//            this.getJakiSwiat().getGra().getAppGui().getActionListenButton().doClick();
            this.makeOlder();
//        }
    }

    public abstract InstanceImage getInstanceImage();

    public abstract  void setInstanceImage(InstanceImage instanceImage);

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

    public void activateSpecialAbbility(SpecialAbbility specialAbbility) {
        return;
    }

    public void move(TypAnimacji typAnimacji) {
        return;
    }

    public int getMoveLimit() {
        return 0;
    }
}

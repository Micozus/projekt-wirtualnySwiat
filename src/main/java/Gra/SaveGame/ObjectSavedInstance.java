package Gra.SaveGame;

import Gra.Gra;

public class ObjectSavedInstance {
    private String typeName;
    private int wiek;
    private int sila;
    private int reproductionCooldown;
    private boolean czyCiaza;
    private int xPosition;
    private int yPosition;

    public ObjectSavedInstance(int xPosition, int yPosition, String typeName, boolean czyCiaza, int sila, int wiek, int reproductionCooldown) {
        this.typeName = typeName;
        this.wiek = wiek;
        this.sila = sila;
        this.reproductionCooldown = reproductionCooldown;
        this.czyCiaza = czyCiaza;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getWiek() {
        return wiek;
    }

    public int getSila() {
        return sila;
    }

    public int getReproductionCooldown() {
        return reproductionCooldown;
    }

    public boolean isCzyCiaza() {
        return czyCiaza;
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }
}

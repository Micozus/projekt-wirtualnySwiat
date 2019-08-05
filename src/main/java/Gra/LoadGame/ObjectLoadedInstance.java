package Gra.LoadGame;

import java.util.Objects;

public class ObjectLoadedInstance {
    private String typeName;
    private int wiek;
    private int sila;
    private int reproductionCooldown;
    private boolean czyCiaza;
    private int xPosition;
    private int yPosition;

    public ObjectLoadedInstance(int xPosition, int yPosition, String typeName, boolean czyCiaza, int sila, int wiek, int reproductionCooldown) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectLoadedInstance that = (ObjectLoadedInstance) o;
        return wiek == that.wiek &&
                sila == that.sila &&
                reproductionCooldown == that.reproductionCooldown &&
                czyCiaza == that.czyCiaza &&
                xPosition == that.xPosition &&
                yPosition == that.yPosition &&
                Objects.equals(typeName, that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, wiek, sila, reproductionCooldown, czyCiaza, xPosition, yPosition);
    }
}

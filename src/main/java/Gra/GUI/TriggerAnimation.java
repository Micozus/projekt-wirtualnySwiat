package Gra.GUI;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;

import java.awt.*;
import java.util.Objects;

public class TriggerAnimation {

    private AppGui appGui;
    private TypAnimacji typAnimacji;
    private Organizm organizm;
    private Component iconToAnimate;
    private Lokalizacja miejsceUcieczki;

    public TriggerAnimation(AppGui appGui, TypAnimacji typAnimacji, Organizm organizm) {
        this.typAnimacji = typAnimacji;
        this.organizm = organizm;
        this.appGui = appGui;
        this.iconToAnimate = findRightComponent(this.organizm);
    }

    public Lokalizacja getMiejsceUcieczki() {
        return miejsceUcieczki;
    }

    public TriggerAnimation(AppGui appGui, TypAnimacji typAnimacji, Organizm organizm, Lokalizacja miejsceUcieczki) {
        this.typAnimacji = typAnimacji;
        this.organizm = organizm;
        this.appGui = appGui;
        this.miejsceUcieczki = miejsceUcieczki;
        this.iconToAnimate = findRightComponent(this.organizm);
    }

    public TypAnimacji getTypAnimacji() {
        return typAnimacji;
    }

    public Organizm getOrganizm() {
        return organizm;
    }

    public Component getIconToAnimate() {
        return iconToAnimate;
    }

    private Component findRightComponent(Organizm organizm) {
        for (int i = 0; i < appGui.getGameMapGrid().getComponents().length; i++) {
            if (appGui.getGameMapGrid().getComponents()[i].equals(organizm.getInstanceImage())) {
                return appGui.getGameMapGrid().getComponents()[i];
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriggerAnimation that = (TriggerAnimation) o;
        return Objects.equals(appGui, that.appGui) &&
                typAnimacji == that.typAnimacji &&
                Objects.equals(organizm, that.organizm) &&
                Objects.equals(iconToAnimate, that.iconToAnimate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appGui, typAnimacji, organizm, iconToAnimate);
    }
}

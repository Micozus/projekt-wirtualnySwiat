package Gra.GUI;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class InstanceImage extends JLabel {

    private Organizm organizm;
    private int boundsX;
    private int boundsY;
    final private int width = 16;
    final private int height = 16;
    private ImageIcon icon;

    private static ImageIcon whichInstance(Organizm organizm) {
        String instanceType = organizm.getTypeName();
        ImageIcon icon = null;
        switch (instanceType) {
            case "Trawa":
                icon = new ImageIcon("pic/grass.png");
                break;
            case "Guarana":
                icon = new ImageIcon("pic/berries.png");
                break;
            case "Mlecz":
                icon = new ImageIcon("pic/dandelion.png");
                break;
            case "WilczeJagody":
                icon = new ImageIcon("pic/berry.png");
                break;
            case "Antylopa":
                icon = new ImageIcon("pic/antelope.png");
                break;
            case "Czlowiek":
                icon = new ImageIcon("pic/man.png");
                break;
            case "Lis":
                icon = new ImageIcon("pic/fox.png");
                break;
            case "Owca":
                icon = new ImageIcon("pic/lamb.png");
                break;
            case "Wilk":
                icon = new ImageIcon("pic/wolf.png");
                break;
            case "Zolw":
                icon = new ImageIcon("pic/turtle.png");
                break;
        }
        return icon;
    }


    public InstanceImage(Organizm organizm) {
        super("", whichInstance(organizm), JLabel.CENTER);
        this.organizm = organizm;
        this.boundsX = returnBounds(organizm.getPolozenie())[0];
        this.boundsY = returnBounds(organizm.getPolozenie())[1];
    }

    private void setBoundsX(int boundsX) {
        this.boundsX = boundsX;
    }

    private void setBoundsY(int boundsY) {
        this.boundsY = boundsY;
    }

    public Organizm getOrganizm() {
        return organizm;
    }

    public int getBoundsX() {
        return boundsX;
    }

    public int getBoundsY() {
        return boundsY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setBounds(Lokalizacja lokalizacja) {
        this.setBoundsX(returnBounds(lokalizacja)[0]);
        this.setBoundsY(returnBounds(lokalizacja)[1]);
    }

    private int[] returnBounds(Lokalizacja lokalizacja) {
        return new int[]{-24 +(lokalizacja.getxValue() * 32), -24 +(lokalizacja.getYvalue() * 32)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceImage that = (InstanceImage) o;
        return boundsX == that.boundsX &&
                boundsY == that.boundsY &&
                Objects.equals(organizm, that.organizm) &&
                Objects.equals(icon, that.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizm, boundsX, boundsY, icon);
    }
}

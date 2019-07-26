package Gra;

import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Rosliny.Gatunki.Guarana;
import Gra.Swiat.Organizm.Rosliny.Gatunki.WilczeJagody;
import Gra.Swiat.Organizm.Rosliny.Roslina;

import java.util.Objects;

public class Logi {
    private int tura;
    private Zdarzenie zdarzenie;
    private Lokalizacja lokalizacja;
    private Organizm organizmAtakujacy;
    private Organizm organizmBroniacy;

    public Logi(int tura, Zdarzenie zdarzenie, Lokalizacja lokalizacja, Organizm organizmAtakujacy) {
        this.tura = tura;
        this.zdarzenie = zdarzenie;
        this.lokalizacja = lokalizacja;
        this.organizmAtakujacy = organizmAtakujacy;
    }

    public Logi(int tura, Zdarzenie zdarzenie, Lokalizacja lokalizacja, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        this.tura = tura;
        this.zdarzenie = zdarzenie;
        this.lokalizacja = lokalizacja;
        this.organizmAtakujacy = organizmAtakujacy;
        this.organizmBroniacy = organizmBroniacy;
    }

    public int getTura() {
        return tura;
    }

    @Override
    public String toString() {
        String event = String.valueOf(zdarzenie);
        if (this.zdarzenie.equals(Zdarzenie.SMIERC)) {
            return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " " + organizmAtakujacy.getTypeName() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] " + " ze starosci w wieku " + (organizmAtakujacy.getWiek() / 12) + " lat gry";
        } else if (this.zdarzenie.equals(Zdarzenie.REPRODUKCJA)) {
            return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                    + organizmAtakujacy.getTypeName();
        } else if (this.zdarzenie.equals(Zdarzenie.SPALENIE)) {
            return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " przez Gracza, [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                    + organizmBroniacy.getTypeName();
        } else {
            if (this.organizmBroniacy != null && this.organizmBroniacy.getClass().getSuperclass().equals(Roslina.class)) {
                if (this.organizmBroniacy.getClass().equals(WilczeJagody.class)) {
                    return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                            + organizmBroniacy.getTypeName() + " przez " + organizmAtakujacy.getTypeName() + ", " + organizmAtakujacy.getTypeName() + " umiera";
                } else if (this.organizmBroniacy.getClass().equals(Guarana.class)) {
                    return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                            + organizmBroniacy.getTypeName() + " przez " + organizmAtakujacy.getTypeName() + ". Siła " + organizmAtakujacy.getTypeName() + " zwiększa się o 3";
                } else {
                    return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                            + organizmBroniacy.getTypeName() + " przez " + organizmAtakujacy.getTypeName();
                }
            } else {
                if (this.zdarzenie.equals(Zdarzenie.UCIECZKA)) {
                    return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                            + organizmBroniacy.getTypeName() + " przed " + organizmAtakujacy.getTypeName();
                } else if (this.zdarzenie.equals(Zdarzenie.OBRONA)) {
                    return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                            + organizmBroniacy.getTypeName() + " przed " + organizmAtakujacy.getTypeName();
                } else if (this.zdarzenie.equals(Zdarzenie.ODSTRASZENIE)) {
                    return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                            + organizmBroniacy.getTypeName() + " odstraszył " + organizmAtakujacy.getTypeName();
                } else {
                    return event.toUpperCase().charAt(0) + event.substring(1).toLowerCase() + " [" + lokalizacja.getxValue() + "," + lokalizacja.getYvalue() + "] "
                            + organizmAtakujacy.getTypeName() + " z " + organizmBroniacy.getTypeName() + ", wygrywa: " + organizmAtakujacy.getTypeName();
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logi logi = (Logi) o;
        return tura == logi.tura &&
                zdarzenie == logi.zdarzenie &&
                Objects.equals(organizmAtakujacy, logi.organizmAtakujacy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tura, zdarzenie, organizmAtakujacy);
    }
}

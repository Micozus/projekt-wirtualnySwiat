package Gra;

import Gra.GUI.AppGui;
import Gra.Swiat.Swiat;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class Gra {

    private int tura = 1;
    private int iloscOrganizmowZwierzecych = 0;
    private int iloscOrganizmowRoslinnych = 0;
    private Set<Logi> logSet = new LinkedHashSet<>();

    public Set<Logi> getLogSet() {
        return logSet;
    }

    public int getTura() {
        return tura;
    }

    public void setTura(int tura) {
        this.tura = tura;
    }

    public int getIloscOrganizmowZwierzecych() {
        return iloscOrganizmowZwierzecych;
    }

    public void setIloscOrganizmowZwierzecych(int iloscOrganizmowZwierzecych) {
        this.iloscOrganizmowZwierzecych = iloscOrganizmowZwierzecych;
    }

    public int getIloscOrganizmowRoslinnych() {
        return iloscOrganizmowRoslinnych;
    }

    public void setIloscOrganizmowRoslinnych(int iloscOrganizmowRoslinnych) {
        this.iloscOrganizmowRoslinnych = iloscOrganizmowRoslinnych;
    }

    public Gra() {
        EventQueue.invokeLater(() -> new AppGui(this));
    }


}

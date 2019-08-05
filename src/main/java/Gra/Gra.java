package Gra;

import Gra.GUI.AppGui;
import Gra.GUI.InstanceImage;
import Gra.LoadGame.LoadGameState;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Swiat;

import java.awt.*;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Gra {

    private int tura = 1;
    private int iloscOrganizmowZwierzecych = 0;
    private int iloscOrganizmowRoslinnych = 0;
    private Set<Logi> logSet = new LinkedHashSet<>();
    private Map<Organizm, InstanceImage> mapaObrazow = new HashMap<>();
    private AppGui appGui;
    private boolean isLoad;

    public Gra() {
        this.isLoad = false;
        EventQueue.invokeLater(() -> this.appGui = new AppGui(new Swiat(this), this));
    }

    public Gra(LoadGameState loadGameState) {
        this.isLoad = true;
        EventQueue.invokeLater(() -> this.appGui = new AppGui(new Swiat(this, loadGameState), this));
    }

    public boolean isLoad() {
        return isLoad;
    }

    public Map<Organizm, InstanceImage> getMapaObrazow() {
        return mapaObrazow;
    }

    public AppGui getAppGui() {
        return this.appGui;
    }

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


}

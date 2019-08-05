package Gra.LoadGame;

import Gra.SaveGame.ObjectSavedInstance;

import java.util.List;

public class LoadGameState {
    private int turaCount;
    private int iloscOrganizmowZwierzecych;
    private int iloscOrganizmowRoslinnych;
    private List<ObjectLoadedInstance> listaWczytanychObiektow;

    public LoadGameState(int turaCount, int iloscOrganizmowZwierzecych, int iloscOrganizmowRoslinnych, List<ObjectLoadedInstance> listaWczytanychObiektow) {
        this.turaCount = turaCount;
        this.iloscOrganizmowZwierzecych = iloscOrganizmowZwierzecych;
        this.iloscOrganizmowRoslinnych = iloscOrganizmowRoslinnych;
        this.listaWczytanychObiektow = listaWczytanychObiektow;
    }

    public int getTuraCount() {
        return turaCount;
    }

    public int getIloscOrganizmowZwierzecych() {
        return iloscOrganizmowZwierzecych;
    }

    public int getIloscOrganizmowRoslinnych() {
        return iloscOrganizmowRoslinnych;
    }

    public List<ObjectLoadedInstance> getListaWczytanychObiektow() {
        return listaWczytanychObiektow;
    }
}

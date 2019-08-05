package Gra.SaveGame;

import Gra.Gra;
import Gra.Swiat.Lokalizacja;
import Gra.Swiat.Organizm.Organizm;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SaveGameState {
    private int turaCount;
    private int iloscOrganizmowZwierzecych;
    private int iloscOrganizmowRoslinnych;
    private List<ObjectSavedInstance> listaZapisanychObiektow = new ArrayList<>();
    private Gra gra;

    public SaveGameState(int turaCount, int iloscOrganizmowZwierzecych, int iloscOrganizmowRoslinnych, Gra gra) {
        this.turaCount = turaCount;
        this.iloscOrganizmowZwierzecych = iloscOrganizmowZwierzecych;
        this.iloscOrganizmowRoslinnych = iloscOrganizmowRoslinnych;
        this.gra = gra;
        populateListObjectSavedInstance();
        JSONsaveToFile(createJSONSave(this));
    }

    private void populateListObjectSavedInstance() {
        Map<Lokalizacja, Organizm> mapaOrganizmow = this.gra.getAppGui().getSwiat().getMapaobiektow();
        mapaOrganizmow
                .entrySet()
                .stream()
                .forEach(entrySet ->
                        this.listaZapisanychObiektow.add
                                (new ObjectSavedInstance(
                                        entrySet.getKey().getxValue(),
                                        entrySet.getKey().getYvalue(),
                                        entrySet.getValue().getTypeName(),
                                        entrySet.getValue().isCzyCiaza(),
                                        entrySet.getValue().getSila(),
                                        entrySet.getValue().getWiek(),
                                        entrySet.getValue().getReproductionCooldown()
                                        )
                                ));

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

    public List<ObjectSavedInstance> getListaZapisanychObiektow() {
        return listaZapisanychObiektow;
    }

    private JSONArray createJSONSave(SaveGameState saveGameState) {
        JSONArray JSONArrayToSave = new JSONCreator(saveGameState);
        return JSONArrayToSave;
    }

    private void JSONsaveToFile(JSONArray jsonArray) {
        new FileSaver(jsonArray, this.gra);
    }

}

package Gra.Swiat.Organizm.Rosliny;

import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.*;
import Gra.Zdarzenie;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Roslina extends Organizm {

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
        if(organizmAtakujacy.getSila() < organizmBroniacy.getSila()) {
            getJakiSwiat().getMapaobiektow().remove(organizmAtakujacy.getPolozenie());
            getJakiSwiat().getMapaobiektow().remove(organizmBroniacy.getPolozenie());
            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.ZJEDZENIE, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
        } else {
            organizmAtakujacy.setPolozenie(pole);
            getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.ZJEDZENIE, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
        }
    }

    public void sianie(Organizm organizm, List<Lokalizacja> dostepne) {
        Lokalizacja nowePolozenie = dostepne.get(new Random().nextInt(dostepne.size()));
        getJakiSwiat().getMapaobiektow().put(nowePolozenie, getJakiSwiat().instanceCreator(organizm.getTypeName(), nowePolozenie));
        setPregnancy(organizm);
        getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.REPRODUKCJA, organizm.getPolozenie(), organizm));
    }

    protected abstract void decayPregnancy(Organizm organizm);

    @Override
    public void akcja() {
        if (this.getReproductionCooldown() <= 0) {
            int szansa = new Random().nextInt(100) + 1;
            if (szansa > 98) {
                List<Lokalizacja> mozliweSianie = obszaryWokol(this.getPolozenie(), getJakiSwiat().getMapaobiektow());
                List<Lokalizacja> dostepne = new ArrayList<>();
                for (Lokalizacja lokalizacja : mozliweSianie) {
                    if (lokalizacja.getxValue() > 0 && lokalizacja.getYvalue() > 0) {
                        dostepne.add(lokalizacja);
                    }
                }
                if (dostepne.size() != 0) {
                    this.sianie(this, dostepne);
                }
            }
        } else {
            decayPregnancy(this);
        }
    }


}

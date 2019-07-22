package Gra.Swiat.Organizm.Zwierzeta;

import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.*;
import Gra.Zdarzenie;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Zwierze extends Organizm {

    protected void akcja() {

        Lokalizacja poprzedniePole = this.getPolozenie();
        List<Lokalizacja> mozliweSciezki = mozliweSciezki(poprzedniePole);
        Lokalizacja nowePolozenie = mozliweSciezki.get(new Random().nextInt(mozliweSciezki.size()));
        if (getJakiSwiat().czyKolizja(nowePolozenie, getJakiSwiat().getMapaobiektow())) {
            jakaKolizja(nowePolozenie, this, getJakiSwiat().getMapaobiektow().get(nowePolozenie));
        } else {
            this.setPolozenie(nowePolozenie);
            getJakiSwiat().getMapaobiektow().put(nowePolozenie, this);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
        }
    }

    protected void reproducja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        int szansa = new Random().nextInt(100 + 1);
        if (szansa > 50) {
            List<Lokalizacja> obszaryWokol = obszaryWokol(pole, getJakiSwiat().getMapaobiektow());
            List<Lokalizacja> dostepne = new ArrayList<>();
            for (Lokalizacja lokalizacja : obszaryWokol) {
                if (lokalizacja.getxValue() > 0 && lokalizacja.getYvalue() > 0) {
                    dostepne.add(lokalizacja);
                }
            }
            if (dostepne.size() != 0) {
                setPregnancy(organizmAtakujacy);
                setPregnancy(organizmBroniacy);
                Lokalizacja nowyObiekt = dostepne.get(new Random().nextInt(dostepne.size()));
                getJakiSwiat().getMapaobiektow().put(nowyObiekt, getJakiSwiat().instanceCreator(this.getTypeName(), nowyObiekt));
                getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.REPRODUKCJA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
            }
        }
    }

    protected void jakaKolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        if ((organizmAtakujacy.getClass().equals(organizmBroniacy.getClass()) && (!organizmAtakujacy.equals(organizmBroniacy)) && ((organizmBroniacy.getReproductionCooldown() == 0) && (organizmAtakujacy.getReproductionCooldown() == 0)))) {
            reproducja(pole, organizmAtakujacy, organizmBroniacy);
        } else if (this.equals(getJakiSwiat().getMapaobiektow().get(pole)) && ((organizmBroniacy.getReproductionCooldown() != 0) || (organizmAtakujacy.getReproductionCooldown() != 0))) {
            if (organizmAtakujacy.isCzyCiaza()) {
                decayPregnancy(organizmAtakujacy);
            }
            if (organizmBroniacy.isCzyCiaza()) {
                decayPregnancy(organizmBroniacy);
            }
        } else if (organizmAtakujacy.equals(organizmBroniacy)) {
            return;
        } else {
            organizmBroniacy.kolizja(pole, organizmAtakujacy, organizmBroniacy);
        }
    }

    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
        if (organizmAtakujacy.getSila() < organizmBroniacy.getSila()) {
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.POTYCZKA, organizmBroniacy.getPolozenie(), organizmBroniacy, organizmAtakujacy));
        } else if(organizmAtakujacy.getSila() == organizmBroniacy.getSila()) {
            if (organizmAtakujacy.getWiek() < organizmBroniacy.getWiek()) {
                getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
                getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.POTYCZKA, organizmBroniacy.getPolozenie(), organizmBroniacy, organizmAtakujacy));
            } else {
                organizmAtakujacy.setPolozenie(pole);
                getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
                getJakiSwiat().getMapaobiektow().remove(pole);
                getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.POTYCZKA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
            }
        }
        else {
            organizmAtakujacy.setPolozenie(pole);
            getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
            getJakiSwiat().getMapaobiektow().remove(pole);
            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(),Zdarzenie.POTYCZKA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));

        }
    }


}

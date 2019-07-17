package Gra.Swiat.Organizm.Zwierzeta;

import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.*;

import java.util.List;
import java.util.Random;

public abstract class Zwierze extends Organizm {


    public Zwierze(Lokalizacja polozenie, Swiat jakiSwiat) {
        super(polozenie, jakiSwiat);
    }

    public void akcja() {
        Lokalizacja poprzedniePole = this.getPolozenie();
        List<Lokalizacja> mozliweSciezki = mozliweSciezki(poprzedniePole);
        Lokalizacja nowePolozenie = mozliweSciezki.get(new Random().nextInt(mozliweSciezki.size()));
        if (getJakiSwiat().czyKolizja(nowePolozenie)) {
            jakaKolizja(nowePolozenie, this, getJakiSwiat().getMapaobiektow().get(nowePolozenie));
        } else {
            this.setPolozenie(nowePolozenie);
            getJakiSwiat().getMapaobiektow().put(nowePolozenie, this);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
        }
    }

    public void decayPregnancy(Organizm organizm) {
        if (organizm.getReproductionCooldown() > 0) {
            organizm.setReproductionCooldown(organizm.getReproductionCooldown() - 1);
        } else if (organizm.getReproductionCooldown() == 1) {
            organizm.setReproductionCooldown(0);
            organizm.setCzyCiaza(false);
        }
    }

    public void setPregnancy(Organizm organizm) {
        organizm.setReproductionCooldown(10);
        organizm.setCzyCiaza(true);
    }

    public void reproducja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        setPregnancy(organizmAtakujacy);
        setPregnancy(organizmBroniacy);
        List<Lokalizacja> obszaryWokol = obszaryWokol(pole, getJakiSwiat().getMapaobiektow());
        Lokalizacja nowyObiekt = obszaryWokol.get(new Random().nextInt(obszaryWokol.size()));
        getJakiSwiat().getMapaobiektow().put(nowyObiekt, getJakiSwiat().instanceCreator(this.getTypeName(), nowyObiekt));
    }

    public void jakaKolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        if ((this.equals(getJakiSwiat().getMapaobiektow().get(pole))) && ((organizmBroniacy.getReproductionCooldown() == 0) && (organizmAtakujacy.getReproductionCooldown() == 0))) {
            reproducja(pole, organizmAtakujacy, organizmBroniacy);
        } else if (this.equals(getJakiSwiat().getMapaobiektow().get(pole)) && ((organizmBroniacy.getReproductionCooldown() != 0) || (organizmAtakujacy.getReproductionCooldown() != 0))) {
            if (organizmAtakujacy.isCzyCiaza()) {
                decayPregnancy(organizmAtakujacy);
            }
            if (organizmBroniacy.isCzyCiaza()) {
                decayPregnancy(organizmBroniacy);
            }
            return;
        } else {
            organizmBroniacy.kolizja(pole, organizmAtakujacy, organizmBroniacy);
        }
    }

    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
        if (organizmAtakujacy.getSila() < organizmBroniacy.getSila()) {
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
        } else {
            organizmAtakujacy.setPolozenie(pole);
            getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
            getJakiSwiat().getMapaobiektow().remove(pole);

        }
    }
}

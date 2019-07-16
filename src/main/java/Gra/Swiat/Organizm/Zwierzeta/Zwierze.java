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
        if(getJakiSwiat().czyKolizja(nowePolozenie)) {
            jakaKolizja(nowePolozenie, this, getJakiSwiat().getMapaobiektow().get(nowePolozenie));
        } else {
            this.setPolozenie(nowePolozenie);
            getJakiSwiat().getMapaobiektow().put(nowePolozenie, this);
            getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
        }
    }

    public void jakaKolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {
        if(this.equals(getJakiSwiat().getMapaobiektow().get(pole))) {
            List<Lokalizacja> obszaryWokol = obszaryWokol(pole, getJakiSwiat().getMapaobiektow());
            Lokalizacja nowyObiekt = obszaryWokol.get(new Random().nextInt(obszaryWokol.size()));
            getJakiSwiat().getMapaobiektow().put(nowyObiekt, getJakiSwiat().instanceCreator(this.getTypeName(), nowyObiekt));

        } else {
            organizmBroniacy.kolizja(pole, organizmAtakujacy, organizmBroniacy);
        }
    }

    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy){
        Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
            if(organizmAtakujacy.getSila() < organizmBroniacy.getSila()) {
                getJakiSwiat().getMapaobiektow().remove(poprzedniePole);

            } else {
                organizmAtakujacy.setPolozenie(pole);
                getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
                getJakiSwiat().getMapaobiektow().remove(pole);

            }
        }
    }

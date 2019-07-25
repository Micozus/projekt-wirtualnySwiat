package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.GUI.InstanceImage;
import Gra.GUI.SpecialAbbility;
import Gra.GUI.TypAnimacji;
import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.*;
import Gra.Zdarzenie;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Czlowiek extends Zwierze {

    private int wiek = 0;
    private final int MAXAGE = 500;
    private int inicjatywa = 4;
    private int sila = 5;
    private Lokalizacja polozenie;
    private int deathProbability = 50;
    private int reproductionCooldown = 0;
    private boolean czyCiaza = false;
    private Swiat swiat;
    private String typeName = "Czlowiek";
    private InstanceImage instanceImage;
    private int specialAbbilityCooldown = 0;

    public int getSpecialAbbilityCooldown() {
        return specialAbbilityCooldown;
    }

    public void setSpecialAbbilityCooldown(int specialAbbilityCooldown) {
        this.specialAbbilityCooldown = specialAbbilityCooldown;
    }

    public Czlowiek(Lokalizacja polozenie, Swiat jakiSwiat) {
        this.swiat = jakiSwiat;
        this.polozenie = polozenie;
    }

    @Override
    public void checkAction() {
        this.checkSteering();
    }

    private void checkSteering() {
        List<Lokalizacja> whereToGo = mozliweSciezki(this.polozenie);
        for (Lokalizacja toGo : whereToGo) {
            if (toGo.getxValue() < this.polozenie.getxValue()) {
                this.swiat.getGra().getAppGui().getGoLeft().setEnabled(true);
            } else if (toGo.getxValue() > this.polozenie.getxValue()) {
                this.swiat.getGra().getAppGui().getGoRight().setEnabled(true);
            } else if (toGo.getYvalue() > this.polozenie.getYvalue()) {
                this.swiat.getGra().getAppGui().getGoDown().setEnabled(true);
            } else if (toGo.getYvalue() < this.polozenie.getYvalue()) {
                this.swiat.getGra().getAppGui().getGoUp().setEnabled(true);
            }
        }
        if (this.getSpecialAbbilityCooldown() == 0) {
            this.swiat.getGra().getAppGui().unlockSpecialAbbility();
        } else {
            this.setSpecialAbbilityCooldown(this.getSpecialAbbilityCooldown() - 1);
            this.swiat.getGra().getAppGui().lockSpecialAbbility(this.getSpecialAbbilityCooldown());
        }
    }

    private List<Lokalizacja> organizmyWokol(Lokalizacja obecnePole, Map<Lokalizacja, Organizm> mapa) {
        List<Lokalizacja> obszaryWokol =
                Stream.of(
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue()),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue(), obecnePole.getYvalue() - 1),
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue() - 1),
                        new Lokalizacja(obecnePole.getxValue() - 1, obecnePole.getYvalue() + 1),
                        new Lokalizacja(obecnePole.getxValue() + 1, obecnePole.getYvalue() - 1))
                        .collect(Collectors.toList());
        for (int i = 0; i < obszaryWokol.size(); i++) {
            if (!czyWewnatrzMapy(obszaryWokol.get(i)) || !mapa.containsKey(obszaryWokol.get(i))) {
                obszaryWokol.remove(obszaryWokol.get(i));
            }
        }
        return (obszaryWokol.size() != 0) ? obszaryWokol : null;
    }

    @Override
    public void activateSpecialAbbility(SpecialAbbility specialAbbility) {
        this.setSpecialAbbilityCooldown(5);
        switch (specialAbbility) {
            case CALOPALENIE:
                organizmyWokol(this.polozenie, this.getJakiSwiat().getMapaobiektow())
                        .stream()
                        .forEach(organizm -> {
                            // null pointer, do dorobienia
                            Organizm obiekt = getJakiSwiat().getMapaobiektow().get(organizm);
                            this.getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.FADEOUT, obiekt);
                            getJakiSwiat().getMapaobiektow().remove(organizm);
                            getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.SPALENIE, obiekt.getPolozenie(), obiekt, this));
                        });
                this.getJakiSwiat().getGra().getAppGui().getActionListenButton().doClick();
                break;
            case TARCZA_ALZURA:

                break;
            case NIESMIERTELNOSC:

                break;
            case MAGICZNY_ELIKSIR:

                break;
            case SZYBKOSC_ANTYLOPY:

                break;
        }
        this.getJakiSwiat().getGra().getAppGui().lockSpecialAbbility(this.getSpecialAbbilityCooldown());
    }

    @Override
    public void akcja() {
        // Czlowiek porusza sie w taki sam sposob jak zwierzeta, ale kierunek jego ruchu definiowany jest przez uzytkownik
        // Strzalki itp.

    }

    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {

        // Specjalna umiejetnosc, osobny przycisk, inne zachowanie metody kolizja na 5 tur, 5 tur cooldown

    }

    @Override
    public InstanceImage getInstanceImage() {
        return instanceImage;
    }

    @Override
    public void setInstanceImage(InstanceImage instanceImage) {
        this.instanceImage = instanceImage;
    }

    @Override
    public int getWiek() {
        return wiek;
    }

    @Override
    public int getDeathProbability() {
        return this.deathProbability;
    }

    @Override
    public Swiat getJakiSwiat() {
        return this.swiat;
    }

    @Override
    public Lokalizacja getPolozenie() {
        return this.polozenie;
    }

    @Override
    public boolean isCzyCiaza() {
        return this.czyCiaza;
    }

    @Override
    public void setCzyCiaza(boolean czyCiaza) {
        this.czyCiaza = czyCiaza;
    }

    @Override
    public void setDeathProbability(int deathProbability) {
        this.deathProbability = deathProbability;
    }

    @Override
    public void makeOlder() {
        this.wiek++;
    }

    @Override
    public void setPolozenie(Lokalizacja polozenie) {
        this.polozenie = polozenie;
    }

    @Override
    public int getSila() {
        return sila;
    }

    @Override
    public void setSila(int sila) {
        this.sila = sila;
    }

    @Override
    public int getMAXAGE() {
        return MAXAGE;
    }

    @Override
    public int getInicjatywa() {
        return inicjatywa;
    }

    @Override
    public String getTypeName() {
        return typeName;
    }

    @Override
    protected void decayPregnancy(Organizm organizm) {
        if (organizm.getReproductionCooldown() > 0) {
            organizm.setReproductionCooldown(organizm.getReproductionCooldown() - 1);
        } else if (organizm.getReproductionCooldown() == 1) {
            organizm.setReproductionCooldown(0);
            organizm.setCzyCiaza(false);
        }
    }

    @Override
    protected void setPregnancy(Organizm organizm) {
        organizm.setReproductionCooldown(9);
        organizm.setCzyCiaza(true);
    }

    @Override
    public int getReproductionCooldown() {
        return this.reproductionCooldown;
    }

    @Override
    public void setReproductionCooldown(int reproductionCooldown) {
        this.reproductionCooldown = reproductionCooldown;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Czlowiek czlowiek = (Czlowiek) o;
        return wiek == czlowiek.wiek &&
                Objects.equals(typeName, czlowiek.typeName) &&
                Objects.equals(polozenie, czlowiek.polozenie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName, wiek, polozenie);
    }


}

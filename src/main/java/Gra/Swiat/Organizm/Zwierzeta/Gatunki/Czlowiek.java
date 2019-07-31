package Gra.Swiat.Organizm.Zwierzeta.Gatunki;

import Gra.GUI.InstanceImage;
import Gra.GUI.SpecialAbbility;
import Gra.GUI.TypAnimacji;
import Gra.Logi;
import Gra.Swiat.Organizm.Organizm;
import Gra.Swiat.Organizm.Zwierzeta.Zwierze;
import Gra.Swiat.*;
import Gra.Zdarzenie;

import java.util.*;
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
    private boolean niesmiertelnosc = false;
    private boolean tarczaEquipped = false;
    private boolean szybkoscAntylopy = false;
    private boolean magicznyEliksir = false;
    private int moveLimit = 0;

    public Czlowiek(Lokalizacja polozenie, Swiat jakiSwiat) {
        this.swiat = jakiSwiat;
        this.polozenie = polozenie;
    }

    @Override
    public void checkAction() {
        this.akcja();
    }

    private void checkSteering() {
        List<Lokalizacja> whereToGo = mozliweSciezki(this.polozenie);

        for (Lokalizacja toGo : whereToGo) {
            if (toGo.getxValue() < this.getPolozenie().getxValue()) {
                this.swiat.getGra().getAppGui().getGoLeft().setEnabled(true);
            } else if (toGo.getxValue() > this.getPolozenie().getxValue()) {
                this.swiat.getGra().getAppGui().getGoRight().setEnabled(true);
            } else if (toGo.getYvalue() > this.getPolozenie().getYvalue()) {
                this.swiat.getGra().getAppGui().getGoDown().setEnabled(true);
            } else if (toGo.getYvalue() < this.getPolozenie().getYvalue()) {
                this.swiat.getGra().getAppGui().getGoUp().setEnabled(true);
            }
        }
    }

    private List<Organizm> organizmyWokol(Lokalizacja obecnePole, Map<Lokalizacja, Organizm> mapa) {
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
        List<Organizm> organizmyWokol = new ArrayList<>();
//        for (int i = 0; i < obszaryWokol.size(); i++) {
//            if (mapa.containsKey(obszaryWokol.get(i))) {
//                organizmyWokol.add(mapa.get(obszaryWokol.get(i)));
//            }
//        }
        for (Lokalizacja lokalizacja : obszaryWokol) {
            if (mapa.containsKey(lokalizacja)) {
                organizmyWokol.add(mapa.get(lokalizacja));
            }
        }

        return organizmyWokol;
    }

    private boolean czyKolizja(Lokalizacja sprawdzanePolozenie) {
        return (this.swiat.getMapaobiektow().containsKey(sprawdzanePolozenie)) ? true : false;
    }

    public void checkMoveLimit() {
        int szansa = new Random().nextInt(100 + 1);
        if (this.isSzybkoscAntylopy() == true) {
            if (this.getSpecialAbbilityCooldown() > 2) {
                this.setMoveLimit(2);
            } else {
                if (szansa > 50) {
                    this.setMoveLimit(2);
                } else {
                    this.setMoveLimit(1);
                }
            }
        } else {
            this.setMoveLimit(1);
        }
    }

    @Override
    public void move(TypAnimacji rodzajRuchu) {
        Lokalizacja poprzedniaLokalizacja = this.getPolozenie();
        switch (rodzajRuchu) {
            case HUMANMOVE_UP:
                Lokalizacja doGory = new Lokalizacja(this.getPolozenie().getxValue(), this.getPolozenie().getYvalue() - 1);
                if (czyKolizja(doGory)) {
                    this.kolizja(doGory, this, this.swiat.getMapaobiektow().get(doGory));
                } else {
                    this.setPolozenie(doGory);
                    this.getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.MOVE, this, doGory);
                    this.getJakiSwiat().getMapaobiektow().put(doGory, this);
                    this.getJakiSwiat().getMapaobiektow().remove(poprzedniaLokalizacja);
                }
                break;
            case HUMANMOVE_DOWN:
                Lokalizacja wDol = new Lokalizacja(this.getPolozenie().getxValue(), this.getPolozenie().getYvalue() + 1);
                if (czyKolizja(wDol)) {
                    this.kolizja(wDol, this, this.swiat.getMapaobiektow().get(wDol));
                } else {
                    this.setPolozenie(wDol);
                    this.getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.MOVE, this, wDol);
                    this.getJakiSwiat().getMapaobiektow().put(wDol, this);
                    this.getJakiSwiat().getMapaobiektow().remove(poprzedniaLokalizacja);
                }
                break;
            case HUMANMOVE_LEFT:
                Lokalizacja wLewo = new Lokalizacja(this.getPolozenie().getxValue() - 1, this.getPolozenie().getYvalue());
                if (czyKolizja(wLewo)) {
                    this.kolizja(wLewo, this, this.swiat.getMapaobiektow().get(wLewo));
                } else {
                    this.setPolozenie(wLewo);
                    this.getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.MOVE, this, wLewo);
                    this.getJakiSwiat().getMapaobiektow().put(wLewo, this);
                    this.getJakiSwiat().getMapaobiektow().remove(poprzedniaLokalizacja);
                }
                break;
            case HUMANMOVE_RIGHT:
                Lokalizacja wPrawo = new Lokalizacja(this.getPolozenie().getxValue() + 1, this.getPolozenie().getYvalue());
                if (czyKolizja(wPrawo)) {
                    this.kolizja(wPrawo, this, this.swiat.getMapaobiektow().get(wPrawo));
                } else {
                    this.setPolozenie(wPrawo);
                    this.getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.MOVE, this, wPrawo);
                    this.getJakiSwiat().getMapaobiektow().put(wPrawo, this);
                    this.getJakiSwiat().getMapaobiektow().remove(poprzedniaLokalizacja);
                }
                break;
        }
        this.setMoveLimit(this.getMoveLimit() - 1);
        if (this.getMoveLimit() == 0) {
            this.getJakiSwiat().getGra().getAppGui().lockSteering();
        }
        this.getJakiSwiat().getGra().getAppGui().getActionListenButton().doClick();
    }

    @Override
    public void activateSpecialAbbility(SpecialAbbility specialAbbility) {
        this.setSpecialAbbilityCooldown(5);
        switch (specialAbbility) {
            case CALOPALENIE:
                List<Organizm> organizmyDoSpalenia = organizmyWokol(this.getPolozenie(), this.getJakiSwiat().getMapaobiektow());
                if (organizmyDoSpalenia != null) {
                    for (Organizm organizm : organizmyDoSpalenia) {
                        this.getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.FADEOUT, organizm);
                        getJakiSwiat().getMapaobiektow().remove(organizm.getPolozenie());
                        // Przy wiecej niz jednym org. dodaje sie tylko jeden log spalenia
                        getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.SPALENIE, organizm.getPolozenie(), this, organizm));
                    }
                    this.getJakiSwiat().getGra().getAppGui().getActionListenButton().doClick();
                }
                break;
            case TARCZA_ALZURA:
                this.setTarczaEquipped(true);
                break;
            case NIESMIERTELNOSC:
                this.setNiesmiertelnosc(true);
                break;
            case MAGICZNY_ELIKSIR:
                this.setMagicznyEliksir(true);
                this.setSila(this.getSila() + 5);
                break;
            case SZYBKOSC_ANTYLOPY:
                this.setSzybkoscAntylopy(true);
                this.setMoveLimit(2);
                break;
        }
        this.getJakiSwiat().getGra().getAppGui().lockSpecialAbbility(this.getSpecialAbbilityCooldown());
        this.checkSteering();
    }

    @Override
    public void akcja() {
        this.getJakiSwiat().getGra().getAppGui().checkIfEndGame();
        this.checkMoveLimit();
        if (this.getSpecialAbbilityCooldown() < 1) {
            this.getJakiSwiat().getGra().getAppGui().unlockSpecialAbbility();
            this.setTarczaEquipped(false);
            this.setNiesmiertelnosc(false);
            this.setMagicznyEliksir(false);
            this.setSzybkoscAntylopy(false);
        } else {
            if (this.isMagicznyEliksir() == true) {
                this.setSila(this.getSila() - 1);
            }
            this.setSpecialAbbilityCooldown(this.getSpecialAbbilityCooldown() - 1);
            this.getJakiSwiat().getGra().getAppGui().lockSpecialAbbility(this.getSpecialAbbilityCooldown());
            if (this.getSpecialAbbilityCooldown() < 1) {
                this.getJakiSwiat().getGra().getAppGui().unlockSpecialAbbility();
                this.setTarczaEquipped(false);
                this.setNiesmiertelnosc(false);
                this.setMagicznyEliksir(false);
                this.setSzybkoscAntylopy(false);
            }
        }
        this.checkSteering();
    }


    @Override
    public void kolizja(Lokalizacja pole, Organizm organizmAtakujacy, Organizm organizmBroniacy) {

        // Specjalna umiejetnosc, osobny przycisk, inne zachowanie metody kolizja na 5 tur, 5 tur cooldown

        if (organizmBroniacy.equals(this)) {
            if (this.isNiesmiertelnosc() == true) {
                List<Lokalizacja> obszaryWokol = obszaryWokol(pole, this.getJakiSwiat().getMapaobiektow());
                if (obszaryWokol != null) {
                    Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
                    Lokalizacja miejsceUcieczki = obszaryWokol.get(new Random().nextInt(obszaryWokol.size()));
                    organizmBroniacy.setPolozenie(miejsceUcieczki);
                    getJakiSwiat().getMapaobiektow().put(miejsceUcieczki, organizmBroniacy);
                    organizmAtakujacy.setPolozenie(pole);
                    getJakiSwiat().getMapaobiektow().put(pole, organizmAtakujacy);
                    getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.UCIECZKA, organizmBroniacy, miejsceUcieczki);
                    getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.MOVE, organizmAtakujacy, pole);
                    getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
                    getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.UCIECZKA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
                } else {
                    getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.OBRONA, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
                }
            } else if (this.isTarczaEquipped() == true) {
                List<Lokalizacja> obszaryWokol = obszaryWokol(organizmAtakujacy.getPolozenie(), this.getJakiSwiat().getMapaobiektow());
                if (obszaryWokol != null) {
                    Lokalizacja poprzedniePole = organizmAtakujacy.getPolozenie();
                    Lokalizacja miejsceUcieczki = obszaryWokol.get(new Random().nextInt(obszaryWokol.size()));
                    organizmAtakujacy.setPolozenie(miejsceUcieczki);
                    getJakiSwiat().getMapaobiektow().put(miejsceUcieczki, organizmAtakujacy);
                    getJakiSwiat().getGra().getAppGui().addTriggerAnimation(TypAnimacji.UCIECZKA, organizmAtakujacy, miejsceUcieczki);
                    getJakiSwiat().getMapaobiektow().remove(poprzedniePole);
                    getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.ODSTRASZENIE, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
                } else {
                    getJakiSwiat().getGra().getLogSet().add(new Logi(getJakiSwiat().getGra().getTura(), Zdarzenie.ODSTRASZENIE, organizmBroniacy.getPolozenie(), organizmAtakujacy, organizmBroniacy));
                }
            } else {
                super.kolizja(pole, organizmAtakujacy, organizmBroniacy);
            }
        } else {
            if (organizmBroniacy.getClass().getSuperclass().equals(Zwierze.class)) {
                super.kolizja(pole, organizmAtakujacy, organizmBroniacy);
            } else {
                organizmBroniacy.kolizja(pole, organizmAtakujacy, organizmBroniacy);
            }
        }

    }

    @Override
    public int getMoveLimit() {
        return moveLimit;
    }

    private void setMoveLimit(int moveLimit) {
        this.moveLimit = moveLimit;
    }

    private boolean isSzybkoscAntylopy() {
        return szybkoscAntylopy;
    }

    private void setSzybkoscAntylopy(boolean szybkoscAntylopy) {
        this.szybkoscAntylopy = szybkoscAntylopy;
    }


    private boolean isMagicznyEliksir() {
        return magicznyEliksir;
    }

    private void setMagicznyEliksir(boolean magicznyEliksir) {
        this.magicznyEliksir = magicznyEliksir;
    }

    private boolean isTarczaEquipped() {
        return tarczaEquipped;
    }

    private void setTarczaEquipped(boolean tarczaEquipped) {
        this.tarczaEquipped = tarczaEquipped;
    }

    public int getSpecialAbbilityCooldown() {
        return specialAbbilityCooldown;
    }

    public void setSpecialAbbilityCooldown(int specialAbbilityCooldown) {
        this.specialAbbilityCooldown = specialAbbilityCooldown;
    }

    private boolean isNiesmiertelnosc() {
        return niesmiertelnosc;
    }

    private void setNiesmiertelnosc(boolean niesmiertelnosc) {
        this.niesmiertelnosc = niesmiertelnosc;
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

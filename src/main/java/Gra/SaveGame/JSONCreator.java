package Gra.SaveGame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONCreator extends JSONArray {
    private SaveGameState saveGameState;

    public JSONCreator(SaveGameState saveGameState) {
        this.saveGameState = saveGameState;
        populateJSONArray();
    }

    private void populateJSONArray() {
        JSONObject gameStatisticsElements = new JSONObject();

        gameStatisticsElements.put("tura", this.saveGameState.getTuraCount());
        gameStatisticsElements.put("zwierzetaIlosc", this.saveGameState.getIloscOrganizmowZwierzecych());
        gameStatisticsElements.put("roslinyIlosc", this.saveGameState.getIloscOrganizmowRoslinnych());

        JSONObject gameStatistics = new JSONObject();
        gameStatistics.put("statystyki", gameStatisticsElements);
        this.add(gameStatistics);
        JSONObject gameObjectsWrapper = new JSONObject();
        JSONObject gameObjects = new JSONObject();
        this.saveGameState.getListaZapisanychObiektow().stream().forEach(o -> {
            JSONObject savedObjectElements = new JSONObject();
            savedObjectElements.put("typeName", o.getTypeName());
            savedObjectElements.put("wiek", o.getWiek());
            savedObjectElements.put("sila", o.getSila());
            savedObjectElements.put("reproductionCooldown", o.getReproductionCooldown());
            savedObjectElements.put("czyCiaza", o.isCzyCiaza());
            savedObjectElements.put("xPosition", o.getxPosition());
            savedObjectElements.put("yPosition", o.getyPosition());
            gameObjects.put("object[" + o.getxPosition() + "," + o.getyPosition() +"]", savedObjectElements);
        });
        gameObjectsWrapper.put("obiekty", gameObjects);
        this.add(gameObjectsWrapper);


    }


}

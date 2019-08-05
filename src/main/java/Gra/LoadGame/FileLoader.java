package Gra.LoadGame;

import Gra.Gra;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileLoader extends JSONParser {
    private File file;
    private Gra gra;
    private LoadGameState loadGameState;

    public LoadGameState getLoadGameState() {
        return loadGameState;
    }

    public FileLoader(File absolutePath, Gra gra) {
        this.file = absolutePath;
        this.gra = gra;
        try (FileReader reader = new FileReader(this.file.getAbsolutePath())) {
            //Read JSON file
            Object obj = this.parse(reader);
            JSONArray jsonArray = (JSONArray) obj;
            this.loadGameState = parseObject(jsonArray);


        } catch (InvalidObjectException e) {
            System.out.println("Plik json nie zawiera danych do wczytania.");
            this.gra.getAppGui().getLogsTextArea().append("Plik json nie zawiera danych do wczytania.");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Nie udało się wczytac pliku - plik nie odnaleziony.");
            this.gra.getAppGui().getLogsTextArea().append("Nie udało się wczytac pliku - plik nie odnaleziony.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Nie udało się wczytac pliku.");
            this.gra.getAppGui().getLogsTextArea().append("Nie udało się przetworzyc pliku.");
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("Nie udało się przetworzyc pliku.");
            this.gra.getAppGui().getLogsTextArea().append("Nie udało się wczytac pliku.");
            e.printStackTrace();
        }
    }

    private LoadGameState parseObject(JSONArray jsonArray) {
        int iloscZwierzat = 0;
        int iloscRoslin = 0;
        int tura = 0;
        List<ObjectLoadedInstance> listaInstancji = new ArrayList<>();

        Map statystyki = ((Map) jsonArray.get(0));
        Iterator<Map.Entry> iterator = statystyki.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = iterator.next();
            JSONObject statystykiElem = (JSONObject) pair.getValue();
            Object[] statystykiElemItems = statystykiElem.values().toArray();
            iloscZwierzat = Integer.parseInt(statystykiElemItems[0].toString());
            iloscRoslin = Integer.parseInt(statystykiElemItems[1].toString());
            tura = Integer.parseInt(statystykiElemItems[2].toString());
        }

        Map obiekty = ((Map) jsonArray.get(1));
        JSONObject jsonObject = (JSONObject) obiekty.get("obiekty");
        Iterator instancesIter = jsonObject.entrySet().iterator();
        while (instancesIter.hasNext()) {
            Map.Entry objectPair = (Map.Entry) instancesIter.next();
            JSONObject instancjaObiektu = (JSONObject) objectPair.getValue();
            Object[] objectInstanceArray = instancjaObiektu.values().toArray();

            listaInstancji.add(new ObjectLoadedInstance(
                    Integer.parseInt(objectInstanceArray[0].toString()),
                    Integer.parseInt(objectInstanceArray[1].toString()),
                    objectInstanceArray[2].toString(),
                    Boolean.getBoolean(objectInstanceArray[3].toString()),
                    Integer.parseInt(objectInstanceArray[4].toString()),
                    Integer.parseInt(objectInstanceArray[5].toString()),
                    Integer.parseInt(objectInstanceArray[6].toString())
            ));

        }
        return new LoadGameState(tura, iloscZwierzat, iloscRoslin, listaInstancji);

    }


}

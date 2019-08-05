package Gra.SaveGame;

import Gra.Gra;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
    private JSONArray jsonArray;
    private Gra gra;

    public FileSaver(JSONArray jsonArray, Gra gra) {
        this.jsonArray = jsonArray;
        this.gra = gra;
        try (FileWriter file = new FileWriter("savedGame-tura_" + this.gra.getTura() + ".json")) {
            saveToFile(file);
        } catch (IOException e) {
            System.out.println("Nie udało się zapisać pliku");
            this.gra.getAppGui().getLogsTextArea().append("Nie udało się zapisać stan gry.");
            e.printStackTrace();
            this.gra.getAppGui().getSaveGryButton().setEnabled(true);
        }
    }

    private void saveToFile(FileWriter file) throws IOException {
        file.write(jsonArray.toJSONString());
        file.flush();
        this.gra.getAppGui().getLogsTextArea().append("Udało się zapisać stan gry.");
        this.gra.getAppGui().getSaveGryButton().setEnabled(true);
    }
}

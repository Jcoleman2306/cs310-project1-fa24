import com.opencsv.CSVWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class JsonToCsvConverter {

    public static void jsonToCsv(String jsonFilePath, String csvFilePath) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(jsonFilePath));

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            if (jsonArray.isEmpty()) {
                return;
            }

            JSONObject firstObject = (JSONObject) jsonArray.get(0);
            String[] headers = (String[]) firstObject.keySet().toArray(new String[0]);
            writer.writeNext(headers);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                String[] row = new String[headers.length];

                for (int i = 0; i < headers.length; i++) {
                    row[i] = (String) jsonObject.get(headers[i]);
                }

                writer.writeNext(row);
            }
        }
    }
}


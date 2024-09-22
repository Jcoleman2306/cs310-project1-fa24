import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class ClassSchedule {

    public JSONArray csvToJson(String csvFilePath) {
        JSONArray jsonArray = new JSONArray(); // This will hold our JSON objects

        System.out.println("Reading from: " + csvFilePath); // Debug statement

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] headers = reader.readNext(); // Get the header row
            String[] line;

            while ((line = reader.readNext()) != null) {
                JSONObject jsonObject = new JSONObject();

                for (int i = 0; i < headers.length; i++) {
                    jsonObject.put(headers[i], line[i]); // Map CSV values to JSON
                }

                jsonArray.add(jsonObject); // Add each row as a JSON object
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public void writeJsonToFile(JSONArray jsonArray, String jsonFilePath) {
        try (FileWriter file = new FileWriter(jsonFilePath)) {
            file.write(jsonArray.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ClassSchedule converter = new ClassSchedule();
        String csvFilePath = "C:\\Users\\maria\\input.csv"; 
        String jsonFilePath = "C:\\Users\\maria\\output.json"; 
        String inputJsonFilePath = "C:\\Users\\maria\\output.json"; // Path to input JSON for conversion to CSV
        String outputCsvFilePath = "C:\\Users\\maria\\output.csv"; // Path to output CSV

        // Convert CSV to JSON
        JSONArray jsonArray = converter.csvToJson(csvFilePath);
        converter.writeJsonToFile(jsonArray, jsonFilePath);

        // Output the JSON to the console for testing
        System.out.println(jsonArray.toJSONString());

        // Convert JSON to CSV
        try {
            JsonToCsvConverter.jsonToCsv(inputJsonFilePath, outputCsvFilePath);
            System.out.println("JSON to CSV conversion completed successfully.");
        } catch (Exception e) {
            System.err.println("Error during JSON to CSV conversion: " + e.getMessage());
        }
    }
}



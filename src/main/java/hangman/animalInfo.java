package hangman;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class animalInfo {
    public static final String API_KEY = "KCQ67rWmRfOVK4hJh4m5oA==97RQ9O9190tc1sp7";
    static ArrayList<String> animals = new ArrayList<>();

    /**
     * Fetch animals data from the API and add them to the list.
     *
     * @param name The name of the animal to search for.
     * @throws IOException If an I/O error occurs.
     */
    public static void fetchAnimals(String name) throws IOException {
        try {
            // Create URL for API request
            URL url = new URL("https://api.api-ninjas.com/v1/animals?X-Api-Key=KCQ67rWmRfOVK4hJh4m5oA==97RQ9O9190tc1sp7&name=" +
                    name.replace(" ", "+") + "&apikey=" + API_KEY);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-Api-Key", API_KEY);

            // Check if the response is OK
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                // Read response line by line
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject animalObject = jsonArray.getJSONObject(i);
                    String animalName = animalObject.getString("name");
                    animals.add(animalName);  // Add animal name to the list
                }
            } else {
                System.out.println("Error: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Select a random animal from the list.
     *
     * @return A randomly selected animal name.
     */
    public static String selectRandomWord() {
        if (!animals.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(animals.size());
            return animals.get(randomIndex);
        } else {
            return null;
        }
    }
}

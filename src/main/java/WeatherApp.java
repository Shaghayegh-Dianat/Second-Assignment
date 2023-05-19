import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class WeatherApp {

    public final static String apiKey = "90f03252d394468f8b6134838231305";

    public static void main(String[] args){
        Scanner input=new Scanner(System.in);
        System.out.println("Enter the name of the city:");
        String city=input.nextLine();
        String JSONString = getWeatherData(city);
        try {
            JSONObject weatherObject = jsonParser(JSONString);
            System.out.println("Temperature: " + getTemperature(weatherObject));
            System.out.println("Humidity: " + getHumidity(weatherObject));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * Retrieves weather data for the specified city.
     *
     * @param city the name of the city for which weather data should be retrieved
     * @return a string representation of the weather data, or null if an error occurred
     */
    public static String getWeatherData(String city) {
        try {
            URL url = new URL("http://api.weatherapi.com/v1/current.json?key=" + apiKey + "&q=" + city);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // TODO: Write getTemperature function returns celsius temperature of city by given json string
    public static double getTemperature(JSONObject weatherObject) {
        JSONObject currentObject = (JSONObject) weatherObject.get("current");
        double temperature = (double) currentObject.get("temp_c");
        return temperature;
    }

    // TODO: Write getHumidity function returns humidity percentage of city by given json string
    public static int getHumidity(JSONObject weatherObject){
        JSONObject currentObject = (JSONObject) weatherObject.get("current");
        int humidity = (int)(long) currentObject.get("humidity");
        return humidity;
    }
    private static JSONObject jsonParser(String jsonStr) throws ParseException {
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(jsonStr);
    }
}

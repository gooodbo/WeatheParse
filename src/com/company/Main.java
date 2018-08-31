package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws ParseException {
        Main weathe = new Main();
        String parsingData;
        parsingData = weathe.connect();
        weathe.parsingJson(parsingData);

    }

    public String connect() {
        String query = "http://api.openweathermap.org/data/2.5/weather?q=Minsk,by&units=metric&APPID=b1e607de7b2c594b9d4a4d6d8fe3916b\n";
        String parsingData = "";
        HttpURLConnection connection = null;
        StringBuilder stringBuilder = new StringBuilder();
        Main main = new Main();
        try {
            connection = (HttpURLConnection) new URL(query).openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(25000);
            connection.setReadTimeout(25000);
            connection.connect();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                System.out.println(parsingData = stringBuilder.toString());
            }


        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return parsingData;

    }

    public void parsingJson(String parsingData) throws ParseException {

        JSONObject weatherJsonObject = (JSONObject) JSONValue.parseWithException(parsingData);
        System.out.println("Город: " + weatherJsonObject.get("name"));

        JSONArray weatherArray = (JSONArray) weatherJsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        System.out.println("Погода: " + weatherData.get("main"));

        JSONObject temp = (JSONObject) weatherJsonObject.get("main");
        JSONArray arrayTemp = new JSONArray();
        arrayTemp.add(temp);

        JSONObject tempData =(JSONObject) arrayTemp.get(0);
        System.out.println("Температура: " + tempData.get("temp")+" °С");


    }
}

package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws ParseException {
        Main weathe = new Main();
        weathe.getFrame();

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
                parsingData = stringBuilder.toString();
            }


        } catch (Throwable cause) {
            cause.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
        }
        return parsingData;

    }

    public String parsingCity(String parsingData) throws ParseException {
        JSONObject weatherJsonObject = (JSONObject) JSONValue.parseWithException(parsingData);
        return (String) weatherJsonObject.get("name");
    }

    public String parsingPogoda(String parsingData) throws ParseException {
        JSONObject weatherJsonObject = (JSONObject) JSONValue.parseWithException(parsingData);
        JSONArray weatherArray = (JSONArray) weatherJsonObject.get("weather");
        JSONObject weatherData = (JSONObject) weatherArray.get(0);
        return (String) weatherData.get("main");
    }

    public Long parsingTemp(String parsingData) throws ParseException {
        JSONObject weatherJsonObject = (JSONObject) JSONValue.parseWithException(parsingData);
        JSONObject temp = (JSONObject) weatherJsonObject.get("main");
        JSONArray arrayTemp = new JSONArray();
        arrayTemp.add(temp);
        JSONObject tempData = (JSONObject) arrayTemp.get(0);
        return (Long) tempData.get("temp");
    }

    public Long parsingHumidity(String parsingData) throws ParseException {
        JSONObject weatherJsonObject = (JSONObject) JSONValue.parseWithException(parsingData);
        JSONObject humidity = (JSONObject) weatherJsonObject.get("main");
        JSONArray arrayHumidity = new JSONArray();
        arrayHumidity.add(humidity);
        JSONObject HumidityData = (JSONObject) arrayHumidity.get(0);
        return (Long) HumidityData.get("humidity");
    }

    public Long parsingWind(String parsingData) throws ParseException {
        JSONObject weatherJsonObject = (JSONObject) JSONValue.parseWithException(parsingData);
        JSONObject wind = (JSONObject) weatherJsonObject.get("wind");
        JSONArray arrayWind = new JSONArray();
        arrayWind.add(wind);
        JSONObject windData = (JSONObject) arrayWind.get(0);
        return (Long) windData.get("speed");
    }


    public class MyComponents extends JComponent {

        @Override
        protected void paintComponent(Graphics g) {
            Main weathe = new Main();
            String parsingData;
            parsingData = weathe.connect();

            Graphics2D g2 = (Graphics2D) g;
            try {
                g2.drawString("Город: " + weathe.parsingCity(parsingData), 50, 50);
                g2.drawString("Погода: " + weathe.parsingPogoda(parsingData), 50, 80);
                g2.drawString("Температура: " + weathe.parsingTemp(parsingData) + " °С", 50, 110);
                g2.drawString("Влажность: " + weathe.parsingHumidity(parsingData) + " %", 50, 140);
                g2.drawString("Скорость ветра: " + weathe.parsingWind(parsingData) + " км/ч", 50, 170);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            super.paintComponent(g2);
        }
    }

    public JFrame getFrame() throws ParseException {

        JFrame jFrame = new JFrame("Weather");

        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        jFrame.setBounds(dimension.width / 2 - 450, dimension.height / 2 - 250, 900, 600);
        // jFrame.add(new MyComponents());

        Main weathe = new Main();
        String parsingData;
        parsingData = weathe.connect();

        Font font = new Font("Verdana", Font.PLAIN, 18);

        JLabel tempLabel = new JLabel("Город: " + weathe.parsingCity(parsingData));
        tempLabel.setFont(font);
        tempLabel.setBounds(50, 50, 200, 30);
        tempLabel.setForeground(Color.GRAY);

        JLabel tempLabel1 = new JLabel("Погода: " + weathe.parsingPogoda(parsingData));
        tempLabel1.setFont(font);
        tempLabel1.setBounds(50, 100, 200, 30);
        tempLabel1.setForeground(Color.GRAY);

        JLabel tempLabel2 = new JLabel("Температура: " + weathe.parsingTemp(parsingData));
        tempLabel2.setFont(font);
        tempLabel2.setBounds(50, 150, 200, 30);
        tempLabel2.setForeground(Color.GRAY);

        JLabel tempLabel3 = new JLabel("Влажность: " + weathe.parsingHumidity(parsingData));
        tempLabel3.setFont(font);
        tempLabel3.setBounds(50, 200, 200, 30);
        tempLabel3.setForeground(Color.GRAY);


        JLabel tempLabel4 = new JLabel("Скорость ветра: " + weathe.parsingWind(parsingData));
        tempLabel4.setFont(font);
        tempLabel4.setBounds(50, 250, 200, 30);
        tempLabel4.setForeground(Color.GRAY);

        jFrame.add(tempLabel);
        jFrame.add(tempLabel1);
        jFrame.add(tempLabel2);
        jFrame.add(tempLabel3);
        jFrame.add(tempLabel4);
        jFrame.setLayout(null);
        jFrame.setVisible(true);

        return jFrame;
    }
}

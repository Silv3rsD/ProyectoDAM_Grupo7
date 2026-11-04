package com.autentifpcar.mobile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class VehicleApi {

    //La dirección del servidor la guardaremos en una constante.
    private final String baseUrl;

    //Constructor
    public VehicleApi(String iBaseUrl) {
        this.baseUrl = iBaseUrl.replaceAll("/+$", "");
    }

    public Vehicle findByPlate(String plate) throws IOException, JSONException{
        HttpURLConnection connection = null;

        try {
            URL url = new URL(baseUrl + "/api/public/vehicles/plate/" + plate);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int code = connection.getResponseCode();

            if (code != 200) {
                throw new HttpException(code);
            }

            // Leer la respuesta del servidor
            StringBuilder body = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;

                while ((line =reader.readLine()) != null) {
                    body.append(line);
                }
            }

            //Convertir el JSON recibido.
            JSONObject json = new JSONObject(body.toString());

            //Creamos el objeto Vehicle.
            return new Vehicle(json.getString("vin"), json.getString("licensePlate"), json.getString("brand"), json.getString("model"), json.getInt("manufacturingYear"), json.getInt("currentMileage"));

        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }

    public static class HttpException extends IOException {
        private final int statusCode;

        public HttpException(int statusCode) {
            super("Error HTTP: " + statusCode);
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return this.statusCode;
        }
    }

}

package com.autentifpcar.mobile;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends Activity {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private EditText plateInput;
    private Button searchButton;
    private ProgressBar loading;
    private TextView status;
    private TextView vehicleData;
    private LinearLayout vehicleDetails;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        plateInput = findViewById(R.id.plate_input);
        searchButton = findViewById(R.id.search_button);
        loading = findViewById(R.id.loading);
        status = findViewById(R.id.status);
        vehicleData = findViewById(R.id.vehicle_data);
        vehicleDetails = findViewById(R.id.vehicle_details);
        searchButton.setOnClickListener(view -> search());
    }

    private void search() {
        String plate = Plate.normalize(plateInput.getText().toString());
        vehicleDetails.setVisibility(View.GONE);
        if (!Plate.isValid(plate)) {
            plateInput.setError(getString(R.string.invalid_plate));
            status.setText(R.string.invalid_plate);
            return;
        }
        plateInput.setError(null);
        setLoading(true);
        executor.execute(() -> fetchVehicle(plate));
    }

    private void fetchVehicle(String plate) {
        HttpURLConnection connection = null;
        try {
            String baseUrl = getString(R.string.api_base_url);
            URL url = new URL(baseUrl + "/api/public/vehicles/plate/" + plate);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int code = connection.getResponseCode();
            if (code == 200) {
                StringBuilder body = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) body.append(line);
                }
                JSONObject vehicle = new JSONObject(body.toString());
                if (!hasVehicleFields(vehicle)) {
                    showMessage(R.string.invalid_response);
                    return;
                }
                runOnUiThread(() -> {
                    if (isFinishing() || isDestroyed()) return;
                    setLoading(false);
                    showVehicle(vehicle);
                });
            } else if (code == 400) {
                showMessage(R.string.bad_request);
            } else if (code == 404) {
                showMessage(R.string.not_found);
            } else {
                runOnUiThread(() -> {
                    if (isFinishing() || isDestroyed()) return;
                    setLoading(false);
                    status.setText(getString(R.string.server_error, code));
                });
            }
        } catch (IOException | JSONException error) {
            showMessage(R.string.connection_error);
        } finally {
            if (connection != null) connection.disconnect();
        }
    }

    private boolean hasVehicleFields(JSONObject vehicle) {
        return vehicle.has("vin") && vehicle.has("licensePlate")
                && vehicle.has("brand") && vehicle.has("model")
                && vehicle.has("manufacturingYear") && vehicle.has("currentMileage");
    }

    private void showVehicle(JSONObject vehicle) {
        String mileage = NumberFormat.getIntegerInstance(new Locale("es", "ES"))
                .format(vehicle.optInt("currentMileage"));
        String details = getString(R.string.plate_label, vehicle.optString("licensePlate")) + "\n"
                + getString(R.string.model_label, vehicle.optString("brand"),
                        vehicle.optString("model")) + "\n"
                + getString(R.string.year_label, vehicle.optInt("manufacturingYear")) + "\n"
                + getString(R.string.mileage_label, mileage) + "\n"
                + getString(R.string.vin_label, vehicle.optString("vin"));
        vehicleData.setText(details);
        vehicleDetails.setVisibility(View.VISIBLE);
        status.setText("");
    }

    private void showMessage(int messageId) {
        runOnUiThread(() -> {
            if (isFinishing() || isDestroyed()) return;
            setLoading(false);
            status.setText(messageId);
        });
    }

    private void setLoading(boolean value) {
        searchButton.setEnabled(!value);
        loading.setVisibility(value ? View.VISIBLE : View.GONE);
        if (value) status.setText(R.string.loading);
    }

    @Override
    protected void onDestroy() {
        executor.shutdownNow();
        super.onDestroy();
    }
}

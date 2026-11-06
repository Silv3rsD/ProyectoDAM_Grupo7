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

import java.io.IOException;

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

    private VehicleApi vehicleApi;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);

        vehicleApi = new VehicleApi(getString(R.string.api_base_url));

        plateInput = findViewById(R.id.plate_input);
        searchButton = findViewById(R.id.search_button);
        loading = findViewById(R.id.loading);
        status = findViewById(R.id.status);
        vehicleData = findViewById(R.id.vehicle_data);
        vehicleDetails = findViewById(R.id.vehicle_details);
        searchButton.setOnClickListener(view -> search());
    }

    private void search() {
        String plate = normalizePlate(plateInput.getText().toString());

        // Ocultamos el resultado anterior
        vehicleDetails.setVisibility(View.GONE);

        // Comprobamos que sea válida
        if (!isValidPlate(plate)) {
            plateInput.setError(getString(R.string.invalid_plate));
            status.setText(R.string.invalid_plate);
            return;
        }

        // Quitamos cualquier error anterior
        plateInput.setError(null);

        // Mostramos que estamos buscando
        setLoading(true);

        // Realizamos la consulta en segundo plano
        executor.execute(() -> fetchVehicle(plate));
    }


    static String normalizePlate(String plate) {

        return plate.trim().toUpperCase(Locale.ROOT).replaceAll("\\s+", "");
    }

    static boolean isValidPlate(String plate) {

        return plate.matches("[A-Z0-9-]{4,15}");
    }



    private void fetchVehicle(String plate) {

        try {
            // Consultamos el vehículo
            Vehicle vehicle = vehicleApi.findByPlate(plate);

            // Volvemos al hilo de la interfaz
            runOnUiThread(() -> {
                if (isFinishing() || isDestroyed()) {
                    return;
                }

                setLoading(false);
                showVehicle(vehicle);
            });

        } catch (VehicleApi.HttpException error) {

            int code = error.getStatusCode();

            if (code == 400) {
                showMessage(R.string.bad_request);
            } else if (code == 404) {
                showMessage(R.string.not_found);
            } else {
                runOnUiThread(() -> {
                    if (isFinishing() || isDestroyed()) {
                        return;
                    }
                    setLoading(false);
                    status.setText(
                            getString(R.string.server_error, code)
                    );
                });
            }
        } catch (JSONException error) {
            showMessage(R.string.invalid_response);
        } catch (IOException error) {
            showMessage(R.string.connection_error);
        }
    }

    private void showVehicle(Vehicle vehicle) {

        String mileage = NumberFormat.getIntegerInstance(new Locale("es", "ES")).format(vehicle.getCurrentMileage());

        String details = getString(R.string.plate_label,
                        vehicle.getLicensePlate()) + "\n"

                        + getString(R.string.model_label,
                        vehicle.getBrand(),
                        vehicle.getModel()) + "\n"

                        + getString(R.string.year_label,
                        vehicle.getManufacturingYear()) + "\n"

                        + getString(R.string.mileage_label,
                        mileage) + "\n"

                        + getString(R.string.vin_label,
                        vehicle.getVin());

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

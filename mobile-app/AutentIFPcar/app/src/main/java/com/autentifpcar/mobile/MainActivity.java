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
    private LinearLayout historySection;
    private LinearLayout historyList;
    private TextView historyStatus;

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
        historySection = findViewById(R.id.history_section);
        historyList = findViewById(R.id.history_list);
        historyStatus = findViewById(R.id.history_status);
        searchButton.setOnClickListener(view -> search());
    }

    private void search() {
        String plate = normalizePlate(plateInput.getText().toString());

        // Ocultamos los resultados anteriores
        vehicleDetails.setVisibility(View.GONE);
        historySection.setVisibility(View.GONE);
        historyList.removeAllViews();

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

        // Mostramos datos de ejemplo solo para nuestro vehículo simulado.
        if ("1234ABC".equals(vehicle.getLicensePlate())
                && "WVWZZZ1JZXW000001".equals(vehicle.getVin())) {

            showDemoHistory();

        } else {

            // El historial real todavía no está conectado.
            historyStatus.setText(R.string.history_pending);
            historySection.setVisibility(View.VISIBLE);
        }
    }


    private void showMessage(int messageId) {
        runOnUiThread(() -> {
            if (isFinishing() || isDestroyed()) return;
            setLoading(false);
            status.setText(messageId);
        });
    }


    private void showDemoHistory() {

        // Registros ficticios para probar la interfaz.
        HistoryRecord[] records = {
                new HistoryRecord(
                        "ITV",
                        "2026-03-18",
                        "Inspección favorable"
                ),
                new HistoryRecord(
                        "Reparación",
                        "2026-02-03",
                        "Sustitución de embrague"
                )
        };

        // Eliminamos cualquier registro anterior.
        historyList.removeAllViews();

        // Espacio vertical expresado en dp.
        int space = Math.round(
                12 * getResources().getDisplayMetrics().density
        );

        // Recorremos los registros.
        for (HistoryRecord record : records) {

            TextView item = new TextView(this);

            item.setText(
                    record.getType() + " · " + record.getDate()
                            + "\n" + record.getDescription()
            );

            item.setTextColor(getColor(R.color.text_primary));
            item.setTextSize(16);
            item.setPadding(0, space, 0, space);

            // Añadimos el registro a la lista visual.
            historyList.addView(item);
        }

        // Dejamos claro que los datos son ficticios.
        historyStatus.setText(R.string.history_demo_warning);

        // Mostramos la tarjeta.
        historySection.setVisibility(View.VISIBLE);
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

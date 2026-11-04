package com.autentifpcar.mobile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Test public void showsValidationErrorWithoutARequest() {
        Intent intent = new Intent(InstrumentationRegistry.getInstrumentation().getTargetContext(),
                MainActivity.class);
        MainActivity activity = (MainActivity) InstrumentationRegistry.getInstrumentation()
                .startActivitySync(intent);
        try {
            InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
                EditText input = activity.findViewById(R.id.plate_input);
                input.setText("X");
                activity.findViewById(R.id.search_button).performClick();
                TextView status = activity.findViewById(R.id.status);
                assertNotNull(input.getError());
                assertEquals(activity.getString(R.string.invalid_plate), status.getText().toString());
            });
        } finally {
            InstrumentationRegistry.getInstrumentation().runOnMainSync(activity::finish);
        }
    }
}

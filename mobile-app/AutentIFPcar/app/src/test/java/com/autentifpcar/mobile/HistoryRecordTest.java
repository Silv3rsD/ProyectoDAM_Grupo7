package com.autentifpcar.mobile;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HistoryRecordTest {

    @Test
    public void testCrearHistoryRecord() {
        HistoryRecord hR = new HistoryRecord( "ITV", "2026-03-18", "Inspección favorable");

        assertEquals("ITV", hR.getType());
        assertEquals("2026-03-18", hR.getDate());
        assertEquals("Inspección favorable", hR.getDescription());

    }
}

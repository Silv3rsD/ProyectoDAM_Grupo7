package com.autentifpcar.mobile;

public class HistoryRecord {

    private final String type;
    private final String date;
    private final String description;

    //Constructor
    public HistoryRecord(String iType, String iDate, String iDescription) {
        this.type = iType;
        this.date = iDate;
        this.description = iDescription;
    }

    public String getType() {
        return this.type;
    }

    public String getDate() {
        return this.date;
    }

    public String getDescription() {
        return this.description;
    }
}

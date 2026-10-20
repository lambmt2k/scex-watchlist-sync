package com.example.scexwatchlist.dto;

import com.example.scexwatchlist.enumeration.OperationType;

public class ChangeResponse {
    private long version;
    private String deviceId;
    private String operationId;
    private OperationType type;
    private String symbol;
    private String note;
    private String afterSymbol;

    public ChangeResponse() {
    }

    public ChangeResponse(long version, String deviceId, String operationId, OperationType type, String symbol, String note, String afterSymbol) {
        this.version = version;
        this.deviceId = deviceId;
        this.operationId = operationId;
        this.type = type;
        this.symbol = symbol;
        this.note = note;
        this.afterSymbol = afterSymbol;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOperationId() {
        return operationId;
    }

    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getAfterSymbol() {
        return afterSymbol;
    }

    public void setAfterSymbol(String afterSymbol) {
        this.afterSymbol = afterSymbol;
    }

}

package com.example.scexwatchlist.dto;

import com.example.scexwatchlist.enumeration.OperationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OperationRequest {
    @NotBlank
    private String operationId;
    @NotNull
    private OperationType type;
    @NotBlank
    private String symbol;
    private String note;
    private String afterSymbol;

    public OperationRequest() {
    }

    public OperationRequest(String operationId, OperationType type, String symbol, String note, String afterSymbol) {
        this.operationId = operationId;
        this.type = type;
        this.symbol = symbol;
        this.note = note;
        this.afterSymbol = afterSymbol;
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

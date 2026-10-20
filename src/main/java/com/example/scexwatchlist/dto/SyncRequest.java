package com.example.scexwatchlist.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public class SyncRequest {
    @NotBlank
    private String deviceId;
    @PositiveOrZero
    private long baseVersion;
    @NotNull
    @Valid
    private List<OperationRequest> operations;

    public SyncRequest() {
    }

    public SyncRequest(String deviceId, long baseVersion, List<OperationRequest> operations) {
        this.deviceId = deviceId;
        this.baseVersion = baseVersion;
        this.operations = operations;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public long getBaseVersion() {
        return baseVersion;
    }

    public void setBaseVersion(long baseVersion) {
        this.baseVersion = baseVersion;
    }

    public List<OperationRequest> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationRequest> operations) {
        this.operations = operations;
    }

}

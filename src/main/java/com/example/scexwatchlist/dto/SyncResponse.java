package com.example.scexwatchlist.dto;

import com.example.scexwatchlist.entity.WatchlistItem;

import java.util.List;

public class SyncResponse {
    private String status;
    private long baseVersion;
    private long currentVersion;
    private long newVersion;
    private List<String> appliedOperationIds;
    private List<String> skippedOperationIds;
    private List<WatchlistItem> items;

    public SyncResponse() {
    }

    public SyncResponse(String status, long baseVersion, long currentVersion, long newVersion, List<String> appliedOperationIds, List<String> skippedOperationIds, List<WatchlistItem> items) {
        this.status = status;
        this.baseVersion = baseVersion;
        this.currentVersion = currentVersion;
        this.newVersion = newVersion;
        this.appliedOperationIds = appliedOperationIds;
        this.skippedOperationIds = skippedOperationIds;
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getBaseVersion() {
        return baseVersion;
    }

    public void setBaseVersion(long baseVersion) {
        this.baseVersion = baseVersion;
    }

    public long getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(long currentVersion) {
        this.currentVersion = currentVersion;
    }

    public long getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(long newVersion) {
        this.newVersion = newVersion;
    }

    public List<String> getAppliedOperationIds() {
        return appliedOperationIds;
    }

    public void setAppliedOperationIds(List<String> appliedOperationIds) {
        this.appliedOperationIds = appliedOperationIds;
    }

    public List<String> getSkippedOperationIds() {
        return skippedOperationIds;
    }

    public void setSkippedOperationIds(List<String> skippedOperationIds) {
        this.skippedOperationIds = skippedOperationIds;
    }

    public List<WatchlistItem> getItems() {
        return items;
    }

    public void setItems(List<WatchlistItem> items) {
        this.items = items;
    }

}

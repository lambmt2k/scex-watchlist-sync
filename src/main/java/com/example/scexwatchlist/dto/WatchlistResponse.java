package com.example.scexwatchlist.dto;

import com.example.scexwatchlist.entity.WatchlistItem;

import java.util.List;

public class WatchlistResponse {
    private String watchlistId;
    private long version;
    private List<WatchlistItem> items;

    public WatchlistResponse() {
    }

    public WatchlistResponse(String watchlistId, long version, List<WatchlistItem> items) {
        this.watchlistId = watchlistId;
        this.version = version;
        this.items = items;
    }

    public String getWatchlistId() {
        return watchlistId;
    }

    public void setWatchlistId(String watchlistId) {
        this.watchlistId = watchlistId;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public List<WatchlistItem> getItems() {
        return items;
    }

    public void setItems(List<WatchlistItem> items) {
        this.items = items;
    }

}

package com.example.scexwatchlist.entity;


public class WatchlistItem {
    private String symbol;
    private String note;
    private int position;

    public WatchlistItem() {
    }

    public WatchlistItem(String symbol, String note, int position) {
        this.symbol = symbol;
        this.note = note;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}

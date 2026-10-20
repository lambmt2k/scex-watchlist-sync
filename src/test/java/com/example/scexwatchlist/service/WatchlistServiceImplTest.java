package com.example.scexwatchlist.service;

import com.example.scexwatchlist.dto.OperationRequest;
import com.example.scexwatchlist.dto.SyncRequest;
import com.example.scexwatchlist.enumeration.OperationType;
import com.example.scexwatchlist.service.impl.WatchlistServiceImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WatchlistServiceImplTest {
    @Test
    void duplicateAddCreatesOneSymbol() {
        WatchlistService s = new WatchlistServiceImpl();
        var r = s.apply(new SyncRequest("mobile", 0, List.of(
                new OperationRequest("a1", OperationType.ADD, "SOL", null, null),
                new OperationRequest("a2", OperationType.ADD, "SOL", null, null))));
        assertEquals(1, r.getItems().stream().filter(i -> i.getSymbol().equals("SOL")).count());
    }

    @Test
    void retrySameOperationIdDoesNotApplyTwice() {
        WatchlistService s = new WatchlistServiceImpl();
        var op = new OperationRequest("same", OperationType.ADD, "SOL", null, null);
        s.apply(new SyncRequest("mobile", 0, List.of(op)));
        var r = s.apply(new SyncRequest("mobile", 0, List.of(op)));
        assertEquals(1, r.getItems().stream().filter(i -> i.getSymbol().equals("SOL")).count());
        assertTrue(r.getAppliedOperationIds().isEmpty());
    }

    @Test
    void staleNoteDoesNotResurrectRemovedSymbol() {
        WatchlistService s = new WatchlistServiceImpl();
        s.apply(new SyncRequest("web", 0, List.of(new OperationRequest("rm", OperationType.REMOVE, "BTC", null, null))));
        var r = s.apply(new SyncRequest("mobile", 0, List.of(new OperationRequest("note", OperationType.UPDATE_NOTE, "BTC", "keep", null))));
        assertFalse(r.getItems().stream().anyMatch(i -> i.getSymbol().equals("BTC")));
    }

    @Test
    void moveUsesRelativeAnchor() {
        WatchlistService s = new WatchlistServiceImpl();
        var r = s.apply(new SyncRequest("web", 0, List.of(new OperationRequest("move", OperationType.MOVE, "BTC", null, "FPT"))));
        assertEquals(List.of("ETH", "FPT", "BTC"), r.getItems().stream().map(i -> i.getSymbol()).toList());
    }
}
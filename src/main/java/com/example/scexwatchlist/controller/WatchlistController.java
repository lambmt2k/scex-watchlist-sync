package com.example.scexwatchlist.controller;

import com.example.scexwatchlist.dto.ChangeResponse;
import com.example.scexwatchlist.dto.SyncRequest;
import com.example.scexwatchlist.dto.SyncResponse;
import com.example.scexwatchlist.dto.WatchlistResponse;
import com.example.scexwatchlist.service.WatchlistService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlists/me")
public class WatchlistController {
    private final WatchlistService watchlistService;

    public WatchlistController(WatchlistService watchlistService) {
        this.watchlistService = watchlistService;
    }

    @GetMapping
    public WatchlistResponse getCurrent() {
        return watchlistService.getCurrent();
    }

    @GetMapping("/changes")
    public List<ChangeResponse> getChanges(@RequestParam long sinceVersion) {
        return watchlistService.getChanges(sinceVersion);
    }

    @PostMapping("/operations")
    public SyncResponse apply(@Valid @RequestBody SyncRequest request) {
        return watchlistService.apply(request);
    }
}
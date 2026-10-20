
package com.example.scexwatchlist.service;

import com.example.scexwatchlist.dto.ChangeResponse;
import com.example.scexwatchlist.dto.SyncRequest;
import com.example.scexwatchlist.dto.SyncResponse;
import com.example.scexwatchlist.dto.WatchlistResponse;

import java.util.List;

public interface WatchlistService {

    WatchlistResponse getCurrent();

    List<ChangeResponse> getChanges(long sinceVersion);

    SyncResponse apply(SyncRequest request);
}

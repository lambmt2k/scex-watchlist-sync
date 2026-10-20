
package com.example.scexwatchlist.service.impl;

import com.example.scexwatchlist.dto.ChangeResponse;
import com.example.scexwatchlist.dto.OperationRequest;
import com.example.scexwatchlist.dto.SyncRequest;
import com.example.scexwatchlist.dto.SyncResponse;
import com.example.scexwatchlist.dto.WatchlistResponse;
import com.example.scexwatchlist.entity.WatchlistItem;
import com.example.scexwatchlist.service.WatchlistService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class WatchlistServiceImpl implements WatchlistService {

    private final List<WatchlistItem> items = new ArrayList<>();

    private final List<ChangeResponse> history = new ArrayList<>();

    private final Map<String, ChangeResponse> processed =
            new ConcurrentHashMap<>();

    private final AtomicLong version = new AtomicLong(0);

    public WatchlistServiceImpl() {
        items.add(new WatchlistItem(
                "BTC", "Monitor support", 0));

        items.add(new WatchlistItem(
                "ETH", "", 1));

        items.add(new WatchlistItem(
                "FPT", "Watch for signal", 2));
    }

    @Override
    public synchronized WatchlistResponse getCurrent() {

        return new WatchlistResponse(
                "default",
                version.get(),
                new ArrayList<>(items)
        );
    }

    @Override
    public synchronized List<ChangeResponse> getChanges(
            long sinceVersion) {

        if (sinceVersion < 0 || sinceVersion > version.get()) {
            throw new IllegalArgumentException(
                    "sinceVersion must be between 0 and current version"
            );
        }

        return history.stream()
                .filter(change ->
                        change.getVersion() > sinceVersion)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized SyncResponse apply(SyncRequest request) {

        long before = version.get();

        List<String> applied = new ArrayList<>();
        List<String> skipped = new ArrayList<>();

        for (OperationRequest op : request.getOperations()) {

            String operationId = op.getOperationId();

            // Prevent duplicate operations
            if (processed.containsKey(operationId)) {
                skipped.add(operationId);
                continue;
            }

            boolean changed = applyOne(op);

            long newVersion;

            if (changed) {
                newVersion = version.incrementAndGet();
            } else {
                newVersion = version.get();
            }

            ChangeResponse event = new ChangeResponse(
                    newVersion,
                    request.getDeviceId(),
                    operationId,
                    op.getType(),
                    op.getSymbol(),
                    op.getNote(),
                    op.getAfterSymbol()
            );

            processed.put(operationId, event);

            if (changed) {
                history.add(event);
                applied.add(operationId);
            } else {
                skipped.add(operationId);
            }
        }

        return new SyncResponse(
                "RECONCILED",
                request.getBaseVersion(),
                before,
                version.get(),
                new ArrayList<>(applied),
                new ArrayList<>(skipped),
                new ArrayList<>(items)
        );
    }

    private boolean applyOne(OperationRequest op) {

        int index = indexOf(op.getSymbol());

        switch (op.getType()) {

            case ADD:
                return add(op, index);

            case REMOVE:
                return remove(index);

            case UPDATE_NOTE:
                return updateNote(op, index);

            case MOVE:
                return move(
                        op.getSymbol(),
                        op.getAfterSymbol()
                );

            default:
                throw new IllegalArgumentException(
                        "Unsupported operation type: " + op.getType()
                );
        }
    }

    private boolean add(OperationRequest op, int index) {

        // Symbol already exists
        if (index >= 0) {
            return false;
        }

        // Preserve existing demo behavior
        items.add(new WatchlistItem(
                op.getSymbol(),
                "",
                items.size()
        ));

        return true;
    }

    private boolean remove(int index) {

        if (index < 0) {
            return false;
        }

        items.remove(index);

        normalize();

        return true;
    }

    private boolean updateNote(
            OperationRequest op,
            int index) {

        // Do not resurrect removed symbols
        if (index < 0) {
            return false;
        }

        String note = op.getNote() == null
                ? ""
                : op.getNote();

        WatchlistItem old = items.get(index);

        if (Objects.equals(old.getNote(), note)) {
            return false;
        }

        items.set(index, new WatchlistItem(
                old.getSymbol(),
                note,
                old.getPosition()
        ));

        return true;
    }

    private boolean move(
            String symbol,
            String afterSymbol) {

        int source = indexOf(symbol);

        if (source < 0) {
            return false;
        }

        if (Objects.equals(symbol, afterSymbol)) {
            return false;
        }

        WatchlistItem moving = items.remove(source);

        int target;

        if (afterSymbol == null) {

            // Move to the beginning
            target = 0;

        } else {

            int anchor = indexOf(afterSymbol);

            if (anchor < 0) {
                // Missing anchor: move to the end
                target = items.size();
            } else {
                target = anchor + 1;
            }
        }

        items.add(target, moving);

        normalize();

        return true;
    }

    private int indexOf(String symbol) {

        if (symbol == null) {
            return -1;
        }

        for (int i = 0; i < items.size(); i++) {

            WatchlistItem item = items.get(i);

            if (item.getSymbol().equalsIgnoreCase(symbol)) {
                return i;
            }
        }

        return -1;
    }

    private void normalize() {

        for (int i = 0; i < items.size(); i++) {

            WatchlistItem old = items.get(i);

            items.set(i, new WatchlistItem(
                    old.getSymbol(),
                    old.getNote(),
                    i
            ));
        }
    }
}
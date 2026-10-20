# SCEX Backend Assignment — Watchlist Sync

## Problem
A device may be offline while another device edits the same watchlist. Replacing the server list with a stale snapshot can resurrect removed symbols, lose notes, and overwrite ordering.

## API
- `GET /api/v1/watchlists/me`: returns current items and server version.
- `POST /api/v1/watchlists/me/operations`: submits `ADD`, `REMOVE`, `UPDATE_NOTE`, `MOVE` operations with `baseVersion`, `deviceId`, and unique `operationId` values.
- `GET /api/v1/watchlists/me/changes?sinceVersion=0`: reads recorded changes after a version.

## Policies
- Duplicate `ADD`: one symbol only.
- `REMOVE` then stale `UPDATE_NOTE`: removed item stays removed; note update does not resurrect it.
- `MOVE` uses `afterSymbol`, not a numeric index. `null` means move to the beginning; a missing anchor falls back to the end.
- Repeated `operationId`: do not apply the operation again.
- A stale `baseVersion` is reported in the response; operations are evaluated against current server state.

## Why not last-write-wins?
A request arriving last may come from a device that has been offline for a long time. Arrival time does not necessarily represent the user's latest intent.

## Clean Architecture
HTTP controller and request DTOs are separated from the service and domain types. For a production system, extract repository/persistence ports and keep database details out of the domain.

## Production gaps (important)
This is a runnable reference implementation, not production-ready:
- State and history are in memory and reset on restart.
- One default watchlist only; no authentication or per-user authorization.
- `synchronized` protects only one application instance. Multi-instance deployment requires database transactions and locking or optimistic concurrency control.
- Persist idempotency keys atomically with state changes.
- Define tombstone retention, explicit re-add semantics, concurrent note-edit behavior, and ordering conflict policy with Product.
- The demo increments versions per changed operation; batch/atomicity semantics should be specified for production.
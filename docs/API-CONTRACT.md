# API contract

## GET `/api/v1/watchlists/me`

Initial response:
```json
{"watchlistId":"default","version":0,"items":[{"symbol":"BTC","note":"Monitor support","position":0},{"symbol":"ETH","note":"","position":1},{"symbol":"FPT","note":"Watch for signal","position":2}]}
```

## POST `/api/v1/watchlists/me/operations`

See four-operation request in README. Response on initial state:
```json
{"status":"RECONCILED","baseVersion":0,"currentVersion":0,"newVersion":4,"appliedOperationIds":["web-01","web-02","web-03","web-04"],"skippedOperationIds":[],"items":[{"symbol":"ETH","note":"Support zone","position":0},{"symbol":"BNB","note":"","position":1},{"symbol":"BTC","note":"Monitor support","position":2}]}
```

`deviceId` is informational, not user authentication. Every operation has its own `operationId`. `baseVersion` is client-known version. `currentVersion` is server version before processing; `newVersion` after processing. Batch is sequential and non-atomic. Version increases per state-changing operation. Duplicate operation IDs are skipped. ADD ignores provided note in current implementation.

## GET `/api/v1/watchlists/me/changes?sinceVersion=0`

Returns changed operation events with `version`, `deviceId`, `operationId`, `type`, `symbol`, `note`, `afterSymbol`. `sinceVersion` outside `0..currentVersion` returns HTTP 400.

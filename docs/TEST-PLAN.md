# Test plan

Chạy `mvn clean test`.

## Unit tests có sẵn
- ADD trùng không tạo bản sao.
- Retry cùng operationId không apply lại.
- UPDATE_NOTE sau REMOVE không hồi sinh symbol.
- MOVE sử dụng afterSymbol.

## Manual scenario
1. Restart app, GET watchlist: `BTC → ETH → FPT`, version 0.
2. POST batch đủ 4 thao tác từ README.
3. GET watchlist: `ETH → BNB → BTC`, version 4.
4. Gửi lại cùng batch: version không tăng, các IDs được skip.
5. GET changes sinceVersion=0: có 4 events.
6. Test MOVE `afterSymbol:null` để đưa mã lên đầu.
7. Test request stale `baseVersion:0` khi server đã version 4.

## Chưa có test tự động
Concurrent multi-instance; competing note updates; reuse operationId with different payload; rollback atomic batch; JWT per-user authorization; persistence after restart.

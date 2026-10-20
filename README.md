# SCEX Watchlist Sync — Java Backend Demo

Demo đồng bộ watchlist giữa Web/Mobile bằng **operation log**, tránh ghi đè snapshot cũ. Stack: Java 21, Spring Boot 3.4.5, Maven, JUnit 5.

## Chạy local

```bash
java -version
mvn -version
mvn clean test
mvn spring-boot:run
```

Server: `http://localhost:8080`. Import [HTTP examples](docs/api-examples.http) vào IntelliJ HTTP Client / VS Code REST Client.

## API

| Method | Path | Mục đích |
|---|---|---|
| GET | `/api/v1/watchlists/me` | Danh sách và version hiện tại |
| POST | `/api/v1/watchlists/me/operations` | Gửi batch operations |
| GET | `/api/v1/watchlists/me/changes?sinceVersion=0` | Xem lịch sử thay đổi |

## Payload đủ 4 loại thao tác

```json
{
  "deviceId":"web",
  "baseVersion":0,
  "operations":[
    {"operationId":"web-01","type":"ADD","symbol":"BNB","note":"Track BNB"},
    {"operationId":"web-02","type":"REMOVE","symbol":"FPT"},
    {"operationId":"web-03","type":"UPDATE_NOTE","symbol":"ETH","note":"Support zone"},
    {"operationId":"web-04","type":"MOVE","symbol":"BTC","afterSymbol":"BNB"}
  ]
}
```

Danh sách khởi tạo `BTC → ETH → FPT`; sau batch: `ETH → BNB → BTC`, version tăng từ 0 lên 4. **Lưu ý:** implementation hiện tại bỏ qua `note` khi ADD (note khởi tạo rỗng); UPDATE_NOTE vẫn hoạt động.

## Tài liệu cho reviewer

- [API contract và response mẫu](docs/API-CONTRACT.md)
- [Quy ước hệ thống và trade-off](docs/DESIGN-DECISIONS.md)
- [Test plan](docs/TEST-PLAN.md)
- [Log minh họa](docs/sample-system-log.txt)

## Giới hạn hiện tại

Đây là **demo phục vụ bài test, không phải production**. Chỉ có một watchlist in-memory, reset khi restart; chưa có JWT/authorization, chưa xác định user từ `/me`; `deviceId` không phải user identity. Idempotency key lưu trong memory. `synchronized` chỉ bảo vệ trong một JVM. Batch xử lý tuần tự, **không atomic all-or-nothing**; version tăng theo từng operation thực sự thay đổi state. Client stale được xử lý trên state hiện tại nhưng chưa có conflict policy đầy đủ. Log mẫu chỉ minh họa, không phải runtime log.
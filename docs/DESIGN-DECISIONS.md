# Quy ước và quyết định thiết kế

1. **Operation-based:** client gửi ADD/REMOVE/UPDATE_NOTE/MOVE thay vì ghi đè toàn bộ snapshot.
2. **Identity:** `deviceId` chỉ để trace thiết bị. Production phải lấy `userId` từ JWT/session đã xác thực, phân quyền theo user.
3. **Version:** `baseVersion` là version client biết; stale nếu nhỏ hơn currentVersion. Demo vẫn apply operations trên server state mới nhất.
4. **Idempotency:** cùng `operationId` không apply lại. Production cần lưu durable và reject cùng ID nhưng khác payload.
5. **ADD:** thêm cuối danh sách nếu chưa có (case-insensitive). Demo hiện bỏ qua note khi ADD.
6. **REMOVE:** xóa nếu tồn tại. UPDATE_NOTE đến sau không làm sống lại mã đã xóa.
7. **UPDATE_NOTE:** chỉ thay note, không đổi vị trí.
8. **MOVE:** `MOVE(symbol,afterSymbol)` đưa symbol ngay sau anchor; `afterSymbol=null` đưa lên đầu. Anchor không tồn tại: demo đưa xuống cuối. Symbol không tồn tại hoặc anchor bằng symbol: no-op.
9. **Batch:** xử lý theo thứ tự mảng, không rollback toàn bộ nếu có lỗi. Version tăng theo từng operation thay đổi state, không phải mỗi batch.
10. **Production roadmap:** JWT, database transaction, optimistic locking, durable event/idempotency store, conflict policy note-vs-note / move-vs-move, atomic batch hoặc partial result rõ ràng, structured logs, multi-instance integration tests.

**Trade-off:** Relative MOVE ít bị ảnh hưởng bởi index thay đổi, nhưng anchor vẫn có thể bị xóa. Stale operation có thể áp dụng hợp lý cho ADD/REMOVE, nhưng UPDATE_NOTE cạnh tranh cần policy riêng. Không nên tuyên bố demo giải quyết mọi xung đột.

# HƯỚNG DẪN SỬ DỤNG PHẦN MỀM WKOL (Lưu ý để chạy code nên dùng netbean)

## 1. Vào SQLServer 2017 để backup file lấy dữ liệu
 - Click chuột phải vào **Databases** chọn **Restore Database**.
 - Chọn **Device** tiếp theo chọn dấu 3 chấm để mở Dialog chọn file backup.
 - Trong mục **Backup media type** chọn **File** sau đó chọn **Add**.
 - Mở thư mục **JavaFX** chọn **CoffeeShop.bak** sau đó nhấn OK.

## 2. Tạo File Kết nối với Database
 - Vào đường dẫn **JavaFX\OL!Tea\src\tqduy\connect** tạo file **SQLDBInfo.java**.
 - Mở file vừa tạo và thêm đoạn code
```
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

/**
 *
 * @author ADMIN
 */
public class SQLDBInfo {
    public final static String USER_NAME = "duy";
    public final static String PASSWORD = "1234";
    public final static String SERVER_NAME = "DESKTOP-6T1NTE9";
}
```
 - Với ***USER_NAME*** là user để connect vào DB ở máy bạn.
 - Với ***PASSWORD*** là pass của user connect vào DB ở máy bạn.
 - Với ***SERVER_NAME*** là tên server của máy bạn.


## 3. Thêm thư viện cần thiết
 - Mở Folder với editor netbean.
 - Click chuột phải vào project.
 - Chọn **Properties** chọn **Libraries**.
 - Trong tab **Compile** xóa các thư viện hiện đang lỗi (đánh dấu màu đỏ).
 - Nhấn nút **Add JAR/Folder** chọn đến thư mục **lib** trong project.
 - Thêm tất cả các thư viện sau đó Ok.

## 4. Chạy chương trình.

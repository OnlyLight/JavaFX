/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.time.LocalDate;
import static tqduy.connect.DBUtils_LoaiMon.execute;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Bill {
    public static void insert(int idNV, int tongTien, LocalDate ngayLap) {
        execute("INSERT INTO dbo.Bill (idNhanVien, tongTien, ngayLap) VALUES ( "+idNV+", "+tongTien+", CONVERT(DATE, '"+ngayLap+"') )");
        System.out.println("Chèn thành công !!");
    }
}

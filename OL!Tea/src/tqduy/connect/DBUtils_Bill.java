/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import tqduy.bean.Bill;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Bill {
    public static ArrayList<Bill> getList() {
        ArrayList<Bill> arrBill = new ArrayList<>();
        
        String sql = "SELECT dbo.NhanVien.userName, dbo.Bill.tongTien, dbo.Bill.ngayLap FROM dbo.NhanVien JOIN dbo.Bill ON Bill.idNhanVien = NhanVien.idNV";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenNV = res.getString("userName");
                int tongTien = res.getInt("tongTien");
                Date ngayLap = res.getDate("ngayLap");
                
                Bill b = new Bill();
                b.setTenNV(tenNV);
                b.setTongTien(tongTien);
                b.setNgayLap(ngayLap);
                arrBill.add(b);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrBill;
        }
    }
    
    public static int getSumPirceForMonth(int thangLap, int namLap) {
        int sumPay = 0;
        
        String sql = "SELECT SUM(dbo.Bill.tongTien) AS tongTien FROM dbo.Bill WHERE MONTH(dbo.Bill.ngayLap) = "+thangLap+" AND YEAR(dbo.Bill.ngayLap) = "+namLap+"";

        ResultSet res = query(sql);

        try {
            if (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                sumPay = res.getInt("tongTien");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return sumPay;
        }
    }
    
    public static void insert(int idNV, int tongTien, LocalDate ngayLap) {
        execute("INSERT INTO dbo.Bill (idNhanVien, tongTien, ngayLap) VALUES ( "+idNV+", "+tongTien+", CONVERT(DATE, '"+ngayLap+"') )");
        System.out.println("Chèn thành công !!");
    }
}

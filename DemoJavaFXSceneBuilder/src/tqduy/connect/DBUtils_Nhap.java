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
import tqduy.bean.Nhap;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Nhap {
    
    // TABLE LOAI MON
    public static ArrayList<Nhap> getList() {
        ArrayList<Nhap> arrNhap = new ArrayList<>();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_NHAP + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idNhap = res.getInt("idNhap");
                String tenSpNhap = res.getString("tenSpNhap");
                int idLoaiNX = res.getInt("idLoaiNX");
                int donGia = res.getInt("donGia");
                int soLuong = res.getInt("soLuong");
                Date date = res.getDate("ngayNhap");
                
                Nhap n = new Nhap(idNhap, tenSpNhap, idLoaiNX, donGia, soLuong, date);
                arrNhap.add(n);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrNhap;
        }
    }
    
    public static int getNhap(int dayNeed) {
        int sL = 0;
        String sql = "SELECT dbo.Nhap.soLuong FROM dbo.Nhap WHERE YEAR(dbo.Nhap.ngayNhap) = "+dayNeed+"";

        ResultSet res = query(sql);
        System.out.println("SQL: " + sql);

        try {
            if (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int soLuong = res.getInt("soLuong");
                
                sL = soLuong;
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " - " + e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return sL;
        }
    }
    
    public static void delete(int id) {
        execute("DELETE FROM " + DBUtils_LoaiNX.TB_NHAP + " WHERE idNhap = " + id + "");
        System.out.println("Delete Success");
    }

    public static void insert(String tenSp, int idLoai, int donGia, int soLuong, LocalDate ngayNhap) {
        execute("INSERT INTO dbo.Nhap " +
"        ( tenSpNhap , " +
"          idLoaiNX , " +
"          donGia , " +
"          soLuong , " +
"          ngayNhap " +
"        )" +
"VALUES  ( N'"+tenSp+"' , " +
"          "+idLoai+" , " +
"          "+donGia+" , " +
"          "+soLuong+" , " +
"          CONVERT(DATE, '"+ngayNhap+"') " +
"        )");
        System.out.println("Chèn thành công !!");
    }
}

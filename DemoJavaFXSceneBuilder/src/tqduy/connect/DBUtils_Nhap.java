/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.Connection;
import java.sql.DriverManager;
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
    private static Connection con = null;

    public static Connection conn() {
        String url = "jdbc:sqlserver://DESKTOP-6T1NTE9\\SQLEXPRESS:1433;" + "databaseName=" + DBUtils_LoaiMon.CREATE_DB_NAME + ";";
        try {
            con = DriverManager.getConnection(url, DBUtils_LoaiMon.USER_NAME, DBUtils_LoaiMon.PASSWORD);
            System.out.println("Connect Success !!");
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return con;
    }
    
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
                System.out.println(n);
                arrNhap.add(n);
            }
        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println("Xin nhap lai !!");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Lỗi");
                }
            }
            return arrNhap;
        }
    }
    
    public static Nhap getNhap(int id) {
        Nhap n = new Nhap();
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_NHAP + " WHERE idNhap="+id+"";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idNhap = res.getInt("idNhap");
                String tenSpNhap = res.getString("tenSpNhap");
                int idLoaiNX = res.getInt("idLoaiNX");
                int donGia = res.getInt("donGia");
                int soLuong = res.getInt("soLuong");
                Date date = res.getDate("ngayNhap");
                
                n = new Nhap(idNhap, tenSpNhap, idLoaiNX, donGia, soLuong, date);
            }
        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println("Xin nhap lai !!");
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Lỗi");
                }
            }
            return n;
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

//    public static void update(int idMon, int soLuong) {
//        execute("UPDATE MonOrder SET soLuong = "+soLuong+" WHERE idMon = "+idMon+"");
//        System.out.println("Update Success");
//    }
}

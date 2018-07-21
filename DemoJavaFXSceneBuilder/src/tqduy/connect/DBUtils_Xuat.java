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
import tqduy.bean.Xuat;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Xuat {
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
    
    // TABLE Xuat
    public static ArrayList<Xuat> getList() {
        ArrayList<Xuat> arrXuat = new ArrayList<>();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_XUAT + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idXuat = res.getInt("idXuat");
                String tenSpXuat = res.getString("tenSpXuat");
                int idLoaiNX = res.getInt("idLoaiNX");
                int soLuong = res.getInt("soLuong");
                Date date = res.getDate("ngayXuat");
                
                Xuat x = new Xuat(idXuat, tenSpXuat, idLoaiNX, soLuong, date);
                System.out.println(x);
                arrXuat.add(x);
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
            return arrXuat;
        }
    }
    
    public static Xuat getMon(int id) {
        Xuat x = new Xuat();
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_XUAT + " WHERE idXuat="+id+"";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idXuat = res.getInt("idXuat");
                String tenSpXuat = res.getString("tenSpXuat");
                int idLoaiNX = res.getInt("idLoaiNX");
                int soLuong = res.getInt("soLuong");
                Date date = res.getDate("ngayNhap");
                
                x = new Xuat(idXuat, tenSpXuat, idLoaiNX, soLuong, date);
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
            return x;
        }
    }
    
    public static void delete(int id) {
        execute("DELETE FROM " + DBUtils_LoaiNX.TB_XUAT + " WHERE idXuat = " + id + "");
        System.out.println("Delete Success");
    }
    
    public static void insert(String tenSp, int idLoai, int soLuong, LocalDate ngayXuat) {
        execute("INSERT INTO dbo.Xuat " +
"        ( tenSpXuat , " +
"          idLoaiNX , " +
"          soLuong , " +
"          ngayXuat " +
"        ) " +
"VALUES  ( N'"+tenSp+"' , " +
"          "+idLoai+" , " +
"          "+soLuong+" , " +
"          CONVERT(DATE, '"+ngayXuat+"') " +
"        )");
        System.out.println("Chèn thành công !!");
    }
}

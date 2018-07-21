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
import java.util.ArrayList;
import tqduy.bean.MonOrder;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_MonOrder {
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
    
    // TABLE MON
    public static ArrayList<MonOrder> getList() {
        ArrayList<MonOrder> arrMon = new ArrayList<>();
        
        String sql = "SELECT dbo.Mon.idMon, dbo.Mon.tenMon, dbo.Mon.donGia, dbo.LoaiMon.loaiMon, dbo.MonOrder.soLuong " +
"FROM dbo.Mon JOIN dbo.LoaiMon ON LoaiMon.id = Mon.idLoaiMon JOIN dbo.MonOrder ON MonOrder.idMon = dbo.Mon.idMon";

        ResultSet res = query(sql);
        
        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = res.getInt("idMon");
                String tenMon = res.getString("tenMon");
                int donGia = res.getInt("donGia");
                String loaiMon = res.getString("loaiMon");
                int soLuong = res.getInt("soLuong");
                
                MonOrder monOrder = new MonOrder(id, tenMon, donGia, loaiMon, soLuong);
                arrMon.add(monOrder);
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
            return arrMon;
        }
    }
    
    public static void deleteAll() {
        execute("DELETE FROM " + DBUtils_LoaiMon.TB_MON_ORDER + "");
        System.out.println("Delete All Success");
    }
    
    public static void delete(int id) {
        execute("DELETE FROM " + DBUtils_LoaiMon.TB_MON_ORDER + " WHERE idMon = " + id + "");
        System.out.println("Delete Success");
    }

    public static void insert(int idMon, int soLuong) {
        execute("INSERT INTO " + DBUtils_LoaiMon.TB_MON_ORDER + "( soLuong, idMon ) VALUES ( "+soLuong+", "+idMon+" )");
        System.out.println("Chèn thành công !!");
    }

    public static void update(int idMon, int soLuong) {
        execute("UPDATE MonOrder SET soLuong = "+soLuong+" WHERE idMon = "+idMon+"");
        System.out.println("Update Success");
    }
    // END TB MON
}

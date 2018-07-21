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
import tqduy.bean.LoaiNX;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_LoaiNX {
    public final static String TB_LOAINX = "LoaiNX";
    public final static String TB_NHAP = "Nhap";
    public final static String TB_XUAT = "Xuat";
    public final static String TB_DVT = "DVT";
    public final static String USER_NAME = "duy";
    public final static String PASSWORD = "1234";
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
    
    // TABLE LOAI NX
    public static ArrayList<LoaiNX> getList() {
        ArrayList<LoaiNX> arrLoaiMon = new ArrayList<>();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_LOAINX + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idNX = res.getInt("idLoaiNX");
                String tenLoaiNX = res.getString("tenLoaiNX");
                int dvt = res.getInt("idDVT");
                
                LoaiNX lnx = new LoaiNX(idNX, tenLoaiNX, dvt);
                arrLoaiMon.add(lnx);
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
            return arrLoaiMon;
        }
    }
    
    public static LoaiNX getLoaiNX(int idLNX) {
        LoaiNX lnx = new LoaiNX();
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_LOAINX + " WHERE idLoaiNX="+idLNX+"";

        ResultSet res = query(sql);

        try {
            int idNX = res.getInt("idLoaiNX");
                String tenLoaiNX = res.getString("tenLoaiNX");
                int dvt = res.getInt("idDVT");

            lnx = new LoaiNX(idNX, tenLoaiNX, dvt);
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
            return lnx;
        }
    }
}

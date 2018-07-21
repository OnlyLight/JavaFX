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
import tqduy.bean.DVT;
import tqduy.bean.LoaiNX;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_DVT {
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
    
    // TABLE DVT
    public static ArrayList<DVT> getList() {
        ArrayList<DVT> arrDVT = new ArrayList<>();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_DVT + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idDVT = res.getInt("idDVT");
                String dVT = res.getString("DVT");
                
                DVT dvt = new DVT(idDVT, dVT);
                arrDVT.add(dvt);
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
            return arrDVT;
        }
    }
    
    public static DVT getDVT(int id) {
        DVT dvt = new DVT();
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_DVT + " WHERE idDVT="+id+"";

        ResultSet res = query(sql);

        try {
            int idDVT = res.getInt("idDVT");
            String dVT = res.getString("DVT");

            dvt = new DVT(idDVT, dVT);
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
            return dvt;
        }
    }
}

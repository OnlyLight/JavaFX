/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.Statement;
import java.util.ArrayList;
import tqduy.bean.LoaiMon;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_LoaiMon {
    public final static String CREATE_DB_NAME = "CoffeeShop";
    public final static String TB_LOAIMON = "LoaiMon";
    public final static String TB_MON = "Mon";
    public final static String TB_MON_ORDER = "MonOrder";
    public final static String USER_NAME = "duy";
    public final static String PASSWORD = "1234";
    public static Connection con = conn();

    public static Connection conn() {
        String url = "jdbc:sqlserver://"+ SQLDBInfo.SERVER_NAME +"\\SQLEXPRESS:1433;" + "databaseName=" + DBUtils_LoaiMon.CREATE_DB_NAME + ";";
        try {
            con = DriverManager.getConnection(url, SQLDBInfo.USER_NAME, SQLDBInfo.PASSWORD);
            System.out.println("Connect Success !!");
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return con;
    }
    
    public static void main(String[] args) {
        System.out.println("Hello");
    }
    
    // TABLE LOAI MON
    public static ArrayList<LoaiMon> getList() throws SQLException {
        ArrayList<LoaiMon> arrLoaiMon = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListLoaiMon}");
            
        ResultSet result = command.executeQuery();
        
//        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_LOAIMON + " ORDER BY id";
//
//        ResultSet res = query(sql);

        try {
            while (result.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = result.getInt("id");
                String loaiMon = result.getString("loaiMon");
                boolean isActive = result.getBoolean("isActive");
                
                LoaiMon lm = new LoaiMon(id, loaiMon, isActive);
                arrLoaiMon.add(lm);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.out.println("Lỗi");
//                }
//            }
            return arrLoaiMon;
        }
    }
    
    public static LoaiMon getMon(int idLM) throws SQLException {
        LoaiMon lm = new LoaiMon();
        
        CallableStatement command = con.prepareCall("{call pr_getListLoaiMonById (?)}");
        command.setInt(1, idLM);

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_LOAIMON + " WHERE id="+idLM+"";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = res.getInt("id");
                String loaiMon = res.getString("loaiMon");
                boolean isActive = res.getBoolean("isActive");
                
                lm = new LoaiMon(id, loaiMon, isActive);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
//            if (con != null) {
//                try {
//                    con.close();
//                } catch (SQLException e) {
//                    System.out.println("Lỗi");
//                }
//            }
            return lm;
        }
    }
    
//    public static void delete(int id) {
//        execute("DELETE FROM " + DBUtils_LoaiMon.TB_LOAIMON + " WHERE id = " + id + "");
//    }

    public static void insert(String loaiMon) {
        execute("INSERT INTO " + DBUtils_LoaiMon.TB_LOAIMON + "(loaiMon) VALUES ( N'"+ loaiMon +"' )");
        System.out.println("Chèn thành công !!");
    }

    public static void update(int id, boolean isActive) {
        int check = 0;
        if(isActive) check = 1;
        execute("UPDATE " + DBUtils_LoaiMon.TB_LOAIMON + " SET isActive = "+check+" WHERE id = "+id+"");
        System.out.println("Insert Success !!");
    }
    // END TB LOAI MON

    public static ResultSet query(String sql) {
        ResultSet res = null;
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            res = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public static void execute(String sql) {
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
            System.out.println("Lỗi cú pháp");
        } finally {
//            //finally block used to close resources
//            try {
//                if (stmt != null) {
//                    stmt.close();
//                }
//            } catch (SQLException se) {
//            }// do nothing
//            try {
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException se) {
//                System.out.println("Lỗi đóng connect !!");
//            }//end finally try
        }
    }
}

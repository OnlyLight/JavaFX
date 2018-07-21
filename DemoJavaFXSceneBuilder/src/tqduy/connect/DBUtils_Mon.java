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
import tqduy.bean.Mon;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Mon {
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
    
    public static void main(String[] args) {
        conn();
    }
    
    // TABLE MON
    public static ArrayList<Mon> getList() {
        ArrayList<Mon> arrMon = new ArrayList<>();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_MON + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = res.getInt("idMon");
                String loaiMon = res.getString("tenMon");
                int donGia = res.getInt("donGia");
                int idLoaiMon = res.getInt("idLoaiMon");
                
                Mon m = new Mon(id, loaiMon, donGia, idLoaiMon);
                arrMon.add(m);
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
    
    public static ArrayList<Mon> getMon(int idLM) {
        ArrayList<Mon> arrMon = new ArrayList<>();
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_MON + " WHERE idLoaiMon="+idLM+"";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = res.getInt("idMon");
                String loaiMon = res.getString("tenMon");
                int donGia = res.getInt("donGia");
                int idLoaiMon = res.getInt("idLoaiMon");
                
                Mon m = new Mon(id, loaiMon, donGia, idLoaiMon);
                arrMon.add(m);
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
    
    public static void delete(int idMon) {

        boolean check = false;
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_MON + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int stid = res.getInt("idMon");
                if (stid == idMon) {
                    execute("DELETE FROM " + DBUtils_LoaiMon.TB_MON + " WHERE idMon = " + idMon + "");
                    check = true;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Xin nhap lai !!");
        } finally {
            if (!check) {
                System.out.println("Không tìm thấy phần tử cần xóa !!");
            } else {
                System.out.println("Xóa thành công !!");
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.getStackTrace();
                    System.out.println("Lỗi");
                }
            }
        }
    }

    public static void insert(String tenMon, int donGia, int idLoaiMon) {
        execute("INSERT INTO " + DBUtils_LoaiMon.TB_MON + "( tenMon, donGia, idLoaiMon ) VALUES ( N'"+tenMon+"', "+donGia+", "+idLoaiMon+" )");
        System.out.println("Chèn thành công !!");
    }

    public static void update(int idMon, String tenMon, int donGia, int idLoaiMon) {
        boolean check = false;
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_MON + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int stid = res.getInt("idMon");
                if (stid == idMon) {
                    execute("UPDATE " + DBUtils_LoaiMon.TB_MON + " SET tenMon = N'"+tenMon+"', donGia="+donGia+", idLoaiMon="+idLoaiMon+" WHERE idMon="+idMon+"");
                    check = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println("Lỗi update");
        } finally {
            if (!check) {
                System.out.println("Không tìm thấy phần tử cần chỉnh sửa !!");
            } else {
                System.out.println("Sửa thành công !!");
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Lỗi đóng connect");
                }
            }
        }
    }
    // END TB MON
}

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
    public static ArrayList<LoaiMon> getList() {
        ArrayList<LoaiMon> arrLoaiMon = new ArrayList<>();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_LOAIMON + " ORDER BY id";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = res.getInt("id");
                String loaiMon = res.getString("loaiMon");
                
                LoaiMon lm = new LoaiMon(id, loaiMon);
                arrLoaiMon.add(lm);
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
    
    public static LoaiMon getMon(int idLM) {
        LoaiMon lm = new LoaiMon();
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_LOAIMON + " WHERE id="+idLM+"";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = res.getInt("id");
                String loaiMon = res.getString("loaiMon");
                
                lm = new LoaiMon(id, loaiMon);
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
            return lm;
        }
    }
    
    public static void delete(int id) {
        boolean check = false;
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_LOAIMON + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int stid = res.getInt("id");
                if (stid == id) {
                    execute("DELETE FROM " + DBUtils_LoaiMon.TB_LOAIMON + " WHERE id = " + id + "");
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
                    System.out.println("Lỗi");
                }
            }
        }
    }

    public static void insert(int id, String loaiMon) {
        boolean check = true;
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_LOAIMON + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int stid = res.getInt("id");
                if (stid == id) {
                    check = false;
                    break;
                }
            }
        } catch (SQLException e) {
            System.out.println("Xin nhap lai !!");
            check = true;
        } finally {
            if (check) {
                execute("INSERT INTO " + DBUtils_LoaiMon.TB_LOAIMON + "(id, loaiMon) VALUES ( "+id+", N'"+ loaiMon +"' )");
                System.out.println("Chèn thành công !!");
            } else {
                System.out.println("Trùng ID !!");
            }

            try {
                if (res != null) {
                    res.close();
                }
            } catch (SQLException se) {
            }// do nothing

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println("Lỗi đóng Connect");
                }
            }
        }
    }

    public static void update(int id, String loaiMon) {
        boolean check = false;
        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_LOAIMON + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int stid = res.getInt("id");
                if (stid == id) {
                    execute("UPDATE " + DBUtils_LoaiMon.TB_LOAIMON + " SET loaiMon = N'"+loaiMon+"' WHERE id = "+id+"");
                    check = true;
                    break;
                }
            }
        } catch (SQLException e) {
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
    // END TB LOAI MON

    public static ResultSet query(String sql) {
        con = conn();
        ResultSet res = null;
        Statement stmt = null;

        try {
            stmt = con.createStatement();
            res = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return res;
    }

    public static void execute(String sql) {
        con = conn();
        Statement stmt = null;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
            System.out.println(e.getMessage());
            System.out.println("Lỗi cú pháp");
//            System.exit(0);
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                System.out.println("Lỗi đóng connect !!");
            }//end finally try
        }
    }
}

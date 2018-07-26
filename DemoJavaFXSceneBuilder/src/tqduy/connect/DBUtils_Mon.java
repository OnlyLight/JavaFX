/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tqduy.bean.Menu;
import tqduy.bean.Mon;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Mon {
    
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
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrMon;
        }
    }
    
    public static ArrayList<Menu> getListMenu() {
        ArrayList<Menu> arrMon = new ArrayList<>();
        
        String sql = "SELECT dbo.Mon.tenMon, dbo.Mon.donGia, dbo.LoaiMon.loaiMon FROM dbo.Mon JOIN dbo.LoaiMon ON LoaiMon.id = Mon.idLoaiMon";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenMon = res.getString("tenMon");
                int donGia = res.getInt("donGia");
                String loaiMon = res.getString("loaiMon");
                
                Menu m = new Menu(tenMon, donGia, loaiMon);
                arrMon.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
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
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
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
            System.out.println(e.getMessage());
            System.out.println("Lỗi update");
        } finally {
            if (!check) {
                System.out.println("Không tìm thấy phần tử cần chỉnh sửa !!");
            } else {
                System.out.println("Sửa thành công !!");
            }
        }
    }
    // END TB MON
}

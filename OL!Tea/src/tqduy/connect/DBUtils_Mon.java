/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tqduy.bean.Menu;
import tqduy.bean.Mon;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Mon {
    
    // TABLE MON
    public static ArrayList<Mon> getList() throws SQLException {
        ArrayList<Mon> arrMon = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListMon}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_MON + "";
//
//        ResultSet res = query(sql);

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
    
    public static ArrayList<Menu> getListMenu() throws SQLException {
        ArrayList<Menu> arrMon = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListMenu}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT dbo.Mon.idMon, dbo.Mon.tenMon, dbo.Mon.donGia, dbo.LoaiMon.loaiMon, dbo.Mon.isActive FROM dbo.Mon JOIN dbo.LoaiMon ON LoaiMon.id = Mon.idLoaiMon";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idMon = res.getInt("idMon");
                String tenMon = res.getNString("tenMon");
                int donGia = res.getInt("donGia");
                String loaiMon = res.getNString("loaiMon");
                boolean isActive = res.getBoolean("isActive");
                
                Menu m = new Menu(tenMon, donGia, loaiMon, isActive);
                m.setIdMon(idMon);
                arrMon.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrMon;
        }
    }
    
    public static ArrayList<Mon> getMon(int idLM) throws SQLException {
        ArrayList<Mon> arrMon = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getMonById (?)}");
        command.setInt(1, idLM);

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM " + DBUtils_LoaiMon.TB_MON + " WHERE idLoaiMon="+idLM+"";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int id = res.getInt("idMon");
                String loaiMon = res.getString("tenMon");
                int donGia = res.getInt("donGia");
                int idLoaiMon = res.getInt("idLoaiMon");
                boolean isActive = res.getBoolean("isActive");
                
                Mon m = new Mon(id, loaiMon, donGia, idLoaiMon);
                m.setIsActive(isActive);
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
    
    public static void update(int id, boolean active) {
        int check = 0;
        if(active) check = 1;
        execute("UPDATE dbo.Mon SET isActive = "+check+" WHERE idMon = "+id+"");
        System.out.println("Update thành công !!");
    }
    // END TB MON
}

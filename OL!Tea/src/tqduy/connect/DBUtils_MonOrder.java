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
import tqduy.bean.MonOrder;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_MonOrder {
    
    // TABLE MON
    public static ArrayList<MonOrder> getList() throws SQLException {
        ArrayList<MonOrder> arrMon = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListMonOrder}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT dbo.Mon.idMon, dbo.Mon.tenMon, dbo.Mon.donGia, dbo.LoaiMon.loaiMon, dbo.MonOrder.soLuong " +
//"FROM dbo.Mon JOIN dbo.LoaiMon ON LoaiMon.id = Mon.idLoaiMon JOIN dbo.MonOrder ON MonOrder.idMon = dbo.Mon.idMon";
//
//        ResultSet res = query(sql);
        
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
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
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

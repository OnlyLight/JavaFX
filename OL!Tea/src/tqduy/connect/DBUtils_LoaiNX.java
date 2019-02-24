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
import tqduy.bean.InsertNX;
import tqduy.bean.LoaiNX;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;
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
    
    // TABLE LOAI NX
    public static ArrayList<LoaiNX> getList() {
        ArrayList<LoaiNX> arrLoaiMon = new ArrayList<>();
        
//        CallableStatement command = con.prepareCall("{call pr_getListLoaiNX}");
//
//        ResultSet res = command.executeQuery();
        
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
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrLoaiMon;
        }
    }
    
    public static ArrayList<LoaiNX> getListForName(String ten) throws SQLException {
        ArrayList<LoaiNX> arrLoaiNX = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListForNameLoaiNX (?)}");
        command.setString(1, ten);

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT dbo.LoaiNX.tenLoaiNX FROM dbo.LoaiNX JOIN dbo.Nhap ON Nhap.idLoaiNX = LoaiNX.idLoaiNX WHERE dbo.Nhap.tenSpNhap = '"+ten+"'";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenLoaiNX = res.getString("tenLoaiNX");
                int idLoaiNX = res.getInt("idLoaiNX");
                int price = res.getInt("price");
                LoaiNX lnx = new LoaiNX();
                lnx.setIdLoaiNX(idLoaiNX);
                lnx.setTenLoaiNX(tenLoaiNX);
                lnx.setPrice(price);
                arrLoaiNX.add(lnx);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrLoaiNX;
        }
    }
    
    public static ArrayList<InsertNX> getListNX() throws SQLException {
        System.out.println("Hello !!!");
        ArrayList<InsertNX> arrLoaiMon = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListNX}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT dbo.LoaiNX.tenLoaiNX, dbo.DVT.DVT FROM dbo.LoaiNX JOIN dbo.DVT ON DVT.idDVT = LoaiNX.idDVT";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenLoaiNX = res.getString("tenLoaiNX");
                String dvt = res.getString("DVT");
                int id = res.getInt("idLoaiNX");
                int price = res.getInt("price");
                InsertNX in = new InsertNX(tenLoaiNX, dvt, id, price);
                System.out.println("NX: " + in);
                arrLoaiMon.add(in);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrLoaiMon;
        }
    }
    
    public static void insert(String tenLoai, int dvt, int price) {
        execute("INSERT INTO " + DBUtils_LoaiNX.TB_LOAINX + "(tenLoaiNX, idDVT, price) VALUES ( N'"+ tenLoai +"', "+dvt+", "+price+" )");
        System.out.println("Chèn thành công !!");
    }
    
    public static void delete(int id) {
        execute("DELETE FROM " + DBUtils_LoaiNX.TB_NHAP + " WHERE idLoaiNX = " + id + "");
        execute("DELETE FROM " + DBUtils_LoaiNX.TB_XUAT + " WHERE idLoaiNX = " + id + "");
        execute("DELETE FROM " + DBUtils_LoaiNX.TB_LOAINX + " WHERE idLoaiNX = " + id + "");
    }
}

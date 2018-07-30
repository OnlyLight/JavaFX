/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tqduy.bean.InsertNX;
import tqduy.bean.LoaiNX;
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
    public final static String USER_NAME = "duy";
    public final static String PASSWORD = "1234";
    
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
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrLoaiMon;
        }
    }
    
    public static ArrayList<LoaiNX> getListForName(String ten) {
        ArrayList<LoaiNX> arrLoaiNX = new ArrayList<>();
        
        String sql = "SELECT dbo.LoaiNX.tenLoaiNX FROM dbo.LoaiNX JOIN dbo.Nhap ON Nhap.idLoaiNX = LoaiNX.idLoaiNX WHERE dbo.Nhap.tenSpNhap = '"+ten+"'";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenLoaiNX = res.getString("tenLoaiNX");
                
                LoaiNX lnx = new LoaiNX();
                lnx.setTenLoaiNX(tenLoaiNX);
                arrLoaiNX.add(lnx);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrLoaiNX;
        }
    }
    
    public static ArrayList<InsertNX> getListNX() {
        System.out.println("Hello !!!");
        ArrayList<InsertNX> arrLoaiMon = new ArrayList<>();
        
        String sql = "SELECT dbo.LoaiNX.tenLoaiNX, dbo.DVT.DVT FROM dbo.LoaiNX JOIN dbo.DVT ON DVT.idDVT = LoaiNX.idDVT";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenLoaiNX = res.getString("tenLoaiNX");
                String dvt = res.getString("DVT");
                
                InsertNX in = new InsertNX(tenLoaiNX, dvt);
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
    
    public static void insert(String tenLoai, int dvt) {
        execute("INSERT INTO " + DBUtils_LoaiNX.TB_LOAINX + "(tenLoaiNX, idDVT) VALUES ( N'"+ tenLoai +"', "+dvt+" )");
        System.out.println("Chèn thành công !!");
    }
}

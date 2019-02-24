/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import tqduy.bean.LoaiNX;
import tqduy.bean.Xuat;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Xuat {
    
    // TABLE Xuat
    public static ArrayList<Xuat> getList() throws SQLException {
        ArrayList<Xuat> arrXuat = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListXuat}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_XUAT + "";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idXuat = res.getInt("idXuat");
                String tenSpXuat = res.getString("tenSpXuat");
                int idLoaiNX = res.getInt("idLoaiNX");
                int soLuong = res.getInt("soLuong");
                Date date = res.getDate("ngayXuat");
                
                Xuat x = new Xuat(idXuat, tenSpXuat, idLoaiNX, soLuong, date);
                arrXuat.add(x);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrXuat;
        }
    }
    
    public static int getXuat(int dayNeed) throws SQLException {
        int sL = 0;
        
        CallableStatement command = con.prepareCall("{call pr_getXuat (?)}");
        command.setInt(1, dayNeed);

        ResultSet res = command.executeQuery();
//        String sql = "SELECT dbo.Xuat.soLuong FROM dbo.Xuat WHERE YEAR(dbo.Xuat.ngayXuat) = "+dayNeed+"";
//
//        System.out.println("SQL: " + sql);
//        ResultSet res = query(sql);

        try {
            if(res.next()) {
                int soLuong = res.getInt("soLuong");
                
                sL = soLuong;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return sL;
        }
    }
    
    public static ArrayList<Xuat> getListForName(String ten) throws SQLException {
        ArrayList<Xuat> arrLoaiNX = new ArrayList<>();
        
        String sql = "SELECT dbo.Xuat.tenSpXuat FROM dbo.Xuat JOIN dbo.LoaiNX ON LoaiNX.idLoaiNX = Xuat.idLoaiNX WHERE dbo.LoaiNX.tenLoaiNX = '"+ten+"'";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenLoaiNX = res.getString("tenSpXuat");
                
                Xuat x = new Xuat();
                x.setTenSpXuat(tenLoaiNX);
                arrLoaiNX.add(x);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrLoaiNX;
        }
    }
    
    public static void delete(int id) {
        execute("DELETE FROM " + DBUtils_LoaiNX.TB_XUAT + " WHERE idXuat = " + id + "");
        System.out.println("Delete Success");
    }
    
    public static void main(String[] args) {
        insert(5, 1, LocalDate.now());
    }
    
    public static void insert(int idLoai, int soLuong, LocalDate ngayXuat) {
        String sql = "INSERT INTO dbo.Xuat " +
"        ( idLoaiNX , " +
"          soLuong , " +
"          ngayXuat " +
"        ) " +
"VALUES  ( "+idLoai+" , " +
"          "+soLuong+" , " +
"          CONVERT(DATE, '"+ngayXuat+"') " +
"        )";
        execute(sql);
        System.out.println(sql);
//        System.out.println("Chèn thành công !!");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import tqduy.bean.Xuat;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Xuat {
    
    // TABLE Xuat
    public static ArrayList<Xuat> getList() {
        ArrayList<Xuat> arrXuat = new ArrayList<>();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_XUAT + "";

        ResultSet res = query(sql);

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
    
    public static int getXuat(int dayNeed) {
        int sL = 0;
        String sql = "SELECT dbo.Xuat.soLuong FROM dbo.Xuat WHERE YEAR(dbo.Xuat.ngayXuat) = "+dayNeed+"";

        System.out.println("SQL: " + sql);
        ResultSet res = query(sql);

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
    
    public static void delete(int id) {
        execute("DELETE FROM " + DBUtils_LoaiNX.TB_XUAT + " WHERE idXuat = " + id + "");
        System.out.println("Delete Success");
    }
    
    public static void insert(String tenSp, int idLoai, int soLuong, LocalDate ngayXuat) {
        execute("INSERT INTO dbo.Xuat " +
"        ( tenSpXuat , " +
"          idLoaiNX , " +
"          soLuong , " +
"          ngayXuat " +
"        ) " +
"VALUES  ( N'"+tenSp+"' , " +
"          "+idLoai+" , " +
"          "+soLuong+" , " +
"          CONVERT(DATE, '"+ngayXuat+"') " +
"        )");
        System.out.println("Chèn thành công !!");
    }
}

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
import tqduy.bean.TKTable;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_TK {
    
    public static ArrayList<TKTable> getList() throws SQLException {
        ArrayList<TKTable> arrTK = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListTK}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT dbo.Nhap.tenSpNhap, dbo.Nhap.donGia, dbo.Nhap.soLuong, dbo.Nhap.ngayNhap, dbo.Xuat.tenSpXuat, dbo.Xuat.soLuong, dbo.Xuat.ngayXuat, dbo.LoaiNX.tenLoaiNX " +
//"FROM dbo.Nhap JOIN dbo.LoaiNX ON LoaiNX.idLoaiNX = Nhap.idLoaiNX JOIN dbo.Xuat ON Xuat.idLoaiNX = LoaiNX.idLoaiNX WHERE dbo.Nhap.tenSpNhap LIKE dbo.Xuat.tenSpXuat AND dbo.Nhap.idLoaiNX = dbo.Xuat.idLoaiNX";
//        System.out.println("SQL: " + sql);
//        
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                String tenSp = res.getString("tenSpNhap");
                int donGia = res.getInt("donGia");
                int soLuongNhap = res.getInt("soLuong");
                Date ngayNhap = res.getDate("ngayNhap");
                int soLuongXuat = res.getInt("soLuong");
                Date ngayXuat = res.getDate("ngayXuat");
                String tenLoai = res.getString("tenLoaiNX");
                
                TKTable tk = new TKTable(tenSp, donGia, soLuongNhap, ngayNhap, soLuongXuat, ngayXuat, tenLoai);
                arrTK.add(tk);
            }
        } catch (SQLException e) {
            e.getStackTrace();
            System.out.println("Xin nhap lai !!");
        }
        return arrTK;
    }
    
    public static ArrayList<TKTable> searchTK(LocalDate dayStartNhap, LocalDate dayFinishNhap, LocalDate dayStartXuat, LocalDate dayFinishXuat, String loai) {
        ArrayList<TKTable> arrTK = new ArrayList<>();
        String dieuKien = " WHERE 1 = 1";
        
        if(dayStartNhap != null && dayFinishNhap != null) dieuKien += " AND "+DBUtils_LoaiNX.TB_NHAP+".ngayNhap BETWEEN '"+dayStartNhap+"' AND '"+dayFinishNhap+"'";
        if(dayStartXuat != null && dayFinishXuat != null) dieuKien += " AND "+DBUtils_LoaiNX.TB_XUAT+".ngayXuat BETWEEN '"+dayStartXuat+"' AND '"+dayFinishXuat+"'";
        if(loai != null) dieuKien += " AND "+DBUtils_LoaiNX.TB_LOAINX+".tenLoaiNX LIKE '"+loai+"'";
        
        String sql = "SELECT dbo.Nhap.tenSpNhap, dbo.Nhap.donGia, dbo.Nhap.soLuong, dbo.Nhap.ngayNhap, dbo.Xuat.tenSpXuat, dbo.Xuat.soLuong, dbo.Xuat.ngayXuat, dbo.LoaiNX.tenLoaiNX " +
"FROM dbo.Nhap JOIN dbo.LoaiNX ON LoaiNX.idLoaiNX = Nhap.idLoaiNX JOIN dbo.Xuat ON Xuat.idLoaiNX = LoaiNX.idLoaiNX "
                + ""+dieuKien+"";
        System.out.println("SQL: " + sql);
        
        ResultSet res = query(sql);

        try {
            if (res != null) {
                while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                    String tenSp = res.getString("tenSpNhap");
                    int donGia = res.getInt("donGia");
                    int soLuongNhap = res.getInt("soLuong");
                    Date ngayNhap = res.getDate("ngayNhap");
                    int soLuongXuat = res.getInt("soLuong");
                    Date ngayXuat = res.getDate("ngayXuat");
                    String tenLoai = res.getString("tenLoaiNX");

                    TKTable tk = new TKTable(tenSp, donGia, soLuongNhap, ngayNhap, soLuongXuat, ngayXuat, tenLoai);
                    arrTK.add(tk);
                }
            } else {
                System.out.println("res NULL");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        }
        return arrTK;
    }
}

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
import tqduy.bean.NhanVien;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_NhanVien {
    public static ArrayList<NhanVien> getList() throws SQLException {
        ArrayList<NhanVien> arrNV = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListNhanVien}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT dbo.NhanVien.idNV, dbo.NhanVien.userName, dbo.NhanVien.passWord, dbo.Role.roleName, dbo.NhanVien.isActive FROM dbo.NhanVien JOIN dbo.Role ON Role.idRole = NhanVien.role";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idNV = res.getInt("idNV");
                String userName = res.getString("userName");
                String passWord = res.getString("passWord");
                String roleName = res.getString("roleName");
                boolean active = res.getBoolean("isActive");
                
                NhanVien nv = new NhanVien(userName, roleName, active);
                nv.setIdNV(idNV);
                nv.setUserName(userName);
                nv.setPassWord(passWord);
                arrNV.add(nv);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrNV;
        }
    }
    
    public static ArrayList<NhanVien> getListForCheck() throws SQLException {
        ArrayList<NhanVien> arrNV = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListForCheckNhanVien}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM dbo.NhanVien";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idNV = res.getInt("idNV");
                String userName = res.getString("userName");
                String passWord = res.getString("passWord");
                int idRoleName = res.getInt("role");
                boolean active = res.getBoolean("isActive");
                
                NhanVien nv = new NhanVien();
                nv.setIdNV(idNV);
                nv.setUserName(userName);
                nv.setPassWord(passWord);
                nv.setIdRoleName(idRoleName);
                nv.setActive(active);
                arrNV.add(nv);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrNV;
        }
    }
    
    public static void insert(String userName, String passWord, int indexRole) {
        execute("INSERT INTO dbo.NhanVien (userName, passWord, role) VALUES ( '"+ userName +"', '"+ passWord +"', "+indexRole+" )");
        System.out.println("Chèn thành công !!");
    }
    
    public static void update(int idNV, boolean active) {
        int check = 0;
        if(active) check = 1;
        execute("UPDATE dbo.NhanVien SET isActive = "+check+" WHERE idNV = "+idNV+"");
        System.out.println("Update Sucess !!");
    }
}

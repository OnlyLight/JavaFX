/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tqduy.bean.Role;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Role {
    public static ArrayList<Role> getList() {
        ArrayList<Role> arrRole = new ArrayList<>();
        
        String sql = "SELECT * FROM dbo.Role";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idNhap = res.getInt("idRole");
                String roleName = res.getString("roleName");
                boolean active = res.getBoolean("Active");
                
                Role role = new Role(idNhap, roleName, active);
                arrRole.add(role);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrRole;
        }
    }
    
    public static void insert(String roleName) {
        execute("INSERT INTO dbo.Role (roleName) VALUES ( N'"+ roleName +"' )");
        System.out.println("Chèn thành công !!");
    }
    
    public static void update(int idRole, boolean active) {
        int check = 0;
        if(active) check = 1;
        execute("UPDATE dbo.Role SET Active = "+check+" WHERE idRole = "+idRole+"");
        System.out.println("Update Sucess !!");
    }
}

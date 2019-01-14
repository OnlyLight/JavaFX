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
import tqduy.bean.Member;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_Member {
    public static ArrayList<Member> getList() throws SQLException {
        ArrayList<Member> arrMember = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListMember}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM dbo.Member";
//
//        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idMember = res.getInt("idMember");
                String tenLoaiMember = res.getString("tenLoaiMember");
                int giamGia = res.getInt("giamGia");
                boolean active = res.getBoolean("active");
                
                Member m = new Member(idMember, tenLoaiMember, giamGia, active);
                arrMember.add(m);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrMember;
        }
    }
    
    public static void insert(String name, int discount) {
        execute("INSERT INTO dbo.Member (tenLoaiMember, giamGia) VALUES ( N'"+ name +"', "+discount+" )");
        System.out.println("Chèn thành công !!");
    }
    
    public static void update(int id, boolean active) {
        int check = 0;
        if(active) check = 1;
        execute("UPDATE dbo.Member SET active = "+check+" WHERE idMember = "+id+"");
        System.out.println("Update Sucess !!");
    }
}

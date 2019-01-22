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
import tqduy.bean.CusMember;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_CusMember {
    public static ArrayList<CusMember> getList() throws SQLException {
        ArrayList<CusMember> arrCusMem = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListCusMember}");
            
        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT dbo.NhanVien.idNV, dbo.NhanVien.userName, dbo.Role.roleName, dbo.Customer.tenCus, dbo.Customer.sdt, dbo.Member.idMember, dbo.Member.tenLoaiMember, dbo.Customer.ngayLap FROM dbo.Member JOIN dbo.Customer ON Customer.idMember = Member.idMember JOIN dbo.DKMember ON DKMember.idCustomer = Customer.idCustomer JOIN dbo.NhanVien ON dbo.DKMember.idNhanVien = dbo.NhanVien.idNV JOIN dbo.Role ON Role.idRole = NhanVien.role";
//
//        ResultSet res = query(sql);
//        System.out.println("SQL: " + sql);
        
        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idNV = res.getInt("idNV");
                String tenNhanVien = res.getString("userName");
                String roleName = res.getString("roleName");
                String tenCus = res.getString("tenCus");
                String sdt = res.getString("sdt");
                int idMember = res.getInt("idMember");
                String loaiMember = res.getString("tenLoaiMember");
                Date ngayLap = res.getDate("ngayLap");
                
                CusMember cm = new CusMember(tenNhanVien, roleName, tenCus, sdt, loaiMember, ngayLap);
                cm.setIdNV(idNV);
                cm.setIdMember(idMember);
                arrCusMem.add(cm);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrCusMem;
        }
    }
    
    public static ArrayList<CusMember> getListForCheck() throws SQLException {
        ArrayList<CusMember> arrCusMem = new ArrayList<>();
        
        CallableStatement command = con.prepareCall("{call pr_getListForCheckCusMember}");

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM dbo.Customer";
//
//        ResultSet res = query(sql);
//        System.out.println("SQL: " + sql);
        
        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idCus = res.getInt("idCustomer");
                String tenCus = res.getString("tenCus");
                String sdt = res.getString("sdt");
                int idMember = res.getInt("idMember");
                Date ngayLap = res.getDate("ngayLap");
                
                CusMember cm = new CusMember();
                cm.setIdCus(idCus);
                cm.setTenCus(tenCus);
                cm.setSdt(sdt);
                cm.setIdMember(idMember);
                cm.setNgayLap(ngayLap);
                arrCusMem.add(cm);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrCusMem;
        }
    }
    
    public static void insert(String tenCus, String sdt, int idMember, LocalDate ngayDK) {
        execute("INSERT INTO dbo.Customer (tenCus, sdt, idMember, ngayLap) VALUES ( N'"+ tenCus +"', '"+sdt+"', "+idMember+", CONVERT(DATE, '"+ngayDK+"') )");
        System.out.println("Chèn thành công !!");
    }
    
    public static void delete(int id) {
        execute("DELETE FROM dbo.Customer WHERE idCustomer = " + id + "");
    }
}

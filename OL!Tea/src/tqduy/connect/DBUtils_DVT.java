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
import tqduy.bean.DVT;
import static tqduy.connect.DBUtils_LoaiMon.con;
import static tqduy.connect.DBUtils_LoaiMon.execute;
import static tqduy.connect.DBUtils_LoaiMon.query;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_DVT {
    // TABLE DVT
    public static ArrayList<DVT> getList() {
        ArrayList<DVT> arrDVT = new ArrayList<>();
        
//        CallableStatement command = con.prepareCall("{call pr_getListDVT}");
//
//        ResultSet res = command.executeQuery();
        
        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_DVT + "";

        ResultSet res = query(sql);

        try {
            while (res.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp.
                int idDVT = res.getInt("idDVT");
                String dVT = res.getString("DVT");
                
                DVT dvt = new DVT(idDVT, dVT);
                arrDVT.add(dvt);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return arrDVT;
        }
    }
    
    public static DVT getDVT(int id) throws SQLException {
        DVT dvt = new DVT();
        
        CallableStatement command = con.prepareCall("{call pr_getListDVTById (?)}");
        command.setInt(1, id);

        ResultSet res = command.executeQuery();
        
//        String sql = "SELECT * FROM " + DBUtils_LoaiNX.TB_DVT + " WHERE idDVT="+id+"";
//
//        ResultSet res = query(sql);

        try {
            int idDVT = res.getInt("idDVT");
            String dVT = res.getString("DVT");

            dvt = new DVT(idDVT, dVT);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Xin nhap lai !!");
        } finally {
            return dvt;
        }
    }
    
    public static void insert(String tenDVT) {
        execute("INSERT INTO dbo.DVT (DVT) VALUES ( N'"+ tenDVT +"' )");
        System.out.println("Chèn thành công !!");
    }
}

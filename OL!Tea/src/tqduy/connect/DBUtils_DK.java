/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.connect;

import static tqduy.connect.DBUtils_LoaiMon.execute;

/**
 *
 * @author QuangDuy
 */
public class DBUtils_DK {
    public static void insert(int idNV, int idCus) {
        execute("INSERT INTO dbo.DKMember (idNhanVien, idCustomer) VALUES ( "+idNV+", "+idCus+" )");
        System.out.println("Chèn thành công !!");
    }
    
    public static void delete(int id) {
        execute("DELETE FROM dbo.DKMember WHERE idDK = " + id + "");
    }
}

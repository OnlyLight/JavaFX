/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.bean;

import java.util.Date;

/**
 *
 * @author QuangDuy
 */
public class Bill {
    private int idBill;
    private int idNV;
    private String tenNV;
    private int tongTien;
    private Date ngayLap;

    public Bill() {
    }

    public Bill(int idBill, int idNV, int tongTien, Date ngayLap) {
        this.idBill = idBill;
        this.idNV = idNV;
        this.tongTien = tongTien;
        this.ngayLap = ngayLap;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    @Override
    public String toString() {
        return "Bill{" + "idBill=" + idBill + ", idNV=" + idNV + ", tongTien=" + tongTien + ", ngayLap=" + ngayLap + '}';
    }
}

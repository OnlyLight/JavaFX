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
public class NhapTable {
    private int id;
    private String tenSp;
    private String tenLoai;
    private String dvt;
    private int donGia;
    private int soLuong;
    private Date ngayNhap;

    public NhapTable() {
    }

    public NhapTable(int id, String tenSp, String tenLoai, String dvt, int donGia, int soLuong, Date ngayNhap) {
        this.id = id;
        this.tenSp = tenSp;
        this.tenLoai = tenLoai;
        this.dvt = dvt;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    @Override
    public String toString() {
        return "NhapTable{" + "tenSp=" + tenSp + ", tenLoai=" + tenLoai + ", dvt=" + dvt + ", donGia=" + donGia + ", soLuong=" + soLuong + ", ngayNhap=" + ngayNhap + '}';
    }
    
    
}

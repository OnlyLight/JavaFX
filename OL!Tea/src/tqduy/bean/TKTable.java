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
public class TKTable {
    private String tenSp;
    private int donGia;
    private int soLuongNhap;
    private Date ngayNhap;
    private int soLuongXuat;
    private Date ngayXuat;
    private String tenLoai;
    private int idLoaiNX;
    private int totalNX;
    private String dvt;

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    public String getDvt() {
        return dvt;
    }

    public void setIdLoaiNX(int idLoaiNX) {
        this.idLoaiNX = idLoaiNX;
    }

    public void setTotalNX(int totalNX) {
        this.totalNX = totalNX;
    }

    public int getIdLoaiNX() {
        return idLoaiNX;
    }

    public int getTotalNX() {
        return totalNX;
    }

    public TKTable() {
    }

    public TKTable(String tenSp, int donGia, int soLuongNhap, Date ngayNhap, int soLuongXuat, Date ngayXuat, String tenLoai, int id, int total, String dvt) {
        this.tenSp = tenSp;
        this.donGia = donGia;
        this.soLuongNhap = soLuongNhap;
        this.ngayNhap = ngayNhap;
        this.soLuongXuat = soLuongXuat;
        this.ngayXuat = ngayXuat;
        this.tenLoai = tenLoai;
        this.totalNX = total;
        this.idLoaiNX = id;
        this.dvt = dvt;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(int soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    @Override
    public String toString() {
        return "TKTable{" + "tenSp=" + tenSp + ", donGia=" + donGia + ", soLuongNhap=" + soLuongNhap + ", ngayNhap=" + ngayNhap + ", soLuongXuat=" + soLuongXuat + ", ngayXuat=" + ngayXuat + ", tenLoai=" + tenLoai + '}';
    }
}

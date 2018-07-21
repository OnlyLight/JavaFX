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
public class Nhap {
    private int idNhap;
    private String tenSpNhap;
    private int idLoaiNX;
    private int donGia;
    private int soLuong;
    private Date ngayNhap;

    public Nhap() {
    }

    public Nhap(int idNhap, String tenSpNhap, int idLoaiNX, int donGia, int soLuong, Date ngayNhap) {
        this.idNhap = idNhap;
        this.tenSpNhap = tenSpNhap;
        this.idLoaiNX = idLoaiNX;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.ngayNhap = ngayNhap;
    }

    public int getIdNhap() {
        return idNhap;
    }

    public void setIdNhap(int idNhap) {
        this.idNhap = idNhap;
    }

    public String getTenSpNhap() {
        return tenSpNhap;
    }

    public void setTenSpNhap(String tenSpNhap) {
        this.tenSpNhap = tenSpNhap;
    }

    public int getIdLoaiNX() {
        return idLoaiNX;
    }

    public void setIdLoaiNX(int idLoaiNX) {
        this.idLoaiNX = idLoaiNX;
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
        return "Nhap{" + "idNhap=" + idNhap + ", tenSpNhap=" + tenSpNhap + ", idLoaiNX=" + idLoaiNX + ", donGia=" + donGia + ", soLuong=" + soLuong + ", ngayNhap=" + ngayNhap + '}';
    }
}

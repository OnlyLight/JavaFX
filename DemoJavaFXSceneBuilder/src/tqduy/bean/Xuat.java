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
public class Xuat {
    private int idXuat;
    private String tenSpXuat;
    private int idLoaiNX;
    private int soLuong;
    private Date ngayXuat;

    public Xuat() {
    }

    public Xuat(int idXuat, String tenSpXuat, int idLoaiNX, int soLuong, Date ngayXuat) {
        this.idXuat = idXuat;
        this.tenSpXuat = tenSpXuat;
        this.idLoaiNX = idLoaiNX;
        this.soLuong = soLuong;
        this.ngayXuat = ngayXuat;
    }

    public int getIdXuat() {
        return idXuat;
    }

    public void setIdXuat(int idXuat) {
        this.idXuat = idXuat;
    }

    public String getTenSpXuat() {
        return tenSpXuat;
    }

    public void setTenSpXuat(String tenSpXuat) {
        this.tenSpXuat = tenSpXuat;
    }

    public int getIdLoaiNX() {
        return idLoaiNX;
    }

    public void setIdLoaiNX(int idLoaiNX) {
        this.idLoaiNX = idLoaiNX;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Date getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(Date ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    @Override
    public String toString() {
        return "Xuat{" + "idXuat=" + idXuat + ", tenSpXuat=" + tenSpXuat + ", idLoaiNX=" + idLoaiNX + ", soLuong=" + soLuong + ", ngayXuat=" + ngayXuat + '}';
    }
}

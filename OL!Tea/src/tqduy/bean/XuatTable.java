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
public class XuatTable {
    private int id;
    private String tenSp;
    private String tenLoai;
    private String dvt;
    private int soLuong;
    private Date ngayXuat;

    public XuatTable() {
    }

    public XuatTable(int id, String tenSp, String tenLoai, String dvt, int soLuong, Date ngayXuat) {
        this.id = id;
        this.tenSp = tenSp;
        this.tenLoai = tenLoai;
        this.dvt = dvt;
        this.soLuong = soLuong;
        this.ngayXuat = ngayXuat;
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
        return "XuatTable{" + "id=" + id + ", tenSp=" + tenSp + ", tenLoai=" + tenLoai + ", dvt=" + dvt + ", soLuong=" + soLuong + ", ngayXuat=" + ngayXuat + '}';
    }
}

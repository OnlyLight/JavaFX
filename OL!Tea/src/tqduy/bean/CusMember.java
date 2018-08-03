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
public class CusMember {
    private int idCus;
    private int idNV;
    private String tenNhanVien;
    private String vaiTro;
    private String tenCus;
    private String sdt;
    private int idMember;
    private String loaiMember;
    private Date ngayLap;

    public CusMember() {
    }

    public CusMember(String tenNhanVien, String vaiTro, String tenCus, String sdt, String loaiMember, Date ngayLap) {
        this.tenNhanVien = tenNhanVien;
        this.vaiTro = vaiTro;
        this.tenCus = tenCus;
        this.sdt = sdt;
        this.loaiMember = loaiMember;
        this.ngayLap = ngayLap;
    }

    public int getIdCus() {
        return idCus;
    }

    public void setIdCus(int idCus) {
        this.idCus = idCus;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    public int getIdMember() {
        return idMember;
    }

    public void setIdMember(int idMember) {
        this.idMember = idMember;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public String getTenCus() {
        return tenCus;
    }

    public void setTenCus(String tenCus) {
        this.tenCus = tenCus;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getLoaiMember() {
        return loaiMember;
    }

    public void setLoaiMember(String loaiMember) {
        this.loaiMember = loaiMember;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    @Override
    public String toString() {
        return "CusMember{" + "tenNhanVien=" + tenNhanVien + ", vaiTro=" + vaiTro + ", tenCus=" + tenCus + ", sdt=" + sdt + ", loaiMember=" + loaiMember + ", ngayLap=" + ngayLap + '}';
    }
}

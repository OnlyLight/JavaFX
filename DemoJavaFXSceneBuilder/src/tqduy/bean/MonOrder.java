/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tqduy.bean;


/**
 *
 * @author QuangDuy
 */
public class MonOrder {
    private int id;
    private int idMon;
    private String tenMon;
    private int donGia;
    private String loaiMon;
    private int soLuong;

    public MonOrder() {
    }

    public MonOrder(int id, String tenMon, int donGia, String loaiMon, int soLuong) {
        this.id = id;
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.loaiMon = loaiMon;
        this.soLuong = soLuong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public int getDonGia() {
        return donGia;
    }

    public void setDonGia(int donGia) {
        this.donGia = donGia;
    }

    public String getLoaiMon() {
        return loaiMon;
    }

    public void setLoaiMon(String loaiMon) {
        this.loaiMon = loaiMon;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
    }

    @Override
    public String toString() {
        return "MonOrder{" + "id=" + id + ", idMon=" + idMon + ", tenMon=" + tenMon + ", donGia=" + donGia + ", loaiMon=" + loaiMon + ", soLuong=" + soLuong + '}';
    }
}

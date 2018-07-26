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
public class Menu {
    private String tenMon;
    private int donGia;
    private String loaiMon;

    public Menu() {
    }

    public Menu(String tenMon, int donGia, String loaiMon) {
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.loaiMon = loaiMon;
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

    @Override
    public String toString() {
        return "Menu{" + "tenMon=" + tenMon + ", donGia=" + donGia + ", loaiMon=" + loaiMon + '}';
    }
}

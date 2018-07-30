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
    private int idMon;
    private String tenMon;
    private int donGia;
    private String loaiMon;
    private boolean isActive;

    public Menu() {
    }

    public Menu(String tenMon, int donGia, String loaiMon, boolean isActive) {
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.loaiMon = loaiMon;
        this.isActive = isActive;
    }

    public int getIdMon() {
        return idMon;
    }

    public void setIdMon(int idMon) {
        this.idMon = idMon;
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

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Menu{" + "idMon=" + idMon + ", tenMon=" + tenMon + ", donGia=" + donGia + ", loaiMon=" + loaiMon + ", isActive=" + isActive + '}';
    }
}

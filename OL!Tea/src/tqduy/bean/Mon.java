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
public class Mon {
    private int idMon;
    private String tenMon;
    private int donGia;
    private int idLoaiMon;
    private boolean isActive;

    public Mon() {
    }

    public Mon(int idMon, String tenMon, int donGia, int idLoaiMon) {
        this.idMon = idMon;
        this.tenMon = tenMon;
        this.donGia = donGia;
        this.idLoaiMon = idLoaiMon;
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

    public int getIdLoaiMon() {
        return idLoaiMon;
    }

    public void setIdLoaiMon(int idLoaiMon) {
        this.idLoaiMon = idLoaiMon;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Mon{" + "idMon=" + idMon + ", tenMon=" + tenMon + ", donGia=" + donGia + ", idLoaiMon=" + idLoaiMon + ", isActive=" + isActive + '}';
    }
}

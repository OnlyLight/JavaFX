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
public class LoaiNX {
    private int idLoaiNX;
    private String tenLoaiNX;
    private int idDVT;

    public LoaiNX() {
    }

    public LoaiNX(int idLoaiNX, String tenLoaiNX, int idDVT) {
        this.idLoaiNX = idLoaiNX;
        this.tenLoaiNX = tenLoaiNX;
        this.idDVT = idDVT;
    }

    public int getIdLoaiNX() {
        return idLoaiNX;
    }

    public void setIdLoaiNX(int idLoaiNX) {
        this.idLoaiNX = idLoaiNX;
    }

    public String getTenLoaiNX() {
        return tenLoaiNX;
    }

    public void setTenLoaiNX(String tenLoaiNX) {
        this.tenLoaiNX = tenLoaiNX;
    }

    public int getIdDVT() {
        return idDVT;
    }

    public void setIdDVT(int idDVT) {
        this.idDVT = idDVT;
    }

    @Override
    public String toString() {
        return "LoaiNX{" + "idLoaiNX=" + idLoaiNX + ", tenLoaiNX=" + tenLoaiNX + ", idDVT=" + idDVT + '}';
    }
}

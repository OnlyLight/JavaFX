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
public class LoaiMon {
    private int id;
    private String loaiMon;

    public LoaiMon() {
    }

    public LoaiMon(int id, String loaiMon) {
        this.id = id;
        this.loaiMon = loaiMon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiMon() {
        return loaiMon;
    }

    public void setLoaiMon(String loaiMon) {
        this.loaiMon = loaiMon;
    }

    @Override
    public String toString() {
        return "LoaiMon{" + "id=" + id + ", loaiMon=" + loaiMon + '}';
    }
}

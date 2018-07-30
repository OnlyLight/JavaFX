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
public class InsertNX {
    private String tenLoaiNX;
    private String dvt; 

    public InsertNX() {
    }

    public InsertNX(String tenLoaiNX, String dvt) {
        this.tenLoaiNX = tenLoaiNX;
        this.dvt = dvt;
    }

    public String getTenLoaiNX() {
        return tenLoaiNX;
    }

    public void setTenLoaiNX(String tenLoaiNX) {
        this.tenLoaiNX = tenLoaiNX;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    @Override
    public String toString() {
        return "InsertNX{" + "tenLoaiNX=" + tenLoaiNX + ", dvt=" + dvt + '}';
    }
}

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
public class DVT {
    private int idDVT;
    private String dvt;

    public DVT() {
    }

    public DVT(int idDVT, String dvt) {
        this.idDVT = idDVT;
        this.dvt = dvt;
    }

    public int getIdDVT() {
        return idDVT;
    }

    public void setIdDVT(int idDVT) {
        this.idDVT = idDVT;
    }

    public String getDvt() {
        return dvt;
    }

    public void setDvt(String dvt) {
        this.dvt = dvt;
    }

    @Override
    public String toString() {
        return "DVT{" + "idDVT=" + idDVT + ", dvt=" + dvt + '}';
    }
}

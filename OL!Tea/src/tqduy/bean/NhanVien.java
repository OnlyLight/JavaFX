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
public class NhanVien {
    private int idNV;
    private String userName;
    private String passWord;
    private String roleName;
    private boolean active;

    public NhanVien() {
    }

    public NhanVien(String userName, String roleName, boolean active) {
        this.userName = userName;
        this.roleName = roleName;
        this.active = active;
    }

    public int getIdNV() {
        return idNV;
    }

    public void setIdNV(int idNV) {
        this.idNV = idNV;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "idNV=" + idNV + ", userName=" + userName + ", passWord=" + passWord + ", roleName=" + roleName + ", active=" + active + '}';
    }
}

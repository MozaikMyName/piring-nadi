package uasGizi.user;

import uasGizi.BaseEntity;

public class User extends BaseEntity {
    private String username;
    private String password;
    private String nama;
    private int usia;
    private float beratKg;
    private float tinggiCm;
    private String gender;
    private String aktivitas;
    private String tujuan;
    private float targetKalori;
    
    public User() {}

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the usia
     */
    public int getUsia() {
        return usia;
    }

    /**
     * @param usia the usia to set
     */
    public void setUsia(int usia) {
        this.usia = usia;
    }

    /**
     * @return the beratKg
     */
    public float getBeratKg() {
        return beratKg;
    }

    /**
     * @param beratKg the beratKg to set
     */
    public void setBeratKg(float beratKg) {
        this.beratKg = beratKg;
    }

    /**
     * @return the tinggiCm
     */
    public float getTinggiCm() {
        return tinggiCm;
    }

    /**
     * @param tinggiCm the tinggiCm to set
     */
    public void setTinggiCm(float tinggiCm) {
        this.tinggiCm = tinggiCm;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the aktivitas
     */
    public String getAktivitas() {
        return aktivitas;
    }

    /**
     * @param aktivitas the aktivitas to set
     */
    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }
    
    @Override
    public String getInfo() {
        return "User: " + nama + " | Username: " + username;
    }

    /**
     * @return the tujuan
     */
    public String getTujuan() {
        return tujuan;
    }

    /**
     * @param tujuan the tujuan to set
     */
    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    /**
     * @return the targetKalori
     */
    public float getTargetKalori() {
        return targetKalori;
    }

    /**
     * @param targetKalori the targetKalori to set
     */
    public void setTargetKalori(float targetKalori) {
        this.targetKalori = targetKalori;
    }
}

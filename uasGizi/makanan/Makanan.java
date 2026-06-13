package uasGizi.makanan;

import uasGizi.BaseEntity;

public class Makanan extends BaseEntity {
    private String nama;
    private float kalori;
    private float protein;
    private float lemak;
    private float karbohidrat;
    private String status;
    private String ditambahOleh;
    private int userId;
    private String alasanTolak;
    
    public Makanan() {}

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
     * @return the kalori
     */
    public float getKalori() {
        return kalori;
    }

    /**
     * @param kalori the kalori to set
     */
    public void setKalori(float kalori) {
        this.kalori = kalori;
    }

    /**
     * @return the protein
     */
    public float getProtein() {
        return protein;
    }

    /**
     * @param protein the protein to set
     */
    public void setProtein(float protein) {
        this.protein = protein;
    }

    /**
     * @return the lemak
     */
    public float getLemak() {
        return lemak;
    }

    /**
     * @param lemak the lemak to set
     */
    public void setLemak(float lemak) {
        this.lemak = lemak;
    }

    /**
     * @return the karbohidrat
     */
    public float getKarbohidrat() {
        return karbohidrat;
    }

    /**
     * @param karbohidrat the karbohidrat to set
     */
    public void setKarbohidrat(float karbohidrat) {
        this.karbohidrat = karbohidrat;
    }
    
    @Override
    public String getInfo() {
        return "Makanan: " + nama + " | Kalori: " + kalori + " kkal";
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the ditambahOleh
     */
    public String getDitambahOleh() {
        return ditambahOleh;
    }

    /**
     * @param ditambahOleh the ditambahOleh to set
     */
    public void setDitambahOleh(String ditambahOleh) {
        this.ditambahOleh = ditambahOleh;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the alasanTolak
     */
    public String getAlasanTolak() {
        return alasanTolak;
    }

    /**
     * @param alasanTolak the alasanTolak to set
     */
    public void setAlasanTolak(String alasanTolak) {
        this.alasanTolak = alasanTolak;
    }
}

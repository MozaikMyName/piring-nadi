package uasGizi;

import java.sql.Date;

public class Log {
    private int id;
    private int userId;
    private int makananId;
    private Date tanggal;
    private float beratGram;
    
    public Log() {}

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
     * @return the makananId
     */
    public int getMakananId() {
        return makananId;
    }

    /**
     * @param makananId the makananId to set
     */
    public void setMakananId(int makananId) {
        this.makananId = makananId;
    }

    /**
     * @return the tanggal
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the beratGram
     */
    public float getBeratGram() {
        return beratGram;
    }

    /**
     * @param beratGram the beratGram to set
     */
    public void setBeratGram(float beratGram) {
        this.beratGram = beratGram;
    }

    
}

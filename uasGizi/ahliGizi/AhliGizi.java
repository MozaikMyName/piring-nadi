package uasGizi.ahliGizi;

import uasGizi.BaseEntity;

public class AhliGizi extends BaseEntity {

    private String username;
    private String password;
    private String nama;
    private String spesialisasi;
    
    public AhliGizi() {}

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
     * @return the spesialisasi
     */
    public String getSpesialisasi() {
        return spesialisasi;
    }

    /**
     * @param spesialisasi the spesialisasi to set
     */
    public void setSpesialisasi(String spesialisasi) {
        this.spesialisasi = spesialisasi;
    }
    
    @Override
    public String getInfo() {
        return " Ahli Gizi: " + getNama() + " | Spesialisasi " + getSpesialisasi();
    }
}

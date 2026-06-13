package uasGizi.admin;

import uasGizi.BaseEntity;

public class Admin extends BaseEntity {
    private String username;
    private String password;
    
    public Admin() {}

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
    
    @Override
    public String getInfo() {
        return "Admin: " + username;
    }
}

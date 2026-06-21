package uasGizi.admin;

import uasGizi.admin.Admin;
import java.sql.SQLException;

public interface AdminInterface {
    Admin getByUsername(String username) throws SQLException;
    void update(Admin a) throws SQLException;
}

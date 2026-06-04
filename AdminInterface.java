package uasGizi;

import java.sql.SQLException;

public interface AdminInterface {
    Admin getByUsername(String username) throws SQLException;
}

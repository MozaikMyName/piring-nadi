package uasGizi.ahliGizi;

import java.sql.SQLException;

public interface AhliGiziInterface {
    AhliGizi getByUsername(String username) throws SQLException;
}

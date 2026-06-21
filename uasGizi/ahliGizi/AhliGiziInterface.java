package uasGizi.ahliGizi;

import java.sql.SQLException;
import java.util.List;

public interface AhliGiziInterface {
    AhliGizi getByUsername(String username) throws SQLException;
    void update(AhliGizi ahliGizi) throws Exception;
    List<AhliGizi> getAll() throws Exception;
}

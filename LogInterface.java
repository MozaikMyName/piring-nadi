package uasGizi;

import java.sql.SQLException;
import java.util.List;

public interface LogInterface {
    Log insert(Log l) throws SQLException;
    List<Log> getByUserId(int UserId) throws SQLException;
    List<Log> getByTanggal(int UserId, String tanggal) throws SQLException;
    void delete(int id) throws SQLException;
}

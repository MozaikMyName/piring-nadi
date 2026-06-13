package uasGizi.user;

import java.sql.SQLException;
import java.util.List;

public interface UserInterface {
    User insert(User u) throws SQLException;
    User getByUsername(String username) throws SQLException;
    void update(User u) throws SQLException;
    void delete(int id) throws SQLException;
    List<User> getAll() throws SQLException;
}

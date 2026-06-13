package uasGizi.makanan;

import java.sql.SQLException;
import java.util.List;

public interface MakananInterface {
    Makanan insert(Makanan m) throws SQLException;
    List<Makanan> getAll()throws SQLException;
    void update(Makanan m) throws SQLException;
    void delete(int id) throws SQLException;
}

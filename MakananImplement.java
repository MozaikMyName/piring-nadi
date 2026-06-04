package uasGizi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MakananImplement implements MakananInterface {
    
    @Override
    public Makanan insert(Makanan m) throws SQLException{
        PreparedStatement st = DBConnection.getConnection().prepareStatement("INSERT INTO makanan VALUES(0,?,?,?,?,?)");
        st.setString(1, m.getNama());
        st.setFloat(2, m.getKalori());
        st.setFloat(3, m.getProtein());
        st.setFloat(4, m.getLemak());
        st.setFloat(5, m.getKarbohidrat());
        st.executeUpdate();
        DBConnection.conn.close();
        return m;
    }
    
    @Override
    public List<Makanan> getAll() throws SQLException {
        List<Makanan> list = new ArrayList<>();
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM makanan ORDER BY nama");
        while (rs.next()) {
            Makanan m = new Makanan();
            m.setId(rs.getInt("id"));
            m.setNama(rs.getString("nama"));
            m.setKalori(rs.getFloat("kalori"));
            m.setProtein(rs.getFloat("protein"));
            m.setLemak(rs.getFloat("lemak"));
            m.setKarbohidrat(rs.getFloat("karbohidrat"));
            list.add(m);
        }
        DBConnection.conn.close();
        return list;
    }
    
    @Override
    public void update(Makanan m) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement("UPDATE makanan SET nama=?, kalori=?, protein=?, lemak=?, karbohidrat=? WHERE id=?");
        st.setString(1, m.getNama());
        st.setFloat(2, m.getKalori());
        st.setFloat(3, m.getProtein());
        st.setFloat(4, m.getLemak());
        st.setFloat(5, m.getKarbohidrat());
        st.setInt(6, m.getId());
        st.executeUpdate();
        DBConnection.conn.close();
    }
    
    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement("DELETE FROM makanan WHERE id=?");
        st.setInt(1, id);
        st.executeUpdate();
        DBConnection.conn.close();
    }
}

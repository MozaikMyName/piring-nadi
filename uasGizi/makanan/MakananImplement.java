package uasGizi.makanan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uasGizi.DBConnection;

public class MakananImplement implements MakananInterface {
    
    @Override
    public Makanan insert(Makanan m) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "INSERT INTO makanan (nama, kalori, protein, lemak, karbohidrat, status, ditambah_oleh, user_id) " +
            "VALUES (?,?,?,?,?,?,?,?)");
        st.setString(1, m.getNama());
        st.setFloat(2, m.getKalori());
        st.setFloat(3, m.getProtein());
        st.setFloat(4, m.getLemak());
        st.setFloat(5, m.getKarbohidrat());
        st.setString(6, m.getStatus());
        st.setString(7, m.getDitambahOleh());
        st.setInt(8, m.getUserId());
        st.executeUpdate();
        DBConnection.conn.close();
        return m;
    }
    
    @Override
    public List<Makanan> getAll() throws SQLException {
        List<Makanan> list = new ArrayList<>();
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM makanan WHERE status='approved' ORDER BY id");
        while (rs.next()) {
            Makanan m = new Makanan();
            m.setId(rs.getInt("id"));
            m.setNama(rs.getString("nama"));
            m.setKalori(rs.getFloat("kalori"));
            m.setProtein(rs.getFloat("protein"));
            m.setLemak(rs.getFloat("lemak"));
            m.setKarbohidrat(rs.getFloat("karbohidrat"));
            m.setStatus(rs.getString("status"));
            list.add(m);
        }
        DBConnection.conn.close();
        return list;
    }
    
    @Override
    public void update(Makanan m) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
        "UPDATE makanan SET nama=?, kalori=?, protein=?, lemak=?, karbohidrat=? WHERE id=?");
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
    
    public List<Makanan> getAllPending() throws SQLException {
        List<Makanan> list = new ArrayList<>();
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM makanan WHERE status='pending' ORDER BY nama");
        while (rs.next()) {
            Makanan m = new Makanan();
            m.setId(rs.getInt("id"));
            m.setNama(rs.getString("nama"));
            m.setKalori(rs.getFloat("kalori"));
            m.setProtein(rs.getFloat("protein"));
            m.setLemak(rs.getFloat("lemak"));
            m.setKarbohidrat(rs.getFloat("karbohidrat"));
            m.setStatus(rs.getString("status"));
            m.setDitambahOleh(rs.getString("ditambah_oleh"));
            list.add(m);
        }
        DBConnection.conn.close();
        return list;
    }

    public void approve(int id, float kalori, float protein, float lemak, float karbo) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "UPDATE makanan SET status='approved', kalori=?, protein=?, lemak=?, karbohidrat=? WHERE id=?");
        st.setFloat(1, kalori);
        st.setFloat(2, protein);
        st.setFloat(3, lemak);
        st.setFloat(4, karbo);
        st.setInt(5, id);
        st.executeUpdate();
        DBConnection.conn.close();
    }

    public void tolak(int id, String alasan) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "UPDATE makanan SET status='pending', alasan_tolak=? WHERE id=?");
        st.setString(1, alasan);
        st.setInt(2, id);
        st.executeUpdate();
        DBConnection.conn.close();
    }
}

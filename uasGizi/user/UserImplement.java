package uasGizi.user;

import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uasGizi.DBConnection;

public class UserImplement implements UserInterface {
    
    @Override
    public User insert(User u) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "INSERT INTO user (username, password, nama, usia, berat_kg, tinggi_cm, gender, aktivitas, tujuan) " +
            "VALUES(?,?,?,?,?,?,?,?,?)");
        st.setString(1, u.getUsername());
        st.setString(2, u.getPassword());
        st.setString(3, u.getNama());
        st.setInt(4, u.getUsia());
        st.setFloat(5, u.getBeratKg());
        st.setFloat(6, u.getTinggiCm());
        st.setString(7, u.getGender());
        st.setString(8, u.getAktivitas());
        st.setString(9, u.getTujuan());
        st.executeUpdate();
        DBConnection.conn.close();
        return u;
    }
    
    @Override
    public User getByUsername(String username) throws SQLException {
        User u = null;
        PreparedStatement st = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE username=?");
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setNama(rs.getString("nama"));
            u.setUsia(rs.getInt("usia"));
            u.setBeratKg(rs.getFloat("berat_kg"));
            u.setTinggiCm(rs.getFloat("tinggi_cm"));
            u.setGender(rs.getString("gender"));
            u.setAktivitas(rs.getString("aktivitas"));
            u.setTujuan(rs.getString("tujuan"));
            u.setTargetKalori(rs.getFloat("target_kalori"));
            u.setCatatanAhliGizi(rs.getString("catatan_ahli_gizi"));
        }
        DBConnection.conn.close();
        return u;
    }
    
    
    @Override
    public User getById(int id) throws SQLException {
        User u = null;
        PreparedStatement st = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE id=?");
        st.setInt(1, id);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setNama(rs.getString("nama"));
            u.setBeratKg(rs.getFloat("berat_kg"));
            u.setTinggiCm(rs.getFloat("tinggi_cm"));
            u.setGender(rs.getString("gender"));
            u.setAktivitas(rs.getString("aktivitas"));
            u.setTujuan(rs.getString("tujuan"));
            u.setTargetKalori(rs.getFloat("target_kalori"));
            u.setCatatanAhliGizi(rs.getString("catatan_ahli_gizi"));
        }
        DBConnection.conn.close();
        return u;
    }
    
    @Override
    public void update(User u) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "UPDATE user SET nama=?, usia=?, berat_kg=?, tinggi_cm=?, gender=?, aktivitas=?, tujuan=?, target_kalori=? WHERE id=?");
        st.setString(1, u.getNama());
        st.setInt(2, u.getUsia());
        st.setFloat(3, u.getBeratKg());
        st.setFloat(4, u.getTinggiCm());
        st.setString(5, u.getGender());
        st.setString(6, u.getAktivitas());
        st.setString(7, u.getTujuan());
        st.setFloat(8, u.getTargetKalori());
        st.setInt(9, u.getId());
        st.executeUpdate();
        DBConnection.conn.close();
    }
    
    public void updateCatatanGizi(int userId, String catatan) throws SQLException {
        String sql = "UPDATE user SET catatan_ahli_gizi = ? WHERE id = ?";
        PreparedStatement st = DBConnection.getConnection().prepareStatement(sql);
        st.setString(1, catatan);
        st.setInt(2, userId);
        st.executeUpdate();
        DBConnection.conn.close();
    }
    
    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement("DELETE FROM user WHERE id=?");
        st.setInt(1, id);
        st.executeUpdate();
        DBConnection.conn.close();
    }
    
    @Override
    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        Statement st = DBConnection.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM user ORDER BY id");
        while (rs.next()) {
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setUsername(rs.getString("username"));
            u.setNama(rs.getString("nama"));
            u.setUsia(rs.getInt("usia"));
            u.setBeratKg(rs.getFloat("berat_kg"));
            u.setTinggiCm(rs.getFloat("tinggi_cm"));
            u.setGender(rs.getString("gender"));
            u.setAktivitas(rs.getString("aktivitas"));
            list.add(u);
        }
        DBConnection.conn.close();
        return list;
    }
}

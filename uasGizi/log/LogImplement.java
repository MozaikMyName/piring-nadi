package uasGizi.log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uasGizi.DBConnection;

public class LogImplement implements LogInterface {

    @Override
    public Log insert(Log l) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "INSERT INTO log_harian (user_id, makanan_id, tanggal, berat_gram, catatan_gizi) VALUES(?,?,?,?,NULL)");
        st.setInt(1, l.getUserId());
        st.setInt(2, l.getMakananId());
        st.setDate(3, l.getTanggal());
        st.setFloat(4, l.getBeratGram());
        st.executeUpdate();
        DBConnection.conn.close();
        return l;
    }

    @Override
    public List<Log> getByUserId(int userId) throws SQLException {
        List<Log> list = new ArrayList<>();
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "SELECT l.*, m.nama, m.kalori, m.protein, m.lemak, m.karbohidrat " +
            "FROM log_harian l JOIN makanan m ON l.makanan_id = m.id " +
            "WHERE l.user_id=? ORDER BY l.tanggal DESC");
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Log l = new Log();
            l.setId(rs.getInt("id"));
            l.setUserId(rs.getInt("user_id"));
            l.setMakananId(rs.getInt("makanan_id"));
            l.setTanggal(rs.getDate("tanggal"));
            l.setBeratGram(rs.getFloat("berat_gram"));
            list.add(l);
        }
        DBConnection.conn.close();
        return list;
    }

    @Override
    public List<Log> getByTanggal(int userId, String tanggal) throws SQLException {
        List<Log> list = new ArrayList<>();
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "SELECT l.*, m.nama, m.kalori, m.protein, m.lemak, m.karbohidrat " +
            "FROM log_harian l JOIN makanan m ON l.makanan_id = m.id " +
            "WHERE l.user_id=? AND l.tanggal=? ORDER BY l.id");
        st.setInt(1, userId);
        st.setString(2, tanggal);
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            Log l = new Log();
            l.setId(rs.getInt("id"));
            l.setUserId(rs.getInt("user_id"));
            l.setMakananId(rs.getInt("makanan_id"));
            l.setTanggal(rs.getDate("tanggal"));
            l.setBeratGram(rs.getFloat("berat_gram"));
            list.add(l);
        }
        DBConnection.conn.close();
        return list;
    }

    @Override
    public void delete(int id) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "DELETE FROM log_harian WHERE id=?");
        st.setInt(1, id);
        st.executeUpdate();
        DBConnection.conn.close();
    }
}
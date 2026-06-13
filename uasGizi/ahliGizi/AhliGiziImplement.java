package uasGizi.ahliGizi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uasGizi.DBConnection;

public class AhliGiziImplement implements AhliGiziInterface {

    @Override
    public AhliGizi getByUsername(String username) throws SQLException {
        AhliGizi ag = null;
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "SELECT * FROM ahli_gizi WHERE username=?");
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            ag = new AhliGizi();
            ag.setId(rs.getInt("id"));
            ag.setUsername(rs.getString("username"));
            ag.setPassword(rs.getString("password"));
            ag.setNama(rs.getString("nama"));
            ag.setSpesialisasi(rs.getString("spesialisasi"));
        }
        DBConnection.conn.close();
        return ag;
    }
    
}

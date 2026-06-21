package uasGizi.ahliGizi;

import uasGizi.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    
    @Override
    public void update(AhliGizi ahliGizi) throws Exception {
        String query = "UPDATE ahli_gizi SET username = ?, password = ?, nama = ?, spesialisasi = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, ahliGizi.getUsername());
            ps.setString(2, ahliGizi.getPassword());
            ps.setString(3, ahliGizi.getNama());
            ps.setString(4, ahliGizi.getSpesialisasi());
            ps.setInt(5, ahliGizi.getId());
            
            ps.executeUpdate();
        }
    }

    @Override
    public List<AhliGizi> getAll() throws Exception {
        return new ArrayList<>(); 
    }
    
}

package uasGizi.admin;

import uasGizi.admin.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uasGizi.DBConnection;

public class AdminImplement implements AdminInterface {
    @Override
    public Admin getByUsername(String username) throws SQLException {
        Admin a = null;
        PreparedStatement st = DBConnection.getConnection().prepareStatement("SELECT * FROM admin WHERE username=?");
        st.setString(1, username);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            a = new Admin();
            a.setId(rs.getInt("id"));
            a.setUsername(rs.getString("username"));
            a.setPassword(rs.getString("password"));
        }
        DBConnection.conn.close();
        return a;
    }
    
    @Override
    public void update(Admin a) throws SQLException {
        PreparedStatement st = DBConnection.getConnection().prepareStatement(
            "UPDATE admin SET username=?, password=? WHERE id=?");
        st.setString(1, a.getUsername());
        st.setString(2, a.getPassword());
        st.setInt(3, a.getId());
        st.executeUpdate();
        DBConnection.conn.close();
    }
}

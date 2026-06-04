package uasGizi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}

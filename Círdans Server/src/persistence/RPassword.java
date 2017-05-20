package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Password;
import util.DBUtil;

public class RPassword extends ARepository implements IRepository<Password, Long> {
    public RPassword() {
    }

    @Override
    public void save(Password entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO tbl_password " +
                    "(created_at, updated_at, iterations, password, salt)" +
                    " VALUE (?,?,?,?,?)";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            this.setTimestampOnCreate(st);
            st.setInt(3, entity.getIterations());
            st.setString(4, entity.getPassword());
            st.setString(5, entity.getSalt());
            int result = st.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(st);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
    }

    @Override
    public void update(Password entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try{
            String query = "UPDATE tbl_password SET " +
                    "updated_at = ?, iterations = ?, password = ?, salt = ? WHERE id = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            this.setTimestampOnUpdate(st);
            st.setInt(2, entity.getIterations());
            st.setString(3, entity.getPassword());
            st.setString(4, entity.getSalt());
            st.setLong(5, entity.getId());
            int result = st.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(st);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
    }

    @Override
    public void delete(Password entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try{
            String query = "DELETE FROM tbl_password WHERE id = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, entity.getId());
            int result = st.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(st);
            }
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
    }

    @Override
    public Password findById(Long id) {
        Connection conn = null;
        PreparedStatement st = null;
        Password pass = null;
        try{
            String query = "SELECT * FROM tbl_password WHERE id = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            while(result.next()){
                pass = new Password(
                        result.getLong("id"),
                        result.getDate("created_at"),
                        result.getDate("updated_at"),
                        result.getInt("iterations"),
                        result.getString("password"),
                        result.getString("salt")
                );
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return pass;
    }
    
    public Password findByHash(String hash){
        Connection conn = null;
        PreparedStatement st = null;
        Password pass = null;
        try{
            String query = "SELECT * FROM tbl_password WHERE password LIKE ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setString(1, hash);
            ResultSet result = st.executeQuery();
            while(result.next()){
                pass = new Password(
                        result.getLong("id"),
                        result.getDate("created_at"),
                        result.getDate("updated_at"),
                        result.getInt("iterations"),
                        result.getString("password"),
                        result.getString("salt")
                );
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return pass;
    }
    
    public Password findByProfileId(Long id){
        Connection conn = null;
        PreparedStatement st = null;
        Password pass = null;
        try{
            String query = "SELECT `tbl_password`.* FROM `tbl_password` " + 
                    "JOIN `tbl_profile` ON `tbl_profile`.`password_id` = `tbl_password`.`id` " +
                    "WHERE `tbl_profile`.id = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            while(result.next()){
                pass = new Password(
                        result.getLong("id"),
                        result.getDate("created_at"),
                        result.getDate("updated_at"),
                        result.getInt("iterations"),
                        result.getString("password"),
                        result.getString("salt")
                );
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return pass;
    }
    
}

package persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Password;
import util.DBUtil;

public class RPassword extends ARepository implements IRepository<Password, Long> {
    public RPassword() {
    }

    @Override
    public void save(Password entity) {
        try {
            String query = "INSERT INTO tbl_password " +
                    "(created_at, updated_at, iterations, password, salt)" +
                    " VALUE (?,?,?,?,?)";
            statement = DBUtil.getConnetion().prepareStatement(query);
            this.setTimestampOnCreate(statement);
            statement.setInt(3, entity.getIterations());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getSalt());
            int result = statement.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(statement);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Password entity) {
        try{
            String query = "UPDATE tbl_password SET " +
                    "updated_at = ?, iterations = ?, password = ?, salt = ? WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            this.setTimestampOnUpdate(statement);
            statement.setInt(2, entity.getIterations());
            statement.setString(3, entity.getPassword());
            statement.setString(4, entity.getSalt());
            statement.setLong(5, entity.getId());
            int result = statement.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(statement);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Password entity) {
        try{
            String query = "DELETE FROM tbl_password WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, entity.getId());
            int result = statement.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(statement);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Password findById(Long id) {
        Password pass = null;
        try{
            String query = "SELECT * FROM tbl_password WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
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
            if(DBUtil.DEBUG) System.out.println(statement);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pass;
    }
    
    public Password findByHash(String hash){
        Password pass = null;
        try{
            String query = "SELECT * FROM tbl_password WHERE password LIKE ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setString(1, hash);
            ResultSet result = statement.executeQuery();
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
            if(DBUtil.DEBUG) System.out.println(statement);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pass;
    }
    
    public Password findByProfileId(Long id){
        Password pass = null;
        try{
            String query = "SELECT `tbl_password`.* FROM `tbl_password` " + 
                    "JOIN `tbl_profile` ON `tbl_profile`.`password_id` = `tbl_password`.`id` " +
                    "WHERE `tbl_profile`.id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
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
            if(DBUtil.DEBUG) System.out.println(statement);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pass;
    }
    
}

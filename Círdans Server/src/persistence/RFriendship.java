 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import app.console.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Friendship;
import util.DBUtil;

/**
 *
 * @author Marcelo Gomes Martins
 */
public class RFriendship extends ARepository implements IRepository<Friendship, Long> {
    PreparedStatement statement;
    
    public RFriendship() {
    }

    @Override
    public void save(Friendship entity) {
        try {
            String query = "INSERT INTO rel_profile_profile " +
                    "(created_at, updated_at, accepted, blocked, profile_reciever, profile_sender)" +
                    " VALUE (?,?,?,?,?,?)";
            statement = DBUtil.getConnetion().prepareStatement(query);
            this.setTimestampOnCreate(statement);
            statement.setBoolean(3, false);
            statement.setBoolean(4, false);
            statement.setLong(5, entity.getReciever());
            statement.setLong(6, entity.getSender());
            int result = statement.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(statement);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Connection conn = DBUtil.getConnetion();
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(RFriendship.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Friendship entity) {
        try{
            String query = "UPDATE rel_profile_profile SET " +
                    "updated_at = ?, accepted = ?, blocked = ?, profile_reciever = ?, profile_sender = ? WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            this.setTimestampOnUpdate(statement);
            statement.setBoolean(2, entity.isAccepted());
            statement.setBoolean(3, entity.isBlocked());
            statement.setLong(4, entity.getReciever());
            statement.setLong(5, entity.getSender());
            statement.setLong(6, entity.getId());
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
    public void delete(Friendship entity) {
        try{
            String query = "DELETE FROM rel_profile_profile WHERE id = ?";
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
    public Friendship findById(Long id) {
        Friendship friends = null;
        try{
            String query = "SELECT * FROM rel_profile_profile WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                friends = new Friendship(
                        result.getLong("id"),
                        result.getTimestamp("created_at"),
                        result.getTimestamp("updated_at"),
                        result.getBoolean("accepted"),
                        result.getBoolean("blocked"),
                        result.getLong("profile_reciever"),
                        result.getLong("profile_sender")
                );
            }
            if(DBUtil.DEBUG) System.out.println(statement);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }
    
    public Friendship findByProfiles(Long proA, Long proB) {
        Friendship friends = null;
        try{
            String query = "SELECT * FROM rel_profile_profile WHERE " + 
                    "profile_reciever = ? AND profile_sender = ? OR " +
                    "profile_reciever = ? AND profile_sender = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, proA);
            statement.setLong(2, proB);            
            statement.setLong(3, proB);
            statement.setLong(4, proA);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                friends = new Friendship(
                        result.getLong("id"),
                        result.getTimestamp("created_at"),
                        result.getTimestamp("updated_at"),
                        result.getBoolean("accepted"),
                        result.getBoolean("blocked"),
                        result.getLong("profile_reciever"),
                        result.getLong("profile_sender")
                );
            }
            if(DBUtil.DEBUG) System.out.println(statement);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }
}

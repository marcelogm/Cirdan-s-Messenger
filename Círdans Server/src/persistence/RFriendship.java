 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Friendship;
import util.DBUtil;

/**
 *
 * @author Marcelo Gomes Martins
 */
public class RFriendship extends ARepository implements IRepository<Friendship, Long> {

    public RFriendship() {
    }

    @Override
    public void save(Friendship entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO rel_profile_profile " +
                    "(created_at, updated_at, accepted, blocked, profile_reciever, profile_sender)" +
                    " VALUE (?,?,?,?,?,?)";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            this.setTimestampOnCreate(st);
            st.setBoolean(3, false);
            st.setBoolean(4, false);
            st.setLong(5, entity.getReciever());
            st.setLong(6, entity.getSender());
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
    public void update(Friendship entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try{
            String query = "UPDATE rel_profile_profile SET " +
                    "updated_at = ?, accepted = ?, blocked = ?, profile_reciever = ?, profile_sender = ? WHERE id = ?";
            st = conn.prepareStatement(query);
            conn = DBUtil.getConnetion();
            this.setTimestampOnUpdate(st);
            st.setBoolean(2, entity.isAccepted());
            st.setBoolean(3, entity.isBlocked());
            st.setLong(4, entity.getReciever());
            st.setLong(5, entity.getSender());
            st.setLong(6, entity.getId());
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
    public void delete(Friendship entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try{
            String query = "DELETE FROM rel_profile_profile WHERE id = ?";
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
    public Friendship findById(Long id) {
        Connection conn = null;
        PreparedStatement st = null;
        Friendship friends = null;
        try{
            String query = "SELECT * FROM rel_profile_profile WHERE id = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
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
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return friends;
    }
    
    public Friendship findByProfiles(Long proA, Long proB) {
        Friendship friends = null;
        Connection conn = null;
        PreparedStatement st = null;
        try{
            String query = "SELECT * FROM rel_profile_profile WHERE " + 
                    "profile_reciever = ? AND profile_sender = ? OR " +
                    "profile_reciever = ? AND profile_sender = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, proA);
            st.setLong(2, proB);            
            st.setLong(3, proB);
            st.setLong(4, proA);
            ResultSet result = st.executeQuery();
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
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return friends;
    }
}

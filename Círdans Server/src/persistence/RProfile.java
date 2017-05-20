package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import model.Profile;
import util.DBUtil;

public class RProfile extends ARepository implements IRepository<Profile, Long> {
    public RProfile() {
    }

    @Override
    public void save(Profile entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO tbl_profile " +
                    "(created_at, updated_at, activated, email," +
                    " image_url, last_ipv4, name, nickname," + 
                    " last_online_time, pstatus, tstatus, password_id)" +
                    " VALUE (?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            this.setTimestampOnCreate(st);
            st.setBoolean(3, entity.isActivated());
            st.setString(4, entity.getEmail());
            st.setString(5, entity.getImageUrl());
            st.setString(6, entity.getIpv4());
            st.setString(7, entity.getName());
            st.setString(8, entity.getNick());
            if(entity.getOnlineAt() != null){
                st.setTimestamp(9, Timestamp.from(
                        Instant.ofEpochMilli(
                                entity.getOnlineAt().getTime()
                        )
                )); 
            } else st.setTimestamp(9, null);
            st.setInt(10, entity.getStatus());
            st.setString(11, entity.getSubnick());
            st.setLong(12, entity.getPassword());
            int result = st.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
    }

    @Override
    public void update(Profile entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try{
            String query = "UPDATE tbl_profile SET " +
                    "updated_at = ?, activated = ?, email = ?, " + 
                    "image_url = ?, last_ipv4 = ?, name = ?, " + 
                    "nickname = ?, last_online_time = ?, pstatus = ?, " +
                    "tstatus = ?, password_id = ? WHERE id = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            this.setTimestampOnUpdate(st);
            st.setBoolean(2, entity.isActivated());
            st.setString(3, entity.getEmail());
            st.setString(4, entity.getImageUrl());
            st.setString(5, entity.getIpv4());
            st.setString(6, entity.getName());
            st.setString(7, entity.getNick());
            if(entity.getOnlineAt() != null){
                st.setTimestamp(8, Timestamp.from(
                        Instant.ofEpochMilli(
                                entity.getOnlineAt().getTime()
                        )
                )); 
            } else st.setTimestamp(8, null);
            st.setInt(9, entity.getStatus());
            st.setString(10, entity.getSubnick());
            st.setLong(11, entity.getPassword());
            st.setLong(12, entity.getId());
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
    public void delete(Profile entity) {
        Connection conn = null;
        PreparedStatement st = null;
        try{
            String query = "DELETE FROM tbl_profile WHERE id = ?";
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
    public Profile findById(Long id) {
        Connection conn = null;
        PreparedStatement st = null;
        Profile profile = null;
        try{
            String query = "SELECT * FROM tbl_profile WHERE id = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            ResultSet result = st.executeQuery();
            while(result.next()){
                profile = this.createProfileByResultSet(result);
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return profile;
    }
    
    public Profile findByEmail(String email) {
        Connection conn = null;
        PreparedStatement st = null;
        Profile profile = null;
        try{
            String query = "SELECT * FROM tbl_profile WHERE email LIKE ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setString(1, email);
            ResultSet result = st.executeQuery();
            while(result.next()){
                profile = this.createProfileByResultSet(result);
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return profile;
    }
    
    public ArrayList<Profile> findFriendList(Long id, boolean isAccepted, boolean isBlocked){
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `fri`.* FROM `tbl_profile` AS `fri` " +
            "WHERE `fri`.`id` IN (SELECT `re1`.`profile_reciever` FROM `rel_profile_profile` AS `re1` JOIN `tbl_profile` AS `me1` ON `me1`.`id` = `re1`.`profile_sender` WHERE `me1`.`id` = ? AND `re1`.`accepted` = ? AND `re1`.`blocked` = ?)" +
            "OR `fri`.`id` IN (SELECT `re2`.`profile_sender` FROM `rel_profile_profile` AS `re2` JOIN `tbl_profile` AS `me2` ON `me2`.`id` = `re2`.`profile_reciever` WHERE `me2`.`id` = ? AND `re2`.`accepted` = ? AND `re2`.`blocked` = ?)";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            st.setBoolean(2, isAccepted);
            st.setBoolean(3, isBlocked);
            st.setLong(4, id);
            st.setBoolean(5, isAccepted);
            st.setBoolean(6, isBlocked);
            ResultSet result = st.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return profiles;
    }
    
    public ArrayList<Profile> findFriendshipSended(Long id, boolean isAccepted, boolean isBlocked){
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `friend`.* FROM `tbl_profile` AS `friend` JOIN `rel_profile_profile` AS `rel` ON `friend`.`id` = `rel`.`profile_reciever` JOIN `tbl_profile` AS `me` ON `me`.`id` = `rel`.`profile_sender` WHERE `me`.`id` = ? AND `rel`.`accepted` = ? AND `rel`.`blocked` = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            st.setBoolean(2, isAccepted);
            st.setBoolean(3, isBlocked);
            ResultSet result = st.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return profiles;
    }
    
    public ArrayList<Profile> findFriendshipRecieved(Long id, boolean isAccepted, boolean isBlocked){
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `friend`.* FROM `tbl_profile` AS `friend` JOIN `rel_profile_profile` AS `rel` ON `friend`.`id` = `rel`.`profile_sender` JOIN `tbl_profile` AS `me` ON `me`.`id` = `rel`.`profile_reciever` WHERE `me`.`id` = ? AND `rel`.`accepted` = ? AND `rel`.`blocked` = ?";
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            st.setBoolean(2, isAccepted);
            st.setBoolean(3, isBlocked);
            ResultSet result = st.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return profiles;
    }
    
    public ArrayList<Profile> findNonFriendByName(Long id, String name){
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `nfr`.* FROM `tbl_profile` AS `nfr` WHERE `nfr`.`id` NOT IN (SELECT `re1`.`profile_reciever` FROM `rel_profile_profile` AS `re1` JOIN `tbl_profile` AS `me1` ON `me1`.`id` = `re1`.`profile_sender` WHERE `me1`.`id` = ? )" +
            "AND `nfr`.`id` NOT IN ( SELECT `re2`.`profile_sender` FROM `rel_profile_profile` AS `re2` JOIN `tbl_profile` AS `me2`  ON `me2`.`id` = `re2`.`profile_reciever` WHERE `me2`.`id` = ? ) " +
            "AND `nfr`.`id` != ? AND `nfr`.`name` LIKE ?"; 
            conn = DBUtil.getConnetion();
            st = conn.prepareStatement(query);
            st.setLong(1, id);
            st.setLong(2, id);
            st.setLong(3, id);
            st.setString(4, "%" + name  + "%");
            ResultSet result = st.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(st);
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { conn.close(); } catch (Exception e) {}
            try { st.close(); } catch (Exception e) {}
        }
        return profiles;
    }
    
    public Profile createProfileByResultSet(ResultSet result) throws SQLException{
        java.util.Date created = new java.util.Date(result.getTimestamp("created_at").getTime());
        java.util.Date updated = new java.util.Date(result.getTimestamp("updated_at").getTime());
        java.util.Date lastOnline = null;
        if(result.getTimestamp("last_online_time") != null){
            lastOnline = new java.util.Date(result.getTimestamp("last_online_time").getTime());
        }
        return new Profile(
                result.getLong("id"),
                created,
                updated,
                result.getBoolean("activated"),
                result.getString("name"),
                result.getString("nickname"),
                result.getString("email"),
                result.getString("tstatus"),
                result.getString("last_ipv4"),
                lastOnline,
                result.getString("image_url"),
                result.getInt("pstatus"),
                result.getLong("password_id")
        );
    }
   
}


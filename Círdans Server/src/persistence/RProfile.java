package persistence;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import model.Profile;
import util.DBUtil;

public class RProfile extends ARepository implements IRepository<Profile, Long> {
    public RProfile() {
    }

    @Override
    public void save(Profile entity) {
        try {
            String query = "INSERT INTO tbl_profile " +
                    "(created_at, updated_at, activated, email," +
                    " image_url, last_ipv4, name, nickname," + 
                    " last_online_time, pstatus, tstatus, password_id)" +
                    " VALUE (?,?,?,?,?,?,?,?,?,?,?,?)";
            statement = DBUtil.getConnetion().prepareStatement(query);
            this.setTimestampOnCreate(statement);
            statement.setBoolean(3, entity.isActivated());
            statement.setString(4, entity.getEmail());
            statement.setString(5, entity.getImageUrl());
            statement.setString(6, entity.getIpv4());
            statement.setString(7, entity.getName());
            statement.setString(8, entity.getNick());
            if(entity.getOnlineAt() != null){
                statement.setTimestamp(9, Timestamp.from(
                        Instant.ofEpochMilli(
                                entity.getOnlineAt().getTime()
                        )
                )); 
            } else statement.setTimestamp(9, null);
            statement.setInt(10, entity.getStatus());
            statement.setString(11, entity.getSubnick());
            statement.setLong(12, entity.getPassword());
            int result = statement.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Profile entity) {
        try{
            String query = "UPDATE tbl_profile SET " +
                    "updated_at = ?, activated = ?, email = ?, " + 
                    "image_url = ?, last_ipv4 = ?, name = ?, " + 
                    "nickname = ?, last_online_time = ?, pstatus = ?, " +
                    "tstatus = ?, password_id = ? WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            this.setTimestampOnUpdate(statement);
            statement.setBoolean(2, entity.isActivated());
            statement.setString(3, entity.getEmail());
            statement.setString(4, entity.getImageUrl());
            statement.setString(5, entity.getIpv4());
            statement.setString(6, entity.getName());
            statement.setString(7, entity.getNick());
            if(entity.getOnlineAt() != null){
                statement.setTimestamp(8, Timestamp.from(
                        Instant.ofEpochMilli(
                                entity.getOnlineAt().getTime()
                        )
                )); 
            } else statement.setTimestamp(8, null);
            statement.setInt(9, entity.getStatus());
            statement.setString(10, entity.getSubnick());
            statement.setLong(11, entity.getPassword());
            statement.setLong(12, entity.getId());
            int result = statement.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Profile entity) {
        try{
            String query = "DELETE FROM tbl_profile WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, entity.getId());
            int result = statement.executeUpdate();
            if(result > 0 && DBUtil.DEBUG){
                System.out.println(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Profile findById(Long id) {
        Profile profile = null;
        try{
            String query = "SELECT * FROM tbl_profile WHERE id = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                profile = this.createProfileByResultSet(result);
            }
            if(DBUtil.DEBUG) System.out.println(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }
    
    public Profile findByEmail(String email) {
        Profile profile = null;
        try{
            String query = "SELECT * FROM tbl_profile WHERE email LIKE ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                profile = this.createProfileByResultSet(result);
            }
            if(DBUtil.DEBUG) System.out.println(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }
    
    public ArrayList<Profile> findFriendList(Long id, boolean isAccepted, boolean isBlocked){
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `fri`.* FROM `tbl_profile` AS `fri` " +
            "WHERE `fri`.`id` IN (SELECT `re1`.`profile_reciever` FROM `rel_profile_profile` AS `re1` JOIN `tbl_profile` AS `me1` ON `me1`.`id` = `re1`.`profile_sender` WHERE `me1`.`id` = ? AND `re1`.`accepted` = ? AND `re1`.`blocked` = ?)" +
            "OR `fri`.`id` IN (SELECT `re2`.`profile_sender` FROM `rel_profile_profile` AS `re2` JOIN `tbl_profile` AS `me2` ON `me2`.`id` = `re2`.`profile_reciever` WHERE `me2`.`id` = ? AND `re2`.`accepted` = ? AND `re2`.`blocked` = ?)";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            statement.setBoolean(2, isAccepted);
            statement.setBoolean(3, isBlocked);
            statement.setLong(4, id);
            statement.setBoolean(5, isAccepted);
            statement.setBoolean(6, isBlocked);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }
    
    public ArrayList<Profile> findFriendshipSended(Long id, boolean isAccepted, boolean isBlocked){
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `friend`.* FROM `tbl_profile` AS `friend` JOIN `rel_profile_profile` AS `rel` ON `friend`.`id` = `rel`.`profile_reciever` JOIN `tbl_profile` AS `me` ON `me`.`id` = `rel`.`profile_sender` WHERE `me`.`id` = ? AND `rel`.`accepted` = ? AND `rel`.`blocked` = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            statement.setBoolean(2, isAccepted);
            statement.setBoolean(3, isBlocked);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }
    
    public ArrayList<Profile> findFriendshipRecieved(Long id, boolean isAccepted, boolean isBlocked){
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `friend`.* FROM `tbl_profile` AS `friend` JOIN `rel_profile_profile` AS `rel` ON `friend`.`id` = `rel`.`profile_sender` JOIN `tbl_profile` AS `me` ON `me`.`id` = `rel`.`profile_reciever` WHERE `me`.`id` = ? AND `rel`.`accepted` = ? AND `rel`.`blocked` = ?";
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            statement.setBoolean(2, isAccepted);
            statement.setBoolean(3, isBlocked);
            ResultSet result = statement.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(statement);
        } catch (SQLException e) {
            e.printStackTrace();
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
    
    public ArrayList<Profile> findNonFriendByName(Long id, String name){
        ArrayList<Profile> profiles = new ArrayList<>();
        try{
            String query = "SELECT `nfr`.* FROM `tbl_profile` AS `nfr` WHERE `nfr`.`id` NOT IN (SELECT `re1`.`profile_reciever` FROM `rel_profile_profile` AS `re1` JOIN `tbl_profile` AS `me1` ON `me1`.`id` = `re1`.`profile_sender` WHERE `me1`.`id` = ? )" +
            "AND `nfr`.`id` NOT IN ( SELECT `re2`.`profile_sender` FROM `rel_profile_profile` AS `re2` JOIN `tbl_profile` AS `me2`  ON `me2`.`id` = `re2`.`profile_reciever` WHERE `me2`.`id` = ? ) " +
            "AND `nfr`.`id` != ? AND `nfr`.`name` LIKE ?"; 
            statement = DBUtil.getConnetion().prepareStatement(query);
            statement.setLong(1, id);
            statement.setLong(2, id);
            statement.setLong(3, id);
            statement.setString(4, "%" + name  + "%");
            ResultSet result = statement.executeQuery();
            while(result.next()){
                if(result != null){
                    profiles.add(this.createProfileByResultSet(result));
                }
            }
            if(DBUtil.DEBUG) System.out.println(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }
    
   
}


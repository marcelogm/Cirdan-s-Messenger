package facade;

import java.util.ArrayList;
import model.Friendship;
import model.Password;
import model.Profile;
import persistence.RFriendship;
import persistence.RPassword;
import persistence.RProfile;
public class Facade {

    private static Facade f;
    private final RPassword passwords;
    private final RProfile profiles;
    private final RFriendship friendships;
    
    private Facade(){
        this.passwords = new RPassword();
        this.profiles = new RProfile();
        this.friendships = new RFriendship();
    }
    
    public static Facade getInstance() {
        if(f == null) {
            f = new Facade();
        }
        return f;
    }
    
    /**
     * PERSISTE OBJETO NO BANCO DE DADOS
     */
    
    public void save(Profile profile){
        this.profiles.save(profile);
    }
       
    public void save(Friendship friendship){
        this.friendships.save(friendship);
    }
    
    public void save(Password pass){
        this.passwords.save(pass);
    }
    
    /**
     * ATUALIZA OBJETO NO BANCO
     */
    
    public void update(Friendship relation){
        this.friendships.update(relation);
    }
    
    public void update(Profile profile){
        this.profiles.update(profile);
    }
    
    public void update(Password password){
        this.passwords.update(password);
    }
    
    /**
     * DELETA OBJETO NO BANCO
     */
    
    public void delete(Friendship friendship){
        this.friendships.delete(friendship);
    }
    
    public Profile findProfileById(long id){
        return this.profiles.findById(id);
    }
    
    public Password findPasswordByProfileId(long id){
        return this.passwords.findByProfileId(id);
    }
    
    public Password findPasswordByHash(String hash){
        return this.passwords.findByHash(hash);
    }
    
    public Profile findProfileByEmail(String email){
        return this.profiles.findByEmail(email);
    }
    
    public ArrayList<Profile> findFriendList(long id){
        return this.profiles.findFriendList(id, true, false);
    }
    
    public ArrayList<Profile> findNonFriendList(long id, String name){
        return this.profiles.findNonFriendByName(id, name);
    }
    
    public Friendship findFriendshipByProfiles(long sender, long reciever){
        return this.friendships.findByProfiles(sender, reciever);
    }
    
    public ArrayList<Profile> findPendingFriends(Long id){
        return this.profiles.findFriendshipRecieved(id, false, false);
    }
}

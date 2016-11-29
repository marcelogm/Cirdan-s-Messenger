package protocol;

public enum EStatus {
    ONLINE(0), BUSY(1), AWAY(2), OFFLINE(3);
    public int status;
    
    EStatus(int value){
        this.status = value;
    }
    
    public static EStatus get(int value){
        switch(value){
            case 0:
                return ONLINE;
            case 1:
                return BUSY;
            case 2:
                return AWAY;
            case 3:
                return OFFLINE;
            default:
                return null;
        }
    }
    
}

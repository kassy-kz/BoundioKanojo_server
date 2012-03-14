package orz.kassy.boundiokanojo.server;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class PhoneData {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String twitterId;

    @Persistent
    private String phoneNum;

    public PhoneData(String twitterId, String content) {
        this.twitterId = twitterId;
        this.phoneNum = content;
    }

    public Key getKey() {
        return key;
    }

    public String getTwitterId() {
        return twitterId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setTwitterId(String twitterId) {
        this.twitterId = twitterId;
    }
}
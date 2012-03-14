/**
 * GETメソッドでtwitterIDを送ると、登録済の場合のみ、電話をかけます
 * @author kashimoto
 */
package orz.kassy.boundiokanojo.server;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import jp.lnc.java.lib.BoundioTelephon;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.search.StatusCode;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
@SuppressWarnings("serial")
public class TelReqServlet extends HttpServlet {
    // 電話の音声ファイル
    private static final String VOICE_FILE = "file(000002)";
    // boundioのユーザ固有シリアル 
    // String USER_SERIAL = "9W9G3VEMASQ8VJTL";
    private static final String USER_SERIAL = "GCYFD8ZWJFYZZS9V";
    // boundioのユーザー固有キー
    // String KEY = "eSxAwZ1qzHY5Bpy6";
    private static final String KEY = "eSxAwZ1qzHY5Bpy6";
    private BoundioTelephon mTelephon;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");

        // リクエストからtwitterID取得
        String twitterId = req.getParameter("twitter");
        
        // データストアクエリを設定
        PersistenceManager pm = PMF.get().getPersistenceManager();
        String queryString = "select from " + PhoneData.class.getName();
        javax.jdo.Query query = pm.newQuery(queryString);
        String param1 = twitterId;
        query.setFilter("twitterId == twitterName");
        query.declareParameters("String twitterName");

        // クエる
        List<PhoneData> phoneDataList = (List<PhoneData>) query.execute(param1);
        if (phoneDataList.isEmpty()) {
            resp.getWriter().println("failure");
        } else {
            PhoneData phone = phoneDataList.get(0);
            String targetPhoneNum = phone.getPhoneNum();
            System.out.println(targetPhoneNum);
            mTelephon = new BoundioTelephon(USER_SERIAL, KEY);
            String ret = mTelephon.call(targetPhoneNum, VOICE_FILE);
            System.out.println(ret);
            // 電話に成功した場合
            if(ret.contains("true")) {
                resp.getWriter().println("success");
            // 電話に失敗した場合
            } else {
                resp.getWriter().println("failure");
            }
        }
    }
}

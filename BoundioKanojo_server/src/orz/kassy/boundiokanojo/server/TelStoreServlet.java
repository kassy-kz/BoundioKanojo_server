/**
 * POSTメソッドでtwitterIDと電話番号を送ると登録します。
 */
package orz.kassy.boundiokanojo.server;

import java.io.IOException;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import jp.lnc.java.lib.BoundioTelephon;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class TelStoreServlet extends HttpServlet {
    // boundioのユーザ固有シリアル
    String USER_SERIAL = "9W9G3VEMASQ8VJTL";
    // boundioのユーザー固有キー
    String KEY = "eSxAwZ1qzHY5Bpy6";
    private BoundioTelephon mTelephon;

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Tel store");
        mTelephon = new BoundioTelephon(USER_SERIAL, KEY);
        //String ret = mTelephon.call("09037054767", "file(000001)");
        doPost(req,resp);
    }
    
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws IOException {
        // リクエストからパラメタ取得
        String phoneNum = req.getParameter("phone");
        String twitterId = req.getParameter("twitter");
        System.out.println("posted phone is" + phoneNum);
        System.out.println("posted twitter is" + twitterId);

        // データストアに格納
        PhoneData phoneData = new PhoneData(twitterId,phoneNum);
        PersistenceManager pm = PMF.get().getPersistenceManager();
        try {
            pm.makePersistent(phoneData);
        } finally {
            pm.close();
        }
        //resp.sendRedirect("/guestbook.jsp");
    }
}

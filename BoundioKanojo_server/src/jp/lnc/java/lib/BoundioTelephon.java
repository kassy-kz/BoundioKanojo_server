package jp.lnc.java.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;

public class BoundioTelephon {
	
	private final String keyVal;
	private final String userSerial;
	private final String apiVarsion;

	public BoundioTelephon(String userSerial,String keyVal,String apiVarsion){
		this.keyVal = keyVal;
		this.userSerial = userSerial;
		this.apiVarsion = apiVarsion;
	}
	
	public BoundioTelephon(String userSerial,String keyVal){
		this.keyVal = keyVal;
		this.userSerial = userSerial;
		this.apiVarsion = "vd1";
	}
	
	public String call(String tel_num,String cast){
		String url_str = "https://boundio.jp/api/"+ apiVarsion +"/" + userSerial + "/call";
		
		String ret = "";
		try{
			URL url = new URL(url_str);
			System.out.println("url:"+url_str);
			System.out.println("key:"+keyVal);
			URLConnection urlConn = url.openConnection();
			urlConn.setDoOutput(true);
			OutputStream outS = urlConn.getOutputStream();
		 	String postStr = "key=" + keyVal + "&tel_to=" + tel_num + "&cast=" + cast;
		 	PrintStream printS = new PrintStream(outS);
		 	printS.print(postStr); 
	        
			InputStream inS = urlConn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inS));
			String s;
			while ((s = reader.readLine()) != null) {
				System.out.println(s);
				ret  += s+"\n";
			}
			reader.close();
		}
		catch( IOException e ){
			System.err.println( e );
		}
		return ret;
	}
	
}
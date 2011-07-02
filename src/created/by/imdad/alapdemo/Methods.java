package created.by.imdad.alapdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.SharedPreferences;
public class Methods extends Activity
{
	public static final String PREF_FILENAME = "AlapPreference";  
	
	private String  host = "http://10.0.2.2/android/",    
					 
					veriryLoginPage = "verify_login.php", 

					url =null ;
				  
	    
	InputStream inputStream;
	List< String[]> query;
	List< NameValuePair> nameValuePair;  
	HttpPost httppost;
	StringBuffer buffer;
	HttpResponse response;
	HttpClient httpclient;
	SharedPreferences preference;

	// This function creates a connection to server.
	public InputStream  makeRequest( String reqPage, String valueList[][] )
	{
		url = host + reqPage;
		try 
		{
			httpclient = new DefaultHttpClient();
			httppost = new HttpPost( url );
		
			nameValuePair = new ArrayList<NameValuePair>();
			
			for( int i=0; i<valueList.length; i++)
				nameValuePair.add( new BasicNameValuePair( valueList[i][0], valueList[i][1] ) );
			
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			
			response = httpclient.execute(httppost);
			inputStream = response.getEntity().getContent();
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return inputStream;
	}
	  
	
	//This function is used to verify user name and password in the login page.
	public boolean verifyLogin( String data[][] ) throws IOException
	{
		boolean validate = false;
		
		
        InputStream is = makeRequest( veriryLoginPage,  data );
		
		BufferedReader reader = new BufferedReader(new InputStreamReader( is ) );	
        String line = reader.readLine();
		
        if( line.charAt(0)== 'Y')
        	validate = true;
        else
        	validate = false;
		
		
		return validate;
	} 
}

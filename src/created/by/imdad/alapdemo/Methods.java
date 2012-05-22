package created.by.imdad.alapdemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
public class Methods extends Activity
{
	public static final String PREF_FILENAME = "AlapPreference";  
	
	private String  host = "http://10.0.2.2/android/",  // "http://imdad.coolpage.biz/alap/", //   
					
					usernamePage = "username.php",
					veriryLoginPage = "verify_login.php",
					signupPage = "signup.php",

					url =null, 
				    result, 
				    value[];
				  
	    
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
	
	
	// This method is used in Search Tab. 
	public List<String[]> getIdAndFullName( InputStream is )
	{
		try
		{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"),8);	
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	
	        while ((line = reader.readLine()) != null)
	        {
	        	sb.append(line + "\n");
	        }	
	        inputStream.close();
	
	        result=sb.toString();	
		}
		catch(Exception e){}

		try
		{	
			query = new ArrayList< String[]>();
	        JSONArray jArray = new JSONArray(result);
	        int count =jArray.length();
	    
            for(int i=1;i<=jArray.length();i++)
	        {
                JSONObject json_data = jArray.getJSONObject(i);
                value = new String[2];
                value[0] = json_data.getString( "user_id");
                value[1] = json_data.getString( "user_fullname" );
                
                query.add(value);              
	        }		        	        	
		}
		catch(JSONException e){}
		
		return  query;
	}
	
	public List< String > getChat( InputStream is )
	{
		String data = null;
		List< String> chatList = new ArrayList< String>();
		try
		{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);	
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	
	        while ((line = reader.readLine()) != null)
	        {
	        	sb.append(line + "\n");
	        }	
	        is.close();
	
	        result=sb.toString();	
	        
			
		}
		catch(Exception e){}

		try
		{	
	        JSONArray jArray = new JSONArray(result);	        
            JSONObject json_data = jArray.getJSONObject(0);         
            data = json_data.getString( "user_chats");
            
            StringTokenizer token = new StringTokenizer( data, "-786-" );
    		while( token.hasMoreTokens())
    		{
    			chatList.add(token.nextToken());
    		}
	        		        	        	
		}
		catch(JSONException e){}
		
		return  chatList;
	}
	
	public ArrayList< String > getNotifications( InputStream is )
	{
		String data = null;
		ArrayList< String> notificationList = new ArrayList< String>();
		try
		{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);	
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	
	        while ((line = reader.readLine()) != null)
	        {
	        	sb.append(line + "\n");
	        }	
	        is.close();
	
	        result=sb.toString();
			
		}
		catch(Exception e){}

		try
		{	
	        JSONArray jArray = new JSONArray(result);	        
            JSONObject json_data = jArray.getJSONObject(0);         
            data = json_data.getString( "user_notification");
            
            StringTokenizer token = new StringTokenizer( data, "-786-" );
    		while( token.hasMoreTokens())
    		{
    			notificationList.add(token.nextToken());
    		}	        	        	
		}
		catch(JSONException e){}
		
		return  notificationList;
	}
	
	
	public List<String> queryAllInformation( InputStream is )
	{

		List< String> listAllInformation = new ArrayList< String >();;
		try
		{
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);	

	        result = reader.readLine();	
	        is.close();	
		}
		catch(Exception e){}

		try
		{	
			
	        JSONArray jArray = new JSONArray(result);

	        JSONObject json_data = jArray.getJSONObject(1);

            listAllInformation.add( json_data.getString( "user_fullname") );
            listAllInformation.add( json_data.getString( "user_address") );
            listAllInformation.add( json_data.getString( "user_country") );
            listAllInformation.add( json_data.getString( "user_age") );
            listAllInformation.add( json_data.getString( "user_sex") );
            listAllInformation.add( json_data.getString( "user_mobileno") );
            listAllInformation.add( json_data.getString( "user_hobby") );
            listAllInformation.add( json_data.getString( "user_religion") );
            listAllInformation.add( json_data.getString( "user_workingfield") );
                       
       }
		catch(JSONException e){}
		
		return  listAllInformation;
	}
	
	
	//Email validation ( simplified form )
	public boolean emailValidation( String eml)
	{
		boolean validate = false;
		
		int atIndex = eml.indexOf( '@' );

	     if( atIndex !=-1 && atIndex>2 && eml.charAt((atIndex+1))!='.' &&  eml.indexOf( '.', (atIndex+1) )>atIndex )
	        validate = true;
		
		 return validate;
	}
	
	// If there already exist a username in the database
	// then this function returns false making this username is invalid.
	public boolean userNameValidation( String usrnm) throws IOException
	{
		boolean validate = false;
		String data[][] = new String[1][2];
		
		
		data[0][0] = "user_name";
		data[0][1] = usrnm;
		
		InputStream is = makeRequest( usernamePage,  data );
		
		BufferedReader reader = new BufferedReader(new InputStreamReader( is ) );	
        String line = reader.readLine();
		
        if( line.charAt(0)== 'Y')
        	validate = false;
        else
        	validate = true;
		
		
		return validate;
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
	
	
	public boolean makeSignup( String usrnm, String pswd, String rpswd, String eml) throws IOException
	{
		boolean validate = false;
		String data[][] = new String[3][2];
		
		data[0][0] = "user_name";			data[0][1] = usrnm;
		data[1][0] = "user_password";		data[1][1] = pswd;
		data[2][0] = "user_email";			data[2][1] = eml;
		
		if( userNameValidation( usrnm ) && pswd.equals( rpswd ) && emailValidation( eml ))
		{
			makeRequest( signupPage,  data );
			
			// Something is going to be happen here.............................
			
			validate = true;
		}
		
		return validate;
	}
	
	
	public String[] getLocalData( String data[] )
	{
		Context context = null;
		String Data[] = new String[data.length];
		
		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		
		preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);
		
		for( int i=0; i<data.length; i++)
		{
			Data[i] = preference.getString(  data[i], "" );
		}
		
		return Data;
	}
	
	public String[] usernameAndPassword( )
	{
		String name_pass[]= new String[2];
		Context context = null;
		
		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		
		preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);

		name_pass[0] = preference.getString("username", "0" );
		name_pass[1] = preference.getString("password", "0");
		
		return name_pass;
	}
	
	public boolean putLocalData( String data[][] )
	{
		Context context = null;
		
		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		
		preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor= preference.edit();
		
		for( int i=0; i<data.length; i++)
		{
			editor.putString(  data[i][0], data[i][1] );
		}
		editor.commit();
		
		return true;	
	}
	
}

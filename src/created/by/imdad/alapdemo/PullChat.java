package created.by.imdad.alapdemo;

import java.util.List;

import created.by.imdad.alapdemo.dialog.ChatDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.IBinder;
import android.widget.Toast;


public class PullChat extends Service 
{

	public static final String PREF_FILENAME = "AlapPreference",
							   PREF_SAVECHAT = "AlapPreferenceReceiveTuits";
	
	String	retriveChat = "chat_retrive.php";  
	Context context;
	SharedPreferences preference;
	private Methods functions;	
	private String req[][];
	private List< String > receiveData;
	
	@Override
	public IBinder onBind(Intent arg0) {
		
		
		return null;
	}

	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		
		preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);

		functions = new Methods();
		req = new String[2][2];
		req[0][0] = "user_name";			req[0][1]= preference.getString("username","0" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "0");
		
		receiveData = functions.getChat(  functions.makeRequest( retriveChat, req) );
		
		preference = context.getSharedPreferences(PREF_SAVECHAT, Context.MODE_WORLD_WRITEABLE);
		for( int i=0; i<receiveData.size(); i++ )
		{

			String old_chat = preference.getString( "tuits", "notdataset" );
			
			SharedPreferences.Editor edt = preference.edit();
			if( old_chat.equals( "notdataset" ) )
				edt.putString("tuits", receiveData.get( i ) + "-786-" );
			else
				edt.putString("tuits", receiveData.get( i ) + "-786-" + old_chat );
			edt.commit();
			Toast.makeText(getApplicationContext(), receiveData.get(i) , 2000 ).show();
			
			ChatDialog chatDialog = new ChatDialog( getApplicationContext() );
			chatDialog.addTuit( receiveData.get(i) );
			

		}
		PullChat.this.stopSelf();
	}

}

package created.by.imdad.alapdemo.tab;

import java.util.ArrayList;
import java.util.List;

import created.by.imdad.alapdemo.Methods;
import created.by.imdad.alapdemo.ParentActivity;
import created.by.imdad.alapdemo.R;
import created.by.imdad.alapdemo.dialog.ChatDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView; 

public class TabList extends ParentActivity
{
	private Methods functions;
	private ListView list;	
	private ArrayAdapter< String > listadapter;
	private ArrayList< String > names = new ArrayList< String >();
	private ArrayList< String > ids = new ArrayList< String >();
	private List< String []> receiveData;
	
	private String  friendList = "friends_list.php",
	req[][];
	
	public static final String  PREF_FILENAME = "AlapPreference",
								PREF_CHAT = "AlapPreferenceChatting",
								PREF_SAVECHAT = "AlapPreferenceReceiveTuits";

	SharedPreferences preference;
	Context context = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setFullScreen();	
		setContentView( R.layout.tab_list );
		
		
		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		preference = getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);
		
		delChattingHistory();
		
		list=(ListView)findViewById(R.id.friends_tab_list );
		list.setOnItemClickListener( new ListItemClick() );
		
		listadapter = new ArrayAdapter< String >( this,
				R.layout.todolist_item, names );
		list.setAdapter(listadapter);
			
		functions = new Methods();
		
		//get username and password from preference
		req = new String[2][2];
		req[0][0] = "user_name";			req[0][1]= preference.getString("username","0" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "0");
		

	
	}
	
	@Override
	protected void onResume() 
	{
		getFriendsName();
		super.onResume();
	}
	
	
	//set full screen
 	public void setFullScreen()
	{
		this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
	}
 	
 	private void getFriendsName()
	{
		
		//clear previous data
		names.clear();
		ids.clear();
		

		ProgressDialog dialog = ProgressDialog.show(TabList.this, "", 
                "Loading. Please wait...", true);		
		receiveData = functions.getIdAndFullName(  functions.makeRequest( friendList, req) );
		dialog.dismiss();
		
		for( int i=0; i< receiveData.size(); i++ )
		{
			String st[]=receiveData.get(i);
			names.add( names.size(), st[1] );      // store Names
			ids.add(ids.size(), st[0]);            // store Ids
			listadapter.notifyDataSetChanged();
			
		}
		
		receiveData.clear();		
	}
 	
 	protected void delChattingHistory()
	{
 		SharedPreferences p = context.getSharedPreferences( PREF_CHAT, Context.MODE_WORLD_WRITEABLE);
 		SharedPreferences.Editor ed = p.edit();
 		ed.putString("chatting", "");
 		ed.commit();
 		
 		p = context.getSharedPreferences( PREF_SAVECHAT, Context.MODE_WORLD_WRITEABLE);
 		ed = p.edit();
 		ed.putString("tuits", "");
 		ed.commit();
	}


	public class ListItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int pos, long arg3) 
		{
			ChatDialog chat = new ChatDialog( TabList.this );
			
			chat.setID( Integer.parseInt( ids.get( pos ) ) ); 
			chat.show();
		}
		
	}

}

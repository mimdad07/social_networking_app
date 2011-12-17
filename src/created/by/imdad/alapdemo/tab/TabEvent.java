package created.by.imdad.alapdemo.tab;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import created.by.imdad.alapdemo.Methods;
import created.by.imdad.alapdemo.ParentActivity;
import created.by.imdad.alapdemo.R;
import created.by.imdad.alapdemo.dialog.NotificationDialog;
import created.by.imdad.alapdemo.dialog.ViewInfoDialog;

public class TabEvent extends ParentActivity implements OnClickListener
{	 
	
	private Methods functions;
	private ListView list;	
	private ArrayAdapter< String > listadapter;
	private ArrayList< String > names = new ArrayList< String >();
	private ArrayList< String > ids = new ArrayList< String >();
	private List< String []> receiveData;
	private ListItemClick itemClick;
	private Button 	btnFriendList, 
					btnPendingFriendList, 
					btnRequestingFriendList, 
					btnNotification, 	
					btnBack;
	
	private String  friendList = "friends_list.php",
					requestingFriendList = "requesting_friends_list.php",
					pendingFriendList = "pending_friends_list.php",
					findAllInformPage = "all_information.php",
					acceptFriendReq = "accept_you_as_friend.php",
					rejectFriendReq = "reject_your_friend_request.php",
					removeFromFriendList = "remove_you_from_friend_list.php",
					withdrawMyFriendReq = "withdraw_my_friend_request.php",
		
					req[][];
	
	public static final String PREF_FILENAME = "AlapPreference"; 
	SharedPreferences preference;
	Context context = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setFullScreen();	
		setContentView( R.layout.tab_event_layout );
		
		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);
		
		//initialize 
		btnFriendList = (Button) findViewById(R.id.btn_event_friendlist); 
		btnRequestingFriendList = (Button) findViewById(R.id.btn_event_requestingfriendlist); 
		btnPendingFriendList = (Button) findViewById(R.id.btn_event_pendingfriendlist); 
		btnNotification = (Button) findViewById(R.id.btn_event_notification); 
		btnBack = (Button) findViewById(R.id.btn_event_back); 
		//register
		btnFriendList.setOnClickListener(this); 
		btnRequestingFriendList.setOnClickListener(this);
		btnPendingFriendList.setOnClickListener(this);
		btnNotification.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		
		itemClick = new ListItemClick();
		list=(ListView)findViewById(R.id.event_tab_list );
		listadapter = new ArrayAdapter< String >( this,
				R.layout.todolist_item, names );
		list.setAdapter(listadapter);
		list.setOnItemClickListener( itemClick );
			
		functions = new Methods();
		
		//get username and password from preference
		req = new String[2][2];
		req[0][0] = "user_name";			req[0][1]= preference.getString("username","0" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "0");
		
	}
	
	
	//set full screen
 	public void setFullScreen()
	{
		this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
	}
 	
 
	@Override
	public void onClick(View view) 
	{
		switch( view.getId() )
		{
		case R.id.btn_event_friendlist:
			itemClick.setOPtion(0);
			getFriendsName();
			break;
			
		case R.id.btn_event_requestingfriendlist:
			itemClick.setOPtion(1);
			getRequestingFriendsName();
			break;
			
		case R.id.btn_event_pendingfriendlist:
			itemClick.setOPtion(2);
			getPendingFriendsName();
			break;
		
		case R.id.btn_event_notification:
			getNotification();
			break;
		
		case R.id.btn_event_back:
			goBack();
			break;
		}
		
	}
	
	

	private void getFriendsName()
	{
		buttonVisibleInvisible();
		displayNames( friendList );
		
	}
	
	private void getRequestingFriendsName()
	{
		buttonVisibleInvisible();
		displayNames( requestingFriendList );
	}
	
	private void getPendingFriendsName()
	{
		buttonVisibleInvisible();
		displayNames( pendingFriendList );
		
	}
	
	private void displayNames( String page )
	{
		//clear previous data
		names.clear();
		ids.clear();
		
		receiveData = functions.getIdAndFullName(  functions.makeRequest( page, req) );

		for( int i=0; i< receiveData.size(); i++ )
		{
			String st[]=receiveData.get(i);
			names.add( names.size(), st[1] );      // store Names
			ids.add(ids.size(), st[0]);            // store Ids
			listadapter.notifyDataSetChanged();
		}
		receiveData.clear();
	}
	
	private void buttonVisibleInvisible()
	{
		btnFriendList.setVisibility( View.GONE ); 
		btnRequestingFriendList.setVisibility( View.GONE );
		btnPendingFriendList.setVisibility( View.GONE );
		btnNotification.setVisibility( View.GONE );
		btnBack.setVisibility( View.VISIBLE );
		list.setVisibility(View.VISIBLE);
	}
	private void getNotification()
	{
		NotificationDialog notif_dialog = new NotificationDialog( this );
		notif_dialog.show();
	}

	//remove accepted or rejected friends name from list
	private void removeItem( int selectedItem )
	{
		names.remove(selectedItem);
		ids.remove(selectedItem);
		listadapter.notifyDataSetChanged();
		
	}
	private void goBack() 
	{
		btnFriendList.setVisibility( View.VISIBLE );
		btnRequestingFriendList.setVisibility( View.VISIBLE ); 
		btnPendingFriendList.setVisibility( View.VISIBLE  );
		btnNotification.setVisibility( View.VISIBLE  );
		btnBack.setVisibility( View.GONE );
		list.setVisibility(View.GONE);
		
	}
	
   	private String[][] prepareSendingData( int selectedItem)
   	{
   		String req[][] = new String[3][2];
    	req[0][0] = "user_name";			req[0][1]= preference.getString("username","" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "");
		req[2][0] = "friends_id";           req[2][1]= ids.get( selectedItem );
		
		return req;
	    
   	}

	private void removeFriend( int selectedItem )
	{
		String sendData[][] = prepareSendingData(  selectedItem);
	    functions.makeRequest( removeFromFriendList, sendData);	    
	    removeItem( selectedItem );
	}
	
	private void viewInfo( int selectedItem)
	{
		
		String id = ids.get(selectedItem);     //find clicked id and will have to query information for this id.
		String idValue[][] = new String[1][2];
		idValue[0][0] = "user_id";
		idValue[0][1] = id;	
		
		List<String> info = functions.queryAllInformation(  functions.makeRequest(findAllInformPage, idValue) );
	
		//display view dialog and waits for 10 seconds
		final ViewInfoDialog dlog = new ViewInfoDialog( this, info);
		dlog.show();
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						dlog.dismiss();
						
					}
		}).start();
	
	}
	
	//need to be defined later.
	private void withdrwRequest( int selectedItem )
	{
		String sendData[][] = prepareSendingData(  selectedItem);
		functions.makeRequest(withdrawMyFriendReq, sendData);
		removeItem( selectedItem );
		
	}
   
	protected void acceptFriend(int selectedItem ) 
   	{
    	String req[][] = new String[3][2];
    	req[0][0] = "user_name";			req[0][1]= preference.getString("username","" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "");
		req[2][0] = "friends_id";           req[2][1]= ids.get( selectedItem );
	    functions.makeRequest(acceptFriendReq, req);
	    
	    removeItem( selectedItem );
	    
   	}
 
   	protected void rejectFriend(int selectedItem ) 
   	{
    	String req[][] = new String[3][2];
    	req[0][0] = "user_name";			req[0][1]= preference.getString("username","" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "");
		req[2][0] = "friends_id";           req[2][1]= ids.get( selectedItem );
	    functions.makeRequest(rejectFriendReq, req);
	    
	    removeItem( selectedItem );
   	}
	
   	protected void removeNotification( int selectedItem )
   	{
   		
   	}
	
	private void showFriendListDialog( final int selectedItem )
	{
	   	final CharSequence[] items = {"Remove Friend", "View Info"};
	
	   	AlertDialog.Builder dialog = new AlertDialog.Builder( this );
	   	dialog.setItems(items, new DialogInterface.OnClickListener() 
	   	{
	   	    @Override
			public void onClick(DialogInterface dialog, int item) 
	   	    {
	   	    	
	   	    	switch(item)
	   	    	{
	   	    		case 0:
	   	    			removeFriend( selectedItem );
	   	    			break;
	   	    		
	   	    		case 1:
	   	    			viewInfo( selectedItem );
	   	    			break;
	   	    	}
	   	    }
	   	});
	   	dialog.show();
	}
   
   
	private void showRequestingFriendListDialog( final int selectedItem )
	{
	   	final CharSequence[] items = {"Withdrw Request", "View Info"};
	
	   	AlertDialog.Builder dialog = new AlertDialog.Builder( this );
	   	dialog.setItems(items, new DialogInterface.OnClickListener() 
	   	{
	   	    @Override
			public void onClick(DialogInterface dialog, int item) 
	   	    {
	   	    	
	   	    	switch(item)
	   	    	{
	   	    		case 0:
	   	    			withdrwRequest( selectedItem );
	   	    			break;
	   	    		
	   	    		case 1:
	   	    			viewInfo( selectedItem );
	   	    			break;
	   	    	}
	   	    }
	   	});
	   	dialog.show();
	}
	
	private void showPendingFriendListDialog( final int selectedItem )
	{
	   	final CharSequence[] items = {"Accept Friend Request", "Reject Friend Request", "View Info"};
	
	   	AlertDialog.Builder dialog = new AlertDialog.Builder( this );
	   	dialog.setItems(items, new DialogInterface.OnClickListener() 
	   	{
	   	    @Override
			public void onClick(DialogInterface dialog, int item) 
	   	    {
	   	    	
	   	    	switch(item)
	   	    	{
	   	    		case 0:
	   	    			acceptFriend( selectedItem );
	   	    			break;
	   	    		case 1:
	   	    			rejectFriend( selectedItem );
	   	    			break;
	   	    		case 2:
	   	    			viewInfo( selectedItem );
	   	    			break;
	   	    	}
	   	     
	   	         
	   	    }
	   	});
	   	dialog.show();
	}
   
	
	public class ListItemClick implements OnItemClickListener
	{
		int OPTION=5;
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int pos, long arg3) 
		{
			
			if( OPTION==0)
				showFriendListDialog( pos );
			
			else if( OPTION==1)
				showRequestingFriendListDialog( pos );
			
			else if( OPTION==2)
				showPendingFriendListDialog( pos );
			
			
		}
		
		public void setOPtion( int optn )
		{
			OPTION = optn;
		}
		
		public int getOPtion( )
		{
			return OPTION;
		}
	}
}

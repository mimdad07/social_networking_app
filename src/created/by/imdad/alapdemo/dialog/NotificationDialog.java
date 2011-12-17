package created.by.imdad.alapdemo.dialog;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import created.by.imdad.alapdemo.Methods;
import created.by.imdad.alapdemo.R;

public class NotificationDialog extends ParentDialog implements OnClickListener
{
	private Button 	btnOk,
			btnBack;
	private int Id=-1;
	private String  req[][];
	private Methods functions;
	private ListView list;	
	private Context context;
	private SharedPreferences preference;
	private static ArrayAdapter< String > listadapter;
	private ArrayList< String > notificationData = new ArrayList< String >();
	private ArrayList< String > receiveData;
	
	private String 	retriveNotifications = "notification_retrive.php",
					removeNotificationPage = "remove_notification.php";

	public NotificationDialog(Context context) {
		super(context);
		this.context = context;
		listadapter = new ArrayAdapter< String >( context,
				R.layout.todolist_item, notificationData );
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.notification_dialog_layout);
		
		btnOk = (Button) findViewById(R.id.btn_notification_ok);
		btnBack = (Button) findViewById(R.id.btn_notification_back);
		
		btnOk.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		
		list=(ListView)findViewById(R.id.notification_list );
		list.setOnItemClickListener( new ListItemClick() );
		list.setAdapter(listadapter);
		
		
		preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);

		functions = new Methods();
		
		req = new String[2][2];
		req[0][0] = "user_name";			req[0][1]= preference.getString("username","0" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "0");
		
		receiveData = functions.getNotifications(  functions.makeRequest( retriveNotifications, req) );
		
		for( int i=0; i< receiveData.size(); i++ )
		{
			notificationData.add( notificationData.size(), receiveData.get(i) );  
			listadapter.notifyDataSetChanged();
		}
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
    	{
    		case R.id.btn_notification_ok:
    			
    			break;
    			
    		case R.id.btn_notification_back:
    			dismiss();
    			break;
    	}
		
	}
	
	private void removeItem( int selectedItem )
	{
		notificationData.remove(selectedItem);
		listadapter.notifyDataSetChanged();
		
	}
	
	private String[][] prepareSendingData( int selectedItem)
   	{
   		String req[][] = new String[3][2];
    	req[0][0] = "user_name";					req[0][1]= preference.getString("username","" );
		req[1][0] = "user_password";				req[1][1]= preference.getString("password", "");
		req[2][0] = "notification_to_delete";       req[2][1]= notificationData.get(selectedItem);
		
		return req;
   	}
	
	protected void removeNotification( int selectedItem )
	{
		String sendData[][] = prepareSendingData( selectedItem );
		functions.makeRequest( removeNotificationPage, sendData) ;
		removeItem( selectedItem );
		
	}
	
	private void showListOptionDialog( final int selectedItem )
	{
	   	final CharSequence[] items = {"Remove Notification"};
	
	   	AlertDialog.Builder dialog = new AlertDialog.Builder( context );
	   	dialog.setItems(items, new DialogInterface.OnClickListener() 
	   	{
	   	    @Override
			public void onClick(DialogInterface dialog, int item) 
	   	    {
	   	    	
	   	    	switch(item)
	   	    	{
	   	    		case 0:
	   	    			removeNotification( selectedItem );
	   	    		
	   	    	}
	   	     
	   	         
	   	    }
	   	});
	   	dialog.show();
	}
	
	class ListItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int pos, long arg3) 
		{
			showListOptionDialog( pos );
		}
		
	}

}

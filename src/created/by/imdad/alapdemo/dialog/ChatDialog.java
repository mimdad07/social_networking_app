package created.by.imdad.alapdemo.dialog;

import java.util.ArrayList;
import java.util.StringTokenizer;

import created.by.imdad.alapdemo.Methods;
import created.by.imdad.alapdemo.R;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ChatDialog extends Dialog implements OnClickListener
{
	public static final String  PREF_CHAT = "AlapPreferenceChatting", 
								PREF_FILENAME = "AlapPreference";
	
	String messageSend = "chat_save.php";
	private int Id=-1;
	private String  req[][];
	private Button btnSend;
	private Methods functions;
	private EditText txtSend; 
	private ListView list;	
	private Context context;
	private SharedPreferences preference;
	private static ArrayAdapter< String > listadapter;
	private ArrayList< String > chatting = new ArrayList< String >();
	
	public ChatDialog(Context context) {
		super(context);
		this.context = context;
		listadapter = new ArrayAdapter< String >( context,
				R.layout.todolist_item, chatting );
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setFullScreen();
		setContentView(R.layout.chat_dialog);
		
		functions = new Methods();

		//set parent like full screen
		LayoutParams params = getWindow().getAttributes();
		params.height = LayoutParams.FILL_PARENT;
		params.width = LayoutParams.FILL_PARENT;
		getWindow().getAttributes().windowAnimations= R.style.ExitDialogAnimation;
		getWindow().setAttributes((android.view.WindowManager.LayoutParams) params); 

		req = new String[4][2];
		
		btnSend = (Button) findViewById(R.id.btn_chat_send );
		btnSend.setOnClickListener( this );
		txtSend = (EditText) findViewById(R.id.txt_chat_send ); 
		
		list=(ListView)findViewById(R.id.chat_list );
		list.setOnItemClickListener( new ListItemClick() );
		list.setAdapter(listadapter);
		
		try {
			context = context.createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		
		preference = context.getSharedPreferences(PREF_CHAT, Context.MODE_WORLD_WRITEABLE);
		String data = preference.getString("chatting", "Not Saved" );
		StringTokenizer token = new StringTokenizer( data, "-786-" );
		while( token.hasMoreTokens())
		{
			chatting.add(token.nextToken());
		}
		listadapter.notifyDataSetChanged();
		
		super.onCreate(savedInstanceState);
	} 
	
	public void setFullScreen()
    {
    	this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
    }

	@Override
	public void onClick( View view ) 
	{
		final String data = txtSend.getText().toString();
		
		String myTuit ="Me : " + data;
		chatting.add( 0, myTuit );      
		listadapter.notifyDataSetChanged();
		
		addTuit( myTuit );
		new Thread(
				new Runnable()
				{
					@Override
					public void run()
					{
						//send message;
						preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);
					
						req[0][0] = "friend_id";		req[0][1]= "" + getID();						
						req[1][0] = "my_username";		req[1][1] = preference.getString("username","0" );
						req[2][0] = "my_password";  	req[2][1] = preference.getString("password", "0");
						req[3][0] = "my_chat";			req[3][1] = data;
						
						functions.makeRequest( messageSend, req );
						
					}
				}).start();
		
		
		txtSend.setText( "" );
		
	}
	
	public void addTuit( String tut)
	{
		preference = context.getSharedPreferences(PREF_CHAT, Context.MODE_WORLD_WRITEABLE);
		String old_chat = preference.getString("chatting", "Not Saved" );
		old_chat = tut + "-786-" + old_chat;
		
		SharedPreferences.Editor edt = preference.edit();
		edt.putString("chatting", old_chat);		
		edt.commit();
	}
	
	public void setID( int id )
	{
		Id = id;
	}
	
	public int getID()
	{
		return Id;
	}
	
	public class ListItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int pos, long arg3) 
		{
			String select = chatting.get(pos);
			int index = select.indexOf(':')+1;
			txtSend.setText( select.substring(index, select.length()).trim());
		}
		
	}

} 
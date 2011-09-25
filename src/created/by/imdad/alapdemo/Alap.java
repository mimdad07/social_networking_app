package created.by.imdad.alapdemo;

import java.io.IOException;

import created.by.imdad.alapdemo.tab.Tab;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;  
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Alap extends Activity implements OnClickListener, OnTouchListener
{	
	private Methods functions;
	private int progress=0, run=0, range =100000,last = 32768, which=1;
	ProgressBar progressBar, progressBar1; 

	Button  login, 
			signup_info, 
			welcome;
	TextView create_a_new_account, 
			 forgate_username; 
	 
	CheckBox autologin;
	
	public static final String PREF_FILENAME = "AlapPreference"; 
	SharedPreferences preference;
	
	String 	sendUsernameAndPassword = "send_username_and_password.php",
			myIpPage= "my_ip.php";
	Thread th;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		setFullScreen();	
		showInternetSettingDialog();
		setContentView( R.layout.firstlayout );
		
		functions = new Methods();
		
		preference = getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);
		
		progressBar = (ProgressBar) findViewById( R.id.start_progressbar );
		progressBar1 = (ProgressBar) findViewById( R.id.start_progressbar1 );
		
		login = (Button ) findViewById( R.id.login );
		signup_info = (Button ) findViewById( R.id.signupinfo ); 
		welcome = (Button ) findViewById( R.id.welcome );
		autologin = ( CheckBox ) findViewById( R.id.autoLogin );
		create_a_new_account = (TextView ) findViewById( R.id.createanewaccount );
		forgate_username = (TextView ) findViewById( R.id.forgateusername ); 
		
		login.setOnClickListener( this );
		create_a_new_account.setOnClickListener( this );
		forgate_username.setOnClickListener( this );
		signup_info.setOnClickListener( this );
		welcome.setOnClickListener( this );
		
		create_a_new_account.setOnTouchListener(this);
		forgate_username.setOnTouchListener(this);
		
		EditText username = (EditText)findViewById( R.id.txtUserName);
		EditText password = (EditText)findViewById( R.id.txtPassword);
		
		String user = preference.getString("username","0" );
        String pass = preference.getString("password", "0");
        String check = preference.getString("checked", "no");
        
        if( check.equals("yes"))
        {
                username.setText(user);
                password.setText(pass);
        }
        
       // showLogo();
		
		renderProgressBar(progressBar  );
		
		
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		finish();
	}

	public void showLogo()
	{
		ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
		vf.setInAnimation(AnimationUtils.loadAnimation( getApplicationContext(), R.anim.logo_in ));
		

		//vf.showNext();
	}
	public void renderProgressBar(ProgressBar p )
    {
		final ProgressBar progressBar=p;
		th = new Thread( new Runnable()
        {
			@Override
			public void run()
    		{
    			while( run<range )
    			{
    				try
    				{
    					handle.sendMessage( handle.obtainMessage());
    					Thread.sleep(10);
    				}
    				catch( Throwable t){}   				  				   			
    			}   			 
    		}  
    	
    		Handler handle = new Handler()
    		{
				@Override
				public void handleMessage( Message msg)
    			{
					run++;
					if( run>=200)
						progress++;
					
					if( run==200)
					{
						ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
						
						vf.showNext();
					}
    				
                   if( progress<=100)
    				progressBar.setProgress(progress);
    				
    				if( progress == 20 &&  !autologin.isChecked())
    				{
    					ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
    					if( which ==1 )
    					{
    						vf.setInAnimation(AnimationUtils.loadAnimation( getApplicationContext(), R.anim.right_in ));
    						vf.setOutAnimation( AnimationUtils.loadAnimation( getApplicationContext(), R.anim.left_out ));
    					}
    					else if(which==2)
    					{
    					}
    					vf.showNext();
    				}
    				
    				if( progress == 20 &&  autologin.isChecked())
    				{   					
    					ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
    					vf.showNext();
    					vf.setAnimation(AnimationUtils.loadAnimation( getApplicationContext(), R.anim.in_anim));   					
    					vf.showNext();
    				}
    				
    				if( (last+progress) == 100 )
    				{
    				
    					ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
    					vf.setAnimation(AnimationUtils.loadAnimation( getApplicationContext(), R.anim.clear));    					
    					vf.showNext();   				
    				}  			
    			}   		
    		};
    	});
		th.start();  
    }
		
	@Override
	public boolean onTouch(View view, MotionEvent action) 
	{
		if( action.getAction()== MotionEvent.ACTION_UP ) //avoiding down listener
		{
		if( view == findViewById( R.id.createanewaccount ))
		{
			ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
			vf.setInAnimation( AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_popup));
			vf.setOutAnimation( AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_popup));
			
			vf.showNext();
		}
		
		
		if( view == findViewById( R.id.forgateusername ))
		{
			
			final String data[][] = new String[1][2];
			
			Context mContext = getApplicationContext();
			AlertDialog.Builder forgateuser = new AlertDialog.Builder( this );
			
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.send_username_and_password_dialog_layout,
			                               (ViewGroup) findViewById(R.id.layout_root));

			final EditText sendMail = (EditText)findViewById( R.id.send_username_text);
		    data[0][0] = "user_email";
		    //data[0][1] = sendMail.getText().toString().trim();
			
			forgateuser.setPositiveButton( "Yes", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface arg0, int arg1) 
				{
				    		data[0][1] = sendMail.getText().toString().trim();
							functions.makeRequest( sendUsernameAndPassword, data);
				   
				}
			});
			
			forgateuser.setNegativeButton( "No", new DialogInterface.OnClickListener() 
			{
				@Override
				public void onClick(DialogInterface arg0, int arg1) 
				{
					//Toast.makeText(getApplicationContext(), "No button Clicked", Toast.LENGTH_SHORT).show();
				}
			});
			
			forgateuser.setView(layout);
			
			forgateuser.show();
		}
		}
		
		return false;
	}
	
	@Override
	public void onClick(View view) 
	{
		if( view == findViewById( R.id.login ))
		{
			ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
			
			EditText username = (EditText)findViewById( R.id.txtUserName);
			EditText password = (EditText)findViewById( R.id.txtPassword);
			CheckBox checkBox = (CheckBox) findViewById(R.id.autoLogin);
			
			String data[][] = new String[2][2];
			
			data[0][0] = "user_name";			data[0][1] = username.getText().toString().trim();
			data[1][0] = "user_password";		data[1][1] = password.getText().toString().trim();
			
			try {
				if( functions.verifyLogin( data ))
				{
					Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT ).show();
	
					//put my ip in the server user_ip field.
					//functions.makeRequest(myIpPage, data);
					 
					progress=20;
					run=100;
					last=0;
					
					which=2;
					renderProgressBar(progressBar1 );
					
					final String d[][]= new String[2][2];
					d[0][0] = "username";	d[0][1] = username.getText().toString().trim();
					d[1][0] = "password";	d[1][1] = password.getText().toString().trim();
					
					if( checkBox.isChecked())
					{
						SharedPreferences.Editor edt = preference.edit();
						edt.putString("checked", "yes" );
						edt.putString("username", username.getText().toString().trim());
						edt.putString("password", password.getText().toString().trim());
						
						edt.commit();
					}
					else
					{
						SharedPreferences.Editor edt = preference.edit();
						edt.putString("checked", "no" );
						edt.putString("username", username.getText().toString().trim());
						edt.putString("password", password.getText().toString().trim());
						edt.commit();
						
					}

					vf.setOutAnimation( AnimationUtils.loadAnimation( getApplicationContext(), R.anim.right_out));
					vf.showNext();
					vf.setInAnimation(AnimationUtils.loadAnimation( getApplicationContext(), R.anim.left_in ));
					vf.showNext();
					
					//initiat the tab list if login successful
					Intent intent = new Intent( this, Tab.class );
					startActivity( intent );
				}
				else
					Toast.makeText(getApplicationContext(), "Login Unsuccessfull", Toast.LENGTH_SHORT ).show();
					
			}
			catch (IOException e) {
				e.printStackTrace();
		
			}
			
			
		}
		
		
		
		
		if( view == findViewById( R.id.signupinfo ))
		{
			ViewFlipper vf = (ViewFlipper) findViewById( R.id.flipFirst);
			vf.setInAnimation(AnimationUtils.loadAnimation( getApplicationContext(), R.anim.left_in));
			vf.setOutAnimation(AnimationUtils.loadAnimation( getApplicationContext(), R.anim.out_popup));

			EditText   username,
					   password,
					   repassword,
					   email;
			
			username = (EditText)findViewById( R.id.signup_username);
			password = (EditText)findViewById( R.id.signup_password);
			repassword = (EditText)findViewById( R.id.signup_repassword);
			email =  (EditText)findViewById( R.id.signup_email );
			
			
			//required for storing ip address.
			String data[][] = new String[2][2];			
			data[0][0] = "user_name";			data[0][1] = username.getText().toString().trim();
			data[1][0] = "user_password";		data[1][1] = password.getText().toString().trim();
			
			
			try {
				if( functions.makeSignup(  username.getText().toString().trim(), 
										   password.getText().toString().trim(), 
										   repassword.getText().toString().trim(),
										   email.getText().toString().trim()))
				{
					Toast.makeText(getApplicationContext(), "Signup Successfull", Toast.LENGTH_SHORT ).show();
				
					//Save username and password
					SharedPreferences.Editor edt = preference.edit();
					edt.putString("checked", "no" );
					edt.putString("username", username.getText().toString().trim());
					edt.putString("password", password.getText().toString().trim());
					edt.commit();
					
					//call this function to store ip address.
					//functions.makeRequest(myIpPage, data);
					
					progress=20;
					run=100;
					last=0;
					
					renderProgressBar(progressBar1);
					vf.showNext(); 
					vf.showNext();
					
					Intent intent = new Intent( this, Tab.class );
					startActivity( intent );
					
				}
				else
					Toast.makeText(getApplicationContext(), "Signup Unsuccessfull", Toast.LENGTH_SHORT ).show();
					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//---------latter i will delete this
		if( view == findViewById( R.id.welcome ))
		{
			Intent intent = new Intent( this, Tab.class );
			startActivity( intent );
		}
		
	}
	
	
	 public void setFullScreen()
	 {
	    	this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
	    	
	 }
	 
		public void showInternetSettingDialog()
		{
			if( !isInternetOn())
			{
				//internet connection does not exist.
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle( "Internet Connection does not exist")
					   .setMessage("Do you want to bring up connectivity settings")
				       .setCancelable(false)
				       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				           @Override
						public void onClick(DialogInterface dialog, int id) {
				                Intent con_intent = new Intent( android.provider.Settings.ACTION_AIRPLANE_MODE_SETTINGS);
				                startActivity(con_intent);
				           }
				       })
				       .setNegativeButton("No", new DialogInterface.OnClickListener() {
				           @Override
						public void onClick(DialogInterface dialog, int id) {
				        	   finish();
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				alert.show();
			}

		}
		public  boolean isInternetOn() 
	    {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

	        if (networkInfo != null && networkInfo.isConnected()) 
	        {
	            return true;
	        }
	        return false;

	    }

		
}

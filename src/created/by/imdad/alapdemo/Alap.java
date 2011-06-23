package created.by.imdad.alapdemo;
 

import created.by.imdad.alapdemo.tab.Tab;

import android.app.Activity; 
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Alap extends Activity implements OnClickListener, OnTouchListener
{	 
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
		setContentView( R.layout.firstlayout ); 
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
		 
		renderProgressBar(progressBar  );
		 
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		finish();
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
}

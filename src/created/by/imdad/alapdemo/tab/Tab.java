package created.by.imdad.alapdemo.tab;

import created.by.imdad.alapdemo.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;

public class Tab extends TabActivity 
{
    @Override
	protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
    	setFullScreen();
        TabHost tabHost = getTabHost();
         
        LayoutInflater.from(this).inflate(R.layout.tabs, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("tab1")
                .setIndicator("List")
                .setContent( new Intent( this, TabList.class )));
        
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Search")
                .setContent( new Intent( this, TabSearch.class )));
        
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Events")
                .setContent( new Intent( this, TabEvent.class )));
        
        tabHost.addTab(tabHost.newTabSpec("tab2")
                .setIndicator("Profile")
                .setContent(new Intent( this, TabProfile.class ) ));
    }
    
    public void setFullScreen()
    {
    	this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
    	
    }
}


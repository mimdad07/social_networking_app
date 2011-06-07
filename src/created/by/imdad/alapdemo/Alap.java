package created.by.imdad.alapdemo;
 

import created.by.imdad.alapdemo.tab.Tab;

import android.app.Activity; 
import android.content.Intent; 
import android.os.Bundle;   
public class Alap extends Activity 
{	  
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		 
		Intent intent = new Intent( this, Tab.class );
		startActivity( intent );
	} 
}

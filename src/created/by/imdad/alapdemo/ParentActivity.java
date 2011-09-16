package created.by.imdad.alapdemo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ParentActivity extends Activity 
{
	private Methods functions;
	public static final String PREF_FILENAME = "AlapPreference"; 
	SharedPreferences preference;
	Context context = null;
	String logoutPage = "set_logout_status.php";
 
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		 
	}

	 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.menu_logout:
	        logout();
	        return true;
	    case R.id.menu_setting:
	        setting();
	        return true;
	        
	    case R.id.menu_about:
	        about();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

	
	public void logout()
	{
		
		
		//get username and password from preference
		String req[][] = new String[2][2];
		req[0][0] = "user_name";			req[0][1]= preference.getString("username","0" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "0");
		
		functions.makeRequest( logoutPage, req );
		
	}
	
	public void setting()
	{
		
	}
	
	public void about()
	{
		
		About about = new About( ParentActivity.this );
		about.show();
		
	}

}

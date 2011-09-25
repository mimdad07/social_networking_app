package created.by.imdad.alapdemo.tab;

import java.util.ArrayList;
import java.util.List;
 
import created.by.imdad.alapdemo.Methods;
import created.by.imdad.alapdemo.ParentActivity;
import created.by.imdad.alapdemo.R;
import created.by.imdad.alapdemo.dialog.ViewInfoDialog;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
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
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class TabSearch extends ParentActivity implements OnClickListener
{	 
	private Methods functions;
	private ListView list;	
	private TableLayout tablelut;
	
	private List< String []> receiveData;
	private ArrayList< String > names = new ArrayList< String >();
	private ArrayList< String > ids = new ArrayList< String >();
	private ArrayAdapter< String > listadapter;
	private Button search;
	
	private Spinner spnrAge, 
					spnrSex, 
					spnrCountry;
	
	private String  searchPage="search_info.php",
		            findAllInformPage = "all_information.php",  
		            addMe = "add_me_as_friend.php",
					output="",
					valueList[][];

	public static final String PREF_FILENAME = "AlapPreference"; 
	SharedPreferences preference;
	Context context = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		setFullScreen();	
		setContentView( R.layout.tab_search );
		
		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {}
		preference = context.getSharedPreferences(PREF_FILENAME, Context.MODE_WORLD_WRITEABLE);

		tablelut = (TableLayout)findViewById( R.id.search_table);		
		list=(ListView)findViewById(R.id.search_tab_list );
		

		listadapter = new ArrayAdapter< String >( this,
				R.layout.todolist_item, names );
		list.setAdapter(listadapter);
		list.setOnItemClickListener( new ListItemClick() );
		
		
		functions = new Methods();
		
		valueList = new String[3][2];
		
		
		//default values if not selected any spinner
		valueList[0][1] = "Female";
		valueList[1][1] = "15 - 20";
		valueList[2][1] = "Afghanistan";

		spnrSex = (Spinner)findViewById( R.id.spnr_srch_sex );
		spnrAge= (Spinner)findViewById( R.id.spnr_srch_age );
		spnrCountry = (Spinner)findViewById( R.id.spnr_srch_country );
		
		search = (Button)findViewById( R.id.search);
		search.setOnClickListener( this );
    
		  //configure spinner for Sex
	    ArrayAdapter<CharSequence> adapter =  new ArrayAdapter<CharSequence>(
												this, 
												android.R.layout.simple_spinner_dropdown_item, 
												sex );
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnrSex.setAdapter(adapter);
	    spnrSex.setOnItemSelectedListener(new SpnrSexSelected() );
	    
	    //configure spinner for Age
	    adapter =  new ArrayAdapter<CharSequence>(
												this, 
												android.R.layout.simple_spinner_dropdown_item, 
												age );
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spnrAge.setAdapter(adapter);
	    spnrAge.setOnItemSelectedListener(new SpnrAgeSelected() );
	    
	    //configure spinner for country
	    adapter =  new ArrayAdapter<CharSequence>(
	   										this, 
	   										android.R.layout.simple_spinner_dropdown_item, 
	   										country_name );
	   adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	   spnrCountry.setAdapter(adapter);
	   spnrCountry.setOnItemSelectedListener(new SpnrCountrySelected() );		
	}
	
	// interface for Sex spinner.
	private class SpnrSexSelected implements OnItemSelectedListener 
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,long id) 
		{
			valueList[0][0] = "user_sex";
			valueList[0][1] = parent.getItemAtPosition( pos ).toString();	
		}
	
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
		}		
	};
	
	
	//interface for Age spinner.
	private class SpnrAgeSelected implements OnItemSelectedListener 
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,long id) 
		{
			valueList[1][0] = "user_age";
			valueList[1][1] = parent.getItemAtPosition( pos ).toString();			
		}
	 
		@Override
		public void onNothingSelected(AdapterView<?> arg) 
		{
		}
		
	};
	
	//interface for Country spinner.
	private class SpnrCountrySelected implements OnItemSelectedListener 
	{
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,long id) 
		{
			valueList[2][0] = "user_country";
			valueList[2][1] = parent.getItemAtPosition( pos ).toString();
		}
	
		@Override
		public void onNothingSelected(AdapterView<?> arg0) 
		{
		}		
	};

	@Override
	public void onClick(View view) 
	{
		if( view == findViewById( R.id.search ))
		{
			new BackgroundWork().execute();
			 
		}
	}
	
    private void showListDialog( final int selectedItem )
    {
    	final CharSequence[] items = {"Add As A Friend", "Send A Personal Message", "View Info"};

    	AlertDialog.Builder dialog = new AlertDialog.Builder( this );
    	dialog.setItems(items, new DialogInterface.OnClickListener() 
    	{
    	    @Override
			public void onClick(DialogInterface dialog, int item) 
    	    {
    	    	
    	    	switch(item)
    	    	{
    	    		case 0:
    	    			addMeAsAFriend( selectedItem );
    	    			break;
    	    		case 1:
    	    			break;
    	    		case 2:
    	    			viewInfo( selectedItem );
    	    			
    	    			Toast.makeText(getBaseContext(), "find info :" + selectedItem, Toast.LENGTH_LONG).show();
    	    			break;
    	    	}
    	     
    	         
    	    }
    	});
    	dialog.show();
    }
  
   
    protected void addMeAsAFriend(int selectedItem ) 
    {
    	String req[][] = new String[3][2];
    	req[0][0] = "user_name";			req[0][1]= preference.getString("username","" );
		req[1][0] = "user_password";		req[1][1]= preference.getString("password", "");
    	req[2][0] = "friends_id";           req[2][1]= ids.get( selectedItem );
        functions.makeRequest(addMe, req);
	}


	private void viewInfo( int selectedItem)
    {
    	
    	String id = ids.get(selectedItem),     //find clicked id and will have to query information for this id.
		 st = "";  
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					dlog.dismiss();
					
				}
			}).start();
	
	
    }

	//set full screen
 	public void setFullScreen()
	{
		this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
		
	}

	

	public class ListItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int pos, long arg3) 
		{
			showListDialog( pos );
		}
		
	}
	
	private class BackgroundWork extends AsyncTask<Void, Void, Void>
	{		
		ProgressDialog dialog;

		@Override
		protected Void doInBackground(Void... params) 
		{
			receiveData = functions.getIdAndFullName(  functions.makeRequest(searchPage, valueList) );
			
			for( int i=0; i< receiveData.size(); i++ )
			{
				String st[]=receiveData.get(i);
				names.add( names.size(), st[1] );      // store Names
				ids.add(ids.size(), st[0]);            // store Ids

				
			}
			receiveData.clear();
			output="";              //must reset
			
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(TabSearch.this, "","Searching. Please wait...", true);
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(Void result) 
		{
			tablelut.setVisibility(View.GONE); 
			listadapter.notifyDataSetChanged();
			dialog.dismiss();
		}
	}
	
	
	static String country_name[] = {  
		"Afghanistan",  "Australia", "Bangladesh", "Bhutan", "Brazil",  "Canada", "China",
		"Colombia", "Denmark", "Egypt", "Finland", "France", "Germany", "Ghana", "Greece", 
		"Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", 
		"Israel", "Italy", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kuwait", "Lebanon", "Libya", 
		"Malaysia", "Mexico", "Mongolia", "Morocco", "Myanmar", "Namibia","Nepal", "Netherlands", 
		"New Zealand", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Philippines",
		"Poland", "Portugal", "Romania", "Russia", "Saudi Arabia", "Singapore", "Slovakia", "Somalia", 
		"South Africa", "South Korea", "Spain", "Sri Lanka", "Sudan", "Swaziland", "Sweden",
		"Switzerland", "Taiwan", "Tajikistan", "Thailand", "Turkey", "Uganda",
		"United Arab Emirates", "United Kingdom", "United States", "Uruguay", "Uzbekistan",
		"Venezuela", "Yemen", "Zimbabwe"
		};
	
	static String age[] = { 
		"15 - 20", "20 - 25", "25 - 30", "30 - 35", "35 - 40", "40 - 50"
	};
	
	static String sex[] = {
		"Female", "Male"
	};

}
package created.by.imdad.alapdemo.tab;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import created.by.imdad.alapdemo.Methods;
import created.by.imdad.alapdemo.ParentActivity;
import created.by.imdad.alapdemo.R;

public class TabProfile extends ParentActivity implements OnClickListener {
	private Methods functions;
	private String updateProfilePage = "update_profile.php",
			myAllInformPage = "my_all_information.php", 
			username = "",
			userpassword = "", valueList[][], req[][];
	private Button save;
	private EditText fullName, address, mobileNo, hobby, religion;
	private Spinner spnrCountry, spnrAge, spnrSex, spnrWork;
	public static final String PREF_FILENAME = "AlapPreference";
	SharedPreferences preference;
	ArrayAdapter<CharSequence> countryAdapter, genderAdapter, ageAdapter, workFieldAdapter ;
	Context context = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		setContentView(R.layout.tab_profile);

		try {
			context = createPackageContext("created.by.imdad.alapdemo", 0);
		} catch (NameNotFoundException e) {
		}
		preference = context.getSharedPreferences(PREF_FILENAME,

		Context.MODE_WORLD_WRITEABLE);

		username = preference.getString("username", "");
		userpassword = preference.getString("password", "");

		req = new String[2][2];
		req[0][0] = "user_name";		req[0][1] = username;
		req[1][0] = "user_password";	req[1][1] = userpassword;

		functions = new Methods();
		valueList = new String[11][2];

		save = (Button) findViewById(R.id.savesetting);
		save.setOnClickListener(this);

		fullName = (EditText) findViewById(R.id.fullname);
		address = (EditText) findViewById(R.id.address);
		mobileNo = (EditText) findViewById(R.id.mobileno);
		hobby = (EditText) findViewById(R.id.hobby);
		religion = (EditText) findViewById(R.id.religion);

		spnrCountry = (Spinner) findViewById(R.id.spnr_set_country);
		spnrAge = (Spinner) findViewById(R.id.spnr_set_age);
		spnrSex = (Spinner) findViewById(R.id.spnr_set_sex);
		spnrWork = (Spinner) findViewById(R.id.spnr_set_workfld);

		// configure spinner for country
		countryAdapter = new ArrayAdapter<CharSequence>(
				this,
				android.R.layout.simple_spinner_dropdown_item, 
				country_name);
		countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnrCountry.setAdapter(countryAdapter);
		spnrCountry.setOnItemSelectedListener(new SpnrCountrySelected());

		// configure spinner for Age
		ageAdapter = new ArrayAdapter<CharSequence>(
				this,
				android.R.layout.simple_spinner_dropdown_item, 
				age);
		ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnrAge.setAdapter(ageAdapter);
		spnrAge.setOnItemSelectedListener(new SpnrAgeSelected());

		// configure spinner for Work Field
		workFieldAdapter = new ArrayAdapter<CharSequence>(
				this,	
				android.R.layout.simple_spinner_dropdown_item,	
				work_field);
		workFieldAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnrWork.setAdapter(workFieldAdapter);
		spnrWork.setOnItemSelectedListener(new SpnrWorkSelected());

		// configure spinner for Sex
		genderAdapter = new ArrayAdapter<CharSequence>(
				this,
				android.R.layout.simple_spinner_dropdown_item,
				sex);
		genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnrSex.setAdapter(genderAdapter);
		spnrSex.setOnItemSelectedListener(new SpnrSexSelected());

	}

	@Override
	protected void onResume() {
		
		new BackgroundWork().execute();
		
		super.onResume();
	}
	
	@Override
	public void onClick(View view) {

		valueList[0][0] = "user_name";			valueList[0][1] = username; // for user name
		valueList[1][0] = "user_password";		valueList[1][1] = userpassword; // and password
		valueList[2][0] = "user_fullname";		valueList[2][1] = fullName.getText().toString().trim();
		valueList[3][0] = "user_address";		valueList[3][1] = address.getText().toString().trim();

		valueList[7][0] = "user_mobileno";		valueList[7][1] = mobileNo.getText().toString().trim();
		valueList[8][0] = "user_hobby";			valueList[8][1] = hobby.getText().toString().trim();
		valueList[9][0] = "user_religion";		valueList[9][1] = religion.getText().toString().trim();

		new SaveInBackground().execute();
	}

	// set full screen
	public void setFullScreen() {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	}

	// interface for Age spinner.
	private class SpnrCountrySelected implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long id) {
			valueList[4][0] = "user_country";
			valueList[4][1] = parent.getItemAtPosition(pos).toString().trim();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	};

	// interface for Work Country spinner.
	private class SpnrAgeSelected implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long id) {
			valueList[5][0] = "user_age";
			valueList[5][1] = parent.getItemAtPosition(pos).toString().trim();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg) {
		}

	};

	// interface for Age spinner.
	private class SpnrSexSelected implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long id) {
			valueList[6][0] = "user_sex";
			valueList[6][1] = parent.getItemAtPosition(pos).toString().trim();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	};

	// interface for Work Field spinner.
	private class SpnrWorkSelected implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int pos,
				long id) {
			valueList[10][0] = "user_workingfield";
			valueList[10][1] = parent.getItemAtPosition(pos).toString().trim();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}

	};
	
	private class BackgroundWork extends AsyncTask<Void, Void, Void>
	{
		List<String> info;
		ProgressDialog dialog;

		@Override
		protected Void doInBackground(Void... params) {
			info = functions.queryAllInformation(functions.makeRequest(myAllInformPage, req));
			return null;
		}
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(TabProfile.this, "","Loading. Please wait...", true);
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(Void result) {
			int index = 0;

			

			fullName.setText(info.get(0));
			address.setText(info.get(1));
			mobileNo.setText(info.get(5));
			hobby.setText(info.get(6));
			religion.setText(info.get(7));


			for( int i=0; i<country_name.length; i++)
			{
				if( country_name[i].equals(info.get(2)))
				{
					index = i;
					break;
				}
			}		
			spnrCountry.setSelection(index);
			countryAdapter.notifyDataSetChanged();
			
			index=0;
			for( int i=0; i<age.length; i++)
			{
				if( age[i].equals(info.get(3)))
				{
					index = i;
					break;
				}
			}	
			spnrAge.setSelection(index);
			ageAdapter.notifyDataSetChanged();
			
			if( info.get(4).equals("Male"))
				spnrSex.setSelection(1);
			else
				spnrSex.setSelection(0);
			genderAdapter.notifyDataSetChanged();
			
			index=0;
			for( int i=0; i<work_field.length; i++)
			{
				if( work_field[i].equals(info.get(8)))
				{
					index = i;
					break;
				}
			}	
			spnrWork.setSelection(index);
			workFieldAdapter.notifyDataSetChanged();
			dialog.dismiss();

			
			super.onPostExecute(result);
		}
		
	}
	
	private class SaveInBackground extends AsyncTask<Void, Void, Void>
	{		
		ProgressDialog dialog;
		@Override
		protected Void doInBackground(Void... params) 
		{
			functions.makeRequest( updateProfilePage, valueList);
			return null;
		}
		@Override
		protected void onPreExecute() 
		{
			dialog = ProgressDialog.show(TabProfile.this, "","Saving. Please wait...", true);
			super.onPreExecute();
		}
		@Override
		protected void onPostExecute(Void result) 
		{
			dialog.dismiss();
		}
	}


	static String country_name[] = { "Afghanistan", "Australia", "Bangladesh",
			"Bhutan", "Brazil", "Canada", "China", "Colombia", "Denmark",
			"Egypt", "Finland", "France", "Germany", "Ghana", "Greece",
			"Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran",
			"Iraq", "Ireland", "Israel", "Italy", "Japan", "Jordan",
			"Kazakhstan", "Kenya", "Kuwait", "Lebanon", "Libya", "Malaysia",
			"Mexico", "Mongolia", "Morocco", "Myanmar", "Namibia", "Nepal",
			"Netherlands", "New Zealand", "Niger", "Nigeria", "North Korea",
			"Norway", "Oman", "Pakistan", "Philippines", "Poland", "Portugal",
			"Romania", "Russia", "Saudi Arabia", "Singapore", "Slovakia",
			"Somalia", "South Africa", "South Korea", "Spain", "Sri Lanka",
			"Sudan", "Swaziland", "Sweden", "Switzerland", "Taiwan",
			"Tajikistan", "Thailand", "Turkey", "Uganda",
			"United Arab Emirates", "United Kingdom", "United States",
			"Uruguay", "Uzbekistan", "Venezuela", "Yemen", "Zimbabwe" };

	static String age[] = { "15 - 20", "20 - 25", "25 - 30", "30 - 35",
			"35 - 40", "40 - 50" };

	static String sex[] = { "Female", "Male" };

	static String work_field[] = { "Doctor", "Business Man", "Engineer",
			"Student", "Teacher", "Other" };

}

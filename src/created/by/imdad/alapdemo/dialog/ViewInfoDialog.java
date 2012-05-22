package created.by.imdad.alapdemo.dialog;

import java.util.List;

import created.by.imdad.alapdemo.R;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewInfoDialog extends ParentDialog
{
	private Context context;
	private List<String> info;
	private Button btn_ok;
	private TextView txt_name, 
					 txt_address,
					 txt_country, 
					 txt_age,
					 txt_sex,
					 txt_mobileno,
					 txt_hobby,
					 txt_religion,
					 txt_work; 

	public ViewInfoDialog(Context context) {
		super(context);
		this.context = context;
	
	}
	
	public ViewInfoDialog(Context context, List<String> list) {
		super(context);
		this.context = context;
		info = list;
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setFullScreen();
		setContentView(R.layout.view_info_dialog_layout);		
		setLayoutParameter();

		txt_name = (TextView)findViewById(R.id.txtFullName_view_info_dialog); 
		txt_address = (TextView)findViewById(R.id.txtAddress_view_info_dialog); 
		txt_country = (TextView)findViewById(R.id.txtCountry_view_info_dialog); 
		txt_age = (TextView)findViewById(R.id.txtAge_view_info_dialog); 
		txt_sex = (TextView)findViewById(R.id.txtSex_view_info_dialog); 
		txt_mobileno = (TextView)findViewById(R.id.txtMobileno_view_info_dialog); 
		txt_hobby = (TextView)findViewById(R.id.txtHobby_view_info_dialog); 
		txt_religion = (TextView)findViewById(R.id.txtReligion_view_info_dialog); 
		txt_work = (TextView)findViewById(R.id.txtWorkField_view_info_dialog); 
		
		try{
			 txt_name.setText( info.get(0) ); 
			 txt_address.setText( info.get(1) ); 
			 txt_country.setText( info.get(2) ); 
			 txt_age.setText( info.get(3) ); 
			 txt_sex.setText( info.get(4) ); 
			 txt_mobileno.setText( info.get(5) ); 
			 txt_hobby.setText( info.get(6) ); 
			 txt_religion.setText( info.get(7) ); 
			 txt_work.setText( info.get(8) ); 
		}
		catch( Exception ex){}

		btn_ok = (Button) findViewById(R.id.btnOk_view_info_dialog);
		btn_ok.setOnClickListener( new  View.OnClickListener()
		{
			@Override
			public void onClick( View v)
			{
				dismiss();
			}
		});
		
	}
	

	
} 
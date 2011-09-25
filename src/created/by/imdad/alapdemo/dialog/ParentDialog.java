package created.by.imdad.alapdemo.dialog;

import created.by.imdad.alapdemo.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;

public class ParentDialog extends Dialog
{
	public static final String PREF_FILENAME = "AlapPreference";
	
	public ParentDialog(Context context) 
	{
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		setFullScreen();
		setLayoutParameter();
	}
	
	public void setFullScreen()
    {
    	this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
    }
	
	public void setLayoutParameter()
	{

		//set parent like full screen
		LayoutParams params = getWindow().getAttributes();
		params.width = LayoutParams.FILL_PARENT;
		getWindow().getAttributes().windowAnimations = R.style.ExitDialogAnimation;
		getWindow().setAttributes( (android.view.WindowManager.LayoutParams) params);
	}


}

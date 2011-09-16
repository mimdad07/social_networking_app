package created.by.imdad.alapdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class About extends Dialog implements OnClickListener{

	private Button btnSend;
	public About(Context context) {
		super(context);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setFullScreen();
		setContentView(R.layout.about);

		//set parent like full screen
		LayoutParams params = getWindow().getAttributes();
		params.height = LayoutParams.FILL_PARENT;
		params.width = LayoutParams.FILL_PARENT;
		getWindow().getAttributes().windowAnimations= R.style.ExitDialogAnimation;
		getWindow().setAttributes((android.view.WindowManager.LayoutParams) params); 
		
		
		btnSend = (Button) findViewById(R.id.btn_about_ok );
		btnSend.setOnClickListener( this );

	}
	public void setFullScreen()
    {
    	this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        
       
    	
    }
	@Override
	public void onClick(View v) {
		
		if( v == findViewById(R.id.btn_about_ok))
		{
			About.this.cancel();
		}
		
	}


}

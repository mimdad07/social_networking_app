package created.by.imdad.alapdemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;


public class TodoListItemView extends TextView{
	
	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;

	
	public TodoListItemView( Context context, AttributeSet aSet, int ds){
		super(context, aSet, ds);
		init();
	}
	
	public TodoListItemView(Context context) {
		super(context);
		init();
		
	}
	public TodoListItemView( Context context, AttributeSet aSet){
		super(context, aSet);
		init();
	}
	
	private void init(){
		Resources myResources=getResources();
		
		marginPaint= new Paint(Paint.DEV_KERN_TEXT_FLAG);
		marginPaint.setColor(myResources.getColor(R.color.notepad_paper));
		
		linePaint= new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(myResources.getColor(R.color.notepad_lines));
		
		paperColor= myResources.getColor(R.color.notepad_paper);
		margin=myResources.getDimension(R.dimen.notepad_margin);
		
	}
	
	@Override
	public void onDraw(Canvas canvas){
		
		canvas.drawColor(paperColor);
		
		canvas.drawLine(0, 0, getMeasuredHeight(), 0, linePaint);
		canvas.drawLine(0, getMeasuredHeight(),
		getMeasuredWidth(), getMeasuredHeight(),
		linePaint);
		
		canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
		
		canvas.save();
		canvas.translate(margin, 0);
		
		super.onDraw(canvas);
		canvas.restore();
	}

}

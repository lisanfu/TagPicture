package com.example;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class PicturetagView extends RelativeLayout implements OnEditorActionListener {
	private Context context;
	private TextView tvPictureTagLabel1;
	private EditText etPictureTagLabel1;
	private View loTag;
	public enum Status{Normal,Edit};
	public enum Direction{Left,Right};
	private Direction direction=Direction.Left;
	private InputMethodManager imn;
	private static final int ViewWidth=80;
	private static final int ViewHeight=50;
	public PicturetagView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public PicturetagView(Context context, Direction direction) {
		super(context);
		this.context=context;
		this.direction=direction;
		initViews();
		init();
		initEvents();
		// TODO Auto-generated constructor stub
	}
	//初始化视图
	/** 初始化视图 **/
	protected void initViews(){
		LayoutInflater.from(context).inflate(R.layout.picturetagview, this,true);
		tvPictureTagLabel1 = (TextView) findViewById(R.id.tvPictureTagLabel1);
		etPictureTagLabel1 = (EditText) findViewById(R.id.etPictureTagLabel1);
		loTag = findViewById(R.id.loTag);
	}
	protected void init()
	{
		imn=(InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
		directionChange();
	}
	//初始化事件
	protected void initEvents()
	{
		etPictureTagLabel1.setOnEditorActionListener(this);
	}
	public void setStatus(Status status)
	{
		switch(status)
		{
		case Normal:
			tvPictureTagLabel1.setVisibility(View.VISIBLE);
			etPictureTagLabel1.clearFocus();
			tvPictureTagLabel1.setText(etPictureTagLabel1.getText());
			etPictureTagLabel1.setVisibility(View.GONE);
			//隐藏键盘
			imn.hideSoftInputFromWindow(etPictureTagLabel1.getWindowToken(), 0);
			break;
		case Edit:
			tvPictureTagLabel1.setVisibility(View.GONE);
			etPictureTagLabel1.setVisibility(View.VISIBLE);
			etPictureTagLabel1.requestFocus();
			//弹出键盘
			imn.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
			break;
		}
	}
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		setStatus(Status.Normal);
		return true;
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		View parent =(View)getParent();
		int halfParentW=(int)(parent.getWidth()*0.5);
		int center=(int)(l+(this.getWidth()*0.5));
		if(center<=halfParentW)
		{
			direction=Direction.Right;
		}
		else
		{
			direction=Direction.Right;
		}
		directionChange();
	}

	private void directionChange() {
		// TODO Auto-generated method stub
		switch(direction)
		{
		case Left:
			loTag.setBackgroundResource(R.drawable.bg_picturetagview_tagview_left);break;
		case Right:
			loTag.setBackgroundResource(R.drawable.bg_picturetagview_tagview_right);break;
		}
		
	}
	public static int getViewWidth()
	{
		return ViewWidth;
	}
	public static int getViewHeight()
	{
		return ViewHeight;
	}
}

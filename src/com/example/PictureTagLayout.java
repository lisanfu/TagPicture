package com.example;

import com.example.PicturetagView.Direction;
import com.example.PicturetagView.Status;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;

public class PictureTagLayout extends RelativeLayout implements OnTouchListener {
	private static final int CLICKRANG=5;
	int startX=0;
	int startY=0;
	int startTouchViewLeft=0;
	int startTouchViewTop=0;
	private View touchView;
	private View clickView;
	public PictureTagLayout(Context context) {
		super(context,null);
		// TODO Auto-generated constructor stub
	}

	public PictureTagLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}
	private void init()
	{
		this.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			touchView=null;
			if(clickView!=null)
			{
				((PicturetagView)clickView).setStatus(Status.Normal);
				clickView=null;
			}
			startX=(int)event.getX();
			startY=(int)event.getY();
			if(hasView(startX,startY))
			{
				startTouchViewLeft=touchView.getLeft();
				startTouchViewTop=touchView.getTop();
			}
			else
			{
				addItem(startX,startY);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			moveView((int)event.getX(),(int)event.getY());
			break;
		case MotionEvent.ACTION_UP:
			int endX=(int)event.getX();
			int endY=(int)event.getY();
			//����ƶ���Χ��С�����ж�Ϊ����
			if(touchView!=null&&Math.abs(endX-startX)<CLICKRANG&&Math.abs(endY-startY)<CLICKRANG)
			{
				//��ǰ�������View����༭״̬
				((PicturetagView)touchView).setStatus(Status.Edit);
				clickView=touchView;
			}
			touchView=null;
			break;
		}
		return true;
	}
	private void addItem(int x,int y)
	{
		View view=null;
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		if(x>getWidth()*0.5)
		{
			params.leftMargin=x-PicturetagView.getViewWidth();
			view=new PicturetagView(getContext(),Direction.Right);
		}
		else
		{
			params.leftMargin=x;
			view=new PicturetagView(getContext(),Direction.Left);
		}
		params.topMargin=y;
		//����λ������ͼ��
		if(params.topMargin<0)
		{
			params.topMargin=0;
		}
		else if((params.topMargin+PicturetagView.getViewHeight())>getHeight())
		{
			params.topMargin=getHeight()-PicturetagView.getViewHeight();
		}
		this.addView(view,params);
	}
	private void moveView(int x,int y)
	{
		if(touchView==null)
		{
			return;
		}
		RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.leftMargin=x-startX+startTouchViewLeft;
		params.topMargin=y-startY+startTouchViewTop;
		//�����ӿռ��ƶ���������ͼ��Χ��
		if(params.leftMargin<0||(params.leftMargin+touchView.getWidth())>getWidth())
		{
			params.leftMargin=touchView.getLeft();
		}
		if(params.topMargin<=0||(params.topMargin+touchView.getHeight())>getHeight())
		{
			params.topMargin=touchView.getTop();
		}
		touchView.setLayoutParams(params);
		
	}
	private boolean hasView(int x,int y)
	{
		//ѭ����ȡ��view���ж�xy�Ƿ�������view�ϼ��ж��Ƿ�ס����view
		for(int index=0;index<this.getChildCount();index++)
		{
			View view=this.getChildAt(index);
			int left=(int)view.getX();
			int top=(int)view.getY();
			int right=view.getRight();
			int bottom=view.getBottom();
			Rect rect=new Rect(left,top,right,bottom);
			boolean contains=rect.contains(x,y);
			//���������View�ص��򷵻�true��ʾ�Ѿ�����view����Ҫ�����view��
			if(contains)
			{
				touchView=view;
				touchView.bringToFront();
				return true;
			}
		}
		touchView=null;
		return false;
		
	}

}

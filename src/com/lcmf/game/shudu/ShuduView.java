package com.lcmf.game.shudu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.lcmf.robot.R;

public class ShuduView extends View {
	//��Ԫ��Ŀ�Ⱥ͸߶ȣ�
private float Width;
private float Height;
static int selectedX;
static int selectedY;
//Invalidate invalidate = new invalidate();
private GameNumber gameNumber = new GameNumber();
	public ShuduView(Context context) {
		super(context);
		// TODO �Զ����ɵĹ��캯�����
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO �Զ����ɵķ������
		//���㵱ǰ��Ԫ��Ŀ���ߣ�
		this.Width=w/9f;
		this.Height=h/9f;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO �Զ����ɵķ������
		//���û��ʵ���ɫ
		Paint backGroundPaint= new Paint();
		backGroundPaint.setColor(getResources().getColor(R.color.shudu_background));
		//���Ʊ�����ɫ
		canvas.drawRect(0, 0, getWidth(), getHeight(), backGroundPaint);
		Paint darkPaint= new Paint();
		darkPaint.setColor(getResources().getColor(R.color.shudu_dark));
		Paint hilitePaint= new Paint();
		hilitePaint.setColor(getResources().getColor(R.color.shudu_hilite));
		Paint lightPaint= new Paint();
		lightPaint.setColor(getResources().getColor(R.color.shudu_light));
		for(int i=0;i<9;i++)
		{
			canvas.drawLine(0, i*Height, getWidth(), i*Height, lightPaint);
			canvas.drawLine(0, i*Height+1, getWidth(), i*Height+1, hilitePaint);
			canvas.drawLine(i*Width, 0, i*Width, getHeight(), lightPaint);
			canvas.drawLine(i*Width+1, 0, i*Width+1, getHeight(), hilitePaint);
			
		}
		for(int i=0;i<9;i++)
		{
			if (i%3!=0) {
				continue;
			}
			canvas.drawLine(0, i*Height, getWidth(), i*Height, darkPaint);
			canvas.drawLine(0, i*Height+1, getWidth(), i*Height+1, hilitePaint);
			canvas.drawLine(i*Width, 0, i*Width, getHeight(), darkPaint);
			canvas.drawLine(i*Width+1, 0, i*Width+1, getHeight(), hilitePaint);
		}
		Paint numberPaint = new Paint();
		numberPaint.setColor(Color.BLACK);
		numberPaint.setStyle(Paint.Style.STROKE);
		numberPaint.setTextSize(Height*0.75f);
		numberPaint.setTextAlign(Paint.Align.CENTER);
		FontMetrics fm = numberPaint.getFontMetrics();
		float x = Width/2;
		float y = Height/2-(fm.ascent + fm.descent)/2;
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<9;j++)
			{
				canvas.drawText(gameNumber.getTileString(i,j), i*Width+x,j*Height+y, numberPaint);
			}
		}
		super.onDraw(canvas);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO �Զ����ɵķ������
		if(event.getAction()!=MotionEvent.ACTION_DOWN){
			return super.onTouchEvent(event);
		}
		 selectedX = (int)(event.getX()/Width);
		 selectedY = (int)(event.getY()/Height);
		
		int used[]=gameNumber.getUsedTilesByCoor(selectedX, selectedY);
		StringBuffer ni = new StringBuffer();
		for (int i = 0; i < used.length; i++) {
//			System.out.println(used[i]);
			ni.append(used[i]);
			
		}
		KeyDialog keyDialog = new KeyDialog(this.getContext(),used,this);
		keyDialog.show();
//		AlertDialog.Builder builder; 
//		AlertDialog alertDialog;
		//����layout����
//		LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
//		View layoutView = layoutInflater.inflate(R.layout.dialog,null);
//		TextView textView = (TextView)layoutView.findViewById(R.id.usedTextId);
//		textView.setText(ni.toString());
//		ImageView image = (ImageView)layoutView.findViewById(R.id.image);
//		image.setImageResource(R.drawable.mv_2);
//		AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//		builder.setView(layoutView);
//		AlertDialog alertDialog = builder.create();
//		alertDialog.show();
		return true;
		 
	   
		}
	   public void setSelectedTile(int tile) {
	  		// TODO �Զ����ɵķ������
	  		 if(gameNumber.setTileIfValid(selectedX,selectedY,tile)){
	  		 invalidate();
	  		}
	  	}


//private void setListener(){
//	for(int i =0;i<Keys.length;i++){
//		final int t=i+1;
//		Keys[i].setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO �Զ����ɵķ������
//				returnResult(t);
//			}
//		});

	/**
	 * @param args
	 */
//	public  void main(String[] args) {
//		// TODO �Զ����ɵķ������
//
//	}
	
}

package com.lcmf.game.shudu;

import com.lcmf.robot.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Dialog;
import android.content.Context;
//��������ʵ��Dialog,ʵ���Զ���ĶԻ�����
public class KeyDialog extends Dialog{
//private GameNumber gameNumber = new GameNumber();
//������ŶԻ����еİ�ť�Ķ���
private final View keys[]=new View[9];
private final int used[];
//�����������final��̬�ᱨ����Ϊ�����ǿ��Ըı��
private ShuduView shuduView = null;
//Ϊ���캯���ĵڶ����������б���ĵ�ǰ��Ԫ���Ѿ�ʹ�ù�������
public KeyDialog(Context context,int[] used, ShuduView shuduView){
	super(context);
	this.used=used;
	this.shuduView = shuduView;
}
//��һ��Dialog��һ����ʵ��ʱ�򣬵�ӾonCreate����
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
	//	super.onCreate(savedInstanceState);
	setContentView(R.layout.keypad);
		setTitle("可选数字");
		// setContentView(R.layout.keypad);
		//��������used���飬����Ԫ���ֵ��Ϊ���ʱ����ʾ
		findViews();
		for(int i=0;i<used.length;i++){
			if(used[i]!=0){
				System.out.println(used[i]);
				keys[used[i]-1].setVisibility(View.INVISIBLE);
			}
		}
		setKeyListeners();
//		Button back_Button = (Button)findViewById(R.id.back_1);
//		back_Button.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO �Զ����ɵķ������
//				dismiss();
//			}
//		});
		super.onCreate(savedInstanceState);
	}
//���ü�����
	private void findViews() {
	// TODO �Զ����ɵķ������
		keys[0]=findViewById(R.id.keypad_1);
		keys[1]=findViewById(R.id.keypad_2);
		keys[2]=findViewById(R.id.keypad_3);
		keys[3]=findViewById(R.id.keypad_4);
		keys[4]=findViewById(R.id.keypad_5);
		keys[5]=findViewById(R.id.keypad_6);
		keys[6]=findViewById(R.id.keypad_7);
		keys[7]=findViewById(R.id.keypad_8);
		keys[8]=findViewById(R.id.keypad_9);
	
}
	public void returnResult(int tile)
	{
		shuduView.setSelectedTile(tile);
		dismiss();
	}
	public void setKeyListeners(){
	for(int i =0;i<keys.length;i++){
		final int t=i+1;
		keys[i].setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				returnResult(t);
			}
		});
	}
	}
	/**
	 * @param args
	 */
//	public  void main(String[] args) {
//		// TODO �Զ����ɵķ������
//
//	}

}

package com.lcmf.cube;

import com.lcmf.game.block.BlockGameActivity;
import com.lcmf.menu.MainClass;
import com.lcmf.menu.SettingActivity;
import com.lcmf.robot.MainActivity;
import com.lcmf.robot.R;
import com.lcmf.textchat.TextActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MotherCube extends Activity implements OnTouchListener
{
	MyGLView myGLView;
	float initPosX;
	float dragDistX;
	float initPosY;
	float dragDistY;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		myGLView = new MyGLView(this);
		myGLView.setOnTouchListener((OnTouchListener) this);
		setContentView(myGLView);

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		myGLView.onResume();
	}
	  
	@Override
	protected void onPause()
	{
		super.onPause();
		myGLView.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(Menu.NONE, Menu.FIRST + 1, 5, "主页").setIcon(android.R.drawable.ic_menu_delete);
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "语音聊天").setIcon(android.R.drawable.ic_menu_edit);
		menu.add(Menu.NONE, Menu.FIRST + 3, 6, "文字聊天").setIcon(android.R.drawable.ic_menu_help);
		menu.add(Menu.NONE, Menu.FIRST + 4, 1, "别踩白块").setIcon(android.R.drawable.ic_menu_add);
		menu.add(Menu.NONE, Menu.FIRST + 5, 4, "游戏").setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(Menu.NONE, Menu.FIRST + 6, 3, "关于我们").setIcon(android.R.drawable.ic_menu_send);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Toast.makeText(this, "主页", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(MotherCube.this,MainClass.class);
		startActivity(intent);
			break;
		case Menu.FIRST + 2:
			Toast.makeText(this, "语音聊天", Toast.LENGTH_LONG).show();
		Intent intent1 = new Intent(MotherCube.this,MainActivity.class);
		startActivity(intent1);
			break;
		case Menu.FIRST + 3:
			Toast.makeText(this, "文字聊天", Toast.LENGTH_LONG).show();
		Intent intent2 = new Intent(MotherCube.this,TextActivity.class);
		startActivity(intent2);
			break;
		case Menu.FIRST + 4:
			Toast.makeText(this, "别踩白块", Toast.LENGTH_LONG).show();
		Intent intent3 = new Intent(MotherCube.this,BlockGameActivity.class);
		startActivity(intent3);
			break;
		case Menu.FIRST + 5:
			Toast.makeText(this, "游戏", Toast.LENGTH_LONG).show();
			break;
		case Menu.FIRST + 6:
			Toast.makeText(this, "关于我们", Toast.LENGTH_LONG).show();
		Intent intent5 = new Intent(MotherCube.this,SettingActivity.class);
		startActivity(intent5);
			break;
			}
		return false;
		
	}
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		int action = event.getAction() & MotionEvent.ACTION_MASK;
		switch (action) {
		case MotionEvent.ACTION_DOWN:
		case MotionEvent.ACTION_POINTER_DOWN:
			dragDistX = 0;
			initPosX = event.getX();
			dragDistY = 0;
			initPosY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_OUTSIDE:
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_MOVE:
			dragDistX = event.getX() - initPosX;
			initPosX = event.getX();
			dragDistY = event.getY() - initPosY;
			initPosY = event.getY();
			break;
		}
		myGLView.rotateView(dragDistX / 2, dragDistY / 2);
		
		return true;
	}

}

package com.lcmf.circle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.lcmf.ad.QuitPopAd;
import com.lcmf.ad.SlideWall;
import com.lcmf.circle.CircleLayout.OnItemClickListener;
import com.lcmf.circle.CircleLayout.OnItemSelectedListener;
import com.lcmf.game.block.BlockGameActivity;
import com.lcmf.menu.MainClass;
import com.lcmf.robot.MainActivity;
import com.lcmf.robot.R;

public class Circle_image extends Activity implements OnItemSelectedListener, OnItemClickListener ,View.OnClickListener, UpdatePointsNotifier {

	
	TextView selectedTextView;
	private TextView pointsTextView;
	private TextView SDKVersionView;

	private String displayPointsText;

	final Handler mHandler = new Handler();

	// 抽屉广告布局
	private View slidingDrawerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_image);
		
		CircleLayout circleMenu = (CircleLayout)findViewById(R.id.main_circle_layout);
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);
		/**
		 * 预加载自定义广告内容（仅在使用了自定义广告、抽屉广告或迷你广告的情况，才需要添加）
		 */
		AppConnect.getInstance(this).initAdInfo();
		/**
		 *  预加载插屏广告内容（仅在使用到插屏广告的情况，才需要添加）
		 */
		AppConnect.getInstance(this).initPopAd(this);
		// 迷你广告调用方式
		// AppConnect.getInstance(this).setAdBackColor(Color.argb(50, 120, 240,
		// 120));//设置迷你广告背景颜色
		// AppConnect.getInstance(this).setAdForeColor(Color.YELLOW);//设置迷你广告文字颜色
		LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
		AppConnect.getInstance(this).showMiniAd(this, miniLayout, 10);// 10秒刷新一次
		selectedTextView = (TextView)findViewById(R.id.main_selected_textView);
		selectedTextView.setText(((CircleImageView)circleMenu.getSelectedItem()).getName());
	}

	@Override
	public void onItemSelected(View view, int position, long id, String name) {		
		selectedTextView.setText(name);
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		switch (position) {
		case 0:
			// 显示插屏广告
			AppConnect.getInstance(this).showPopAd(this);
			break;

		case 1:
			// 显示推荐列表（软件）
			AppConnect.getInstance(this).showAppOffers(this);
			break;
		case 2:
			// 显示插屏广告
			AppConnect.getInstance(this).showPopAd(this);
			break;
		case 3:
			Intent intent = new Intent(Circle_image.this,MainClass.class);
			startActivity(intent);
			break;
		case 4:
			Intent intent1 = new Intent(Circle_image.this,MainActivity.class);
			startActivity(intent1);
			break;
		case 5:
			Intent intent2 =new Intent(Circle_image.this,BlockGameActivity.class);
			startActivity(intent2);
			break;
		}
		Toast.makeText(getApplicationContext(), getResources().getString(R.string.start_app) + " " + name, Toast.LENGTH_SHORT).show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (SlideWall.getInstance().slideWallDrawer != null && SlideWall.getInstance().slideWallDrawer.isOpened()) {
				// 如果抽屉式应用墙展示中，则关闭抽屉
				SlideWall.getInstance().closeSlidingDrawer();
			} else {
				// 调用退屏广告
				QuitPopAd.getInstance().show(this);
			}

		}
		return true;
	}
	@Override
	protected void onResume() {
		// 从服务器端获取当前用户的虚拟货币.
		// 返回结果在回调函数getUpdatePoints(...)中处理
		AppConnect.getInstance(this).getPoints(this);
		super.onResume();
	}

	
	
	
	
	@Override
	protected void onDestroy() {
		// 释放资源，原finalize()方法名修改为close()
		AppConnect.getInstance(this).close();
		super.onDestroy();
	}

	
	
	
	
	
	/**
	 * 用于监听插屏广告的显示与关闭
	 */
	// @Override
	// public void onWindowFocusChanged(boolean hasFocus) {
	// super.onWindowFocusChanged(hasFocus);
	// Dialog dialog = AppConnect.getInstance(this).getPopAdDialog();
	// if(dialog != null){
	// if(dialog.isShowing()){
	// // 插屏广告正在显示
	// }
	// dialog.setOnCancelListener(new OnCancelListener(){
	// @Override
	// public void onCancel(DialogInterface dialog) {
	// // 监听插屏广告关闭事件
	// }
	// });
	// }
	// }

	
	
	
	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				pointsTextView.setText(displayPointsText);
			}
		}
	};

	
	
	
	
	/**
	 * AppConnect.getPoints()方法的实现，必须实现
	 * 
	 * @param currencyName
	 *            虚拟货币名称.
	 * @param pointTotal
	 *            虚拟货币余额.
	 */
	public void getUpdatePoints(String currencyName, int pointTotal) {
		displayPointsText = currencyName + ": " + pointTotal;
		
		mHandler.post(mUpdateResults);
	}

	
	
	
	
	/**
	 * AppConnect.getPoints() 方法的实现，必须实现
	 * 
	 * @param error
	 *            请求失败的错误信息
	 */
	public void getUpdatePointsFailed(String error) {
		displayPointsText = error;
		mHandler.post(mUpdateResults);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

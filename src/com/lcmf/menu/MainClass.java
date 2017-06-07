package com.lcmf.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.lcmf.ad.QuitPopAd;
import com.lcmf.ad.SlideWall;
import com.lcmf.circle.Circle_image;
import com.lcmf.eyes.EyesActivity;
import com.lcmf.residemenu.ResideMenu;
import com.lcmf.residemenu.ResideMenuInfo;
import com.lcmf.residemenu.ResideMenuItem;
import com.lcmf.robot.R;

public class MainClass extends FragmentActivity implements
		View.OnClickListener ,UpdatePointsNotifier{

	private TextView pointsTextView;
	private TextView SDKVersionView;
	private ResideMenu resideMenu;
	final Handler mHandler = new Handler();
	private String displayPointsText;
	
	private ResideMenuItem itemHuiyuan;
	private ResideMenuItem itemQianbao;
	private ResideMenuItem itemZhuangban;
	private ResideMenuItem itemShoucang;
	private ResideMenuItem itemXiangce;
	private ResideMenuItem itemFile;
//	private ResideMenuItem setOn;

	private ResideMenuInfo info;

	private TextView text1, text2, text3;

	private boolean is_closed = false;
	private long mExitTime;

	private Button leftMenu;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menutest);
        AppConnect.getInstance(this);
/*        AppConnect.getInstance(this).setOffersCloseListener(new AppListener(){
        	@Override
        	public void onOffersClose() {
        	// TODO 关闭积分墙时癿操作代码
        	}
        	});*/
      //  AppConnect.getInstance(this).showOffers(this);

		setUpMenu();
		changeFragment(new NewsFragment());
		setListener();
		/*pointsTextView = (TextView) findViewById(R.id.PointsTextView);
		SDKVersionView = (TextView) findViewById(R.id.SDKVersionView);*/
		// 带有默认参数值的在线配置，使用此方法，程序第一次启动使用的是"defaultValue"，之后再启动则是使用的服务器端返回的参数值
			//	String showAd = AppConnect.getInstance(this).getConfig("showAd", "defaultValue");

			//	SDKVersionView.setText("在线参数:showAd = " + showAd);

			//	SDKVersionView.setText(SDKVersionView.getText() + "\nSDK版本: " + AppConnect.LIBRARY_VERSION_NUMBER);
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
	// 创建一个线程
	final Runnable mUpdateResults = new Runnable() {
		public void run() {
			if (pointsTextView != null) {
				pointsTextView.setText(displayPointsText);
			}
		}
	};
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

	@SuppressWarnings("deprecation")
	private void setUpMenu() {
		leftMenu = (Button) findViewById(R.id.title_bar_left_menu);

		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		//setOn = (TextView) findViewById(R.id.setOn);

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.bc_m);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);
		// 禁止使用右侧菜单
		resideMenu.setDirectionDisable(ResideMenu.DIRECTION_RIGHT);

		// create menu items;
		itemHuiyuan = new ResideMenuItem(this, R.drawable.btn, "更多应用");
		itemQianbao = new ResideMenuItem(this, R.drawable.btn, "关于我们");
	    itemZhuangban = new ResideMenuItem(this, R.drawable.btn, "科学家");
/*		itemShoucang = new ResideMenuItem(this, R.drawable.ic_launcher, "我的收藏");
		itemXiangce = new ResideMenuItem(this, R.drawable.ic_launcher, "我的相册");
		itemFile = new ResideMenuItem(this, R.drawable.ic_launcher, "我的文件");*/
		//setOn = new ResideMenuItem(this,R.drawable.ic_launcher,"设置");

		resideMenu.addMenuItem(itemHuiyuan, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemQianbao, ResideMenu.DIRECTION_LEFT);
	    resideMenu.addMenuItem(itemZhuangban, ResideMenu.DIRECTION_LEFT);
/*		resideMenu.addMenuItem(itemShoucang, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemXiangce, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemFile, ResideMenu.DIRECTION_LEFT);*/
		//resideMenu.addMenuItem(setOn,ResideMenu.DIRECTION_LEFT);

		info = new ResideMenuInfo(this, R.drawable.mm_tv, "心旷神怡",
				"喜欢的地方");
	}

	private void setListener() {
		resideMenu.addMenuInfo(info);

		itemHuiyuan.setOnClickListener(this);
		itemQianbao.setOnClickListener(this);
		itemZhuangban.setOnClickListener(this);
   /*	itemShoucang.setOnClickListener(this);
		itemXiangce.setOnClickListener(this);
		itemFile.setOnClickListener(this);*/

		text1.setOnClickListener(this);
		text2.setOnClickListener(this);
		text3.setOnClickListener(this);
		//setOn.setOnClickListener(this);

		info.setOnClickListener(this);

		leftMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			}
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return resideMenu.dispatchTouchEvent(ev);
	}
/*	else if (view.getId() == R.id.setOn) {
		Intent intent = new Intent(getApplicationContext(),Setting.class);
		startActivity(intent);
	}*/
	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.text1) {
			changeFragment(new NewsFragment());
		} else if (view.getId() == R.id.text2) {
			changeFragment(new ContactsFragment());
		} else if (view.getId() == R.id.text3) {
			changeFragment(new DongtaiFragment());
		} else if (view == itemHuiyuan) {
			//Intent intent = new Intent();
			//intent.putExtra("flog", "会员");
			//intent.setClass(getApplicationContext(), AdDemo.class);
			//startActivity(intent);
			 AppConnect.getInstance(this).showOffers(this);
		} else if (view == itemQianbao) {
			Intent intent = new Intent();
			//intent.putExtra("flog", "钱包");
			intent.setClass(getApplicationContext(), EyesActivity.class);
			startActivity(intent);
		} else if (view == itemZhuangban) {
			Intent intent = new Intent();
			//intent.putExtra("flog", "装扮");
			intent.setClass(getApplicationContext(), Circle_image.class);
			startActivity(intent);
		} /*else if (view == itemShoucang) {
			Intent intent = new Intent();
			intent.putExtra("flog", "收藏");
			intent.setClass(getApplicationContext(), SettingActivity.class);
			startActivity(intent);
		} else if (view == itemXiangce) {
			Intent intent = new Intent();
			intent.putExtra("flog", "相册");
			intent.setClass(getApplicationContext(), SettingActivity.class);
			startActivity(intent);
		} else if (view == itemFile) {
			Intent intent = new Intent();
			intent.putExtra("flog", "文件");
			intent.setClass(getApplicationContext(), SettingActivity.class);
			startActivity(intent);
		}*/ else if (view == info) {
			Intent intent = new Intent();
			intent.putExtra("flog", "超级助理2.1;开发者:lcmf");
			intent.setClass(getApplicationContext(), SettingActivity.class);
			startActivity(intent);
		}
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			is_closed = false;
			leftMenu.setVisibility(View.GONE);
		}

		@Override
		public void closeMenu() {
			is_closed = true;
			leftMenu.setVisibility(View.VISIBLE);
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	// What good method is to access resideMenu？
	public ResideMenu getResideMenu() {
		return resideMenu;
	}

/*	// 监听手机上的BACK键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// 判断菜单是否关闭
			if (is_closed) {
				// 判断两次点击的时间间隔（默认设置为2秒）
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

					mExitTime = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
					super.onBackPressed();
				}
			} else {
				resideMenu.closeMenu();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}*/
}

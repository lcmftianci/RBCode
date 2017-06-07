package com.lcmf.eyes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import cn.waps.AppConnect;

import com.lcmf.eyes.ArcMenu.onMenuItemClickListener;
import com.lcmf.robot.MainActivity;
import com.lcmf.robot.R;

public class EyesActivity extends Activity {
	ArcMenu arcMenu;
	//ArcMenu arcMenu2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.eyes_c);
		arcMenu = new ArcMenu(this);
		//arcMenu2 = new ArcMenu(this);
		arcMenu = (ArcMenu) findViewById(R.id.id_menu);
		//arcMenu = (ArcMenu) findViewById(R.id.id_menu1);
		/**
		 * 预加载自定义广告内容（仅在使用了自定义广告、抽屉广告或迷你广告的情况，才需要添加）
		 */
		AppConnect.getInstance(this).initAdInfo();
		arcMenu.setOnMenuItemClickListener(new onMenuItemClickListener() {

			@Override
			public void onclick(View view, int position) {
				// TODO Auto-generated method stub
				Toast.makeText(EyesActivity.this,
						position + ":" + view.getTag(), Toast.LENGTH_SHORT)
						.show();
			}
		});
		// 迷你广告调用方式
				// AppConnect.getInstance(this).setAdBackColor(Color.argb(50, 120, 240,
				// 120));//设置迷你广告背景颜色
				// AppConnect.getInstance(this).setAdForeColor(Color.YELLOW);//设置迷你广告文字颜色
				LinearLayout miniLayout = (LinearLayout) findViewById(R.id.miniAdLinearLayout);
				AppConnect.getInstance(this).showMiniAd(this, miniLayout, 10);// 10秒刷新一次
/*		arcMenu2.setOnMenuItemClickListener(new onMenuItemClickListener() {

			@Override
			public void onclick(View view, int position) {
				// TODO Auto-generated method stub
				Toast.makeText(EyesActivity.this,
						position + ":" + view.getTag(), Toast.LENGTH_SHORT)
						.show();
			}
		});*/
	}
}

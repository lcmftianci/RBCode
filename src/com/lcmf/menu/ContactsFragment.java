package com.lcmf.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lcmf.browser.ShowWebPageActivity;
import com.lcmf.game.block.BlockGameActivity;
import com.lcmf.game.mm.Gamemm;
import com.lcmf.game.shudu.ShuduActivity;
import com.lcmf.robot.R;
/**
 * User: special Date: 13-12-22 Time: 下午1:31 Mail: specialcyci@gmail.com

 */
public class ContactsFragment extends Fragment implements View.OnClickListener{
	
	
	private LinearLayout btn1;
	private LinearLayout btn2;
	private LinearLayout btn3;
	private LinearLayout btn4;
	private View v;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//View v=View.inflate(this.getActivity(), R.layout.fragment_contacts, null);
	  v	= inflater.inflate(R.layout.fragment_contacts, container, false);
	 // View v=View.inflate(this.getActivity(), R.layout.fragment_contacts, null);
		setView(v);
		return v;
		//return inflater.inflate(R.layout.fragment_contacts, container, false);
	}
	private void setView(View v) {
		// TODO Auto-generated method stub
		btn1 = (LinearLayout)v.findViewById(R.id.ll_btn1);
		btn2 = (LinearLayout)v.findViewById(R.id.ll_btn2);
		btn3 = (LinearLayout)v.findViewById(R.id.ll_btn3);
		btn4 = (LinearLayout)v.findViewById(R.id.ll_btn4);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getActivity(),BlockGameActivity.class);
			//	in.putExtra("url", "http://m.hao123.com/");
				startActivity(in);
			}
		});
        btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getActivity(),Gamemm.class);
				//in.putExtra("url", "http://m.hao123.com/");
				startActivity(in);
			}
		});
       btn3.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent in=new Intent(getActivity(),ShuduActivity.class);
		//in.putExtra("url", "http://m.hao123.com/");
		startActivity(in);
	}
});
      btn4.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent in=new Intent(getActivity(),ShowWebPageActivity.class);
		in.putExtra("url", "http://www.baidu.com");
		startActivity(in);
	}
});

	}
	
	
	
	
/*	public void doOnClick(View v) {
		Intent in=new Intent(getActivity(),ShowWebPageActivity.class);
		switch (v.getId()) {
			case R.id.ll_btn1:
				in.putExtra("url", "http://m.hao123.com/");
				break;
			case R.id.ll_btn2:
				in.putExtra("url", "http://m.hao123.com/");
				break;
			case R.id.ll_btn3:
				in.putExtra("url", "http://m.hao123.com/");
				break;
			case R.id.ll_btn4:
				in.putExtra("url", "http://m.hao123.com/");
				break;
		}
		startActivity(in);
	}*/
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
}

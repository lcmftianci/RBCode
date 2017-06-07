package com.lcmf.game.shudu;

import com.lcmf.robot.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ShuduActivity extends Activity {
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	       // setContentView(R.layout.activity_main);
	      // setContentView(new MyView(this));
//	        MyView1 myView1 = new MyView1(this);
//	        setContentView(myView1);
	        setContentView(new ShuduView(this));
	    }

/*		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }*/
}

package com.lcmf.start;

import java.util.Random;

import com.lcmf.menu.MainClass;
import com.lcmf.robot.MainActivity;
import com.lcmf.robot.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Startpic extends Activity {
	/**
	 * �����л��Ķ���
	 */
	private Animation mFadeIn;// ���Զ���
	private Animation mFadeInScale;// �Ŵ󶯻�
	private Animation mFadeOut;// ��������

	// @InjectView(R.id.image)
	ImageView mImageView;

	public static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startpic);

		mImageView = (ImageView) findViewById(R.id.image);
		int index = new Random().nextInt(2);
		if (index == 1) {
			mImageView.setImageResource(R.drawable.brege);
		} else {
			mImageView.setImageResource(R.drawable.tower_1);
		}
		initAnim();
		setListener();
	}

	/**
	 * ��ʼ�� ����Ч��
	 */
	private void initAnim() {
		mFadeIn = AnimationUtils
				.loadAnimation(this, R.drawable.welcome_fade_in);
		mFadeIn.setDuration(500);
		mFadeInScale = AnimationUtils.loadAnimation(this,
				R.drawable.welcome_fade_in_scale);
		mFadeInScale.setDuration(2000);
		mFadeOut = AnimationUtils.loadAnimation(this,
				R.drawable.welcome_fade_out);
		mFadeOut.setDuration(500);
		mImageView.startAnimation(mFadeIn);
	}

	/**
	 * ���ö���Ч�� �����¼�
	 */
	public void setListener() {
		/**
		 * �����л�ԭ��:��ʼʱ���õ�һ�����ֶ���,����һ����������ʱ��ʼ�ڶ����Ŵ󶯻�,���ڶ����
		 * �������ʱ���õ�������������,
		 * ��������������ʱ�޸���ʾ�����ݲ������µ��õ�һ������,�Ӷ��ﵽѭ��Ч��
		 */
		mFadeIn.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				mImageView.startAnimation(mFadeInScale);
			}
		});
		mFadeInScale.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				// ��תActivity
				Intent intent = new Intent(Startpic.this, MainActivity.class);
				startActivity(intent);
				finish();
				// mImageView.startAnimation(mFadeOut);
			}
		});
		mFadeOut.setAnimationListener(new AnimationListener() {

			public void onAnimationStart(Animation animation) {
			}

			public void onAnimationRepeat(Animation animation) {
			}

			public void onAnimationEnd(Animation animation) {
				// startActivity(MainActivity.class);
			}
		});
	}
}

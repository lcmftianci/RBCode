package com.lcmf.menu;

import com.lcmf.robot.R;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

public class DongtaiFragment extends Fragment {

	private View parentView;
	private VideoView videoView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fragment_dongtai, container, false);
        setView(parentView);
		return parentView;
	}
	        
	private void setView(View parentView){
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		videoView = (VideoView)parentView.findViewById(R.id.videoView1);
		MediaController controller = new MediaController(getActivity());
		this.videoView.setMediaController(controller);
		//videoView.setVideoURI(Uri.parse("android.resource://com.lcmf.robot/"+R.raw.my_video_file));
		//videoView.start();
		  String uri = "android.resource://" + getActivity().getPackageName() + "/" +R.raw.my_video_files;
	      videoView.setVideoURI(Uri.parse(uri));
		  videoView.start();
	}
/*	private void getView(View parentView){
		//videoView.setVideoPath("/sdcard/download/videoviewdemo.mp4");//设置了要播放的视频文件位置。
		videoView.setVideoPath("/sdcard0/download/mtv.3gp");
		videoView.setMediaController(new MediaController(getActivity()));//设置了一个播放控制器。
		videoView.start();//程序运行时自动开始播放视频。
		videoView.requestFocus(); //播放窗口为当前窗口
	}*/

}

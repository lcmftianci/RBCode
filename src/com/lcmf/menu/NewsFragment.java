package com.lcmf.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.lcmf.cube.Breakdancer;
import com.lcmf.robot.MainActivity;
import com.lcmf.robot.R;
import com.lcmf.textchat.TextActivity;

public class NewsFragment extends Fragment implements View.OnClickListener{
	private GridView gridView;
	private View parentView;
	private ImageAdapter adapter;

/*	WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
	int width = wm.getDefaultDisplay().getWidth();
	int height = wm.getDefaultDisplay().getHeight();*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//gridView.setColumnWidth(BIND_AUTO_CREATE);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.fragment_news, container, false);
        setView(parentView);
		return parentView;
	}
//获取屏幕的宽度 
public static int getScreenWidth(Context context) { 
 WindowManager manager = (WindowManager) context 
         .getSystemService(Context.WINDOW_SERVICE); 
 Display display = manager.getDefaultDisplay(); 
 return display.getWidth(); 
} 
//获取屏幕的高度 
public static int getScreenHeight(Context context) { 
 WindowManager manager = (WindowManager) context 
         .getSystemService(Context.WINDOW_SERVICE); 
 Display display = manager.getDefaultDisplay(); 
 return display.getHeight(); 
}
	private void setView(View parentView) {
		// TODO Auto-generated method stub
		gridView = (GridView)parentView.findViewById(R.id.gridView1);
		adapter = new ImageAdapter(getActivity());
		gridView.setAdapter(adapter);
	
		gridView.setOnItemClickListener(new OnItemClickListener() {

			private int[] images = {
					R.drawable.text_bg, R.drawable.mv_09,
		         R.drawable.mv_07,/*    R.drawable.mv_06,
		            R.drawable.mv_07, R.drawable.mv_101,*/
			};

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO 自动生成的方法存根
				Toast.makeText(getActivity(), "您选择了语音功能", 5000).show();
				if (position == 0) {
					ImageView imageView = new ImageView(getActivity());
					int imageId = images [position];
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setImageResource(imageId);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(imageView);
					builder.setTitle("                     文字聊天");
					builder.setPositiveButton("开始聊天", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),TextActivity.class);
							startActivity(intent);
						}
					}).setNegativeButton("关闭", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "正在退下", 1).show();
						}
					});
					builder.setCancelable(true);
					//step3.创建并显示对话框
					builder.create().show();
					
				}else if(position == 1){
					ImageView imageView = new ImageView(getActivity());
					int imageId = images [position];
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setImageResource(imageId);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(imageView);
					builder.setTitle("                     语音聊天");
					builder.setPositiveButton("开始聊天", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),MainActivity.class);
							startActivity(intent);
						}
					}).setNegativeButton("关闭", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "正在退下", 1).show();
						}
					});
					builder.setCancelable(true);
					//step3.创建并显示对话框
					builder.create().show();
				}else if(position == 2){
					ImageView imageView = new ImageView(getActivity());
					int imageId = images [position];
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setImageResource(imageId);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(imageView);
					builder.setTitle("                      小应用");
					builder.setPositiveButton("开始聊天", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),Breakdancer.class);
							startActivity(intent);
						}
					}).setNegativeButton("关闭", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "正在退下", 1).show();
						}
					});
					builder.setCancelable(true);
					//step3.创建并显示对话框
					builder.create().show();
				}else if (position == 3 ) {
					ImageView imageView = new ImageView(getActivity());
					int imageId = images [position];
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setImageResource(imageId);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(imageView);
					builder.setTitle("                     四川话");
					builder.setPositiveButton("开始聊天", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),MainActivity.class);
							startActivity(intent);
						}
					}).setNegativeButton("关闭", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "正在退下", 1).show();
						}
					});
					builder.setCancelable(true);
					//step3.创建并显示对话框
					builder.create().show();
				}else if(position == 4) {
					ImageView imageView = new ImageView(getActivity());
					int imageId = images [position];
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setImageResource(imageId);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(imageView);
					builder.setTitle("                       男士");
					builder.setPositiveButton("开始聊天", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),MainActivity.class);
							startActivity(intent);
						}
					}).setNegativeButton("关闭", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "正在退下", 1).show();
						}
					});
					builder.setCancelable(true);
					//step3.创建并显示对话框
					builder.create().show();
				}else if (position == 5) {
					ImageView imageView = new ImageView(getActivity());
					int imageId = images [position];
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setImageResource(imageId);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(imageView);
					builder.setTitle("                        女士");
					builder.setPositiveButton("开始聊天", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(getActivity(),Breakdancer.class);
							startActivity(intent);
						}
					}).setNegativeButton("关闭", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							Toast.makeText(getActivity(), "正在退下", 1).show();
						}
					});
					builder.setCancelable(true);
					//step3.创建并显示对话框
					builder.create().show();
				}
			}     
        	});

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}

class ImageAdapter extends BaseAdapter {
	//获取屏幕尺寸大小，是程序能在不同大小的手机上有更好的兼容性
	 // WindowManager wm=(WindowManager)getContext.getSystemService(Context.WINDOW_SERVICE);
	//  wwidth=wm.getDefaultDisplay().getWidth();//手机屏幕的宽度
	//  hheight=wm.getDefaultDisplay().getHeight();//手机屏幕的高度
	/*private DisplayMetrics dm; 
	dm = new DisplayMetrics(); 
	getWindowManager().getDefaultDisplay().getMetrics(dm); */
	//获取屏幕的宽度 
	public static int getScreenWidth(Context context) { 
	 WindowManager manager = (WindowManager) context 
	         .getSystemService(Context.WINDOW_SERVICE); 
	 Display display = manager.getDefaultDisplay(); 
	 return display.getWidth(); 
	} 
	//获取屏幕的高度 
	public static int getScreenHeight(Context context) { 
	 WindowManager manager = (WindowManager) context 
	         .getSystemService(Context.WINDOW_SERVICE); 
	 Display display = manager.getDefaultDisplay(); 
	 return display.getHeight(); 
	}
/*	int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
	int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
	textView.measure(w,h);*/
    private Context mContext;

   // Adapter adapter = (Adapter)getViewTypeCount();
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return images.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    private int[] images= {
            R.drawable.text_bg, R.drawable.mv_09,
            R.drawable.mv_07,/* R.drawable.mv_06,
            R.drawable.mv_07, R.drawable.mv_101,*/
    };
    //getScreenWidth(mContext)
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        int size=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
        		getScreenWidth(mContext),mContext.getResources().getDisplayMetrics());
        int size2=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 
        		getScreenWidth(mContext),mContext.getResources().getDisplayMetrics());
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(size/2, size/2));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(1, 1, 1, 1);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(images[position]);
        return imageView;
    }
}

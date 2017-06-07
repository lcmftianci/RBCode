package com.lcmf.robot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import com.iflytek.speech.setting.IatSettings;
import com.iflytek.speech.setting.TtsSettings;
import com.iflytek.speech.util.JsonParser;
import com.lcmf.ad.QuitPopAd;
import com.lcmf.ad.SlideWall;
import com.lcmf.menu.MainClass;
import com.lcmf.robot.bean.ChatMessage;
import com.lcmf.robot.bean.ChatMessage.Type;
import com.lcmf.tts.TtsDemo;
import com.zhy.utils.HttpUtils;
//import android.speech.SpeechRecognizer;

public class MainActivity extends Activity implements OnClickListener {
	private static String TAG = MainActivity.class.getSimpleName(); 
	// 语音合成对象
	private SpeechSynthesizer mTts;
	/**
	 * 展示消息的listview
	 */
	// 听写结果内容
	private ListView mChatView;
	// 语音听写对象
	private SpeechRecognizer mIat;
	/**
	 * 文本域
	 */
	private SharedPreferences mSharedPreferences;
	private RecognizerDialog mIatDialog;
	private EditText mMsg;
	private EditText mGetMsg;
	/**
	 * 存储聊天消息
	 */
	
	// 默认云端发音人
	public static String voicerCloud="xiaoyan";
	// 默认本地发音人
	public static String voicerLocal="xiaoyan";
		
	
	// 云端发音人列表
	private String[] cloudVoicersEntries;
	private String[] cloudVoicersValue ;
	
	// 本地发音人列表
	private String[] localVoicersEntries;
	private String[] localVoicersValue ;
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	//缓冲进度
	private int mPercentForBuffering = 0;	
	//播放进度
	private int mPercentForPlaying = 0;
	// 云端/本地选择按钮
	private RadioGroup mRadioGroup;
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	private Toast mToast;
		
		@SuppressLint("ShowToast")
	private List<ChatMessage> mDatas = new ArrayList<ChatMessage>();
	/**
	 * 适配器
	 */
	private ChatMessageAdapter mAdapter;

	private  Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
		 ChatMessage from = (ChatMessage) msg.obj;
			mDatas.add(from);
			mAdapter.notifyDataSetChanged();
			mChatView.setSelection(mDatas.size() - 1);
			mGetMsg.setText(from.getMsg().toString());
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_chatting);
		initView();
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);

		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(this, mInitListener);
		mAdapter = new ChatMessageAdapter(this, mDatas);
		mChatView.setAdapter(mAdapter);
		// 初始化合成对象
		mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
		// 云端发音人名称列表
		cloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
		cloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);		
		// 本地发音人名称列表
		localVoicersEntries = getResources().getStringArray(R.array.voicer_local_entries);
		localVoicersValue = getResources().getStringArray(R.array.voicer_local_values);
		mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, Activity.MODE_PRIVATE);
		mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
		//SpeechRecognizer mIat = SpeechRecognizer.createSpeechRecognizer(this,null);
	    // mIat.setParameter(SpeechConstant.DOMAIN,"iat");
	    // mIat.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
	}
	     @Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.add(Menu.NONE, Menu.FIRST + 1, 5, "语音设置").setIcon(android.R.drawable.ic_menu_delete);
		menu.add(Menu.NONE, Menu.FIRST + 2, 2, "听写设置").setIcon(android.R.drawable.ic_menu_edit);
		menu.add(Menu.NONE, Menu.FIRST + 3, 6, "选择发音人").setIcon(android.R.drawable.ic_menu_help);
		/*menu.add(Menu.NONE, Menu.FIRST + 4, 1, "添加").setIcon(android.R.drawable.ic_menu_add);
		menu.add(Menu.NONE, Menu.FIRST + 5, 4, "详细").setIcon(android.R.drawable.ic_menu_info_details);
		menu.add(Menu.NONE, Menu.FIRST + 6, 3, "发送").setIcon(android.R.drawable.ic_menu_send);*/
		return true;
	}
	    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Toast.makeText(this, "语音设置", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(MainActivity.this,TtsSettings.class);
		startActivity(intent);
			break;
		case Menu.FIRST + 2:
			Toast.makeText(this, "听写设置", Toast.LENGTH_LONG).show();
		Intent intent1 = new Intent(MainActivity.this, IatSettings.class);
		startActivity(intent1);
			break;
		case Menu.FIRST + 3:
			Toast.makeText(this, "选择发音人", Toast.LENGTH_LONG).show();
		showPresonSelectDialog();
			break;
			}
		return false;
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
	private void initView() {
		mChatView = (ListView) findViewById(R.id.id_chat_listView);
		mMsg = (EditText) findViewById(R.id.id_chat_msg);
		mGetMsg = (EditText) findViewById(R.id.id_get_msg);
		mDatas.add(new ChatMessage(Type.INPUT, "你好，我是小智机器人"));
	}
	int ret = 0;// 函数调用返回值
    public void menu(View view){
    	Intent intent = new Intent(MainActivity.this,MainClass.class);
    	startActivity(intent);
    }
	public void sendMessage(View view) {
		final String msg = mMsg.getText().toString();
		if (TextUtils.isEmpty(msg)) {
			Toast.makeText(this, "您还没有填写消息呢吧...", Toast.LENGTH_SHORT).show();
			return;
		}
		ChatMessage to = new ChatMessage(Type.OUTPUT, msg);
		to.setDate(new Date());
		mDatas.add(to);
		mAdapter.notifyDataSetChanged();
		mChatView.setSelection(mDatas.size() - 1);
		mMsg.setText("");
		// 关闭软键盘
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
		new Thread() {
			public void run() {
				ChatMessage from = null;
				try {
					from = HttpUtils.sendMsg(msg);
				} catch (Exception e) {
					from = new ChatMessage(Type.INPUT, "服务器连接失败...");
				}
				Message message = Message.obtain();
				message.obj = from;
				mHandler.sendMessage(message);
			};
		}.start();
	}
	
	/**
	 * 	语音转文字
	 * @param v
	 */
	public void getVoice(View v){
    //  SpeechRecognizer mIat = SpeechRecognizer.createSpeechRecognizer(context.null);
    //  mIat.setParameter(SpeechConstant.DOMAIN,"iat");
    //   mIat.setParameter(SpeechConstant.LANGUAGE,"zh_cn");
		mMsg.setText(null);// 清空显示内容
		mIatResults.clear();
		// 设置参数
		setParam1();
		boolean isShowDialog = mSharedPreferences.getBoolean(
				getString(R.string.pref_key_iat_show), true);
		if (isShowDialog) {
			// 显示听写对话框
			mIatDialog.setListener(mRecognizerDialogListener);
			mIatDialog.show();
			showTip(getString(R.string.text_begin));
		} else {
			// 不显示听写对话框
			ret = mIat.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				showTip("听写失败,错误码：" + ret);
			} else {
				showTip(getString(R.string.text_begin));
			}
		}
	}
	/**
	 * 参数设置 
	 * @param param
	 * @return
	 */
	public void setParam1() {
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);
		String lag = mSharedPreferences.getString("iat_language_preference",
				"mandarin");
		// 设置引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		} else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}

		// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
		mIat.setParameter(SpeechConstant.VAD_BOS,
				mSharedPreferences.getString("iat_vadbos_preference", "4000"));

		// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
		mIat.setParameter(SpeechConstant.VAD_EOS,
				mSharedPreferences.getString("iat_vadeos_preference", "1000"));

		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIat.setParameter(SpeechConstant.ASR_PTT,
				mSharedPreferences.getString("iat_punc_preference", "1"));

		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
		mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH,
				Environment.getExternalStorageDirectory() + "/msc/iat.wav");
	}

	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败，错误码：" + code);
			}
		}
	};
	/**
	 * 听写监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			showTip("开始说话");
		}
		@Override
		public void onError(SpeechError error) {
			// Tips：
			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
			showTip(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
			showTip("结束说话");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			String text = JsonParser.parseIatResult(results.getResultString());
			mMsg.append(text);
			mMsg.setSelection(mMsg.length());
			if (isLast) {
				// TODO 最后的结果
			}
		}
		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			showTip("当前正在说话，音量大小：" + volume);
			Log.d(TAG, "返回音频数据：" + data.length);
		}
		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			// if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			// String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			// Log.d(TAG, "session id =" + sid);
			// }
		}
	};
	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.d(TAG, "recognizer result：" + results.getResultString());
			String text = JsonParser.parseIatResult(results.getResultString());
			mMsg.append(text);
			mMsg.setSelection(mMsg.length());
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

	};
      private RecognizerListener mRecoListener = new RecognizerListener() {
	
		@Override
		public void onVolumeChanged(int volume, byte[] arg1) {
			// TODO Auto-generated method stub
			
	 }
	
	@Override
	public void onResult(RecognizerResult results, boolean isLast) {
		// TODO Auto-generated method stub
		Log.d("Result:", results.getResultString());
	}
	
	@Override
	public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onError(SpeechError error) {
		// TODO Auto-generated method stub
		error.getPlainDescription(true);
	}
	
	@Override
	public void onEndOfSpeech() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBeginOfSpeech() {
		// TODO Auto-generated method stub
		
	}
  }; 
	//释放语音
	 public void getMessage(View view){
		 String text = mGetMsg.getText().toString();
			// 设置参数
			setParam();
			int code = mTts.startSpeaking(text, mTtsListener);
			if (code != ErrorCode.SUCCESS) {
				Toast.makeText(MainActivity.this, "语音合成失败,错误码: "+code, 5000).show();	
			}
	    }
	 
		/**
		 * 初始化监听。
		 */
		private InitListener mTtsInitListener = new InitListener() {
			@Override
			public void onInit(int code) {
				Log.d(TAG, "InitListener init() code = " + code);
				if (code != ErrorCode.SUCCESS) {
	        		showTip("初始化失败,错误码："+code);
	        	} else {
					// 初始化成功，之后可以调用startSpeaking方法
	        		// 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
	        		// 正确的做法是将onCreate中的startSpeaking调用移至这里
				}		
			}
		};
		/**
		 * 合成回调监听。
		 */
		private SynthesizerListener mTtsListener = new SynthesizerListener() {
			
			@Override
			public void onSpeakBegin() {
				showTip("开始播放");
			}

			@Override
			public void onSpeakPaused() {
				showTip("暂停播放");
			}

			@Override
			public void onSpeakResumed() {
				showTip("继续播放");
			}

			@Override
			public void onBufferProgress(int percent, int beginPos, int endPos,
					String info) {
				// 合成进度
				mPercentForBuffering = percent;
				showTip(String.format(getString(R.string.tts_toast_format),
						mPercentForBuffering, mPercentForPlaying));
			}

			@Override
			public void onSpeakProgress(int percent, int beginPos, int endPos) {
				// 播放进度
				mPercentForPlaying = percent;
				showTip(String.format(getString(R.string.tts_toast_format),
						mPercentForBuffering, mPercentForPlaying));
			}

			@Override
			public void onCompleted(SpeechError error) {
				if (error == null) {
					showTip("播放完成");
				} else if (error != null) {
					showTip(error.getPlainDescription(true));
				}
			}

			@Override
			public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
				// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
				// 若使用本地能力，会话id为null
				//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
				//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
				//		Log.d(TAG, "session id =" + sid);
				//	}
			}
		};
		private void showTip(final String str){
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					mToast.setText(str);
					mToast.show();
				}
			});
		}
		private void setParam(){
			// 清空参数
			mTts.setParameter(SpeechConstant.PARAMS, null);
			//设置合成
			if(mEngineType.equals(SpeechConstant.TYPE_CLOUD))
			{
				//设置使用云端引擎
				mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
				//设置发音人
				mTts.setParameter(SpeechConstant.VOICE_NAME,voicerCloud);
			}else {
				//设置使用本地引擎
				mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
				//设置发音人资源路径
				mTts.setParameter(ResourceUtil.TTS_RES_PATH,getResourcePath());
				//设置发音人
				mTts.setParameter(SpeechConstant.VOICE_NAME,voicerLocal);
			}
			//设置合成语速
			mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "500"));
			//设置合成音调
			mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "500"));
			//设置合成音量
			mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "500"));
			//设置播放器音频流类型
			mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
			
			// 设置播放合成音频打断音乐播放，默认为true
			mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
			
			// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
			// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
			mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
			mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
		}
		//获取发音人资源路径
		private String getResourcePath(){
			StringBuffer tempBuffer = new StringBuffer();
			//合成通用资源
			tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "tts/common.jet"));
			tempBuffer.append(";");
			//发音人资源
			tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "tts/"+TtsDemo.voicerLocal+".jet"));
			return tempBuffer.toString();
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	private static int selectedNumCloud=0;
	private static int selectedNumLocal=0;
	/**
	 * 发音人选择。
	 */
	private void showPresonSelectDialog() {
		switch (1) {
		// 选择在线合成
		case 1:			
			new AlertDialog.Builder(this).setTitle("在线合成发音人选项")
			.setSingleChoiceItems(cloudVoicersEntries, // 单选框有几项,各是什么名字
					selectedNumCloud, // 默认的选项
					new DialogInterface.OnClickListener() { // 点击单选框后的处理
				public void onClick(DialogInterface dialog,
						int which) { // 点击了哪一项
					voicerCloud = cloudVoicersValue[which];
				/*	if ("catherine".equals(voicerCloud) || "henry".equals(voicerCloud) || "vimary".equals(voicerCloud)) {
						 ((EditText) findViewById(R.id.id_get_msg)).setText(R.string.text_tts_source_en);
					}else {
						((EditText) findViewById(R.id.id_get_msg)).setText(R.string.text_tts_source);
					}*/
					selectedNumCloud = which;
					dialog.dismiss();
				}
			}).show();
			break;
			
		// 选择本地合成
		//case R.id.tts_radioLocal:
		//	new AlertDialog.Builder(this).setTitle("本地合成发音人选项")
		//	.setSingleChoiceItems(localVoicersEntries, // 单选框有几项,各是什么名字
		//			selectedNumLocal, // 默认的选项
		//			new DialogInterface.OnClickListener() { // 点击单选框后的处理
		//		public void onClick(DialogInterface dialog,
		//				int which) { // 点击了哪一项
		//			voicerLocal = localVoicersValue[which];
		//			if ("catherine".equals(voicerLocal) || "henry".equals(voicerLocal) || "vimary".equals(voicerLocal)) {
		//				 ((EditText) findViewById(R.id.id_get_msg)).setText(R.string.text_tts_source_en);
		//			}else {
		//				((EditText) findViewById(R.id.id_get_msg)).setText(R.string.text_tts_source);
		//			}
		//			selectedNumLocal = which;
		//			dialog.dismiss();
		//		}
		//	}).show();
		//	break;
		default:
			break;
		}
	}
	
}

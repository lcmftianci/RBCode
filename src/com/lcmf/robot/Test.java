package com.lcmf.robot;

import com.zhy.utils.HttpUtils;

import android.test.AndroidTestCase;

public class Test extends AndroidTestCase {
	public void testSendMsg() {
		HttpUtils.sendMsg("西斜七路堵车吗");
		HttpUtils.sendMsg("你好");
		HttpUtils.sendMsg("讲个笑话");
		HttpUtils.sendMsg("新浪体育");
		HttpUtils.sendMsg("背首诗");
		HttpUtils.sendMsg("说歌词" +
				"查星座" +
				"让翻译" +
				"查百科" +
				"计算机");
	}

}

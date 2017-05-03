package com.example.aidlclient;

import com.demo.aidl.IMyAidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

/**此为aidl客户端代码，aidl服务端代码编译成apk放在assets包中**/
public class MainActivity extends Activity implements OnClickListener {
	private String TAG = "Dylan_MainActivity";
	// 创建aidl引用
	private IMyAidl mBinder;
	// 是否绑定
	private boolean isBinder;
	/**定义在服务端的服务**/
	private String serviceAction = "com.demo.service.aidl";

	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		/** 获取另一进程传递过来的IBinder对象，并转化该对象 **/
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub

			// 获取IBinder对象，并将其转化为IMyAidl
			mBinder = IMyAidl.Stub.asInterface(service);
			isBinder = true;
			Log.e(TAG, "****onServiceConnected");

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bind_bt:
			bindAidlService();
			break;
		case R.id.play_bt:
			bindPlay();
			break;
		case R.id.pause_bt:
			bindPause();
			break;
		}
	}

	private void bindPlay() {
		if (isBinder) {
			try {
				mBinder.play();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void bindPause() {
		if (isBinder) {
			try {
				mBinder.pause();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void bindAidlService() {
		Intent intent = new Intent();
		// 设置服务的action
		intent.setAction(serviceAction);
		// 绑定服务
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
		Log.e(TAG, "****bind服务中。。。");

	}
}

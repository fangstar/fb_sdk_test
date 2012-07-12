package com.apn.fb;

import android.app.Application;
import android.util.Log;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.Facebook;

public class FBHelloApp extends Application {
	private static final String TAG = "FBHelloApp";
	private static final String APP_ID = "";
	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;

	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.d(TAG, "onCreate");
		
		mFacebook = new Facebook(APP_ID);
		mAsyncRunner = new AsyncFacebookRunner(mFacebook);

	}

	public Facebook getmFacebook() {
		return mFacebook;
	}

	public AsyncFacebookRunner getmAsyncRunner() {
		return mAsyncRunner;
	}

}

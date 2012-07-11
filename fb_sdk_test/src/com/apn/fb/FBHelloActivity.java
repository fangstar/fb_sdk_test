package com.apn.fb;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.BaseRequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.LoginButton;
import com.facebook.android.R;
import com.facebook.android.SessionEvents;
import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;
import com.facebook.android.SessionStore;
import com.facebook.android.Util;

public class FBHelloActivity extends Activity {
	private static final String TAG = "FBHelloActivity";
//	public static final String APP_ID = "";

	private LoginButton mLoginButton;
	private TextView mText;

	private Facebook mFacebook;
	private AsyncFacebookRunner mAsyncRunner;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

//		if (APP_ID == null) {
//			Util.showAlert(this, "Warning", "Facebook Applicaton ID must be "
//					+ "specified before running this example: see Example.java");
//		}

		setContentView(R.layout.main);
		mLoginButton = (LoginButton) findViewById(R.id.login);
		mText = (TextView) findViewById(R.id.txt);

//		mFacebook = new Facebook(APP_ID);
//		mAsyncRunner = new AsyncFacebookRunner(mFacebook);
		FBHelloApp fbApp = (FBHelloApp)getApplication();
		mFacebook = fbApp.getmFacebook();
		mAsyncRunner = fbApp.getmAsyncRunner();

		SessionStore.restore(mFacebook, this);
		SessionEvents.addAuthListener(new SampleAuthListener());
		SessionEvents.addLogoutListener(new SampleLogoutListener());
		mLoginButton.init(this, mFacebook);

		
		mFacebook.authorize(this, new String[] {"publish_stream"}, new DialogListener() {

			@Override
			public void onComplete(Bundle values) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFacebookError(FacebookError e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onError(DialogError e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d(TAG, "onRestart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "onStop");
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		mFacebook.authorizeCallback(requestCode, resultCode, data);
	}

	public class SampleAuthListener implements AuthListener {

		public void onAuthSucceed() {
			mText.setText("You have logged in! ");
			// mRequestButton.setVisibility(View.VISIBLE);
			// mUploadButton.setVisibility(View.VISIBLE);
			// mPostButton.setVisibility(View.VISIBLE);
		}

		public void onAuthFail(String error) {
			mText.setText("Login Failed: " + error);
		}
	}

	public class SampleLogoutListener implements LogoutListener {
		public void onLogoutBegin() {
			mText.setText("Logging out...");
		}

		public void onLogoutFinish() {
			mText.setText("You have logged out! ");
			// mRequestButton.setVisibility(View.INVISIBLE);
			// mUploadButton.setVisibility(View.INVISIBLE);
			// mPostButton.setVisibility(View.INVISIBLE);
		}
	}

	public void doWallPost(View v) {
		String message = "TEST: Post this to my wall from android 1";
		Bundle parameters = new Bundle();
		parameters.putString("message", message);
		mAsyncRunner.request("me/feed", parameters, "POST",
				new WallPostRequestListener(), null);
	}

	public class WallPostRequestListener extends BaseRequestListener {

		public void onComplete(final String response, final Object state) {
			Log.d("Facebook-Example", "Got response: " + response);
			String message = "<empty>";
			try {
				JSONObject json = Util.parseJson(response);
				message = json.getString("message");
			} catch (JSONException e) {
				Log.w("Facebook-Example", "JSON Error in response");
			} catch (FacebookError e) {
				Log.w("Facebook-Example", "Facebook Error: " + e.getMessage());
			}
			final String text = "Your Wall Post: " + message;
			Log.d("Facebook-Example", text);
			FBHelloActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					mText.setText(text);
				}
			});
		}
	}
}
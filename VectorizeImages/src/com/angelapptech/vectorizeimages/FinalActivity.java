package com.angelapptech.vectorizeimages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

public class FinalActivity extends Activity {
	LinearLayout llayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_final);
		Thread background = new Thread() {
			public void run() {

				try {
					// Thread will sleep for 5 seconds
					sleep(4 * 1000);

					// After 5 seconds redirect to another intent
					Intent i = new Intent(FinalActivity.this,
							MainActivity.class);
					startActivity(i);

					// Remove activity
					finish();

				} catch (Exception e) {

				}
			}
		};

		// start thread
		background.start();
	}

}

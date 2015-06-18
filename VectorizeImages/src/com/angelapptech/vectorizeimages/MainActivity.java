package com.angelapptech.vectorizeimages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {
	ImageView imgvec, imgbac, imgpho, imggra;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		imgvec = (ImageView) findViewById(R.id.imgvec);
		imgvec.setOnClickListener(this);
		imgbac = (ImageView) findViewById(R.id.imgbac);
		imgbac.setOnClickListener(this);
		imgpho = (ImageView) findViewById(R.id.imgpho);
		imgpho.setOnClickListener(this);
		imggra = (ImageView) findViewById(R.id.imggra);
		imggra.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.imgvec) {
			Intent newActivity = new Intent(MainActivity.this,
					StartProjectActoivity.class);
			newActivity.putExtra("value", "VECTORIZATION");
			startActivity(newActivity);
			finish();
		} else if (v.getId() == R.id.imgbac) {
			Intent newActivity = new Intent(MainActivity.this,
					StartProjectActoivity.class);
			newActivity.putExtra("value", "BACKGROUND REMOVAL");
			startActivity(newActivity);
			finish();
		} else if (v.getId() == R.id.imgpho) {
			Intent newActivity = new Intent(MainActivity.this,
					StartProjectActoivity.class);
			newActivity.putExtra("value", "PHOTO EDITION & RETOUCH");
			startActivity(newActivity);
			finish();

		} else if (v.getId() == R.id.imggra) {
			Intent newActivity = new Intent(MainActivity.this,
					StartProjectActoivity.class);
			newActivity.putExtra("value", "GRAPHIC DESIGN");
			startActivity(newActivity);
			finish();

		}

	}

}

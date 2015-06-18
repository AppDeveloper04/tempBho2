package com.angelapptech.vectorizeimages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class StartProjectActoivity extends Activity implements OnClickListener {
	Button btntackimage, btnopengallery, btnsubmit;
	private static final int CAMERA_REQUEST = 1888;
	private static final int RESULT_LOAD_IMG = 2888;
	private static final int RESULT_sent_Email = 255;
	EditText etemail, etIFD;
	String imgDecodableString;
	String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	String email, IFD, value, jsonUrl;
	Uri URI = null;
	ImageView imgView;
	File pic;
	String ImageBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_start_project_actoivity);
		value = getIntent().getExtras().getString("value");
		Log.e("values", "values are==>" + value);
		imgView = (ImageView) findViewById(R.id.imageView2);
		btntackimage = (Button) findViewById(R.id.btntackimage);
		btntackimage.setOnClickListener(this);
		btnopengallery = (Button) findViewById(R.id.btnopengallery);
		btnopengallery.setOnClickListener(this);
		btnsubmit = (Button) findViewById(R.id.btnsubmit);
		btnsubmit.setOnClickListener(this);
		etemail = (EditText) findViewById(R.id.etemail);
		etIFD = (EditText) findViewById(R.id.etIFD);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btntackimage) {
			Intent cameraIntent = new Intent(
					android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
		} else if (v.getId() == R.id.btnopengallery) {
			Intent galleryIntent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
		} else if (v.getId() == R.id.btnsubmit) {
			if (isvalid()) {
				try {
					// File sourceFile = new File(
					// String.valueOf(Uri.fromFile(pic)));

					jsonUrl = "http://vectorizeimages.com/app/mail_test.php";
					// + "?email="
					// + email
					// + "&instruction="
					// + IFD
					// + "&image="
					// + sourceFile+ "";
					// Log.e("Uri from file", "Uri==>" +sourceFile);
					new TestAsync().execute(jsonUrl);
					// Uri.fromFile(pic);

					// Intent emailIntent = new Intent(
					// android.content.Intent.ACTION_SEND);
					// emailIntent.setType("message/rfc822");
					// emailIntent.putExtra(Intent.EXTRA_EMAIL,
					// new String[] { "maheshsheliya@gmail.com" });
					// emailIntent.putExtra(Intent.EXTRA_SUBJECT, value);
					// emailIntent.putExtra(Intent.EXTRA_TEXT, "Email :" + email
					// + "\nInstruction's for desiganer :" + IFD);
					// if (pic != null) {
					// emailIntent.putExtra(Intent.EXTRA_STREAM,
					// Uri.fromFile(pic));
					// }
					// if (URI != null) {
					// emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
					// }
					// startActivityForResult(Intent.createChooser(emailIntent,
					// "Pick an Email provider"), RESULT_sent_Email);
				} catch (Throwable t) {
					Toast.makeText(StartProjectActoivity.this,
							"Request failed try again: " + t.toString(),
							Toast.LENGTH_LONG).show();

				}

			}
		}

	}

	public String BitMapToString(Bitmap bitmap) {
		ByteArrayOutputStream ByteStream = new ByteArrayOutputStream();

		bitmap.compress(Bitmap.CompressFormat.PNG, 100, ByteStream);
		byte[] b = ByteStream.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	public class TestAsync extends AsyncTask<String, Void, String> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = new ProgressDialog(StartProjectActoivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setMessage("Loading......");
			pd.setIndeterminate(true);
			pd.setCancelable(false);
			pd.show();
		}

		@SuppressWarnings("deprecation")
		@Override
		protected String doInBackground(String... params) {
			String responce = "";

			try {

				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(params[0]);
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						5);
				nameValuePairs.add(new BasicNameValuePair("email", email));
				nameValuePairs.add(new BasicNameValuePair("instruction", IFD));
				if (ImageBitmap != null) {
					nameValuePairs.add(new BasicNameValuePair("image",
							ImageBitmap));
				}
				nameValuePairs.add(new BasicNameValuePair("subject", value));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);

				// @SuppressWarnings("deprecation")
				// HttpGet httppost = new HttpGet(params[0]);
				// @SuppressWarnings("deprecation")
				// HttpClient httpclient = new DefaultHttpClient();
				// HttpResponse httpresponce = httpclient.execute(httppost);
				// @SuppressWarnings("deprecation")
				HttpEntity entity = response.getEntity();
				responce = EntityUtils.toString(entity);
				Log.e("responce", "responce==>" + responce);

			} catch (IOException e) {
				e.printStackTrace();
			}

			return responce;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (pd != null) {
				pd.dismiss();

			}
			try {
				if (result != null) {
					JSONObject jobj = new JSONObject(result);
					String success = jobj.getString("status");
					String message = jobj.getString("message");

					if (success.equalsIgnoreCase("1")) {
						startActivity(new Intent(StartProjectActoivity.this,
								FinalActivity.class));
						finish();
					} else {
						Toast.makeText(StartProjectActoivity.this, message,
								Toast.LENGTH_SHORT).show();
					}
				}

			} catch (JSONException e) {

				e.printStackTrace();
				Toast.makeText(StartProjectActoivity.this, "Internet Error",
						Toast.LENGTH_SHORT).show();
			}
		}

	}

	private boolean isvalid() {
		email = etemail.getText().toString();
		IFD = etIFD.getText().toString();
		if (!email.matches(emailPattern)) {
			Toast.makeText(getApplicationContext(), "Please Enter Valid Email",
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (IFD.matches("")) {
			Toast.makeText(getApplicationContext(),
					"Instruction for Designer is empty", Toast.LENGTH_SHORT)
					.show();
			return false;
		} else {
			return true;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			try {
				Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
				imgView.setImageBitmap(thumbnail);
				// ImageBitmap = BitMapToString(thumbnail);

				ImageBitmap = BitMapToString(decodeUri(
						StartProjectActoivity.this,
						getImageUri(StartProjectActoivity.this, thumbnail),
						5000));
				File root = Environment.getExternalStorageDirectory();
				if (root.canWrite()) {
					pic = new File(root, "pic.png");
					FileOutputStream out = new FileOutputStream(pic);
					thumbnail.compress(CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
					Toast.makeText(getApplicationContext(), "Image captured",
							Toast.LENGTH_SHORT).show();
				}
			} catch (IOException e) {
				Log.e("BROKEN", "Could not write file " + e.getMessage());
			}

		}
		try {
			if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
					&& null != data) {
				try {
					Uri selectedImage = data.getData();
					String[] filePathColumn = { MediaStore.Images.Media.DATA };

					Cursor cursor = getContentResolver().query(selectedImage,
							filePathColumn, null, null, null);
					cursor.moveToFirst();

					int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
					String picturePath = cursor.getString(columnIndex);
					cursor.close();

					ImageBitmap = BitMapToString(decodeUri(
							StartProjectActoivity.this, selectedImage, 500));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), "You Image picked",
						Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Something went wrong",
					Toast.LENGTH_LONG).show();
		}
		if (requestCode == RESULT_sent_Email && resultCode == RESULT_CANCELED) {
			startActivity(new Intent(StartProjectActoivity.this,
					FinalActivity.class));
			finish();

		}
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(),
				inImage, "Title", null);
		return Uri.parse(path);
	}

	public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
			throws FileNotFoundException {
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri),
				null, o);

		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;

		while (true) {
			if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeStream(c.getContentResolver()
				.openInputStream(uri), null, o2);
	}

}

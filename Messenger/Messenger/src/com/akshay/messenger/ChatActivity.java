package com.akshay.messenger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

public class ChatActivity extends Activity
{
	static Context chat_activity_context;
	static GoogleCloudMessaging gcm;
	AtomicInteger msgId = new AtomicInteger();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_message_send);

		Log.i(Common.TAG, "ChatActivity Started.");

		Log.i(Common.TAG, "ChatActivity Chatting with " + Constants.RECEIVER_EMAIL);

		Button btn_send_message = (Button) findViewById(R.id.btn_send);
		btn_send_message.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Log.i(Common.TAG, "MainActivity Send Message Clicked");

				new AsyncTask<Void, Void, Void>()
				{
					final EditText message_text = (EditText) findViewById(R.id.message);
					
					@Override
					protected Void doInBackground(Void... params)
					{
						try
						{
							Log.i(Common.TAG, "ChatActivity message_text : " + message_text.getText().toString());
							Log.i(Common.TAG, "ChatActivity Constants.SENDER_ID : " + Constants.SENDER_ID);

							Bundle data = new Bundle();
							data.putString("my_message", message_text.getText().toString());
							data.putString("my_action", "com.google.android.gcm.demo.app.ECHO_NOW");
							String id = Integer.toString(msgId.incrementAndGet());

							Log.i(Common.TAG, "ChatActivity id : " + id);

							gcm.send(Constants.SENDER_ID + "@gcm.googleapis.com", id, data);

							Sender sender = new Sender(Constants.API_KEY);

							String time = getCurrentTimeStamp();
							
							Log.i(Common.TAG, "ChatActivtiy time : " + time);

							Log.i(Common.TAG, "ChatActivtiy Constants.RECEIVER_EMAIL : " + Constants.RECEIVER_EMAIL);
							
							MongoDatabase.getRegistrationIdFromEmailId(Constants.RECEIVER_EMAIL);

							sender.sendMessage2(message_text.getText().toString(), Constants.RECEIVER_EMAIL, time);

							MongoDatabase.sendNewMessage(message_text.getText().toString(), Constants.RECEIVER_EMAIL, time);
						}
						catch (IOException ex)
						{
							Log.i(Common.TAG, "ChatActivity IOException " + ex.getMessage());
						}
						
						return null;
					}

					@Override
					protected void onPostExecute(Void params)
					{
						Log.i(Common.TAG, "ChatActivity AsyncTask Completed.");
						
						message_text.setText("");
					}
				}.execute(null, null, null);
			}
		});
	}

	public static String getCurrentTimeStamp()
	{
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now = new Date();
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static void initialize(Context context)
	{
		Log.i(Common.TAG, "ChatActivity Initializing gcm object.");

		gcm = GoogleCloudMessaging.getInstance(context);
	}
}

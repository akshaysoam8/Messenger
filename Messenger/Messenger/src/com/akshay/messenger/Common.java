package com.akshay.messenger;

import java.util.ArrayList;
import java.util.List;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Patterns;

public class Common extends Application
{
	public static String[] email_arr;
	private static SharedPreferences prefs;
	public static final String TAG = "Messenger";
	public static String FROM = "email";
	
	public static final String GCM_SEND_ENDPOINT = "https://android.googleapis.com/gcm/send";

	public static String PARAM_REGISTRATION_ID = "registration_id";
	public static String PARAM_RECIEVER_REGISTRATION_ID = "registration_id";
	public static String SENDER_NAME = "name";

	public static final String PARAM_COLLAPSE_KEY = "collapse_key";

	public static final String PARAM_SENDER_NAME = "sender_name";

	public static final String PARAM_MESSAGE_BODY = "messaage_body";

	public static final String PARAM_DELAY_WHILE_IDLE = "delay_while_idle";

	public static final String PARAM_DRY_RUN = "dry_run";

	public static final String PARAM_RESTRICTED_PACKAGE_NAME = "restricted_package_name";

	public static final String PARAM_PAYLOAD_PREFIX = "data.";

	public static final String PARAM_TIME_TO_LIVE = "time_to_live";

	public static final String ERROR_QUOTA_EXCEEDED = "QuotaExceeded";

	public static final String ERROR_DEVICE_QUOTA_EXCEEDED = "DeviceQuotaExceeded";

	public static final String ERROR_MISSING_REGISTRATION = "MissingRegistration";

	public static final String ERROR_INVALID_REGISTRATION = "InvalidRegistration";

	public static final String ERROR_MISMATCH_SENDER_ID = "MismatchSenderId";

	public static final String ERROR_NOT_REGISTERED = "NotRegistered";

	public static final String ERROR_MESSAGE_TOO_BIG = "MessageTooBig";

	public static final String ERROR_MISSING_COLLAPSE_KEY = "MissingCollapseKey";

	public static final String ERROR_UNAVAILABLE = "Unavailable";

	public static final String ERROR_INTERNAL_SERVER_ERROR = "InternalServerError";

	public static final String ERROR_INVALID_TTL = "InvalidTtl";

	public static final String TOKEN_MESSAGE_ID = "id";

	public static final String TOKEN_CANONICAL_REG_ID = "registration_id";

	public static final String TOKEN_ERROR = "Error";

	public static final String JSON_REGISTRATION_IDS = "registration_ids";

	public static final String JSON_PAYLOAD = "data";

	public static final String JSON_SUCCESS = "success";

	public static final String JSON_FAILURE = "failure";

	public static final String JSON_CANONICAL_IDS = "canonical_ids";

	public static final String JSON_MULTICAST_ID = "multicast_id";

	public static final String JSON_RESULTS = "results";

	public static final String JSON_ERROR = "error";

	public static final String JSON_MESSAGE_ID = "message_id";

	public static boolean isNotify()
	{
		return prefs.getBoolean("notifications_new_message", true);
	}

	public static String getRingtone()
	{
		return prefs.getString("notifications_new_message_ringtone", android.provider.Settings.System.DEFAULT_NOTIFICATION_URI.toString());
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);

		List<String> emailList = getEmailList();
		email_arr = emailList.toArray(new String[emailList.size()]);
	}

	private List<String> getEmailList()
	{
		List<String> lst = new ArrayList<String>();
		Account[] accounts = AccountManager.get(this).getAccounts();

		for (Account account : accounts)
		{
			if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches())
			{
				lst.add(account.name);
			}
		}

		return lst;
	}
}

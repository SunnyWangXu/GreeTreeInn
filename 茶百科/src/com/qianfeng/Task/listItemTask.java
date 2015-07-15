package com.qianfeng.Task;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

public class listItemTask extends AsyncTask<String, Void, String> {

	private String mID;
	private String data;
	private Context mContext;
	private String baseUrl;

	public listItemTask(Context context, String itemID) {
		mID = itemID;
		mContext = context;
	}

	@Override
	protected String doInBackground(String... params) {
		baseUrl = params[0] + mID;
		try {
			JSONObject obj = new JSONObject(baseUrl);
			String result = obj.getString("errorMessage");
			if (result.equals("success")) {
				JSONObject dataObj = obj.getJSONObject("data");
				data = dataObj.getString("dataObj");

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return data;
	}

	@Override
	protected void onPostExecute(String result) {
		WebView web = new WebView(mContext);
		web.loadDataWithBaseURL(baseUrl, result, "text/html", "utf_8", null);
	}
}

package open.callforblood.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import open.callforblood.DAO.RecordDBD;
import open.callforblood.main.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DatabaseEntity {

	private String serverFile;
	private ArrayList<NameValuePair> nameValuePairs;
	private JSONArray jArray = null;
	public static final String SERVER_URL = "http://call4blood.hostingsiteforfree.com/";
	private String id = "";
	private String password = "";
	private Context context;

	public DatabaseEntity() {
		nameValuePairs = new ArrayList<NameValuePair>();

	}

	public DatabaseEntity(String serverFile) {
		this.serverFile = serverFile;
		nameValuePairs = new ArrayList<NameValuePair>();

	}

	public void addNameValuesPair(String name, String value) {
		nameValuePairs.add(new BasicNameValuePair(name, value));
	}

	private void getConnection() {
		String result = "";
		InputStream is = null;

		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(SERVER_URL + serverFile + ".php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();

			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();
		} catch (Exception e) {
		}

		try {
			jArray = new JSONArray(result);
			Lock.unlockServer();
		} catch (JSONException e1) {
		}

	}

	public void getSession(Context context) {
		executeServerQuery();
		new ResponseHandler().execute(context);
	}

	private class ResponseHandler extends AsyncTask<Context, Void, String> {

		@Override
		protected String doInBackground(Context... params) {
			while (Lock.isServerLocked()) {
			}
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data;
				try {
					json_data = jArray.getJSONObject(i);
					id = json_data.getString("id_user");
				} catch (JSONException e) {
				}
			}
			context = params[0];

			return id;
		}

		@Override
		protected void onPostExecute(String id) {
			if (id.equals("null")) {
				// mot de passe erroné
				Toast.makeText(context, "Mot de passe erroné",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			} else {
				// page principale
				RecordDBD db = new RecordDBD(context);
				db.open();
				db.updateContactIdByMail(nameValuePairs.get(1).getValue(), id);
				Toast.makeText(context, "id : " + id, Toast.LENGTH_LONG).show(); // to be removed
			}
		}

	}

	public void setServerFile(String serverFile) {
		this.serverFile = serverFile;
		nameValuePairs = new ArrayList<NameValuePair>();
	}

	private void executeServerQuery() {
		Lock.lockServer();
		new SendHttpData().execute(Integer.valueOf(0));
	}

	private class SendHttpData extends AsyncTask<Integer, Void, String> {
		@Override
		protected String doInBackground(Integer... arg) {

			getConnection();
			return "succes";
		}

		@Override
		protected void onPostExecute(String result) {
		}
	}

}

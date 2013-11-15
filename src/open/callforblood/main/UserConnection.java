package open.callforblood.main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

import android.util.Log;

public class UserConnection {

	private String email;
	private String password;
	private int userId;

	public UserConnection() {
	}

	public UserConnection(String email, String password) {
		super();
		this.email = email;
		this.password = password;
		getConnection();
	}

	private void getConnection() {
		String result = "";
		InputStream is = null;
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("password", password));
		
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					"http://call4blood.hostingsiteforfree.com/getUserId.php");
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			;
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
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

		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
		} catch (JSONException e1) {
		}
		for (int i = 0; i < jArray.length(); i++) {
			JSONObject json_data;
			try {
				json_data = jArray.getJSONObject(i);
				Log.i("log_tag","id: "+json_data.getInt("id")+
						", name: "+json_data.getString("name")+
						", sex: "+json_data.getInt("sex")+
						", birthyear: "+json_data.getInt("birthyear"));
			} catch (JSONException e) {
			}
			
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

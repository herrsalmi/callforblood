package open.callforblood.main;

import java.io.IOException;
import java.util.ArrayList;

import open.callforblood.utils.Encryption;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class LRegisterValidat implements OnClickListener {

	private String firstname;
	private String lastname;
	private String password;
	private String email;
	private String tel;
	private EditText etfirstname;
	private EditText etlastname;
	private EditText etphone;
	private EditText etemail;
	private EditText etpassword;
	private EditText etpassword2;
	private Activity register;
	private boolean submited;

	public LRegisterValidat(Activity register) {
		super();
		this.register = register;
		etfirstname = (EditText) register.findViewById(R.id.firstname);
		etlastname = (EditText) register.findViewById(R.id.lastname);
		etphone = (EditText) register.findViewById(R.id.phone);
		etemail = (EditText) register.findViewById(R.id.email);
		etpassword = (EditText) register.findViewById(R.id.password);
		etpassword2 = (EditText) register.findViewById(R.id.repassword);
	}

	@Override
	public void onClick(final View v) {
		/*
		 * new Handler().postDelayed(new Runnable() {
		 * 
		 * public void run() {
		 * 
		 * }
		 * 
		 * }, 200L); for(EditText text : textValues.values()){
		 * System.out.println(text.getText()); if(text.getText().equals("")){
		 * text.setBackgroundColor(Color.RED); } }
		 */
		setFirstname(etfirstname.getText().toString());
		setLastname(etlastname.getText().toString());
		setEmail(etemail.getText().toString());
		setTel(etphone.getText().toString());
		setPassword(etpassword.getText().toString(), etpassword2.getText()
				.toString());

		ConnectivityManager connMgr = (ConnectivityManager) register
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			if (!(firstname.isEmpty()) && !(lastname.isEmpty())
					&& !(password.isEmpty()) && !(email.isEmpty())
					&& !(tel.isEmpty())) {
				submited = true;
				new SendHttpData().execute(Integer.valueOf(0));
			} else {
				etfirstname.setText("");
				etlastname.setText("");
				etemail.setText("");
				etphone.setText("");
				etpassword.setText("");
				Toast.makeText(register,
						"voulez-vous bien remplir les champs correctement",
						Toast.LENGTH_LONG).show();

			}
		} else {
			Toast.makeText(register, "Network error", Toast.LENGTH_LONG).show();
		}

	}

	private class SendHttpData extends AsyncTask<Integer, Void, String> {
		@Override
		protected String doInBackground(Integer... arg) {

			postData();
			return "succes";
		}

		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(register, "sent", Toast.LENGTH_LONG).show();
		}
	}

	public void postData() {
		HttpClient client = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				"http://call4blood.hostingsiteforfree.com/insertUser.php");

		try {
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					2);
			nameValuePairs.add(new BasicNameValuePair("firstname",
					getFirstname()));
			nameValuePairs
					.add(new BasicNameValuePair("lastname", getLastname()));
			nameValuePairs.add(new BasicNameValuePair("email", getEmail()));
			nameValuePairs.add(new BasicNameValuePair("phone", getTel()));
			nameValuePairs
					.add(new BasicNameValuePair("password", getPassword()));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			System.out.println(EntityUtils.toString(httppost.getEntity()));
			HttpResponse response = client.execute(httppost);

		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}
	}

	public String getFirstname() {
		return firstname.trim();
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname.trim();
	}

	public String getLastname() {
		return lastname.trim();
	}

	public void setLastname(String lastname) {
		this.lastname = lastname.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password, String repassword) {

		if (password.equals(repassword))
			this.password = Encryption.md5Encryption(password);
	}

	public String getEmail() {
		return email.trim();
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		if ((tel.length() == 10) && (tel.charAt(0) == '0'))
			this.tel = tel;
	}

}

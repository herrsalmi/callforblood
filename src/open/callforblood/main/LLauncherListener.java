package open.callforblood.main;

import open.callforblood.DAO.RecordDBD;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LLauncherListener implements OnClickListener {

	Activity mainActivity;
	EditText email;
	EditText password;

	public LLauncherListener(Activity mainActivity) {
		super();
		this.mainActivity = mainActivity;
	}

	@Override
	public void onClick(View e) {
		int b = ((Button) e).getId();
		if (b == R.id.connectbtn) {
			ConnectivityManager connMgr = (ConnectivityManager) mainActivity
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				EditText email = (EditText) mainActivity
						.findViewById(R.id.usermail);
				EditText password = (EditText) mainActivity
						.findViewById(R.id.userpass);
				if (email.getText().toString().isEmpty()
						|| password.getText().toString().isEmpty()) {
					Toast.makeText(mainActivity, "Champ vide",
							Toast.LENGTH_LONG).show();
				} else {
					RecordDBD db = new RecordDBD(mainActivity);
					db.open();
					db.insertContactMail(email.getText().toString(), password
							.getText().toString());
					Intent sessionIntent = new Intent(
							"open.callforblood.main.SessionLauncher");
					mainActivity.startActivity(sessionIntent);
				}
			} else {
				Toast.makeText(mainActivity, "Network error", Toast.LENGTH_LONG)
						.show();
			}

		} else if (b == R.id.signinbtn) {
			Intent sessionIntent = new Intent(
					"open.callforblood.main.RegisterLauncher");
			mainActivity.startActivity(sessionIntent);
		} else {
			Intent sessionIntent = new Intent(
					"open.callforblood.main.RegisterLauncher");
			mainActivity.startActivity(sessionIntent);
		}

	}

}

package open.callforblood.main;

import open.callforblood.DAO.IMPEntity;
import open.callforblood.DAO.RecordDBD;
import open.callforblood.utils.DatabaseEntity;
import open.callforblood.utils.Encryption;
import android.app.Activity;
import android.os.Bundle;

public class SessionLauncher extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_launcher);
		init();
		
	}

	private void init() {
		RecordDBD db = new RecordDBD(this);
		db.open();
		String email = db.getEmailWithDefaultId();
		IMPEntity entity = db.getLoginData(email);
		System.out.println(email);
		System.out.println(Encryption.md5Encryption(entity.getPassword()));
		DatabaseEntity dbe = new DatabaseEntity("getSession");
		
		dbe.addNameValuesPair("password", Encryption.md5Encryption(entity.getPassword()));
		dbe.addNameValuesPair("email", entity.getEmail());
		
		
		dbe.getSession(this);
		

		
	}

}

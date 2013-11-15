package open.callforblood.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class RegisterLauncher extends Activity {


	private Button submitbt;
	

	
	private void init() {
		submitbt = (Button) findViewById(R.id.submitbtn);
		submitbt.setOnClickListener(new LRegisterValidat(this));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_launcher);
		init();

	}

}

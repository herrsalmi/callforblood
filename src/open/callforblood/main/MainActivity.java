package open.callforblood.main;

import open.callforblood.DAO.RecordDBD;
import open.callforblood.main.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static Button signin,connect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    


    private void init() {
    	connect = (Button)findViewById(R.id.connectbtn);
    	signin = (Button)findViewById(R.id.signinbtn);
    	connect.setOnClickListener(new LLauncherListener(this));
    	signin.setOnClickListener(new LLauncherListener(this));
    	
    	/**********************/
    	RecordDBD db = new RecordDBD(this);
		db.open();
		db.removeUserWithID("0");
		db.removeUserWithID("1");
    }



	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
  
    	
    	
    
}

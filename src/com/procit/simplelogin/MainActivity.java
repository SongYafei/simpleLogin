package com.procit.simplelogin;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private XmlCreate xmlCreate;
	private int userId = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button but = (Button)findViewById(R.id.button1);
		but.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//create a new xml file in SDCard using DOM parser
				xmlCreate = new XmlCreate(MainActivity.this, userId);
				xmlCreate.readFromSDCard();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

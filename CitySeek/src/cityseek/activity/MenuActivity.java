package cityseek.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);

		ImageView myImg = (ImageView) findViewById(R.id.goseek);
		myImg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				startNext();
			}
		});
	}

	public void startNext() {
		Intent intent = new Intent(this, CitySeekActivity.class);
		startActivity(intent);
	}
}

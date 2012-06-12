package cityseek.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuizActivity extends Activity {

	private static final String ANSWER = "Massachusetts";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quiz);

		final EditText editText = (EditText) findViewById(R.id.editText1);

		final Button button = (Button) findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String text = editText.getText().toString();
				if (text.equalsIgnoreCase(ANSWER)) {
					Toast.makeText(getBaseContext(), "Correct!",
							Toast.LENGTH_SHORT).show();
				} else {
					editText.clearComposingText();
					Toast.makeText(getBaseContext(), "Try again",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}

package cityseek.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageView;

public class SlidingPuzzleActivity extends Activity implements OnKeyListener {

	private static final double TARGET_LATITUDE = 81.500000;
	private static final double TARGET_LONGITUDE = 51.500000;
	private static final double UPPER_LATITUDE = TARGET_LATITUDE + 0.000025;
	private static final double LOWER_LATITUDE = TARGET_LATITUDE - 0.000025;
	private static final double UPPER_LONGITUDE = TARGET_LONGITUDE + 0.000025;
	private static final double LOWER_LONGITUDE = TARGET_LONGITUDE - 0.000025;

	private ImageView mCompleteView;
	private TileView mTileView;
	private Chronometer mTimerView;
	private long mTime;

	private AnimationListener mCompleteAnimListener = new AnimationListener() {
		public void onAnimationEnd(Animation animation) {
			mTileView.setVisibility(View.GONE);
		}

		public void onAnimationRepeat(Animation animation) {
		}

		public void onAnimationStart(Animation animation) {
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.slide_puzzle);
		mTileView = (TileView) findViewById(R.id.tile_view);
		mTileView.requestFocus();
		mTileView.setOnKeyListener(this);

		mCompleteView = (ImageView) findViewById(R.id.complete_view);
		mCompleteView.setImageBitmap(mTileView.getCurrentImage());

		mTimerView = (Chronometer) findViewById(R.id.timer_view);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		mTimerView.setTextColor(getResources().getColor(
				R.drawable.default_fg_color));

		if (icicle == null) {
			int blankLoc = Integer.parseInt(String.valueOf(1));
			mTileView.newGame(null, blankLoc, mTimerView);
			mTime = 0;
		} else {
			Parcelable[] parcelables = icicle.getParcelableArray("tiles");
			Tile[] tiles = null;
			if (parcelables != null) {
				int len = parcelables.length;

				tiles = new Tile[len];
				for (int i = 0; i < len; i++) {
					tiles[i] = (Tile) parcelables[i];
				}
			}

			mTileView.newGame(tiles, icicle.getInt("blank_first"), mTimerView);
			mTime = icicle.getLong("time", 0);
		}

	}

	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);

		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		int bgColor = getResources().getColor(R.drawable.default_bg_color);
		findViewById(R.id.layout).setBackgroundColor(bgColor);

		mTileView.updateInstantPrefs();
		mTimerView.setBase(SystemClock.elapsedRealtime() - mTime);
		if (!mTileView.isSolved()) {
			mTimerView.start();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		if (!mTileView.isSolved()) {
			mTime = (SystemClock.elapsedRealtime() - mTimerView.getBase());
		}
		mTimerView.stop();
	}

	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// Prevent user from moving tiles if the puzzle has been solved
		if (mTileView.isSolved()) {
			return false;
		}

		boolean moved;
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_DPAD_DOWN: {
				moved = mTileView.move(TileView.DIR_DOWN);
				break;
			}
			case KeyEvent.KEYCODE_DPAD_UP: {
				moved = mTileView.move(TileView.DIR_UP);
				break;
			}
			case KeyEvent.KEYCODE_DPAD_LEFT: {
				moved = mTileView.move(TileView.DIR_LEFT);
				break;
			}
			case KeyEvent.KEYCODE_DPAD_RIGHT: {
				moved = mTileView.move(TileView.DIR_RIGHT);
				break;
			}
			default:
				return false;
			}

			if (mTileView.checkSolved()) {
				mCompleteView.setImageBitmap(mTileView.getCurrentImage());
				mCompleteView.setVisibility(View.VISIBLE);

				Animation animation = AnimationUtils.loadAnimation(this,
						R.anim.fade_in);
				animation.setAnimationListener(mCompleteAnimListener);
				mCompleteView.startAnimation(animation);

				goToQuestion();
			}
			return true;
		}

		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		// Prevent user from moving tiles if the puzzle has been solved
		if (mTileView.isSolved()) {
			return false;
		}

		int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN: {
			mTileView.grabTile(event.getX(), event.getY());
			return true;
		}
		case MotionEvent.ACTION_MOVE: {
			mTileView.dragTile(event.getX(), event.getY());
			return true;
		}
		case MotionEvent.ACTION_UP: {
			boolean moved = mTileView.dropTile(event.getX(), event.getY());

			if (mTileView.checkSolved()) {
				mCompleteView.setImageBitmap(mTileView.getCurrentImage());
				mCompleteView.setVisibility(View.VISIBLE);

				Animation animation = AnimationUtils.loadAnimation(this,
						R.anim.fade_in);
				animation.setAnimationListener(mCompleteAnimListener);
				mCompleteView.startAnimation(animation);

				goToQuestion();
			}
			return true;
		}
		}

		return false;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		outState.putParcelableArray("tiles", mTileView.getTiles());
		outState.putInt("blank_first", mTileView.mBlankLocation);
		outState.putLong("time", mTime);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	public void goToQuestion() {

	}
}
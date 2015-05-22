package com.example.ashwini.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class BounceActivity extends Activity implements AnimationListener {

	ImageView imgPoster;
	Button btnStart;

	// Animation
	Animation animBounce;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bounce);

		imgPoster = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load the animation
		animBounce = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.bounce);

		// set animation listener
		animBounce.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// start the animation
				imgPoster.setVisibility(View.VISIBLE);
				imgPoster.startAnimation(animBounce);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for zoom in animation
		if (animation == animBounce) {
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}

/*****************
 *
 * BounceInterpolator	@android:anim/bounce_interpolator
 * An interpolator where the change bounces at the end.
 *
 * -----------------------------------------------------
 *
 * <set xmlns:android="http://schemas.android.com/apk/res/android"
 android:fillAfter="true"
 android:interpolator="@android:anim/bounce_interpolator">

 <scale
 android:duration="500"
 android:fromXScale="1.0"
 android:fromYScale="0.0"
 android:toXScale="1.0"
 android:toYScale="1.0" />

 </set>

 */
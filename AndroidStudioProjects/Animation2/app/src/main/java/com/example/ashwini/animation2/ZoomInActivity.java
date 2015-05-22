package com.example.ashwini.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class ZoomInActivity extends Activity implements AnimationListener {

	ImageView imgPoster;
	Button btnStart;

	// Animation
	Animation animZoomIn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zoom_in);

		imgPoster = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load the animation
		animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.zoom_in);
		
		// set animation listener
		animZoomIn.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// start the animation
				imgPoster.startAnimation(animZoomIn);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for zoom in animation
		if (animation == animZoomIn) {			
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

/*****
 * <set xmlns:android="http://schemas.android.com/apk/res/android"
 android:fillAfter="true" >

 <scale   = for resizing
 xmlns:android="http://schemas.android.com/apk/res/android"
 android:duration="1000"
 android:fromXScale="1" ////Float. Starting X size offset, where 1.0 is no change.
 android:fromYScale="1"
 android:pivotX="50%" //Float. The X coordinate to remain fixed when the object is scaled.
 android:pivotY="50%"
 android:toXScale="3"
 android:toYScale="3" >
 </scale>

 </set>
 *
 *
 *
 * ******/

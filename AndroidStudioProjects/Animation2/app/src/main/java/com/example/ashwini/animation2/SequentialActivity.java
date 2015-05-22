package com.example.ashwini.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

public class SequentialActivity extends Activity implements AnimationListener {

	ImageView imgLogo;
	Button btnStart;

	// Animation
	Animation animSequential;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sequential);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load the animation
		animSequential = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.sequential);

		// set animation listener
		animSequential.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// start the animation
				imgLogo.startAnimation(animSequential);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for zoom in animation
		if (animation == animSequential) {
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



/**********************

 android:startOffset
 int. The amount of milliseconds the animation delays after start() is called.
 <translate
 android:duration="800"    // +ve x
 android:fillAfter="true"
 android:fromXDelta="0%p"
 android:startOffset="300"
 android:toXDelta="75%p" />
 <translate
 android:duration="800"
 android:fillAfter="true"   //+ve y
 android:fromYDelta="0%p"
 android:startOffset="1100"
 android:toYDelta="70%p" />
 <translate
 android:duration="800"
 android:fillAfter="true"   // -x axis
 android:fromXDelta="0%p"
 android:startOffset="1900"
 android:toXDelta="-75%p" />
 <translate
 android:duration="800"
 android:fillAfter="true"
 android:fromYDelta="0%p"   //- ve y
 android:startOffset="2700"
 android:toYDelta="-70%p" />

 ******************/
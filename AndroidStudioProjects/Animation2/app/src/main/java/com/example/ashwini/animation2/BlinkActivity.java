package com.example.ashwini.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class BlinkActivity extends Activity implements AnimationListener {

	TextView txtMessage;
	Button btnStart;

	// Animation
	Animation animBlink;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blink);

		txtMessage = (TextView) findViewById(R.id.txtMessage);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load the animation
		animBlink = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.blink);
		
		// set animation listener
		animBlink.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				txtMessage.setVisibility(View.VISIBLE);
				
				// start the animation
				txtMessage.startAnimation(animBlink);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for blink animation
		if (animation == animBlink) {
		}

	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

}

/*
*
* <?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android">
    <alpha android:fromAlpha="0.0" // trasnparent to opaque
        android:toAlpha="1.0"
        android:interpolator="@android:anim/accelerate_interpolator"
        android:duration="600" //600ms

          //android:repeatMode	setRepeatMode(int)
          	//Defines the animation behavior when it reaches the end and the repeat count is greater than 0 or infinite.
          	//int	REVERSE	When the animation reaches the end and the repeat count is INFINTE_REPEAT or a positive value,
          	// the animation plays backward (and then forward again).

        android:repeatMode="reverse" //uses repeatcount= +ve or infinte and when reaches end of count reverse it and starts again

        public void setRepeatCount (int repeatCount)

Added in API level 1
Sets how many times the animation should be repeated. If the repeat count is 0, the animation is never repeated.
///If the repeat count is greater than 0 or INFINITE, the repeat mode will be taken into account. The repeat count is 0 by default.


        android:repeatCount="infinite"/>
</set>

*
* android:background="#FFFB64" =Yellow
*
* **/
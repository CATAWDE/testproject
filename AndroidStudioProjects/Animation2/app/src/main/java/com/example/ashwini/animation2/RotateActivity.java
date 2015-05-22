package com.example.ashwini.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class RotateActivity extends Activity implements AnimationListener {

	ImageView imgLogo;
	Button btnStart;

	// Animation
	Animation animRotate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rotate);

		imgLogo = (ImageView) findViewById(R.id.imgLogo);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load the animation
		animRotate = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.rotate);

		// set animation listener
		animRotate.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				imgLogo.setVisibility(View.VISIBLE);

				// start the animation
				imgLogo.startAnimation(animRotate);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for fade in animation
		if (animation == animRotate) {
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

/******************


 <set xmlns:android="http://schemas.android.com/apk/res/android">
 <rotate android:fromDegrees="0"   //Float. Starting angular position, in degrees.
 android:toDegrees="360"           //Float. Ending angular position, in degrees.
 android:pivotX="50%"  //Float or percentage. The X coordinate of the center of rotation.
 //Expressed either: in pixels relative to the object's left edge (such as "5"),
 //in percentage relative to the object's left edge (such as "5%"),
// or in percentage relative to the parent container's left edge (such as "5%p").

 android:pivotY="50%"
 //Float or percentage. The Y coordinate of the center of rotation.
 //Expressed either: in pixels relative to the object's top edge (such as "5"),
 //in percentage relative to the object's top edge (such as "5%"), or in percentage relative to the parent container's top edge (such as "5%p").
 android:duration="600"
 android:repeatMode="restart"
 android:repeatCount="infinite"
 android:interpolator="@android:anim/cycle_interpolator"/>

 </set>







 */
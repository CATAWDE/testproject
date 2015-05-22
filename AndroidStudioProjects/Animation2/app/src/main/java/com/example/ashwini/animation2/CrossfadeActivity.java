package com.example.ashwini.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CrossfadeActivity extends Activity implements AnimationListener {

	//TextView txtMessage1, txtMessage2;
	ImageView img1,img2;
	Button btnStart;

	// Animation
	Animation animFadeIn, animFadeOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crossfade);

		//txtMessage1 = (TextView) findViewById(R.id.txtMessage1);
		//txtMessage2 = (TextView) findViewById(R.id.txtMessage2);

		img1=(ImageView)findViewById(R.id.imageView);
		img2=(ImageView)findViewById(R.id.imageView1);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load animations
		animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_out);

		// set animation listeners
		animFadeIn.setAnimationListener(this);
		animFadeOut.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// make fade in element visible
		//		txtMessage2.setVisibility(View.VISIBLE);
				img2.setVisibility(View.VISIBLE);
				// start fade in animation
				img2.startAnimation(animFadeIn);// animation starts with transparent to opeque
				// but as soon as it reaches to end its view is set as setVisibility is gone thts y
				//its effect is reversed..msg become opeque and then transparent
				
				// start fade out animation
			img1.startAnimation(animFadeOut);//animation starts with opeque to transparent
				// but as sson as  animation reaches to end its view setVisibility is VISIBLE thts Y
				//	its effect is reversed..msg is 1st transparent then visible

			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// if animation is fade out hide them after completing animation
		if (animation == animFadeOut) {
			
			// hide faded out element
			img1.setVisibility(View.GONE);
		}
		
		if(animation == animFadeIn){
			// do something after fade in completed
			
			// set visibility of fade in element
			img2.setVisibility(View.VISIBLE);
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

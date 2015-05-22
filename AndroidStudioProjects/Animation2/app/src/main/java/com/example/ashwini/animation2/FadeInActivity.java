package com.example.ashwini.animation2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
/** 3 classes to import **/
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



/* 3 elements attach : FadeInActivity.java + activity_fadein.xml + res/anim folder : fade_in.xml  */

// AnimationLIstener :
/* 1) It is an interface & Listens for notifications send by animation..
   2)  Notifications indicate animation related events, such as the end or the repetition or start of the animation.
   2) Public Methods :
			a)	abstract void	onAnimationEnd(Animation animation) : Notifies the end of the animation.
			b)  abstract void	onAnimationRepeat(Animation animation) : Notifies the repetition of the animation.
			c)	abstract void	onAnimationStart(Animation animation) : Notifies the start of the animation.

		------------------------------------------------------------------------
		AnimationUtils : extends Object
		                Defines common utilities for working with animations.
		                http://developer.android.com/reference/android/view/animation/AnimationUtils.html
		                have any of following methods as :
		                a) currentAnimationTimeMillis() : Returns the current animation time in milliseconds.
		                b) loadAnimation(Context context, int id) :Loads an Animation object from a resource
		                c) makeOutAnimation(Context c, boolean toRight) :Make an animation for objects becoming invisible.
		                d)makeInAnimation(Context c, boolean fromLeft): Make an animation for objects becoming visible.
		                e)loadLayoutAnimation(Context context, int id) :Loads a LayoutAnimationController object from a resource


*/
public class FadeInActivity extends Activity implements AnimationListener {

	TextView txtMessage;
	Button btnStart;

	// Animation
	Animation animFadein; // Name of Animation

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fadein);

		txtMessage = (TextView) findViewById(R.id.txtMessage);
		btnStart = (Button) findViewById(R.id.btnStart);

		// load the animation
		/*loadAnimation(Context context, int id) : Loads an Animation object from a resource (resource here is id)*/
		animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
		
		// set animation listener
		animFadein.setAnimationListener(this);

		// button click event
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				txtMessage.setVisibility(View.VISIBLE);
				
				// start the animation
				txtMessage.startAnimation(animFadein);
			}
		});

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// Take any action after completing the animation

		// check for fade in animation
		if (animation == animFadein) {
			Toast.makeText(getApplicationContext(), "Animation Stopped",
					Toast.LENGTH_SHORT).show();
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


/*******
 *  fade_in.xml :
 *<set>
 A container that holds other animation
 elements (<alpha>, <scale>, <translate>, <rotate>) or other <set> elements. Represents an AnimationSet.
 a)  <alpha> :A fade-in or fade-out animation. Represents an AlphaAnimation.
 b)  <scale> :A resizing animation
 c)  <translate> : A vertical and/or horizontal motion.
 d) <rotate> :A rotation animation.


 <set xmlns:android="http://schemas.android.com/apk/res/android"android:fillAfter="true" >

 <alpha = for fade-in activity , from transparent to opaque
 android:duration="1000" //android:duration int = he time in milliseconds of the animation. 300ms is the default.
 android:fromAlpha="0.0"  //android:fromAlpha Float. Starting opacity offset, where 0.0 is transparent and 1.0 is opaque.
 // interpoltors : An interpolator is an animation modifier defined in XML that affects the rate of change in an animation.
 // This allows your existing animation effects to be accelerated, decelerated, repeated, bounced, etc.
 //All interpolators available in Android are subclasses of the Interpolator class.
 //For each interpolator class, Android includes a public resource you can reference in order to apply the interpolator to an animation using
 the android:interpolator attribute.
 The rate of change starts out slowly, then accelerates.
 attributes:android:factor Float. The acceleration rate (default is 1).
 android:interpolator="@android:anim/accelerate_interpolator"
 android:toAlpha="1.0" //android:toAlpha Float. Ending opacity offset, where 0.0 is transparent and 1.0 is opaque. />

 </set>

 *
 *
 */

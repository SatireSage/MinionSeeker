package ca.cmpt276.minionseeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;

/*
*   Class MainActivity inherits AppCompatActivity
*   Purpose: Provide functionality to activity_main.xml elements
* */
public class MainActivity extends AppCompatActivity {

    //Create a thread to concurrently have a timer running for a task that needs timing
    Thread timer;

    //Use MediaPlayer object to play mp3
    MediaPlayer hello;

    //Override the onCreate function such that on launch
    //We will have an animation of our buttons, a gradient background, a gif, and the minion sound playing in the background.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Changes action bar font color
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#0C457A'>" + getString(R.string.app_name) + "</font>"));

        TextView devInfo = findViewById(R.id.devInfo);
        devInfo.setVisibility(View.GONE);
        Button skipButton = findViewById(R.id.skipButton);
        skipButton.setVisibility(View.GONE);

        // play scanner sound if not a minion
        hello = MediaPlayer.create(this, R.raw.minions_hello);
        hello.start();

        // followed: https://www.youtube.com/watch?v=4lEnLTqsnaw for adding animated
        // gradient background
        ConstraintLayout layout = findViewById(R.id.welcomeScreen);
        AnimationDrawable animationDrawable = (AnimationDrawable) layout.getBackground();
        animationDrawable.setEnterFadeDuration(500);
        animationDrawable.setExitFadeDuration(500);
        animationDrawable.start();

        // Fade in the gif by changing the alpha value over a given duration of time
        GifImageView minionGif = findViewById(R.id.welcomeMinion);
        minionGif.setAlpha(0f);
        minionGif.animate().alpha(1f).setDuration(2000);

        // show the devInfo and skip button after 3 seconds
        timer = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> {
                    devInfo.setVisibility(View.VISIBLE);
                    skipButton.setVisibility(View.VISIBLE);
                    devInfo.setAlpha(0f);
                    devInfo.animate().alpha(1f).setDuration(1500);
                    skipButton.setAlpha(0f);
                    skipButton.animate().alpha(1f).setDuration(1500);

                    // make the devInfo and skip button spin
                    devInfo.animate().rotationBy(360).setDuration(1500);
                    skipButton.animate().rotationBy(-360).setDuration(1500);
                });
            }
        });
        timer.start();

        // After 5 seconds, if the skip button hasn't been pressed, launch an intent to enter the MainMenu activity.
        timer = new Thread(() -> {
            try {
                Thread.sleep(9000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
            }
        });
        timer.start();
    }

    // skipButton onClick method for the skip button
    // Stop the timer
    //Launch a new intent to enter the MainMenu activity
    public void skipButton(View view) {
        // cancel the timer
        timer.interrupt();
        timer = null;
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        hello.release();
    }

}
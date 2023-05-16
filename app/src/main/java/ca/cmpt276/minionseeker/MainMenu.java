package ca.cmpt276.minionseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import java.util.Objects;

import ca.cmpt276.minionseeker.model.GameManager;
import pl.droidsonroids.gif.GifImageView;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        GameManager.getInstance().getSavedValues(MainMenu.this);

        //Changes action bar font color
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#0C457A'>" + getString(R.string.app_name) + "</font>"));

    }

    // when back button is pressed, exit the app
    @Override
    public void onBackPressed() {
        // create dialog box to confirm exit
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit the app?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, id) -> finishAffinity());
        builder.setNegativeButton("No", (dialog, id) -> dialog.cancel());
        builder.show();
    }

    // on resume refresh the gif
    @Override
    protected void onResume() {
        super.onResume();
        GifImageView gif = findViewById(R.id.findMinions);
        gif.setImageResource(R.drawable.minions_searching);
    }

    // OnClick the Play Game button calls this method
    public void playGame(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    // OnClick the options button calls this method
    public void optionsButton(View view) {
        Intent intent = new Intent(this, OptionsMenu.class);
        startActivity(intent);
    }

    // OnClick the options button calls this method
    public void scoreButton(View view) {
        Intent intent = new Intent(this, ScoreMenu.class);
        startActivity(intent);
    }

    //OnClick the help button calls this method
    public void helpButton(View view){
        Intent intent = new Intent(this, HelpMenu.class);
        startActivity(intent);
    }

}
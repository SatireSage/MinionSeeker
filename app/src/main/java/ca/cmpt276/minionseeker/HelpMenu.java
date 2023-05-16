package ca.cmpt276.minionseeker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

/*
*  Class: HelpMenu inherit AppCompatActivity
*  Purpose: Provide functionality to the activity_help_menu.xml elements including onClick button functionality, and hyperlink sources
* */
public class HelpMenu extends AppCompatActivity {

    //Create button variables for to our respective ones in the activity XML file
    Button btnHowToPlay;
    Button btnSources;
    Button btnLicense;


    //Create alertdialog objects to open when we click the respective button
    AlertDialog.Builder builderHowToPlay;
    AlertDialog.Builder builderSources;
    AlertDialog.Builder builderLicense;

    //Override the onCreate function such that when the activity starts
    //We have our alert dialogs filled out with the information we want
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);


        //Changes action bar font color
        Objects.requireNonNull(getSupportActionBar()).setTitle(Html.fromHtml("<font color='#0C457A'>" + getString(R.string.app_name) + "</font>"));

        //Create alertdialog builder of "How to Play" Button
        btnHowToPlay = findViewById(R.id.howtoplay_button);
        builderHowToPlay = new AlertDialog.Builder(this);
        //Listen for onClick action for the button
        btnHowToPlay.setOnClickListener(view -> {
            //Populate the button with Title and a message
            //Provide cancelable option
            builderHowToPlay.setTitle("How to play?");
            builderHowToPlay.setMessage(R.string.how_to_play);
            builderHowToPlay.setCancelable(true);
            builderHowToPlay.setPositiveButton("Okay", (dialogInterface, i) -> {
            });
            builderHowToPlay.show();
        });

        //Create alertdialog builder of "Sources" Button
        btnSources = findViewById(R.id.sources_button);
        builderSources = new AlertDialog.Builder(this);
        //Listen for onClick action for the button
        btnSources.setOnClickListener(view -> {
            //Populate the button with Title and a message
            //Provide cancelable option
            builderSources.setTitle("Sources");
            builderSources.setView(setText());
            builderSources.setCancelable(true);
            builderSources.setPositiveButton("Okay", (dialogInterface, i) -> {
            });
            builderSources.show();
        });

        //Create alertdialog builder of "Licenses" Button
        btnLicense = findViewById(R.id.license_button);
        builderLicense = new AlertDialog.Builder(this);
        //Listen for onClick action for the button
        btnLicense.setOnClickListener(view -> {
            //Populate the button with Title and a message
            //Provide cancelable option
            builderLicense.setTitle("Licenses");
            builderLicense.setMessage(R.string.license);
            builderLicense.setCancelable(true);
            builderLicense.setPositiveButton("Okay", (dialogInterface, i) -> {
            });
            builderLicense.show();
        });
    }

    // setText
    // Purpose: Provide HTML hyperlinking functionality to our sources alert dialog message
    // So the user can click the link and view the sources themselves.
    public TextView setText(){
        TextView sourceTextView = new TextView(HelpMenu.this);
        sourceTextView.setPadding(5,5,5,5);
        sourceTextView.setGravity(Gravity.CENTER);
        sourceTextView.setMovementMethod(LinkMovementMethod.getInstance());
        sourceTextView.setText(Html.fromHtml("<b>Tutorials</b> <br> " +
                "- Dr Brian's YouTube Tutorials <br>" +
                "<a href=https://www.youtube.com/watch?v=KsyCfxxEIwQ>- Gif Animation Tutorial</a><br>" +
                "<a href=https://www.youtube.com/watch?v=4lEnLTqsnaw>- Animated Gradient Background.Com</a><br>" +
                "<a href=https://code.tutsplus.com/tutorials/how-to-add-a-dropdown-menu-in-android-studio--cms-37860>- Options Menu Dropdown</a><br>" +
                "<a href=https://www.youtube.com/watch?v=0SJAK_pKUoE>- Sound Tutorial <br>" +
                "<a href=https://stackoverflow.com/questions/13680219/save-value-of-spinner-selected-item>- Options Menu Spinner Item</a><br>" +
                "<a href=https://devstudioonline.com/article/create-rounded-background-as-border-radius-in-android-layout>- Rounded Buttons </a><br>" +
                "<a href=https://www.youtube.com/watch?v=5PaWtQAOdi8>- Setup custom Alert Dialog </a><br> <br>" +

                "<b>Media</b> <br>" +
                "<a href=https://www.pinterest.ca/pin/826762444091993289/>- Minion Gif</a><br>" +
                "<a href=https://www.pinterest.ca/pin/318911217375856747/>- Main Menu Minion</a><br>" +
                "<a href=https://www.pinterest.ca/pin/42713896452521110/>- Help Menu Minion (How To Play?)</a><br>" +
                "<a href=https://www.pinterest.ca/pin/34128909653346532/>- Help Menu Minion (Sources)</a><br>" +
                "<a href=https://www.youtube.com/watch?v=0dFqDg6UYRY/>- Minions Celebrating Sound (Sources)</a><br>" +
                "<a href=https://favpng.com/png_view/minions-minions-paradise-despicable-me-minion-rush-bob-the-minion-illumination-entertainment-png/Ac4S7kxz/>- Minion Found Image <br>" +
                "<a href=https://www.youtube.com/watch?v=4EoAHdwGBvU>- Hysterical Minion Laughter Sound Effect <br>" +
                "<a href=https://www.youtube.com/watch?v=DYztkmK_Vz4>- Banana Sound Effect <br>" +
                "<a href=https://www.pinterest.ca/pin/400187116866562010/>- Scores Page Minion <br>" +
                "<a href=https://www.pinterest.ca/pin/216806169536591999/>- Options Menu Minions <br>" +
                "<a href=https://www.youtube.com/watch?v=U-jTCYMfWMQ>- Minions welcome sound <br>" +
                "<a href=https://www.pinterest.ca/pin/418482990345391305/>- Minions Winner Dialog <br>" +
                "<a href=https://www.pinterest.ca/pin/386676317980417384/>- Help Menu Minion (License)</a><br>"));
        return sourceTextView;
    }
}
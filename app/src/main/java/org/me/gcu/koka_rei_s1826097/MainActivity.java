package org.me.gcu.koka_rei_s1826097;
// @Koka_Rei_S1826097


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView rawDataDisplay;
    private Button currentIncidentButton;
    private Button roadworksButton;
    private Button plannedRoadworksButton;
    private String result = "";
    private String url1 = "";
    private long lastBackPressed = 0;

    // Traffic Scotland Current Incidents XML link
    private String urlSourceCurrentIncidents = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    // Traffic Scotland Roadworks XML link
    private String urlSourceRoadworks = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    // Traffic Scotland Planned Roadworks XML link
    private String urlSourcePlannedRoadworks = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag", "in onCreate");

        // Set up the raw links to the graphical components

        currentIncidentButton = (Button) findViewById(R.id.btnCurrentIncidents);
        currentIncidentButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCurrentIncidentActivity();
                Log.e("MyTag", "Current Incidents - clicked IncidentButton");
            }
        });

        // More Code goes here
        roadworksButton = (Button) findViewById(R.id.btnRoadworks);
        roadworksButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRoadworksActivity();
                Log.e("MyTag", "Roadworks - clicked Roadworks Button");
            }
        });


        plannedRoadworksButton = (Button) findViewById(R.id.btnPlannedRoadworks);
        plannedRoadworksButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPlannedRoadworksActivity();
                Log.e("MyTag", "Planned Roadworks - clicked PlannedRoadworks Button");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            goToSettings();
            Log.e("MyTag", "Settings - clicked Settings Button");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Go to Settings Activity
    private void goToSettings() {
        Intent intentGoToSetting = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intentGoToSetting);
    }

    @Override
    public void onBackPressed() {
            askToFinish();
    }

    // Ask to finish method, method serves to ask the user to click the back button twice in 2sec to exit the application.
    private void askToFinish() {
        long current = System.currentTimeMillis();

        if (current > lastBackPressed && current - lastBackPressed <= 2000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                // Close app
                finishAffinity();
            }
        } else {
            lastBackPressed = current;
            // Show suggestion to the user
            Toast.makeText(this, R.string.toast_back_to_close, Toast.LENGTH_SHORT).show();
        }
    }

    //go To next Activity to get Current Incidents
    void goToCurrentIncidentActivity() {
        Intent goToCurrentIncidentIntent = new Intent(MainActivity.this, TrafficScotlandActivityList.class);
        goToCurrentIncidentIntent.putExtra("URL", urlSourceCurrentIncidents);
        goToCurrentIncidentIntent.putExtra("Title", currentIncidentButton.getText());
        startActivity(goToCurrentIncidentIntent);
    }

    //go To next Activity to get Roadworks , is the same Activity only changes the Url and Title parameters
    void goToRoadworksActivity() {
        Intent goToRoadworksIntent = new Intent(MainActivity.this, TrafficScotlandActivityList.class);
        goToRoadworksIntent.putExtra("URL", urlSourceRoadworks);
        goToRoadworksIntent.putExtra("Title", roadworksButton.getText());
        startActivity(goToRoadworksIntent);
    }

    //go To next Activity to get Planned Roadworks is the same Activity only changes  the Url and Title parameters
    void goToPlannedRoadworksActivity() {
        Intent goToPlannedRoadworksIntent = new Intent(MainActivity.this, TrafficScotlandActivityList.class);
        goToPlannedRoadworksIntent.putExtra("URL", urlSourcePlannedRoadworks);
        goToPlannedRoadworksIntent.putExtra("Title", plannedRoadworksButton.getText());
        startActivity(goToPlannedRoadworksIntent);
    }
}
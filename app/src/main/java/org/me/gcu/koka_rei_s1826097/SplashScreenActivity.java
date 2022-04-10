package org.me.gcu.koka_rei_s1826097;

// Koka_Rei_S1826097

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int SPLASH_SCREEN_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Delay 3 seconds before going to the MainActivity
        new Handler().postDelayed(this::goToMainActivityMethod, SPLASH_SCREEN_TIME);
    }

    // Go to the MainActivity
    private void goToMainActivityMethod() {
        Intent goToMainActivityIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(goToMainActivityIntent);
    }
}
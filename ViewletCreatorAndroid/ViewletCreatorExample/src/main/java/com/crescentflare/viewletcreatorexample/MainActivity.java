package com.crescentflare.viewletcreatorexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * The main activity shows a small layout example, explanation and buttons to show other layout examples
 */
public class MainActivity extends AppCompatActivity
{
    // ---
    // Initialization
    // ---

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Set view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

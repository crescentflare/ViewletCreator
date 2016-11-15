package com.crescentflare.viewletcreatorexample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.ViewletLoader;
import com.crescentflare.viewletcreator.binder.ViewletAnnotationBinder;
import com.crescentflare.viewletcreator.binder.ViewletRef;

import java.util.Map;

/**
 * The playground activity has a layout which can be adjusted to play around with the library
 */
public class PlaygroundActivity extends AppCompatActivity implements LiveJson.LiveJsonListener
{
    // ---
    // Members
    // ---

    private LiveJson liveLayout = null;


    // ---
    // Initialization
    // ---

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Configure activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
        setTitle("Playground");
        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        // Live loading or file loading
        updateState();
    }


    // ---
    // State handling
    // ---

    @Override
    protected void onPause()
    {
        super.onPause();
        if (liveLayout != null)
        {
            liveLayout.setPollingEnabled(false);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (liveLayout != null)
        {
            liveLayout.setPollingEnabled(Settings.instance.isAutoRefresh());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // ---
    // Update state based on settings
    // ---

    private void updateState()
    {
        if (Settings.instance.isServerEnabled())
        {
            liveLayout = new LiveJson("layout_playground.json");
            liveLayout.setPollingEnabled(Settings.instance.isAutoRefresh());
            liveLayout.setListener(this);
        }
        else
        {
            Map<String, Object> attributes = ViewletLoader.loadAttributes(this, R.raw.layout_playground);
            if (liveLayout != null)
            {
                liveLayout.setListener(null);
            }
            liveLayout = null;
            if (attributes != null)
            {
                layoutLoaded(attributes);
            }
        }
    }


    // ---
    // Inflation
    // ---

    private void layoutLoaded(Map<String, Object> attributes)
    {
        View view = findViewById(R.id.activity_playground_container);
        ViewletCreator.inflateOn(view, attributes, null, new ViewletAnnotationBinder(this));
    }


    // ---
    // Live viewlet handling
    // ---


    @Override
    public void jsonUpdated(LiveJson liveJson)
    {
        layoutLoaded(liveJson.getData());
    }

    @Override
    public void jsonFailed(LiveJson liveJson)
    {
        ViewGroup contentView = (ViewGroup)findViewById(R.id.activity_playground_container);
        if (contentView != null)
        {
            TextView errorView = new TextView(this);
            int sidePadding = (int)(getResources().getSystem().getDisplayMetrics().density * 10);
            contentView.removeAllViews();
            errorView.setText("Can't connect to server or load layout_playground.json");
            errorView.setTextColor(Color.RED);
            errorView.setPadding(sidePadding, 0, sidePadding, 0);
            contentView.addView(errorView);
        }
    }
}

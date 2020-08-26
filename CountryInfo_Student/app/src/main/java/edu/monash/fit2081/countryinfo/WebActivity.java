package edu.monash.fit2081.countryinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        getSupportActionBar().setTitle("Wikipedia Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //put a back button

        final String selectedCountry = getIntent().getStringExtra("country name");

        WebView myWeb = findViewById(R.id.webView);
        myWeb.setWebViewClient(new WebViewClient()); //to browse within the app itself not chrome or another browser
        myWeb.loadUrl("https://en.wikipedia.org/wiki/" + selectedCountry);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this); //navigate back to last activity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

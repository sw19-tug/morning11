package com.example.paintu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bt_tools_chooser) {
            if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

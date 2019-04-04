package com.example.paintu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    LinearLayout linearBottomSheet;
    BottomSheetBehavior sheetBehavior;
    DrawingView drawingView;
    LinearLayout toolPointLayout;
    LinearLayout toolLineLayout;
    LinearLayout toolPathLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        drawingView = (DrawingView) findViewById(R.id.drawing_view);
        toolPointLayout = (LinearLayout) findViewById(R.id.tool_point);
        toolLineLayout = (LinearLayout) findViewById(R.id.tool_line);
        toolPathLayout = (LinearLayout) findViewById(R.id.tool_path);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sheetBehavior = BottomSheetBehavior.from(linearBottomSheet);

        toolPointLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setTool(DrawingView.TOOL_POINT);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        toolLineLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setTool(DrawingView.TOOL_LINE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        toolPathLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setTool(DrawingView.TOOL_PATH);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

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


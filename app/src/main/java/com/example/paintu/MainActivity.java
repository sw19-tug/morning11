package com.example.paintu;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements DrawingView.DrawingInProgress {

    LinearLayout linearBottomSheet;
    BottomSheetBehavior sheetBehavior;
    DrawingView drawingView;
    LinearLayout toolPointLayout;
    LinearLayout toolLineLayout;
    LinearLayout toolPathLayout;
    LinearLayout toolEraserLayout;
    TextView colorBlack;
    TextView colorWhite;
    TextView colorRed;
    TextView colorGreen;
    TextView colorBlue;
    TextView colorYellow;
    TextView colorOrange;
    TextView colorPurple;

    LinearLayout options;

    TextView stroke;
    LinearLayout strokeOptions;
    SeekBar strokeSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        drawingView = (DrawingView) findViewById(R.id.drawing_view);
        drawingView.setListener(this);

        toolPointLayout = (LinearLayout) findViewById(R.id.tool_point);
        toolLineLayout = (LinearLayout) findViewById(R.id.tool_line);
        toolPathLayout = (LinearLayout) findViewById(R.id.tool_path);
        toolEraserLayout = (LinearLayout) findViewById(R.id.tool_eraser);

        colorBlack = (TextView) findViewById(R.id.color_black);
        colorWhite = (TextView) findViewById(R.id.color_white);
        colorRed = (TextView) findViewById(R.id.color_red);
        colorGreen = (TextView) findViewById(R.id.color_green);
        colorBlue = (TextView) findViewById(R.id.color_blue);
        colorYellow = (TextView) findViewById(R.id.color_yellow);
        colorOrange = (TextView) findViewById(R.id.color_orange);
        colorPurple = (TextView) findViewById(R.id.color_purple);

        options = findViewById(R.id.options);

        stroke = findViewById(R.id.stroke);
        strokeOptions = findViewById(R.id.stroke_options);
        strokeSeekBar = findViewById(R.id.stroke_seekbar);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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

        toolEraserLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setTool(DrawingView.TOOL_ERASER);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        colorBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_black));
                inflateColorButton(getResources().getColor(R.color.option_color_black));
            }
        });

        colorWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_white));
                inflateColorButton(getResources().getColor(R.color.option_color_white));
            }
        });

        colorRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_red));
                inflateColorButton(getResources().getColor(R.color.option_color_red));
            }
        });

        colorGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_green));
                inflateColorButton(getResources().getColor(R.color.option_color_green));
            }
        });

        colorBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_blue));
                inflateColorButton(getResources().getColor(R.color.option_color_blue));
            }
        });

        colorYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_yellow));
                inflateColorButton(getResources().getColor(R.color.option_color_yellow));
            }
        });

        colorOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_orange));
                inflateColorButton(getResources().getColor(R.color.option_color_orange));
            }
        });

        colorPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_purple));
                inflateColorButton(getResources().getColor(R.color.option_color_purple));
            }
        });

        strokeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                drawingView.getDrawPaint().setStrokeWidth(progress + 10);
                drawingView.getEraserPaint().setStrokeWidth(progress + 10);
                stroke.setText(Integer.toString(progress + 10));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

    public void inflateColorButton(int color) {
        if(color == getResources().getColor(R.color.option_color_black)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlack.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorBlack.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlack.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorBlack.setLayoutParams(params);
        }

        if(color == getResources().getColor(R.color.option_color_white)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorWhite.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorWhite.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorWhite.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorWhite.setLayoutParams(params);
        }

        if(color == getResources().getColor(R.color.option_color_red)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorRed.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorRed.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorRed.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorRed.setLayoutParams(params);
        }

        if(color == getResources().getColor(R.color.option_color_green)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorGreen.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorGreen.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorGreen.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorGreen.setLayoutParams(params);
        }

        if(color == getResources().getColor(R.color.option_color_blue)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlue.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorBlue.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlue.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorBlue.setLayoutParams(params);
        }

        if(color == getResources().getColor(R.color.option_color_yellow)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorYellow.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorYellow.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorYellow.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorYellow.setLayoutParams(params);
        }

        if(color == getResources().getColor(R.color.option_color_orange)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorOrange.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorOrange.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorOrange.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorOrange.setLayoutParams(params);
        }

        if(color == getResources().getColor(R.color.option_color_purple)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorPurple.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorPurple.setLayoutParams(params);
        }
        else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorPurple.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorPurple.setLayoutParams(params);
        }
    }

    @Override
    public void onDrawingStart() {
        final Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out);
        fadeOut.setDuration(200);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                options.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        options.startAnimation(fadeOut);

    }

    @Override
    public void onDrawingEnd() {
        final Animation fadeIn = AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in);
        fadeIn.setDuration(200);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                options.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        options.startAnimation(fadeIn);
    }
}


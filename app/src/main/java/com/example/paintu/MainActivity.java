package com.example.paintu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v14.preference.PreferenceFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements DrawingView.DrawingInProgress {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 112;

    public static Boolean switchPref;
    public static SharedPreferences sharedPref;

    LinearLayout linearBottomSheet;
    BottomSheetBehavior sheetBehavior;
    DrawingView drawingView;
    LinearLayout toolPointLayout;
    LinearLayout toolLineLayout;
    LinearLayout toolPathLayout;
    LinearLayout toolEraserLayout;
    LinearLayout toolCircleLayout;
    LinearLayout toolRectangleLayout;
    LinearLayout toolFillLayout;
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
        toolCircleLayout = (LinearLayout) findViewById(R.id.tool_circle);
        toolRectangleLayout = (LinearLayout) findViewById(R.id.tool_rectangle);
        toolFillLayout = (LinearLayout) findViewById(R.id.tool_fill);

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

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        final MediaPlayer blackSound = MediaPlayer.create(this, R.raw.black);
        final MediaPlayer whiteSound = MediaPlayer.create(this, R.raw.white);
        final MediaPlayer redSound = MediaPlayer.create(this, R.raw.red);
        final MediaPlayer greenSound = MediaPlayer.create(this, R.raw.green);
        final MediaPlayer blueSound = MediaPlayer.create(this, R.raw.blue);
        final MediaPlayer yellowSound = MediaPlayer.create(this, R.raw.yellow);
        final MediaPlayer orangeSound = MediaPlayer.create(this, R.raw.orange);
        final MediaPlayer pinkSound = MediaPlayer.create(this, R.raw.pink);

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

        toolRectangleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setTool(DrawingView.TOOL_RECTANGLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        toolCircleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setTool(DrawingView.TOOL_CIRCLE);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        toolFillLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setTool(DrawingView.TOOL_PAINT_BUCKET);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        colorBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_black));
                inflateColorButton(getResources().getColor(R.color.option_color_black));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    blackSound.start();
                }
            }
        });

        colorWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_white));
                inflateColorButton(getResources().getColor(R.color.option_color_white));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    whiteSound.start();
                }
            }
        });

        colorRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_red));
                inflateColorButton(getResources().getColor(R.color.option_color_red));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    redSound.start();
                }
            }
        });

        colorGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_green));
                inflateColorButton(getResources().getColor(R.color.option_color_green));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    greenSound.start();
                }
            }
        });

        colorBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_blue));
                inflateColorButton(getResources().getColor(R.color.option_color_blue));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    blueSound.start();
                }
            }
        });

        colorYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_yellow));
                inflateColorButton(getResources().getColor(R.color.option_color_yellow));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    yellowSound.start();
                }
            }
        });

        colorOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_orange));
                inflateColorButton(getResources().getColor(R.color.option_color_orange));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    orangeSound.start();
                }
            }
        });

        colorPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setPaintColor(getResources().getColor(R.color.option_color_purple));
                inflateColorButton(getResources().getColor(R.color.option_color_purple));

                switchPref = sharedPref.getBoolean(SettingsActivity.KEY_PREF_COLORBLIND_MODE_SWITCH, false);

                if(switchPref) {
                    pinkSound.start();
                }
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

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
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
        else if(id == R.id.save_btn){

            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                this.save();
            else
            {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        Log.d("SAVING", "Comes before");
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                        Log.d("SAVING", "Comes after");

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    this.save();
                }
            }

        }
           if (id == R.id.settings_button) {

               Intent intent = new Intent(this, SettingsActivity.class);
               startActivity(intent);
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
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.save();
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request.
            }
        }
    }

    public void save () {
        AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
        saveDialog.setTitle("Save drawing");
        saveDialog.setMessage("Save drawing to device Gallery?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String filename;
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                filename = sdf.format(date);

                try {
                    File path = new File(Environment.getExternalStorageDirectory(), "paintu");
                    if (!path.exists())
                        path.mkdirs();
                    OutputStream fOut = null;
                    File file = new File(path, filename + ".png");
                    fOut = new FileOutputStream(file);

                    drawingView.getBitmap().compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    fOut.flush();
                    fOut.close();


                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (filename != null) {
                    Toast savedToast = Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG);
                    savedToast.show();
                } else {
                    Toast unSaved = Toast.makeText(getApplicationContext(), "Opps, something went wrong, image not saved", Toast.LENGTH_LONG);
                    unSaved.show();
                }

            }

        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();
    }

    public static SharedPreferences getSharedPref() {
        return sharedPref;
    }
}


package com.example.paintu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements DrawingView.DrawingInProgress, DrawingView.UndoBtnAvailable {
    protected static final int GALLERY_PICTURE = 1;
    protected static final int CAMERA_PICTURE = 2;


    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 112;

    public static Boolean switchPref;
    public static SharedPreferences sharedPref;

    LinearLayout linearBottomSheet;
    BottomSheetBehavior sheetBehavior;
    DrawingView drawingView;

    LinearLayout options;
    LinearLayout toolPointLayout;
    LinearLayout toolLineLayout;
    LinearLayout toolPathLayout;
    LinearLayout toolEraserLayout;
    LinearLayout toolCircleLayout;
    LinearLayout toolRectangleLayout;
    LinearLayout toolFillLayout;
    LinearLayout toolImportImageFromGalleryLayout;
    LinearLayout toolImportImageFromCameraLayout;
    LinearLayout toolFiltersLayout;

    TextView colorBlack;
    TextView colorWhite;
    TextView colorRed;
    TextView colorGreen;
    TextView colorBlue;
    TextView colorYellow;
    TextView colorOrange;
    TextView colorPurple;

    MenuItem toolUndo;

    TextView stroke;
    LinearLayout strokeOptions;
    LinearLayout colorRow;
    SeekBar strokeSeekBar;

    int lastUsedTool = 1;

    FloatingActionButton dragDone;

    // we use this when photo is taken
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearBottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);

        drawingView = (DrawingView) findViewById(R.id.drawing_view);
        drawingView.setListener(this);
        drawingView.setBtnListner(this);

        toolPointLayout = (LinearLayout) findViewById(R.id.tool_point);
        toolLineLayout = (LinearLayout) findViewById(R.id.tool_line);
        toolPathLayout = (LinearLayout) findViewById(R.id.tool_path);
        toolEraserLayout = (LinearLayout) findViewById(R.id.tool_eraser);
        toolCircleLayout = (LinearLayout) findViewById(R.id.tool_circle);
        toolRectangleLayout = (LinearLayout) findViewById(R.id.tool_rectangle);
        toolFillLayout = (LinearLayout) findViewById(R.id.tool_fill);
        toolImportImageFromGalleryLayout = (LinearLayout) findViewById(R.id.tool_import_gallery);
        toolImportImageFromCameraLayout = (LinearLayout) findViewById(R.id.tool_import_camera);
        toolFiltersLayout = (LinearLayout) findViewById(R.id.tool_filters);

        dragDone = (FloatingActionButton) findViewById(R.id.button_dragdone);

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
        colorRow = findViewById(R.id.color_row);
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

        //https://stackoverflow.com/questions/11732872/android-how-can-i-call-camera-or-gallery-intent-together
        toolImportImageFromGalleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastUsedTool = drawingView.getTool();
                drawingView.setTool(DrawingView.TOOL_IMPORT);

                strokeOptions.setVisibility(View.GONE);
                colorRow.setVisibility(View.GONE);


                Intent pictureActionIntent = new Intent(Intent.ACTION_PICK);
                pictureActionIntent.setType("image/*");

                // Mime types we accept
                String[] mimeTypes = {"image/jpeg", "image/png"};
                pictureActionIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);

                // request code 2 for get image from gallery,...
                pictureActionIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(
                        pictureActionIntent,
                        GALLERY_PICTURE);

            }
        });

        toolImportImageFromCameraLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lastUsedTool = drawingView.getTool();
                drawingView.setTool(DrawingView.TOOL_IMPORT);

                strokeOptions.setVisibility(View.GONE);
                colorRow.setVisibility(View.GONE);

                int camera = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA);
                int storage = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (camera != PackageManager.PERMISSION_GRANTED && storage != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "You dont gave me permission to use this!", Toast.LENGTH_LONG).show();

                    return;
                }

                // options.setVisibility(View.GONE);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        galleryAddPic();
                    } catch (IOException ex) {
                        Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                                "com.example.paintu.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                        startActivityForResult(takePictureIntent, CAMERA_PICTURE);
                    }
                }

            }
        });

        // reset to last tool
        dragDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                //dragDone.hide();
                dragDone.setVisibility(View.GONE);
                drawingView.finalizeBitmap();
                drawingView.setTool(lastUsedTool);
                strokeOptions.setVisibility(View.VISIBLE);
                colorRow.setVisibility(View.VISIBLE);
            }
        });

        toolFiltersLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //  drawingView.setTool(DrawingView.TOOL_FILTERS);
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle(R.string.pick_filter)
                        .setItems(R.array.filters_names, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                drawingView.applyFilter(which);

                            }
                        });
                builder.show();
            }
        });
    }

    public void enableUndoBt()
    {
        toolUndo.setEnabled(true);
    }

    public void disableUndoBt()
    {
        toolUndo.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //https://stackoverflow.com/questions/16500415/findviewbyid-for-menuitem-returns-null
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        toolUndo = menu.findItem(R.id.bt_undo);
        disableUndoBt();

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
        else if(id == R.id.bt_undo){
            this.drawingView.undo();
            if(drawingView.isUndoPossible())
            {
                enableUndoBt();
            }
            else
            {
                disableUndoBt();
            }
            return super.onOptionsItemSelected(item);
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

        else if (id == R.id.clear_btn){
            this.clearScreen();
        }

        else if (id == R.id.settings_button) {

               Intent intent = new Intent(this, SettingsActivity.class);
               startActivity(intent);
               return true;
        }
        else if(id == R.id.share_btn)
        {
            String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), drawingView.getBitmap(),"title", null);
            Uri bitmapUri = Uri.parse(bitmapPath);
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/png");
            intent.putExtra(Intent.EXTRA_STREAM, bitmapUri );
            startActivity(Intent.createChooser(intent , "Share"));

        }

        return super.onOptionsItemSelected(item);
    }

    public void inflateColorButton(int color) {
        if (color == getResources().getColor(R.color.option_color_black)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlack.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorBlack.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlack.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorBlack.setLayoutParams(params);
        }

        if (color == getResources().getColor(R.color.option_color_white)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorWhite.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorWhite.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorWhite.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorWhite.setLayoutParams(params);
        }

        if (color == getResources().getColor(R.color.option_color_red)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorRed.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorRed.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorRed.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorRed.setLayoutParams(params);
        }

        if (color == getResources().getColor(R.color.option_color_green)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorGreen.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorGreen.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorGreen.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorGreen.setLayoutParams(params);
        }

        if (color == getResources().getColor(R.color.option_color_blue)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlue.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorBlue.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorBlue.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorBlue.setLayoutParams(params);
        }

        if (color == getResources().getColor(R.color.option_color_yellow)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorYellow.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorYellow.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorYellow.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorYellow.setLayoutParams(params);
        }

        if (color == getResources().getColor(R.color.option_color_orange)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorOrange.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorOrange.setLayoutParams(params);
        } else {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorOrange.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.not_selected_color);
            colorOrange.setLayoutParams(params);
        }

        if (color == getResources().getColor(R.color.option_color_purple)) {
            ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) colorPurple.getLayoutParams();
            params.width = getResources().getDimensionPixelSize(R.dimen.selected_color);
            params.height = getResources().getDimensionPixelSize(R.dimen.selected_color);
            colorPurple.setLayoutParams(params);
        } else {
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

                    MediaScannerConnection.scanFile(getApplicationContext(), new String[] { file.getPath() }, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("SAVING", "Scanned " + path);
                                }
                            });

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

    // https://stackoverflow.com/questions/41150995/appcompatactivity-oncreate-can-only-be-called-from-within-the-same-library-group
    // RestrictedApi
    //-------------
    //Summary: Restricted API
    //
    //Priority: 4 / 10
    //Severity: Error
    //Category: Correctness
    //
    //This API has been flagged with a restriction that has not been met.
    //
    //Examples of API restrictions:
    //* Method can only be invoked by a subclass
    //* Method can only be accessed from within the same library (defined by the
    //Gradle library group id)
    //* Method can only be accessed from tests.
    //
    //You can add your own API restrictions with the @RestrictTo annotation.
    // http://tools.android.com/tips/lint-checks
    // TODO: sorry i was not able to get rid of this.
    @SuppressLint("RestrictedApi") // this one is needed for dragDone.setVisibility(..)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result code is RESULT_OK only if the user selects an Image

        //dragDone.show(); // does not work
        dragDone.setVisibility(View.VISIBLE);

        //Bitmap bitmap_resized = null;
        Bitmap bitmap_full = null;

        boolean actionCallOk = false;

        if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            Uri selectedImage = data.getData();

            if (selectedImage == null) {
                Toast.makeText(this, "No image selected.", Toast.LENGTH_LONG).show();
                return;
            }

            try {
                bitmap_full = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                actionCallOk = true;
            } catch (IOException e) {
                actionCallOk = false;
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        if (resultCode == RESULT_OK && requestCode == CAMERA_PICTURE) {
            try {
                bitmap_full = BitmapFactory.decodeFile(currentPhotoPath);
                actionCallOk = true;
            } catch (Exception e) {
                actionCallOk = false;
                e.printStackTrace();
            }
        }

        /* draw resized image */
        if (bitmap_full != null && actionCallOk == true) {
            drawingView.saveUndoPoint();
            drawingView.setDrawBitmap(bitmap_full);
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            drawingView.invalidate();

        } else {
            bitmap_full = null;
            dragDone.setVisibility(View.GONE);
            drawingView.setTool(lastUsedTool);
            strokeOptions.setVisibility(View.VISIBLE);
            colorRow.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Image not loaded.", Toast.LENGTH_LONG).show();
        }

    }

    // create empty temporary image
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    // trigger mediascanner to add image to gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void clearScreen() {
        AlertDialog.Builder clearScreenDialog = new AlertDialog.Builder(this);
        clearScreenDialog.setTitle("Clear");
        clearScreenDialog.setMessage("Clear the screen?");
        clearScreenDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                drawingView.clearScreen();
            }

        });
        clearScreenDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        clearScreenDialog.show();
    }

    public static SharedPreferences getSharedPref() {
        return sharedPref;
    }
}


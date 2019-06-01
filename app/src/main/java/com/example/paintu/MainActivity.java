package com.example.paintu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements DrawingView.DrawingInProgress {
    protected static final int GALLERY_PICTURE = 1;


    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 112;

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

    TextView colorBlack;
    TextView colorWhite;
    TextView colorRed;
    TextView colorGreen;
    TextView colorBlue;
    TextView colorYellow;
    TextView colorOrange;
    TextView colorPurple;

    TextView stroke;
    LinearLayout strokeOptions;
    LinearLayout colorRow;
    SeekBar strokeSeekBar;

    int lastUsedTool = 0;

    FloatingActionButton dragDone;

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
        toolImportImageFromGalleryLayout = (LinearLayout) findViewById(R.id.tool_import_gallery);

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

        // reset to last tool
        dragDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                dragDone.setVisibility(View.GONE);
                drawingView.finalizeBitmap();
                drawingView.setTool(lastUsedTool);
                //options.setVisibility(View.VISIBLE);
                strokeOptions.setVisibility(View.VISIBLE);
                colorRow.setVisibility(View.VISIBLE);
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

        /* draw resized image */
        if (bitmap_full != null && actionCallOk == true) {
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
}


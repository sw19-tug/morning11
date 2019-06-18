package com.example.paintu;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Canvas;

public class VintageFilter{

    Canvas canvas;
    Bitmap bitmap;

    public VintageFilter(Canvas canvas, Bitmap bitmap) {
        this.canvas = canvas;
        this.bitmap = bitmap;
    }

    public void applyFilter(){
        float[] mat = new float[]
                {
                        0.7f, 0f, 0f, 0f, 0f,
                        0f ,0.3f ,0f ,0f,0f,
                        0f, 0f, 0.12f, 0f, 0f,
                        0f, 0f, 0f, 1f, 0f,};

        ColorMatrix cm = new ColorMatrix(mat);
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter( cm);

        Paint paint = new Paint();
        paint.setColorFilter(cf);
        canvas.drawBitmap(bitmap, 0,0,paint);
    }
}
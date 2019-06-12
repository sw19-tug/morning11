package com.example.paintu;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Canvas;




public class DeepFryFilter{

    Canvas canvas;
    Bitmap bitmap;

    public DeepFryFilter(Canvas canvas, Bitmap bitmap) {
        this.canvas = canvas;
        this.bitmap = bitmap;
    }

    public void applyFilter(){
        Bitmap bmpGrayscale = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);

        // Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(100);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        canvas.drawBitmap(bitmap, 0, 0, paint);


/*        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        Bitmap bitmap = new Bitmap()
        bitmap = canvas.bi
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);

        canvas.setDrawFilter();*/
    }



}

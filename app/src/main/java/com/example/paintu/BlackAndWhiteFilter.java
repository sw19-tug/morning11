package com.example.paintu;
import android.graphics.Bitmap;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Canvas;




public class BlackAndWhiteFilter{

    Canvas canvas;
    Bitmap bitmap;

    public BlackAndWhiteFilter(Canvas canvas, Bitmap bitmap) {
        this.canvas = canvas;
        this.bitmap = bitmap;
    }

    public void applyFilter(){
        Bitmap bmpGrayscale = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        canvas.drawBitmap(bitmap, 0, 0, paint);

    }



}

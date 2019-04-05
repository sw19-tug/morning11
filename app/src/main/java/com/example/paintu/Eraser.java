package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Eraser extends DrawPath {

    public Eraser(Canvas canvas, Paint paint, int backgroundColor) {
        super(canvas, paint);
        paint.setColor(backgroundColor);
    }
}

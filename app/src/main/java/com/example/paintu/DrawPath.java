package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawPath {

    private Paint drawPaint = null;
    private Canvas drawCanvas = null;

    private int paintColor = 0xFFFFFF00;


    public DrawPath(Canvas canvas, Paint paint)
    {
        this.drawCanvas = canvas;
        this.drawPaint = paint;
    }

    public void setColor(int color)
    {
        this.paintColor = color;
    }

    public int getColor()
    {
        return this.paintColor;
    }


}

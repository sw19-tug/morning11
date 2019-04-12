package com.example.paintu;
import  android.view.MotionEvent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class DrawPoint{

    private Paint paint;
    private Canvas canvas;

    public DrawPoint(Canvas canvas, Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
    }

    public boolean drawPoint(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                canvas.drawPoint(event.getX(),event.getY(), paint);
                return true;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return false;
    }
}

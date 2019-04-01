package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class DrawPath {

    private Paint paint = null;
    private Canvas canvas = null;
    private Path path = null;
    private MotionEvent motionEvent = null;

    public DrawPath(Canvas canvas, Paint paint)
    {
        this.canvas = canvas;
        this.paint = paint;
    }

    public boolean draw(MotionEvent event)
    {
        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();

        if(path == null)
            path = new Path();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(touchX, touchY);
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);
                canvas.drawPath(path, paint);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(touchX, touchY);
                canvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                path.reset();
                break;
        }
        return false;

    }

}

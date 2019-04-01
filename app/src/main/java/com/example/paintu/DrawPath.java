package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class DrawPath {

    private Paint paint = null;
    private Canvas canvas = null;
    private Path path = new Path();
    private MotionEvent motionEvent = null;

    public DrawPath(Canvas canvas, Paint paint)
    {
        this.canvas = canvas;
        this.paint = paint;
    }

    public boolean draw(MotionEvent event)
    {

        if(event == null)
            return false;

        if(canvas == null)
            return false;

        int action = event.getAction();
        float touchX = event.getX();
        float touchY = event.getY();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                if(path != null)
                    path = new Path();

                path.reset();

                path.moveTo(touchX, touchY);
                path.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                if(path == null)
                    break;

                path.lineTo(touchX, touchY);
                canvas.drawPath(path, paint);
                break;
            case MotionEvent.ACTION_UP:
                if(path == null)
                    break;

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

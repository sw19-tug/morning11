package com.example.paintu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    public static final int TOOL_POINT = 1;
    public static final int TOOL_LINE = 2;
    public static final int TOOL_PATH = 3;

    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xFF000000;
    Canvas canvas;
    Bitmap bitmap;
    int tool = TOOL_POINT;
    DrawPoint drawPoint;
    DrawLine drawLine;
    DrawPath drawPath;
    DrawLine.Line line;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);
        setupDrawing();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        drawPoint = new DrawPoint(canvas, drawPaint);
        drawLine = new DrawLine(canvas, drawPaint);
        drawPath = new DrawPath(canvas, drawPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);
        if(line != null && line.getEndX() >= 0 && line.getEndY() >= 0) {
            canvas.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(),
                    drawPaint);
            Log.d("StartX", line.getStartX() + "");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(tool == TOOL_POINT)
            drawPoint.drawPoint(event);
        else if(tool == TOOL_LINE)
            line = drawLine.draw(event);
        else if(tool == TOOL_PATH)
            drawPath.draw(event);
        Log.d("Event", event.getAction() + ": " + event.getX() + ", " + event.getY());
        this.invalidate();
        return true;
    }

    private void setupDrawing() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
        drawPaint.setColor(paintColor);
    }

    public int getTool() {
        return tool;
    }

    public void setTool(int tool) {
        this.tool = tool;
    }
}

package com.example.paintu;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    public interface DrawingInProgress {
        public void onDrawingStart();

        public void onDrawingEnd();
    }

    public static final int TOOL_POINT = 1;
    public static final int TOOL_LINE = 2;
    public static final int TOOL_PATH = 3;
    public static final int TOOL_PAINT_BUCKET = 4;
    public static final int TOOL_ERASER = 5;
    public static final int TOOL_RECTANGLE = 7;
    public static final int TOOL_IMPORT = 8;

    DrawingInProgress listener;
    private Paint drawPaint, canvasPaint, eraserPaint;
    private int paintColor = 0xFF000000;
    private int backgroundColor = 0xFFFFFFFF;
    Canvas canvas;
    Bitmap bitmap;
    int tool = TOOL_POINT;
    DrawPoint drawPoint;
    DrawLine drawLine;
    DrawPath drawPath;
    DrawRectangle drawRectangle;
    DrawLine.Line line;
    DrawRectangle.Rectangle rectangle;
    Eraser eraser;
    DrawBitmap drawBitmap;
    DrawBitmap.BitmapOwn ownBitmap;
    private int width = 0;
    private int height = 0;
    private boolean finalizeBitmap = false;


    public DrawingView(Context context, AttributeSet attrs) {
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
        eraser = new Eraser(canvas, eraserPaint, backgroundColor);
        drawRectangle = new DrawRectangle(canvas, drawPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(bitmap == null)
            return;

        canvas.drawBitmap(bitmap, 0, 0, canvasPaint);

        if (line != null && line.getEndX() >= 0 && line.getEndY() >= 0) {
            canvas.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(),
                    drawPaint);
        }

        if (rectangle != null)
            canvas.drawRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), drawPaint);


        if (!finalizeBitmap) {
            canvas.save();
            if (this.ownBitmap != null) {
                canvas.setMatrix(drawBitmap.getMatrix());  // get transformation matrix
                canvas.drawBitmap(this.ownBitmap.getBitmap(), this.ownBitmap.getLeft(), this.ownBitmap.getTop(), drawPaint);
            }
            canvas.restore();
        } else {
            finalizeBitmap = false;
            if (this.ownBitmap != null) {
                // save origin!
                this.canvas.setMatrix(drawBitmap.getMatrix());  // get transformation matrix
                this.canvas.drawBitmap(this.ownBitmap.getBitmap(), this.ownBitmap.getLeft(), this.ownBitmap.getTop(), drawPaint);
                this.canvas.setMatrix(new Matrix());
                canvas = null;
                this.ownBitmap = null;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (tool) {
            case TOOL_POINT:
                drawPoint.drawPoint(event);
                break;
            case TOOL_LINE:
                line = drawLine.draw(event);
                break;
            case TOOL_PATH:
                drawPath.draw(event);
                break;
            case TOOL_ERASER:
                eraser.draw(event);
                break;
            case TOOL_IMPORT:
                drawBitmap.draw(event);
                break;
            default:
                break;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN && tool != TOOL_POINT && tool != TOOL_PAINT_BUCKET && tool != TOOL_IMPORT)
            listener.onDrawingStart();
        else if (event.getAction() == MotionEvent.ACTION_UP && tool != TOOL_POINT && tool != TOOL_PAINT_BUCKET && tool != TOOL_IMPORT)
            listener.onDrawingEnd();

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

        eraserPaint = new Paint();
        eraserPaint.setColor(backgroundColor);
        eraserPaint.setAntiAlias(true);
        eraserPaint.setStrokeWidth(40);
        eraserPaint.setStyle(Paint.Style.STROKE);
        eraserPaint.setStrokeJoin(Paint.Join.ROUND);
        eraserPaint.setStrokeCap(Paint.Cap.ROUND);

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

    public Paint getDrawPaint() {
        return drawPaint;
    }

    public Paint getEraserPaint() {
        return eraserPaint;
    }

    public void setDrawPaint(Paint drawPaint) {
        this.drawPaint = drawPaint;
    }

    public void setListener(DrawingInProgress listener) {
        this.listener = listener;
    }

    public void finalizeBitmap() {
        finalizeBitmap = true;
    }

    public Boolean isBitmapFinalized() {
        return finalizeBitmap;
    }

    public void setDrawBitmap(Bitmap bitmap) {
        this.drawBitmap = new DrawBitmap(this.canvas, bitmap);
        this.ownBitmap = this.drawBitmap.getOwnBitmap();
    }

    public int get_height() {
        return height;
    }

    public int get_width() {
        return width;
    }

}

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
    public static final int TOOL_CIRCLE = 6;
    public static final int TOOL_RECTANGLE = 7;
    public static final int TOOL_IMPORT = 8;
    public static final int TOOL_FILTERS = 9;

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
    DrawCircle drawCircle;
    DrawLine.Line line;
    DrawRectangle.Rectangle rectangle;
    DrawCircle.Circle circle;
    Eraser eraser;
    BucketFill bucketFill;
    BlackAndWhiteFilter blackAndWhiteFilter;
    VintageFilter vintageFilter;
    DeepFryFilter deepFryFilter;

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
        canvas.drawColor(0xFFFFFFFF);
        drawPoint = new DrawPoint(canvas, drawPaint);
        drawLine = new DrawLine(canvas, drawPaint);
        drawPath = new DrawPath(canvas, drawPaint);
        drawCircle = new DrawCircle(canvas, drawPaint);
        eraser = new Eraser(canvas, eraserPaint, backgroundColor);
        drawRectangle = new DrawRectangle(canvas, drawPaint);
        bucketFill = new BucketFill(bitmap, drawPaint);
        blackAndWhiteFilter = new BlackAndWhiteFilter(canvas, bitmap);
        vintageFilter = new VintageFilter(canvas, bitmap);
        deepFryFilter = new DeepFryFilter(canvas, bitmap);
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

        if(circle != null)
            canvas.drawCircle(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), drawPaint);


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
        if(tool == TOOL_POINT)
            drawPoint.drawPoint(event);
        else if(tool == TOOL_LINE)
            line = drawLine.draw(event);
        else if(tool == TOOL_PATH)
            drawPath.draw(event);
        else if(tool == TOOL_ERASER)
            eraser.draw(event);
        else if(tool == TOOL_CIRCLE)
            circle = drawCircle.draw(event);
        else if(tool == TOOL_RECTANGLE)
            rectangle = drawRectangle.draw(event);
        else if(tool == TOOL_PAINT_BUCKET) {
            bucketFill.draw(event);
        }
        else if(tool == TOOL_IMPORT)
            drawBitmap.draw(event);


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

    public void applyFilter(int which){
        switch (which){
            case 0:
                blackAndWhiteFilter.applyFilter();
                break;
            case 1:
                deepFryFilter.applyFilter();
                break;
            case 2:
                vintageFilter.applyFilter();
                break;
        }
    }

    public void setDrawPaint(Paint drawPaint) {
        this.drawPaint = drawPaint;
    }

    public void setListener(DrawingInProgress listener) {
        this.listener = listener;
    }
    public Bitmap getBitmap () {
        return bitmap;
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

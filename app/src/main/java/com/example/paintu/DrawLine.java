package com.example.paintu;

import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawLine {

    private Canvas canvas;
    private Paint paint;
    private Line line = new Line();

    public DrawLine(Canvas canvas, Paint paint) {
        this.canvas = canvas;
        this.paint = paint;
    }

    public Line draw(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            line.setStartX(x);
            line.setStartY(y);
            line.setEndX(-1);
            line.setEndY(-1);
            return line;
        }
        else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            line.setEndX(x);
            line.setEndY(y);
            return line;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            canvas.drawLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY(), paint);
            return null;
        }

        return null;
    }

    public class Line {
        private float startX, startY, endX, endY;

        public Line() {
        }

        public Line(float startX, float startY, float endX, float endY) {
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
        }

        public float getStartX() {
            return startX;
        }

        public void setStartX(float startX) {
            this.startX = startX;
        }

        public float getStartY() {
            return startY;
        }

        public void setStartY(float startY) {
            this.startY = startY;
        }

        public float getEndX() {
            return endX;
        }

        public void setEndX(float endX) {
            this.endX = endX;
        }

        public float getEndY() {
            return endY;
        }

        public void setEndY(float endY) {
            this.endY = endY;
        }
    }
}
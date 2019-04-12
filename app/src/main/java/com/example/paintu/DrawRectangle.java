package com.example.paintu;

import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawRectangle {
    private Canvas canvas;
    private Paint paint;
    private Rectangle rectangle;
    private float startX;
    private float startY;

    public DrawRectangle(Canvas canvas, Paint paint)
    {
        this.canvas = canvas;
        this.paint = paint;
        this.rectangle = new Rectangle();
    }

    public Rectangle draw(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        if(event.getAction() == MotionEvent.ACTION_DOWN)
        {
            startX = x;
            startY = y;
            return null;
        }

        else if(event.getAction() == MotionEvent.ACTION_MOVE)
        {
            if(startX > x)
            {
                rectangle.left = startX;
                rectangle.right = x;
            }
            else
            {
                rectangle.left = x;
                rectangle.right = startX;
            }

            if(startY > y)
            {
                rectangle.top = startY;
                rectangle.bottom = y;
            }
            else
            {
                rectangle.top = y;
                rectangle.bottom = startY;
            }
            return rectangle;
        }

        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            canvas.drawRect(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom(), paint);
            return null;
        }

        return null;
    }
    public class Rectangle {
        private float left, top, right, bottom;

        public Rectangle(float left, float bottom, float rigtht, float top){
            this.left = left;
            this.top = top;
            this.right = rigtht;
            this.bottom = bottom;
        }

        public Rectangle() {
        }
        public float getLeft(){
            return left;
        }
        public float getTop(){
            return top;
        }
        public float getRight(){
            return right;
        }
        public float getBottom(){
            return bottom;
        }


        public void setLeft(float Left){
            this.left = left;
        }
        public void setTop(float top){
            this.top = top;
        }
        public void setRight(float right){
            this.right = right;
        }
        public void setBottom(float bottom){
            this.bottom = bottom;
        }

    }
}


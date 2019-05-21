package com.example.paintu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.Queue;

public class BucketFill {

    private Paint paint;
    private Bitmap bitmap;
    private int replacementColor;

    private int x;
    private int y;

    public BucketFill(Bitmap bitmap, Paint paintColorObject) {
        this.bitmap = bitmap;
        this.paint = paintColorObject;
    }

    public boolean draw(MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int)event.getX();
                int y = (int)event.getY();
                int pixel = bitmap.getPixel(x,y);

                int redValue = Color.red(pixel);
                int greenValue = Color.green(pixel);
                int blueValue = Color.blue(pixel);
                int alphaValue = Color.alpha(pixel);

                int fillTargetPaint = Color.argb(alphaValue, redValue, greenValue, blueValue);
                Point pt = new Point(x, y);
                replacementColor = paint.getColor();
                if(replacementColor != fillTargetPaint)
                    floodFill(bitmap, pt, fillTargetPaint, replacementColor);
                return true;
            default:
                break;
        }
        return false;
    }

    public void floodFill(Bitmap  image, Point node, int targetColor,
                          int replacementColor) {
        int width = image.getWidth();
        int height = image.getHeight();
        int target = targetColor;
        int replacement = replacementColor;
        if (target != replacement) {
            Queue<Point> queue = new LinkedList<Point>();
            do {
                int x = node.x;
                int y = node.y;
                while (x > 0 && image.getPixel(x - 1, y) == target) {
                    x--;
                }
                boolean spanUp = false;
                boolean spanDown = false;
                while (x < width && image.getPixel(x, y) == target) {
                    image.setPixel(x, y, replacement);
                    if (!spanUp && y > 0 && image.getPixel(x, y - 1) == target) {
                        queue.add(new Point(x, y - 1));
                        spanUp = true;
                    } else if (spanUp && y > 0
                            && image.getPixel(x, y - 1) != target) {
                        spanUp = false;
                    }
                    if (!spanDown && y < height - 1
                            && image.getPixel(x, y + 1) == target) {
                        queue.add(new Point(x, y + 1));
                        spanDown = true;
                    } else if (spanDown && y < height - 1
                            && image.getPixel(x, y + 1) != target) {
                        spanDown = false;
                    }
                    x++;
                }
            } while ((node = queue.poll()) != null);
        }
    }

    private void floodFill2(Bitmap bmp, Point pt, int targetColor, int replacementColor)
    {
        Queue<Point> q = new LinkedList<Point>();
        q.add(pt);
        while (q.size() > 0) {
            Point n = q.poll();
            if (bmp.getPixel(n.x, n.y) != targetColor)
                continue;

            Point w = n, e = new Point(n.x + 1, n.y);

            while ((w.x > 0) && (bmp.getPixel(w.x, w.y) == targetColor)) {
                bmp.setPixel(w.x, w.y, replacementColor);
                if ((w.y > 0) && (bmp.getPixel(w.x, w.y - 1) == targetColor))
                    q.add(new Point(w.x, w.y - 1));
                if ((w.y < bmp.getHeight() - 1) && (bmp.getPixel(w.x, w.y + 1) == targetColor))
                    q.add(new Point(w.x, w.y + 1));

                w.x--;
            }

            while ((e.x < bmp.getWidth() - 1) && (bmp.getPixel(e.x, e.y) == targetColor)) {
                bmp.setPixel(e.x, e.y, replacementColor);
                if ((e.y > 0) && (bmp.getPixel(e.x, e.y - 1) == targetColor))
                    q.add(new Point(e.x, e.y - 1));
                if ((e.y < bmp.getHeight() - 1) && (bmp.getPixel(e.x, e.y + 1) == targetColor))
                    q.add(new Point(e.x, e.y + 1));

                e.x++;
            }
        }
    }
}

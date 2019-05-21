package com.example.paintu;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BucketFillTest{
    Canvas mockCanvas;
    Paint mockPaint;
    Bitmap mockBitmap;
    MotionEvent mockMotion;
    static BucketFill bucketFill;
    static DrawLine drawLine;


    @Before
    public void setUp() throws Exception {
        mockCanvas =  Mockito.mock(Canvas.class);
        mockBitmap = Mockito.mock(Bitmap.class);
        mockPaint = Mockito.mock(Paint.class);
        mockMotion = Mockito.mock(MotionEvent.class);
        bucketFill = new BucketFill (mockBitmap, mockPaint);
        drawLine = new DrawLine(mockCanvas, mockPaint);
    }


    @Test
    public void testActionDown(){
        when(mockMotion.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(mockMotion.getX()).thenReturn(30f);
        when(mockMotion.getY()).thenReturn(50f);
        bucketFill.draw(mockMotion);
        int x = (int)mockMotion.getX();
        int y = (int)mockMotion.getY();
        int pixel = mockBitmap.getPixel(x,y);

        int redValue = Color.red(pixel);
        int greenValue = Color.green(pixel);
        int blueValue = Color.blue(pixel);
        int alphaValue = Color.alpha(pixel);
        int pixelColor = Color.argb(alphaValue, redValue, greenValue, blueValue);

        assertEquals(pixelColor, mockPaint.getColor());
    }
}
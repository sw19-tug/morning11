package com.example.paintu;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
public class DrawShapeTest {
    Canvas MockCanvas;
    Paint MockPaint;
    MotionEvent MockMotion;

    @Before
    public void setUp() throws Exception{
        MockCanvas =  Mockito.mock(Canvas.class);
        MockPaint = Mockito.mock(Paint.class);
        MockMotion  = Mockito.mock(MotionEvent.class);

}
    @Test
    public void testMotion(){
        DrawShape testClass1 = new DrawShape(MockCanvas, MockPaint);

        boolean result = testClass1.drawShape(MockMotion);

        assertTrue(result);

    }
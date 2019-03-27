package com.example.paintu;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class DrawPointTest {
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
    public void testColor(){
        DrawPoint testClass1 = new DrawPoint(MockCanvas, MockPaint);

        boolean result = testClass1.drawPoint(MockMotion);

        assertTrue(result);

    }

    public void testDraw{

    }


}

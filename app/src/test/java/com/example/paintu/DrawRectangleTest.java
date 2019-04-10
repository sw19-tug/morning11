package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class DrawRectangleTest {


    Canvas MockCanvas;
    Paint MockPaint;
    MotionEvent MockMotionEvent;
    Rect MockRect;

    @Before
    public void setUp() throws Exception {
        MockCanvas = Mockito.mock(Canvas.class);
        MockPaint = Mockito.mock(Paint.class);
        MockMotionEvent = Mockito.mock(MotionEvent.class);
        MockRect = Mockito.mock(Rect.class);
    }


    @Test
    public void testColor() {
        DrawPoint testClass1 = new DrawRectangle(MockCanvas, MockPaint, MotionEvent);

        Rect compare = new Rect();
        float xy = 10f;
        float xy_move = 20f;
        when(MockMotionEvent.getAction()).thenReturn(MockMotionEvent.ACTION_DOWN);
        when(MockMotionEvent.getX()).thenReturn(xy);
        when(MockMotionEvent.getY()).thenReturn(xy);
        //not sure what
        Rect result = testClass1.drawRectangle(MockMotionEvent);
        assertEquals(compare, result);

        when(MockMotionEvent.getAction()).thenReturn(MockMotionEvent.ACTION_MOVE);
        when(MockMotionEvent.getX()).thenReturn(xy_move);
        when(MockMotionEvent.getY()).thenReturn(xy_move);

        //when assignint it to the Rect we need to cast the Positions
        Rect compare = new Rect((int)xy, (int)xy, (int)xy_move, (int)xy_move);
        result = testClass1.drawRectangle(MockMotionEvent);
        assertEquals(compare, result);

        when(MockMotionEvent.getAction()).thenReturn(MockMotionEvent.ACTION_MOVE);
        result = testClass1.drawRectangle(MockMotionEvent);
        assertEquals(null, result);

    }

    public void testDraw

    {

    }


}



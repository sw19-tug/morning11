package com.example.paintu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;//@RunWith(MockitoJUnitRunner.class)

public class DrawPathTest {
    private static int TEST_COLOR = 500;
    Paint paint = mock(Paint.class);
    Canvas canvas = mock(Canvas.class);
    MotionEvent mockEvent = mock(MotionEvent.class);
    ArgumentCaptor<Path> pathArg = ArgumentCaptor.forClass(Path.class);
    ArgumentCaptor<Paint> paintArg = ArgumentCaptor.forClass(Paint.class);
    DrawPath mockedDrawPath = mock(DrawPath.class);

    @Before
    public void setUp() throws Exception {
        Mockito.reset(paint);
        Mockito.reset(canvas);
        Mockito.reset(mockEvent);
        when(paint.getColor()).thenReturn(TEST_COLOR);
    }

    @Test
    public void testDraw() {
        DrawPath drawPathClass = new DrawPath(canvas, paint);        // TEST ACTION_DOWN
        float xy = 10.f;
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_DOWN);
        when(mockEvent.getX()).thenReturn(xy);
        when(mockEvent.getY()).thenReturn(xy);
        drawPathClass.draw(mockEvent);
        verify(canvas, never()).drawPath(any(Path.class), any(Paint.class));        // TEST ACTION_MOVE 1
        xy = 11.f;
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(mockEvent.getX()).thenReturn(xy);
        when(mockEvent.getY()).thenReturn(xy);
        drawPathClass.draw(mockEvent);        // TEST ACTION_MOVE 2
        xy = 12.f;
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_MOVE);
        when(mockEvent.getX()).thenReturn(xy);
        when(mockEvent.getY()).thenReturn(xy);
        drawPathClass.draw(mockEvent);        // TEST ACTION_UP
        xy = 13.f;
        when(mockEvent.getAction()).thenReturn(MotionEvent.ACTION_UP);
        when(mockEvent.getX()).thenReturn(xy);
        when(mockEvent.getY()).thenReturn(xy);
        drawPathClass.draw(mockEvent);        // this should fire 2 times
        verify(canvas, times(2)).drawPath(pathArg.capture(), paintArg.capture());        // get all catched arguments
        List<Path> capturedPaths = pathArg.getAllValues();
        List<Paint> capturedPaints = paintArg.getAllValues();        // test if color is the same
        //assertEquals("John", capturedPaths.get(0)...);
        assertEquals(TEST_COLOR, capturedPaints.get(0).getColor());
        assertEquals(TEST_COLOR, capturedPaints.get(1).getColor());
        //assertEquals(TEST_COLOR, capturedPaints.get(2).getColor());
    }
}
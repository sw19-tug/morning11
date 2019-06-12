package com.example.paintu;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class VintageFilterTest {
    Canvas mockCanvas;
    Bitmap mockBitmap;
    VintageFilter vintageFilter;
    Paint mockPaint;

    @Before
    public void setUp() throws Exception{
        mockCanvas =  Mockito.mock(Canvas.class);
        mockPaint = Mockito.mock(Paint.class);
        mockBitmap = Mockito.mock(Bitmap.class);
        vintageFilter = new VintageFilter(mockCanvas, mockBitmap);

    }


    @Test
    public void testVintageFilter() {
        vintageFilter.applyFilter();
        verify(mockCanvas).drawBitmap(eq(mockBitmap), eq(0f), eq(0f), any(Paint.class));

    }
}

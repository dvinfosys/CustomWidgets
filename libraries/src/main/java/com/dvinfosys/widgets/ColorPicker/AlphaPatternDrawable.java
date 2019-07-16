package com.dvinfosys.widgets.ColorPicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

class AlphaPatternDrawable extends Drawable {

    private int rectangleSize = 10;

    private Paint paint = new Paint();
    private Paint paintWhite = new Paint();
    private Paint paintGray = new Paint();

    private int numRectanglesHorizontal;
    private int numRectanglesVertical;

    private Bitmap bitmap;

    AlphaPatternDrawable(int rectangleSize) {
        this.rectangleSize = rectangleSize;
        paintWhite.setColor(0xFFFFFFFF);
        paintGray.setColor(0xFFCBCBCB);
    }

    @Override public void draw(Canvas canvas) {
        if (bitmap != null && !bitmap.isRecycled()) {
            canvas.drawBitmap(bitmap, null, getBounds(), paint);
        }
    }

    @Override public int getOpacity() {
        return 0;
    }

    @Override public void setAlpha(int alpha) {
        throw new UnsupportedOperationException("Alpha is not supported by this drawable.");
    }

    @Override public void setColorFilter(ColorFilter cf) {
        throw new UnsupportedOperationException("ColorFilter is not supported by this drawable.");
    }

    @Override protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        int height = bounds.height();
        int width = bounds.width();
        numRectanglesHorizontal = (int) Math.ceil((width / rectangleSize));
        numRectanglesVertical = (int) Math.ceil(height / rectangleSize);
        generatePatternBitmap();
    }

    private void generatePatternBitmap() {
        if (getBounds().width() <= 0 || getBounds().height() <= 0) {
            return;
        }

        bitmap = Bitmap.createBitmap(getBounds().width(), getBounds().height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect r = new Rect();
        boolean verticalStartWhite = true;
        for (int i = 0; i <= numRectanglesVertical; i++) {
            boolean isWhite = verticalStartWhite;
            for (int j = 0; j <= numRectanglesHorizontal; j++) {
                r.top = i * rectangleSize;
                r.left = j * rectangleSize;
                r.bottom = r.top + rectangleSize;
                r.right = r.left + rectangleSize;
                canvas.drawRect(r, isWhite ? paintWhite : paintGray);
                isWhite = !isWhite;
            }
            verticalStartWhite = !verticalStartWhite;
        }
    }
}

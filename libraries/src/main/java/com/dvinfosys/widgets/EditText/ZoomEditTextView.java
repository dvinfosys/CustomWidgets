package com.dvinfosys.widgets.EditText;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dvinfosys.widgets.R;

import java.util.ArrayList;
import java.util.List;

public class ZoomEditTextView extends EditText {

    private static final int INVALID_POINTER_ID = -1;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    private int mActivePointerId = INVALID_POINTER_ID;

    private float mPosX;
    private float mPosY;
    private float mLastTouchX;
    private float mLastTouchY;

    private Bitmap mBitmap = null;
    private Paint mPaint;
    private Path mPath;
    private Paint mBitmapPaint;
    private Paint mCirclePaint;
    private Path mCirclePath;
    private Paint mDrawingPaint;
    private List<Path> mPaths = new ArrayList<Path>();
    private List<Paint> mPaints = new ArrayList<Paint>();
    private float mBrushSize = 12.0f;
    private Drawable mDrawable;
    private Paint mRectPaint;
    private Context mContext;

    public ZoomEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.mContext = context;
        init();
    }

    public ZoomEditTextView(Context context) {
        super(context, null, 0);
        this.mContext = context;
        init();

        setFocusableInTouchMode(true);
        requestFocus();

        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent me) {
                InputMethodManager imm = (InputMethodManager) mContext
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                return true;
            }

        });
    }

    private void init() {
        mScaleDetector = new ScaleGestureDetector(mContext, new ScaleListener());

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        mCirclePaint = new Paint();
        mCirclePath = new Path();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.CYAN);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeJoin(Paint.Join.MITER);
        mCirclePaint.setStrokeWidth(4f);

        mDrawingPaint = new Paint();

        mDrawingPaint.setAntiAlias(true);
        mDrawingPaint.setDither(true);
        mDrawingPaint.setColor(Color.GREEN);
        mDrawingPaint.setStyle(Paint.Style.STROKE);
        mDrawingPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawingPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawingPaint.setStrokeWidth(mBrushSize);

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setColor(Color.WHITE);

        mDrawable = mContext.getResources().getDrawable(R.drawable.vp_loading);
        mDrawable.setBounds(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mPosX, mPosY);
        canvas.scale(mScaleFactor, mScaleFactor);
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);

        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                if (!mScaleDetector.isInProgress()) {
                    final float x = event.getX();
                    final float y = event.getY();

                    mActivePointerId = event.getPointerId(0);

                    mLastTouchX = x;
                    mLastTouchY = y;

                    mActivePointerId = event.getPointerId(0);
                    break;
                }
            }

            case MotionEvent.ACTION_MOVE: {
                if (!mScaleDetector.isInProgress()) {
                    final int pointerIndex = event
                            .findPointerIndex(mActivePointerId);
                    final float x = event.getX(pointerIndex);
                    final float y = event.getY(pointerIndex);

                    float dx = x - mLastTouchX;
                    float dy = y - mLastTouchY;

                    mPosX += dx;
                    mPosY += dy;
                    mLastTouchX = x;
                    mLastTouchY = y;

                    invalidate();
                }
                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;

            }

            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = event.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {

                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;

                    if (event.getPointerCount() >= 2) {
                        mLastTouchX = event.getX(newPointerIndex);
                        mLastTouchY = event.getY(newPointerIndex);
                    }
                    mActivePointerId = event.getPointerId(newPointerIndex);

                } else {
                    final int tempPointerIndex = event
                            .findPointerIndex(mActivePointerId);
                    mLastTouchX = event.getX(tempPointerIndex);
                    mLastTouchY = event.getY(tempPointerIndex);
                }
                break;
            }
        }
        return true;
    }

    private class ScaleListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }
}

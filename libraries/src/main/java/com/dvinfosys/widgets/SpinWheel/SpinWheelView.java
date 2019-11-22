package com.dvinfosys.widgets.SpinWheel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dvinfosys.widgets.R;

import java.util.List;
import java.util.Random;

public class SpinWheelView extends RelativeLayout implements PielView.PieRotateListener{

    private int mBackgroundColor;
    private int mTextColor;
    private int mTopTextSize;
    private int mSecondaryTextSize;
    private int mBorderColor;
    private int mTopTextPadding;
    private int mEdgeWidth;
    private Drawable mCenterImage;
    private Drawable mCursorImage;

    private PielView pielView;
    private ImageView ivCursorView;
    private SpinWheelItemSelectedListener mSpinWheelItemSelectedListener;

    @Override
    public void rotateDone(int index) {
        if (mSpinWheelItemSelectedListener != null) {
            mSpinWheelItemSelectedListener.SpinWheelItemSelected(index);
        }
    }

    public interface SpinWheelItemSelectedListener {
        void SpinWheelItemSelected(int index);
    }

    public void setSpinWheelItemSelectedListener(SpinWheelItemSelectedListener listener) {
        this.mSpinWheelItemSelectedListener = listener;
    }
    public SpinWheelView(Context context) {
        super(context);
        init(context, null);
    }

    public SpinWheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context ctx, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.SpinWheelView);
            mBackgroundColor = typedArray.getColor(R.styleable.SpinWheelView_swvBackgroundColor, 0xffcc0000);
            mTopTextSize = typedArray.getDimensionPixelSize(R.styleable.SpinWheelView_swvTopTextSize, (int) SpinWheelUtils.convertDpToPixel(10f, getContext()));
            mSecondaryTextSize = typedArray.getDimensionPixelSize(R.styleable.SpinWheelView_swvSecondaryTextSize, (int) SpinWheelUtils.convertDpToPixel(20f, getContext()));
            mTextColor = typedArray.getColor(R.styleable.SpinWheelView_swvTopTextColor, 0);
            mTopTextPadding = typedArray.getDimensionPixelSize(R.styleable.SpinWheelView_swvTopTextPadding, (int) SpinWheelUtils.convertDpToPixel(10f, getContext())) + (int) SpinWheelUtils.convertDpToPixel(10f, getContext());
            mCursorImage = typedArray.getDrawable(R.styleable.SpinWheelView_swvCursor);
            mCenterImage = typedArray.getDrawable(R.styleable.SpinWheelView_swvCenterImage);
            mEdgeWidth = typedArray.getInt(R.styleable.SpinWheelView_swvEdgeWidth, 10);
            mBorderColor = typedArray.getColor(R.styleable.SpinWheelView_swvEdgeColor, 0);
            typedArray.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.spin_wheel_layout, this, false);

        pielView = frameLayout.findViewById(R.id.pieView);
        ivCursorView = frameLayout.findViewById(R.id.cursorView);

        pielView.setPieRotateListener(this);
        pielView.setPieBackgroundColor(mBackgroundColor);
        pielView.setTopTextPadding(mTopTextPadding);
        pielView.setTopTextSize(mTopTextSize);
        pielView.setSecondaryTextSizeSize(mSecondaryTextSize);
        pielView.setPieCenterImage(mCenterImage);
        pielView.setBorderColor(mBorderColor);
        pielView.setBorderWidth(mEdgeWidth);


        if (mTextColor != 0)
            pielView.setPieTextColor(mTextColor);

        ivCursorView.setImageDrawable(mCursorImage);

        addView(frameLayout);
    }
    public boolean isTouchEnabled() {
        return pielView.isTouchEnabled();
    }
    public void setTouchEnabled(boolean touchEnabled) {
        pielView.setTouchEnabled(touchEnabled);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //This is to control that the touch events triggered are only going to the PieView
        for (int i = 0; i < getChildCount(); i++) {
            if (isPielView(getChildAt(i))) {
                return super.dispatchTouchEvent(ev);
            }
        }
        return false;
    }

    private boolean isPielView(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < getChildCount(); i++) {
                if (isPielView(((ViewGroup) view).getChildAt(i))) {
                    return true;
                }
            }
        }
        return view instanceof PielView;
    }

    public void setSpinWheelViewBackgrouldColor(int color) {
        pielView.setPieBackgroundColor(color);
    }

    public void setSpinWheelViewCursorImage(int drawable) {
        ivCursorView.setBackgroundResource(drawable);
    }

    public void setSpinWheelViewCenterImage(Drawable drawable) {
        pielView.setPieCenterImage(drawable);
    }

    public void setBorderColor(int color) {
        pielView.setBorderColor(color);
    }

    public void setSpinWheelViewTextColor(int color) {
        pielView.setPieTextColor(color);
    }

    public void setData(List<SpinWheeltem> data) {
        pielView.setData(data);
    }

    public void setRound(int numberOfRound) {
        pielView.setRound(numberOfRound);
    }

    public void setPredeterminedNumber(int fixedNumber) {
        pielView.setPredeterminedNumber(fixedNumber);
    }

    public void startSpinWheelViewWithTargetIndex(int index) {
        pielView.rotateTo(index);
    }

    public void startSpinWheelWithRandomTarget() {
        Random r = new Random();
        pielView.rotateTo(r.nextInt(pielView.getLuckyItemListSize() - 1));
    }
}

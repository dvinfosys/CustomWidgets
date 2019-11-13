package com.dvinfosys.widgets.AlertDialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvinfosys.widgets.R;

import java.util.Objects;

public class DAlertDialog extends AlertDialog implements View.OnClickListener {

    private final AnimationSet mModalInAnim,mModalOutAnim,mErrorXInAnim;//,mSuccessLayoutAnimSet;
    private final Animation mOverlayOutAnim, mImageAnim;//,mSuccessBowAnim;

    private TextView mTitleTextView,mContentTextView;
    private ImageView mErrorX,mSuccessTick,mCustomImage;
    private Drawable mCustomImgDrawable;
    private Button mConfirmButton,mCancelButton;
    private Drawable mColor,mCancelColor;
    private View mDialogView;//,mSuccessLeftMask,mSuccessRightMask;
    private View mCustomView;
    private FrameLayout mCustomViewContainer;

    private String mTitleText,mContentText,mCancelText,mConfirmText;

    private boolean mShowCancel,mShowContent,mShowTitleText,mCloseFromCancel;
    private int contentTextSize = 0;

    private FrameLayout mErrorFrame,mSuccessFrame,mProgressFrame,mWarningFrame;
    //private SuccessTickView mSuccessTick;

    private final ProgressHelper mProgressHelper;
    private DAlertClickListener mCancelClickListener;
    private DAlertClickListener mConfirmClickListener;

    private int mAlertType;
    public static final int NORMAL_TYPE = 0;
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int PROGRESS_TYPE = 5;

    public static boolean DARK_STYLE = false;

    public interface DAlertClickListener {
        void onClick(DAlertDialog dAlertDialog);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public DAlertDialog(Context context) {
        this(context, NORMAL_TYPE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);

        mDialogView = Objects.requireNonNull(getWindow()).getDecorView().findViewById(android.R.id.content);
        mTitleTextView = findViewById(R.id.title_text);
        mContentTextView = findViewById(R.id.content_text);
        mErrorFrame = findViewById(R.id.error_frame);
        mErrorX = mErrorFrame.findViewById(R.id.error_x);
        mSuccessFrame = findViewById(R.id.success_frame);
        mProgressFrame = findViewById(R.id.progress_dialog);
        mSuccessTick = mSuccessFrame.findViewById(R.id.success_x);
        //mSuccessLeftMask = mSuccessFrame.findViewById(R.id.mask_left);
        //mSuccessRightMask = mSuccessFrame.findViewById(R.id.mask_right);
        mCustomImage = findViewById(R.id.custom_image);
        mWarningFrame = findViewById(R.id.warning_frame);
        mCustomViewContainer = findViewById(R.id.custom_view_container);
        mProgressHelper.setProgressWheel(findViewById(R.id.progressWheel));

        mConfirmButton = findViewById(R.id.custom_confirm_button);
        mCancelButton = findViewById(R.id.cancel_button);
        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);

        setCustomView(mCustomView);
        setTitleText(mTitleText);
        setContentText(mContentText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        setConfirmButtonColor(mColor);
        setCancelButtonColor(mCancelColor);
        changeAlertType(mAlertType, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public DAlertDialog(Context context, int alertType) {
        super(context, DARK_STYLE ?  R.style.alert_dialog_dark : R.style.alert_dialog_light);

        setCancelable(true);
        setCanceledOnTouchOutside(false);
        mProgressHelper = new ProgressHelper(context);
        mAlertType = alertType;
        mImageAnim = AnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        //mSuccessBowAnim = AnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
        //mSuccessLayoutAnimSet = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) AnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        Objects.requireNonNull(mModalOutAnim).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(() -> {
                    if (mCloseFromCancel) {
                        DAlertDialog.super.cancel();
                    } else {
                        DAlertDialog.super.dismiss();
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = Objects.requireNonNull(getWindow()).getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);
    }

    private void restore () {
        mCustomImage.setVisibility(View.GONE);
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.GONE);
        mConfirmButton.setVisibility(View.VISIBLE);

        mConfirmButton.setBackgroundResource(R.drawable.button_background);
        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        mSuccessTick.clearAnimation();
        //mSuccessLeftMask.clearAnimation();
        //mSuccessRightMask.clearAnimation();
    }

    private void playAnimation () {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mImageAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
            mSuccessTick.startAnimation(mImageAnim);
            mSuccessFrame.startAnimation(mImageAnim);
        }
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        if (mDialogView != null) {
            if (!fromCreate) {
                restore();
            }
            switch (mAlertType) {
                case ERROR_TYPE:
                    mErrorFrame.setVisibility(View.VISIBLE);
                    setConfirmButtonColor(mColor);
                    break;
                case SUCCESS_TYPE:
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    //mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
                    //mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
                    setConfirmButtonColor(mColor);
                    break;
                case WARNING_TYPE:
                    mWarningFrame.setVisibility(View.VISIBLE);
                    setConfirmButtonColor(mColor);
                    break;
                case CUSTOM_IMAGE_TYPE:
                    setCustomImage(mCustomImgDrawable);
                    setConfirmButtonColor(mColor);
                    break;
                case PROGRESS_TYPE:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmButton.setVisibility(View.GONE);
                    setConfirmButtonColor(mColor);
                    break;
            }
            if (!fromCreate) {
                playAnimation();
            }
        }
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }

    public DAlertDialog setTitleText (String text) {

        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            showTitleText();
            //mTitleTextView.setText(mTitleText);
            mTitleTextView.setText(Html.fromHtml(mTitleText));
        }
        return this;
    }

    private void showTitleText() {
        mShowTitleText = true;
        if (mTitleTextView != null) {
            mTitleTextView.setVisibility(View.VISIBLE);
        }
    }

    public DAlertDialog setCustomImage (int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    private DAlertDialog setCustomImage(Drawable drawable) {
        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public DAlertDialog setContentText (String text) {
 /*
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText();
            //mContentTextView.setText(mContentText);
            mContentTextView.setText(Html.fromHtml(mContentText));
        }
        return this;
*/
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText();
            if (contentTextSize != 0) {
                mContentTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, spToPx(contentTextSize, getContext()));
            }
            mContentTextView.setText(Html.fromHtml(mContentText));
            mCustomViewContainer.setVisibility(View.GONE);
        }
        return this;
    }

    public DAlertDialog showCancelButton (boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    private void showContentText() {
        mShowContent = true;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(View.VISIBLE);
        }
    }

    public DAlertDialog setCancelText (String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public DAlertDialog setConfirmText (String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }

    public DAlertDialog setCancelClickListener (DAlertClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public DAlertDialog setConfirmClickListener (DAlertClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
        playAnimation();
    }

    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    private DAlertDialog setConfirmButtonColor(Drawable background) {
        mColor = background;
        if (mConfirmButton != null && mColor !=null) {
            mConfirmButton.setBackground(mColor);
        }
        return this;
    }

    private DAlertDialog setCancelButtonColor(Drawable background) {
        mCancelColor = background;
        if (mCancelButton != null && mCancelColor !=null) {
            mCancelButton.setBackground(mCancelColor);
        }
        return this;
    }

    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    public void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public int getAlertType() {
        return mAlertType;
    }

    public String getTitleText () {
        return mTitleText;
    }

    public boolean isShowTitleText () {
        return mShowTitleText;
    }

    public String getContentText () {
        return mContentText;
    }

    public boolean isShowCancelButton () {
        return mShowCancel;
    }

    public boolean isShowContentText () {
        return mShowContent;
    }

    public String getCancelText () {
        return mCancelText;
    }

    public String getConfirmText () {
        return mConfirmText;
    }

    public DAlertDialog confirmButtonColor (int color) {
        return setConfirmButtonColor(getContext().getResources().getDrawable(color));
    }

    public DAlertDialog cancelButtonColor (int color) {
        return setCancelButtonColor(getContext().getResources().getDrawable(color));
    }

    public DAlertDialog setContentTextSize(int value) {
        this.contentTextSize = value;
        return this;
    }

    public int getContentTextSize() {
        return contentTextSize;
    }

    public DAlertDialog setCustomView(View view) {
        mCustomView = view;
        if (mCustomView != null && mCustomViewContainer != null) {
            mCustomViewContainer.addView(view);
            mCustomViewContainer.setVisibility(View.VISIBLE);
            //mContentTextView.setVisibility(View.GONE);
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_button) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(DAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.custom_confirm_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(DAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        }
    }

    public ProgressHelper getProgressHelper () {
        return mProgressHelper;
    }
}

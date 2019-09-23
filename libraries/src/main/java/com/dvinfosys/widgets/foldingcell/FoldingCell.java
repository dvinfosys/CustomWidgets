package com.dvinfosys.widgets.foldingcell;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.dvinfosys.widgets.R;
import com.dvinfosys.widgets.foldingcell.animations.AnimationEndListener;
import com.dvinfosys.widgets.foldingcell.animations.FoldAnimation;
import com.dvinfosys.widgets.foldingcell.animations.HeightAnimation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FoldingCell extends RelativeLayout {

    private boolean mUnfolded;
    private boolean mAnimationInProgress;

    private final int DEF_ANIMATION_DURATION = 1000;
    private final int DEF_BACK_SIDE_COLOR = Color.GRAY;
    private final int DEF_ADDITIONAL_FLIPS = 0;
    private final int DEF_CAMERA_HEIGHT = 30;

    private int mAnimationDuration = DEF_ANIMATION_DURATION;
    private int mBackSideColor = DEF_BACK_SIDE_COLOR;
    private int mAdditionalFlipsCount = DEF_ADDITIONAL_FLIPS;
    private int mCameraHeight = DEF_CAMERA_HEIGHT;

    public FoldingCell(Context context) {
        this(context, null);
    }

    public FoldingCell(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FoldingCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.FoldingCell);
        if (styledAttrs!=null){
            final int count = styledAttrs.getIndexCount();
            for (int i = 0; i < count; ++i) {
                int attr = styledAttrs.getIndex(i);
                if (attr == R.styleable.FoldingCell_animationDuration) {
                    this.mAnimationDuration = styledAttrs.getInt(R.styleable.FoldingCell_animationDuration, DEF_ANIMATION_DURATION);
                } else if (attr == R.styleable.FoldingCell_backSideColor) {
                    this.mBackSideColor = styledAttrs.getColor(R.styleable.FoldingCell_backSideColor, DEF_BACK_SIDE_COLOR);
                } else if (attr == R.styleable.FoldingCell_additionalFlipsCount) {
                    this.mAdditionalFlipsCount = styledAttrs.getInt(R.styleable.FoldingCell_additionalFlipsCount, DEF_ADDITIONAL_FLIPS);
                } else if (attr == R.styleable.FoldingCell_cameraHeight) {
                    this.mCameraHeight = styledAttrs.getInt(R.styleable.FoldingCell_cameraHeight, DEF_CAMERA_HEIGHT);
                }
            }
            styledAttrs.recycle();
        }

        this.setClipChildren(false);
        this.setClipToPadding(false);
    }

    public void initialize(int animationDuration, int backSideColor, int additionalFlipsCount) {
        this.mAnimationDuration = animationDuration;
        this.mBackSideColor = backSideColor;
        this.mAdditionalFlipsCount = additionalFlipsCount;
    }

    public void initialize(int cameraHeight, int animationDuration, int backSideColor, int additionalFlipsCount) {
        this.mAnimationDuration = animationDuration;
        this.mBackSideColor = backSideColor;
        this.mAdditionalFlipsCount = additionalFlipsCount;
        this.mCameraHeight = cameraHeight;
    }

    public boolean isUnfolded() {
        return mUnfolded;
    }
    public void unfold(boolean skipAnimation) {
        if (mUnfolded || mAnimationInProgress) return;

        // get main content parts
        final View contentView = getChildAt(0);
        if (contentView == null) return;
        final View titleView = getChildAt(1);
        if (titleView == null) return;

        // hide title and content views
        titleView.setVisibility(GONE);
        contentView.setVisibility(GONE);

        // Measure views and take a bitmaps to replace real views with images
        Bitmap bitmapFromTitleView = measureViewAndGetBitmap(titleView, this.getMeasuredWidth());
        Bitmap bitmapFromContentView = measureViewAndGetBitmap(contentView, this.getMeasuredWidth());

        if (skipAnimation) {
            contentView.setVisibility(VISIBLE);
            FoldingCell.this.mUnfolded = true;
            FoldingCell.this.mAnimationInProgress = false;
            this.getLayoutParams().height = contentView.getHeight();
        } else {
            ViewCompat.setHasTransientState(this, true);
            // create layout container for animation elements
            final LinearLayout foldingLayout = createAndPrepareFoldingContainer();
            this.addView(foldingLayout);
            // calculate heights of animation parts
            ArrayList<Integer> heights = calculateHeightsForAnimationParts(titleView.getHeight(), contentView.getHeight(), mAdditionalFlipsCount);
            // create list with animation parts for animation
            ArrayList<FoldingCellView> foldingCellElements = prepareViewsForAnimation(heights, bitmapFromTitleView, bitmapFromContentView);
            // start unfold animation with end listener
            int childCount = foldingCellElements.size();
            int part90degreeAnimationDuration = mAnimationDuration / (childCount * 2);
            startUnfoldAnimation(foldingCellElements, foldingLayout, part90degreeAnimationDuration, new AnimationEndListener() {
                public void onAnimationEnd(Animation animation) {
                    contentView.setVisibility(VISIBLE);
                    foldingLayout.setVisibility(GONE);
                    FoldingCell.this.removeView(foldingLayout);
                    FoldingCell.this.mUnfolded = true;
                    FoldingCell.this.mAnimationInProgress = false;
                    ViewCompat.setHasTransientState(FoldingCell.this, true);
                }
            });

            startExpandHeightAnimation(heights, part90degreeAnimationDuration * 2);
            this.mAnimationInProgress = true;
        }
    }

    public void fold(boolean skipAnimation) {
        if (!mUnfolded || mAnimationInProgress) return;

        // get basic views
        final View contentView = getChildAt(0);
        if (contentView == null) return;
        final View titleView = getChildAt(1);
        if (titleView == null) return;

        // hide title and content views
        titleView.setVisibility(GONE);
        contentView.setVisibility(GONE);

        // make bitmaps from title and content views
        Bitmap bitmapFromTitleView = measureViewAndGetBitmap(titleView, this.getMeasuredWidth());
        Bitmap bitmapFromContentView = measureViewAndGetBitmap(contentView, this.getMeasuredWidth());

        if (skipAnimation) {
            contentView.setVisibility(GONE);
            titleView.setVisibility(VISIBLE);
            FoldingCell.this.mAnimationInProgress = false;
            FoldingCell.this.mUnfolded = false;
            this.getLayoutParams().height = titleView.getHeight();
        } else {
            ViewCompat.setHasTransientState(this, true);
            // create empty layout for folding animation
            final LinearLayout foldingLayout = createAndPrepareFoldingContainer();
            // add that layout to structure
            this.addView(foldingLayout);

            // calculate heights of animation parts
            ArrayList<Integer> heights = calculateHeightsForAnimationParts(titleView.getHeight(), contentView.getHeight(), mAdditionalFlipsCount);
            // create list with animation parts for animation
            ArrayList<FoldingCellView> foldingCellElements = prepareViewsForAnimation(heights, bitmapFromTitleView, bitmapFromContentView);
            int childCount = foldingCellElements.size();
            int part90degreeAnimationDuration = mAnimationDuration / (childCount * 2);
            // start fold animation with end listener
            startFoldAnimation(foldingCellElements, foldingLayout, part90degreeAnimationDuration, new AnimationEndListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    contentView.setVisibility(GONE);
                    titleView.setVisibility(VISIBLE);
                    foldingLayout.setVisibility(GONE);
                    FoldingCell.this.removeView(foldingLayout);
                    FoldingCell.this.mAnimationInProgress = false;
                    FoldingCell.this.mUnfolded = false;
                    ViewCompat.setHasTransientState(FoldingCell.this, true);
                }
            });
            startCollapseHeightAnimation(heights, part90degreeAnimationDuration * 2);
            this.mAnimationInProgress = true;
        }
    }

    public void toggle(boolean skipAnimation) {
        if (this.mUnfolded) {
            this.fold(skipAnimation);
        } else {
            this.unfold(skipAnimation);
            this.requestLayout();
        }
    }

    protected ArrayList<FoldingCellView> prepareViewsForAnimation(ArrayList<Integer> viewHeights, Bitmap titleViewBitmap, Bitmap contentViewBitmap) {
        if (viewHeights == null || viewHeights.isEmpty())
            throw new IllegalStateException("ViewHeights array must be not null and not empty");

        ArrayList<FoldingCellView> partsList = new ArrayList<>();

        int partWidth = titleViewBitmap.getWidth();
        int yOffset = 0;
        for (int i = 0; i < viewHeights.size(); i++) {
            int partHeight = viewHeights.get(i);
            Bitmap partBitmap = Bitmap.createBitmap(partWidth, partHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(partBitmap);
            Rect srcRect = new Rect(0, yOffset, partWidth, yOffset + partHeight);
            Rect destRect = new Rect(0, 0, partWidth, partHeight);
            canvas.drawBitmap(contentViewBitmap, srcRect, destRect, null);
            ImageView backView = createImageViewFromBitmap(partBitmap);
            ImageView frontView = null;
            if (i < viewHeights.size() - 1) {
                frontView = (i == 0) ? createImageViewFromBitmap(titleViewBitmap) : createBackSideView(viewHeights.get(i + 1));
            }
            partsList.add(new FoldingCellView(frontView, backView, getContext()));
            yOffset = yOffset + partHeight;
        }

        return partsList;
    }

    protected ArrayList<Integer> calculateHeightsForAnimationParts(int titleViewHeight, int contentViewHeight, int additionalFlipsCount) {
        ArrayList<Integer> partHeights = new ArrayList<>();
        int additionalPartsTotalHeight = contentViewHeight - titleViewHeight * 2;
        if (additionalPartsTotalHeight < 0)
            throw new IllegalStateException("Content View height is too small");
        // add two main parts - guarantee first flip
        partHeights.add(titleViewHeight);
        partHeights.add(titleViewHeight);

        // if no space left - return
        if (additionalPartsTotalHeight == 0)
            return partHeights;

        // if some space remained - use two different logic
        if (additionalFlipsCount != 0) {
            // 1 - additional parts count is specified and it is not 0 - divide remained space
            int additionalPartHeight = additionalPartsTotalHeight / additionalFlipsCount;
            int remainingHeight = additionalPartsTotalHeight % additionalFlipsCount;

            if (additionalPartHeight + remainingHeight > titleViewHeight)
                throw new IllegalStateException("Additional flips count is too small");
            for (int i = 0; i < additionalFlipsCount; i++)
                partHeights.add(additionalPartHeight + (i == 0 ? remainingHeight : 0));
        } else {
            // 2 - additional parts count isn't specified or 0 - divide remained space to parts with title view size
            int partsCount = additionalPartsTotalHeight / titleViewHeight;
            int restPartHeight = additionalPartsTotalHeight % titleViewHeight;
            for (int i = 0; i < partsCount; i++)
                partHeights.add(titleViewHeight);
            if (restPartHeight > 0)
                partHeights.add(restPartHeight);
        }

        return partHeights;
    }

    protected ImageView createBackSideView(int height) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundColor(mBackSideColor);
        imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        return imageView;
    }

    protected ImageView createImageViewFromBitmap(Bitmap bitmap) {
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(bitmap);
        imageView.setLayoutParams(new LayoutParams(bitmap.getWidth(), bitmap.getHeight()));
        return imageView;
    }

    protected Bitmap measureViewAndGetBitmap(View view, int parentWidth) {
        int specW = View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY);
        int specH = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(specW, specH);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        return b;
    }
    protected LinearLayout createAndPrepareFoldingContainer() {
        LinearLayout foldingContainer = new LinearLayout(getContext());
        foldingContainer.setClipToPadding(false);
        foldingContainer.setClipChildren(false);
        foldingContainer.setOrientation(LinearLayout.VERTICAL);
        foldingContainer.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        return foldingContainer;
    }

    protected void startExpandHeightAnimation(ArrayList<Integer> viewHeights, int partAnimationDuration) {
        if (viewHeights == null || viewHeights.isEmpty())
            throw new IllegalArgumentException("ViewHeights array must have at least 2 elements");

        ArrayList<Animation> heightAnimations = new ArrayList<>();
        int fromHeight = viewHeights.get(0);
        int delay = 0;
        int animationDuration = partAnimationDuration - delay;
        for (int i = 1; i < viewHeights.size(); i++) {
            int toHeight = fromHeight + viewHeights.get(i);
            HeightAnimation heightAnimation = new HeightAnimation(this, fromHeight, toHeight, animationDuration)
                    .withInterpolator(new DecelerateInterpolator());
            heightAnimation.setStartOffset(delay);
            heightAnimations.add(heightAnimation);
            fromHeight = toHeight;
        }
        createAnimationChain(heightAnimations, this);
        this.startAnimation(heightAnimations.get(0));
    }

    protected void startCollapseHeightAnimation(ArrayList<Integer> viewHeights, int partAnimationDuration) {
        if (viewHeights == null || viewHeights.isEmpty())
            throw new IllegalArgumentException("ViewHeights array must have at least 2 elements");

        ArrayList<Animation> heightAnimations = new ArrayList<>();
        int fromHeight = viewHeights.get(0);
        for (int i = 1; i < viewHeights.size(); i++) {
            int toHeight = fromHeight + viewHeights.get(i);
            heightAnimations.add(new HeightAnimation(this, toHeight, fromHeight, partAnimationDuration)
                    .withInterpolator(new DecelerateInterpolator()));
            fromHeight = toHeight;
        }

        Collections.reverse(heightAnimations);
        createAnimationChain(heightAnimations, this);
        this.startAnimation(heightAnimations.get(0));
    }

    protected void createAnimationChain(final List<Animation> animationList, final View animationObject) {
        for (int i = 0; i < animationList.size(); i++) {
            Animation animation = animationList.get(i);
            if (i + 1 < animationList.size()) {
                final int finalI = i;
                animation.setAnimationListener(new AnimationEndListener() {
                    public void onAnimationEnd(Animation animation) {
                        animationObject.startAnimation(animationList.get(finalI + 1));
                    }
                });
            }
        }
    }

    protected void startFoldAnimation(ArrayList<FoldingCellView> foldingCellElements, ViewGroup foldingLayout,
                                      int part90degreeAnimationDuration, AnimationEndListener animationEndListener) {
        for (FoldingCellView foldingCellElement : foldingCellElements)
            foldingLayout.addView(foldingCellElement);

        Collections.reverse(foldingCellElements);

        int nextDelay = 0;
        for (int i = 0; i < foldingCellElements.size(); i++) {
            FoldingCellView cell = foldingCellElements.get(i);
            cell.setVisibility(VISIBLE);
            // not FIRST(BOTTOM) element - animate front view
            if (i != 0) {
                FoldAnimation foldAnimation = new FoldAnimation(FoldAnimation.FoldAnimationMode.UNFOLD_UP, mCameraHeight, part90degreeAnimationDuration)
                        .withStartOffset(nextDelay)
                        .withInterpolator(new DecelerateInterpolator());
                // if last(top) element - add end listener
                if (i == foldingCellElements.size() - 1) {
                    foldAnimation.setAnimationListener(animationEndListener);
                }
                cell.animateFrontView(foldAnimation);
                nextDelay = nextDelay + part90degreeAnimationDuration;
            }
            // if not last(top) element - animate whole view
            if (i != foldingCellElements.size() - 1) {
                cell.startAnimation(new FoldAnimation(FoldAnimation.FoldAnimationMode.FOLD_UP, mCameraHeight, part90degreeAnimationDuration)
                        .withStartOffset(nextDelay)
                        .withInterpolator(new DecelerateInterpolator()));
                nextDelay = nextDelay + part90degreeAnimationDuration;
            }
        }
    }

    protected void startUnfoldAnimation(ArrayList<FoldingCellView> foldingCellElements, ViewGroup foldingLayout,
                                        int part90degreeAnimationDuration, AnimationEndListener animationEndListener) {
        int nextDelay = 0;
        for (int i = 0; i < foldingCellElements.size(); i++) {
            FoldingCellView cell = foldingCellElements.get(i);
            cell.setVisibility(VISIBLE);
            foldingLayout.addView(cell);
            // if not first(top) element - animate whole view
            if (i != 0) {
                FoldAnimation foldAnimation = new FoldAnimation(FoldAnimation.FoldAnimationMode.UNFOLD_DOWN, mCameraHeight, part90degreeAnimationDuration)
                        .withStartOffset(nextDelay)
                        .withInterpolator(new DecelerateInterpolator());

                // if last(bottom) element - add end listener
                if (i == foldingCellElements.size() - 1) {
                    foldAnimation.setAnimationListener(animationEndListener);
                }

                nextDelay = nextDelay + part90degreeAnimationDuration;
                cell.startAnimation(foldAnimation);

            }
            // not last(bottom) element - animate front view
            if (i != foldingCellElements.size() - 1) {
                cell.animateFrontView(new FoldAnimation(FoldAnimation.FoldAnimationMode.FOLD_DOWN, mCameraHeight, part90degreeAnimationDuration)
                        .withStartOffset(nextDelay)
                        .withInterpolator(new DecelerateInterpolator()));
                nextDelay = nextDelay + part90degreeAnimationDuration;
            }
        }
    }
}

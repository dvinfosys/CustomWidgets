package com.dvinfosys.widgets.Sticker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import com.dvinfosys.widgets.R;

public class TextSticker extends Sticker {

    private static final String mEllipsis = "\u2026";

    private final Context context;
    private final Rect realBounds;
    private final Rect textRect;
    private final TextPaint textPaint;
    private Drawable drawable;
    private StaticLayout staticLayout;
    private Layout.Alignment alignment;
    private String text;
    private boolean isShadow = false;
    private int shadowColor = Color.TRANSPARENT;
    private float maxTextSizePixels;

    private float minTextSizePixels;

    private float lineSpacingMultiplier = 1.0f;

    private float lineSpacingExtra = 0.0f;

    public TextSticker(@NonNull Context context) {
        this(context, null);
    }

    public TextSticker(@NonNull Context context, @Nullable Drawable drawable) {
        this.context = context;
        this.drawable = drawable;
        if (drawable == null) {
            this.drawable = ContextCompat.getDrawable(context, R.drawable.sticker_transparent_background);
        }
        textPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG);
        realBounds = new Rect(0, 0, getWidth(), getHeight());
        textRect = new Rect(0, 0, getWidth(), getHeight());
        minTextSizePixels = convertSpToPx(6);
        maxTextSizePixels = convertSpToPx(32);
        alignment = Layout.Alignment.ALIGN_CENTER;
        textPaint.setTextSize(maxTextSizePixels);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Matrix matrix = getMatrix();
        canvas.save();
        canvas.concat(matrix);
        if (drawable != null) {
            drawable.setBounds(realBounds);
            drawable.draw(canvas);
        }
        canvas.restore();

        canvas.save();
        canvas.concat(matrix);
        if (textRect.width() == getWidth()) {
            int dy = getHeight() / 2 - staticLayout.getHeight() / 2;
            // center vertical
            canvas.translate(0, dy);
        } else {
            int dx = textRect.left;
            int dy = textRect.top + textRect.height() / 2 - staticLayout.getHeight() / 2;
            canvas.translate(dx, dy);
        }
        staticLayout.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getWidth() {
        return drawable.getIntrinsicWidth();
    }

    @Override
    public int getHeight() {
        return drawable.getIntrinsicHeight();
    }

    @Override
    public void release() {
        super.release();
        if (drawable != null) {
            drawable = null;
        }
    }

    @NonNull
    @Override
    public TextSticker setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        textPaint.setAlpha(alpha);
        return this;
    }

    @NonNull
    @Override
    public Drawable getDrawable() {
        return drawable;
    }

    @Override
    public TextSticker setDrawable(@NonNull Drawable drawable) {
        this.drawable = drawable;
        realBounds.set(0, 0, getWidth(), getHeight());
        textRect.set(0, 0, getWidth(), getHeight());
        return this;
    }

    @NonNull
    public TextSticker setDrawable(@NonNull Drawable drawable, @Nullable Rect region) {
        this.drawable = drawable;
        realBounds.set(0, 0, getWidth(), getHeight());
        if (region == null) {
            textRect.set(0, 0, getWidth(), getHeight());
        } else {
            textRect.set(region.left, region.top, region.right, region.bottom);
        }
        return this;
    }

    @NonNull
    public Typeface getTypeface() {
        return this.textPaint.getTypeface();
    }

    @NonNull
    public TextSticker setTypeface(@Nullable Typeface typeface) {
        textPaint.setTypeface(typeface);
        return this;
    }

    @NonNull
    public TextSticker setShadowText(@ColorInt int shadowText) {
        this.shadowColor = shadowText;
        if (isShadow)
            textPaint.setShadowLayer(1.0f, 2.0f, 2.0f, shadowColor);
        else textPaint.setShadowLayer(0, 0, 0, Color.TRANSPARENT);
        return this;
    }

    @NonNull
    public int getShadowColor() {
        return this.shadowColor;
    }

    @NonNull
    public TextSticker isShadow(@NonNull boolean isShadow) {
        this.isShadow = isShadow;
        return this;
    }

    @NonNull
    public boolean isShadow() {
        return this.isShadow;
    }

    @NonNull
    public TextSticker setTextAlign(@NonNull Layout.Alignment alignment) {
        this.alignment = alignment;
        return this;
    }

    @NonNull
    public TextSticker setMaxTextSize(@Dimension(unit = Dimension.SP) float size) {
        textPaint.setTextSize(convertSpToPx(size));
        maxTextSizePixels = textPaint.getTextSize();
        return this;
    }

    @NonNull
    public int getMaxTextSize(){
        return (int)this.textPaint.getTextSize();
    }

    @NonNull
    public TextSticker setMinTextSize(float minTextSizeScaledPixels) {
        minTextSizePixels = convertSpToPx(minTextSizeScaledPixels);
        return this;
    }

    @NonNull
    public TextSticker setLineSpacing(float add, float multiplier) {
        lineSpacingMultiplier = multiplier;
        lineSpacingExtra = add;
        return this;
    }

    @Nullable
    public String getText() {
        return text;
    }

    @NonNull
    public TextSticker setText(@Nullable String text) {
        this.text = text;
        return this;
    }

    @NonNull
    public int getTextColor() {
        return this.textPaint.getColor();
    }

    @NonNull
    public TextSticker setTextColor(@ColorInt int color) {
        textPaint.setColor(color);
        return this;
    }

    @NonNull
    public TextSticker resizeText() {
        final int availableHeightPixels = textRect.height();

        final int availableWidthPixels = textRect.width();

        final CharSequence text = getText();

        // Safety check
        // (Do not resize if the view does not have dimensions or if there is no text)
        if (text == null
                || text.length() <= 0
                || availableHeightPixels <= 0
                || availableWidthPixels <= 0
                || maxTextSizePixels <= 0) {
            return this;
        }

        float targetTextSizePixels = maxTextSizePixels;
        int targetTextHeightPixels =
                getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);

        // Until we either fit within our TextView
        // or we have reached our minimum text size,
        // incrementally try smaller sizes
        while (targetTextHeightPixels > availableHeightPixels
                && targetTextSizePixels > minTextSizePixels) {
            targetTextSizePixels = Math.max(targetTextSizePixels - 2, minTextSizePixels);

            targetTextHeightPixels =
                    getTextHeightPixels(text, availableWidthPixels, targetTextSizePixels);
        }

        // If we have reached our minimum text size and the text still doesn't fit,
        // append an ellipsis
        // (NOTE: Auto-ellipsize doesn't work hence why we have to do it here)
        if (targetTextSizePixels == minTextSizePixels
                && targetTextHeightPixels > availableHeightPixels) {
            // Make a copy of the original TextPaint object for measuring
            TextPaint textPaintCopy = new TextPaint(textPaint);
            textPaintCopy.setTextSize(targetTextSizePixels);

            // Measure using a StaticLayout instance
            StaticLayout staticLayout =
                    new StaticLayout(text, textPaintCopy, availableWidthPixels, Layout.Alignment.ALIGN_NORMAL,
                            lineSpacingMultiplier, lineSpacingExtra, false);

            // Check that we have a least one line of rendered text
            if (staticLayout.getLineCount() > 0) {
                // Since the line at the specific vertical position would be cut off,
                // we must trim up to the previous line and add an ellipsis
                int lastLine = staticLayout.getLineForVertical(availableHeightPixels) - 1;

                if (lastLine >= 0) {
                    int startOffset = staticLayout.getLineStart(lastLine);
                    int endOffset = staticLayout.getLineEnd(lastLine);
                    float lineWidthPixels = staticLayout.getLineWidth(lastLine);
                    float ellipseWidth = textPaintCopy.measureText(mEllipsis);

                    // Trim characters off until we have enough room to draw the ellipsis
                    while (availableWidthPixels < lineWidthPixels + ellipseWidth) {
                        endOffset--;
                        lineWidthPixels =
                                textPaintCopy.measureText(text.subSequence(startOffset, endOffset + 1).toString());
                    }

                    setText(text.subSequence(0, endOffset) + mEllipsis);
                }
            }
        }
        textPaint.setTextSize(targetTextSizePixels);
        staticLayout =
                new StaticLayout(this.text, textPaint, textRect.width(), alignment, lineSpacingMultiplier,
                        lineSpacingExtra, true);
        return this;
    }

    public float getMinTextSizePixels() {
        return minTextSizePixels;
    }


    protected int getTextHeightPixels(@NonNull CharSequence source, int availableWidthPixels,
                                      float textSizePixels) {
        textPaint.setTextSize(textSizePixels);
        StaticLayout staticLayout =
                new StaticLayout(source, textPaint, availableWidthPixels, Layout.Alignment.ALIGN_NORMAL,
                        lineSpacingMultiplier, lineSpacingExtra, true);
        return staticLayout.getHeight();
    }

    private float convertSpToPx(float scaledPixels) {
        return scaledPixels * context.getResources().getDisplayMetrics().scaledDensity;
    }
}

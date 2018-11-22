package com.carozhu.fastdev.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.carozhu.fastdev.R;


/**
 * 图标+文字 Button
 */
public class StateButton extends AppCompatButton implements View.OnTouchListener {

    //圆角半径
    int cornerRadius = 0;
    //边框宽度
    int borderStroke = 0;
    //边框颜色
    int borderColor = 0;
    //enable为false时的颜色
    int unableColor = 0;
    //背景shape
    GradientDrawable shape;

    int colorId;
    int alpha;
    boolean unable;

    protected int drawableWidth;
    protected DrawablePositions drawablePosition;
    protected int iconPadding;




    // Cached to prevent allocation during onLayout
    Rect bounds;


    private enum DrawablePositions {
        NONE,
        LEFT_AND_RIGHT,
        LEFT,
        RIGHT
    }


    public StateButton(Context context) {
        this(context, null);
        bounds = new Rect();
    }

    public StateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        bounds = new Rect();
        if (shape == null) {
            shape = new GradientDrawable();
        }
        // Slight contortion to prevent allocating in onLayout
        if (null == bounds) {
            bounds = new Rect();
        }
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateButton, 0, 0);

        cornerRadius = (int) a.getDimension(R.styleable.StateButton_corner_radius, 0);
        borderStroke = (int) a.getDimension(R.styleable.StateButton_border_stroke, 0);
        borderColor = a.getColor(R.styleable.StateButton_border_color, 0);
        unableColor = a.getColor(R.styleable.StateButton_unable_color, Color.GRAY);
        int paddingId = a.getDimensionPixelSize(R.styleable.StateButton_iconPadding, 0);
        setIconPadding(paddingId);

        ColorDrawable buttonColor = (ColorDrawable) getBackground();
        colorId = buttonColor.getColor();
        alpha = 255;

        if (unable) {
            shape.setColor(unableColor);
        } else {
            shape.setColor(colorId);
        }

        a.recycle();



        setOnTouchListener(this);


        init();
    }



    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {

        return super.onTouchEvent(motionEvent);

    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed) {
            shape.setAlpha((int) (alpha * 0.6));
            init();
        } else {
            shape.setAlpha(alpha);
            init();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        unable = !enabled;
        if (shape != null) {
            shape = new GradientDrawable();
            if (unable) {
                shape.setColor(unableColor);
            } else {
                shape.setColor(colorId);
            }
            init();
        }
    }


    public void init() {
        //设置圆角半径
        shape.setCornerRadius(cornerRadius);
        //设置边框宽度和颜色
        shape.setStroke(borderStroke, borderColor);
        //将GradientDrawable设置为背景
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(shape);
        } else {
            setBackgroundDrawable(shape);
        }
    }

    public void setIconPadding(int padding) {
        iconPadding = padding;
        requestLayout();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        Paint textPaint = getPaint();
        String text = getText().toString();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        int textWidth = bounds.width();
        int factor = (drawablePosition == DrawablePositions.LEFT_AND_RIGHT) ? 2 : 1;
        int contentWidth = drawableWidth + iconPadding * factor + textWidth;
        int horizontalPadding = (int) ((getWidth() / 2.0) - (contentWidth / 2.0));

        setCompoundDrawablePadding(-horizontalPadding + iconPadding);

        switch (drawablePosition) {
            case LEFT:
                setPadding(horizontalPadding, getPaddingTop(), 0, getPaddingBottom());
                break;

            case RIGHT:
                setPadding(0, getPaddingTop(), horizontalPadding, getPaddingBottom());
                break;

            case LEFT_AND_RIGHT:
                setPadding(horizontalPadding, getPaddingTop(), horizontalPadding, getPaddingBottom());
                break;

            default:
                setPadding(0, getPaddingTop(), 0, getPaddingBottom());
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);

        if (left != null && right != null) {
            drawableWidth = left.getIntrinsicWidth() + right.getIntrinsicWidth();
            drawablePosition = DrawablePositions.LEFT_AND_RIGHT;
        } else if (left != null) {
            drawableWidth = left.getIntrinsicWidth();
            drawablePosition = DrawablePositions.LEFT;
        } else if (right != null) {
            drawableWidth = right.getIntrinsicWidth();
            drawablePosition = DrawablePositions.RIGHT;
        } else {
            drawablePosition = DrawablePositions.NONE;
        }

        requestLayout();
    }


    public float getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        init();
    }

    public int getBorderStroke() {
        return borderStroke;
    }

    public void setBorderStroke(int borderStroke) {
        this.borderStroke = borderStroke;
        init();
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        init();
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = Color.parseColor(borderColor);
        init();
    }

    public int getUnableColor() {
        return unableColor;
    }

    public void setUnableColor(@ColorInt int unableColor) {
        this.unableColor = unableColor;
        init();
    }

    public void setUnableColor(String unableColor) {
        this.unableColor = Color.parseColor(unableColor);
        init();
    }



}

package com.ng.xerath.ui.view.rounded;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class RoundedFrameLayout extends FrameLayout implements RoundedLayoutHelper.RoundedLayoutDelegate {

    private RoundedLayoutHelper mHelper;

    public RoundedFrameLayout(Context context) {
        this(context, null);
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mHelper = new RoundedLayoutHelper(this);
    }

    /**
     * @param radius px
     */
    public void setRadius(int radius) {
        setRadius(radius, radius, radius, radius);
    }

    public void setRadius(float radius) {
        setRadius((int) (radius + 0.5));
    }

    public void setRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        mHelper.setRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius);
    }

    /**
     * @return {mTopRightRadius, mTopRightRadius, mBottomLeftRadius, mBottomRightRadius}
     */
    public int[] getRadius() {
        return mHelper.getRadius();
    }


    public void setRadiusEnable(boolean enable){
        mHelper.setRadiusEnable(enable);
    }

    public boolean isRadiusEnable() {
        return mHelper.isRadiusEnable();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        mHelper.dispatchDraw(canvas);
    }

    @Override
    public void rLayoutInvalidate() {
        invalidate();
    }

    @Override
    public void rLayoutDispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    @Override
    public ViewGroup rLayoutSelf() {
        return this;
    }

    public void setStroke(boolean enableStroke, float strokeWidth, int strokeColor) {
        mHelper.setStroke(enableStroke, strokeWidth, strokeColor);
    }
}
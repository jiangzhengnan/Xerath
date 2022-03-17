package com.ng.xerath.ui.view.rounded;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

public class RoundedLayoutHelper {

    private int mTopLeftRadius;
    private int mTopRightRadius;
    private int mBottomLeftRadius;
    private int mBottomRightRadius;

    private boolean mRadiusEnable = true;

    private Paint mRoundPaint;
    private Paint mImagePaint;

    private RoundedLayoutDelegate mDelegate;
    private boolean mEnableStroke;
    private Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public interface RoundedLayoutDelegate {
        void rLayoutInvalidate();

        void rLayoutDispatchDraw(Canvas canvas);

        View rLayoutSelf();
    }

    public RoundedLayoutHelper(RoundedLayoutDelegate delegate) {
        mDelegate = delegate;
        mRoundPaint = new Paint();
        mRoundPaint.setColor(Color.WHITE);
        mRoundPaint.setAntiAlias(true);
        mRoundPaint.setStyle(Paint.Style.FILL);
        mRoundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mImagePaint = new Paint();
        mImagePaint.setXfermode(null);

        mStrokePaint.setStyle(Paint.Style.STROKE);
    }

    public void setRadius(int radius) {
        setRadius(radius, radius, radius, radius);
    }

    public void setRadius(float radius) {
        setRadius((int) (radius + 0.5));
    }

    public void setRadius(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius) {
        mTopLeftRadius = topLeftRadius;
        mTopRightRadius = topRightRadius;
        mBottomLeftRadius = bottomLeftRadius;
        mBottomRightRadius = bottomRightRadius;
        mDelegate.rLayoutInvalidate();
    }

    /**
     * @return {mTopRightRadius, mTopRightRadius, mBottomLeftRadius, mBottomRightRadius}
     */
    public int[] getRadius() {
        return new int[]{mTopRightRadius, mTopRightRadius, mBottomLeftRadius, mBottomRightRadius};
    }

    public void setRadiusEnable(boolean enable) {
        mRadiusEnable = enable;
    }

    public boolean isRadiusEnable() {
        return mRadiusEnable;
    }

    public void dispatchDraw(Canvas canvas) {
        if (!mRadiusEnable ||
                mTopLeftRadius == 0 && mTopRightRadius == 0 && mBottomLeftRadius == 0 && mBottomRightRadius == 0
                ) {
            mDelegate.rLayoutDispatchDraw(canvas);
            drawRightAngleStrokeLine(canvas);
            return;
        }
        canvas.saveLayer(new RectF(0, 0, canvas.getWidth(), canvas.getHeight()), mImagePaint, Canvas.ALL_SAVE_FLAG);
        mDelegate.rLayoutDispatchDraw((canvas));
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        drawRoundStrokeLine(canvas);
        canvas.restore();
    }

    private void drawStrokeArc(Canvas canvas, RectF rectF,  float startAngle, float sweepAngle) {
        if (mEnableStroke) {
            canvas.drawArc(rectF, startAngle, sweepAngle, false, mStrokePaint);
        }
    }

    private void drawRightAngleStrokeLine(Canvas canvas) {
        if (mEnableStroke) {
            int width = mDelegate.rLayoutSelf().getWidth();
            int height = mDelegate.rLayoutSelf().getHeight();
            canvas.drawLine(0, 0, width, 0, mStrokePaint);
            canvas.drawLine(0, height, width, height, mStrokePaint);
            canvas.drawLine(width, 0, width, height, mStrokePaint);
            canvas.drawLine(0, 0, 0, height, mStrokePaint);
        }
    }

    private void drawRoundStrokeLine(Canvas canvas) {
        if (mEnableStroke) {
            int width = mDelegate.rLayoutSelf().getWidth();
            int height = mDelegate.rLayoutSelf().getHeight();
            canvas.drawLine(mTopLeftRadius, 0, width - mTopRightRadius, 0, mStrokePaint);
            canvas.drawLine(mBottomLeftRadius, height, width - mBottomRightRadius, height, mStrokePaint);
            canvas.drawLine(width, mTopRightRadius, width, height - mBottomRightRadius, mStrokePaint);
            canvas.drawLine(0, mTopLeftRadius, 0, height - mBottomLeftRadius, mStrokePaint);
        }
    }

    private void drawTopLeft(Canvas canvas) {
        if (mTopLeftRadius > 0) {
            Path path = new Path();
            path.moveTo(0, mTopLeftRadius);
            path.lineTo(0, 0);
            path.lineTo(mTopLeftRadius, 0);
            RectF rectF = new RectF(0, 0, mTopLeftRadius * 2, mTopLeftRadius * 2);
            path.arcTo(rectF,
                    -90, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
            drawStrokeArc(canvas, rectF, 180, 90);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (mTopRightRadius > 0) {
            int width = mDelegate.rLayoutSelf().getWidth();
            Path path = new Path();
            path.moveTo(width - mTopRightRadius, 0);
            path.lineTo(width, 0);
            path.lineTo(width, mTopRightRadius);
            RectF rectF = new RectF(width - 2 * mTopRightRadius, 0, width,
                    mTopRightRadius * 2);
            path.arcTo(rectF, 0, -90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
            drawStrokeArc(canvas, rectF, 270, 90);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mBottomLeftRadius > 0) {
            int height = mDelegate.rLayoutSelf().getHeight();
            Path path = new Path();
            path.moveTo(0, height - mBottomLeftRadius);
            path.lineTo(0, height);
            path.lineTo(mBottomLeftRadius, height);
            RectF rectF = new RectF(0, height - 2 * mBottomLeftRadius,
                    mBottomLeftRadius * 2, height);
            path.arcTo(rectF, 90, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
            drawStrokeArc(canvas, rectF, 90, 90);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (mBottomRightRadius > 0) {
            int height = mDelegate.rLayoutSelf().getHeight();
            int width = mDelegate.rLayoutSelf().getWidth();
            Path path = new Path();
            path.moveTo(width - mBottomRightRadius, height);
            path.lineTo(width, height);
            path.lineTo(width, height - mBottomRightRadius);
            RectF rectF = new RectF(width - 2 * mBottomRightRadius, height - 2
                    * mBottomRightRadius, width, height);
            path.arcTo(rectF, 0, 90);
            path.close();
            canvas.drawPath(path, mRoundPaint);
            drawStrokeArc(canvas, rectF, 0, 90);
        }
    }

    public void setStroke(boolean enableStroke, float strokeWidth, int strokeColor) {
        mEnableStroke = enableStroke;
        mStrokePaint.setStrokeWidth(strokeWidth);
        mStrokePaint.setColor(strokeColor);
    }

}
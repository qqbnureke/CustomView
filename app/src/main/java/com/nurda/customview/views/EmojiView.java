package com.nurda.customview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nurda.customview.R;

public class EmojiView extends View {

    public static final int HAPPY = 0;
    public static final int SAD = 1;
    public static final int NEUTRAL = 2;
    public static final int SHOCKED = 3;

    private int faceColor = Color.YELLOW;
    private int eyesColor = Color.BLACK;
    private int mouthColor = Color.BLACK;
    private int borderColor = Color.BLACK;
    private float borderWidth = 4.0F;
    private int happinessState = HAPPY;

    private Paint paint;
    private Path mouthPath;
    private Path eyebrowPath;
    private RectF mouthRect;

    private int size = 0;

    public EmojiView(Context context) {
        this(context, null);
    }

    public EmojiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EmojiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }


    public final void setHappinessState(int state) {
        this.happinessState = state;
        this.invalidate();
    }

    private void init(AttributeSet attrs){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mouthPath = new Path();
        eyebrowPath = new Path();
        mouthRect = new RectF();

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.EmojiView);

        happinessState = ta.getInt(R.styleable.EmojiView_state, (int) HAPPY);
        faceColor = ta.getColor(R.styleable.EmojiView_faceColor, faceColor);
        eyesColor = ta.getColor(R.styleable.EmojiView_eyesColor, eyesColor);
        mouthColor = ta.getColor(R.styleable.EmojiView_mouthColor, mouthColor);
        borderColor = ta.getColor(R.styleable.EmojiView_borderColor, borderColor);
        borderWidth = ta.getDimension(R.styleable.EmojiView_borderWidth, borderWidth);

        ta.recycle();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawFace(canvas);
        drawEyes(canvas);
        drawEyeBrows(canvas);
        drawMouth(canvas);
    }

    private void drawFace(Canvas canvas) {
        paint.setColor(faceColor);
        paint.setStyle(Paint.Style.FILL);

        float radius = size / 2f;

        canvas.drawCircle(size / 2f, size / 2f, radius, paint);

        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);

        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint);
    }

    private void drawEyes(Canvas canvas) {
        paint.setColor(eyesColor);
        paint.setStyle(Paint.Style.FILL);

        switch (happinessState){
            case HAPPY:
            case SAD:
                RectF leftEyeRect = new RectF(size * 0.32f, size * 0.23f,
                        size * 0.43f, size * 0.50f);
                canvas.drawOval(leftEyeRect, paint);

                RectF rightEyeRect = new RectF(size * 0.57f, size * 0.23f,
                        size * 0.68f, size * 0.50f);
                canvas.drawOval(rightEyeRect, paint);
                break;
            case NEUTRAL:
            case SHOCKED:
                canvas.drawCircle(size*0.3f, size*0.365f, size*0.08f, paint);//left
                canvas.drawCircle(size*0.7f, size*0.365f, size*0.08f, paint);//right
                break;
        }
    }

    private void drawEyeBrows(Canvas canvas){
        if (happinessState == SHOCKED){

            eyebrowPath.reset();

            paint.setColor(eyesColor);
            paint.setStyle(Paint.Style.FILL);

            eyebrowPath.moveTo(size * 0.2f, size * 0.27f);
            eyebrowPath.quadTo(size * 0.27f, size * 0.15f, size * 0.36f, size * 0.21f);
            eyebrowPath.quadTo(size * 0.3f, size * 0.19f, size * 0.2f, size * 0.27f);

            eyebrowPath.moveTo(size * 0.8f, size * 0.27f);
            eyebrowPath.quadTo(size * 0.73f, size * 0.15f, size * 0.63f, size * 0.21f);
            eyebrowPath.quadTo(size * 0.7f, size * 0.19f, size * 0.8f, size * 0.27f);

            canvas.drawPath(eyebrowPath, paint);
        }

    }


    private void drawMouth(Canvas canvas) {
        mouthPath.reset();

        mouthPath.moveTo(size * 0.22f, size * 0.7f);

        paint.setColor(mouthColor);
        paint.setStyle(Paint.Style.FILL);

        switch (happinessState){
            case HAPPY:
                mouthPath.quadTo(size * 0.5f, size * 0.80f, size * 0.78f, size * 0.7f);
                mouthPath.quadTo(size * 0.5f, size * 0.90f, size * 0.22f, size * 0.7f);
                canvas.drawPath(mouthPath, paint);
                break;
            case SAD:
                mouthPath.quadTo(size * 0.5f, size * 0.50f, size * 0.78f, size * 0.7f);
                mouthPath.quadTo(size * 0.5f, size * 0.60f, size * 0.22f, size * 0.7f);
                canvas.drawPath(mouthPath, paint);
                break;
            case NEUTRAL:
                mouthRect.set(size*0.308f, size*0.7f, size*0.708f, size*0.75f);
                canvas.drawRect(mouthRect, paint);
                break;
            case SHOCKED:
                canvas.drawCircle(size*0.5f, size*0.75f, size*0.08f, paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }
}

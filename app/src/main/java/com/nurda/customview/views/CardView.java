package com.nurda.customview.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurda.customview.R;
import com.nurda.customview.model.CardInfo;

public class CardView extends ViewGroup {

    private int headerColor;
    private int headerTextColor;
    private int cardBackgroundColor;
    private int subtopicTextColor;
    private int descriptionTextColor;
    private int dividerColor;

    private int paddingHorizontal;
    private int paddingVertical;
    private int paddingInner;
    private int dividerHeight;
    private int cornerRadius;

    private float subtopicTextSize;
    private float descriptionTextSize;

    private Paint paint;
    private RectF rect;

    private TextView topicTextView;
    private TextView subtopicTextView;
    private TextView descriptionTextView;
    private Button action1;
    private Button action2;
    private ImageView cardImageView;

    public CardView( Context context) {
        super(context);
        init(context);
    }

    public CardView( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardView( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        setClipToPadding(false);

        // Prepare colors
        headerColor = ContextCompat.getColor(context, R.color.primary);
        headerTextColor = ContextCompat.getColor(context, R.color.white_text);
        cardBackgroundColor = ContextCompat.getColor(context, R.color.white);
        subtopicTextColor = ContextCompat.getColor(context, R.color.primary_text);
        descriptionTextColor = ContextCompat.getColor(context, R.color.secondary_text);
        dividerColor = ContextCompat.getColor(context, R.color.divider);

        // Prepare paddings
        paddingHorizontal = dpToPx(8);
        paddingVertical = dpToPx(8);
        paddingInner = dpToPx(8);
        dividerHeight = dpToPx(1);
        cornerRadius = dpToPx(16);

        // Prepare text sizes
        subtopicTextSize = 14;
        descriptionTextSize = 12;

        paint = new Paint();
        rect = new RectF();


        // Instantiate, setup and attach child views
        topicTextView = new TextView(context);
        topicTextView.setTextSize(subtopicTextSize);
        topicTextView.setTextColor(headerTextColor);
        topicTextView.setGravity(Gravity.START);
        addView(topicTextView);

        subtopicTextView = new TextView(context);
        subtopicTextView.setTextSize(subtopicTextSize);
        subtopicTextView.setTextColor(subtopicTextColor);
        subtopicTextView.setGravity(Gravity.START);
        addView(subtopicTextView);

        descriptionTextView = new TextView(context);
        descriptionTextView.setTextSize(descriptionTextSize);
        descriptionTextView.setTextColor(descriptionTextColor);
        descriptionTextView.setGravity(Gravity.START);
        addView(descriptionTextView);

        action1 = new Button(context);
        action1.setTextSize(subtopicTextSize);
        action1.setTextColor(headerTextColor);
        action1.setGravity(Gravity.START);
        addView(action1);

        action2 = new Button(context);
        action2.setTextSize(subtopicTextSize);
        action2.setTextColor(headerTextColor);
        action2.setGravity(Gravity.END);
        addView(action2);

        cardImageView = new ImageView(context);
        cardImageView.setImageResource(R.drawable.imagee);
        addView(cardImageView);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();

        paint.setColor(headerColor);
        final int headerHeight = paddingVertical + topicTextView.getMeasuredHeight() + paddingInner;
        rect.set(0,0, width, headerHeight + cornerRadius);
        canvas.save();
        canvas.clipRect(0,0, width, headerHeight);
        canvas.drawRoundRect( rect, cornerRadius, cornerRadius, paint);
        canvas.restore();

        paint.setColor(cardBackgroundColor);
        rect.set(0, headerHeight - cornerRadius, width, height);
        canvas.save();
        canvas.clipRect(0, headerHeight, width, height);
        canvas.drawRoundRect( rect, cornerRadius, cornerRadius, paint);
        canvas.restore();

        paint.setStrokeWidth(dividerHeight);
        paint.setColor(dividerColor);
        final float largeDividerY = descriptionTextView.getBottom() + paddingInner
                + dividerHeight / 2f + 1;
        canvas.drawLine(0, largeDividerY, width, largeDividerY, paint);

        super.dispatchDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        final int bodyTextWidth = widthMeasureSpec - paddingHorizontal * 2;
        final int footerButtonWidth = widthMeasureSpec / 2 - paddingHorizontal;
        final int headerTextWidth = widthMeasureSpec / 2 - paddingHorizontal;

        //Specify max width for the components
        cardImageView.setMaxWidth(bodyTextWidth);
        descriptionTextView.setMaxWidth(bodyTextWidth);
        action1.setMaxWidth(footerButtonWidth);
        action2.setMaxWidth(footerButtonWidth);

        measureChild(topicTextView, widthMeasureSpec, heightMeasureSpec);
        measureChild(subtopicTextView, widthMeasureSpec, heightMeasureSpec);
        measureChild(cardImageView, bodyTextWidth, heightMeasureSpec);
        measureChild(descriptionTextView, bodyTextWidth, heightMeasureSpec);
        measureChild(action1, footerButtonWidth, heightMeasureSpec);
        measureChild(action2, footerButtonWidth, heightMeasureSpec);

        final int heightUsed = paddingVertical +
                topicTextView.getMeasuredHeight() +
                paddingInner +
                cardImageView.getMeasuredHeight() +
                paddingInner +
                descriptionTextView.getMeasuredHeight() +
                paddingInner +
                Math.max(action1.getMeasuredHeight(), action2.getMeasuredHeight()) +
                paddingVertical;

        setMeasuredDimension(widthMeasureSpec, heightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int width = right - left;
        final int leftBorder = paddingHorizontal;
        final int rightBorder = right - paddingHorizontal;
        final int centerHorizontal  = width / 2;
        final int centerVertical = (bottom - top) / 2;

        int heightUsed = paddingVertical;


        //Layout rows by one-one
        topicTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + topicTextView.getMeasuredWidth(),
                heightUsed + topicTextView.getMeasuredHeight()
        );

        heightUsed += topicTextView.getMeasuredHeight() + paddingInner;

        cardImageView.layout(
                leftBorder,
                heightUsed,
                leftBorder + cardImageView.getMeasuredWidth(),
                heightUsed + cardImageView.getMeasuredHeight()
        );

        heightUsed += cardImageView.getMeasuredHeight();

        descriptionTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + descriptionTextView.getMeasuredWidth(),
                heightUsed + descriptionTextView.getMeasuredHeight()
        );

        heightUsed += descriptionTextView.getMeasuredHeight() + paddingInner;

        action1.layout(
                leftBorder,
                heightUsed,
                leftBorder + action1.getMeasuredWidth(),
                heightUsed + action1.getMeasuredHeight()
        );
        action2.layout(
                centerHorizontal + paddingHorizontal,
                heightUsed,
                centerHorizontal + action2.getMeasuredWidth() + paddingHorizontal,
                heightUsed + action2.getMeasuredHeight()
        );
        heightUsed += Math.max(action1.getMeasuredHeight(), action2.getMeasuredHeight()) + paddingInner;


    }

    public void bindFlight(CardInfo cardInfo){
        topicTextView.setText(cardInfo.getTopic());
        subtopicTextView.setText(cardInfo.getSubtopic());
        descriptionTextView.setText(cardInfo.getDescription());
        cardImageView.setImageResource(cardInfo.getImage());
        action1.setText(cardInfo.getAction1());
        action2.setText(cardInfo.getAction2());
    }

    private int dpToPx(int dp){
        final Resources resources = getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }
}

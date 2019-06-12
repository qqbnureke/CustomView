package com.nurda.customview.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nurda.customview.R;
import com.nurda.customview.model.CardInfo;
import com.nurda.customview.model.Flight;

public class FlightView extends ViewGroup {

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
    private Path cardBordersPath;

    private TextView topicTextView;
    private TextView subtopicTextView;
    private TextView descriptionTextView;
    private Button action1;
    private Button action2;
    private ImageView cardImageView;

    public FlightView(Context context) {
        super(context);
        init(context);
    }

    public FlightView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FlightView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
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
        descriptionTextSize = 4;

        // Prepare drawing objects
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rect = new RectF();
        cardBordersPath = new Path();

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
        descriptionTextView.setTextSize(subtopicTextSize);
        descriptionTextView.setTextColor(subtopicTextColor);
        descriptionTextView.setGravity(Gravity.START);
        addView(descriptionTextView);

        action1 = new Button(context);
        action1.setTextSize(descriptionTextSize);
        action1.setTextColor(descriptionTextColor);
        action1.setGravity(Gravity.START);
        addView(action1);

        action2 = new Button(context);
        action2.setTextSize(descriptionTextSize);
        action2.setTextColor(descriptionTextColor);
        action2.setGravity(Gravity.START);
        addView(action2);

        cardImageView = new ImageView(context);
        cardImageView.setImageResource(R.drawable.imagee);
        addView(cardImageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        // Measure the plane image first, it's kind of anchor for other children
        measureChild(cardImageView, widthMeasureSpec, heightMeasureSpec);

        // Determine text width for the header views and the body views
        final int bodyTextWidth = (widthMeasureSpec - cardImageView.getMeasuredWidth()) / 2 - paddingHorizontal;
        final int headerTextWidth = widthMeasureSpec / 2 - paddingHorizontal;

        // Specify max width for the TextViews
        topicTextView.setMaxWidth(headerTextWidth);
        subtopicTextView.setMaxWidth(headerTextWidth);
        descriptionTextView.setMaxWidth(bodyTextWidth);
        action1.setMaxWidth(bodyTextWidth);
        action2.setMaxWidth(bodyTextWidth);

        // Measure children
        measureChild(topicTextView, headerTextWidth, heightMeasureSpec);
        measureChild(subtopicTextView, bodyTextWidth, heightMeasureSpec);
        measureChild(descriptionTextView, bodyTextWidth, heightMeasureSpec);
        measureChild(action1, bodyTextWidth, heightMeasureSpec);
        measureChild(action2, bodyTextWidth, heightMeasureSpec);

        // Calculate the height of the whole view
        final int heightUsed = paddingVertical +
                topicTextView.getMeasuredHeight() +
                paddingInner +
                paddingInner +
                subtopicTextView.getMeasuredHeight() +
                cardImageView.getMeasuredHeight() +
                descriptionTextView.getMeasuredHeight() +
                action1.getMeasuredHeight() +
                action2.getMeasuredHeight() +
                dividerHeight +
                paddingInner +
                dividerHeight +
                paddingInner +
                paddingVertical;

        // Finally, set the measured dimensions to the FlightView
        setMeasuredDimension(widthMeasureSpec, heightUsed);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        // Prepare some dimens
        final int width = right - left;
        final int leftBorder = paddingHorizontal;
        final int rightBorder = width - paddingHorizontal;
        final int centerHorizontal = width / 2;
        final int centerVertical = (bottom - top) / 2;

        // Counter for the spent height
        int heightUsed = paddingVertical;

        // Layout rows one-by-one
        // 1. Header
        topicTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + topicTextView.getMeasuredWidth(),
                heightUsed + topicTextView.getMeasuredHeight()
        );
        heightUsed += topicTextView.getMeasuredHeight() + paddingInner + paddingInner;

        // 2. Take off and landing times
        subtopicTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + subtopicTextView.getMeasuredWidth(),
                heightUsed + subtopicTextView.getMeasuredHeight()
        );
        heightUsed += subtopicTextView.getMeasuredHeight();

        cardImageView.layout(
                leftBorder,
                heightUsed,
                leftBorder + cardImageView.getMeasuredWidth(),
                heightUsed + cardImageView.getMeasuredHeight()
        );
        heightUsed += cardImageView.getMeasuredHeight();

        // 3. Airports' codes row
        descriptionTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + descriptionTextView.getMeasuredWidth(),
                heightUsed + descriptionTextView.getMeasuredHeight()
        );
        heightUsed += descriptionTextView.getMeasuredHeight();

        // 4. Cities row
        action1.layout(
                leftBorder,
                heightUsed,
                leftBorder + action1.getMeasuredWidth(),
                heightUsed + action1.getMeasuredHeight()
        );
        heightUsed += action1.getMeasuredHeight() + dividerHeight;

        // 5. Airports' names row
        action2.layout(
                leftBorder,
                heightUsed,
                leftBorder + action2.getMeasuredWidth(),
                heightUsed + action2.getMeasuredHeight()
        );
        heightUsed += action2.getMeasuredHeight() + paddingInner + dividerHeight + paddingInner;

        // 7. Plane image view
//        cardImageView.layout(
//                centerHorizontal - cardImageView.getMeasuredWidth() / 2,
//                centerVertical - cardImageView.getMeasuredHeight() / 2,
//                centerHorizontal + cardImageView.getMeasuredWidth() - cardImageView.getMeasuredWidth() / 2,
//                centerVertical + cardImageView.getMeasuredHeight() - cardImageView.getMeasuredHeight() / 2
//        );
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int width = canvas.getWidth();
        final int height = canvas.getHeight();

        // Draw card header background
        paint.setColor(headerColor);
        final int headerHeight = paddingVertical + topicTextView.getMeasuredHeight() + paddingInner;
        rect.set(0, 0, width, headerHeight + cornerRadius);
        canvas.save();
        canvas.clipRect(0, 0, width, headerHeight);
        canvas.drawRoundRect(
                rect,
                cornerRadius,
                cornerRadius,
                paint
        );
        canvas.restore();

        // Draw card background
        paint.setColor(cardBackgroundColor);
        rect.set(0, headerHeight - cornerRadius, width, height);
        canvas.save();
        canvas.clipRect(0, headerHeight, width, height);
        canvas.drawRoundRect(
                rect,
                cornerRadius,
                cornerRadius,
                paint
        );
        canvas.restore();

        // Draw dividers
        //noinspection SuspiciousNameCombination
        paint.setStrokeWidth(dividerHeight);
        paint.setColor(dividerColor);
        final float smallDividerY = action1.getBottom() + dividerHeight / 2f + 1;
        canvas.drawLine(
                paddingHorizontal,
                smallDividerY,
                cardImageView.getLeft() - paddingInner,
                smallDividerY,
                paint
        );
        canvas.drawLine(
                cardImageView.getRight() + paddingInner,
                smallDividerY,
                width - paddingHorizontal,
                smallDividerY,
                paint
        );
        final float largeDividerY = action2.getBottom() + paddingInner + dividerHeight / 2f + 1;
        canvas.drawLine(
                0,
                largeDividerY,
                width,
                largeDividerY,
                paint
        );

        // Prevent drawing touch feedback outside
        rect.set(0, 0, width, height);
        cardBordersPath.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CCW);
        canvas.clipPath(cardBordersPath);

        // Draw children
        super.dispatchDraw(canvas);
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

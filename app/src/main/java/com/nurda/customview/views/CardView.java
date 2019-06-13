package com.nurda.customview.views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
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
    private int buttonTextColor;

    private int paddingHorizontal;
    private int paddingVertical;
    private int paddingInner;
    private float dividerWidth;
    private int cornerRadius;

    private float headerTextSize;
    private float subtopicTextSize;
    private float buttonTextSize;
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
        this(context, null);
    }

    public CardView( Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CardView( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        setClipToPadding(false);

        headerColor = ContextCompat.getColor(getContext(), R.color.primary);
        headerTextColor = ContextCompat.getColor(getContext(), R.color.white_text);
        cardBackgroundColor = ContextCompat.getColor(getContext(), R.color.white);
        subtopicTextColor = ContextCompat.getColor(getContext(), R.color.primary_text);
        descriptionTextColor = ContextCompat.getColor(getContext(), R.color.secondary_text);
        dividerColor = ContextCompat.getColor(getContext(), R.color.divider);
        buttonTextColor = ContextCompat.getColor(getContext(), R.color.primary);

        paddingHorizontal = dpToPx(8);
        paddingVertical = dpToPx(8);
        paddingInner = dpToPx(8);
        dividerWidth = dpToPx(1);
        cornerRadius = dpToPx(16);

        headerTextSize = 14f;
        subtopicTextSize = 12f;
        buttonTextSize = 14f;
        descriptionTextSize = 12f;

        paint = new Paint();
        rect = new RectF();

        setupAttributes(attrs);

        attachChildViews();
    }

    private void setupAttributes(AttributeSet attrs){

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CardView);

        headerColor = ta.getColor(R.styleable.CardView_headerColor, headerColor);
        headerTextColor = ta.getColor(R.styleable.CardView_headerTextColor, headerTextColor);
        cardBackgroundColor = ta.getColor(R.styleable.CardView_cardBackgroundColor, cardBackgroundColor);
        descriptionTextColor = ta.getColor(R.styleable.CardView_descriptionTextColor, descriptionTextColor);
        dividerColor = ta.getColor(R.styleable.CardView_dividerColor, dividerColor);
        buttonTextColor = ta.getColor(R.styleable.CardView_buttonTextColor, buttonTextColor);

        headerTextSize = ta.getDimension(R.styleable.CardView_headerTextSize, headerTextSize);
        descriptionTextSize = ta.getDimension(R.styleable.CardView_descriptionTextSize, descriptionTextSize);
        dividerWidth = ta.getDimension(R.styleable.CardView_dividerWidth, dividerWidth);


        ta.recycle();
    }

    private void attachChildViews(){
        topicTextView = new TextView(getContext());
        topicTextView.setTextSize(headerTextSize);
        topicTextView.setTextColor(headerTextColor);
        topicTextView.setGravity(Gravity.START);
        addView(topicTextView);

        subtopicTextView = new TextView(getContext());
        subtopicTextView.setTextSize(subtopicTextSize);
        subtopicTextView.setTextColor(subtopicTextColor);
        subtopicTextView.setGravity(Gravity.START);
        addView(subtopicTextView);

        descriptionTextView = new TextView(getContext());
        descriptionTextView.setTextSize(descriptionTextSize);
        descriptionTextView.setTextColor(descriptionTextColor);
        descriptionTextView.setGravity(Gravity.START);
        addView(descriptionTextView);

        action1 = new Button(getContext());
        action1.setTextSize(buttonTextSize);
        action1.setTextColor(buttonTextColor);
        action1.setText("Добавить");
        action1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        action1.setGravity(Gravity.CENTER);
        addView(action1);

        action2 = new Button(getContext());
        action2.setTextSize(buttonTextSize);
        action2.setTextColor(buttonTextColor);
        action2.setText("Перейти");
        action2.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        action2.setGravity(Gravity.CENTER);
        addView(action2);

        cardImageView = new ImageView(getContext());
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

        paint.setStrokeWidth(dividerWidth);
        paint.setColor(dividerColor);
        final float largeDividerY = descriptionTextView.getBottom() + paddingInner
                + dividerWidth / 2f + 1;
        canvas.drawLine(0, largeDividerY, width, largeDividerY, paint);

        super.dispatchDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

        final int bodyTextWidth = widthMeasureSpec - paddingHorizontal * 2;
        final int footerButtonWidth = widthMeasureSpec / 2 - paddingHorizontal;
        final int headerTextWidth = widthMeasureSpec / 2 - paddingHorizontal;

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
                paddingInner + paddingInner +
                subtopicTextView.getMeasuredHeight() +
                paddingInner +
                cardImageView.getMeasuredHeight() +
                paddingInner +
                descriptionTextView.getMeasuredHeight() +
                paddingInner + paddingInner +
                Math.max(action1.getMeasuredHeight(), action2.getMeasuredHeight());

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

        topicTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + topicTextView.getMeasuredWidth(),
                heightUsed + topicTextView.getMeasuredHeight()
        );

        heightUsed += topicTextView.getMeasuredHeight() + paddingInner;

        subtopicTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + subtopicTextView.getMeasuredWidth(),
                heightUsed + subtopicTextView.getMeasuredHeight()
        );
        heightUsed += subtopicTextView.getMeasuredHeight() + paddingInner;

        cardImageView.layout(
                centerHorizontal - cardImageView.getMeasuredWidth() / 2,
                heightUsed,
                centerHorizontal + cardImageView.getMeasuredWidth() - cardImageView.getMeasuredWidth() / 2,
                heightUsed + cardImageView.getMeasuredHeight()
        );


        heightUsed += cardImageView.getMeasuredHeight();

        descriptionTextView.layout(
                leftBorder,
                heightUsed,
                leftBorder + descriptionTextView.getMeasuredWidth(),
                heightUsed + descriptionTextView.getMeasuredHeight()
        );

        heightUsed += descriptionTextView.getMeasuredHeight() + paddingInner*2;

        action1.layout(
                leftBorder,
                heightUsed,
                leftBorder + action1.getMeasuredWidth(),
                heightUsed + action1.getMeasuredHeight()
        );
        action2.layout(
                action1.getMeasuredWidth() + paddingHorizontal*2,
                heightUsed,
                action1.getMeasuredWidth() + action2.getMeasuredWidth() + paddingHorizontal*3,
                heightUsed + action2.getMeasuredHeight()
        );

    }

    public void bindCard(CardInfo cardInfo){
        topicTextView.setText(cardInfo.getTopic());
        subtopicTextView.setText(cardInfo.getSubtopic());
        descriptionTextView.setText(cardInfo.getDescription());
        cardImageView.setImageResource(cardInfo.getImage());

    }

    private int dpToPx(int dp){
        final Resources resources = getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }
}

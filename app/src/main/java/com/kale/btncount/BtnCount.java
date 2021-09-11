package com.kale.btncount;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BtnCount extends RelativeLayout {

    private Context context;
    private AttributeSet attrs;
    private int styleAttr;
    private OnClickListener mListener;
    private int initialNumber;
    private int lastNumber;
    private int currentNumber;
    private int finalNumber;
    private OnValueChangeListener mOnValueChangeListener;
    public TextView addBtn, subtractBtn,textView;

    public BtnCount(Context context) {
        super(context);
    }

    public BtnCount(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BtnCount(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BtnCount(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView() {
        inflate(context, R.layout.btn_layout, this);
        final Resources res = getResources();
        final int defaultColor = res.getColor(R.color.final1);
        final int defaultTextColor = res.getColor(R.color.black);
        final Drawable defaultDrawable = res.getDrawable(R.drawable.rounded_corner);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ElegantNumberBtn,
                styleAttr, 0);

        initialNumber = a.getInt(R.styleable.ElegantNumberBtn_initialNumber, 0);
        finalNumber = a.getInt(R.styleable.ElegantNumberBtn_finalNumber, Integer.MAX_VALUE);
        float textSize = a.getDimension(R.styleable.ElegantNumberBtn_textSize, 13);
        int color = a.getColor(R.styleable.ElegantNumberBtn_backGroundColor, defaultColor);
        int textColor = a.getColor(R.styleable.ElegantNumberBtn_textColor, defaultTextColor);
        Drawable drawable = a.getDrawable(R.styleable.ElegantNumberBtn_backgroundDrawable);

        subtractBtn = findViewById(R.id.subtract_btn);
        addBtn = findViewById(R.id.add_btn);
        textView = findViewById(R.id.number_counter);
        LinearLayout mLayout = findViewById(R.id.layout);

        subtractBtn.setTextColor(textColor);
        addBtn.setTextColor(textColor);
        textView.setTextColor(textColor);
        subtractBtn.setTextSize(textSize);
        addBtn.setTextSize(textSize);
        textView.setTextSize(textSize);

        if (drawable == null) {
            drawable = defaultDrawable;
        }
        assert drawable != null;
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));
        mLayout.setBackground(drawable);

        textView.setText(String.valueOf(initialNumber));

        currentNumber = initialNumber;
        lastNumber = initialNumber;

        subtractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                int num = Integer.valueOf(textView.getText().toString());
                setNumber(String.valueOf(num - 1), true);
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                int num = Integer.valueOf(textView.getText().toString());
                setNumber(String.valueOf(num + 1), true);
            }
        });
        a.recycle();
    }

    private void callListener(View view) {
        if (mListener != null) {
            mListener.onClick(view);
        }

        if (mOnValueChangeListener != null) {
            if (lastNumber != currentNumber) {
                mOnValueChangeListener.onValueChange(this, lastNumber, currentNumber);
            }
        }
    }

    public void setNumber(String number) {
        lastNumber = currentNumber;
        this.currentNumber = Integer.parseInt(number);
        if (this.currentNumber > finalNumber) {
            this.currentNumber = finalNumber;
        }
        if (this.currentNumber < initialNumber) {
            this.currentNumber = initialNumber;
        }
        textView.setText(String.valueOf(currentNumber));
    }

    public void setNumber(String number, boolean notifyListener) {
        setNumber(number);
        if (notifyListener) {
            callListener(this);
        }
    }

    @FunctionalInterface
    public interface OnClickListener {
        void onClick(View view);
    }

    public interface OnValueChangeListener {
        void onValueChange(BtnCount view, int oldValue, int newValue);
    }

}

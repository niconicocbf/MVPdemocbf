package com.niconicocbf.tokubailab.mvpdemo_cbf.view.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.niconicocbf.tokubailab.mvpdemo_cbf.R;

public class RoundImageView extends AppCompatImageView {


    //圆角大小，默认为10
    private int mBorderRadius = 30;

    private Paint mPaint;

    // 3x3 矩阵，主要用于缩小放大
    private Matrix mMatrix;

    //渲染图像，使用图像为绘制图形着色
    private BitmapShader mBitmapShader;

    public String getmTextString() {
        return mTextString;
    }

    public void setmTextString(String mTextString) {
        this.mTextString = mTextString;
    }

    private String mTextString;
    private int mTextColor;
    private float mTextSize;
    private int integer;
    private int mTextBgColor = Color.DKGRAY;
    private int mtvBackgroudColor;
    private TextPaint mTextPaint;
    private Paint.FontMetrics fontMetrics;
    private float mTextHeight;
    private Paint p;
    private float[] radiusArray = { 30f, 30f, 30f, 30f, 30f, 30f, 30f, 30f };

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mMatrix = new Matrix();
        mPaint = new Paint();
        p = new Paint();
        mPaint.setAntiAlias(true);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.WBottomTitleView, defStyleAttr, 0);
        mTextString = typedArray.getString(R.styleable.WBottomTitleView_textString);
        mTextColor = typedArray.getColor(R.styleable.WBottomTitleView_textColor, Color.GRAY);
        mTextSize = typedArray.getDimension(R.styleable.WBottomTitleView_textDimension, 15);
        integer = typedArray.getInteger(R.styleable.WBottomTitleView_mAlpha, 150);
        mtvBackgroudColor = typedArray.getInteger(R.styleable.WBottomTitleView_mTextBgColor, mTextBgColor);
        typedArray.recycle();
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        invalidateTextPantAndMeasurements();

    }

    private void invalidateTextPantAndMeasurements() {
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        if(TextUtils.isEmpty(mTextString)){
            mTextString="";
        }
        fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null){
            return;
        }
        Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radiusArray, Path.Direction.CW);
        canvas.clipPath(path);


        Bitmap bitmap = drawableToBitamp(getDrawable());
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        if (!(bitmap.getWidth() == getWidth() && bitmap.getHeight() == getHeight()))
        {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bitmap.getWidth(),
                    getHeight() * 1.0f / bitmap.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mPaint.setShader(mBitmapShader);
        canvas.drawRoundRect(new RectF(0,0,getWidth(),getHeight()), mBorderRadius, mBorderRadius,
                mPaint);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        fontMetrics = mTextPaint.getFontMetrics();
        p.setColor(mTextBgColor);// 设置灰色
        p.setAlpha(integer);
        p.setStyle(Paint.Style.FILL);//设置填满
        canvas.drawRect(new RectF(paddingLeft,contentHeight - (fontMetrics.bottom - fontMetrics.top),contentWidth,contentHeight),p);
                //paddingLeft, contentHeight - (fontMetrics.bottom - fontMetrics.top), contentWidth, contentHeight, p);// 矩形
        // Draw the text.

        canvas.drawText(mTextString,
                contentWidth/2,
                paddingTop + (contentHeight - mTextHeight),
                mTextPaint);


    }


    private Bitmap drawableToBitamp(Drawable drawable)
    {
        if (drawable instanceof BitmapDrawable)
        {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        // 当设置不为图片，为颜色时，获取的drawable宽高会有问题，所有当为颜色时候获取控件的宽高
        int w = drawable.getIntrinsicWidth() <= 0 ? getWidth() : drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight() <= 0 ? getHeight() : drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}

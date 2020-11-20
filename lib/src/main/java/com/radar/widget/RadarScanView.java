package com.radar.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Zhangxian
 * @date 2020/3/27 11:58
 */
public class RadarScanView extends View {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    private int defaultWidth;
    private int defaultHeight;
    private int start;
    private int centerX;
    private int centerY;
    private int radarRadius;
    private int circleColor = Color.parseColor("#a2a2a2");
    private int radarColor = Color.parseColor("#99a2a2a2");
    private int tailColor = Color.parseColor("#FFaaaaaa");
    private int startAngle = 10;
    private int startAngle2 = 20;

    private OnScanClickListener onScanClickListener;
    private OnScanStateChangeListener onScanStateChangeListener;
    private Boolean canClickToStart = false; //是否可点击启动扫描

    //雷达内部圆环画笔宽度
    private int circleWidth = 1;
    //雷达外部内圆环
    private int innerRingStrokeWidth = 6;
    private int outerRingStrokeWidth = 3;

    private Paint mPaintCircle;
    private Paint mPaintRadar;
    private Paint mPaintInnerRing;
    private Paint mPaintOuterRing;
    private Matrix matrix;

    private boolean isThreadRunning = false;
    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            start += 3;
            matrix = new Matrix();
            matrix.postRotate(start, centerX, centerY);
            postInvalidate();
            handler.postDelayed(run, 20);
        }
    };

    public RadarScanView(Context context) {
        super(context);
        init(null, context);
    }

    public RadarScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public RadarScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    public void setOnScanClickListener(OnScanClickListener onScanClickListener) {
        this.onScanClickListener = onScanClickListener;
    }

    public void setOnScanStateChangeListener(OnScanStateChangeListener onScanStateChangeListener) {
        this.onScanStateChangeListener = onScanStateChangeListener;
    }

    @TargetApi(21)
    public RadarScanView(Context context, AttributeSet attrs, int defStyleAttr,
                         int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        radarRadius = Math.min(w, h) - 30;
    }

    private void init(AttributeSet attrs, Context context) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs,
                    R.styleable.RadarScanView);
            circleColor = ta.getColor(R.styleable.RadarScanView_circleColor, circleColor);
            radarColor = ta.getColor(R.styleable.RadarScanView_radarColor, radarColor);
            tailColor = ta.getColor(R.styleable.RadarScanView_tailColor, tailColor);
            circleWidth = ta.getInteger(R.styleable.RadarScanView_circleWidth, circleWidth);
            ta.recycle();
        }

        initPaint();
        //得到当前屏幕的像素宽高

        defaultWidth = dip2px(context, DEFAULT_WIDTH);
        defaultHeight = dip2px(context, DEFAULT_HEIGHT);

        matrix = new Matrix();

    }

    private void initPaint() {
        mPaintCircle = new Paint();
        mPaintCircle.setColor(circleColor);
        mPaintCircle.setAlpha(100);
        mPaintCircle.setAntiAlias(true);//抗锯齿
        mPaintCircle.setStyle(Paint.Style.STROKE);//设置实心
        mPaintCircle.setStrokeWidth(circleWidth);//画笔宽度

        //TODO 雷达外部内圆环改成属性
        mPaintInnerRing = new Paint();
        mPaintInnerRing.setColor(tailColor);
        mPaintInnerRing.setAlpha(94);
        mPaintInnerRing.setAntiAlias(true);
        mPaintInnerRing.setStyle(Paint.Style.STROKE);
        mPaintInnerRing.setStrokeWidth(innerRingStrokeWidth);

        mPaintOuterRing = new Paint();
        mPaintOuterRing.setColor(tailColor);
        mPaintOuterRing.setAlpha(43);
        mPaintOuterRing.setAntiAlias(true);
        mPaintOuterRing.setStyle(Paint.Style.STROKE);
        mPaintOuterRing.setStrokeWidth(outerRingStrokeWidth);

        mPaintRadar = new Paint();
        mPaintRadar.setColor(radarColor);
        mPaintRadar.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultWidth = 0;
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        if (modeWidth == MeasureSpec.EXACTLY) {
            resultWidth = sizeWidth;
        } else {
            resultWidth = defaultWidth;
            if (modeWidth == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, sizeWidth);
            }
        }

        int resultHeight = 0;
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (modeHeight == MeasureSpec.EXACTLY) {
            resultHeight = sizeHeight;
        } else {
            resultHeight = defaultHeight;
            if (modeHeight == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, sizeHeight);
            }
        }

        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        startAngle = (startAngle - 5) % 360;
        startAngle2 = (startAngle2 + 1) % 360;
        //分别绘制四个圆
        canvas.drawCircle(centerX, centerY, radarRadius / 7, mPaintCircle);
        canvas.drawCircle(centerX, centerY, radarRadius / 4, mPaintCircle);
        canvas.drawCircle(centerX, centerY, radarRadius / 3, mPaintCircle);
        canvas.drawCircle(centerX, centerY, 3 * radarRadius / 7, mPaintCircle);

        //设置颜色渐变从透明到不透明
        Shader shader = new SweepGradient(centerX, centerY, Color.TRANSPARENT, tailColor);
        mPaintRadar.setShader(shader);
        canvas.concat(matrix);
        canvas.drawCircle(centerX, centerY, 3 * radarRadius / 7, mPaintRadar);

        //绘制雷达外部内环
        RectF rectF1 = new RectF(15, 15, centerX * 2 - 15, centerY * 2 - 15);
        canvas.drawArc(rectF1, startAngle, 60, false, mPaintInnerRing);
        canvas.drawArc(rectF1, startAngle + 120, 60, false, mPaintInnerRing);
        canvas.drawArc(rectF1, startAngle + 240, 60, false, mPaintInnerRing);

        //绘制雷达外部外环
        RectF rectF2 = new RectF(2, 2, centerX * 2 - 2, centerY * 2 - 2);
        canvas.drawArc(rectF2, startAngle2, 80, false, mPaintOuterRing);
        canvas.drawArc(rectF2, startAngle2 + 120, 80, false, mPaintOuterRing);
        canvas.drawArc(rectF2, startAngle2 + 240, 80, false, mPaintOuterRing);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (canClickToStart) {
                    if (!isThreadRunning) {
                        isThreadRunning = true;
                        handler.post(run);
                    } else {
                        isThreadRunning = false;
                        handler.removeCallbacks(run);
                    }
                }
                if (onScanClickListener != null) {
                    onScanClickListener.onClick(this);
                }
                if (onScanStateChangeListener != null) {
                    onScanStateChangeListener.onStateChange(isThreadRunning);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                break;
        }
        return true;
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 设置view是否可单击启动扫描
     *
     * @param canClickToStart
     */
    public RadarScanView setCanClickToStart(Boolean canClickToStart) {
        this.canClickToStart = canClickToStart;
        return this;
    }

    /**
     * 开始扫描
     */
    public void startScan() {
        if (!isThreadRunning) {
            isThreadRunning = true;
            handler.post(run);
            if (onScanStateChangeListener != null) {
                onScanStateChangeListener.onStateChange(true);
            }
        }
    }

    /**
     * 结束扫描
     */
    public void stopScan() {
        if (isThreadRunning) {
            isThreadRunning = false;
            handler.removeCallbacks(run);
            if (onScanStateChangeListener != null) {
                onScanStateChangeListener.onStateChange(false);
            }
        }
    }

    /**
     * 获取扫描状态
     *
     * @return
     */
    public Boolean getScanState() {
        return isThreadRunning;
    }

    /**
     * 雷达扫描单击监听
     */
    public interface OnScanClickListener {
        void onClick(View view);
    }

    /**
     * 雷达状态监听
     */
    public interface OnScanStateChangeListener {
        void onStateChange(Boolean isScanning);
    }

}

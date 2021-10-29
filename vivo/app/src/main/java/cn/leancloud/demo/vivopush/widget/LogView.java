package cn.leancloud.demo.vivopush.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import cn.leancloud.demo.vivopush.R;

public class LogView extends View {

    public static final int LEVEL_INFO = 0;
    public static final int LEVEL_WARN = LEVEL_INFO + 1;
    public static final int LEVEL_ERROR = LEVEL_INFO + 2;

    private ArrayList<LogMessage> mLogMessages = new ArrayList<>();
    private ArrayList<LogMessage> mErrLogMessages = new ArrayList<>();
    private TextPaint mPaint;
    private boolean mFilter = false;

    private boolean isShowMills = false; //是否显示毫秒

    private int mDateColor;
    private int mErrColor;
    private int mWarnColor;

    private String mLocalPrefix;
    private String mServerPrefix;

    private int mDataTextWidth;
    private int mTextHeight;

    private int mLogLineCount = 0;

    private Paint mSeperatorPaint;
    private Path mSeperatorPath;

    private View mRootView;
    private View mLogFrameView;
    private View mBtnFrameView;
    private Runnable mRemeasureRunnable = new Runnable() {
        @Override
        public void run() {

            requestLayout();
        }
    };
    private SimpleDateFormat sDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);

    public LogView(Context context) {

        this(context, null);
    }

    public LogView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public LogView(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        final Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        float density = displayMetrics.density;

        mPaint = new TextPaint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(14f * density);

        mDateColor = resources.getColor(R.color.log_ui_date);
        mWarnColor = resources.getColor(R.color.log_ui_level_warn);
        mErrColor = resources.getColor(R.color.log_ui_level_error);
        mLocalPrefix = resources.getString(R.string.log_ui_client);
        mServerPrefix = resources.getString(R.string.log_ui_server);

        mSeperatorPaint = new Paint();
        mSeperatorPaint.setStyle(Paint.Style.STROKE);
        PathEffect effects = new DashPathEffect(new float[]{10f * density, 7f * density, 4 * density, 7f * density}, 1f);
        mSeperatorPaint.setColor(mDateColor);
        mSeperatorPaint.setPathEffect(effects);
        mSeperatorPaint.setStrokeWidth(1f * density);

        mSeperatorPath = new Path();

        String date = "00:00:00 ";
        mDataTextWidth = (int) mPaint.measureText(date);
        mTextHeight = (int) Math.ceil(mPaint.descent() - mPaint.ascent()) + 2;
    }

    public void setViews(View rootView, View logFrameview, View btnFrameView) {

        mRootView = rootView;
        mLogFrameView = logFrameview;
        mBtnFrameView = btnFrameView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minHeight = mRootView.getMeasuredHeight() - mBtnFrameView.getMeasuredHeight() - mLogFrameView.getMeasuredHeight();
        int logHeight = mLogLineCount * mTextHeight;
        int height = logHeight > minHeight ? logHeight : minHeight;
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
    }

    public void setText(String text, int level, boolean serverLog) {

        LogMessage logMessage = new LogMessage(text, level, serverLog, false);
        mLogMessages.add(logMessage);
        if (level == LEVEL_ERROR || level == LEVEL_WARN) {
            mErrLogMessages.add(logMessage);
        }
        prepareDraw();
    }

    public void seperate() {

        if (mFilter) {
            mErrLogMessages.add(new LogMessage(" ", 0, false, true));
        } else {
            mLogMessages.add(new LogMessage(" ", 0, false, true));
        }
        prepareDraw();
    }

    public void clear() {

        mLogMessages.clear();
        mErrLogMessages.clear();
        prepareDraw();
    }

    public void filter(boolean filter) {

        mFilter = filter;
        prepareDraw();
    }

    private void prepareDraw() {

        if (mLogMessages.size() > 400) {
            mLogMessages.remove(0);
        }
        if (mErrLogMessages.size() > 400) {
            mErrLogMessages.remove(0);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        ArrayList<LogMessage> logMessages = mFilter ? mErrLogMessages : mLogMessages;

        int lineCount = 0;
        int width = getMeasuredWidth();
        int size = logMessages.size();
        for (int i = size - 1; i >= 0; i--) {
            canvas.save();
            canvas.translate(0, lineCount * mTextHeight);
            LogMessage logMessage = logMessages.get(i);

            // date
            mPaint.setColor(mDateColor);
            StaticLayout staticLayout = new StaticLayout(logMessage.date, mPaint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
            staticLayout.draw(canvas);

            // log color
            int level = logMessage.level;
            int levelColor = Color.WHITE;
            if (level == LEVEL_WARN) {
                levelColor = mWarnColor;
            } else if (level == LEVEL_ERROR) {
                levelColor = mErrColor;
            }
            if (logMessage.emptyLine) {
                levelColor = mErrColor;
            }
            mPaint.setColor(levelColor);

            // log
            staticLayout = new StaticLayout(logMessage.msg, mPaint, width - mDataTextWidth, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false);
            canvas.save();
            canvas.translate(mDataTextWidth, 0);
            staticLayout.draw(canvas);
            canvas.restore();

            // sperator
            if (logMessage.emptyLine) {
                mSeperatorPath.reset();
                mSeperatorPath.moveTo(mDataTextWidth, mTextHeight / 2);
                mSeperatorPath.lineTo(width, mTextHeight / 2);
                canvas.drawPath(mSeperatorPath, mSeperatorPaint);
            }

            canvas.restore();
            lineCount += staticLayout.getLineCount();
        }

        // remeasure
        if (mLogLineCount != lineCount) {
            mLogLineCount = lineCount;
            removeCallbacks(mRemeasureRunnable);
            postDelayed(mRemeasureRunnable, 100);
        }
    }

    public void reverseTimeMillsState() {
        isShowMills = !isShowMills;
        mDataTextWidth = (int) mPaint.measureText(isShowMills ? "00:00:00:000 " : "00:00:00 ");
    }

    private class LogMessage {
        private String msg;
        private int level = LEVEL_INFO;
        private String date;
        private boolean emptyLine = false;

        private LogMessage(String _msg, int _level, boolean _serverLog, boolean _emptyLine) {

            if (!_emptyLine) {
                String prefix = _serverLog ? mServerPrefix : mLocalPrefix;
                msg = prefix + (_msg == null ? "" : _msg);
            } else {
                msg = " ";
            }
            level = _level;
            emptyLine = _emptyLine;
            date = sDateFormat.format(new Date()) + (isShowMills ? ":" + GregorianCalendar.getInstance().get(GregorianCalendar.MILLISECOND) : "") + " ";
        }
    }
}

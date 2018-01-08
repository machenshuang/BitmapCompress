package www.sean.com.bitmapcompress.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import www.sean.com.bitmapcompress.R;

import static www.sean.com.bitmapcompress.Constants.COMPRESS_QUALITY;
import static www.sean.com.bitmapcompress.Constants.COMPRESS_SAMPLING;

/**
 * 自定义ImageView
 * Created by SeanMa on 2018/1/4.
 */

@SuppressLint("AppCompatCustomView")
public class MyImageView extends View {

    private Bitmap mSrcBitmap;
    private TextListener mListener;
    private Paint mBitPaint;
    private int mType;
    private Rect mSrcRect, mDestRect;
    private String mSrcSize;
    private int screenWidth;
    private Context mContext;

    public MyImageView(Context context) {
        this(context,null);
    }

    public void setListener(TextListener listener){
        mListener = listener;
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        compressQuality();
    }

    public void setCompressType(int type){
        mType = type;
        switchCompressWay(mType);
        invalidate();
    }

    private void switchCompressWay(int type){
        switch (type){
            case COMPRESS_QUALITY :
                compressQuality();
                break;
            case COMPRESS_SAMPLING:
                compressSampling();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSrcRect = new Rect(0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight());
        mDestRect = new Rect(screenWidth/2-300, 0, screenWidth/2+300, 900);
        if (mListener!=null){
            mListener.showText(mSrcSize,mSrcBitmap.getByteCount()+"byte");
        }
        canvas.drawBitmap(mSrcBitmap,mSrcRect,mDestRect,mBitPaint);
    }

    private void compressQuality(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        mSrcSize = bm.getByteCount()+"byte";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,bos);
        byte[] bytes = bos.toByteArray();
        mSrcBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    private void compressSampling(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mSrcBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.test,options);
    }

    public interface TextListener{
        void showText(String srcSize,String size);
    }
}

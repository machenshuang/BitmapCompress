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
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

import www.sean.com.bitmapcompress.R;

/**
 * 自定义ImageView
 * Created by SeanMa on 2018/1/4.
 */

@SuppressLint("AppCompatCustomView")
public class MyImageView extends ImageView {

    private Bitmap mSrcBitmap;
    private TextListener mListener;
    private Paint mBitPaint;
    private int mType;
    private Rect mSrcRect, mDestRect;

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
        compressQuality();
        init();
    }

    private void init(){
        /*ViewGroup.LayoutParams params = this.getLayoutParams();
        params.height=200;
        params.width =100;
        this.setLayoutParams(params);*/

        mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBitPaint.setFilterBitmap(true);
        mBitPaint.setDither(true);

        mSrcRect = new Rect(0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight());
        mDestRect = new Rect(0, 0, 200, 300);
        if (mListener!=null){
            mListener.showText(mSrcBitmap.getByteCount()+"");
        }


    }


    public void setType(int type){
        mType = type;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mSrcBitmap!=null){
            canvas.drawBitmap(mSrcBitmap,mSrcRect,mDestRect,mBitPaint);
        }


    }

    private void compressQuality(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        Log.d("MyImageView","图片原始大小："+bm.getByteCount());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,bos);
        byte[] bytes = bos.toByteArray();
        mSrcBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        if (mListener!=null){
            mListener.showText(mSrcBitmap.getByteCount()+"");
        }

        invalidate();
    }

    public interface TextListener{
        void showText(String text);
    }
}

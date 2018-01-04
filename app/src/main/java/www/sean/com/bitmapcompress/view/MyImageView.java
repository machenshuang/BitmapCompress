package www.sean.com.bitmapcompress.view;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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

    public MyImageView(Context context) {
        super(context);
    }

    public void setListener(TextListener listener){
        mListener = listener;
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        this.getLayoutParams().width = 200;
        this.getLayoutParams().height = 300;

        compressQuality();
        if (mListener!=null){
            mListener.showText(mSrcBitmap.getByteCount()+"");
        }

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }

    private void compressQuality(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,bos);
        byte[] bytes = bos.toByteArray();
        mSrcBitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
    }

    public interface TextListener{
        void showText(String text);
    }
}

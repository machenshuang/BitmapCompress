package www.sean.com.bitmapcompress.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.sean.com.bitmapcompress.R;
import www.sean.com.bitmapcompress.view.MyImageView;

/**
 * 加载原图的Fragment
 * Created by SeanMa on 2018/1/4.
 */

public class FragmentA extends Fragment implements MyImageView.TextListener {

    private TextView tvSize;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_page,container,false);
        TextView text = view.findViewById(R.id.text);
        text.setText("质量压缩");
        MyImageView imageView = view.findViewById(R.id.imageview);
        imageView.setListener(this);
        tvSize = view.findViewById(R.id.size);

        return view;
    }

    @Override
    public void showText(String text) {
        tvSize.setText(text);
    }
}

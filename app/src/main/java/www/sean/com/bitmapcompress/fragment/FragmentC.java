package www.sean.com.bitmapcompress.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import www.sean.com.bitmapcompress.R;
import www.sean.com.bitmapcompress.view.MyImageView;

import static www.sean.com.bitmapcompress.Constants.COMPRESS_MATRIX;

/**
 * Created by SeanMa on 2018/1/8.
 */

public class FragmentC extends Fragment implements MyImageView.TextListener {
    private TextView tvSize;
    private TextView tvSrcSize;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_page, container, false);
        TextView text = view.findViewById(R.id.text);
        text.setText("缩放法压缩");
        MyImageView imageView = view.findViewById(R.id.imageview);
        imageView.setListener(this);
        imageView.setCompressType(COMPRESS_MATRIX);
        tvSize = view.findViewById(R.id.size);
        tvSrcSize = view.findViewById(R.id.src_size);

        return view;
    }

    @Override
    public void showText(String srcSize, String size) {
        tvSize.setText(size);
        tvSrcSize.setText(srcSize);
    }
}

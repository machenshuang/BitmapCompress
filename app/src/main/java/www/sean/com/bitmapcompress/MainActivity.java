package www.sean.com.bitmapcompress;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import www.sean.com.bitmapcompress.fragment.FragmentA;
import www.sean.com.bitmapcompress.fragment.FragmentB;
import www.sean.com.bitmapcompress.fragment.FragmentC;
import www.sean.com.bitmapcompress.fragment.FragmentD;
import www.sean.com.bitmapcompress.fragment.FragmentE;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏状态栏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mViewPager = findViewById(R.id.viewpager);
        List<Fragment> list = new ArrayList<>();
        list.add(new FragmentA());
        list.add(new FragmentB());
        list.add(new FragmentC());
        list.add(new FragmentD());
        list.add(new FragmentE());
        FragAdapter adapter = new FragAdapter(list, getSupportFragmentManager());

        mViewPager.setAdapter(adapter);
    }
}

class FragAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;

    public FragAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

package xzy.android.awidgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * (C) 2012 zhengyang.xu
 *
 * @author zhengyang.xu
 * @version 0.1
 * @since 1:39:33 PM Apr 17, 2012
 */
public class AnimationListViewActivity extends Activity {

    private ListView listView;

    private static boolean doAnimation = true;

    private int mFirstIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = new ListView(this);
        listView.setBackgroundColor(Color.WHITE);
        listView.setAdapter(new CustomAdapter());
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                    int totalItemCount) {
                // TODO Auto-generated method stub
                if (doAnimation) {
                    doAnimation = false;
                }
            }
        });
        setContentView(listView);

    }

    @Override
    protected void onResume() {
        doAnimation = true;
        listView.requestLayout();
        super.onResume();
    }

    private List<String> getData() {

        List<String> data = new ArrayList<String>();
        data.add("TEST DATA 1");
        data.add("TEST DATA 2");
        data.add("TEST DATA 3");
        data.add("TEST DATA 4");
        data.add("TEST DATA 5");
        data.add("TEST DATA 6");
        data.add("TEST DATA 7");
        data.add("TEST DATA 8");
        data.add("TEST DATA 9");
        data.add("TEST DATA 10");
        data.add("TEST DATA 11");
        return data;
    }

    private Handler mHandler = new Handler();

    private class CustomAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public CustomAdapter() {
            mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return AnimationListViewActivity.this.getData().size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = mInflater.inflate(R.layout.item1, null);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(AnimationListViewActivity.this.getData().get(position));

            if (doAnimation) {
                final Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide);
                final View animationView = convertView;
                animationView.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        animationView.setVisibility(View.VISIBLE);
                        animationView.startAnimation(anim);
                    }

                }, 30 * position);
            }
            return convertView;
        }

    }
}

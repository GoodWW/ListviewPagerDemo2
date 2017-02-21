package com.example.administrator.listviewpagerdemo;

import android.content.Context;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RefreshListView lv;

    private List<String> str;
    private ArrayList<Integer> img;
    private List<View> viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = new ArrayList<String>();

        img = new ArrayList<Integer>();
        img.add(R.mipmap.b);
        img.add(R.mipmap.c);
        img.add(R.mipmap.d);

        for (int i = 0; i < 50; i++) {
            str.add("data-----" + i);
        }

        lv = (RefreshListView) findViewById(R.id.lv);

        lv.addHeaderView(new HeadView(MainActivity.this));

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, str);
        lv.setAdapter(adapter);

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                System.out.println("firstVisibleItem=====" + firstVisibleItem);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 > 1) {
                    //Toast.makeText(MainActivity.this, "arg2======" + (arg2 - 2), 2000).show();
                    Toast.makeText(MainActivity.this, "arg2===="+(arg2 - 2), Toast.LENGTH_LONG).show();
                }

            }
        });

        lv.setonRefreshListener(new RefreshListView.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new AsyncTask<Void, Void, Void>() { //刷新过程中需要做的操作在这里
                    protected Void doInBackground(Void... params) { try {
                        Thread.sleep(1000); } catch (Exception e) {
                        e.printStackTrace(); }
                        int m = 1;
                        str.add("New ListItem"+m);
                        m++;
                        return null; }
                    //刷新完成后要通知listview进行界面调整
                    @Override
                    protected void onPostExecute(Void result) {
                        adapter.notifyDataSetChanged();
                        lv.onRefreshComplete();
                    }
                }.execute();

            }
        });
    }

    private View viewPage;
    /**
     * 顶部
     */
    private class HeadView extends RelativeLayout {
        ViewPager viewPager;
        DemoAdapter adapter;
        LinearLayout linDots;
        private Context mContext;
        public HeadView(Context context) {
            super(context);
            this.mContext = context;
            inflate(context, R.layout.lv_viewpager, this);
            viewPager = (ViewPager) findViewById(R.id.viewPager);
            linDots=(LinearLayout) findViewById(R.id.linDot);
            initData();
        }
        private void initData() {
            viewList = new ArrayList<View>();
            for (int i = 0; i < img.size(); i++) {

                viewPage = View
                        .inflate(mContext, R.layout.viewpager_item, null);
                ImageView imageView = (ImageView) viewPage
                        .findViewById(R.id.iv);
                imageView.setBackgroundResource(img.get(i));
                linDots.addView(dotsItem(i));
                viewList.add(viewPage);
            }

            adapter = new DemoAdapter(viewList);
            viewPager.setAdapter(adapter);

            //设置第一个小点为选中
            linDots.getChildAt(0).setSelected(true);

            viewPager.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    PointF downP = new PointF();
                    PointF curP = new PointF();
                    int act = event.getAction();
                    if (act == MotionEvent.ACTION_DOWN
                            || act == MotionEvent.ACTION_MOVE
                            || act == MotionEvent.ACTION_UP) {
                        ((ViewGroup) v)
                                .requestDisallowInterceptTouchEvent(true);
                        System.out.println("downP.x==="+downP.x+",downP.y==="+downP.y+",downP.x=="+downP.x+",downP.y==="+downP.y);
                        if (downP.x == curP.x && downP.y == curP.y) {
                            return false;
                        }
                    }
                    return false;
                }
            });
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    // TODO Auto-generated method stub
                    for (int i = 0; i < linDots.getChildCount(); i++) {
                        linDots.getChildAt(i).setSelected(false);
                    }
                    linDots.getChildAt(arg0).setSelected(true);
                }
                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    // TODO Auto-generated method stub
                }
                @Override
                public void onPageScrollStateChanged(int arg0) {
                    // TODO Auto-generated method stub
                }
            });
        }

        /**
         * 点
         * @param position
         * @return
         */
        private ImageView dotsItem(int position) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.view_dots, null);
            ImageView point = (ImageView) layout.findViewById(R.id.dot);
            android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            point.setLayoutParams(params);
            point.setId(position);
            return point;
        }
    }
}

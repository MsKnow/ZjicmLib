package com.know.zjicmlib.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.know.zjicmlib.APP;
import com.know.zjicmlib.R;
import com.know.zjicmlib.adapter.FViewpagerAdapter;
import com.know.zjicmlib.fragment.SearchFragment;
import com.know.zjicmlib.fragment.HomeFragment;
import com.know.zjicmlib.fragment.YooFragment;
import com.know.zjicmlib.modle.bean.Notice;
import com.know.zjicmlib.util.SharedPreUtil;
import com.know.zjicmlib.util.ToastUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity {

    private int quit = 0;
    //@Bind(R.id.tabs) TabLayout tab ;
    @Bind(R.id.viewpager) ViewPager viewPager;
    //@Bind(R.id.bottom_bar)BottomNavigationBar bottomBar;
    @Bind(R.id.bottomBar)BottomBar bottomBar;
    YooFragment yooFragment;

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(SharedPreUtil.getSharedPre().getBoolean("once",true)){

            Notice myNotice = new Notice();
            myNotice.setDate("2017-7-7");
            myNotice.setSrc("gank.io");
            myNotice.setTitle("浙传移动图书馆app上线");
            APP.mDb.insert(myNotice);

            //copy("home.html","home.html");
            SharedPreUtil.putBool("once",false);
        }



        ButterKnife.bind(this);

        bottomBar = BottomBar.attach(this,savedInstanceState);

        initBottomBar();

        List<String> tabList = new ArrayList<>();
        tabList.add("首页");
        tabList.add("搜索");
        tabList.add("个人");
        /*tab.setTabMode(TabLayout.MODE_FIXED);
        //tab.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式
        tab.addTab(tab.newTab().setText(tabList.get(0)));
        tab.addTab(tab.newTab().setText(tabList.get(1)));
        tab.addTab(tab.newTab().setText(tabList.get(2)));*/

        List<Fragment> fragments = new ArrayList<>();

            Fragment fm = new HomeFragment();
            Bundle bundle = new Bundle();
            bundle.putString("content","hehe");
            fm.setArguments(bundle);
            fragments.add(fm);


        yooFragment = new YooFragment();
        fragments.add(new SearchFragment());
        fragments.add(yooFragment);

        FViewpagerAdapter fViewpagerAdapter = new FViewpagerAdapter(getSupportFragmentManager(),
                fragments,tabList);
        viewPager.setAdapter(fViewpagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //bottomBar.unHide(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //tab.setupWithViewPager(viewPager);
        //tab.setTabsFromPagerAdapter(fViewpagerAdaer);

        //initBottomBar();
    }

    /*private void initBottomBar(){
        bottomBar.addItem(new BottomNavigationItem(R.drawable.bg_edit, "home").setActiveColor(R.color.color_refresh_1))
                .addItem(new BottomNavigationItem(R.drawable.bg_edit, "search").setActiveColor(R.color.color_refresh_2))
                .addItem(new BottomNavigationItem(R.drawable.bg_card_x, "me").setActiveColor(R.color.color_refresh_3))
                .setBarBackgroundColor(R.color.colorWrite)
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.colorPrimaryDark)
        .initialise();
        //bottomBar.setMode(BottomNavigationBar.MODE_SHIFTING);
        //bottomBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        //bottomBar.setAnimationDuration(1000);

        bottomBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position,false);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {



           }
      });
    }*/

    private void initBottomBar(){

        bottomBar.setItemsFromMenu(R.menu.menu_bottom, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(int menuItemId) {
                switch (menuItemId){
                    case R.id.bbar1:
                        viewPager.setCurrentItem(0,true);
                        break;
                    case R.id.bbar2:
                        viewPager.setCurrentItem(1,true);
                        break;
                    case R.id.bbar3:
                        viewPager.setCurrentItem(2,true);
                        break;
                }
            }

            @Override
            public void onMenuTabReSelected(int menuItemId) {
                Log.e("reselect",menuItemId+"");
            }
        });

        bottomBar.mapColorForTab(0, R.color.colorRed);
        bottomBar.mapColorForTab(1,R.color.colorYellow);
        bottomBar.mapColorForTab(2,R.color.colorBlue);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_share:
                //SharedPreUtil.getSharedPre().edit().clear().commit();
                //APP.mDb.delete(Notice.class);

                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);

                break;
        }
        switch (item.getItemId()){
            case R.id.action_logout:
                SharedPreUtil.getSharedPre().edit().clear().commit();
                yooFragment.logout();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }



    private void copy(String asstname,String filename){

        InputStream is;
        try {
            is = getAssets().open(asstname);
            int lenght = is.available();
            //创建byte数组
            byte[]  buffer = new byte[lenght];
            //将文件中的数据读到byte数组中
            is.read(buffer);
            String isString = new String(buffer, "utf-8");

            is.close();
            System.out.println(isString);
            write(isString,filename);

            System.out.println("安装 "+asstname+" 完成！" );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    private void write(String content, String filename) {

        try {
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        quit+=1;
        if(quit==2){
            this.finish();
        }else {
            ToastUtil.tShort("再按一次退出");
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    quit = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        //super.onBackPressed();
    }
}

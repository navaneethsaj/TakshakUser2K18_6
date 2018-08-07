package takshak.mace.takshak2k18;

import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class EventListActivity extends AppCompatActivity implements WorkshopFragment.OnFragmentInteractionListener,EventFragment.OnFragmentInteractionListener,InformalFragment.OnFragmentInteractionListener {
    String dept;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setBackgroundColor(Color.rgb(100,220,220));
        toolbar.setTitleTextColor(Color.WHITE);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        Bundle bundle = getIntent().getExtras();
        dept = bundle.getString("dept");
        tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Workshop"));
        tabLayout.addTab(tabLayout.newTab().setText("Formals"));
        tabLayout.addTab(tabLayout.newTab().setText("inFormals"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(Color.rgb(100,220,220),Color.WHITE);

        Log.d("resid",Integer.toString(getResources().getIdentifier("noimg.jpg", "assets",  getPackageName())));

        final ViewPager viewPager = findViewById(R.id.viewpagerevent);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public String getDept() {
        return dept;
    }
}

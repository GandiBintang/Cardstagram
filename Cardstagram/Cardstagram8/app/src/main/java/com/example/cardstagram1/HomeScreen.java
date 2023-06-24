package com.example.cardstagram1;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import android.animation.ArgbEvaluator;
import android.os.Handler;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;


public class HomeScreen extends AppCompatActivity {

    ViewPager viewPager;
    Adapter adapter;
    ArrayList<com.example.cardstagram1.Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    ImageButton logout;
    LayoutInflater layoutInflater;
    View viewPopUpWindow;
    PopupWindow popupWindow;
    boolean clicked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        doFunction();
    }



    @Override
    protected void onPause() {
        Log.d("Paused", "Paused right now");
        if(popupWindow != null) {
            popupWindow.dismiss();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("Resumed", "Resumed right now");
        doFunction();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if(popupWindow != null) {
            popupWindow.dismiss();
        }
        super.onDestroy();
    }

    public String getUri(int imageResource) {
        Uri imageUri = (new Uri.Builder())
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(getResources().getResourcePackageName(imageResource))
                .appendPath(getResources().getResourceTypeName(imageResource))
                .appendPath(getResources().getResourceEntryName(imageResource))
                .build();

        String returnString = imageUri.toString();
        return returnString;
    }




    public void doFunction() {


        logout = findViewById(R.id.logoutBtn);
        ImageButton likeBtn=(ImageButton)findViewById(R.id.btnLike);
        ImageButton postBtn=(ImageButton)findViewById(R.id.btnPost);
        Animation animBounce1, animBounce2;
        ConstraintLayout li = (ConstraintLayout) findViewById(R.id.homeLayout);

        Intent intentReceive = getIntent();
        if(intentReceive.getBundleExtra("BUNDLE1") != null) {
            Bundle args = intentReceive.getBundleExtra("BUNDLE1");
            models = (ArrayList<Model>) args.getSerializable("MODELKEY1");
        } else {
            models = new ArrayList<>();
            models.add(new com.example.cardstagram1.Model(getUri(R.drawable.guangzhou), "ryutanada", "Coming back to Guangzhou soon to attend offline class and explore China!"));
            models.add(new com.example.cardstagram1.Model(getUri(R.drawable.scut), "hirotanada", "One of the best University in China that is located in Guangdong Province, Guangzhou City."));
            models.add(new com.example.cardstagram1.Model(getUri(R.drawable.score), "gandi", "Our group project just got an A+ from the teacher! We really put our all into this test."));
            models.add(new com.example.cardstagram1.Model(getUri(R.drawable.scut_logo), "SCUT", "The South China University of Technology is a public university in Guangzhou, Guangdong, China."));
        }



        adapter = new Adapter(models, this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };

        colors = colors_temp;

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.logoutBtn) {
                    startActivity(new Intent(HomeScreen.this, MainActivity.class));
                }
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount())) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position % colors.length],
                                    colors[(position + 1) % colors.length]
                            )
                    );
                    li.setBackgroundColor(
                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position % colors.length],
                                    colors[(position + 1) % colors.length]
                            )
                    );

                }
                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                    li.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        animBounce1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        clicked = false;
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeBtn.startAnimation(animBounce1);

                if(!clicked) {
                    clicked = true;

                    layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

                    viewPopUpWindow = layoutInflater.inflate(R.layout.popuplayout,null);

                    popupWindow = new PopupWindow(viewPopUpWindow, 300,300,true);

                    popupWindow.setOutsideTouchable(true);
                    popupWindow.setAnimationStyle(R.style.Animation);
                    viewPopUpWindow.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            popupWindow.dismiss();
                            clicked = false;
                            return true;
                        }
                    });

                    popupWindow.showAtLocation(v.getRootView(), Gravity.CENTER, 0 ,0 );

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(popupWindow!= null) {
                                popupWindow.dismiss();
                                popupWindow = null;
                                clicked = false;
                            }
                        }
                    },1000);


                }
            }
        });


        animBounce2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.bounce);
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postBtn.startAnimation(animBounce2);
                Intent intent = new Intent(HomeScreen.this, InputActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("MODELKEY", (Serializable) models);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);


            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HomeScreen.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.onBackPressed();
    }
}

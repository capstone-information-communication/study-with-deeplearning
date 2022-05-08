package com.smp.frontend.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smp.frontend.R;
import com.smp.frontend.fragment.HomeFragment;
import com.smp.frontend.fragment.UploadFragment;
import com.smp.frontend.fragment.WorkBookFragment;
import com.smp.frontend.fragment.WrongAnswerFragment;

public class MainActivity extends AppCompatActivity {
    private Toast toast;
    private long backKeyPressedTime = 0;

    BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment HomeFrag;
    private UploadFragment UploadFrag;
    private WorkBookFragment WorkBookFrag;
    private WrongAnswerFragment WrongAnswerFrag;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home:
                        setFrag(0);
                        break;
                    case R.id.upload:
                        setFrag(1);
                        break;
                    case R.id.work_book:
                        setFrag(2);
                        break;
                    case R.id.wrong_answer_book:
                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        HomeFrag = new HomeFragment();
        //fragment1로 번들 전달
        UploadFrag = new UploadFragment();
        WorkBookFrag = new WorkBookFragment();
        WrongAnswerFrag = new WrongAnswerFragment();

        setFrag(0); // 첫 프래그먼트 화면을 무엇으로 지정해줄 것인지 선택.

    }
    // 프래그먼트 교체가 일어나는 실행문이다.
    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, HomeFrag);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, UploadFrag);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, WorkBookFrag);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, WrongAnswerFrag);
                ft.commit();
                break;
        }
    }
}
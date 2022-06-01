package com.smp.frontend.member.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.smp.frontend.R;
import com.smp.frontend.likeWorkbook.fragment.likeWorkBookFragment;
import com.smp.frontend.workbook.fragment.UploadFragment;
import com.smp.frontend.workbook.fragment.WorkBookFragment;
import com.smp.frontend.wrongAnswer.fragment.WrongAnswerFragment;

public class MainActivity extends AppCompatActivity {
    private Toast toast;
    private long backKeyPressedTime = 0;

    BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private likeWorkBookFragment HomeFrag;
    private UploadFragment UploadFrag;
    private WorkBookFragment WorkBookFrag;
    private WrongAnswerFragment WrongAnswerFrag;
    // Storage Permissions
    Boolean PermissionGranted = false;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE


            );
        }
    }

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
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            PermissionGranted = true;
            System.out.println("P granted");
        } else {
            PermissionGranted = false;
            System.out.println("P not granted");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verifyStoragePermissions(this);
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
        HomeFrag = new likeWorkBookFragment();
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
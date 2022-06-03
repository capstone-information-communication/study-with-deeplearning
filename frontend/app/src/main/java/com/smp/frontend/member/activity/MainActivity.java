package com.smp.frontend.member.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.smp.frontend.R;

import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.global.ViewPagerAdapter;
import com.smp.frontend.likeWorkbook.fragment.likeWorkBookFragment;
import com.smp.frontend.workbook.fragment.UploadFragment;
import com.smp.frontend.workbook.fragment.WorkBookFragment;
import com.smp.frontend.wrongAnswer.fragment.WrongAnswerFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toast toast;
    private long backKeyPressedTime = 0;

    private NavigationView navigationView;



    BottomNavigationView bottomNavigationView;


    // Storage Permissions
    Boolean PermissionGranted = false;

    public String getExtractedText() {
        return extractedText;
    }
    private String extractedText ="";

    ViewPager2 pager;
    ViewPagerAdapter adapter;

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,

    };
    private void extractPDF(InputStream Filename) {
        try {
            PdfReader reader = new PdfReader(Filename);
            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1).trim();
                if(extractedText.length() >= 3000){
                    break;
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error found is : \n" + e);
        }

    }
    public void startUpload(Intent intent){
        startReuslt.launch(intent);

    }
    ActivityResultLauncher<Intent> startReuslt =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    extractedText ="";
                    Intent Getresponse = result.getData();
                    Uri path = Getresponse.getData();
                    ContentResolver res = getContentResolver();
                    try {
                        InputStream in = res.openInputStream(path);
                        extractPDF(in);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    System.out.println("result.toString() = " + result.toString());
                    extractedText ="";

                }
            });

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
        navigationView = (NavigationView)findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);


        pager = findViewById(R.id.pager);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(likeWorkBookFragment.newInstance(0));
        fragments.add(UploadFragment.newInstance(1));
        fragments.add(WorkBookFragment.newInstance(2));
        fragments.add(WrongAnswerFragment.newInstance(3));

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

        });
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            pager.setCurrentItem(0);
                            break;
                        case R.id.upload:
                            pager.setCurrentItem(1);
                            break;
                        case R.id.work_book:
                            pager.setCurrentItem(2);
                            break;
                        case R.id.wrong_answer_book:
                            pager.setCurrentItem(3);
                            break;
                    }
                return true;
            }
        });
        pager = (ViewPager2) findViewById(R.id.pager);

        ViewPagerAdapter viewPager2Adapter = new ViewPagerAdapter(this, fragments);
        pager.setAdapter(viewPager2Adapter);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.btn_logout:
                PreferencesManager.removeValue(getApplicationContext());
                Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;
        }
        return false;
    }
}
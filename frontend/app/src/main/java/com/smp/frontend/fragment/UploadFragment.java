package com.smp.frontend.fragment;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.smp.frontend.R;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.STORAGE_SERVICE;

public class UploadFragment extends Fragment {
    private  View view;
    private  static  final  int REQ_CODE =123;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.fragment_upload, container, false);
         ImageButton Upload_btn = view.findViewById(R.id.btn_upload);
         TextView filename = view.findViewById(R.id.file_name);



         Upload_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                startReuslt.launch(intent);

             }
         });

        // Inflate the layout for this fragment
        return view;
    }
    ActivityResultLauncher<Intent> startReuslt =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    System.out.println("result = " + result);
                    System.out.println("result.getData().getClipData() = " + result.getData().getData().getPath());

                    String  SelectData = result.getData().getData().getPath();
                    System.out.println("SelectData = " + SelectData);
                    String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
                    System.out.println("rootPath = " + rootPath);
                    File f= new File(SelectData);
                    File b = f.getAbsoluteFile();
                    String fn = f.getName();
                    String fpn = rootPath + "/" + fn;
                    System.out.println("fpn = " + fpn);
                
                }
                else {
                    System.out.println("result.toString() = " + result.toString());
                }
            }
    );


}
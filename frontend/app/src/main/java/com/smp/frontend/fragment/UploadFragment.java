package com.smp.frontend.fragment;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
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

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.smp.frontend.R;


import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


import static android.app.Activity.RESULT_OK;
import static android.content.Context.STORAGE_SERVICE;
import static android.content.Context.STORAGE_STATS_SERVICE;


public class UploadFragment extends Fragment {
    private  View view;
    private void extractPDF(InputStream Filename) {
        try {
            String extractedText = "";
            PdfReader reader = new PdfReader(Filename);

            int n = reader.getNumberOfPages();
            for (int i = 0; i < n; i++) {
                extractedText = extractedText + PdfTextExtractor.getTextFromPage(reader, i + 1) ;
            }

            System.out.println("extractedText = " + extractedText);
            reader.close();
        } catch (Exception e) {
            System.out.println("Error found is : \n" + e);
        }
    }
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
                 intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
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
                    Intent Getresponse = result.getData();
                    Uri path = Getresponse.getData();

                    ContentResolver res = getActivity().getContentResolver();
                    try {
                        InputStream in = res.openInputStream(path);
                        extractPDF(in);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
                else {
                    System.out.println("result.toString() = " + result.toString());
                }
            }
    );


}
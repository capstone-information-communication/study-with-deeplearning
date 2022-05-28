package com.smp.frontend.workbook.fragment;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.smp.frontend.global.PreferencesManager;
import com.smp.frontend.R;
import com.smp.frontend.workbook.dto.UploadWorkBookResponseDto;
import com.smp.frontend.workbook.RetrofitClientWorkbook;
import com.smp.frontend.workbook.WorkbookController;
import com.smp.frontend.workbook.dto.UploadWorkBookRequestDto;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


import static android.app.Activity.RESULT_OK;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UploadFragment extends Fragment {
    private EditText title,description;
    private Button make_workbooks;
    private  View view;
    RetrofitClientWorkbook retrofitClient;
    private String extractedText="";
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.fragment_upload, container, false);
         title = (EditText) view.findViewById(R.id.et_title_send);
         description = (EditText) view.findViewById(R.id.et_content_send);
         make_workbooks = view.findViewById(R.id.make_workbooks_btn);
         make_workbooks.setEnabled(false);
         make_workbooks.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String title_text = title.getText().toString();
                 String description_text = description.getText().toString();

                     UploadWorkBookRequestDto request = new UploadWorkBookRequestDto(title_text, description_text, extractedText);
                     retrofitClient = RetrofitClientWorkbook.getInstance();
                     WorkbookController workbookController = RetrofitClientWorkbook.getRetrofitInterface();
                     Call<UploadWorkBookResponseDto> sendData = workbookController.SendWorkBook(
                             PreferencesManager.getString(getActivity().getApplicationContext(),"token"),request);
                     make_workbooks.setEnabled(false);
                     extractedText="";
                     sendData.enqueue(new Callback<UploadWorkBookResponseDto>() {
                         @Override
                         public void onResponse(Call<UploadWorkBookResponseDto> call, Response<UploadWorkBookResponseDto> response) {
                             if (response.code() == 201) {
                                 System.out.println("제이슨 요청 성공");
                             }
                             else if(response.code() == 400){
                                 try {
                                     JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                     String message = jsonObject.get("message").toString();
                                     System.out.println("message = " + message);
                                 } catch (IOException | JSONException e) {
                                     e.printStackTrace();
                                     System.out.println("제이슨 에러 메세지 message를 찾을 수 없음");
                                 }
                             }
                             else if(response.code() == 500){
                                 try {
                                     System.out.println("response.errorBody().string() = " + response.errorBody().string());
                                 } catch (IOException e) {
                                     e.printStackTrace();
                                 }
                             }
                         }

                         @Override
                         public void onFailure(Call<UploadWorkBookResponseDto> call, Throwable t) {
                             System.out.println("서버 통신 오류 서버 상태 확인");
                         }
                     });

             }
         });

         ImageButton Upload_btn = view.findViewById(R.id.btn_upload);
         Upload_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                 intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                 extractedText="";
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
                    System.out.println("Getresponse = " + Getresponse);
                    ContentResolver res = getActivity().getContentResolver();
                    try {
                        make_workbooks.setEnabled(true);
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
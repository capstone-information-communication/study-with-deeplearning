package com.smp.frontend.workbook.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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
import com.smp.frontend.likeWorkbook.fragment.likeWorkBookFragment;
import com.smp.frontend.member.activity.MainActivity;
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
    private ImageButton Upload_btn;
    private  View view;
    RetrofitClientWorkbook retrofitClient;
    private String extractedText="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public static UploadFragment newInstance(int number) {
        UploadFragment UploadFragment = new UploadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        UploadFragment.setArguments(bundle);
        return UploadFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view =inflater.inflate(R.layout.fragment_upload, container, false);
         title = (EditText) view.findViewById(R.id.et_title_send);
         description = (EditText) view.findViewById(R.id.et_content_send);
         make_workbooks = view.findViewById(R.id.make_workbooks_btn);
         Upload_btn = view.findViewById(R.id.btn_upload);
         make_workbooks.setEnabled(false);
         make_workbooks.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String title_text = title.getText().toString();
                 String description_text = description.getText().toString();
                    if(title.equals("") || description_text.equals("") || extractedText.equals("")){
                        make_workbooks.setEnabled(false);
                        System.out.println("?????? ?????? !!!");
                        return;
                    }
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
                                 System.out.println("????????? ?????? ??????");
                             }
                             else if(response.code() == 400){
                                 try {
                                     JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                     String message = jsonObject.get("message").toString();
                                     System.out.println("message = " + message);
                                 } catch (IOException | JSONException e) {
                                     e.printStackTrace();
                                     System.out.println("????????? ?????? ????????? message??? ?????? ??? ??????");
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
                             System.out.println("?????? ?????? ?????? ?????? ?????? ??????");
                         }
                     });

             }
         });
        Upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                extractedText="";
                ((MainActivity)getActivity()).startUpload(intent);
                extractedText= ((MainActivity)getActivity()).getExtractedText();
                make_workbooks.setEnabled(true);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
    }

}
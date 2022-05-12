package com.smp.frontend.workbook.dto;

public class UploadWorkBookRequestDto {
    private String title;
    private String description;
    private String text;

    public UploadWorkBookRequestDto(String title, String description, String text) {
        this.title = title;
        this.description = description;
        this.text = text;
    }
}

package com.whydigit.wms.ResponseDTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialUploadResponseDTO {

    private int totalRecords;
    private int successCount;
    private int failedCount;

    private List<String> successRows = new ArrayList<>();
    private List<String> errorRows = new ArrayList<>();
}
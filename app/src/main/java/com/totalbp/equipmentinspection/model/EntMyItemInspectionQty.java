package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntMyItemInspectionQty {

    public EntMyItemInspectionQty(String parentItemID, String totalResult) {
        ParentItemID = parentItemID;
        TotalResult = totalResult;
    }

    public  String ParentItemID;
    public  String TotalResult;

    public String getParentItemID() {
        return ParentItemID;
    }

    public void setParentItemID(String parentItemID) {
        ParentItemID = parentItemID;
    }

    public String getTotalResult() {
        return TotalResult;
    }

    public void setTotalResult(String totalResult) {
        TotalResult = totalResult;
    }

    public EntMyItemInspectionQty() {
    }


}


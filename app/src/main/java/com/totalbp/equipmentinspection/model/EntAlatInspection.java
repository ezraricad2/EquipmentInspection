package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntAlatInspection {


    public EntAlatInspection(String ID, String grpUniqueID, String subGrpUniqueID, String levelItem, String parentItemID, String tipeInspeksi, String namaItem, String mdInspAst, String StatusInspeksi, String TdtransID, String TipeJawaban, String Result, String ResultDeskripsi, String Notes, String TotalIns) {
        this.ID = ID;
        GrpUniqueID = grpUniqueID;
        SubGrpUniqueID = subGrpUniqueID;
        LevelItem = levelItem;
        ParentItemID = parentItemID;
        TipeInspeksi = tipeInspeksi;
        NamaItem = namaItem;
        MdInspAst = mdInspAst;
        this.StatusInspeksi = StatusInspeksi;
        this.TdtransID = TdtransID;
        this.TipeJawaban = TipeJawaban;
        this.Result = Result;
        this.ResultDeskripsi = ResultDeskripsi;
        this.Notes = Notes;
        this.TotalIns = TotalIns;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getGrpUniqueID() {
        return GrpUniqueID;
    }

    public void setGrpUniqueID(String grpUniqueID) {
        GrpUniqueID = grpUniqueID;
    }

    public String getSubGrpUniqueID() {
        return SubGrpUniqueID;
    }

    public void setSubGrpUniqueID(String subGrpUniqueID) {
        SubGrpUniqueID = subGrpUniqueID;
    }

    public String getLevelItem() {
        return LevelItem;
    }

    public void setLevelItem(String levelItem) {
        LevelItem = levelItem;
    }

    public String getParentItemID() {
        return ParentItemID;
    }

    public void setParentItemID(String parentItemID) {
        ParentItemID = parentItemID;
    }

    public String getTipeInspeksi() {
        return TipeInspeksi;
    }

    public void setTipeInspeksi(String tipeInspeksi) {
        TipeInspeksi = tipeInspeksi;
    }

    public String getNamaItem() {
        return NamaItem;
    }

    public void setNamaItem(String namaItem) {
        NamaItem = namaItem;
    }

    public  String ID;
    public  String GrpUniqueID;
    public  String SubGrpUniqueID;
    public  String LevelItem;
    public  String ParentItemID;
    public  String TipeInspeksi;
    public  String NamaItem;
    public String Notes;

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String Result;

    public String getTdtransID() {
        return TdtransID;
    }

    public void setTdtransID(String tdtransID) {
        TdtransID = tdtransID;
    }

    public String getTipeJawaban() {
        return TipeJawaban;
    }

    public void setTipeJawaban(String tipeJawaban) {
        TipeJawaban = tipeJawaban;
    }

    public String TdtransID;
    public String TipeJawaban;

    public String getStatusInspeksi() {
        return StatusInspeksi;
    }

    public void setStatusInspeksi(String statusInspeksi) {
        StatusInspeksi = statusInspeksi;
    }

    public String StatusInspeksi;

    public String getMdInspAst() {
        return MdInspAst;
    }

    public void setMdInspAst(String mdInspAst) {
        MdInspAst = mdInspAst;
    }

    public  String MdInspAst;
    public String ResultDeskripsi;
    public String TotalIns;

    public String getResultDeskripsi() {
        return ResultDeskripsi;
    }

    public void setResultDeskripsi(String resultDeskripsi) {
        ResultDeskripsi = resultDeskripsi;
    }

    public EntAlatInspection() {
    }


    public String getTotalIns() {
        return TotalIns;
    }

    public void setTotalIns(String totalIns) {
        TotalIns = totalIns;
    }
}


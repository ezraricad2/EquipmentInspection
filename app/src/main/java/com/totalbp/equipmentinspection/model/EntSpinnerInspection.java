package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntSpinnerInspection {

    public String MdInspAst;
    public String TipeJawaban;
    public String Deskripsi;
    public String Value;

    public String getMdInspAst() {
        return MdInspAst;
    }

    public void setMdInspAst(String mdInspAst) {
        MdInspAst = mdInspAst;
    }

    public String getTipeJawaban() {
        return TipeJawaban;
    }

    public void setTipeJawaban(String tipeJawaban) {
        TipeJawaban = tipeJawaban;
    }

    public String getDeskripsi() {
        return Deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        Deskripsi = deskripsi;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public EntSpinnerInspection(String mdInspAst, String tipeJawaban, String deskripsi, String value) {

        MdInspAst = mdInspAst;
        TipeJawaban = tipeJawaban;
        Deskripsi = deskripsi;
        Value = value;
    }

    public EntSpinnerInspection() {
    }



}


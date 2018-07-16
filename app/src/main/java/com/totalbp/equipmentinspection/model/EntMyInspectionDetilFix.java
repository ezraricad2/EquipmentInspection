package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntMyInspectionDetilFix {

    public EntMyInspectionDetilFix(String mdinsp, String inspector, String tglInspeksi, String kodeProyek) {
        MdInsp = mdinsp;
        Inspector = inspector;
        TglInspeksi = tglInspeksi;
        KodeProyek = kodeProyek;
    }

    public String getTglInspeksi() {
        return TglInspeksi;
    }

    public void setTglInspeksi(String tglInspeksi) {
        TglInspeksi = tglInspeksi;
    }

    public String getMdInsp() {
        return MdInsp;
    }

    public void setMdInsp(String mdInsp) {
        MdInsp = mdInsp;
    }

    public String getInspector() {
        return Inspector;
    }

    public void setInspector(String inspector) {
        Inspector = inspector;
    }

    public  String MdInsp;
    public  String Inspector;
    public  String TglInspeksi;

    public String getKodeProyek() {
        return KodeProyek;
    }

    public void setKodeProyek(String kodeProyek) {
        KodeProyek = kodeProyek;
    }

    public  String KodeProyek;

    public EntMyInspectionDetilFix() {
    }


}


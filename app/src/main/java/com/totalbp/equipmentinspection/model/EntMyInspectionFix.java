package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntMyInspectionFix {

    public EntMyInspectionFix(String id, String kodeProyek, String namaProyek, String date, String statusStart, String weekSubGrpUniqueID, String monthSubGrpUniqueID, String jenisInspeksi, String tglInspeksiParam, String tInspScheID) {
        Id = id;
        KodeProyek = kodeProyek;
        NamaProyek = namaProyek;
        Date = date;
        StatusStart = statusStart;
        WeekSubGrpUniqueID = weekSubGrpUniqueID;
        MonthSubGrpUniqueID = monthSubGrpUniqueID;
        JenisInspeksi = jenisInspeksi;
        TglInspeksiParam = tglInspeksiParam;
        TInspScheID = tInspScheID;
    }

    public  String Id;
    public  String KodeProyek;
    public  String NamaProyek;
    public  String Date;
    public  String StatusStart;
    public  String WeekSubGrpUniqueID;
    public  String MonthSubGrpUniqueID;
    public  String JenisInspeksi;

    public String getTInspScheID() {
        return TInspScheID;
    }

    public void setTInspScheID(String TInspScheID) {
        this.TInspScheID = TInspScheID;
    }

    public  String TInspScheID;

    public String getTglInspeksiParam() {
        return TglInspeksiParam;
    }

    public void setTglInspeksiParam(String tglInspeksiParam) {
        TglInspeksiParam = tglInspeksiParam;
    }

    public String TglInspeksiParam;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getKodeProyek() {
        return KodeProyek;
    }

    public void setKodeProyek(String kodeProyek) {
        KodeProyek = kodeProyek;
    }

    public String getNamaProyek() {
        return NamaProyek;
    }

    public void setNamaProyek(String namaProyek) {
        NamaProyek = namaProyek;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatusStart() {
        return StatusStart;
    }

    public void setStatusStart(String statusStart) {
        StatusStart = statusStart;
    }

    public String getWeekSubGrpUniqueID() {
        return WeekSubGrpUniqueID;
    }

    public void setWeekSubGrpUniqueID(String weekSubGrpUniqueID) {
        WeekSubGrpUniqueID = weekSubGrpUniqueID;
    }

    public String getMonthSubGrpUniqueID() {
        return MonthSubGrpUniqueID;
    }

    public void setMonthSubGrpUniqueID(String monthSubGrpUniqueID) {
        MonthSubGrpUniqueID = monthSubGrpUniqueID;
    }

    public String getJenisInspeksi() {
        return JenisInspeksi;
    }

    public void setJenisInspeksi(String jenisInspeksi) {
        JenisInspeksi = jenisInspeksi;
    }

    public EntMyInspectionFix() {
    }




}


package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class CommonEnt {

    public  String Id;
    public  String NamaProyek;
    public  String Date;
    public  String Inspector;
    public  String StatusStart;
    public  String ScheduleStatus;
    public  String Type;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getInspector() {
        return Inspector;
    }

    public void setInspector(String inspector) {
        Inspector = inspector;
    }

    public String getStatusStart() {
        return StatusStart;
    }

    public void setStatusStart(String statusStart) {
        StatusStart = statusStart;
    }

    public String getScheduleStatus() {
        return ScheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        ScheduleStatus = scheduleStatus;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public CommonEnt(String id, String namaProyek, String date, String inspector, String statusStart, String scheduleStatus, String type) {
        Id = id;
        NamaProyek = namaProyek;
        Date = date;
        Inspector = inspector;
        StatusStart = statusStart;
        ScheduleStatus = scheduleStatus;
        Type = type;
    }

    public CommonEnt() {
    }


}


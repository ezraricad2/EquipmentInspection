package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntMyInspectionHead {

    public  String Id;
    public  String Periode;

    public EntMyInspectionHead() {
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPeriode() {
        return Periode;
    }

    public void setPeriode(String periode) {
        Periode = periode;
    }

    public EntMyInspectionHead(String id, String periode) {

        Id = id;
        Periode = periode;
    }
}


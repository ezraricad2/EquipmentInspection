package com.totalbp.equipmentinspection.model;

import java.util.ArrayList;

/**
 * Created by Ezra
 */

public class EntTdInspectionInsert {

    public EntTdInspectionInsert(String IDTdInspection, String id_Th_Inspection, String IDDetilItemInspeksi, String result, String notes, String status, String pengubah) {
        this.IDTdInspection = IDTdInspection;
        Id_Th_Inspection = id_Th_Inspection;
        this.IDDetilItemInspeksi = IDDetilItemInspeksi;
        Result = result;
        Notes = notes;
        Status = status;
        Pengubah = pengubah;
    }

    public String getIDTdInspection() {
        return IDTdInspection;
    }

    public void setIDTdInspection(String IDTdInspection) {
        this.IDTdInspection = IDTdInspection;
    }

    public String getId_Th_Inspection() {
        return Id_Th_Inspection;
    }

    public void setId_Th_Inspection(String id_Th_Inspection) {
        Id_Th_Inspection = id_Th_Inspection;
    }

    public String getIDDetilItemInspeksi() {
        return IDDetilItemInspeksi;
    }

    public void setIDDetilItemInspeksi(String IDDetilItemInspeksi) {
        this.IDDetilItemInspeksi = IDDetilItemInspeksi;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPengubah() {
        return Pengubah;
    }

    public void setPengubah(String pengubah) {
        Pengubah = pengubah;
    }

    public  String IDTdInspection;
    public  String Id_Th_Inspection;
    public  String IDDetilItemInspeksi;
    public  String Result;
    public  String Notes;
    public  String Status;
    public  String Pengubah;

    public ArrayList<EntFileAttachment> getEquipmentDetilDoc() {
        return EquipmentDetilDoc;
    }

    public void setEquipmentDetilDoc(ArrayList<EntFileAttachment> equipmentDetilDoc) {
        EquipmentDetilDoc = equipmentDetilDoc;
    }

    public ArrayList<EntFileAttachment> EquipmentDetilDoc;

    public EntTdInspectionInsert() {
    }

}


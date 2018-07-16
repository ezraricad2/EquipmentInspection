package com.totalbp.equipmentinspection.model;

import java.util.ArrayList;

/**
 * Created by Ezra
 */

public class EntThInspectionInsert {
    public String getIDThInspection() {
        return IDThInspection;
    }

    public void setIDThInspection(String IDThInspection) {
        this.IDThInspection = IDThInspection;
    }

    public String getInspectionNo() {
        return InspectionNo;
    }

    public void setInspectionNo(String inspectionNo) {
        InspectionNo = inspectionNo;
    }

    public String getGrpUniqueID() {
        return GrpUniqueID;
    }

    public void setGrpUniqueID(String grpUniqueID) {
        GrpUniqueID = grpUniqueID;
    }

    public String getAlatInfoUniqueID() {
        return AlatInfoUniqueID;
    }

    public void setAlatInfoUniqueID(String alatInfoUniqueID) {
        AlatInfoUniqueID = alatInfoUniqueID;
    }

    public String getAlatUniqueID() {
        return AlatUniqueID;
    }

    public void setAlatUniqueID(String alatUniqueID) {
        AlatUniqueID = alatUniqueID;
    }

    public String getInspectionType() {
        return InspectionType;
    }

    public void setInspectionType(String inspectionType) {
        InspectionType = inspectionType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPembuat() {
        return Pembuat;
    }

    public void setPembuat(String pembuat) {
        Pembuat = pembuat;
    }

    public String getTanggalBuat() {
        return TanggalBuat;
    }

    public void setTanggalBuat(String tanggalBuat) {
        TanggalBuat = tanggalBuat;
    }

    public String getPengubah() {
        return Pengubah;
    }

    public void setPengubah(String pengubah) {
        Pengubah = pengubah;
    }

    public String getTanggalUbah() {
        return TanggalUbah;
    }

    public void setTanggalUbah(String tanggalUbah) {
        TanggalUbah = tanggalUbah;
    }

    public String getTokenID() {
        return TokenID;
    }

    public void setTokenID(String tokenID) {
        TokenID = tokenID;
    }


    public ArrayList<EntTdInspectionInsert> getEquipmentDetil() {
        return EquipmentDetil;
    }

    public void setEquipmentDetil(ArrayList<EntTdInspectionInsert> InspectionDetail) {
        this.EquipmentDetil = InspectionDetail;
    }


    public  String IDThInspection;
    public  String InspectionNo;
    public String GrpUniqueID;
    public String AlatInfoUniqueID;
    public String AlatUniqueID;
    public String InspectionType;
    public String Status;
    public String Pembuat;
    public String TanggalBuat;
    public String Pengubah;
    public String TanggalUbah;
    public String TokenID;
    public ArrayList<EntTdInspectionInsert> EquipmentDetil;

    public String getTInspScheID() {
        return TInspScheID;
    }

    public void setTInspScheID(String TInspScheID) {
        this.TInspScheID = TInspScheID;
    }

    public String TInspScheID;


    public EntThInspectionInsert() {
    }


}


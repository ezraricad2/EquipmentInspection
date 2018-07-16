package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntGroupAlatDetil {



    public  String ID;
    public  String NamaAlat;
    public  String InventoryNo;
    public  String NamaGroupAlat;
    public  String LastInspect;
    public  String RentDate;
    public String paramSubGrpUniqueID;
    public String paramPeriode;

    public String getAlatInfoUniqueID() {
        return AlatInfoUniqueID;
    }

    public void setAlatInfoUniqueID(String alatInfoUniqueID) {
        AlatInfoUniqueID = alatInfoUniqueID;
    }

    public String AlatInfoUniqueID;

    public String getAlatUniqueID() {
        return AlatUniqueID;
    }

    public void setAlatUniqueID(String alatUniqueID) {
        AlatUniqueID = alatUniqueID;
    }

    public String AlatUniqueID;

    public String getParamSubGrpUniqueID() {
        return paramSubGrpUniqueID;
    }

    public void setParamSubGrpUniqueID(String paramSubGrpUniqueID) {
        this.paramSubGrpUniqueID = paramSubGrpUniqueID;
    }

    public String getParamPeriode() {
        return paramPeriode;
    }

    public void setParamPeriode(String paramPeriode) {
        this.paramPeriode = paramPeriode;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNamaAlat() {
        return NamaAlat;
    }

    public void setNamaAlat(String namaAlat) {
        NamaAlat = namaAlat;
    }

    public String getInventoryNo() {
        return InventoryNo;
    }

    public void setInventoryNo(String inventoryNo) {
        InventoryNo = inventoryNo;
    }

    public String getNamaGroupAlat() {
        return NamaGroupAlat;
    }

    public void setNamaGroupAlat(String namaGroupAlat) {
        NamaGroupAlat = namaGroupAlat;
    }

    public String getLastInspect() {
        return LastInspect;
    }

    public void setLastInspect(String lastInspect) {
        LastInspect = lastInspect;
    }

    public String getRentDate() {
        return RentDate;
    }

    public void setRentDate(String rentDate) {
        RentDate = rentDate;
    }

    public EntGroupAlatDetil(String ID, String namaAlat, String inventoryNo, String namaGroupAlat, String lastInspect, String rentDate, String paramSubGrpUniqueID, String paramPeriode, String AlatUniqueID, String AlatInfoUniqueID) {

        this.ID = ID;
        NamaAlat = namaAlat;
        InventoryNo = inventoryNo;
        NamaGroupAlat = namaGroupAlat;
        LastInspect = lastInspect;
        RentDate = rentDate;
        this.paramSubGrpUniqueID = paramSubGrpUniqueID;
        this.paramPeriode = paramPeriode;
        this.AlatUniqueID = AlatUniqueID;
        this.AlatInfoUniqueID = AlatInfoUniqueID;
    }

    public EntGroupAlatDetil() {
    }




}


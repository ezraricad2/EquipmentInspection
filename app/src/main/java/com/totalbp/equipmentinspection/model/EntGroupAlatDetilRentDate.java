package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntGroupAlatDetilRentDate {



    public  String SubGrpUniqueID;
    public  String RentDate;
    public String AlatUniqueID;


    public EntGroupAlatDetilRentDate() {
    }

    public String getAlatUniqueID() {
        return AlatUniqueID;
    }

    public void setAlatUniqueID(String alatUniqueID) {
        AlatUniqueID = alatUniqueID;
    }

    public EntGroupAlatDetilRentDate(String subGrpUniqueID, String rentDate, String AlatUniqueID) {
        SubGrpUniqueID = subGrpUniqueID;
        RentDate = rentDate;
        this.AlatUniqueID = AlatUniqueID;
    }

    public String getSubGrpUniqueID() {

        return SubGrpUniqueID;
    }

    public void setSubGrpUniqueID(String subGrpUniqueID) {
        SubGrpUniqueID = subGrpUniqueID;
    }

    public String getRentDate() {
        return RentDate;
    }

    public void setRentDate(String rentDate) {
        RentDate = rentDate;
    }
}


package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntGroupAlatDetilNoSeri {


    public EntGroupAlatDetilNoSeri(String subGrpUniqueID, String konten, String AlatUniqueID) {
        SubGrpUniqueID = subGrpUniqueID;
        Konten = konten;
        this.AlatUniqueID = AlatUniqueID;
    }

    public String getSubGrpUniqueID() {
        return SubGrpUniqueID;
    }

    public void setSubGrpUniqueID(String subGrpUniqueID) {
        SubGrpUniqueID = subGrpUniqueID;
    }

    public String getKonten() {
        return Konten;
    }

    public void setKonten(String konten) {
        Konten = konten;
    }

    public  String SubGrpUniqueID;
    public  String Konten;

    public String getAlatUniqueID() {
        return AlatUniqueID;
    }

    public void setAlatUniqueID(String alatUniqueID) {
        AlatUniqueID = alatUniqueID;
    }

    public String AlatUniqueID;

    public EntGroupAlatDetilNoSeri() {
    }


}


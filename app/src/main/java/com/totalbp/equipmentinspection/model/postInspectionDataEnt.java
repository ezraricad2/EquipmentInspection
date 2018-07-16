package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra.R on 20/10/2017.
 */
public class postInspectionDataEnt {

    public String getJenisInspeksi() {
        return JenisInspeksi;
    }

    public void setJenisInspeksi(String jenisInspeksi) {
        JenisInspeksi = jenisInspeksi;
    }

    public postInspectionDataEnt(String kodeProyek, String tglInspeksi, String pembuat, String tanggalBuat, String pengubah, String tanggalUbah, String tokenID, String JenisInspeksi) {
        KodeProyek = kodeProyek;
        TglInspeksi = tglInspeksi;
        Pembuat = pembuat;
        TanggalBuat = tanggalBuat;
        Pengubah = pengubah;

        TanggalUbah = tanggalUbah;
        TokenID = tokenID;
        this.JenisInspeksi = JenisInspeksi;
    }

    public String getKodeProyek() {
        return KodeProyek;
    }

    public void setKodeProyek(String kodeProyek) {
        KodeProyek = kodeProyek;
    }

    public String getTglInspeksi() {
        return TglInspeksi;
    }

    public void setTglInspeksi(String tglInspeksi) {
        TglInspeksi = tglInspeksi;
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

    public String KodeProyek;
    public String TglInspeksi;
    public String Pembuat;
    public String TanggalBuat;
    public String Pengubah;
    public String TanggalUbah;
    public String TokenID;
    public String JenisInspeksi;

    public postInspectionDataEnt() {
    }

}


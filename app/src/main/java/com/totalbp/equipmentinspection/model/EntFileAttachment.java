package com.totalbp.equipmentinspection.model;

/**
 * Created by Ezra
 */

public class EntFileAttachment {


    public String getDocStatus() {
        return DocStatus;
    }

    public void setDocStatus(String docStatus) {
        DocStatus = docStatus;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String docName) {
        DocName = docName;
    }

    public  String IDDoc;
    public  String IDTdInspection;
    public  String UrlAttachment;
    public String DocStatus;
    public String DocName;

    public String getIDDoc() {
        return IDDoc;
    }

    public void setIDDoc(String IDDoc) {
        this.IDDoc = IDDoc;
    }

    public String getIDTdInspection() {
        return IDTdInspection;
    }

    public void setIDTdInspection(String IDTdInspection) {
        this.IDTdInspection = IDTdInspection;
    }

    public String getUrlAttachment() {
        return UrlAttachment;
    }

    public void setUrlAttachment(String urlAttachment) {
        UrlAttachment = urlAttachment;
    }

    public EntFileAttachment(String IDDoc, String IDTdInspection, String urlAttachment, String docStatus, String docName) {

        this.IDDoc = IDDoc;
        this.IDTdInspection = IDTdInspection;
        UrlAttachment = urlAttachment;
        DocStatus = docStatus;
        DocName = docName;
    }

    public EntFileAttachment() {
    }



}


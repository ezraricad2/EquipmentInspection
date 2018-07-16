package com.totalbp.equipmentinspection.config;

/**
 * Created by Ezra.R on 28/07/2017.
 */

public class AppConfig {

//    public static String URL_LOGIN = "http://10.66.1.8:2017/API/Security/RequestToken";
//    public static String URL_LOGIN_OLD = "http://10.66.1.8:81/TBPService/Service.svc/rest/LoginRest";
//    public static final String URL_PAGING = "http://10.66.1.8:81/TBPService/Service.svc/rest/GetDDLRest";
//    public static final String URL_PAGING_RESTFULL = "http://10.66.1.8:81/TBPService/Service.svc/rest/GetPagingRestFull";
//    public static final String URL_IMAGE_PREFIX = "http://10.66.1.8/CISUploads";
//    public static final String URL_PAGING_REST = "http://10.66.1.8:81/TBPService/Service.svc/rest/GetPagingRest";
//    public static final String URL_GETLISTSPN = "http://10.66.1.8:81/TBPService/Service.svc/rest/GetListSpnDetails";
//    public static final String URL_CHECK_TOKEN = "http://10.66.1.8:2017/API/Security/CheckToken";
//    public static final String URL_PROGRESS_SAVE = "http://10.66.1.8:2017/API/QSPV/DailyWork/Progress/Update";
//    public static final String URL_POST_PICTURE = "http://10.66.1.8:2017/API/QSPV/DailyWork/Progress/PostPicture";
//    public static final String URL_UPLOAD = "http://10.66.1.8:2017/API/General/UploadFile";
//    public static final String URL_AKTIFITAS_SAVE = "http://10.66.1.8:2017/API/QSPV/DailyWork/IKP/Passing/Update";
//    public static final String urlProfileFromTBP = "http://10.66.1.8/assets/images/avatar/";


    public static String URL_LOGIN = "REST/API/Security/RequestToken";
    public static String URL_LOGIN_OLD = "TBPService/Service.svc/rest/LoginRest";
    public static final String URL_PAGING_RESTFULL_NEWDLL = "REST/API/General/GetDataDDL";
    public static final String URL_IMAGE_PREFIX = "CISUploads";
    public static final String URL_POST_APPROVAL = "REST/API/General/Approve";
    public static final String URL_POST_REJECT = "REST/API/General/Reject";
    public static final String urlProfileFromTBP = "assets/images/avatar/";
    public static final String URL_CRUD_INSPECTION = "REST/API/AssetManagement/EquipInspeksi/SaveEquipInspeksi";
    public static final String URL_CRUD_INSPECTION_MAIN = "REST/API/AssetManagement/EquipInspeksiMain/SaveEquipInspeksiMain";
    public static final String URL_CRUD_MM_TRANSFER = "REST/API/Transaction/MaterialManagement/Mobile/SaveTransfer";
    public static final String URL_UPLOAD_GAMBAR = "REST/API/General/UploadFile";
    public static final String URL_NEW_APPROVAL = "REST/API/General/Approval/Core";
    public static final String URL_IMAGE_PORTAL = "portal.totalbp.com/Pages/Data-Karyawan.aspx?Id=";
}

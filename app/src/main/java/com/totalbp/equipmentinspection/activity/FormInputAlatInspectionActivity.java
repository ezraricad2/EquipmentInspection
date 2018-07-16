package com.totalbp.equipmentinspection.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.totalbp.equipmentinspection.MainActivity;
import com.totalbp.equipmentinspection.R;
import com.totalbp.equipmentinspection.adapter.FormAlatInspectionAdapter;
import com.totalbp.equipmentinspection.adapter.FormFileAttachmentAdapter;
import com.totalbp.equipmentinspection.config.AppConfig;
import com.totalbp.equipmentinspection.config.FilePath;
import com.totalbp.equipmentinspection.config.JSONParser;
import com.totalbp.equipmentinspection.config.SessionManager;
import com.totalbp.equipmentinspection.controller.MMController;
import com.totalbp.equipmentinspection.interfaces.VolleyCallback;
import com.totalbp.equipmentinspection.model.EntAlatInspection;
import com.totalbp.equipmentinspection.model.EntFileAttachment;
import com.totalbp.equipmentinspection.model.EntGroupAlatDetilNoSeri;
import com.totalbp.equipmentinspection.model.EntMyInspectionDetilFix;
import com.totalbp.equipmentinspection.model.EntMyInspectionFix;
import com.totalbp.equipmentinspection.model.EntSpinnerInspection;
import com.totalbp.equipmentinspection.model.EntTdInspectionInsert;
import com.totalbp.equipmentinspection.model.EntThInspectionInsert;
import com.totalbp.equipmentinspection.model.postInspectionDataEnt;
import com.totalbp.equipmentinspection.utils.DividerItemDecoration;
import com.totalbp.equipmentinspection.utils.MProgressDialog;


import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.totalbp.equipmentinspection.utils.ImageDecoder.decodeSampledBitmapFromFile;
import static com.totalbp.equipmentinspection.utils.ImageDecoder.getStringImage;
import static com.totalbp.equipmentinspection.config.AppConfig.URL_IMAGE_PREFIX;
//import static com.rts.tangerang.config.AppConfig.urlProfileFromTBP;

public class FormInputAlatInspectionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, FormFileAttachmentAdapter.MessageAdapterListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Dialog dialog, dialogG;
    private BottomNavigationView bottomNavigation;
    private View navHeader;
    private int mSelectedItem;
    private long lastBackPressTime = 0;
    private Toast toast;
    private TextView selectProject, projectSelected;
    private TextView tvTowerFloorZona, tvNamaPekerjaan, tvUserNameSideBar, tvEmailSideBar;
    private ImageView imgProfile;
    private Button dBtnDialogSubmit, btnInspeksiSubmit, dBtnDialogSaveDI, dBtnDialogCancelDI;
    JSONParser jsonParser = new JSONParser();
    String typepost;
    public String generalvalue="";

    private static final int request_data_from_project_items  = 99;
    private SessionManager session;
    TextView projectName;
    public String idspk, kodespk, tower, floor, zona, namapekerjaan, namavendor, percentage;
    public  Bundle extras;

    private FloatingActionButton fab,fab1,fab2;
    private TextView tvLabelNewMo, tvLabelFiler, dTitle, dContent, dGTittle, dGContent;

    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private static final int openactivitytoforminputalatinspection  = 53;
    private static final int request_data_from_2  = 21;
    private Boolean isFabOpen = false;

    private SwipeRefreshLayout swipeRefreshLayout;

    public ArrayList<EntFileAttachment> listItems = new ArrayList<>();

    private ActionMode actionMode;
    public Integer currentPage = 1;
    public Integer pageSize = 10;

    public Integer searchActive = 0;

    JSONObject item;

    private MMController controller;

    private static final int request_data_to_confirmation  = 22;
    JSONArray products  = null;
    JSONArray jsonListSPNDummy = null;
    public  ArrayList<EntMyInspectionFix> list = new ArrayList<>();
    public ArrayList<EntMyInspectionDetilFix> list2 = new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    public String MhInspAst, NamaInspeksi,NamaInspeksiDetil, grpUniqueId, altInfoUniqueId, altUniqueId, inspectionType, idDetilItemInspeksi, TdtransID, TipeJawaban, Result, Notes, paramInvNo, paramNoSeri, paramItemName, paramAction, paramNamaGroupItem;
    public FormFileAttachmentAdapter adapter;
    public TextView tvNoInv, tvNoSeri, tvNamaInspeksi, dTittle, namaItemTittle, dTittleDI, tvNamaAlat;
    private Dialog dialogInputInspeksi, dialogInputFileName;
    private EditText etDialogComment, etComment, etDialogCommenDI;
    private ImageView ivDialogInfo, ivCamera, ivAttachment;
    String today;
    public ArrayList<EntTdInspectionInsert> listBeforePostEquipmentDetil = new ArrayList<>();
    private int year, month, day;
    Uri photoURI;
    public String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public String id_daily_log_temp;
    private String savePath, savePathFile;
    private RecyclerView recyclerView;
    public LinearLayout layout_middle2;
    Uri uri;
    public int PDF_REQ_CODE = 2;

    String PdfNameHolder, PdfPathHolder, PdfID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // SugarContext.init(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alat_forminputinspection_activity);

        AllowRunTimePermission();

        controller = new MMController();
        session = new SessionManager(getApplicationContext());

        tvNoSeri = (TextView) findViewById(R.id.tvNoSeri);
        tvNoInv = (TextView) findViewById(R.id.tvNoInv);
        tvNamaInspeksi = (TextView) findViewById(R.id.tvNamaInspeksi);
        tvNamaAlat= (TextView) findViewById(R.id.tvNamaAlat);

        ivDialogInfo = (ImageView) findViewById(R.id.ivDialogInfo);
        namaItemTittle = (TextView) findViewById(R.id.namaItemTittle);
        btnInspeksiSubmit = (Button) findViewById(R.id.btnInspeksiSubmit);
        etComment = (EditText) findViewById(R.id.etComment);
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivAttachment = (ImageView) findViewById(R.id.ivAttachment);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        layout_middle2 = (LinearLayout) findViewById(R.id.layout_middle2);

        ivCamera.setColorFilter(getApplicationContext().getResources().getColor(R.color.icon_gray));
        ivAttachment.setColorFilter(getApplicationContext().getResources().getColor(R.color.icon_gray));
        //ivCamera.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.image_placeholder_background_color), android.graphics.PorterDuff.Mode.MULTIPLY);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        Calendar calendar = new GregorianCalendar();
        today = dateFormat.format(calendar.getTime());

        year = myCalendar.get(Calendar.YEAR);
        month = myCalendar.get(Calendar.MONTH);
        day = myCalendar.get(Calendar.DAY_OF_MONTH);

        Intent intent = getIntent();
        extras = intent.getExtras();
        if(extras!=null){
            if(extras.getString("inspectionType")!=null)
            {

                grpUniqueId = extras.getString("grpUniqueId");
                altInfoUniqueId = extras.getString("altInfoUniqueId");
                altUniqueId = extras.getString("altUniqueId");
                inspectionType = extras.getString("inspectionType");
                idDetilItemInspeksi = extras.getString("idDetilItemInspeksi");
                MhInspAst = extras.getString("MhInspAst");
                NamaInspeksiDetil = extras.getString("NamaInspeksiDetil");
                TdtransID = extras.getString("TdtransID");
                TipeJawaban = extras.getString("TipeJawaban");
                Result = extras.getString("Result");
                Notes = extras.getString("Notes");
                paramItemName = extras.getString("paramItemName");

                NamaInspeksi= extras.getString("NamaInspeksi");
                paramItemName= extras.getString("paramItemName");
                paramNoSeri= extras.getString("paramNoSeri");
                paramInvNo= extras.getString("paramInvNo");
                paramAction = extras.getString("paramAction");
                paramNamaGroupItem = extras.getString("paramNamaGroupItem");

                setTitle(paramNamaGroupItem);
                tvNamaAlat.setText(paramItemName);
                tvNoInv.setText(paramInvNo);
                tvNoSeri.setText(paramNoSeri);
                tvNamaInspeksi.setText(NamaInspeksi);

                if(!NamaInspeksiDetil.equals("null"))
                {
                    namaItemTittle.setText(NamaInspeksiDetil);
                }

                if(!Notes.equals("null")) {
                    etComment.setText(Notes);
                }

                if(paramAction.equals("commentcamera"))
                {
                    layout_middle2.setVisibility(View.GONE);
                    btnInspeksiSubmit.setVisibility(View.GONE);
                }

                Log.d("LogResult",Result);
                Log.d("LogNotes",extras.getString("Notes"));


            }
        }

        //Dialog untuk show keterangan
        dialogInputInspeksi = new Dialog(FormInputAlatInspectionActivity.this);
        dialogInputInspeksi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInputInspeksi.setContentView(R.layout.generalinspectioninput);
        dialogInputInspeksi.setCanceledOnTouchOutside(false);
        dTittle = (TextView) dialogInputInspeksi.findViewById(R.id.dTittle);
        etDialogComment = (EditText) dialogInputInspeksi.findViewById(R.id.etDialogComment);
        dBtnDialogSubmit = (Button) dialogInputInspeksi.findViewById(R.id.dBtnDialogSubmit);

        //Dialog untuk input namafile
        dialogInputFileName = new Dialog(FormInputAlatInspectionActivity.this);
        dialogInputFileName.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInputFileName.setContentView(R.layout.generaldialoginput);
        dialogInputFileName.setCanceledOnTouchOutside(false);
        dTittleDI = (TextView) dialogInputFileName.findViewById(R.id.dTittleDI);
        etDialogCommenDI = (EditText) dialogInputFileName.findViewById(R.id.etDialogCommenDI);
        dBtnDialogSaveDI = (Button) dialogInputFileName.findViewById(R.id.dBtnDialogSaveDI);
        dBtnDialogCancelDI = (Button) dialogInputFileName.findViewById(R.id.dBtnDialogCancelDI);


        adapter = new FormFileAttachmentAdapter(getApplicationContext(), listItems, this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

//        ivDialogInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//
//                dTittle.setText("Spesifikasi");
//
//                dBtnDialogSubmit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialogInputInspeksi.dismiss();
//                    }
//                });
//
//                dialogInputInspeksi.show();
//
//            }
//        });

        btnInspeksiSubmit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if(!etComment.getText().toString().equals(""))
                {
                    String IdTable = "0";
                    if(!TdtransID.equals("null"))
                    {
                        IdTable = TdtransID;
                    }

                    EntTdInspectionInsert entTdInspection = new EntTdInspectionInsert();
                    entTdInspection.setIDTdInspection(IdTable);
                    entTdInspection.setId_Th_Inspection(IdTable);
                    entTdInspection.setIDDetilItemInspeksi(idDetilItemInspeksi);
                    entTdInspection.setResult(Result);
                    entTdInspection.setNotes(etComment.getText().toString());
                    entTdInspection.setStatus("1");
                    entTdInspection.setPengubah(session.getKeyNik());
                    entTdInspection.setEquipmentDetilDoc(listItems);
                    listBeforePostEquipmentDetil.add(entTdInspection);

                    Gson gsonDetail2 = new Gson();
                    EntThInspectionInsert entThInspection = new EntThInspectionInsert();
                    entThInspection.setIDThInspection(IdTable);
                    entThInspection.setInspectionNo("0");
                    entThInspection.setGrpUniqueID(grpUniqueId);
                    entThInspection.setAlatInfoUniqueID(altInfoUniqueId);
                    entThInspection.setAlatUniqueID(altUniqueId);
                    entThInspection.setInspectionType(inspectionType);
                    entThInspection.setStatus("1");
                    entThInspection.setPembuat(session.getKeyNik());
                    entThInspection.setTanggalBuat(today);
                    entThInspection.setPengubah(session.getKeyNik());
                    entThInspection.setTanggalUbah(today);
                    entThInspection.setTokenID(session.getUserToken());
                    entThInspection.setEquipmentDetil(listBeforePostEquipmentDetil);
                    entThInspection.setTInspScheID(session.getTInspScheID());

                    String jsonString = gsonDetail2.toJson(entThInspection);

                    controller.SaveInspectionObject(getApplicationContext(),jsonString,new VolleyCallback(){
                        @Override
                        public void onSuccess(JSONArray result) {
                            MProgressDialog.dismissProgressDialog();
                        }

                        @Override
                        public void onSave(String output) {

                            try {

                                JSONObject obj = new JSONObject(output);
                                Log.d("LogSPNRes",obj.toString());

                                if(obj.getString("IsSucceed").equals("true"))
                                {
                                    MProgressDialog.dismissProgressDialog();
                                    Toast.makeText(getApplicationContext(),"Save Equipment Success!",Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent();
                                    intent.putExtra("", "");
                                    setResult(openactivitytoforminputalatinspection, intent);
                                    finish();
                                }
                                else
                                {
                                    MProgressDialog.dismissProgressDialog();
                                    Toast.makeText(getApplicationContext(),"Save Equipment Fail: "+obj.getString("Message"),Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }
                else
                {
                    Toast toast = Toast.makeText(FormInputAlatInspectionActivity.this,"Komentar diperlukan!", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    etComment.requestFocus();
                }
            }
        });

        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(photoCaptureIntent, requestCode);

                dispatchTakePictureIntent();
            }
        });

        ivAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] mimeTypes = {"application/*","text/plain"};

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                    if (mimeTypes.length > 0) {
                        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                    }
                } else {
                    String mimeTypesStr = "";
                    for (String mimeType : mimeTypes) {
                        mimeTypesStr += mimeType + "|";
                    }
                    intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                }

                startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PDF_REQ_CODE);

            }
        });

        if(!TdtransID.equals("null"))
        {
            SetDataFileAttachment(TdtransID);
        }

    }


    public void AllowRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(FormInputAlatInspectionActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(FormInputAlatInspectionActivity.this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(FormInputAlatInspectionActivity.this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                Log.d("LogFile=", mCurrentPhotoPath.toString());
                photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.totalbp.equipmentinspection.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public void SetDataFileAttachment(String IDTdInspection){
//        swipeRefreshLayout.setRefreshing(true);
        session = new SessionManager(getApplicationContext());

        controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListFileAttachmentMobile",
                "@KodeProject","'" + session.getKodeProyek() + "'",
                "@currentpage","1",
                "@pagesize","10",
                "@sortby","",
                "@wherecond"," where DocStatus = '1' AND IDTdInspection = "+IDTdInspection+" ",
                "@nik",session.isNikUserLoggedIn(),
                "@formid","",
                "@zonaid","",

                new VolleyCallback() {
                    @Override
                    public void onSave(String output) {

                    }

                    @Override
                    public void onSuccess(JSONArray result) {
                        try {
                            Log.d("LogFileAttachment", result.toString());
//                            swipeRefreshLayout.setRefreshing(false);
                            if (result.length() > 0) {
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject item = result.getJSONObject(i);

                                    EntFileAttachment fileEnt = new EntFileAttachment(
                                            item.getString("IDDoc"),item.getString("IDTdInspection"),item.getString("UrlAttachment"), item.getString("DocStatus"), item.getString("DocName")
                                    );

                                    listItems.add(fileEnt);
                                    Log.d("LogIDDoc", item.getString("IDDoc"));
                                    Log.d("LogIDTdInspection", item.getString("IDTdInspection"));
                                    Log.d("LogUrlAttachment", item.getString("UrlAttachment"));

                                }
                                adapter.notifyDataSetChanged();
                            }
                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private  File createImageFile() throws IOException { //bener2 simpen filenya di storage directory
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()){
            if (!storageDir.mkdirs()){
                return null;
            }
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }


    private void displayDialog(String filenameparam) {
        UploadPicture(filenameparam);
        //materialDialog.show();
    }


    public void PdfUploadFunction(String filenameparam) {

        PdfPathHolder = FilePath.getPath(this, uri);
        Log.d("LogFile2=", PdfPathHolder.toString());

        if (PdfPathHolder == null) {

            Toast.makeText(this, "Please move your PDF file to internal storage & try again.", Toast.LENGTH_LONG).show();

        } else {

            try {

                RequestQueue requestQueue;
                session = new SessionManager(getApplicationContext());
                JSONObject request = new JSONObject();

                InputStream inputStream = new FileInputStream(PdfPathHolder);
                byte[] byteArray = IOUtils.toByteArray(inputStream);
                String encodedPdfString = Base64.encodeToString(byteArray, Base64.DEFAULT);
                String filename="file" +"_"+ PdfPathHolder.substring(PdfPathHolder.lastIndexOf("/")+1);
                String fileformat= PdfPathHolder.substring(PdfPathHolder.lastIndexOf(".")+1);

                try {
                    request.put("Pengubah", session.getUserDetails().get("nik"));
                    request.put("FileName", filename);
                    request.put("TokenID", session.getUserToken());
                    request.put("FormID", "AR.03.01");
                    request.put("KodeProyek",session.getKodeProyek()); //Dummy
                    request.put("Base64String",encodedPdfString);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                Log.d("WOI2",request.toString());
//                Log.d("WOI22",encodedPdfString);
//                Log.d("WOI222",fileformat);

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, session.getUrlConfig()+ AppConfig.URL_UPLOAD_GAMBAR,request,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    Log.d("LogUploadPicPenerimaGbr",response.toString());
                                    if(response.getString("IsSucceed").equals("true")){
                                        // Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_LONG).show();
//                                String savePath = response.getString("VarOutput");
                                        String pathDatabase = response.getString("VarOutput"); //hashcode
                                        String yearstring = String.valueOf(year);
                                        String monthstring = "0"+String.valueOf(month+1);
                                        savePathFile = session.getUrlConfig()+URL_IMAGE_PREFIX + "/GeneralFile/"+yearstring.substring(yearstring.length()-2)+"/"+monthstring.substring(monthstring.length()-2)+"/" + response.getString("Message") + "."+fileformat;

                                        appendFileAttach(savePathFile, TdtransID, filenameparam+"."+fileformat);
                                        Log.d("savePathFile",savePathFile);
                                        MProgressDialog.dismissProgressDialog();

                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), response.getString("Message "+"Please Re-Login"), Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error Response " + error.toString(), Toast.LENGTH_LONG).show();
                            }
                        });
                strReq.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(strReq);

            } catch (Exception exception) {

                Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {

            dTittleDI.setText("Save with Name");

            dBtnDialogSaveDI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!etDialogCommenDI.getText().toString().equals("")) {
                        displayDialog(etDialogCommenDI.getText().toString());
                        dialogInputFileName.dismiss();
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Filename Required!",Toast.LENGTH_SHORT).show();
                        etDialogCommenDI.requestFocus();
                    }
                }
            });

            dBtnDialogCancelDI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogInputFileName.dismiss();
                }
            });

            dialogInputFileName.show();

            //Toast.makeText(getBaseContext(), "Photo Taken",Toast.LENGTH_SHORT).show();



//            final Bitmap selectedImage = decodeSampledBitmapFromFile(mCurrentPhotoPath,500, 250);
//            String encodedImage = getStringImage(selectedImage);
//
//            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

            //Start
            //Code By Ezra
            //bitmapnya dideclare ulang terlebih dahulu untuk dibenarkan rotatenya kemudian baru diset ke imageview
//            ExifInterface ei = null;
//            try {
//                ei = new ExifInterface(mCurrentPhotoPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
//                    ExifInterface.ORIENTATION_UNDEFINED);
//
//            Bitmap rotatedBitmap = null;
//            switch(orientation) {
//
//                case ExifInterface.ORIENTATION_ROTATE_90:
//                    rotatedBitmap = rotateImage(decodedByte, 90);
//                    break;
//
//                case ExifInterface.ORIENTATION_ROTATE_180:
//                    rotatedBitmap = rotateImage(decodedByte, 180);
//                    break;
//
//                case ExifInterface.ORIENTATION_ROTATE_270:
//                    rotatedBitmap = rotateImage(decodedByte, 270);
//                    break;
//
//                case ExifInterface.ORIENTATION_NORMAL:
//                default:
//                    rotatedBitmap = decodedByte;
//            }
            //Close
            //imageHolder.setImageBitmap(rotatedBitmap);

        }
        else if (requestCode == PDF_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            dTittleDI.setText("Save with Name");

            dBtnDialogSaveDI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!etDialogCommenDI.getText().toString().equals("")) {
                        uri = data.getData();
                        PdfUploadFunction(etDialogCommenDI.getText().toString());
                        dialogInputFileName.dismiss();
                        Log.d("LogFile=", uri.toString());
                    }
                    else
                    {
                        Toast.makeText(getBaseContext(), "Filename Required!",Toast.LENGTH_SHORT).show();
                        etDialogCommenDI.requestFocus();
                    }
                }
            });

            dBtnDialogCancelDI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogInputFileName.dismiss();
                }
            });

            dialogInputFileName.show();


            //SelectButton.setText("PDF is Selected");
        }

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    private void UploadPicture(String filenameparam){
        final Bitmap selectedImage = decodeSampledBitmapFromFile(mCurrentPhotoPath,500, 500);
        String filename="DP" +"_"+ mCurrentPhotoPath.substring(mCurrentPhotoPath.lastIndexOf("/")+1);
        String encodedImage = getStringImage(selectedImage);

        //Start
        //Code by Ezra
        //Decode terlebih dahulu untuk dibenarkan rotatenya kemudian baru di upload
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        ExifInterface ei = null;
        try {
            ei = new ExifInterface(mCurrentPhotoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap = null;
        switch(orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(decodedByte, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(decodedByte, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(decodedByte, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
            default:
                rotatedBitmap = decodedByte;
        }

        String encodedImage2 = getStringImage(rotatedBitmap);
        //end
        UploadFileToServer(encodedImage2,filename, filenameparam);

    }

    public void UploadFileToServer(String base64encode, String filename, String filenameparam){

        MProgressDialog.showProgressDialog(FormInputAlatInspectionActivity.this, true, new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MProgressDialog.dismissProgressDialog();
            }
        });

        RequestQueue requestQueue;
        session = new SessionManager(getApplicationContext());
        JSONObject request = new JSONObject();

        try {
            request.put("Pengubah", session.getUserDetails().get("nik"));
            request.put("FileName", filename);
            request.put("TokenID", session.getUserToken());
            request.put("FormID", "AR.03.01");
            request.put("KodeProyek",session.getKodeProyek()); //Dummy
            request.put("Base64String",base64encode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String fileformat= filename.substring(filename.lastIndexOf(".")+1);
        Log.d("LogInspectFile",fileformat.toString());
        Log.d("LogInspectParam",request.toString());

        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        http://10.100.223.51:20173/API/TQA/Upload
        JsonObjectRequest strReq = new JsonObjectRequest(Request.Method.POST, session.getUrlConfig()+ AppConfig.URL_UPLOAD_GAMBAR,request,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("LogUploadPicPenerimaGbr",response.toString());
                            if(response.getString("IsSucceed").equals("true")){
                                // Toast.makeText(getApplicationContext(), response.getString("Message"), Toast.LENGTH_LONG).show();
//                                String savePath = response.getString("VarOutput");
                                String pathDatabase = response.getString("VarOutput"); //hashcode
                                String yearstring = String.valueOf(year);
                                String monthstring = "0"+String.valueOf(month+1);
                                savePath = session.getUrlConfig()+URL_IMAGE_PREFIX + "/GeneralFile/"+yearstring.substring(yearstring.length()-2)+"/"+monthstring.substring(monthstring.length()-2)+"/" + response.getString("Message") + "."+fileformat;

                                appendFileAttach(savePath, TdtransID, filenameparam+"."+fileformat);
                                Log.d("savePath",savePath);
                                MProgressDialog.dismissProgressDialog();

                            }
                            else{
                                Toast.makeText(getApplicationContext(), response.getString("Message "+"Please Re-Login"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error Response " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(strReq);
    }

    public void appendFileAttach(String path, String Tdtransid, String filenameparam)
    {
        if(!Tdtransid.equals("null"))
        {
            EntFileAttachment itemEnts2 = new EntFileAttachment(
                    "0", Tdtransid, path, "1",filenameparam
            );
            listItems.add(itemEnts2);
            adapter.notifyDataSetChanged();
        }
        else
        {
                EntFileAttachment itemEnts2 = new EntFileAttachment(
                        "0", "0", path, "1",filenameparam
                );

                Log.d("LogFileAttach",path);
                listItems.add(itemEnts2);
                adapter.notifyDataSetChanged();
        }
    }

    public void UpdateReschedule(String datechoosen, String kodeproyek, String datetglinspeksi)
    {
        Log.d("postDate",datechoosen);

        Gson gsonHeader = new Gson();
        postInspectionDataEnt postInspectionData = new postInspectionDataEnt();
        postInspectionData.setKodeProyek(kodeproyek);
        postInspectionData.setTglInspeksi(datetglinspeksi);
        postInspectionData.setPembuat(session.getKeyNik());
        postInspectionData.setTanggalBuat(datechoosen);
        postInspectionData.setPengubah(session.getKeyNik());
        //param tgl ubah dipakai untuk tgl yang dikirim untuk reschedule tgl
        postInspectionData.setTanggalUbah(datechoosen);
        postInspectionData.setTokenID(session.getUserToken());

        String jsonString = gsonHeader.toJson(postInspectionData);
        Log.d("postSPNFinal",jsonString.toString());

        controller.SaveGeneralObject(getApplicationContext(),jsonString,new VolleyCallback(){
            @Override
            public void onSuccess(JSONArray result) {
                MProgressDialog.dismissProgressDialog();
            }

            @Override
            public void onSave(String output) {

                try {
                    JSONObject obj = new JSONObject(output);
                    Log.d("postInspection",obj.toString());

                    if(obj.getString("IsSucceed").equals("true"))
                    {
                        if(obj.getString("VarOutput").equals("Success"))
                        {
                            Toast toast = Toast.makeText(FormInputAlatInspectionActivity.this,"Reschedule Update Success!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(FormInputAlatInspectionActivity.this,"Reschedule Update Failed : Data Not Found!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    else
                    {
                        //Toast toast = Toast.makeText(MainActivity.this,"Reschedule Failed, "+obj.getString("Message")+" ! ", Toast.LENGTH_LONG);
                        Toast toast = Toast.makeText(FormInputAlatInspectionActivity.this,"Reschedule Update Failed!", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.search_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

//            dGTittle.setText("Konfirmasi Logout");
//            dGContent.setText("Apakah anda ingin keluar Aplikasi ?");
//            dBtnGSubmit.setText("Yakin");
//            dBtnGCancel.setText("Tidak");
//            dBtnGSubmit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogG.dismiss();
//                    session.RemoveSession();
//                    finish();
//                }
//            });
//
//            dBtnGCancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dialogG.dismiss();
//                }
//            });
//
//            dialogG.show();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onIconClicked(int position) {

    }

    @Override
    public void onIconImportantClicked(int position) {

    }

    @Override
    public void onMessageRowClicked(int position) {
        EntFileAttachment message = listItems.get(position);
        listItems.set(position, message);

        String filename = message.getUrlAttachment().toString();
        Log.d("LogFileNameClick",filename);
    }

    @Override
    public void onMessageRowDelClicked(int position) {


        EntFileAttachment message = listItems.get(position);

        EntFileAttachment itemEntsEdit = new EntFileAttachment(
                message.getIDDoc(), message.getIDTdInspection(), message.getUrlAttachment(), "0", "fileedit"
        );
        listItems.set(position,itemEntsEdit);
        adapter.notifyDataSetChanged();
        String filename = message.getUrlAttachment().toString();
        Log.d("LogFileNameDel",filename);
    }

    @Override
    public void onMessageRowDownClicked(int position) {
        EntFileAttachment message = listItems.get(position);
        listItems.set(position, message);

        String filename = message.getUrlAttachment().toString();
        Log.d("LogFileNameDown",filename);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message.getUrlAttachment().toString()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    @Override
    public void onRowLongClicked(int position) {

    }
}

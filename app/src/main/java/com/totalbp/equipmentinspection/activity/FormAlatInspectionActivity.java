package com.totalbp.equipmentinspection.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.totalbp.equipmentinspection.R;
import com.totalbp.equipmentinspection.adapter.FormAlatInspectionAdapter;
import com.totalbp.equipmentinspection.config.JSONParser;
import com.totalbp.equipmentinspection.config.SessionManager;
import com.totalbp.equipmentinspection.controller.MMController;
import com.totalbp.equipmentinspection.interfaces.VolleyCallback;
import com.totalbp.equipmentinspection.model.CommonTowerFormEnt;
import com.totalbp.equipmentinspection.model.EntAlatInspection;
import com.totalbp.equipmentinspection.model.EntMyInspectionDetilFix;
import com.totalbp.equipmentinspection.model.EntMyInspectionFix;
import com.totalbp.equipmentinspection.model.EntSpinnerInspection;
import com.totalbp.equipmentinspection.model.postInspectionDataEnt;
import com.totalbp.equipmentinspection.utils.DividerItemDecoration;
import com.totalbp.equipmentinspection.utils.MProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

//import static com.rts.tangerang.config.AppConfig.urlProfileFromTBP;

public class FormAlatInspectionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,  FormAlatInspectionAdapter.MessageAdapterListener {

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
    private Button dBtnDialogSubmit, btnDialogSparePart;
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

    private static final int request_data_from  = 20;
    private static final int request_data_from_2  = 21;
    private Boolean isFabOpen = false;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public List<EntAlatInspection> listItems = new ArrayList<>();
    public ArrayList<EntSpinnerInspection> listSpinner = new ArrayList<>();

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
    public String paramSubGrpUniqueID, paramPeriode,paramAlatUniqueID,paramAlatInfoUniqueID, paramInvNo, paramNoSeri, paramItemName, MdInspAst, MhInspAst, NamaInspeksi, paramNamaGroupItem;
    public FormAlatInspectionAdapter adapter;
    public TextView tvNoInv, tvNoSeri, tvNamaInspeksi, dTittle, tvNamaAlat;
    private Dialog dialogInputInspeksi;
    private EditText etComment;
    private ImageView ivDialogInfo, ivDialogCamera, ivDialogAttachment;
    private static final int openactivitytoforminputalatinspection  = 53;
    private ArrayList<CommonTowerFormEnt> ddlArrayListTower2 = new ArrayList<>();
    public ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // SugarContext.init(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alat_forminspection_activity);

        controller = new MMController();
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);

        tvNoSeri = (TextView) findViewById(R.id.tvNoSeri);
        tvNoInv = (TextView) findViewById(R.id.tvNoInv);
        tvNamaInspeksi = (TextView) findViewById(R.id.tvNamaInspeksi);
        tvNamaAlat = (TextView) findViewById(R.id.tvNamaAlat);

        //islogin
        Intent intent = getIntent();
        extras = intent.getExtras();
        if(extras!=null){
            if(extras.getString("paramPeriode")!=null)
            {

                MhInspAst = extras.getString("MhInspAst");
                NamaInspeksi = extras.getString("NamaInspeksi");

                MdInspAst = extras.getString("MdInspAst");
                paramPeriode = extras.getString("paramPeriode");
                paramAlatUniqueID = extras.getString("paramAlatUniqueID");
                paramAlatInfoUniqueID = extras.getString("paramAlatInfoUniqueID");
                paramInvNo = extras.getString("paramInvNo");
                paramNoSeri = extras.getString("paramNoSeri");
                paramItemName = extras.getString("paramItemName");
                paramNamaGroupItem = extras.getString("paramNamaGroupItem");
                tvNoInv.setText(paramInvNo);
                tvNoSeri.setText(paramNoSeri);
                tvNamaInspeksi.setText(NamaInspeksi);
                tvNamaAlat.setText(paramItemName);

                setTitle(paramNamaGroupItem);
                Log.d("paramAlatInfoUniqueID",paramAlatInfoUniqueID);
            }
        }


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new FormAlatInspectionAdapter(getApplicationContext(), listItems, listSpinner, this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        //Dialog untuk input text
        dialogInputInspeksi = new Dialog(FormAlatInspectionActivity.this);
        dialogInputInspeksi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogInputInspeksi.setContentView(R.layout.generalinspectioninput);
        dialogInputInspeksi.setCanceledOnTouchOutside(false);
        dTittle = (TextView) dialogInputInspeksi.findViewById(R.id.dTittle);
        etComment = (EditText) dialogInputInspeksi.findViewById(R.id.etComment);
        dBtnDialogSubmit = (Button) dialogInputInspeksi.findViewById(R.id.dBtnDialogSubmit);
        btnDialogSparePart = (Button) dialogInputInspeksi.findViewById(R.id.btnDialogSparePart);
        ivDialogInfo = (ImageView) dialogInputInspeksi.findViewById(R.id.ivDialogInfo);

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        SetDataGroupItem(MdInspAst, paramPeriode);

                    }
                }
        );


    }

    public void SetDataGroupItem(String paramMdInspAst, String paramPeriode){
        swipeRefreshLayout.setRefreshing(true);
        session = new SessionManager(getApplicationContext());

            controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListFormAlatInspectionMobile",
                    "@KodeProject","'" + session.getKodeProyek() + "'",
                    "@currentpage","1",
                    "@pagesize","10",
                    "@sortby","",
                    "@wherecond"," AND MdInspAst.ParentItemID = "+paramMdInspAst+ " AND MdInspAst.TipeInspeksi = "+paramPeriode,
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
                                Log.d("LogAlatInspection", result.toString());
                                swipeRefreshLayout.setRefreshing(false);
                                if (result.length() > 0) {
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject item = result.getJSONObject(i);

                                        EntAlatInspection itemEnts = new EntAlatInspection(
                                                item.getString("ID"),item.getString("GrpUniqueID"),item.getString("SubGrpUniqueID"), item.getString("LevelItem"), item.getString("ParentItemID"), item.getString("TipeInspeksi"), item.getString("NamaItem"), item.getString("MdInspAst"), item.getString("StatusInspeksi"), item.getString("TdtransID"), item.getString("TipeJawaban"), item.getString("Result"), item.getString("ResultDeskripsi"), item.getString("Notes"),""
                                        );
                                        listItems.add(itemEnts);

                                        SetDataTipeJawaban(item.getString("MdInspAst").toString(), item.getString("TipeJawaban").toString());

                                    }
                                    //adapter.notifyDataSetChanged();
                                }
                            }catch (JSONException e){
                                Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });

    }

    public void SetDataTipeJawaban(String MdInspAst, String TipeJawaban){
        swipeRefreshLayout.setRefreshing(true);
        session = new SessionManager(getApplicationContext());

        controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListTipeJawaban",
                "@KodeProject","'" + session.getKodeProyek() + "'",
                "@currentpage","1",
                "@pagesize","10",
                "@sortby"," Value ASC ",
                "@wherecond"," WHERE TipeJawaban = '"+TipeJawaban+"' ",
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
                            Log.d("LogTipeJawaban", result.toString());
                            swipeRefreshLayout.setRefreshing(false);
                            if (result.length() > 0) {
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject item = result.getJSONObject(i);

                                    EntSpinnerInspection itemEnts2 = new EntSpinnerInspection(
                                            MdInspAst,TipeJawaban,item.getString("Deskripsi"),item.getString("Value")
                                    );

                                    listSpinner.add(itemEnts2);
                                    Log.d("LogTipeJawaban1", MdInspAst);
                                    Log.d("LogTipeJawaban2", TipeJawaban);
                                    Log.d("LogTipeJawaban3", item.getString("Deskripsi"));
                                    Log.d("LogTipeJawaban4", item.getString("Value"));

                                }
                                adapter.notifyDataSetChanged();
                            }
                        }catch (JSONException e){
                            Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);


        if( requestCode == openactivitytoforminputalatinspection ) {
            if (data != null){
                listItems.clear();
                listSpinner.clear();
                SetDataGroupItem(MdInspAst, paramPeriode);
            }
        }
//        else if( requestCode == request_data_from_2 ) {
//            if (data != null){}
//        }

    }

    public void getDataForDDL2(){
    }

    public void showDialog() {
        pDialog.setMessage("Loading ...");
        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
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
                            Toast toast = Toast.makeText(FormAlatInspectionActivity.this,"Reschedule Update Success!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(FormAlatInspectionActivity.this,"Reschedule Update Failed : Data Not Found!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    else
                    {
                        //Toast toast = Toast.makeText(MainActivity.this,"Reschedule Failed, "+obj.getString("Message")+" ! ", Toast.LENGTH_LONG);
                        Toast toast = Toast.makeText(FormAlatInspectionActivity.this,"Reschedule Update Failed!", Toast.LENGTH_LONG);
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
        listItems.clear();
        listSpinner.clear();
        SetDataGroupItem(MdInspAst, paramPeriode);
    }


    @Override
    public void onIconClicked(int position) {

    }

    @Override
    public void onIconImportantClicked(int position) {

    }



    @Override
    public void onMessageRowClicked(int position, String action) {

        EntAlatInspection message = listItems.get(position);
        listItems.set(position, message);

        String grpUniqueId = message.getGrpUniqueID().toString();
        String altInfoUniqueId = paramAlatInfoUniqueID;
        String altUniqueId = paramAlatUniqueID;
        String inspectionType = message.getTipeInspeksi().toString();
        String idDetilItemInspeksi = message.getMdInspAst().toString();
        String MhInspAst = message.getID().toString();
        String NamaInspeksiDetil = message.getNamaItem().toString();
        String TdtransID = message.getTdtransID().toString();
        String TipeJawaban = message.getTipeJawaban().toString();
        String notes = message.getNotes().toString();

        Log.d("LogInsAction",action);


        Intent intent = new Intent(FormAlatInspectionActivity.this, FormInputAlatInspectionActivity.class);
        intent.putExtra("grpUniqueId", grpUniqueId);
        intent.putExtra("altInfoUniqueId", altInfoUniqueId);
        intent.putExtra("altUniqueId", altUniqueId);
        intent.putExtra("inspectionType", inspectionType);
        intent.putExtra("idDetilItemInspeksi", idDetilItemInspeksi);
        intent.putExtra("MhInspAst", MhInspAst);
        intent.putExtra("NamaInspeksiDetil", NamaInspeksiDetil);
        intent.putExtra("TdtransID",TdtransID);
        intent.putExtra("TipeJawaban", TipeJawaban);
        intent.putExtra("Result", action);
        intent.putExtra("Notes", notes);
        intent.putExtra("paramInvNo",paramInvNo);
        intent.putExtra("paramNoSeri",paramNoSeri);
        intent.putExtra("NamaInspeksi",NamaInspeksi);
        intent.putExtra("paramItemName",paramItemName);
        intent.putExtra("paramAction","");
        intent.putExtra("paramNamaGroupItem",paramNamaGroupItem);


        Log.d("LoggrpUniqueId",grpUniqueId);
        Log.d("LogaltInfoUniqueId",altInfoUniqueId);
        Log.d("LogaltUniqueId",altUniqueId);
        Log.d("LoginspectionType",inspectionType);
        Log.d("LogidDetilItemInspeksi",idDetilItemInspeksi);
        Log.d("LogMhInspAst",MhInspAst);
        Log.d("LogNamaInspeksi",NamaInspeksi);
        Log.d("LogTdtransID",TdtransID);
        Log.d("LogTipeJawaban",TipeJawaban);
        Log.d("LogResult",action);

        startActivityForResult(intent, openactivitytoforminputalatinspection);


    }

    @Override
    public void onMessageCommentClicked(int position, String action) {

        EntAlatInspection message = listItems.get(position);
        listItems.set(position, message);

        String grpUniqueId = message.getGrpUniqueID().toString();
        String altInfoUniqueId = paramAlatInfoUniqueID;
        String altUniqueId = paramAlatUniqueID;
        String inspectionType = message.getTipeInspeksi().toString();
        String idDetilItemInspeksi = message.getMdInspAst().toString();
        String MhInspAst = message.getID().toString();
        String NamaInspeksiDetil = message.getNamaItem().toString();
        String TdtransID = message.getTdtransID().toString();
        String TipeJawaban = message.getTipeJawaban().toString();
        String notes = message.getNotes().toString();

        Intent intent = new Intent(FormAlatInspectionActivity.this, FormInputAlatInspectionActivity.class);
        intent.putExtra("grpUniqueId", grpUniqueId);
        intent.putExtra("altInfoUniqueId", altInfoUniqueId);
        intent.putExtra("altUniqueId", altUniqueId);
        intent.putExtra("inspectionType", inspectionType);
        intent.putExtra("idDetilItemInspeksi", idDetilItemInspeksi);
        intent.putExtra("MhInspAst", MhInspAst);
        intent.putExtra("NamaInspeksiDetil", NamaInspeksiDetil);
        intent.putExtra("TdtransID",TdtransID);
        intent.putExtra("TipeJawaban", TipeJawaban);
        intent.putExtra("Result", "");
        intent.putExtra("Notes", notes);
        intent.putExtra("paramInvNo",paramInvNo);
        intent.putExtra("paramNoSeri",paramNoSeri);
        intent.putExtra("NamaInspeksi",NamaInspeksi);
        intent.putExtra("paramItemName",paramItemName);
        intent.putExtra("paramAction","commentcamera");

        startActivityForResult(intent, openactivitytoforminputalatinspection);

    }

    @Override
    public void onMessageCameraClicked(int position, String action) {
        EntAlatInspection message = listItems.get(position);
        listItems.set(position, message);

        String grpUniqueId = message.getGrpUniqueID().toString();
        String altInfoUniqueId = paramAlatInfoUniqueID;
        String altUniqueId = paramAlatUniqueID;
        String inspectionType = message.getTipeInspeksi().toString();
        String idDetilItemInspeksi = message.getMdInspAst().toString();
        String MhInspAst = message.getID().toString();
        String NamaInspeksiDetil = message.getNamaItem().toString();
        String TdtransID = message.getTdtransID().toString();
        String TipeJawaban = message.getTipeJawaban().toString();
        String notes = message.getNotes().toString();

        Intent intent = new Intent(FormAlatInspectionActivity.this, FormInputAlatInspectionActivity.class);
        intent.putExtra("grpUniqueId", grpUniqueId);
        intent.putExtra("altInfoUniqueId", altInfoUniqueId);
        intent.putExtra("altUniqueId", altUniqueId);
        intent.putExtra("inspectionType", inspectionType);
        intent.putExtra("idDetilItemInspeksi", idDetilItemInspeksi);
        intent.putExtra("MhInspAst", MhInspAst);
        intent.putExtra("NamaInspeksiDetil", NamaInspeksiDetil);
        intent.putExtra("TdtransID",TdtransID);
        intent.putExtra("TipeJawaban", TipeJawaban);
        intent.putExtra("Result", "");
        intent.putExtra("Notes", notes);
        intent.putExtra("paramInvNo",paramInvNo);
        intent.putExtra("paramNoSeri",paramNoSeri);
        intent.putExtra("NamaInspeksi",NamaInspeksi);
        intent.putExtra("paramItemName",paramItemName);
        intent.putExtra("paramAction","commentcamera");

        startActivityForResult(intent, openactivitytoforminputalatinspection);
    }

    @Override
    public void onRowLongClicked(int position) {

    }
}

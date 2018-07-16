package com.totalbp.equipmentinspection.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
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
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.totalbp.equipmentinspection.R;
import com.totalbp.equipmentinspection.adapter.AlatInspectionAdapter;
import com.totalbp.equipmentinspection.config.JSONParser;
import com.totalbp.equipmentinspection.config.SessionManager;
import com.totalbp.equipmentinspection.controller.MMController;
import com.totalbp.equipmentinspection.interfaces.VolleyCallback;
import com.totalbp.equipmentinspection.model.EntAlatInspection;
import com.totalbp.equipmentinspection.model.EntGroupAlatDetil;
import com.totalbp.equipmentinspection.model.EntGroupAlatDetilRentDate;
import com.totalbp.equipmentinspection.model.EntMyInspectionDetilFix;
import com.totalbp.equipmentinspection.model.EntMyInspectionFix;
import com.totalbp.equipmentinspection.model.EntMyInspectionHead;
import com.totalbp.equipmentinspection.model.EntMyItemInspectionQty;
import com.totalbp.equipmentinspection.model.postInspectionDataEnt;
import com.totalbp.equipmentinspection.utils.DividerItemDecoration;
import com.totalbp.equipmentinspection.utils.MProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

//import static com.rts.tangerang.config.AppConfig.urlProfileFromTBP;

public class AlatInspectionActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,  AlatInspectionAdapter.MessageAdapterListener {

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
    private Button btnDialogInputCancel, dBtnGSubmit, dBtnGCancel;
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
    public List<EntMyItemInspectionQty> listItemsQty = new ArrayList<>();
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
    public String paramSubGrpUniqueID, paramPeriode, paramInvNo, paramNoSeri, paramItemName, paramAlatUniqueID, paramNamaGroupItem, paramAlatInfoUniqueID;
    public AlatInspectionAdapter adapter;
    public TextView tvNoInv, tvNoSeri, tvNamaAlat;
    private static final int openactivitytoformalatinspection  = 52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // SugarContext.init(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alat_inspection_activity);

        controller = new MMController();
        session = new SessionManager(getApplicationContext());

        tvNoSeri = (TextView) findViewById(R.id.tvNoSeri);
        tvNoInv = (TextView) findViewById(R.id.tvNoInv);
        tvNamaAlat =(TextView) findViewById(R.id.tvNamaAlat);

        //islogin
        Intent intent = getIntent();
        extras = intent.getExtras();
        if(extras!=null){
            if(extras.getString("paramPeriode")!=null)
            {
                paramSubGrpUniqueID = extras.getString("paramSubGrpUniqeID");
                paramPeriode = extras.getString("paramPeriode");
                paramInvNo =  extras.getString("paramInvNo");
                paramNoSeri = extras.getString("paramNoSeri");
                paramItemName = extras.getString("paramItemName");
                paramAlatUniqueID = extras.getString("paramAlatUniqueID");
                paramAlatInfoUniqueID = extras.getString("paramAlatInfoUniqueID");
                paramNamaGroupItem = extras.getString("paramNamaGroupItem");

                tvNoInv.setText(paramInvNo);
                tvNoSeri.setText(paramNoSeri);
                tvNamaAlat.setText(paramItemName);
                setTitle(paramNamaGroupItem);

                Log.d("paramSubGrpUniqeID",extras.getString("paramSubGrpUniqeID"));
                Log.d("paramInvNo",extras.getString("paramInvNo"));
                Log.d("paramPeriode",extras.getString("paramPeriode"));
                Log.d("paramNoSeri",extras.getString("paramNoSeri"));
                Log.d("paramItemName",extras.getString("paramItemName"));
            }
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new AlatInspectionAdapter(getApplicationContext(), listItems, listItemsQty, this);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        SetDataGroupItem(paramSubGrpUniqueID, paramPeriode);
                    }
                }
        );


    }

    public void SetDataGroupItem(String paramSubGrpUniqueID, String paramPeriode){
        swipeRefreshLayout.setRefreshing(true);
        session = new SessionManager(getApplicationContext());

            controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListAlatInspectionMobile",
                    "@KodeProject","'" + session.getKodeProyek() + "'",
                    "@currentpage","1",
                    "@pagesize","10",
                    "@sortby","",
                    "@wherecond"," AND MhInspAst.SubGrpUniqueID = "+paramSubGrpUniqueID+ " AND MdInspAst.TipeInspeksi = "+paramPeriode,
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
                                                item.getString("ID"),item.getString("GrpUniqueID"),item.getString("SubGrpUniqueID"), item.getString("LevelItem"), item.getString("ParentItemID"), item.getString("TipeInspeksi"), item.getString("NamaItem"), item.getString("MdInspAst"),"","","","","","", item.getString("TotalIns")
                                        );
                                        listItems.add(itemEnts);

                                        SetQtyItemInspect(item.getString("MdInspAst"));

                                    }
                                    //adapter.notifyDataSetChanged();
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


//        if( requestCode == request_data_from ) {
//            if (data != null){
//                listItems.clear();
//            }
//        }
//        else if( requestCode == request_data_from_2 ) {
//            if (data != null){}
//        }

    }

    public void SetQtyItemInspect(String paramParentID){
        swipeRefreshLayout.setRefreshing(true);
        session = new SessionManager(getApplicationContext());
        String date = null;

        //JIKA Untuk PO dan Kontrak (date = "")
        if(date != "") {
            controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetQtyAlatInspectionMobile",
                    "@KodeProject","'" + session.getKodeProyek() + "'",
                    "@currentpage","1",
                    "@pagesize","10",
                    "@sortby","",
                    "@wherecond"," AND MdInsp.ParentItemId = "+paramParentID,
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
                                swipeRefreshLayout.setRefreshing(false);
                                Log.d("LogItemDetil", result.toString());
                                if (result.length() > 0) {
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject item = result.getJSONObject(i);

                                        EntMyItemInspectionQty itemEnts = new EntMyItemInspectionQty(
                                                item.getString("ParentItemID"),item.getString("TotalResult")
                                        );
                                        listItemsQty.add(itemEnts);
                                    }

                                }
                                adapter.notifyDataSetChanged();
                            }catch (JSONException e){
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
                            Toast toast = Toast.makeText(AlatInspectionActivity.this,"Reschedule Update Success!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(AlatInspectionActivity.this,"Reschedule Update Failed : Data Not Found!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    else
                    {
                        //Toast toast = Toast.makeText(MainActivity.this,"Reschedule Failed, "+obj.getString("Message")+" ! ", Toast.LENGTH_LONG);
                        Toast toast = Toast.makeText(AlatInspectionActivity.this,"Reschedule Update Failed!", Toast.LENGTH_LONG);
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

        EntAlatInspection message = listItems.get(position);
        listItems.set(position, message);

//        Toast toast = Toast.makeText(AlatInspectionActivity.this,"Toast Inspection Id : "+message.getMdInspAst().toString(), Toast.LENGTH_LONG);
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.show();

        //Log.d("LogInspectionId", message.getMdInspAst().toString());
        Intent intent = new Intent(AlatInspectionActivity.this, FormAlatInspectionActivity.class);
        intent.putExtra("MhInspAst", message.getID().toString());
        intent.putExtra("NamaInspeksi", message.getNamaItem().toString());
        intent.putExtra("MdInspAst", message.getMdInspAst().toString());
        intent.putExtra("paramPeriode", message.getTipeInspeksi().toString());
        intent.putExtra("paramAlatUniqueID", paramAlatUniqueID);
        intent.putExtra("paramAlatInfoUniqueID", paramAlatInfoUniqueID);
        intent.putExtra("paramNoSeri", paramNoSeri);
        intent.putExtra("paramInvNo", paramInvNo);
        intent.putExtra("paramItemName", paramItemName);
        intent.putExtra("paramNamaGroupItem", paramNamaGroupItem);

        startActivityForResult(intent, openactivitytoformalatinspection);

    }

    @Override
    public void onRowLongClicked(int position) {

    }
}

package com.totalbp.equipmentinspection.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
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
import com.totalbp.equipmentinspection.config.JSONParser;
import com.totalbp.equipmentinspection.config.SessionManager;
import com.totalbp.equipmentinspection.controller.MMController;
import com.totalbp.equipmentinspection.interfaces.VolleyCallback;
import com.totalbp.equipmentinspection.model.EntGroupAlatDetil;
import com.totalbp.equipmentinspection.model.EntGroupAlatDetilNoSeri;
import com.totalbp.equipmentinspection.model.EntGroupAlatDetilRentDate;
import com.totalbp.equipmentinspection.model.EntMyInspectionDetilFix;
import com.totalbp.equipmentinspection.model.EntMyInspectionFix;
import com.totalbp.equipmentinspection.model.postInspectionDataEnt;
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

public class AlatGroupActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

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

    public List<EntGroupAlatDetil> listItem = new ArrayList<>();

    private ActionMode actionMode;
    public Integer currentPage = 1;
    public Integer pageSize = 10;

    public Integer searchActive = 0;

    JSONObject item;

    private MMController controller;

    private static final int request_data_to_confirmation  = 22;
    private SectionedRecyclerViewAdapter sectionAdapter;
    JSONArray products  = null;
    JSONArray jsonListSPNDummy = null;
    public  ArrayList<EntMyInspectionFix> list = new ArrayList<>();
    public ArrayList<EntMyInspectionDetilFix> list2 = new ArrayList<>();
    Calendar myCalendar = Calendar.getInstance();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    public String paramSubGrpUniqueID, paramPeriode;
    ArrayList<EntGroupAlatDetilRentDate> listItemRentDate = new ArrayList<>();
    ArrayList<EntGroupAlatDetilNoSeri> listItemNoSeri = new ArrayList<>();
    private static final int openactivitytoalatinspection  = 51;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // SugarContext.init(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alat_group_activity);

        controller = new MMController();
        session = new SessionManager(getApplicationContext());

        //islogin
        Intent intent = getIntent();
        extras = intent.getExtras();
        if(extras!=null){
            if(extras.getString("paramPeriode")!=null)
            {
                paramSubGrpUniqueID = extras.getString("paramSubGrpUniqueID");
                paramPeriode = extras.getString("paramPeriode");

                setTitle(session.getNamaProyek());
                Log.d("LogSubGrpUniqueID",extras.getString("paramSubGrpUniqueID"));
                Log.d("LogPeriode",extras.getString("paramPeriode"));
            }
        }

        //Start Recycler View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        sectionAdapter = new SectionedRecyclerViewAdapter();
        //sectionAdapter.addSection(new NewsSection(NewsSection.WORLD));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(sectionAdapter);

        SetDataGroupItem(paramSubGrpUniqueID, paramPeriode);
    }

    public ArrayList SetDataGroupItem(String paramSubGrpUniqueID, String paramPeriode){
        swipeRefreshLayout.setRefreshing(true);
        session = new SessionManager(getApplicationContext());
        String date = null;

        //JIKA Untuk PO dan Kontrak (date = "")
        if(date != "") {
            controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListGroupAlatMobile",
                    "@KodeProject","'" + session.getKodeProyek() + "'",
                    "@currentpage","1",
                    "@pagesize","10",
                    "@sortby","",
                    "@wherecond"," AND TInsp.JenisInspeksi = "+paramPeriode+" AND MdSubGrp.SubGrpUniqueID = "+paramSubGrpUniqueID,
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
                                Log.d("LogInsection", result.toString());
                                if (result.length() > 0) {
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject item = result.getJSONObject(i);

                                        sectionAdapter.addSection(new NewsSection(NewsSection.WORLD, item.getString("NamaGroupAlat")+'('+item.getString("QtyGroupAlat")+')', item.getString("SubGrpUniqueID"), paramPeriode));
                                    }
                                }
                            }catch (JSONException e){
                                swipeRefreshLayout.setRefreshing(false);
                                Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        return list;

    }

    private class NewsSection extends StatelessSection {

        final static int WORLD = 0;
        final static int BUSINESS = 1;
        final static int TECHNOLOGY = 2;
        final static int SPORTS = 3;
        final static int SPORTS2 = 4;
        final static int NPO = 5;

        final int topic;

        String title;

        ArrayList<EntGroupAlatDetil> listItem = new ArrayList<>();
        int imgPlaceholderResId;
        boolean expanded = true;

        NewsSection(int topic, String string, String subGrpUniqueID, String paramPeriode) {
            super(new SectionParameters.Builder(R.layout.alat_group_row_activity)
                    .headerResourceId(R.layout.alat_group_head_activity)
                    //.footerResourceId(R.layout.fragment_upcoming_orders_row_footer)
                    .build());

            this.topic = topic;
            switch (topic) {
                case WORLD:
                    this.title = string;
                    this.listItem = SetDataGroupAlatDetil(subGrpUniqueID, paramPeriode);
                    this.imgPlaceholderResId = R.drawable.ic_spn;
                    break;
            }
        }

        private List<String> getNews(int arrayResource) {
            return new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayResource)));
        }

        public ArrayList SetDataGroupAlatDetil(String paramSubGrpUniqueID, String paramPeriode){
            swipeRefreshLayout.setRefreshing(true);
            session = new SessionManager(getApplicationContext());
            String date = null;

            //JIKA Untuk PO dan Kontrak (date = "")
            if(date != "") {
                controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListGroupDetilAlatMobile",
                        "@KodeProject","'" + session.getKodeProyek() + "'",
                        "@currentpage","1",
                        "@pagesize","10",
                        "@sortby","",
                        "@wherecond"," AND TInsp.JenisInspeksi = "+paramPeriode+" AND MdSubGrp.SubGrpUniqueID = "+paramSubGrpUniqueID,
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

                                            EntGroupAlatDetil itemEnts = new EntGroupAlatDetil(
                                                    //Ambil paramSubGrpUniqueID dari atas
                                                    paramSubGrpUniqueID,item.getString("NamaAlat"),item.getString("InventoryNo"),item.getString("NamaGroupAlat"), item.getString("LastInspect"), item.getString("RentDate"), paramSubGrpUniqueID, paramPeriode, item.getString("AlatUniqueID"), item.getString("AlatInfoUniqueID")
                                            );
                                            listItem.add(itemEnts);

                                            SetRentDate(item.getString("AlatUniqueID"));
                                            SetNoSeriDate(item.getString("AlatUniqueID"), paramPeriode);
                                        }
                                        sectionAdapter.notifyDataSetChanged();
                                    }
                                }catch (JSONException e){
                                    Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            return list;

        }

        public void SetRentDate(String AlatUniqueID){
            swipeRefreshLayout.setRefreshing(true);
            session = new SessionManager(getApplicationContext());
            String date = null;

            //JIKA Untuk PO dan Kontrak (date = "")
            if(date != "") {
                controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListGroupDetilAlatRentDateMobile",
                        "@KodeProject","'" + session.getKodeProyek() + "'",
                        "@currentpage","1",
                        "@pagesize","10",
                        "@sortby","",
                        "@wherecond"," and InventoryAssetUNiqueId = "+AlatUniqueID+" and F_Stock = 1",
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
                                    Log.d("LogItemDetilRentDate", result.toString());
                                    if (result.length() > 0) {
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject item = result.getJSONObject(i);

                                            EntGroupAlatDetilRentDate itemEnts = new EntGroupAlatDetilRentDate(
                                                    item.getString("SubGrpUniqueID"),item.getString("RentDate"), item.getString("AlatUniqueID")
                                            );
                                            listItemRentDate.add(itemEnts);
                                        }
                                        sectionAdapter.notifyDataSetChanged();
                                    }
                                }catch (JSONException e){
                                    swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        }

        public void SetNoSeriDate(String paramAlatUniqueID, String paramPeriode){
            swipeRefreshLayout.setRefreshing(true);
            listItemNoSeri.clear();
            session = new SessionManager(getApplicationContext());
            String date = null;

            //JIKA Untuk PO dan Kontrak (date = "")
            if(date != "") {
                controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetNoSeriAlatInspectionMobile",
                        "@KodeProject","'" + session.getKodeProyek() + "'",
                        "@currentpage","1",
                        "@pagesize","10",
                        "@sortby","",
                        "@wherecond"," AND TInsp.JenisInspeksi = "+paramPeriode+" AND TInsp.AlatUniqueID = "+paramAlatUniqueID+" AND MdAltExt.AlatUniqueID = "+paramAlatUniqueID+" ",
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
                                    Log.d("LogItemNoSeri", result.toString());
                                    if (result.length() > 0) {
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject item = result.getJSONObject(i);

                                            EntGroupAlatDetilNoSeri itemEnts = new EntGroupAlatDetilNoSeri(
                                                    item.getString("SubGrpUniqueID"),item.getString("Konten"),item.getString("AlatUniqueID")
                                            );
                                            listItemNoSeri.add(itemEnts);
                                        }
                                        sectionAdapter.notifyDataSetChanged();
                                    }
                                }catch (JSONException e){
                                    swipeRefreshLayout.setRefreshing(false);
                                    Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }

        }

        @Override
        public int getContentItemsTotal() {
            return expanded? listItem.size() : 0;
//            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

//            btnViewHistory = (Button) view.findViewById(R.id.btnViewHistory);
//            btnInspect = (Button) view.findViewById(R.id.btnInspect);

            itemHolder.tvNamaItem.setText(listItem.get(position).getNamaAlat());
            if(!listItem.get(position).getLastInspect().toString().equals("null")) {
                itemHolder.tvLastInspect.setText(listItem.get(position).getLastInspect());
            }

            itemHolder.tvNoInv.setText(listItem.get(position).getInventoryNo());
//
//            StringBuilder builder = new StringBuilder();
            for (EntGroupAlatDetilRentDate details : listItemRentDate) {
                if(details.getAlatUniqueID().toString().equals(listItem.get(position).getAlatUniqueID().toString()))
                {
                    itemHolder.tvRentSince.setText(details.getRentDate());
                }
            }

//            for (EntGroupAlatDetilNoSeri details2 : listItemNoSeri) {
//                if(details2.getAlatUniqueID().toString().equals(listItem.get(position).getAlatUniqueID().toString()))
//                {
//                    itemHolder.tvNoSeri.setText(listItemNoSeri.get(position).getKonten());
//                }
//            }


            itemHolder.btnInspect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {

                    //Log.d("LogParamSubGrpUniqueID",listItem.get(position).getParamSubGrpUniqueID());
                    Intent intent = new Intent(AlatGroupActivity.this, AlatInspectionActivity.class);
                    intent.putExtra("paramSubGrpUniqeID", listItem.get(position).getParamSubGrpUniqueID().toString());
                    intent.putExtra("paramInvNo", listItem.get(position).getInventoryNo().toString());
                    intent.putExtra("paramPeriode", listItem.get(position).getParamPeriode().toString());
                    intent.putExtra("paramItemName", listItem.get(position).getNamaAlat().toString());
                    intent.putExtra("paramNamaGroupItem", listItem.get(position).getNamaGroupAlat().toString());
                    for (EntGroupAlatDetilNoSeri details2 : listItemNoSeri) {
                        if(details2.getAlatUniqueID().toString().equals(listItem.get(position).getAlatUniqueID().toString()))
                        {
                            intent.putExtra("paramNoSeri", listItemNoSeri.get(position).getKonten().toString());
                        }
                    }
                    intent.putExtra("paramAlatUniqueID", listItem.get(position).getAlatUniqueID().toString());
                    intent.putExtra("paramAlatInfoUniqueID", listItem.get(position).getAlatInfoUniqueID().toString());



                    startActivityForResult(intent, openactivitytoalatinspection);
                }
            });

        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);

            headerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expanded = !expanded;
                    headerHolder.imgArrow.setImageResource(
                            expanded ? R.drawable.ic_keyboard_arrow_up_black_18dp : R.drawable.ic_keyboard_arrow_down_black_18dp
                    );
                    sectionAdapter.notifyDataSetChanged();
                }
            });

        }

        @Override
        public RecyclerView.ViewHolder getFooterViewHolder(View view) {
            return new FooterViewHolder(view);
        }

        @Override
        public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
            FooterViewHolder footerHolder = (FooterViewHolder) holder;

            footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), String.format("Clicked on footer of Section %s", title), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final View rootView;
        private final ImageView imgArrow;

        HeaderViewHolder(View view) {
            super(view);
            rootView = view;
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            imgArrow = (ImageView) view.findViewById(R.id.imgArrow);
        }
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;

        FooterViewHolder(View view) {
            super(view);

            rootView = view;
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout rootView;
        private final TextView tvNamaItem;
        private final TextView tvLastInspect;
        private final TextView tvRentSince;
        private final TextView tvNoInv;
        private final TextView tvViewHistory;
        private final Button btnInspect;
        private final TextView tvNoSeri;

        ItemViewHolder(View view) {
            super(view);

            rootView = (RelativeLayout) view.findViewById(R.id.rootView);
            tvNamaItem = (TextView) view.findViewById(R.id.tvFileName);
            tvLastInspect = (TextView) view.findViewById(R.id.tvInspectionStatus);
            tvRentSince = (TextView) view.findViewById(R.id.tvRentSince);
            tvNoSeri = (TextView) view.findViewById(R.id.tvNoSeri);
            tvNoInv = (TextView) view.findViewById(R.id.tvNoInv);
            tvViewHistory = (TextView) view.findViewById(R.id.tvViewHistory);
            btnInspect = (Button) view.findViewById(R.id.btnInspect);
        }
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
                            Toast toast = Toast.makeText(AlatGroupActivity.this,"Reschedule Update Success!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(AlatGroupActivity.this,"Reschedule Update Failed : Data Not Found!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    else
                    {
                        //Toast toast = Toast.makeText(MainActivity.this,"Reschedule Failed, "+obj.getString("Message")+" ! ", Toast.LENGTH_LONG);
                        Toast toast = Toast.makeText(AlatGroupActivity.this,"Reschedule Update Failed!", Toast.LENGTH_LONG);
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
//        else if(id == R.id.home)
//        {
//            Toast.makeText(getApplicationContext(),"Back button clicked", Toast.LENGTH_SHORT).show();
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("LogInspection","OnResume");

    }
}

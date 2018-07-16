package com.totalbp.equipmentinspection;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.totalbp.equipmentinspection.activity.AlatGroupActivity;
import com.totalbp.equipmentinspection.config.JSONParser;
import com.totalbp.equipmentinspection.config.SessionManager;
import com.totalbp.equipmentinspection.controller.MMController;
import com.totalbp.equipmentinspection.interfaces.VolleyCallback;
import com.totalbp.equipmentinspection.model.EntMyInspectionDetilFix;
import com.totalbp.equipmentinspection.model.EntMyInspectionFix;
import com.totalbp.equipmentinspection.model.EntMyInspectionHead;
import com.totalbp.equipmentinspection.model.postInspectionDataEnt;
import com.totalbp.equipmentinspection.utils.MProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

import static com.totalbp.equipmentinspection.config.AppConfig.urlProfileFromTBP;

//import static com.rts.tangerang.config.AppConfig.urlProfileFromTBP;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

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

    public List<EntMyInspectionHead> listItems = new ArrayList<>();

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
    private static final SimpleDateFormat dateFormatParam = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static final int openactivitytoalatgroup  = 50;
    String today, hplus14, hmin14, hplus30, hmin30;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE  = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // SugarContext.init(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new MMController();
        session = new SessionManager(getApplicationContext());

        selectProject = (TextView) findViewById(R.id.select_project);
        projectSelected = (TextView) findViewById(R.id.project_selected);

        Calendar calendar = new GregorianCalendar();
        today = dateFormat.format(calendar.getTime());

        Date currentDate = new Date();
        Calendar calendarforparam1 = new GregorianCalendar();
        Calendar calendarforparam2 = new GregorianCalendar();
        Calendar calendarforparam3 = new GregorianCalendar();
        Calendar calendarforparam4 = new GregorianCalendar();

        calendarforparam1.setTime(currentDate);
        calendarforparam2.setTime(currentDate);
        calendarforparam3.setTime(currentDate);
        calendarforparam4.setTime(currentDate);

        calendarforparam1.add(Calendar.DATE, 14);
        calendarforparam2.add(Calendar.DATE, -14);
        calendarforparam3.add(Calendar.DATE, 30);
        calendarforparam4.add(Calendar.DATE, -30);

        hplus14 =  dateFormatParam.format(calendarforparam1.getTime());
        hmin14 = dateFormatParam.format(calendarforparam2.getTime());
        hplus30 = dateFormatParam.format(calendarforparam3.getTime());
        hmin30 = dateFormatParam.format(calendarforparam4.getTime());

        //islogin
        Intent intent = getIntent();
        extras = intent.getExtras();
        if(extras!=null){
            if(extras.getString("nik")!=null)
            {
                //Jika url tidak dapat dari launcher
                if(extras.getString("url") == null){
                    session.setUrlConfig("http://testcis.totalbp.com/");
                }
                else
                {
                    session.setUrlConfig(extras.getString("url"));
                }

                session.setKodeProyek(extras.getString("projectid"));
                session.setKodeProyek2(extras.getString("projectid"));
                session.setNamaProyek(extras.getString("projectname"));

                session.createLoginSession(true,extras.getString("nik"),extras.getString("email"), "role", extras.getString("token"), extras.getString("nama"));
                Log.d("login_session_mm",extras.getString("nik")+extras.getString("email")+ "role"+ extras.getString("token")+ extras.getString("nama"));

                session.setCanView(extras.getString("toapprove"));
                session.setCanEdit(extras.getString("todelete"));
                session.setCanInsert(extras.getString("toedit"));
                session.setCanDelete(extras.getString("toinsert"));
                session.setCanApprove(extras.getString("toview"));
//                session.setUrlConfig(extras.getString("url"));

                Log.d("LOG.USERPRIVILEGE","To Approve : "+extras.getString("toapprove")+ " To Delete :" +extras.getString("todelete")+ " To Edit :"+ extras.getString("toedit")+ " To Insert :" + extras.getString("toinsert")+ " To View : "+ extras.getString("toview"));

            }
//            Toast.makeText(getApplicationContext(),extras.getString("nik"),Toast.LENGTH_SHORT).show();
        }
        else{
            Intent inent = new Intent("com.totalbp.cis.main_action");
            PackageManager pm = getPackageManager();
            List<ResolveInfo> resolveInfos = pm.queryIntentActivities(inent, PackageManager.GET_RESOLVED_FILTER);
            if(resolveInfos.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Application Not Installed", Toast.LENGTH_SHORT).show();
                Log.i("NoneResolved", "No Activities");
                new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage("Please using CIS launcher to use the apps")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                finish();
                            }}).show();
            } else {
                Log.i("Resolved!", "There are activities" + resolveInfos);
                startActivity(inent);
                this.finish();
            }
        }

        //getSupportActionBar().setTitle("List Item Material");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);

        tvUserNameSideBar = (TextView) navHeader.findViewById(R.id.tv_UsernameSideBar);
        tvEmailSideBar = (TextView) navHeader.findViewById(R.id.tv_EmailSideBar);
        imgProfile = (ImageView) navHeader.findViewById(R.id.imageProfile);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        if (!session.getKeyNik().equals("")) {
            projectSelected.setText(session.getNamaProyek());
        }

        tvUserNameSideBar.setText(session.getUserName());
        tvEmailSideBar.setText(session.getUserEmail());

        //Start Recycler View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        sectionAdapter = new SectionedRecyclerViewAdapter();

        //sectionAdapter.addSection(new NewsSection(NewsSection.WORLD, "Weekly"));


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(sectionAdapter);

        loadJSONFromAsset();

        Glide.with(getApplicationContext()).load(session.getUrlConfig()+urlProfileFromTBP+session.getUserDetails().get("nik")+".jpg")
                .asBitmap()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(imgProfile) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        imgProfile.setImageDrawable(circularBitmapDrawable);
                    }
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }


    }

    public String loadJSONFromAsset() {
        swipeRefreshLayout.setRefreshing(true);
        String json = null;
        listItems.clear();
        try {
            InputStream is = getApplicationContext().getAssets().open("list_headinspection.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            swipeRefreshLayout.setRefreshing(false);
            jsonListSPNDummy = new JSONArray(json);

            if (jsonListSPNDummy.length() > 0) {
                Log.d("LogInspectionApps", jsonListSPNDummy.toString());

                for (int i = 0; i < jsonListSPNDummy.length(); i++) {
                    item = jsonListSPNDummy.getJSONObject(i);

                    EntMyInspectionHead listHeadInspection = new EntMyInspectionHead(
                            item.getString("Id"), item.getString("Periode")
                            );

                    listItems.add(listHeadInspection);
                    sectionAdapter.addSection(new NewsSection(NewsSection.WORLD, item.getString("Periode"), item.getString("Id")));
                    swipeRefreshLayout.setRefreshing(false);
                }
//
//
//                adapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);

                /*
                if (listItemsKriteria.size() > 0 & listViewProject != null) {
                    adapter = new AllSPNAdapter(getActivity().getApplicationContext(),listItemsKriteria);
                    listViewProject.setAdapter(adapter);
                    listViewProject.setTextFilterEnabled(false);
                }
                */
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
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

        ArrayList<EntMyInspectionFix> list = new ArrayList<>();
        ArrayList<EntMyInspectionDetilFix> list2 = new ArrayList<>();
        int imgPlaceholderResId;
        boolean expanded = true;

        NewsSection(int topic, String string, String param) {
            super(new SectionParameters.Builder(R.layout.activity_myinspection_row)
                    .headerResourceId(R.layout.activity_myinspection_header)
                    //.footerResourceId(R.layout.fragment_upcoming_orders_row_footer)
                    .build());

            this.topic = topic;
            switch (topic) {
                case WORLD:
                    this.title = string;
                    this.list = SetData(param);
                    this.imgPlaceholderResId = R.drawable.ic_spn;
                    break;
            }
        }

        private List<String> getNews(int arrayResource) {
            return new ArrayList<>(Arrays.asList(getResources().getStringArray(arrayResource)));
        }

        public ArrayList SetData(String param){
            swipeRefreshLayout.setRefreshing(true);
            session = new SessionManager(getApplicationContext());
            String date = null;

            String date1 = null, date2 = null;
            if(param.equals("14"))
            {
                date1 = hmin14;
                date2 = hplus14;
            }
            else
            {
                date1 = hmin30;
                date2 = hplus30;
            }
            if(date != "") {
                controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListInspectionMobile",
                        "@KodeProject","'" + session.getKodeProyek2() + "'",
                        "@currentpage","1",
                        "@pagesize","10",
                        "@sortby","",
                        "@wherecond"," AND TInsp.JenisInspeksi = "+param+" AND TInsp.TglInspeksi Between CONVERT(date, '"+date1+"') and CONVERT(date, '"+date2+"')",
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
                                    Log.d("LogInsection", result.toString());
                                    swipeRefreshLayout.setRefreshing(false);
                                    if (result.length() > 0) {
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject item = result.getJSONObject(i);

                                            EntMyInspectionFix itemEnts = new EntMyInspectionFix(
                                                    item.getString("MdInsp"),item.getString("KodeProyek"),item.getString("Nama_Proyek"),item.getString("TglInspeksi"), item.getString("StartStatus"), item.getString("WeekSubGrpUniqueID"),item.getString("MonthSubGrpUniqueID"),item.getString("JenisInspeksi"), item.getString("TglInspeksiParam"), item.getString("TInspScheID")
                                            );
                                            list.add(itemEnts);

                                            SetDataInspector(param, item.getString("MdInsp").toString(), item.getString("KodeProyek"), item.getString("TglInspeksiParam"));
//                                            sectionAdapter.notifyDataSetChanged();
                                        }
                                    }
                                }catch (JSONException e){
                                    Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            return list;

        }

        public ArrayList SetDataInspector(String param, String param2, String KodeProyek, String TglInspeksi){
            swipeRefreshLayout.setRefreshing(true);
            session = new SessionManager(getApplicationContext());
            String date = null;

            //JIKA Untuk PO dan Kontrak (date = "")
            if(date != "") {
                controller.InqGeneralPagingFullEzra(getApplicationContext(),"GetListInspectionInspectorMobile",
                        "@KodeProject","'"+KodeProyek+"'",
                        "@currentpage","1",
                        "@pagesize","10",
                        "@sortby","",
                        "@wherecond"," AND TInsp.JenisInspeksi = "+param+" AND MhInsp.ID = "+param2+" AND CONVERT(VARCHAR,TInsp.TglInspeksi,105) = "+"'"+TglInspeksi+"'",
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
                                    Log.d("LogInspector", result.toString());
                                    if (result.length() > 0) {
                                        for (int i = 0; i < result.length(); i++) {
                                            JSONObject item = result.getJSONObject(i);

                                            EntMyInspectionDetilFix itemEnts = new EntMyInspectionDetilFix(
                                                    item.getString("MdInsp"),item.getString("Inspector"), TglInspeksi, KodeProyek
                                            );

                                            list2.add(itemEnts);
                                        }
                                        sectionAdapter.notifyDataSetChanged();
                                    }
                                    sectionAdapter.notifyDataSetChanged();
                                }catch (JSONException e){
                                    Toast.makeText(getApplicationContext(), "CATCH "+e.toString(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
            return list2;

        }

        @Override
        public int getContentItemsTotal() {
            return expanded? list.size() : 0;
//            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;



            itemHolder.tvNamaTower.setText(list.get(position).getNamaProyek());
            itemHolder.tvDate.setText(list.get(position).getDate());
//            itemHolder.tvInspector.setText(list.get(position).getInspector());
            itemHolder.tvStartStatus.setText(list.get(position).getStatusStart());
            Log.d("LogInspections222",list.get(position).getTglInspeksiParam().toString());
            StringBuilder builder = new StringBuilder();
            for (EntMyInspectionDetilFix details : list2)
            {

                Log.d("LogInspections3",details.getMdInsp().toString()+details.getTglInspeksi().toString()+"="+list.get(position).getId().toString()+list.get(position).getTglInspeksiParam().toString());

                if(details.getMdInsp().toString().equals(list.get(position).getId().toString()) && details.getTglInspeksi().toString().equals(list.get(position).getTglInspeksiParam().toString() ))
                {
                    Log.d("LogInspections33",details.getTglInspeksi().toString()+"="+list.get(position).getTglInspeksiParam().toString());
                    builder.append(details.getInspector() + "\n");
                    Log.d("LogInspection1",details.getInspector().toString());
                }
            }

            itemHolder.tvInspector.setText(builder.toString());

            if(list.get(position).getStatusStart().equals("NOTSTARTED"))
            {
                itemHolder.ivStartStatus2.setVisibility(View.VISIBLE);
                //itemHolder.ivStartStatus.setColorFilter(getApplicationContext().getResources().getColor(R.color.my_red));
                //itemHolder.ivStartStatus.getDrawable().setColorFilter()
                itemHolder.tvStartStatus.setText("NOT STARTED");
            }
            else
            {
                itemHolder.ivStartStatus.setVisibility(View.VISIBLE);
                //itemHolder.ivStartStatus.setColorFilter(getApplicationContext().getResources().getColor(R.color.weixin_point_background));
                itemHolder.tvStartStatus.setText("STARTED");
            }

            Date d1 = null;
            Date d2 = null;

            try {
                d1 = dateFormat.parse(today);
                d2 = dateFormat.parse(list.get(position).getDate());
                long diff = d2.getTime() - d1.getTime();
                long diffDays = diff / (24 * 60 * 60 * 1000);

                itemHolder.tvDateOutstanding.setText(String.valueOf(diffDays)+"d");
                //Log.d("LogInspectionDateRange", String.valueOf(diffDays));
            } catch (ParseException e) {
                Log.d("LogInspectionDateRange", e.toString());
                e.printStackTrace();
            }


            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String datechoose = dateFormat.format(myCalendar.getTime());

                    UpdateReschedule(datechoose, session.getKodeProyek(), list.get(position).getDate(), list.get(position).getJenisInspeksi());
                }

            };

            itemHolder.btnReschedule.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new DatePickerDialog(MainActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    session.setKodeProyek(list.get(position).getKodeProyek());
                    session.setTInspScheID(list.get(position).getTInspScheID());
                    Intent intent = new Intent(MainActivity.this, AlatGroupActivity.class);
                    //Jika Inspeksi Mingguan
                    if(list.get(position).getJenisInspeksi().toString().equals("14")) {
                        intent.putExtra("paramSubGrpUniqueID", list.get(position).getWeekSubGrpUniqueID());
                        intent.putExtra("paramPeriode", list.get(position).getJenisInspeksi());
                    }
                    else{
                        intent.putExtra("paramSubGrpUniqueID", list.get(position).getWeekSubGrpUniqueID());
                        intent.putExtra("paramPeriode", list.get(position).getJenisInspeksi());
                    }
                    startActivityForResult(intent, openactivitytoalatgroup);
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
        private final ImageView ivStartStatus;
        private final ImageView ivStartStatus2;
        private final TextView tvNamaTower;
        private final TextView tvDateOutstanding;
        private final TextView tvDate;
        private final TextView tvInspector;
        private final TextView tvStartStatus;
        private final Button btnReschedule;

        ItemViewHolder(View view) {
            super(view);

            rootView = (RelativeLayout) view.findViewById(R.id.rootView);
            ivStartStatus = (ImageView) view.findViewById(R.id.ivStartStatus);
            ivStartStatus2 = (ImageView) view.findViewById(R.id.ivStartStatus2);
            tvNamaTower = (TextView) view.findViewById(R.id.tvNamaTower);
            tvDateOutstanding = (TextView) view.findViewById(R.id.tvDateOutstanding);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvInspector = (TextView) view.findViewById(R.id.tvInspector);
            tvStartStatus = (TextView) view.findViewById(R.id.tvStartStatus);
            btnReschedule = (Button) view.findViewById(R.id.btnReschedule);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);


        if( requestCode == openactivitytoalatgroup ) {
            if (data != null){
                //listItems.clear();
            }
        }
//        else if( requestCode == request_data_from_2 ) {
//            if (data != null){}
//        }

    }

    public void UpdateReschedule(String datechoosen, String kodeproyek, String datetglinspeksi, String jenisinspeksi)
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
        postInspectionData.setJenisInspeksi(jenisinspeksi);

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
                            Toast toast = Toast.makeText(MainActivity.this,"Reschedule Update Success!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                        else
                        {
                            Toast toast = Toast.makeText(MainActivity.this,"Reschedule Update Failed : Data Not Found!", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    }
                    else
                    {
                        //Toast toast = Toast.makeText(MainActivity.this,"Reschedule Failed, "+obj.getString("Message")+" ! ", Toast.LENGTH_LONG);
                        Toast toast = Toast.makeText(MainActivity.this,"Reschedule Update Failed!", Toast.LENGTH_LONG);
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
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_item, menu);
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
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRefresh() {
        sectionAdapter.removeAllSections();
        listItems.clear();
        list.clear();
        list2.clear();
        loadJSONFromAsset();
        sectionAdapter.notifyDataSetChanged();
    }
}

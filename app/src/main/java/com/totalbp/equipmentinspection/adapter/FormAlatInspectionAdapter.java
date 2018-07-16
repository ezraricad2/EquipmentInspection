package com.totalbp.equipmentinspection.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.totalbp.equipmentinspection.R;
import com.totalbp.equipmentinspection.model.EntAlatInspection;
import com.totalbp.equipmentinspection.model.EntSpinnerInspection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ezra.R on 26/03/2018.
 */

public class FormAlatInspectionAdapter extends RecyclerView.Adapter<FormAlatInspectionAdapter.MyViewHolder>{

    private Context mContext;
    private List<EntAlatInspection> itemRequest;
    private List<EntSpinnerInspection> itemSpinner;
    private List<String> itemSpinnerUse = new ArrayList<>();
    HashMap<String, String> listSpinnerDataChild = new HashMap<>();
    private MessageAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private static int currentSelectedIndex = -1;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;
    private RadioButton radioResult;
    int selectedId;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItemName;
        public RelativeLayout messageContainer;
        private LinearLayout llThree, llFour, llTwo;
        private RadioGroup rgThree, rgTwo, rgFour;
        private RadioButton rbOK, rbAdjust, rbChange;
        private RadioButton rbS1, rbS2, rbS3, rbS4;
        private RadioButton rbPass, rbFail;
        private Spinner spinnerAction;
        public ImageView ivComment, ivCamera;


        public MyViewHolder(View view) {
            super(view);

            tvItemName = (TextView) view.findViewById(R.id.tvFileName);
            messageContainer = (RelativeLayout) view.findViewById(R.id.messageContainer);
            llTwo = (LinearLayout) view.findViewById(R.id.llTwo);
            llThree = (LinearLayout) view.findViewById(R.id.llThree);
            llFour = (LinearLayout) view.findViewById(R.id.llFour);

            rgTwo = (RadioGroup) view.findViewById(R.id.rgTwo);
            rgThree = (RadioGroup) view.findViewById(R.id.rgThree);
            rgFour = (RadioGroup) view.findViewById(R.id.rgFour);

            rbOK = (RadioButton) view.findViewById(R.id.rbOK);
            rbAdjust = (RadioButton) view.findViewById(R.id.rbAdjust);
            rbChange = (RadioButton) view.findViewById(R.id.rbChange);

            rbS1 = (RadioButton) view.findViewById(R.id.rbS1);
            rbS2 = (RadioButton) view.findViewById(R.id.rbS2);
            rbS3 = (RadioButton) view.findViewById(R.id.rbS3);
            rbS4 = (RadioButton) view.findViewById(R.id.rbS4);

            rbPass = (RadioButton) view.findViewById(R.id.rbPass);
            rbFail = (RadioButton) view.findViewById(R.id.rbFail);

            spinnerAction = (Spinner) view.findViewById(R.id.spinnerAction);

            ivComment = (ImageView) view.findViewById(R.id.ivComment);
            ivCamera = (ImageView) view.findViewById(R.id.ivCamera);

            ivComment.setColorFilter(mContext.getResources().getColor(R.color.icon_gray));
            ivCamera.setColorFilter(mContext.getResources().getColor(R.color.icon_gray));

        }

    }


    public FormAlatInspectionAdapter(Context mContext, List<EntAlatInspection> itemrequest, List<EntSpinnerInspection> itemspinner, MessageAdapterListener listener) {
        this.mContext = mContext;
        this.itemRequest = itemrequest;
        this.itemSpinner = itemspinner;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detilalat_inspection_row_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        EntAlatInspection rowItem = itemRequest.get(position);
        EntSpinnerInspection rowItem2 = itemSpinner.get(position);

        holder.tvItemName.setText(rowItem.getNamaItem());

        if(rowItem.getStatusInspeksi().toString().equals("1"))
        {
            holder.messageContainer.setBackgroundColor(Color.parseColor("#7018e248"));
        }

        Log.d("LogInspectionT", rowItem.getMdInspAst().toString());
        //Log.d("LogInspectionT", rowItem.getMdInspAst().toString());

        for (EntSpinnerInspection details : itemSpinner) {
            //Log.d("LogInspectionT", details.getMdInspAst().toString());
            if(details.getMdInspAst().toString().equals(itemRequest.get(position).getMdInspAst().toString()) && details.getTipeJawaban().toString().equals(itemRequest.get(position).getTipeJawaban().toString()))
            {
                Log.d("LogInspectionT2", details.getDeskripsi().toString());


                if(!listSpinnerDataChild.containsKey(details.getDeskripsi().toString())) {
                    Log.d("LogInsSpinnerKey", details.getDeskripsi().toString());
                    Log.d("LogInsSpinnerValue", details.getValue().toString());
                    listSpinnerDataChild.put(details.getDeskripsi().toString(), details.getValue().toString());
                }

                if(!itemSpinnerUse.contains(details.getDeskripsi().toString())) {
                    Log.d("LogInspectionT3", details.getDeskripsi().toString());
                    itemSpinnerUse.add(details.getDeskripsi().toString());

                }
                ArrayAdapter<String> adapterDDLGlobal = new ArrayAdapter<>(mContext,
                        R.layout.spinner_item_background1, itemSpinnerUse);
                adapterDDLGlobal.setDropDownViewResource(R.layout.spinner_item_background1);
                holder.spinnerAction.setAdapter(adapterDDLGlobal);
            }
        }



        holder.spinnerAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                String items = holder.spinnerAction.getSelectedItem().toString();

                if(rowItem.getStatusInspeksi().toString().equals("1"))
                {
                    holder.spinnerAction.setSelection(Integer.parseInt(rowItem.getResult().toString()));
                    if(!items.equals(rowItem.getResultDeskripsi().toString())) {
                        if (!items.equals("-Select Action-")) {

                            if (listSpinnerDataChild.containsKey(items)) {
                                //AMBIL VALUE (HASHMAP) YANG TERDAPAT BERDASARKAN KEY DI LISTVIEW SPINNER
                                listener.onMessageRowClicked(position, listSpinnerDataChild.get(items).toString());
                            }


                        }
                    }
                }
                else
                {
                    if (!items.equals("-Select Action-")) {
                        Log.d("Selecteditem:", items);
                        if (listSpinnerDataChild.containsKey(items)) {
                            //AMBIL VALUE (HASHMAP) YANG TERDAPAT BERDASARKAN KEY DI LISTVIEW SPINNER
                            listener.onMessageRowClicked(position, listSpinnerDataChild.get(items).toString());
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        holder.ivComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageCommentClicked(position, "");
            }
        });

        holder.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageCameraClicked(position, "");
            }
        });
//        if(rowItem.getTipeJawaban().toString().equals("1"))
//        {
//            holder.llThree.setVisibility(View.VISIBLE);
//        }
//        else if(rowItem.getTipeJawaban().toString().equals("2"))
//        {
//            holder.llFour.setVisibility(View.VISIBLE);
//        }
//        else if(rowItem.getTipeJawaban().toString().equals("3"))
//        {
//            holder.llTwo.setVisibility(View.VISIBLE);
//        }



        //applyFile(holder, rowItem);
        // handle icon animation
        //applyIconAnimation(holder, position);

        // display profile image
        //applyProfilePicture(holder, rowItem);

        // apply click events

//        holder.rbOK.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbAdjust.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbS1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbS2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbS3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbS4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbPass.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
//
//        holder.rbFail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(rowItem.getTipeJawaban().toString().equals("1"))
//                {
//                    selectedId = holder.rgThree.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("2"))
//                {
//                    selectedId = holder.rgFour.getCheckedRadioButtonId();
//                }
//                else if(rowItem.getTipeJawaban().toString().equals("3"))
//                {
//                    selectedId = holder.rgTwo.getCheckedRadioButtonId();
//                }
//
//                radioResult = (RadioButton) view.findViewById(selectedId);
//                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
//                Log.d("LogInspectionRadio",radioResult.getText().toString());
//                listener.onMessageRowClicked(position, radioResult.getText().toString());
//            }
//        });
    }

    private void applyClickEvents(MyViewHolder holder, final int position) {



         /*
        holder.messageContainer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onRowLongClicked(position);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                return true;
            }
        });
        */
    }

    // As the views will be reused, sometimes the icon appears as
    // flipped because older view is reused. Reset the Y-axis to 0
    private void resetIconYAxis(View view) {
        if (view.getRotationY() != 0) {
            view.setRotationY(0);
        }
    }

    public void resetAnimationIndex() {
        reverseAllAnimations = false;
        animationItemsIndex.clear();
    }


    @Override
    public int getItemCount() {
        return itemRequest.size();
    }

    public void toggleSelection(int pos) {
        currentSelectedIndex = pos;
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
            animationItemsIndex.delete(pos);
        } else {
            selectedItems.put(pos, true);
            animationItemsIndex.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    private void resetCurrentIndex() {
        currentSelectedIndex = -1;
    }

    public interface MessageAdapterListener {
        void onIconClicked(int position);

        void onIconImportantClicked(int position);

        void onMessageRowClicked(int position, String action);

        void onMessageCommentClicked(int position, String action);

        void onMessageCameraClicked(int position, String action);

        void onRowLongClicked(int position);
    }


}

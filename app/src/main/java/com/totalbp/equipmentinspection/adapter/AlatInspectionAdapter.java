package com.totalbp.equipmentinspection.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.totalbp.equipmentinspection.R;
import com.totalbp.equipmentinspection.model.EntAlatInspection;
import com.totalbp.equipmentinspection.model.EntMyItemInspectionQty;
import com.totalbp.equipmentinspection.model.EntSpinnerInspection;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Ezra.R on 26/03/2018.
 */

public class AlatInspectionAdapter extends RecyclerView.Adapter<AlatInspectionAdapter.MyViewHolder>{

    private Context mContext;
    private List<EntAlatInspection> itemRequest;
    private List<EntMyItemInspectionQty> itemRequestQty;
    private MessageAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private static int currentSelectedIndex = -1;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItemName, tvInspectionView, tvValueBased, tvValueProgress, tvInspectionStatus;
        public RelativeLayout messageContainer;


        public MyViewHolder(View view) {
            super(view);

            tvItemName = (TextView) view.findViewById(R.id.tvFileName);
            messageContainer = (RelativeLayout) view.findViewById(R.id.messageContainer);
            tvValueBased = (TextView) view.findViewById(R.id.tvValueBased);
            tvValueProgress = (TextView) view.findViewById(R.id.tvValueProgress);
            tvInspectionStatus = (TextView) view.findViewById(R.id.tvInspectionStatus);

        }

    }


    public AlatInspectionAdapter(Context mContext, List<EntAlatInspection> itemrequest, List<EntMyItemInspectionQty> itemrequestqty, MessageAdapterListener listener) {
        this.mContext = mContext;
        this.itemRequest = itemrequest;
        this.itemRequestQty = itemrequestqty;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alat_inspection_row_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        EntAlatInspection rowItem = itemRequest.get(position);

        holder.tvItemName.setText(rowItem.getNamaItem());
        holder.tvValueBased.setText((rowItem.getTotalIns()));

        for (EntMyItemInspectionQty details : itemRequestQty) {
            //Log.d("LogInspectionT", details.getMdInspAst().toString());
            if(details.getParentItemID().toString().equals(itemRequest.get(position).getMdInspAst().toString()))
            {
                //if(!itemRequestQty.contains(details.getParentItemID().toString())) {
                holder.tvValueProgress.setText(details.getTotalResult());



                //}
//                Log.d("LogInspectionT2", details.getParentItemID().toString());
//                if(!itemSpinnerUse.contains(details.getDeskripsi().toString())) {
//                    Log.d("LogInspectionT3", details.getDeskripsi().toString());
//                    itemSpinnerUse.add(details.getDeskripsi().toString());
//
//                }
//                ArrayAdapter<String> adapterDDLGlobal = new ArrayAdapter<>(mContext,
//                        android.R.layout.simple_spinner_dropdown_item, itemSpinnerUse);
//
//                holder.spinnerAction.setAdapter(adapterDDLGlobal);
            }

            if(!holder.tvValueProgress.getText().toString().equals(holder.tvValueBased.getText().toString()))
            {
                holder.tvValueProgress.setTextColor(ContextCompat.getColor(mContext, R.color.paymethod_weixin_color));
            }
        }



        Log.d("LogValueProgress", holder.tvValueProgress.getText().toString());
        if(!(holder.tvValueProgress.getText().toString().equals("0"))) {
            holder.tvInspectionStatus.setText("STARTED");
            holder.tvInspectionStatus.setBackgroundResource(R.drawable.btn_greentrans);
        }
        else
        {
            holder.tvInspectionStatus.setText("NOT STARTED");
            holder.tvInspectionStatus.setBackgroundResource(R.drawable.btn_redtrans);
        }

        // apply click events
        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("LogInspectionId", rowItem.getMdInspAst().toString());
                listener.onMessageRowClicked(position);
            }
        });

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

        void onMessageRowClicked(int position);

        void onRowLongClicked(int position);
    }


}

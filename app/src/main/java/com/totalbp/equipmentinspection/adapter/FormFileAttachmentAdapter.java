package com.totalbp.equipmentinspection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.totalbp.equipmentinspection.R;
import com.totalbp.equipmentinspection.model.EntFileAttachment;

import java.util.List;

/**
 * Created by Ezra.R on 26/03/2018.
 */

public class FormFileAttachmentAdapter extends RecyclerView.Adapter<FormFileAttachmentAdapter.MyViewHolder>{

    private Context mContext;
    private List<EntFileAttachment> itemAttachment;
    private MessageAdapterListener listener;
    private SparseBooleanArray selectedItems;
    private static int currentSelectedIndex = -1;

    // array used to perform multiple animation at once
    private SparseBooleanArray animationItemsIndex;
    private boolean reverseAllAnimations = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tvItemName;
        public RelativeLayout messageContainer;
        private ImageView btnDownload, btnDelete;


        public MyViewHolder(View view) {
            super(view);

            tvItemName = (TextView) view.findViewById(R.id.tvFileName);
            messageContainer = (RelativeLayout) view.findViewById(R.id.messageContainer);
            btnDownload = (ImageView) view.findViewById(R.id.btnDownload);
            btnDelete = (ImageView) view.findViewById(R.id.btnDelete);


        }

    }


    public FormFileAttachmentAdapter(Context mContext, List<EntFileAttachment> itemattachment, MessageAdapterListener listener) {
        this.mContext = mContext;
        this.itemAttachment = itemattachment;
        this.listener = listener;
        selectedItems = new SparseBooleanArray();
        animationItemsIndex = new SparseBooleanArray();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detil_attachment_row_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        EntFileAttachment rowItem = itemAttachment.get(position);

        holder.btnDownload.setColorFilter(mContext.getResources().getColor(R.color.icon_gray));
        holder.btnDelete.setColorFilter(mContext.getResources().getColor(R.color.icon_gray));
        holder.tvItemName.setText(rowItem.getDocName());

        holder.messageContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowClicked(position);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.itemView.setVisibility(View.GONE);
                listener.onMessageRowDelClicked(position);
            }
        });

        holder.btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMessageRowDownClicked(position);
            }
        });
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
        return itemAttachment.size();
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

        void onMessageRowDelClicked(int position);

        void onMessageRowDownClicked(int position);

        void onRowLongClicked(int position);
    }


}

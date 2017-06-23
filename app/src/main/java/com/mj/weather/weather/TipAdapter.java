package com.mj.weather.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mj.weather.R;
import com.mj.weather.weather.http.HeProtocol;

import java.util.List;

/**
 * Created by MengJie on 2017/1/30.
 */

public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder>{
    private List<HeProtocol.Tip> tipList;
    private Context context;

    public TipAdapter(List<HeProtocol.Tip> tipList) {
        this.tipList = tipList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_suggestion, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HeProtocol.Tip tip = tipList.get(position);
        holder.tvTipTitle.setText(tip.getName());
        holder.tvTipBrief.setText(tip.getBrief());
        holder.tvTipTxt.setText(tip.getTxt());
    }

    @Override
    public int getItemCount() {
        return tipList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTipTitle, tvTipBrief, tvTipTxt;
        public ViewHolder(View view) {
            super(view);
            tvTipTitle = (TextView) view.findViewById(R.id.tv_tip_title);
            tvTipBrief = (TextView) view.findViewById(R.id.tv_tip_brief);
            tvTipTxt = (TextView) view.findViewById(R.id.tv_tip_txt);
        }
    }
}

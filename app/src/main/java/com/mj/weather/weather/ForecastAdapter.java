package com.mj.weather.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mj.weather.R;
import com.mj.weather.weather.http.HeProtocol;

import java.util.List;

/**
 * Created by MengJie on 2017/1/30.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder>{
    private List<HeProtocol.Daily_forecast> forecastList;
    private Context context;

    public ForecastAdapter(List<HeProtocol.Daily_forecast> forecastList) {
        this.forecastList = forecastList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_forecast, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HeProtocol.Daily_forecast forecast = forecastList.get(position);
        holder.tvDate.setText(forecast.getDate());
        holder.tvTxtD.setText(forecast.getCond().getTxt_d());
        holder.tvTxtN.setText(forecast.getCond().getTxt_n());
        holder.tvTmpMax.setText(forecast.getTmp().getMax());
        holder.tvTmpMin.setText(forecast.getTmp().getMin());
        Glide.with(context).load(HeProtocol.getIconUrl(forecast.getCond().getCode_d())).into(holder.ivCondD);
        Glide.with(context).load(HeProtocol.getIconUrl(forecast.getCond().getCode_n())).into(holder.ivCondN);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvTxtD, tvTmpMax, tvTmpMin, tvTxtN;
        ImageView ivCondD, ivCondN;
        public ViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            tvTxtD = (TextView) view.findViewById(R.id.tv_txt_d);
            tvTmpMax = (TextView) view.findViewById(R.id.tv_tmp_max);
            tvTmpMin = (TextView) view.findViewById(R.id.tv_tmp_min);
            tvTxtN = (TextView) view.findViewById(R.id.tv_txt_n);
            ivCondD = (ImageView) view.findViewById(R.id.iv_cond_d);
            ivCondN = (ImageView) view.findViewById(R.id.iv_cond_n);
        }
    }

}

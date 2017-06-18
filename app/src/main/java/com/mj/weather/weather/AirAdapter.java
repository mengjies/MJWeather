package com.mj.weather.weather;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mj.weather.R;
import com.mj.weather.http.HeProtocol;

import java.util.List;

/**
 * Created by MengJie on 2017/1/30.
 */

public class AirAdapter extends RecyclerView.Adapter<AirAdapter.ViewHolder>{
    private List<HeProtocol.AirQuality> airList;
    private Context context;

    public AirAdapter(List<HeProtocol.AirQuality> airList) {
        this.airList = airList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_aqi, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HeProtocol.AirQuality air = airList.get(position);
        holder.tvAirTitle.setText(air.getName());
        holder.tvAirValue.setText(air.getValue());
    }

    @Override
    public int getItemCount() {
        return airList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvAirTitle, tvAirValue;
        public ViewHolder(View view) {
            super(view);
            tvAirTitle = (TextView) view.findViewById(R.id.tv_air_title);
            tvAirValue = (TextView) view.findViewById(R.id.tv_air_value);
        }
    }
}

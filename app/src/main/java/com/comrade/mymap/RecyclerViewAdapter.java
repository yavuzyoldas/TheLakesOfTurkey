package com.comrade.mymap;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.model.LatLng;

import java.text.MessageFormat;
import java.util.ArrayList;

import static com.comrade.mymap.MainActivity.hMap;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Lakes> lakesArrayList;

    Lakes lake;

    public RecyclerViewAdapter(Context context, ArrayList<Lakes> lakesArrayList) {
        this.context = context;
        this.lakesArrayList = lakesArrayList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {

         lake = lakesArrayList.get(position); // each contact object inside of our list

        holder.lakeName.setText(lake.getName());
        holder.lakeArea.setText(MessageFormat.format("Area:{0}",lake.getArea()));
        holder.lakeDepth.setText(MessageFormat.format("Depth:{0}",String.valueOf(lake.getDepth())));
        holder.lakeCoordinate.setText(MessageFormat.format("Coordinate:{0}",String.valueOf(lake.getLatitude()+","+lake.getLongitude())));
        holder.lakeDescription.setText(MessageFormat.format("Description : {0}",String.valueOf(lake.getDescription())));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.lakeDescription.setFocusable(true);

                if(holder.lakeDescription.getVisibility() == View.GONE) {


                    holder.lakeDescription.setVisibility(View.VISIBLE);
                    holder.scrollView.post(new Runnable() {
                        public void run() {
                            holder.scrollView.requestChildFocus(holder.relativeLayout,holder.lakeDescription);
                            //hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lake.getLatitude(),lake.getLongitude()), 10f)); // 1 - 20
                            Lakes  lake1 = lakesArrayList.get(position);
                            LatLng latLng = new LatLng(lake1.getLatitude(),lake1.getLongitude());
                            hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f));
                        }
                    });
                }else{
                    holder.lakeDescription.setVisibility(View.GONE);
                    holder.scrollView.post(new Runnable() {
                        public void run() {
                            holder.scrollView.requestChildFocus(holder.relativeLayout,holder.cardView);
                            hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(38.785523,33.341002), 4.5f)); // 1 - 20
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lakesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lakeName, lakeArea, lakeDepth, lakeCoordinate , lakeDescription;

        RelativeLayout relativeLayout;
        ScrollView scrollView;
        CardView cardView;



        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;

            lakeName = itemView.findViewById(R.id.lake_name);
            lakeArea = itemView.findViewById(R.id.lake_area);
            lakeDepth = itemView.findViewById(R.id.lake_depth);
            lakeCoordinate = itemView.findViewById(R.id.lake_coordinate);
            lakeDescription = itemView.findViewById(R.id.hidden_text);

            cardView = itemView.findViewById(R.id.card_view);

            scrollView = itemView.findViewById(R.id.scrollView);

            relativeLayout = itemView.findViewById(R.id.relativeLayout);

        }
    }

}

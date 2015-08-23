package randomz.com.notset;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nilesh Verma on 09-08-2015.
 */
public class HitAdapter extends RecyclerView.Adapter<HitAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList(); //making the list of information
    private  Context context;


    public HitAdapter(Context context,List<Information> data){
        inflater = LayoutInflater.from(context);
        this.data = data; //this will get the data and add them to this class global data

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_layout, viewGroup, false);
        //this will be the look of the recycler view

        MyViewHolder holder = new MyViewHolder(view);
        //now sending the layout to the the holder class to add the data on to it
        return holder;
    }

    @Override //this place where we gona fill the data item
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        Information current = data.get(i);
        viewHolder.title.setText(current.title); //using the data of the Information class
        viewHolder.icon.setImageResource(current.iconId);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView icon;
        TextView title;


        public MyViewHolder(View itemView) {
            super(itemView);
            //setting the title acc to the item
            title = (TextView) itemView.findViewById(R.id.list_Text);
            icon = (ImageView) itemView.findViewById(R.id.list_Icon);
            icon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            delete(getPosition());


        }
    }
    public  void delete(int position){
        data.remove(position);
        notifyItemRemoved(position); //this method will gona delete the item form the list

    }
}
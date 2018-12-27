package standardsoft.com.coraje.ui.view.detailPlanningModule.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Planning;

public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.ViewHolder>{

    private Context mContext;
    private List<Planning> mItems;

    // Instancia de escucha
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public PlanningAdapter(Context context, List<Planning> items) {
        this.mContext = context;
        this.mItems = items;
    }

    public interface OnItemClickListener{
        void onItemClick(Planning clickedPlanning);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(Planning longClickedPlanning);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongSelected){
        mOnItemLongClickListener = onItemLongSelected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public ImageView imgView;
        public TextView txtTask,txt1, txt2, txt3, txt4;
        public View priorityIndicator;

        public ViewHolder(View itemView) {
            super(itemView);

            priorityIndicator = itemView.findViewById(R.id.indicator_priority);
            //imgView  = (ImageView)itemView.findViewById(R.id.image_task);
            txtTask  = (TextView) itemView.findViewById(R.id.text_task);
            txt1  = (TextView) itemView.findViewById(R.id.text_1);
            txt2  = (TextView) itemView.findViewById(R.id.text_2);
            txt3  = (TextView) itemView.findViewById(R.id.text_3);
            txt4  = (TextView) itemView.findViewById(R.id.text_4);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION){
                mOnItemClickListener.onItemClick(mItems.get(position));
            }
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION){
                mOnItemLongClickListener.onItemLongClick(mItems.get(position));
                return true;
            }
            return false;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_planning, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Planning planning = mItems.get(position);

        String task = planning.getTask().trim();
        String module = planning.getModule().getDescription();
        String status = planning.getStatus().getDescription();
        String priority = planning.getPriority().getDescription();
        long date = planning.getDate();
        int percentage = planning.getPercentage();

        View priorityIndicator = holder.priorityIndicator;

        switch (priority){
            case "MEDIA":
                priorityIndicator.setBackgroundResource(R.color.colorBlue);
                break;
            case "ALTA":
                priorityIndicator.setBackgroundResource(R.color.colorRed);
                break;
            case "BAJA":
                priorityIndicator.setBackgroundResource(R.color.colorGreen);
                break;
        }

        holder.txtTask.setText(task);
        holder.txt1.setText(percentage+"%");
        holder.txt2.setText(status);
        holder.txt3.setText(module);
        holder.txt4.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(date));
        //holder.imgView.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_today_white));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

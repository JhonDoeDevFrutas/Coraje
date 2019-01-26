package standardsoft.com.coraje.ui.view.detailBugsFragmentModule.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Bugs;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{
    private Context mContext;
    private List<Bugs> mItems;

    // Instancia de escucha
    private OnItemClickListener mOnItemClickListener;

    public RequestAdapter(Context context, List<Bugs> items) {
        this.mContext = context;
        this.mItems = items;
    }

    public interface OnItemClickListener{
        void onItemClick(Bugs clickedBugs);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtTask,txt1, txt2, txt3, txt4;
        public View priorityIndicator;

        public ViewHolder(View itemView) {
            super(itemView);

            priorityIndicator = itemView.findViewById(R.id.indicator_priority);
            txtTask  = (TextView) itemView.findViewById(R.id.text_task);
            txt1  = (TextView) itemView.findViewById(R.id.text_1);
            txt2  = (TextView) itemView.findViewById(R.id.text_2);
            txt3  = (TextView) itemView.findViewById(R.id.text_3);
            txt4  = (TextView) itemView.findViewById(R.id.text_4);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION){
                mOnItemClickListener.onItemClick(mItems.get(position));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_bugs, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Bugs bugs = mItems.get(position);

        String task     = bugs.getTask().trim();
        String module   = bugs.getModule().getDescription();
        String status   = bugs.getStatus().getDescription();
        String priority = bugs.getPriority().getDescription();
        long date       = bugs.getDate();
        int time  = bugs.getEstimation();

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
        holder.txt1.setText(Integer.toString(time));
        holder.txt2.setText(status);
        holder.txt2.setTextColor(status ==  "EN PROCESO" ? mContext.getResources().getColor(R.color.colorOrange)
            : status == "LISTO" ? mContext.getResources().getColor(R.color.colorGreen)
            : mContext.getResources().getColor(R.color.colorRed));
        holder.txt3.setText(module);
        holder.txt4.setText(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ROOT).format(date));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}

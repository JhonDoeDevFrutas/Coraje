package standardsoft.com.coraje.ui.view.detailActivityModule.view.adapter;

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
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private Context mContext;
    private List<SubPlanning> mItems;

    // Instancia de escucha
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public TaskAdapter(Context context, List<SubPlanning> items) {
        this.mContext = context;
        this.mItems = items;
    }

    public interface OnItemClickListener{
        void onItemClick(SubPlanning clickedPlanning);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(SubPlanning longClickedSubPlanning);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongSelected){
        mOnItemLongClickListener = onItemLongSelected;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView txtTask, txtTaskPlanning;
        public TextView txt1,txt2,txt3;
        public View priorityStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            priorityStatus = itemView.findViewById(R.id.indicator_status);
            txtTask             = (TextView) itemView.findViewById(R.id.text_task);
            txtTaskPlanning     = (TextView) itemView.findViewById(R.id.text_task_planning);
            txt1                = (TextView) itemView.findViewById(R.id.text_1);
            txt2                = (TextView) itemView.findViewById(R.id.text_2);
            txt3                = (TextView) itemView.findViewById(R.id.text_3);

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
        View view = layoutInflater.inflate(R.layout.item_task, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubPlanning subPlanning = mItems.get(position);

        String task         = subPlanning.getTask().trim();
        String taskPlanning = subPlanning.getTaskPlanning().trim();
        String status       = subPlanning.getStatus().getDescription();
        String developer    = subPlanning.getAssignee() != null ? subPlanning.getAssignee().getName().trim() : "" ;
        String time         = Integer.toString(subPlanning.getEstimation()) ;
        long date           = subPlanning.getDate();

        View priorityStatus = holder.priorityStatus;

        switch (status){
            case "ESPERANDO REVISION":
                priorityStatus.setBackgroundResource(R.color.colorBlue);
                break;
            case "EN PROCESO":
                priorityStatus.setBackgroundResource(R.color.colorOrange);
                break;
            case "LISTO":
                priorityStatus.setBackgroundResource(R.color.colorGreen);
                break;
        }

        holder.txtTask.setText(task);
        holder.txtTaskPlanning.setText(taskPlanning);
        holder.txt1.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(date));
        holder.txt2.setText(developer);
        holder.txt3.setText(time+" Hora(s)");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}

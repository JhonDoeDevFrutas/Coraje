package standardsoft.com.coraje.ui.view.detailSubPlanningModule.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.SubPlanning;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder>{

    private Context mContext;
    private List<SubPlanning> mItems;

    // Instancia de escucha
    private OnItemClickListener mOnItemClickListener;
    private OnItemSelectedListener mOnItemSelectedListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public RequestAdapter(Context context, List<SubPlanning> items) {
        this.mContext = context;
        this.mItems = items;
    }

    public interface OnItemClickListener{
        void onItemClick(SubPlanning clickedPlanning);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(SubPlanning longClickedSubPlanning);
    }

    public interface  OnItemSelectedListener{
        void onMenuAction(SubPlanning selectPlanning, MenuItem item);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongSelected){
        mOnItemLongClickListener = onItemLongSelected;
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener,
            View.OnLongClickListener, PopupMenu.OnMenuItemClickListener, View.OnClickListener  {

        public TextView txtTask,txt1;
        public View priorityStatus;

        public ViewHolder(View itemView) {
            super(itemView);

            priorityStatus = itemView.findViewById(R.id.indicator_status);
            txtTask  = (TextView) itemView.findViewById(R.id.text_task);
            txt1  = (TextView) itemView.findViewById(R.id.text_1);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo menuInfo) {
            PopupMenu popup = new PopupMenu(view.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.custom_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION){
                mOnItemSelectedListener.onMenuAction(mItems.get(position), menuItem);
            }

            return false;
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

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION){
                mOnItemClickListener.onItemClick(mItems.get(position));
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.item_sub_planning, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubPlanning subPlanning = mItems.get(position);

        String task = subPlanning.getTask().trim();
        String developer = subPlanning.getAssignee() != null ? subPlanning.getAssignee().getName() : "";
        String status = subPlanning.getStatus().getDescription();

        View priorityStatus = holder.priorityStatus;

        switch (status){
            case "ESPERANDO REVISION":
                priorityStatus.setBackgroundResource(R.color.colorRed);
                break;
            case "EN PROCESO":
                priorityStatus.setBackgroundResource(R.color.colorBlue);
                break;
            case "LISTO":
                priorityStatus.setBackgroundResource(R.color.colorGreen);
                break;
        }

        holder.txtTask.setText(task);
        holder.txt1.setText(developer);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}

package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Remark;

public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = RequestAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private List<Object> itemObjects;

    public RequestAdapter(List<Object> itemObjects) {
        this.itemObjects = itemObjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
            return new HeaderViewHolder(layoutView);
        } else if (viewType == TYPE_ITEM) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_remark, parent, false);
            return new ItemViewHolder(layoutView);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object mObject = itemObjects.get(position);
        Remark remark = (Remark)getItem(position);

        if(holder instanceof HeaderViewHolder){
            ((HeaderViewHolder) holder).headerTitle.setText(remark.getDescription());
        }else if(holder instanceof ItemViewHolder){
            ((ItemViewHolder) holder).itemContent.setText(remark.getDescription());
        }
    }
    private Object getItem(int position) {
        return itemObjects.get(position);
    }

    @Override
    public int getItemCount() {
        return itemObjects.size();
    }
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }
    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}

package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Remark;

public class RequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER    = 0;
    private static final int TYPE_ITEM      = 1;
    private List<Object> mItems;

    public RequestAdapter(List<Object> items) {
        this.mItems = items;
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
        if(holder instanceof HeaderViewHolder){
            String key = getItem(position).toString();
            ((HeaderViewHolder) holder).headerTitle.setText(key);
        }else if(holder instanceof ItemViewHolder){
            Remark remark = (Remark)getItem(position);
            long date = remark.getDate();

            ((ItemViewHolder) holder).itemUser.setText(remark.getUser());
            ((ItemViewHolder) holder).itemContent.setText(remark.getDescription());
            ((ItemViewHolder) holder).itemTime.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.ROOT).format(date));
        }
    }

    private Object getItem(int position) {
        return mItems.get(position);//3
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))//1
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        if (getItem(position) instanceof String)//2
            return true;

        return false; //return position == 0;
    }
}

package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import standardsoft.com.coraje.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemUser, itemContent, itemTime;
    public ItemViewHolder(View itemView) {
        super(itemView);
        itemUser    = (TextView)itemView.findViewById(R.id.item_user);
        itemContent = (TextView)itemView.findViewById(R.id.item_content);
        itemTime    = (TextView)itemView.findViewById(R.id.item_time);
    }
}

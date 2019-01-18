package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import standardsoft.com.coraje.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView itemContent;
    public ItemViewHolder(View itemView) {
        super(itemView);
        itemContent = (TextView)itemView.findViewById(R.id.item_content);
    }
}

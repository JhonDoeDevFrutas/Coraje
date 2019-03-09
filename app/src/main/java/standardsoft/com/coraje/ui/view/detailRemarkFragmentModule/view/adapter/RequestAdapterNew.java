package standardsoft.com.coraje.ui.view.detailRemarkFragmentModule.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import standardsoft.com.coraje.R;
import standardsoft.com.coraje.data.model.entities.Bugs;
import standardsoft.com.coraje.data.model.entities.PromptTask;
import standardsoft.com.coraje.data.model.entities.Remark;

public class RequestAdapterNew extends BaseAdapter {

    private static final int TYPE_HEADER        = 0;
    private static final int TYPE_ITEM          = 1;
    private static final int TYPE_ITEM_BUGS     = 2;
    private static final int TYPE_ITEM_PROMPT   = 3;

    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<Object> mItems;

    public RequestAdapterNew(Context context, ArrayList<Object> items) {
        this.mContext = context;
        this.mItems = items;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        int type = getItemViewType(position);
        if (view == null){
            switch (type){
                case TYPE_HEADER:
                    view = inflater.inflate(R.layout.item_header_remark, parent, false);
                    break;

                case TYPE_ITEM:
                    view = inflater.inflate(R.layout.item_detail_remark_new, parent, false);
                    break;

                case TYPE_ITEM_BUGS:
                    view = inflater.inflate(R.layout.item_detail_prompt_task, parent, false);
                    break;
                case TYPE_ITEM_PROMPT:
                    view = inflater.inflate(R.layout.item_detail_prompt_task, parent, false);
                    break;
            }
        }

        TextView txtUser, txtTime, txtTask,txtStatus, txtContent;
        String status;
        long date;

        switch (type){
            case TYPE_HEADER:
                String key = getItem(position).toString();

                TextView txtHeader = (TextView)view.findViewById(R.id.header_id);
                txtHeader.setText(key);
                break;

            case TYPE_ITEM:
                Remark remark = (Remark)getItem(position);
                status = remark.getSubPlanning().getStatus().getDescription();
                date = remark.getDate();

                txtUser    = (TextView)view.findViewById(R.id.item_user);
                txtTask    = (TextView)view.findViewById(R.id.item_task);
                txtStatus  = (TextView)view.findViewById(R.id.item_status);
                txtContent = (TextView)view.findViewById(R.id.item_content);
                txtTime             = (TextView)view.findViewById(R.id.item_time);

                switch (status){
                    case "ESPERANDO REVISION":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorBlack));

                        break;
                    case "EN PROCESO":
                        //txtStatus.setTextColor(Color.parseColor("#F1AD54"));
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorOrange));


                        break;
                    case "LISTO":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                        break;
                }

                txtUser.setText(remark.getUser());
                txtTask.setText(remark.getSubPlanning().getTask());
                txtStatus.setText(remark.getSubPlanning().getStatus().getDescription());
                txtContent.setText(remark.getDescription());
                txtTime.setText(new SimpleDateFormat("hh:mm a", Locale.ROOT).format(date));
                break;
            case TYPE_ITEM_BUGS:
                Bugs bugs = (Bugs)getItem(position);
                status = bugs.getStatus().getDescription();
                date = bugs.getDate();

                txtUser    = (TextView)view.findViewById(R.id.item_user);
                txtTime    = (TextView)view.findViewById(R.id.item_time);
                txtTask    = (TextView)view.findViewById(R.id.item_task);
                txtStatus  = (TextView)view.findViewById(R.id.item_status);
                txtContent  = (TextView)view.findViewById(R.id.item_content);

                switch (status){
                    case "ESPERANDO REVISION":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                        break;
                    case "EN PROCESO":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorOrange));
                        break;
                    case "LISTO":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                        break;
                }

                txtUser.setText(bugs.getAssignee().getName());
                txtTime.setText(new SimpleDateFormat("hh:mm a", Locale.ROOT).format(date));
                txtTask.setText(bugs.getTask());
                txtStatus.setText(bugs.getStatus().getDescription());
                txtContent.setText(bugs.getDescription());
                break;
            case TYPE_ITEM_PROMPT:
                view.setBackgroundColor(Color.parseColor("#e7eecc"));
                PromptTask promptTask = (PromptTask) getItem(position);
                status  = promptTask.getStatus().getDescription();
                date    = promptTask.getDate();

                txtUser    = (TextView)view.findViewById(R.id.item_user);
                txtTime    = (TextView)view.findViewById(R.id.item_time);
                txtTask    = (TextView)view.findViewById(R.id.item_task);
                txtStatus  = (TextView)view.findViewById(R.id.item_status);
                txtContent  = (TextView)view.findViewById(R.id.item_content);

                switch (status){
                    case "ESPERANDO REVISION":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                        break;
                    case "EN PROCESO":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorOrange));
                        break;
                    case "LISTO":
                        txtStatus.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                        break;
                }

                txtUser.setText(promptTask.getAssignee() != null ? promptTask.getAssignee().getName() : "");
                txtTime.setText(new SimpleDateFormat("hh:mm a", Locale.ROOT).format(date));
                txtTask.setText(promptTask.getTask());
                txtStatus.setText(promptTask.getStatus().getDescription());
                txtContent.setText(promptTask.getDescription());
                break;
        }

        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof String){
            return TYPE_HEADER;
        }if (getItem(position) instanceof Remark){
            return TYPE_ITEM;
        }if (getItem(position) instanceof Bugs){
            return TYPE_ITEM_BUGS;
        }else {
            return TYPE_ITEM_PROMPT;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return (getItemViewType(position) == TYPE_ITEM);
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }
}

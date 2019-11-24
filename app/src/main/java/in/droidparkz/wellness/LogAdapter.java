package in.droidparkz.wellness;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
    private Context mContext;
    private ArrayList<LogItem> mLogList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public LogAdapter(Context context, ArrayList<LogItem> LogList) {
        mContext = context;
        mLogList = LogList;
    }

    @Override
    public LogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.log_contents, parent, false);
        return new LogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LogViewHolder holder, int position) {
        LogItem currentItem = mLogList.get(position);

        String name = currentItem.getName();
        String date = currentItem.getDate();
        String image = currentItem.getImage();

        holder.mTextViewName.setText(name);
        holder.mTextViewDate.setText(date);
        holder.link = image;
    }

    @Override
    public int getItemCount() {

        return mLogList.size();
    }

    public class LogViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewName,mTextViewDate;

        public ImageView view;

        public String link;

        public Context context;

        public LogViewHolder(View itemView) {
            super(itemView);

            mTextViewName = itemView.findViewById(R.id.logname);
            mTextViewDate = itemView.findViewById(R.id.logdate);
            view = itemView.findViewById(R.id.logview);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url= link;
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(url));
                    Context context = view.getContext();
                    context.startActivity(intent);
                }
            });

        }
    }
}

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

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LifelineAdapter extends RecyclerView.Adapter<LifelineAdapter.LifelineViewHolder> {
    private Context mContext;
    private ArrayList<LifelineItem> mLifelineList;

    public LifelineAdapter(Context context, ArrayList<LifelineItem> LifelineList) {
        mContext = context;
        mLifelineList = LifelineList;
    }

    @Override
    public LifelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.lifeline_content, parent, false);
        return new LifelineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(LifelineViewHolder holder, int position) {
        LifelineItem currentItem = mLifelineList.get(position);

        String name = currentItem.getName();
        String email = currentItem.getEmail();
        String contact = currentItem.getContact();

        holder.mTextViewName.setText(name);
        holder.mTextViewEmail.setText(email);
        holder.mTextViewContact.setText(contact);
    }

    @Override
    public int getItemCount() {

        return mLifelineList.size();
    }

    public class LifelineViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewName,mTextViewEmail,mTextViewContact;

        public ImageView call;

        public LifelineViewHolder(View itemView) {
            super(itemView);

            mTextViewName = itemView.findViewById(R.id.lifelinename);
            mTextViewEmail = itemView.findViewById(R.id.lifelineemail);
            mTextViewContact = itemView.findViewById(R.id.lifelinecontact);

            call = itemView.findViewById(R.id.lifelinecall);
            call.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onClick(View view) {
                    String url= mTextViewContact.getText().toString();
                    Context context = view.getContext();
                    Intent call = new Intent(Intent.ACTION_CALL);
                    call.setData(Uri.parse("tel:" + url ));
                    context.startActivity(call);
                }
            });

        }
    }
}

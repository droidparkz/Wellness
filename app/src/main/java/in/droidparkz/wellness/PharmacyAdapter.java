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

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyAdapter.PharmacyViewHolder> {
    private Context mContext;
    private ArrayList<PharmacyItem> mPharmacyList;

    public PharmacyAdapter(Context context, ArrayList<PharmacyItem> PharmacyList) {
        mContext = context;
        mPharmacyList = PharmacyList;
    }

    @Override
    public PharmacyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.pharmacy_content, parent, false);
        return new PharmacyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PharmacyViewHolder holder, int position) {
        PharmacyItem currentItem = mPharmacyList.get(position);

        String name = currentItem.getName();
        String quantity = currentItem.getQuantity();
        String price = currentItem.getPrice();

        holder.mTextViewName.setText(name);
        holder.mTextViewQuantity.setText(quantity);
        holder.mTextViewPrice.setText("₹ "+price);
    }

    @Override
    public int getItemCount() {

        return mPharmacyList.size();
    }

    public class PharmacyViewHolder extends RecyclerView.ViewHolder  {

        public TextView mTextViewName,mTextViewQuantity,mTextViewPrice;


        public PharmacyViewHolder(View itemView) {
            super(itemView);

            mTextViewName = itemView.findViewById(R.id.pharmacyname);
            mTextViewQuantity = itemView.findViewById(R.id.pharmacyquantity);
            mTextViewPrice = itemView.findViewById(R.id.pharmacyprice);


        }
    }
}

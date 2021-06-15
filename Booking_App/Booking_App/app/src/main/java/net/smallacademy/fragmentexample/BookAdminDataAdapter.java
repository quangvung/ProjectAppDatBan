package net.smallacademy.fragmentexample;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.List;


public class BookAdminDataAdapter extends RecyclerView.Adapter<BookAdminDataAdapter.myviewholder> {

    private List<BookAdminDataModel> AdminData;

    public BookAdminDataAdapter(List<BookAdminDataModel> adminData) {
        AdminData = adminData;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookAdminDataAdapter.myviewholder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.booking_datalog_admin_layout,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdminDataAdapter.myviewholder holder, int position) {
        final BookAdminDataModel dataItem = AdminData.get(position);
        holder.booking_count.setText("Booking count: "+ dataItem.getBooking_Count());
        holder.name_res_b.setText("Name restaurant: "+dataItem.getName_res());
        holder.layout_card.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(),BookAdminDataActivity.class);
            intent.putExtra("postkey",dataItem.getPostkey());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return AdminData.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView name_res_b,booking_count;
        CardView layout_card;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            layout_card = itemView.findViewById(R.id.layout_card);
            booking_count = itemView.findViewById(R.id.booking_count);
            name_res_b = itemView.findViewById(R.id.name_res_b);
        }
    }
}

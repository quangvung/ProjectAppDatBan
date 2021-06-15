package net.smallacademy.fragmentexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BookAdminInDataAdapter extends FirebaseRecyclerAdapter<BookAdminInDataModel, BookAdminInDataAdapter.myviewholder> {
    public BookAdminInDataAdapter(@NonNull FirebaseRecyclerOptions<BookAdminInDataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookAdminInDataAdapter.myviewholder holder, int position, @NonNull BookAdminInDataModel model) {
        holder.name_user_b.setText("User name: "+model.getUser_name());
        holder.person_b.setText("Person: "+model.getPerson());
        holder.place_b.setText("Place: "+model.getWhere());
        holder.seat_number_b.setText("Seat number:"+model.getSeats());
    }

    @NonNull
    @Override
    public BookAdminInDataAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_datalog_admin_in_layout, parent, false);

        return new BookAdminInDataAdapter.myviewholder(view);
    }
    @Override
    public int getItemCount(){
        return super.getItemCount();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView name_user_b,place_b,person_b,seat_number_b;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            name_user_b = itemView.findViewById(R.id.name_user_b);
            place_b = itemView.findViewById(R.id.place_b);
            person_b = itemView.findViewById(R.id.person_b);
            seat_number_b = itemView.findViewById(R.id.seat_number_b);
        }
    }
}

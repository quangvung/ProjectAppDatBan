package net.smallacademy.fragmentexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BookUserDataAdapter extends FirebaseRecyclerAdapter<BookUserDataModel, BookUserDataAdapter.myviewholder> {
    public BookUserDataAdapter(@NonNull FirebaseRecyclerOptions<BookUserDataModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookUserDataAdapter.myviewholder holder, int position, @NonNull BookUserDataModel model) {
        holder.name_res_b.setText("Name restaurant: "+model.getName_res());
        holder.person_b.setText("Person: "+model.getPerson());
        holder.place_b.setText("Place: "+model.getWhere());
        holder.seat_number_b.setText("Seat number:"+model.getSeats());
    }

    @NonNull
    @Override
    public BookUserDataAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_datalog_user_layout, parent, false);

        return new BookUserDataAdapter.myviewholder(view);
    }
    @Override
    public int getItemCount(){
        return super.getItemCount();
    }

    public class myviewholder extends RecyclerView.ViewHolder {
        TextView name_res_b,place_b,person_b,seat_number_b;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name_res_b = itemView.findViewById(R.id.name_res_b);
            place_b = itemView.findViewById(R.id.place_b);
            person_b = itemView.findViewById(R.id.person_b);
            seat_number_b = itemView.findViewById(R.id.seat_number_b);
        }
    }
}

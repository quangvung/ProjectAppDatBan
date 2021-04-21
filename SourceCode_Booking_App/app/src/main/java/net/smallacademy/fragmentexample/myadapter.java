package net.smallacademy.fragmentexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    Boolean testclick=false;
    DatabaseReference likereference;

    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final model model)
    {
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        Glide.with(holder.purl.getContext()).load(model.getPurl()).into(holder.purl);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final String userid=firebaseUser.getUid();
        final String postkey=getRef(position).getKey();

        holder.getlikebuttonstatus(postkey,userid);
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent intent
                        = new Intent(context,
                        Detail_CardView.class);
                intent.putExtra("titleC", model.getTitle());
                intent.putExtra("purlC", model.getPurl());
                intent.putExtra("nameC", model.getName());
                intent.putExtra("purl1", model.getPurl1());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testclick=true;

                likereference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(testclick==true)
                        {
                            if(snapshot.child(postkey).hasChild(userid))
                            {
                                likereference.child(postkey).child(userid).removeValue();
                                testclick=false;
                            }
                            else
                            {
                                likereference.child(postkey).child(userid).setValue(true);
                                testclick=false;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

//        holder.edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final DialogPlus dialogPlus=DialogPlus.newDialog(holder.purl.getContext())
//                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
//                        .setExpanded(true,1100)
//                        .create();
//
//                View myview=dialogPlus.getHolderView();
//                final EditText purl=myview.findViewById(R.id.uimgurl);
//                final EditText name=myview.findViewById(R.id.uname);
//                final EditText course=myview.findViewById(R.id.ucourse);
//                final EditText email=myview.findViewById(R.id.uemail);
//                Button submit=myview.findViewById(R.id.usubmit);
//
//                purl.setText(model.getPurl());
//                name.setText(model.getName());
//                course.setText(model.getCourse());
//                email.setText(model.getEmail());
//
//                dialogPlus.show();
//
//                submit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Map<String,Object> map=new HashMap<>();
//                        map.put("purl",purl.getText().toString());
//                        map.put("name",name.getText().toString());
//                        map.put("email",email.getText().toString());
//                        map.put("course",course.getText().toString());
//
//                        FirebaseDatabase.getInstance().getReference().child("students")
//                                .child(getRef(position).getKey()).updateChildren(map)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void aVoid) {
//                                        dialogPlus.dismiss();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        dialogPlus.dismiss();
//                                    }
//                                });
//                    }
//                });
//            }
//        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.purl.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete...?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("restaurants`")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        ImageView purl;
        TextView name,address;
        ImageView like_btn;
        TextView like_text;
        CardView cardview;
        ImageView edit,delete;

        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            purl=itemView.findViewById(R.id.purl);
            name=itemView.findViewById(R.id.nametext);
            address=itemView.findViewById(R.id.addressText);
            like_btn=itemView.findViewById(R.id.like_btn);
            like_text=itemView.findViewById(R.id.like_text);
            cardview = itemView.findViewById(R.id.cardview);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
        public void getlikebuttonstatus(final String postkey, final String userid)
        {
            likereference= FirebaseDatabase.getInstance().getReference("likes");
            likereference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(postkey).hasChild(userid))
                    {
                        int likecount=(int)snapshot.child(postkey).getChildrenCount();
                        like_text.setText(likecount+" likes");
                        like_btn.setImageResource(R.drawable.ic_baseline_favorite_24);
                    }
                    else
                    {
                        int likecount=(int)snapshot.child(postkey).getChildrenCount();
                        like_text.setText(likecount+" likes");
                        like_btn.setImageResource(R.drawable.ic_baseline_favorite_24_unlike);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}

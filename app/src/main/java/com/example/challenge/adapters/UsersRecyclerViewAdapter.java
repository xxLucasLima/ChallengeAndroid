package com.example.challenge.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.challenge.MainActivity2;
import com.example.challenge.MainActivity3;
import com.example.challenge.R;
import com.example.challenge.entities.Profile;
import com.example.challenge.entities.User;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersAdapters> {

    List<Profile> mProfiles;
    Context mContext;
    User mUser;

    public UsersRecyclerViewAdapter(Context c, List<Profile> profiles, User user) {
        mContext = c;
        mProfiles = profiles;
        mUser = user;
    }

    @NonNull
    @NotNull
    @Override
    public UsersAdapters onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.row_users, parent, false);
        return new UsersAdapters(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull UsersAdapters holder, int position) {
        if (mProfiles.get(position).getBirth_date() != null) {
            holder.birth_date.setText(DateFormatter(mProfiles.get(position).getBirth_date()));
        } else
            holder.birth_date.setText(mProfiles.get(position).getBirth_date());

        holder.name.setText(mProfiles.get(position).getFirst_name() + " "
                + mProfiles.get(position).getLast_name());

        Glide.with(mContext)
                .load(mProfiles.get(position).getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String BirthDate = null;
                if (mProfiles.get(position).getBirth_date() != null)
                    BirthDate = DateFormatter(mProfiles.get(position).getBirth_date());

                Intent intent = new Intent(mContext, MainActivity3.class);
                intent.putExtra("id", mProfiles.get(position).getId());
                intent.putExtra("first_name", mProfiles.get(position).getFirst_name());
                intent.putExtra("last_name", mProfiles.get(position).getLast_name());
                intent.putExtra("birth_date", BirthDate);
                intent.putExtra("avatar", mProfiles.get(position).getAvatar());
                intent.putExtra("loggedUser", mUser);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProfiles.size();
    }

    public class UsersAdapters extends RecyclerView.ViewHolder {

        TextView name, birth_date, height, weight, goal, gender;
        ImageView image;
        ConstraintLayout mainLayout;

        public UsersAdapters(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            birth_date = itemView.findViewById(R.id.birth_date);
            image = itemView.findViewById(R.id.image);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public String DateFormatter(String Date){
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat DFormat = DateFormat.getDateInstance();
        String str = DFormat.format(newDate);
        DFormat = new SimpleDateFormat("dd/MM/yyyy");
        str = DFormat.format(newDate);

        return str;
    }
}

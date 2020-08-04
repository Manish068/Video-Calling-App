package com.andoiddevop.face2face.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andoiddevop.face2face.R;
import com.andoiddevop.face2face.listeners.UsersListener;
import com.andoiddevop.face2face.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private UsersListener usersListener;

    public UserAdapter(List<User> users,UsersListener usersListener) {
        this.users = users; this.usersListener=usersListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_container,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public  class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textFirstChar, textUsername, textEmail;
        ImageView imageCall,imageVideoCall;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            textFirstChar=itemView.findViewById(R.id.textFirstChar);
            textUsername=itemView.findViewById(R.id.textUsername);
            textEmail=itemView.findViewById(R.id.textEmail);
            imageCall=itemView.findViewById(R.id.imageCall);
            imageVideoCall=itemView.findViewById(R.id.imageVideoCall);




        }

        void setUserData(User userData){
            textFirstChar.setText(userData.firstName.substring(0,1));
            textUsername.setText(String.format("%s %s",userData.firstName,userData.lastName));
            textEmail.setText(userData.email);

            imageCall.setOnClickListener(v -> usersListener.initiateAudioMeeting(userData));

            imageVideoCall.setOnClickListener(v -> usersListener.initiateVideoMeeting(userData));
        }
    }
}

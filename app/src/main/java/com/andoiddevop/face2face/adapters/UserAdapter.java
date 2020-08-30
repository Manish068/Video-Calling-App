package com.andoiddevop.face2face.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andoiddevop.face2face.R;
import com.andoiddevop.face2face.listeners.UsersListener;
import com.andoiddevop.face2face.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> users;
    private UsersListener usersListener;
    private List<User> selectedUser;

    public UserAdapter(List<User> users, UsersListener usersListener) {
        this.users = users;
        this.usersListener = usersListener;
        selectedUser = new ArrayList<>();
    }

    public List<User> getSelectedUser(){
        return selectedUser;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {

        TextView textFirstChar, textUsername, textEmail;
        ImageView imageCall, imageVideoCall;
        ConstraintLayout userContainer;
        ImageView imageSelected;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            textFirstChar = itemView.findViewById(R.id.textFirstChar);
            textUsername = itemView.findViewById(R.id.textUsername);
            textEmail = itemView.findViewById(R.id.textEmail);
            imageCall = itemView.findViewById(R.id.imageCall);
            imageVideoCall = itemView.findViewById(R.id.imageVideoCall);
            userContainer = itemView.findViewById(R.id.userContainer);
            imageSelected = itemView.findViewById(R.id.imageSelected);


        }

        void setUserData(User userData) {
            textFirstChar.setText(userData.firstName.substring(0, 1));
            textUsername.setText(String.format("%s %s", userData.firstName, userData.lastName));
            textEmail.setText(userData.email);

            imageCall.setOnClickListener(v -> usersListener.initiateAudioMeeting(userData));

            imageVideoCall.setOnClickListener(v -> usersListener.initiateVideoMeeting(userData));

            userContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    selectedUser.add(userData);
                    imageSelected.setVisibility(View.VISIBLE);
                    imageCall.setVisibility(View.GONE);
                    imageVideoCall.setVisibility(View.GONE);
                    usersListener.onMultipleUsersAction(true);
                    return true;
                }
            });

            userContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (imageSelected.getVisibility() == View.VISIBLE) {
                        selectedUser.remove(userData);
                        imageSelected.setVisibility(View.GONE);
                        imageCall.setVisibility(View.VISIBLE);
                        imageVideoCall.setVisibility(View.VISIBLE);
                        if (selectedUser.size() == 0) {
                            usersListener.onMultipleUsersAction(false);
                        } else {
                            if (selectedUser.size() > 0) {
                                selectedUser.add(userData);
                                imageSelected.setVisibility(View.VISIBLE);
                                imageVideoCall.setVisibility(View.GONE);
                                imageCall.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });
        }
    }
}

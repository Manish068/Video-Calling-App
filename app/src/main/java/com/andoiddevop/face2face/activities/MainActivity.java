package com.andoiddevop.face2face.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.andoiddevop.face2face.R;
import com.andoiddevop.face2face.adapters.UserAdapter;
import com.andoiddevop.face2face.listeners.UsersListener;
import com.andoiddevop.face2face.models.User;
import com.andoiddevop.face2face.utilities.Constants;
import com.andoiddevop.face2face.utilities.PreferenceManager;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UsersListener {

    private PreferenceManager preferenceManager;
    private List<User> users;
    private UserAdapter userAdapter;
    private TextView textErrorMessage;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView imageConference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferenceManager = new PreferenceManager(this);

        imageConference= findViewById(R.id.imageConference);

        TextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(String.format(
                "%s %s",
                preferenceManager.getString(Constants.KEY_FIRST_NAME),
                preferenceManager.getString(Constants.KEY_LAST_NAME)
        ));

        findViewById(R.id.textSignOut).setOnClickListener(v -> signOut());

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                sendFCMTokenToDatabase(task.getResult().getToken());
            }
        });


        swipeRefreshLayout=findViewById(R.id.SwipeRefreshLayout);
        RecyclerView userRecyclerView = findViewById(R.id.usersRecyclerView);
        textErrorMessage = findViewById(R.id.textErrorMessage);


        users = new ArrayList<>();
        userAdapter = new UserAdapter(users,this);
        userRecyclerView.setAdapter(userAdapter);

        swipeRefreshLayout.setOnRefreshListener(this::getUsers);

        getUsers();
    }

    private void getUsers() {
        swipeRefreshLayout.setRefreshing(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .get()
                .addOnCompleteListener(task -> {
                    swipeRefreshLayout.setRefreshing(false);
                    String myuserId= preferenceManager.getString(Constants.KEY_USER_ID);
                    if (task.isSuccessful() && task != null) {
                        users.clear();                                                            //refreshing multiple time so we need to clear user data
                        for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                            if(myuserId.equals(documentSnapshot.getId())){                       //Here, we will display the user list except for the currently signed in user, Because
                              continue;                                                          //no one will have meeting with himself. That's why we are excluding a signed -in user from the list
                            }
                            User user= new User();
                            user.firstName =documentSnapshot.getString(Constants.KEY_FIRST_NAME);
                            user.lastName =documentSnapshot.getString(Constants.KEY_LAST_NAME);
                            user.email = documentSnapshot.getString(Constants.KEY_EMAIL);
                            user.token = documentSnapshot.getString(Constants.KEY_FCM_TOKEN);
                            users.add(user);
                        }
                        if(users.size()>0){
                            userAdapter.notifyDataSetChanged();
                        }else{
                            textErrorMessage.setText(String.format("%s","No users available"));
                            textErrorMessage.setVisibility(View.VISIBLE);
                        }
                    }else{
                        textErrorMessage.setText(String.format("%s","No users available"));
                        textErrorMessage.setVisibility(View.VISIBLE);
                    }
                });
    }

    private void sendFCMTokenToDatabase(String token) {
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID));

        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "TUnable to send token :" + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void signOut() {
        Toast.makeText(this, "Signing Out....", Toast.LENGTH_SHORT).show();
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(preferenceManager.getString(Constants.KEY_USER_ID));

        HashMap<String, Object> updates = new HashMap<>();
        updates.put(Constants.KEY_FCM_TOKEN, FieldValue.delete());
        documentReference.update(updates)
                .addOnSuccessListener(aVoid -> {
                    preferenceManager.clearPreferences();
                    startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Unable to sign out", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void initiateVideoMeeting(User user) {
        //if user token is null then we display user is not available
        if(user.token == null || user.token.trim().isEmpty()){
            Toast.makeText(
                    this,
                    user.firstName +" "+ user.lastName +" is not available for meeting",
                    Toast.LENGTH_SHORT
            ).show();
        }else {
            Intent intent = new Intent(getApplicationContext(),OutgoingInvitationActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("type","video");
            startActivity(intent);
        }
    }

    @Override
    public void initiateAudioMeeting(User user) {
        if(user.token == null || user.token.trim().isEmpty()){
            Toast.makeText(
                    this,
                    user.firstName +" "+ user.lastName +" is not available for meeting",
                    Toast.LENGTH_SHORT
            ).show();
        }else {
            Intent intent = new Intent(getApplicationContext(),OutgoingInvitationActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("type","audio");
            startActivity(intent);
        }
    }

    @Override
    public void onMultipleUsersAction(Boolean isMultipleUserSelected) {
        if(isMultipleUserSelected){
            imageConference.setVisibility(View.VISIBLE);
            imageConference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),OutgoingInvitationActivity.class);
                    intent.putExtra("selectedUsers", new Gson().toJson(userAdapter.getSelectedUser()));
                    intent.putExtra("type","video");
                    intent.putExtra("isMultiple",true);
                    startActivity(intent);
                }
            });
        } else{
            imageConference.setVisibility(View.GONE);
        }
    }
}
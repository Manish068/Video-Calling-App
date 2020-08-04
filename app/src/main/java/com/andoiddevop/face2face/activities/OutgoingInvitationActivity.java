package com.andoiddevop.face2face.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andoiddevop.face2face.R;
import com.andoiddevop.face2face.models.User;

public class OutgoingInvitationActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_invitation);

        ImageView imageMeetingType=findViewById(R.id.imageMeetingType);
        String meetingType= getIntent().getStringExtra("type");

        if(meetingType != null){
            if(meetingType.equals("video")){
                imageMeetingType.setImageResource(R.drawable.ic_video);
            }
        }

        TextView textFirstChar = findViewById(R.id.textFirstChar);
        TextView textUsername=findViewById(R.id.textUsername);
        TextView textEmail=findViewById(R.id.textEmail);

        User user = (User) getIntent().getSerializableExtra("user");
        if(user != null){
            textFirstChar.setText(user.firstName.substring(0,1));
            textUsername.setText(String.format("%s %s",user.firstName,user.lastName));
            textEmail.setText(user.email);
        }

        ImageView imageStopInvitaiton=findViewById(R.id.imageStopInvitation);

        imageStopInvitaiton.setOnClickListener(v -> onBackPressed());
    }
}
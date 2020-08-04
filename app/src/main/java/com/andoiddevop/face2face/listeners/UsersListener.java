package com.andoiddevop.face2face.listeners;

import com.andoiddevop.face2face.models.User;

public interface UsersListener {
    void initiateVideoMeeting(User user);
    void initiateAudioMeeting(User user);
}

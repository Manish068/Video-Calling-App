package com.andoiddevop.face2face.utilities;

import java.util.HashMap;

public class Constants {
    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_PREFERENCE_NAME = ".videoMeetingPrefer";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";

    /*TO send and recieve a meeting invitation we need an FCM token of a particular user.
    Therefore we will update user's token in database after successful signup/sign in and
    will remove the token from database on signout*/

    public static final String KEY_FCM_TOKEN = "fcm_token";

    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";

    public static final String REMOTE_MSG_TYPE="type";
    public static final String REMOTE_MSG_INVITATION = "invitation";
    public static final String REMOTE_MSG_MEETING_TYPE = "meetingType";
    public static final String REMOTE_MSG_INVITER_TOKEN = "inviterToken";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static final String REMOTE_MSG_INVITATION_RESPONSE = "invitationResponse";

    public static final String REMOTE_MSG_INVITATION_ACCEPTED= "accepted";
    public static final String REMOTE_MSG_INVITATION_REJECTED= "rejected";
    public static final String REMOTE_MSG_INVITATION_CANCELLED= "cancelled";

    public static final String REMOTE_MSG_MEETING_ROOM= "meetingRoom";

    public static HashMap<String, String> getRemoteMessageHeaders(){
        HashMap<String, String> headers = new HashMap<>();
        headers.put(Constants.REMOTE_MSG_AUTHORIZATION,
                "key=AAAAR4icudE:APA91bGWArFT2Ha0CcP1E-A0ni-YbWv_2RJsCRCC3iLdIPmp3qln7plXFU2QlRwmCzyIh1RCoXVJR8exeLFWxx0NT3Nb0UPh9TwoAmZAjZq-W9Zf4EX96w_R-PkO5_sJ9nKUAbnsraSD");

        headers.put(Constants.REMOTE_MSG_CONTENT_TYPE, "application/json");
        return headers;
    }
}

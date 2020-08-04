package com.andoiddevop.face2face.utilities;

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
}

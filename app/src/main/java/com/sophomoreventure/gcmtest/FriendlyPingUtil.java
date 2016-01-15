package com.sophomoreventure.gcmtest;

import android.content.Context;

public class FriendlyPingUtil {

    public static String getServerUrl(Context context) {
        return context.getString(R.string.gcm_defaultSenderId) + "@gcm.googleapis.com";
    }

}

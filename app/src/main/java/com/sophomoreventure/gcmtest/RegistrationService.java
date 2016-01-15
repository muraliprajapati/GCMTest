package com.sophomoreventure.gcmtest;

/**
 * Created by Murali on 07/01/2016.
 */

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Deal with registration of the user with the GCM instance.
 */
public class RegistrationService extends IntentService {

    private static final String TAG = "tag";

    public RegistrationService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        try {
            // Just in case that onHandleIntent has been triggered several times in short
            // succession.
            synchronized (TAG) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                        GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.d(TAG, "GCM registration token: " + token);

                // Register to the server and subscribe to the topic of interest.
                sendRegistrationToServer(token);

                final SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(RegistrationConstants.SENT_TOKEN_TO_SERVER, true);
                editor.putString(RegistrationConstants.TOKEN, token);
                editor.apply();
            }
        } catch (IOException e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(RegistrationConstants.
                    SENT_TOKEN_TO_SERVER, false).apply();

        }
        Intent registrationComplete = new Intent(RegistrationConstants.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /**
     * Sends the registration to the server.
     *
     * @param token The token to send.
     * @throws IOException Thrown when a connection issue occurs.
     */
    private void sendRegistrationToServer(String token) throws IOException {
//        final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
//                .addApi(Plus.API)
//                .addScope(Plus.SCOPE_PLUS_PROFILE)
//                .build();
//        googleApiClient.blockingConnect();
//
//        Bundle registration = createRegistrationBundle(googleApiClient);
//        registration.putString(PingerKeys.REGISTRATION_TOKEN, token);
//
//        // Register the user at the server.
//        GoogleCloudMessaging.getInstance(this).send(FriendlyPingUtil.getServerUrl(this),
//                String.valueOf(System.currentTimeMillis()), registration);
    }


}

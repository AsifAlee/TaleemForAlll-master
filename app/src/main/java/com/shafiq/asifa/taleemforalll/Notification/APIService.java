package com.shafiq.asifa.taleemforalll.Notification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService  {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAYx_diWg:APA91bHKmUxzZTTodh1tSSe8hK0rAzyMl91EcDEOKf7CkN1ey0H0_nL9tZi4dWB44KhxQu-QXkg7NvkDuBzlY4D6mEqoiy5xpCKTYyEm46HWneapJeb0tbJlBY5IYa751LFp8rAOWWVu"

            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}

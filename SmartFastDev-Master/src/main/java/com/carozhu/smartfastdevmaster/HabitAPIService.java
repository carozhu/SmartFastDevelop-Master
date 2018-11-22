package com.carozhu.smartfastdevmaster;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HabitAPIService {
    @FormUrlEncoded
    @POST("/interface/GetConfig.php")
    Observable<ConfigBean> getConfig(@FieldMap Map<String, String> map);

}

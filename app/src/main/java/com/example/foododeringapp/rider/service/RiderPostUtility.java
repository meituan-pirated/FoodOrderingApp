package com.example.foododeringapp.rider.service;


import com.example.foododeringapp.bean.RestFulBean;
import com.google.gson.reflect.TypeToken;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;

public class RiderPostUtility {

    private static Requests requestManager = new Requests();
    private static String requestUrl = Util.Url;

    public static String ChangeRiderInfo(String riderID, String newNickName, String newSex, String finalPwd, String advatarEncoded){
        String postForm = null;
        try {
            postForm = requestManager.postForm(requestUrl+"RiderOpera/changeRiderInfo",
                    "rider_id",riderID,
                    "nick_name",newNickName,
                    "sex", newSex,
                    "password",finalPwd,
                    "advatar",advatarEncoded,
                    "rider_id", riderID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  postForm;
    }
}
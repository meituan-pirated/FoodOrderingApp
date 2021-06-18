package com.example.foododeringapp.merchant.service;

import com.example.foododeringapp.bean.Products;
import com.example.foododeringapp.service.Requests;
import com.example.foododeringapp.util.Util;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * 所有对数据库的操作都需要用post方法，以保证机密性
 */
public class MerchantPostUtility {
    private static Requests requestManager = new Requests();
    private static String requestUrl = Util.Url;

    /**
     * 修改订单状况
     * @param order_id
     * @param order_state
     * @return
     */
    public static String updateOrderState(int order_id,String order_state){
        String postForm = null;
        try {
            postForm = requestManager.postForm(requestUrl+"MerchantOpera/changeOrderState",
                    "order_id", order_id+"",
                    "order_state",order_state);
        }catch (Exception e){
            e.printStackTrace();
        }
         return  postForm;
    }

    /**
     *删除商品
     * @param product_id
     */
    public static String deleteProduct(int product_id){
        String postForm = null;
        try {
            postForm = requestManager.postForm(requestUrl+"MerchantOpera/deleteProduct",
                    "product_id", product_id+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return  postForm;
    }

    /**
     * 编辑商品
     * @param products
     * @return
     */
    public static String saveProductChange(Products products){
        String postForm = null;
        try {
            postForm = requestManager.postForm(requestUrl+"MerchantOpera/saveProductChange",
                    "product_id", products.getProduct_id()+"",
                    "productName",products.getProductName(),
                    "descriptions", products.getDescriptions(),
                    "salePrice",products.getSalePrice()+"",
                    "image", products.getImage());
//            FormBody.Builder builder = new FormBody.Builder();
//            builder.add("product_id", products.getProduct_id()+"");
//            if(products.getProductName() != null){
//                builder.add("productName", products.getProductName());
//            }else{
//                builder.add("productName", "");
//            }
//            if(products.getDescriptions() != null){
//                builder.add("descriptions", products.getDescriptions());
//            }else{
//                builder.add("descriptions", "");
//            }
//            if(products.getSalePrice() != 0){
//                builder.add("salePrice",products.getSalePrice()+"");
//            }else{
//                builder.add("salePrice",0);
//            }
//            Request request = new Request.Builder().post(builder.build()).url(requestUrl+"MerchantOpera/saveProductChange").build();
//            postForm = requestManager.postForm(request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  postForm;
    }
}

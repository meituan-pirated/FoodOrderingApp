package com.example.foododeringapp.util;

/**
 * 自定义Listener
 *
 * 用于实现Dialog的复用
 *
 */
public interface IAlertDialogButtonListener {

    /**
     * 实现对话框的点击事件
     */
    void onDialogOkButtonClick();

    void onDialogCancelButtonClick();

}
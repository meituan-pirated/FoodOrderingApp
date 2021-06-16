package com.example.foododeringapp.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.foododeringapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * content: 底部显示的单选对话框
 * time: 2020-3-16
 *
 * @author: zhouqiang
 */

public class BottomDialog extends DialogFragment{

    private TextView dialog_camera, dialog_photos;
    private Button mBtn_Cancel;
    private int mSelectedPosition;

    private static final int PHOTO_FROM_GALLERY = 304;
    private static final int PHOTO_FROM_CAMERA = 305;
    private File appDir;
    private Uri uriForCamera;
    private Date date;
    private String str = "";

    private SharedPreferences sharePreference;
    private boolean selectClicked = false;


    @NonNull @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.i("dialog","create");
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
//        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        Dialog dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.dialog_bottom_single);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消

        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
//        window.setWindowAnimations(R.style.main_menu_animstyle);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);



//        ButterKnife.bind(this, dialog); // Dialog即View

//        initClickTypes();

        return dialog;
    }


        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            Log.i("dialog","create view");
            View view = inflater.inflate(R.layout.dialog_bottom_single, container, false);
            initListener(view);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            Log.i("dialog","view create ");
            super.onViewCreated(view, savedInstanceState);

        }

        @Override
        public void onStart() {
            Log.i("dialog","onstart");
            super.onStart();
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;//设置宽度为铺满
            params.gravity = Gravity.BOTTOM;
            getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        }



        private void initListener(View view) {
            dialog_camera = view.findViewById(R.id.dialog_camera);
            dialog_photos = view.findViewById(R.id.dialog_photos);
            mBtn_Cancel = view.findViewById(R.id.mBtn_Cancel);

            sharePreference = getActivity().getSharedPreferences("changeProductInfo", Activity.MODE_PRIVATE);


            mBtn_Cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //在activity里面处理点击事件
                    dismiss();
                }
            });

            dialog_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("click","camera");
                    selectClicked=true;
                    uriForCamera = Uri.fromFile(createImageStoragePath());
                    SharedPreferences.Editor editors = sharePreference.edit();
                    editors.putString("uri",  String.valueOf(uriForCamera));
                    editors.commit();//提交修改

                    Intent intent_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    /**
                     * 指定了uri路径，startActivityForResult不返回intent，
                     * 所以在onActivityResult()中不能通过data.getData()获取到uri;
                     */
                    intent_camera.putExtra(MediaStore.EXTRA_OUTPUT, uriForCamera);
                    startActivityForResult(intent_camera, PHOTO_FROM_CAMERA);
                }
            });

            dialog_photos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("click","photos");
                    selectClicked=true;
                    Intent intent_photos = new Intent();
                    intent_photos.setType("image/*");
                    intent_photos.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(intent_photos, PHOTO_FROM_GALLERY);
                }
            });
        }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //第一层switch
        switch (requestCode) {
            case PHOTO_FROM_GALLERY:
                //第二层switch
                switch (resultCode) {
                    case -1:
                        if (data != null) {
                            Log.i("test", "img");
                            try {
                                Uri uri = data.getData();
                                SharedPreferences.Editor editors = sharePreference.edit();
                                editors.putString("uri",  String.valueOf(uri));
                                ImageView change_img = getActivity().findViewById(R.id.change_img);
                                Bitmap bitmap = ImageTools.getBitmapFromUri(uri, getActivity());
                                change_img.setImageBitmap(bitmap);
//                                change_img.setImageURI(uri);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                    case 0:
                        break;
                }
                break;
            case PHOTO_FROM_CAMERA:
                if (resultCode == -1) {
                    Uri uri = Uri.parse(sharePreference.getString("uri", ""));
                    updateDCIM(uri);
                    try {
                        //把URI转换为Bitmap，并将bitmap压缩，防止OOM(out of memory)
                        Bitmap bitmap = ImageTools.getBitmapFromUri(uri, getActivity());
                        ImageView change_img = getActivity().findViewById(R.id.change_img);
                        change_img.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    removeCache("uri");
                } else {
                    Log.e("result", "is not ok" + resultCode);
                }
                break;
            default:
                break;
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("resume","resume");
        if(selectClicked){
            dismiss();
            selectClicked=false;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("pause","pause");
//        if(selectClicked){
////            dismiss();
//            selectClicked=false;
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("stop", "stop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




    /**
     * 设置相片存放路径，先将照片存放到SD卡中，再操作
     *
     * @return
     */
    private File createImageStoragePath() {
        if (hasSdcard()) {
            appDir = new File("/sdcard/testImage/");
            if (!appDir.exists()) {
                appDir.mkdirs();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            date = new Date();
            str = simpleDateFormat.format(date);
            String fileName = str + ".jpg";
            File file = new File(appDir, fileName);
            return file;
        } else {
            Log.e("sd", "is not load");
            return null;
        }
    }

    /**
     * 将照片插入系统相册，提醒相册更新
     *
     * @param uri
     */
    private void updateDCIM(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        getActivity().sendBroadcast(intent);

        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath());
        MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "", "");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 移除缓存
     *
     * @param cache
     */
    private void removeCache(String cache) {
        SharedPreferences.Editor editors = sharePreference.edit();
//        editors.putString("uri",  String.valueOf(uriForCamera));
//        if (sharePreference.ifHaveShare(cache)) {
        if (sharePreference.getString(cache,null) != null) {
//            sharePreference.removeOneCache(cache);
            editors.remove(cache);
//            sharePreference.removeOneCache(cache);
        } else {
            Log.e("this cache", "is not exist.");
        }
    }



}


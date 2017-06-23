package com.mj.weather.account.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.mj.weather.R;
import com.mj.weather.weather.MainActivity;
import com.mj.weather.common.base.BaseActivity;
import com.mj.weather.account.model.dp.CityItem;
import com.mj.weather.common.util.JsonUtils;
import com.mj.weather.common.util.LogUtils;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 应用启动短暂白屏处理
 * 初始化权限请求
 */
public class SplashActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "SplashActivity";

    private static final int RC_LOCATION_STORAGE_PHONE = 110;
    private static final int RC_SETTING_SCREEN = 111;
    private boolean isDbFinished = false;
    private boolean isPermsFinished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        //action
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isDbFinished && isPermsFinished) {
                        onNext();
                        return;
                    }
                }
            }
        }).start();

        //初始化权限
        initPermissions();

        //初始化数据库
        LitePal.getDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = DataSupport.count(CityItem.class);
                LogUtils.i(TAG, count+"");
                if (count == 0) {
                    initDatabase();
                }
                onDbFinished();
            }
        }).start();

    }

    private void initDatabase() {
        //获取城市json对象
        String json = "";
        InputStream is = null;
        try {
            is = getAssets().open("CityList.JSON");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            json = new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //获取城市集合
        List<CityItem> cityItemList = JsonUtils.toObjectArray(json, CityItem[].class);
        //添加到数据库
        DataSupport.saveAll(cityItemList);
    }

    @AfterPermissionGranted(RC_LOCATION_STORAGE_PHONE)
    private void initPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
        };
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //有权限 继续业务
            onPermsFinished();
        } else {
            //没权限，申请权限
            EasyPermissions.requestPermissions(this, getString(R.string.permission_init_alert),
                    RC_LOCATION_STORAGE_PHONE, permissions);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // Some permissions have been granted
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // Some permissions have been denied
        // 判断是否有权限被永久禁止
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AlertDialog.Builder(this).setMessage(getString(R.string.permission_init_not_granted))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.bt_setting), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                            startActivityForResult(intent, RC_SETTING_SCREEN);
                        }
                    })
                    .setNegativeButton(getString(R.string.bt_cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onPermsFinished();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        } else {
            onPermsFinished();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onPermsFinished();
    }

    private void onPermsFinished() {
        isPermsFinished = true;
    }

    private void onDbFinished() {
        isDbFinished = true;
    }

    private void onNext() {
        MainActivity.actionStart(this);
    }
}

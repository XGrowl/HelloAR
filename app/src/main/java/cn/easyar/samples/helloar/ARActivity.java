//================================================================================================================================
//
// Copyright (c) 2015-2019 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package cn.easyar.samples.helloar;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

import cn.easyar.CameraDevice;
import cn.easyar.Engine;
import cn.easyar.ImageTracker;
import cn.easyar.samples.helloar.pop.LetterDialog;
import cn.easyar.samples.helloar.pop.PopActivity;

public class ARActivity extends Activity
{
    /*
    * Steps to create the key for this sample:
    *  1. login www.easyar.com
    *  2. create app with
    *      Name: HelloAR
    *      Package Name: cn.easyar.samples.helloar
    *  3. find the created item in the list and show key
    *  4. set key string bellow
    */
    private Context mContext;
    private static String key = "EWq2vBV5rqANH1wxPQOeSB8afN3H3n/gcmyexyFYgJcVSIaKIUWRx24Jgpc7XIm6Yxyl1GYdy4Y7RsfJdkaEliBOl64xUqyBdhHUyXZHjIYxRZaAJwnfvi8Jh5A6T4mAHU+Wx25wuMl2XYSXPUqLkScJ3752SIqIOV6LjCBSx7h4CZWJNV+DiiZGlsducMeSPUWBiiNYx8l2RoSGdnbJxzlEgZA4TpbHbnDHljFFloB6YoiEM06xlzVIjow6TMfJdliAiydOy6Y4RJCBBk6GijNFjJE9RIvHeAmWgDpYgMsGToaKJk+MizMJyccnTouWMQWqhz5OhpEAWYSGP0KLgnYHx5YxRZaAeniQlzJKhoAAWYSGP0KLgnYHx5YxRZaAeniVhCZYgLYkSpGMNUeohCQJyccnTouWMQWoiiBCiosAWYSGP0KLgnYHx5YxRZaAem+AiydOtpU1X4yEOGaElXYHx5YxRZaAemikoQBZhIY/QouCdnbJxzFTlYwmTrGMOU62kTVGlcduRZCJOAfHjCdnioY1R8ffMkqJljFWyZ52SZCLMEeArDBYx98PCYaLek6Eli1Kl8snSoiVOE6WyzxOiYk7SpfHCQfHkzVZjIQ6X5bHbnDHhjtGiJA6QpGcdnbJxyRHhJEyRJeIJwnfvnZKi4EmRIyBdnbJxzlEgZA4TpbHbnDHljFFloB6YoiEM06xlzVIjow6TMfJdliAiydOy6Y4RJCBBk6GijNFjJE9RIvHeAmWgDpYgMsGToaKJk+MizMJyccnTouWMQWqhz5OhpEAWYSGP0KLgnYHx5YxRZaAeniQlzJKhoAAWYSGP0KLgnYHx5YxRZaAeniVhCZYgLYkSpGMNUeohCQJyccnTouWMQWoiiBCiosAWYSGP0KLgnYHx5YxRZaAem+AiydOtpU1X4yEOGaElXYHx5YxRZaAemikoQBZhIY/QouCdnbJxzFTlYwmTrGMOU62kTVGlcduRZCJOAfHjCdnioY1R8ffMkqJljFWyZ52SZCLMEeArDBYx98PCce4eAmThCZChIsgWMffDwmGijlGkIs9X5zHCQfHlThKkYM7WYiWdhG+xz1ElscJB8eIO0+QiTFYx98PCZaAOliAyx1GhIIxf5eEN0CMizMJyccnTouWMQWmiTtegbcxSIqCOkKRjDtFx8l2WICLJ07LtzFIipcwQouCdgfHljFFloB6ZIePMUiRsSZKho49RYLHeAmWgDpYgMsHXpeDNUiAsSZKho49RYLHeAmWgDpYgMsHW4SXJ062lTVfjIQ4ZoSVdgfHljFFloB6ZoqRPUSLsSZKho49RYLHeAmWgDpYgMsQTouWMXiVhCBChIkZSpXHeAmWgDpYgMsXaqGxJkqGjj1FgscJB8eALFuMlzF/jIgxeJGEOVvH3zpeiYl4CYyWGESGhDgJ34M1R5aAKXaYofv6f5jOy3H0HFfEOtwAF4rjYXP+b/9pnCtsl9lZ/g3cFmiz2pwd/i+R5gtqGxGMJowobt80JWRElDHzk35aMkeZh+glz9lw5BagZ3AD0oVlUxQY/VKBLy+tX+ohxHAMzvxaQm+ur0WvUDXK9/F5F5MJal67YU9hRPROdL0Mt216EXma1Z7FVTw3uJAk6OeAxWAyss8DaClbdRZmdqfbJ2qu8v1zildKIcl0OjiQgS8gbh+MUzZrVUYQEABrCP4Cmn9G7Plrs2HYKFOwX8md9JMrjZ9cqvsDtSd6DCpdR3NZeVRoEIrsH+ffA/ia2w+dQgNb8+7HSxKngIZYVCvl5Q==";
    private GLView glView;
    public LetterDialog letterOpenView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        mContext=this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (!Engine.initialize(this, key)) {
            Log.e("HelloAR", "Initialization Failed.");
            Toast.makeText(ARActivity.this, Engine.errorMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        if (!CameraDevice.isAvailable()) {
            Toast.makeText(ARActivity.this, "CameraDevice not available.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!ImageTracker.isAvailable()) {
            Toast.makeText(ARActivity.this, "ImageTracker not available.", Toast.LENGTH_LONG).show();
            return;
        }

        glView = new GLView(this,this);

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                letterOpenView = new LetterDialog(mContext);
//                letterOpenView.show();
             ((ViewGroup) findViewById(R.id.preview)).addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }

            @Override
            public void onFailure() {
            }
        });
    }

    private interface PermissionCallback
    {
        void onSuccess();
        void onFailure();
    }
    private HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();
    private int permissionRequestCodeSerial = 0;
    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback)
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (permissionCallbacks.containsKey(requestCode)) {
            PermissionCallback callback = permissionCallbacks.get(requestCode);
            permissionCallbacks.remove(requestCode);
            boolean executed = false;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    executed = true;
                    callback.onFailure();
                }
            }
            if (!executed) {
                callback.onSuccess();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (glView != null) { glView.onResume(); }
    }

    @Override
    protected void onPause()
    {
        if (glView != null) { glView.onPause(); }
        super.onPause();
    }
}

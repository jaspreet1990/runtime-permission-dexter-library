package com.stacklearning.dexterpermission;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn_camera_permission,btn_multi_permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_camera_permission= (Button) findViewById(R.id.btn_camera_permission);
        btn_multi_permission= (Button) findViewById(R.id.btn_multi_permission);

        btn_camera_permission.setOnClickListener(this);
        btn_multi_permission.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera_permission:

                Dexter.withActivity(this)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Toast.makeText(MainActivity.this,"Permession Granted with Dexter",Toast.LENGTH_SHORT).show();
                            }
                            @Override public void onPermissionDenied(PermissionDeniedResponse response)
                            {
                                Toast.makeText(MainActivity.this,"Permession Deny",Toast.LENGTH_SHORT).show();
                               if(response.isPermanentlyDenied()){
                                   // navigate to app setting

                                   Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                           Uri.fromParts("package", getPackageName(), null));
                                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   startActivity(intent);
                               }

                            }
                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token)
                            {
                                    token.continuePermissionRequest();
                            }
                        }).check();

                break;

            case R.id.btn_multi_permission:

                Dexter.withActivity(this)
                        .withPermissions(
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                        .withListener(new MultiplePermissionsListener() {
                    @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }
                        for (int i=0;i<report.getDeniedPermissionResponses().size();i++) {
                            Log.d("dennial permision res", report.getDeniedPermissionResponses().get(i).getPermissionName());
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings

                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        }
                    @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token)
                    {
                        token.continuePermissionRequest();
                    }
                }).check();
                break;
        }
    }
}

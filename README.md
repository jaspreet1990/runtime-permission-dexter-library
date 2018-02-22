# runtime-permission-dexter-library
This repository is for getting runtime permission with Dexter library . By Using Dexter library, very easy to get Runtime permission with few lines of code .


By Using Dexter Library in Android, We can get the Permission via few lines of code .
With Dexter Library in Android , User can get Single Runtime permission or Multiple Runtime Permission.

Full Tutorial on Dexter Library in Android you can reach to : http://stacklearning.blogspot.in/2018/02/android-runtime-permissions-with-dexter.html

2 Step Process to use Dexter Library in Android .

Add Dependency :

dependencies {
   
    implementation 'com.karumi:dexter:4.2.0'    // Dexter runtime permissions

}


Single Permission Code :

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


Multiple Permission Code :


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

Full Tutorial on Dexter Library in Android  you can reach to : http://stacklearning.blogspot.in/2018/02/android-runtime-permissions-with-dexter.html

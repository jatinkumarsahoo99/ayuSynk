package com.ayudevice.ayushareintegrationdemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText clientIdET, emailIdET;
    private CheckBox hideTab, hideOption, disableEarphone;
    private String deviceType = "";
    String receivedText = "JKS";
    String email = "JKS";
    int age = 0;
    String gender = "JKS";
    String referenceId = "JKS";
    private int mode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAGBRScanJKs", "receivedText");
        Intent receivedIntent = getIntent();
        if (receivedIntent != null) {
            String action = receivedIntent.getAction();
            String type = receivedIntent.getType();

            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {
                     receivedText = receivedIntent.getStringExtra("clientId");
                      email = receivedIntent.getStringExtra("emailId");
//                     receivedText = receivedIntent.getStringExtra("patient_name");
                    // Log.e("TAGBRScanJKs", receivedText);
                    if (receivedText != null) {
                        Log.i("TAGBRScanJKs", receivedText);

                        // System.out.print("My data is>>>" + receivedText);
                    }
                }
            }
        }

        clientIdET = findViewById(R.id.et_clientId);
        emailIdET = findViewById(R.id.et_emailId);
        clientIdET.setText(receivedText);
        emailIdET.setText(email);
        RadioGroup deviceTypeRG = findViewById(R.id.rg_deviceType);
        deviceTypeRG.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbLynk) {
                deviceType = getString(R.string.ayuLynk);
            } else if (checkedId == R.id.rbSynk) {
                deviceType = getString(R.string.ayuSynk);
            } else if (checkedId == R.id.rbDongle) {
                deviceType = getString(R.string.dongle);
            }
        });
        RadioGroup modeRG = findViewById(R.id.rg_mode);
        modeRG.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbRecord) {
                mode = 0;
            } else if (checkedId == R.id.rbOnline) {
                mode = 1;
            }
        });
        hideTab = findViewById(R.id.cb_hidePatientSupportTab);
        hideOption = findViewById(R.id.cb_hideNavigationOption);
        disableEarphone = findViewById(R.id.cb_disableEarphoneRequirement);
        findViewById(R.id.btn_open).setOnClickListener(v -> {
            if (validate(clientIdET.getText().toString(), deviceTypeRG.getCheckedRadioButtonId()))
                launchApp(AppIntent.packageName);
            else
                Toast.makeText(MainActivity.this, "Please enter client id and select deviceType", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validate(String clientId, int radioBtnSelected) {
        return !clientId.isEmpty() && radioBtnSelected != -1 && mode != -1;
    }

    public void launchApp(String packageName) {
        Intent launchIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            launchIntent.setFlags(0);
            launchIntent.putExtra(AppIntent.KEY.clientId, clientIdET.getText().toString());
            if (!emailIdET.getText().toString().isEmpty())
                launchIntent.putExtra(AppIntent.KEY.emailId, emailIdET.getText().toString());
            launchIntent.putExtra(AppIntent.KEY.mode, mode);
            launchIntent.putExtra(AppIntent.KEY.deviceType, deviceType);
            launchIntent.putExtra(AppIntent.KEY.hidePatientSupportTab, hideTab.isChecked());
            launchIntent.putExtra(AppIntent.KEY.hideSideMenuOption, hideOption.isChecked());
            launchIntent.putExtra(AppIntent.KEY.disableEarphoneRequirement, disableEarphone.isChecked());
            launchIntent.putExtra(AppIntent.KEY.sendToServer, true);
            startActivity(launchIntent);
        } else {
            Toast.makeText(this, "Please download AyuShare first", Toast.LENGTH_SHORT).show();
            launchIntent = new Intent(Intent.ACTION_VIEW);
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            launchIntent.setData(Uri.parse("market://details?id=" + packageName));
            startActivity(launchIntent);
        }
    }

    /*
     * Send this broadcast to close AyuShare app
     */
    private void closeApp() {
        final Intent intent = new Intent();
        intent.setAction(AppIntent.CloseApp.action);
        intent.putExtra(AppIntent.KEY.clientId, clientIdET.getText().toString());
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(
                new ComponentName(AppIntent.packageName, AppIntent.CloseApp.broadcastReceiver));
        sendBroadcast(intent);
    }
}
package com.ayudevice.ayushareintegrationdemo;

public interface AppIntent {

    interface KEY {
        String emailId = "emailId";
        String clientId = "clientId";
        String mode = "mode";
        String deviceType = "deviceType";
        String hidePatientSupportTab = "hidePatientSupportTab";
        String hideSideMenuOption = "hideSideMenuOption";
        String disableEarphoneRequirement = "disableEarphoneRequirement";
        String sendToServer = "sendToServer";
    }

    interface CloseApp {
        String action = "com.ayudevices.ayushare2.closeApp";
        String broadcastReceiver = "com.ayudevices.ayushare2.broadcastReceiver.CloseAppBroadCastReceiver";
    }

    String packageName = "com.ayudevices.ayushare2";

}

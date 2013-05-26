//package diverse;
//
//import aktiviteter.BlodsukkerMain_akt;
//import aktiviteter.Historik_akt;
//import aktiviteter.Insulinberegning_akt;
//import aktiviteter.Kulhydrat_akt;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.hardware.usb.UsbManager;
//import android.os.Bundle;
//
//import android.util.Log;
//
//public class USB_broadcastReceiver extends BroadcastReceiver{
//
//	public boolean usbConnected;
//	BlodsukkerMain_akt blodsukkermain;
//	Historik_akt historik;
//	Insulinberegning_akt insulin;
//	Kulhydrat_akt kulhydrat;
//	
//	
//
//	@Override
//	public void onReceive(Context context, Intent intent) {
//
//		// BroadcastReceiver when remove the device USB plug from a USB port  
//		BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
//		    public void onReceive(Context context, Intent intent) {
//		    	
//		        if (intent.getAction().equals("AFSLUT")) {
//
//		    	blodsukkermain.afslut();
//		    	historik.afslut();
//		    	insulin.afslut();
//		    	kulhydrat.afslut();
//		        }
//		    }
//		};
//		
//	}
//	
//}

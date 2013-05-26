package usb_kommunikation;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;


import android.hardware.usb.*;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class USB_komm extends Activity implements View.OnClickListener, Runnable {
	Button knap;
	TextView enhedstekst, forhandler;
	private final static String usbKey = "!!!!";
	
	
	UsbAccessory mAccessory;
	ParcelFileDescriptor mFileDescriptor;
	FileInputStream mInputStream;
	FileOutputStream mOutputStream;
	private UsbManager UM;
	private UsbDevice device;
	private HashMap<String, UsbDevice> devicelist;
	private Iterator<UsbDevice> deviceIterator;
	


	final static int USB_ENDPOINT_OUT = 0x01;
	final static int USB_ENDPOINT_IN = 0x81;

//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//		knap = (Button) findViewById(R.id.knap);
//		knap.setOnClickListener(this);
//		enhedstekst = (TextView) findViewById(R.id.enhedstekst);
//		forhandler = (TextView) findViewById(R.id.forhandler);
//	}

	
//    public void readDevice() {
//		deviceIterator = devicelist.values().iterator();
//		while(deviceIterator.hasNext()){
//			device = deviceIterator.next();
//    	
//    	StringBuilder sb = new StringBuilder();
//        sb.append("Device Name: " + device.getDeviceName() + "\n");
//        sb.append(String.format(
//                "Device Class: %s -> Subclass: 0x%02x -> Protocol: 0x%02x\n",
//               device.getDeviceClass(),
//                device.getDeviceSubclass(), device.getDeviceProtocol()));
//
//        for (int i = 0; i < device.getInterfaceCount(); i++) {
//            UsbInterface intf = device.getInterface(i);
//            sb.append(String
//                    .format("+--Interface %d Class: %s -> Subclass: 0x%02x -> Protocol: 0x%02x\n",
//                            intf.getId(),
//                            intf.getInterfaceClass(),
//                            intf.getInterfaceSubclass(),
//                            intf.getInterfaceProtocol()));
//
//            for (int j = 0; j < intf.getEndpointCount(); j++) {
//                UsbEndpoint endpoint = intf.getEndpoint(j);
//                sb.append(String.format(" +---Endpoint %d: %s %s\n",
//                        endpoint.getEndpointNumber(),
//                        (endpoint.getType()),
//                        (endpoint.getDirection())));
//            }
//        }
//        Log.d("Læs endhed",sb.toString());
////        return sb.toString();
//		}
//    }



	public void usbOpfrisk() {
		UM = (UsbManager) getSystemService(Context.USB_SERVICE);		
		devicelist = UM.getDeviceList();
		UsbDevice enhed = devicelist.get("deviceName");
		UsbDevice enhed2 = UM.getDeviceList().get(usbKey);

		//UsbDevice usbstick = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

//		for (int i=0;devicelist.entrySet();){
//			enhedstekst = ;
//		};
		
		String[] array = devicelist.keySet().toArray(new String[devicelist.keySet().size()]);

		
		Arrays.sort(array);
		
		enhedstekst.setText("Antal enheder: " + devicelist.size());
		
		deviceIterator = devicelist.values().iterator();
		while(deviceIterator.hasNext()){
			device = deviceIterator.next();
			
			log("===================================\n");
			log("Model: " + device.getDeviceName() + "\n");  
			log("DeviceID: 0x" + Integer.toHexString(device.getDeviceId()) + "\n");
			log("Vendor: 0x" + Integer.toHexString(device.getVendorId()) + "\n");
			log("Product:  0x" + Integer.toHexString(device.getProductId()) + "\n");
			log("Class: 0x" + Integer.toHexString(device.getDeviceClass()) + "\n");
			log("Subclass: 0x" + Integer.toHexString(device.getDeviceSubclass()) + "\n");
			log("Protocol: 0x" + Integer.toHexString(device.getDeviceProtocol()) + "\n");
			log("Interfacecount" + Integer.toHexString(device.getInterfaceCount()) + "\n");
			log("===================================\n");
			
			
			   // Get interface details
	        for (int index = 0; index < device.getInterfaceCount(); index++)
	        {
	        UsbInterface mUsbInterface = device.getInterface(index);
	       
	        log("  Interface index: " + index+ "\n");
	        log("  Interface ID: " + mUsbInterface.getId()+ "\n");
	        log("  Inteface class: " + mUsbInterface.getInterfaceClass()+ "\n");
	        log("  Interface protocol: " + mUsbInterface.getInterfaceProtocol()+ "\n");
	        log("  Endpoint count: " + mUsbInterface.getEndpointCount()+ "\n");
	    // Get endpoint details 
	            for (int epi = 0; epi < mUsbInterface.getEndpointCount(); epi++)
	        {
	            UsbEndpoint mEndpoint = mUsbInterface.getEndpoint(epi);
	            log("    ++++   ++++   ++++"+ "\n");
	            log("    Endpoint index: " + epi+ "\n");
	            log("    Attributes: " + mEndpoint.getAttributes()+ "\n");
	            log("    Direction: " + mEndpoint.getDirection()+ "\n");
	            log("    Number: " + mEndpoint.getEndpointNumber()+ "\n");
	            log("    Interval: " + mEndpoint.getInterval()+ "\n");
	            log("    Packet size: " + mEndpoint.getMaxPacketSize()+ "\n");
	            log("    Type: " + mEndpoint.getType()+ "\n");
	        }
	            log("===================================\n");
	        }

			//Bayer int vendorID værdi er 6777 (hex 1a79)
			if(device.getVendorId()==6777){
				
				//Bayer int ProductID værdi er 29712 (hex 7410)
				if(device.getProductId()==29712){
					Toast.makeText(getBaseContext(), "Dette Bayer apparat er kompatibelt", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getBaseContext(), "Dette Bayer apparat er IKKE kompatibelt", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(getBaseContext(), "Dette er ikke et Bayer apparat", Toast.LENGTH_LONG).show();
			}
//			forhandler.setText("Forhandler: " +Integer.toHexString(device.getVendorId()));
//			forhandler.setText("Forhandler: " +(device.getDeviceName()));
			
			new Thread(this).start();
		
//		ArrayAdapter<String> adaptor = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, array);
//		devicelist.setAdapter(adaptor);
//		forhandler.setText("Forhandler: " + Integer.toHexString(devicelist.get(usbKey).getVendorId()));
		
//		if(enhed.getDeviceName()==null){
//			forhandler.setText("Farck");
//		}else{
//			forhandler.setText("Forhandler: " + enhed.getVendorId());
//		}
//		Toast.makeText(getBaseContext(), "i USB mode", Toast.LENGTH_SHORT).show();
		
		}

		Log.d("FORHANDLER", forhandler.getText().toString());
	}
	
	
	private void log(final String string) {
		runOnUiThread(new Runnable() {			
			@Override
			public void run() {
				forhandler.append(string);				
			}
		});
		Log.d("PYJAMAS", string);
	}

	private void openAccessory() {
////	    Log.d("usb-halløj", "openAccessory: " + accessory);
//	    mFileDescriptor = UM.openAccessory(mAccessory);
//	    if (mFileDescriptor != null) {
//	        FileDescriptor fd = mFileDescriptor.getFileDescriptor();
//	        mInputStream = new FileInputStream(fd);
//	        mOutputStream = new FileOutputStream(fd);
//	        Thread thread = new Thread(null, (Runnable) this, "AccessoryThread");
//	        thread.start();
//	        Log.d(mInputStream.toString(), "openAccessory: ");
//	    }
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		readDevice();
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

	@Override
	public void onClick(View v) {
//		if (v == findViewById(R.id.knap)) {
			log("=-==-==-==-==-==-=-=-=-=-=-=-\n");
			usbOpfrisk();
//		}
	}

	@Override
	public void run() {
		log("tråd startet");
		for (int ix=0; ix<device.getInterfaceCount()-1; ix++) {
			log("===================================\n");
			UsbInterface intf = device.getInterface(ix);
			int antEndepunkt = intf.getEndpointCount();
			log("Endepunkter til "+ix+"'te interface: "+ antEndepunkt+"\n");
			UsbEndpoint indEp=null, udEp = null;
			for (int n=0; n<antEndepunkt; n++) {
				UsbEndpoint ep = intf.getEndpoint(n);
				
				log("Endepunkt "+n+" : "+ ep.toString()+" dir "+ep.getDirection()+"\n");
				if (ep.getDirection() == UsbConstants.USB_DIR_IN ) { //  USB_ENDPOINT_IN
					indEp = ep;
					log("IndEndepunkt "+n+" VALGT\n");
				}
				if (ep.getDirection() == UsbConstants.USB_DIR_OUT){// USB_ENDPOINT_OUT
					udEp =ep;
					log("UdEndepunkt "+n+" VALGT\n");
				}
			}				
			
			log("Permission: " + Boolean.toString(UM.hasPermission(device))  + "\n");
			
			if (!UM.hasPermission(device)) {
				PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent("hejsa.hej"), 0);
				UM.requestPermission(device, mPermissionIntent);
				return;
			}

			if (indEp == null || udEp == null) continue;
			UsbDeviceConnection connection = UM.openDevice(device);
//			connection.
			if(connection==null){
				log("Kan ikke åbne forbindelsen \n");
			}else{ 	
				log("claimer interfacet....: "+intf);
				boolean claimet = connection.claimInterface(intf, true);
				log("Har successfuld claimet interfacet: "+claimet+" \n");
				log("Serial: "+connection.getSerial()+" \n");
				log("Descriptor: "+connection.getFileDescriptor()+" \n");
				byte[] buffer = new byte[64];
				
				log("Kontrol: "+connection.controlTransfer(0x01, 0x58, 0x200, 0x00, buffer, 64, 2000)+"\n");				
				buffer[0] = (byte) 'x';
				int lgd = connection.bulkTransfer(indEp, buffer, 1, 5000);
				log("Skrevet: "+lgd+" \n");
				
				lgd = connection.bulkTransfer(udEp, buffer, buffer.length, 5000);  
				//buffer = Arrays.copyOfRange(buffer, 0, lgd);
				log("Læst: "+lgd+" \n");
				//log("Læst "+new String(buffer));
				//log("Læst "+Arrays.toString(buffer) );
				
			}
		}
	}

}
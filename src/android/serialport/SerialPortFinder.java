package android.serialport;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

//import android.util.Log;

public class SerialPortFinder {

	public class Driver {
		public Driver(String name, String root) {
			mDriverName = name;
			mDeviceRoot = root;
		}
		private String mDriverName;
		private String mDeviceRoot;
		Vector<File> mDevices = null;
		public Vector<File> getDevices() {
			if (mDevices == null) {
				mDevices = new Vector<File>();
				File dev = new File("/dev");
				File[] files = dev.listFiles();
				int i;
				for (i=0; i<files.length; i++) {
					if (files[i].getAbsolutePath().startsWith(mDeviceRoot)) {
						//Log.d(TAG, "Found new device: " + files[i]);
						mDevices.add(files[i]);
					}
				}
			}
			return mDevices;
		}
		public String getName() {
			return mDriverName;
		}
	}

	private static final String TAG = "SerialPort";

	private Vector<Driver> mDrivers = null;

	Vector<Driver> getDrivers() throws IOException {
		if (mDrivers == null) {
			mDrivers = new Vector<Driver>();
			LineNumberReader r = new LineNumberReader(new FileReader("/proc/tty/drivers"));
			String l;
			while((l = r.readLine()) != null) {
				String[] w = l.split(" +");
				if ((w.length == 5) && (w[4].equals("serial"))) {
					//Log.d(TAG, "Found new driver: " + w[1]);
					mDrivers.add(new Driver(w[0], w[1]));
				}
			}
			r.close();
		}
		return mDrivers;
	}

	public String[] getAllDevices() {
		Vector<String> devices = new Vector<String>();
		// Parse each driver
		Iterator<Driver> itdriv;
		try {
			itdriv = getDrivers().iterator();
			devices.add("");
			while(itdriv.hasNext()) {
				Driver driver = itdriv.next();
				Iterator<File> itdev = driver.getDevices().iterator();
				while(itdev.hasNext()) {
					String device = itdev.next().getName();
					//String value = String.format("%s (%s)", device, driver.getName());
					//devices.add(value);
					/*
					 * Note : add only ttymxc , and rename to COM
					 * ex : ttymxc0 to COM1
					 */
					if (device.indexOf("ttymxc")>=0) {
						int i=Integer.parseInt(device.substring(device.indexOf("ttymxc")+"ttymxc".length()));
						i++;
						devices.add("COM"+String.valueOf(i));
					} else if (device.indexOf("ttyACM0")>=0) {
						devices.add("PL-200 USB");
					}
				}
			}
			Collections.sort(devices);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return devices.toArray(new String[devices.size()]);
	}

	public String[] getAllDevicesPath() {
		Vector<String> devices = new Vector<String>();
		// Parse each driver
		Iterator<Driver> itdriv;
		try {
			itdriv = getDrivers().iterator();
			devices.add("");
			while(itdriv.hasNext()) {
				Driver driver = itdriv.next();
				Iterator<File> itdev = driver.getDevices().iterator();
				while(itdev.hasNext()) {
					String device = itdev.next().getAbsolutePath();
					//devices.add(device);
					/*
					 * Note : add only ttymxc , and rename to COM
					 * ex : /dev/ttymxc0 to COM1
					 */
					if (device.indexOf("/dev/ttymxc")>=0) {
						int i=Integer.parseInt(device.substring(device.indexOf("/dev/ttymxc")+"/dev/ttymxc".length()));
						i++;
						devices.add("COM"+String.valueOf(i));
					} else if (device.indexOf("/dev/ttyACM0")>=0) {
						devices.add("PL-200 USB");
					}
				}
			}
			Collections.sort(devices);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return devices.toArray(new String[devices.size()]);
	}
}


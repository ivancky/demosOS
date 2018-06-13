/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.osram.os.demos;

import android.app.Activity;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yc.peddemo.sdk.BLEServiceOperate;
import com.yc.peddemo.sdk.DeviceScanInterfacer;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Activity for scanning and displaying available Bluetooth LE devices.
 */
public class DeviceScanActivity extends ListActivity implements DeviceScanInterfacer{

//	BluetoothConnectionService mBluetoothConnection;
//	private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

	private LeDeviceListAdapter mLeDeviceListAdapter;
	private boolean mScanning;
	private Handler mHandler;

	private final int REQUEST_ENABLE_BT = 1;
	// Stops scanning after 10 seconds.
	private final long SCAN_PERIOD = 10000;

	private BLEServiceOperate mBLEServiceOperate;

	BluetoothDevice bluetoothDevice;

    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        //        maintain connection on this Activity
        bluetoothDevice = getIntent().getExtras().getParcelable("btdevice");
//        mBluetoothConnection = new BluetoothConnectionService(DeviceScanActivity.this);
//        mBluetoothConnection.startClient(bluetoothDevice, MY_UUID_INSECURE);

//		getActionBar().setTitle("Bluetooth Connection");//		GBUtils gbUtils = GBUtils.getInstance();
//		String[] body= {"中文全字库","粤语全字庫","英语Full font","丹麦语Fuld font","德语Vollständiger Schrift","西班牙completo de fuente","法语police complète",
//				"意大利fonte completa","日语フルフォント","韩语전체 글꼴","波兰语Pełna czcionki","俄语Полный шрифт","泰语ตัวอักษรเต็มรูปแบบ","土耳其语tam yazı","乌克兰语повний шрифт"};
//		for (int i = 0; i < body.length; i++) {
//			String dataString = gbUtils.string2unicode(body[i]);
//			Log.d("DeviceScanActivity","body ="+body[i]+ ",unicode ="+dataString);
//			
//		}

//		// retrieve the intent from MainActivity - use to get the paired device
//		final BluetoothDevice bluetoothDevice = getIntent().getExtras().getParcelable("btdevice");
//
////        maintain connection on Activity 2
		mBluetoothConnection = new BluetoothConnectionService(DeviceScanActivity.this);
		mBluetoothConnection.startClient(bluetoothDevice, MY_UUID_INSECURE);

		mHandler = new Handler();
		mBLEServiceOperate = BLEServiceOperate
				.getInstance(getApplicationContext());// 用于BluetoothLeService实例化准备,必须

		// Checks if Bluetooth is supported on the device.
		if (!mBLEServiceOperate.isSupportBle4_0()) {
			Toast.makeText(this, R.string.not_support_ble, Toast.LENGTH_SHORT)
					.show();
			finish();
			return;
		}
		mBLEServiceOperate.setDeviceScanListener(this);//for DeviceScanInterfacer
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		if (!mScanning) {
			menu.findItem(R.id.menu_stop).setVisible(false);
			menu.findItem(R.id.menu_scan).setVisible(true);
			menu.findItem(R.id.menu_refresh).setActionView(null);
		} else {
			menu.findItem(R.id.menu_stop).setVisible(true);
			menu.findItem(R.id.menu_scan).setVisible(false);
			menu.findItem(R.id.menu_refresh).setActionView(
					R.layout.actionbar_indeterminate_progress);
		}
		return true;
	}

	public void onBackPressed(){
		Intent intent = new Intent(DeviceScanActivity.this, ColorMixing.class);
        intent.putExtra("btdevice", bluetoothDevice); // maintain BT connection
		startActivity(intent);
		finish();
		super.onBackPressed();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_scan:
			mLeDeviceListAdapter.clear();
			scanLeDevice(true);
			break;
		case R.id.menu_stop:
			scanLeDevice(false);
			break;
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		// Ensures Bluetooth is enabled on the device. If Bluetooth is not
		// currently enabled,
		// fire an intent to display a dialog asking the user to grant
		// permission to enable it.
		if (!mBLEServiceOperate.isBleEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}

		// Initializes list view adapter.
		mLeDeviceListAdapter = new LeDeviceListAdapter();
		setListAdapter(mLeDeviceListAdapter);
		scanLeDevice(true);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		if (requestCode == REQUEST_ENABLE_BT
				&& resultCode == Activity.RESULT_CANCELED) {
			finish();
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		super.onPause();
		scanLeDevice(false);
		mLeDeviceListAdapter.clear();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mBLEServiceOperate.unBindService();// unBindService
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// retrieve the intent from MainActivity - use to get the paired device

        Intent intent = new Intent(DeviceScanActivity.this, HeartRate.class);
        intent.putExtra("btdevice", bluetoothDevice); // maintain BT connection
//        startActivity(intent);
//		final BluetoothDevice bluetoothDevice = getIntent().getExtras().getParcelable("btdevice");
		final BluetoothDevice device = mLeDeviceListAdapter.getDevice(position);
		if (device == null)
			return;
//		final Intent intent = new Intent(this, HeartRate.class);
		intent.putExtra(HeartRate.EXTRAS_DEVICE_NAME, device.getName());
		intent.putExtra(HeartRate.EXTRAS_DEVICE_ADDRESS, device.getAddress());
//		intent.putExtra("btdevice", bluetoothDevice);
		if (mScanning) {
			mBLEServiceOperate.stopLeScan();
			mScanning = false;
		}
		startActivity(intent);
	}

	private void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBLEServiceOperate.stopLeScan();
					invalidateOptionsMenu();
				}
			}, SCAN_PERIOD);

			mScanning = true;
			mBLEServiceOperate.startLeScan();
		} else {
			mScanning = false;
			mBLEServiceOperate.stopLeScan();
		}
		invalidateOptionsMenu();
	}

	// Adapter for holding devices found through scanning.
	private class LeDeviceListAdapter extends BaseAdapter {
		private ArrayList<BluetoothDevice> mLeDevices;
		private LayoutInflater mInflator;

		public LeDeviceListAdapter() {
			super();
			mLeDevices = new ArrayList<BluetoothDevice>();
			mInflator = DeviceScanActivity.this.getLayoutInflater();
		}

		public void addDevice(BluetoothDevice device) {
			if (!mLeDevices.contains(device)) {
				mLeDevices.add(device);
			}
		}

		public BluetoothDevice getDevice(int position) {
			return mLeDevices.get(position);
		}

		public void clear() {
			mLeDevices.clear();
		}

		@Override
		public int getCount() {
			return mLeDevices.size();
		}

		@Override
		public Object getItem(int i) {
			return mLeDevices.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			ViewHolder viewHolder;
			// General ListView optimization code.
			if (view == null) {
				view = mInflator.inflate(R.layout.listitem_device, null);
				viewHolder = new ViewHolder();
				viewHolder.deviceName = (TextView) view
						.findViewById(R.id.device_name);
				viewHolder.deviceAddress = (TextView) view
						.findViewById(R.id.device_address);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			BluetoothDevice device = mLeDevices.get(i);
			final String deviceName = device.getName();
			if (deviceName != null && deviceName.length() > 0)
				viewHolder.deviceName.setText(deviceName);
			else
				viewHolder.deviceName.setText(R.string.unknown_device);
			viewHolder.deviceAddress.setText(device.getAddress());

			return view;
		}
	}

	static class ViewHolder {
		TextView deviceName;
		TextView deviceAddress;
	}

	@Override
	public void LeScanCallback(final BluetoothDevice device, final int rssi) {
		// TODO Auto-generated method stub
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mLeDeviceListAdapter.addDevice(device);
				mLeDeviceListAdapter.notifyDataSetChanged();
			}
		});
	}
}
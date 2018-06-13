package com.osram.os.demos;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by User on 4/15/2017.
 */

public class Bluetooth extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    public static final String TAG = "MainActivity";
    private final int DISCONNECTED = 3;
    private int CURRENT_STATUS = DISCONNECTED;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothConnectionService mBluetoothConnection;
    ListView lvNewDevices;
    Button btnReset;

    public static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothDevice mBTDevice;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    public Set<BluetoothDevice> pairedDevices;
    public DeviceListAdapter mDeviceListAdapter;

    Bluetooth_DBHandling bluetoothDB = new Bluetooth_DBHandling(this);


    // Create a BroadcastReceiver for ACTION_FOUND
    public final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int id;
            String address = "";
            String name = "";
            // When discovery finds a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);
                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                         mBluetoothAdapter.cancelDiscovery();
                        checkBTPermissions();

                        String myActivity = (String) getIntent().getSerializableExtra("key"); // get Activity name
                                switch(myActivity) {
                                    case "horti":
                                        id = 1;
                                        break;
                                    case "heart":
                                        id = 2;
                                        break;
                                    case "horti2":
                                        id = 3;
                                        break;
                                    default:
                                        id = 0;
                                        break;
                                }

                        bluetoothDB.openDB();
                        name = bluetoothDB.getName(id);
                        Log.d(TAG, "BT name: " + name);
                        address = bluetoothDB.getAddress(id);
                        Log.d(TAG, "BT add: " + address);
                        bluetoothDB.closeDB();

                            if (name != null && !name.isEmpty() && !name.equals("null")) {
                                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                                mBTDevices.add(device);
                                Log.d(TAG, "BT Device MATCHED");
                                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                                lvNewDevices.setAdapter(mDeviceListAdapter);

//                                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//                                Log.d(TAG, "BT Device MATCHED");
//                                mBTDevice = device;
//                                mBluetoothConnection = new BluetoothConnectionService(Bluetooth.this);
//                                String myActivity = (String) getIntent().getSerializableExtra("key"); // get Activity name
//                                switch(myActivity){
//                                    case "horti":
//                                        Intent intent2 = new Intent(Bluetooth.this, Horticulture.class);
//                                        intent2.putExtra("btdevice", mBTDevice); // maintain BT connection
//                                        startActivity(intent2);
//                                        break;
//                                    case "heart":
//                                        Intent intent3 = new Intent(Bluetooth.this, DeviceScanActivity.class);
//                                        intent3.putExtra("btdevice", mBTDevice); // maintain BT connection
//                                        startActivity(intent3);
//                                        break;
//                                    default:
//                                        break;
//                                }
                            } else {
                            mBluetoothAdapter.startDiscovery();
                            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
                        }
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }
            }
        }
    };

    /**
     * Broadcast Receiver for changes made to bluetooth states such as:
     * 1) Discoverability mode on/off or expire.
     */
    public final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {

                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    //Device is in Discoverable Mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Enabled.");
                        break;
                    //Device not in discoverable mode
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Able to receive connections.");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d(TAG, "mBroadcastReceiver2: Discoverability Disabled. Not able to receive connections.");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG, "mBroadcastReceiver2: Connecting....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG, "mBroadcastReceiver2: Connected.");
                        break;
                }

            }
        }
    };


    /**
     * Broadcast Receiver for listing devices that are not yet paired
     * -Executed by btnDiscover() method.
     */
    public BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: ACTION FOUND.");
            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);
            }
        }
    };

    /**
     * Broadcast Receiver that detects bond state changes (Pairing status changes)
     */
    public final BroadcastReceiver mBroadcastReceiver4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //3 cases:
                //case1: bonded already
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDED.");
                    //inside BroadcastReceiver4
                    mBTDevice = mDevice;
                }
                //case2: creating a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d(TAG, "BroadcastReceiver: BOND_BONDING.");
                }
                //case3: breaking a bond
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d(TAG, "BroadcastReceiver: BOND_NONE.");
                }
            }
        }
    };



    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        mBluetoothAdapter.cancelDiscovery();
//        mBluetoothConnection.cancel();
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
//        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        unregisterReceiver(mBroadcastReceiver4);
    }

    public void onBackPressed(){
        mBluetoothAdapter.cancelDiscovery();
        super.onBackPressed();
        mBluetoothAdapter.disable();
//        mBluetoothConnection.cancel();
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bluetooth);
        getSupportActionBar().setTitle("Bluetooth Connection");

        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();
        btnReset = findViewById(R.id.btnResetBlue);

        bluetoothDB.openDB();
        bluetoothDB.insertData("", "");
//        bluetoothDB.insertData("", "");
        bluetoothDB.closeDB();
        bluetoothDB.openDB();
//        bluetoothDB.insertData("", "");
        bluetoothDB.insertData("", "");
        bluetoothDB.closeDB();

            //Broadcasts when bond state changes (ie:pairing)
            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1, BTIntent);
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver4, filter);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        lvNewDevices.setOnItemClickListener(Bluetooth.this);
        btnReset.setOnClickListener(Bluetooth.this);
        enableDisableBT();

    }

    protected void onPause() {
        super.onPause();
    }

    public void startConnection(){
        startBTConnection(mBTDevice,MY_UUID_INSECURE);
    }

    /**
     * starting chat service method
     */
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");
        mBluetoothConnection.startClient(device,uuid);
    }

    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //first cancel discovery because its very memory intensive.
        mBluetoothAdapter.cancelDiscovery();

        Log.d(TAG, "onItemClick: You Clicked on a device.");
        String deviceName = mBTDevices.get(i).getName();
        String deviceAddress = mBTDevices.get(i).getAddress();

//        bluetoothDB.openDB();
//        bluetoothDB.insertData(deviceName,deviceAddress);
//        bluetoothDB.updateContact(1, deviceName, deviceAddress);
//        bluetoothDB.closeDB();

        Log.d(TAG, "onItemClick: deviceName = " + deviceName);
        Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

        //create the bond.
        //NOTE: Requires API 17+? I think this is JellyBean
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d(TAG, "Trying to pair with " + deviceName);
            mBTDevices.get(i).createBond();
            mBTDevice = mBTDevices.get(i);
            mBluetoothConnection = new BluetoothConnectionService(Bluetooth.this);
            String myActivity = (String) getIntent().getSerializableExtra("key"); // get Activity name
            switch(myActivity){
                case "horti":
                    bluetoothDB.openDB();
//                    bluetoothDB.insertData("","");
                    bluetoothDB.updateContact(1, deviceName, deviceAddress);
                    bluetoothDB.closeDB();
                    Intent intent = new Intent(Bluetooth.this, Horticulture.class);
                    intent.putExtra("btdevice", mBTDevice); // maintain BT connection
                    startActivity(intent);
                    break;
                case "horti2":
                    bluetoothDB.openDB();
//                    bluetoothDB.insertData("","");
                    bluetoothDB.updateContact(3, deviceName, deviceAddress);
                    bluetoothDB.closeDB();
                    Intent intent2 = new Intent(Bluetooth.this, Horticulture_HP.class);
                    intent2.putExtra("btdevice", mBTDevice); // maintain BT connection
                    startActivity(intent2);
                    break;
                case "heart":
                    bluetoothDB.openDB();
//                    bluetoothDB.insertData("","");
                    bluetoothDB.updateContact(2, deviceName, deviceAddress);
                    bluetoothDB.closeDB();
                    Intent intent1 = new Intent(Bluetooth.this, ColorMixing.class); // connect to DeviceScanActivity for heart-rate mode
                    intent1.putExtra("btdevice", mBTDevice); // maintain BT connection
                    startActivity(intent1);
                    break;
                default:
                    break;
            }
        }
    }

    public void enableDisableBT() {
        Log.d(TAG, "onClick: enabling/disabling bluetooth.");
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
            Toast.makeText(getApplicationContext(),"Your device does not have bluetooth capabilities", Toast.LENGTH_LONG).show();
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);
//            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
//            registerReceiver(mBroadcastReceiver1, BTIntent);
        }
        if (mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: BT already enabled.");
            mBluetoothAdapter.cancelDiscovery();
            checkBTPermissions();
            mBluetoothAdapter.startDiscovery();
//            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
        }
    }

    @Override
    public void onClick(View view) {
        int id;
        String myActivity = (String) getIntent().getSerializableExtra("key"); // get Activity name
        switch(myActivity) {
            case "horti":
                id = 1;
                break;
            case "heart":
                id = 2;
                break;
            case "horti2":
                id = 3;
                break;
            default:
                id = 0;
                break;
        }
        bluetoothDB.openDB();
        bluetoothDB.updateContact(id, "", "");
//        bluetoothDB.updateContact(2, "", "");
        Log.d(TAG, "Delete");
        bluetoothDB.closeDB();
        mBTDevices.clear();
        mBluetoothAdapter.startDiscovery();
        IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver3, discoverDevicesIntent);
    }
}

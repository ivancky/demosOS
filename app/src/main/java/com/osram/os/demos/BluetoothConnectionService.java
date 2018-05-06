package com.osram.os.demos;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by User on 12/21/2016.
 */

public class BluetoothConnectionService extends AppCompatActivity {
    public static final String TAG = "BluetoothConnectionServ";

    public static final String appName = "MYAPP";

    public static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    public final BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    private Handler mHandler; // handler that gets info from Bluetooth service
    public AcceptThread mInsecureAcceptThread;

    public ConnectThread mConnectThread;
    public BluetoothDevice mmDevice;
    public UUID deviceUUID;
    ProgressDialog mProgressDialog;

    public ConnectedThread mConnectedThread;

    public BluetoothConnectionService(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }

    private interface MessageConstants {
        public static final int MESSAGE_READ = 0;
        public static final int MESSAGE_WRITE = 1;
        public static final int MESSAGE_TOAST = 2;

        // ... (Add other message types here as needed.)
    }


    /**
     * This thread runs while listening for incoming connections. It behaves
     * like a server-side client. It runs until a connection is accepted
     * (or until cancelled).
     */
    public class AcceptThread extends Thread {

        // The local server socket
        public final BluetoothServerSocket mmServerSocket;
        public BluetoothSocket mmSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;

            // Create a new listening server socket
            try {
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);

                Log.d(TAG, "AcceptThread: Setting up Server using: " + MY_UUID_INSECURE);
            } catch (IOException e) {
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());
            }

            mmServerSocket = tmp;
        }

        public void run() {
            Log.d(TAG, "run: AcceptThread Running.");

            BluetoothSocket socket = null;

            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                Log.d(TAG, "run: RFCOM server socket start.....");

                socket = mmServerSocket.accept();

                Log.d(TAG, "run: RFCOM server socket accepted connection.");

            } catch (IOException e) {
                Log.e(TAG, "AcceptThread: IOException: " + e.getMessage());
            }

            //talk about this is in the 3rd
            if (socket != null) {
                connected(socket, mmDevice);

            }

            Log.i(TAG, "END mAcceptThread ");
        }

        public void cancel() {
            Log.d(TAG, "cancel: Canceling AcceptThread.");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: Close of AcceptThread ServerSocket failed. " + e.getMessage());
            }
        }


    }

    /**
     * This thread runs while attempting to make an outgoing connection
     * with a device. It runs straight through; the connection either
     * succeeds or fails.
     */
    public class ConnectThread extends Thread {
        public BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: started.");
            mmDevice = device;
            deviceUUID = uuid;
        }

        public void run() {
            BluetoothSocket tmp = null;
            Log.i(TAG, "RUN mConnectThread ");

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                // Use the UUID of the device that discovered // TODO Maybe need extra device object
                if (mmDevice != null) {
                    Log.i(TAG, "Device Name: " + mmDevice.getName());
                    Log.i(TAG, "Device UUID: " + mmDevice.getUuids()[0].getUuid());
                    tmp = mmDevice.createRfcommSocketToServiceRecord(mmDevice.getUuids()[0].getUuid());

                } else Log.d(TAG, "Device is null.");
            } catch (NullPointerException e) {
                Log.d(TAG, " UUID from device is null, Using Default UUID, Device name: " + mmDevice.getName());
                try {
                    tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID_INSECURE);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
            }

            mmSocket = tmp;

            // Always cancel discovery because it will slow down a connection
            mBluetoothAdapter.cancelDiscovery();

            // Make a connection to the BluetoothSocket

            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();

                Log.d(TAG, "run: ConnectThread connected.");
            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                    Log.d(TAG, "run: Closed Socket.");
                } catch (IOException e1) {
                    Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e1.getMessage());
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE);
            }

            //will talk about this in the 3rd video
            connected(mmSocket, mmDevice);

        }

        public void cancel() {
            try {
                Log.d(TAG, "cancel: Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
            }
        }

    }


    /**
     * Start the chat service. Specifically start AcceptThread to begin a
     * session in listening (server) mode. Called by the Activity onResume()
     */
    public synchronized void start() {
        Log.d(TAG, "start");

        // Cancel any thread attempting to make a connection
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (mInsecureAcceptThread == null) {
            mInsecureAcceptThread = new AcceptThread();
            mInsecureAcceptThread.start();
        }
    }

    /**
     * AcceptThread starts and sits waiting for a connection.
     * Then ConnectThread starts and attempts to make a connection with the other devices AcceptThread.
     **/

    public void startClient(BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startClient: Started.");

//        //initprogress dialog
//        mProgressDialog = ProgressDialog.show(mContext, "Connecting Bluetooth"
//                , "Please Wait...", true);

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    /**
     * Finally the ConnectedThread which is responsible for maintaining the BTConnection, Sending the data, and
     * receiving incoming data through input/output streams respectively.
     **/
    public class ConnectedThread extends Thread {
        public final BluetoothSocket mmSocket;
        public final InputStream mmInStream;
        public final OutputStream mmOutStream;
        private byte[] mmBuffer; // mmBuffer store for the stream
        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread: Starting.");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            //dismiss the progressdialog when connection is established
            try {
//                mProgressDialog.dismiss();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

//        public void run(){
//            byte[] buffer = new byte[1024];  // buffer store for the stream
//
//            int bytes; // bytes returned from read()
//
//            // Keep listening to the InputStream until an exception occurs
//            while (true) {
//                // Read from the InputStream
//                try {
//                    bytes = mmInStream.read(buffer);
//                    String incomingMessage = new String(buffer, 0, bytes);
//                    Log.d(TAG, "InputStream: " + incomingMessage);
//                } catch (IOException e) {
//                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage() );
//                    break;
//                }
//            }
//        }


        //Call this from the main activity to send data to the remote device
        public void write(int bytes) {
//            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to outputstream: " + bytes);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to output stream. " + e.getMessage());
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    public void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice) {
        Log.d(TAG, "connected: Starting.");
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();
        //initprogress dialog

    }

    /**
     * Write to the ConnectedThread in an unsynchronized manner
     *
     * @param out The bytes to write
     * @see ConnectedThread#write(int)
     */
    public void write(int out) {
        // Create temporary object
        ConnectedThread r;

        // Synchronize a copy of the ConnectedThread
        Log.d(TAG, "write: Write Called.");
        //perform the write
        try {
            mConnectedThread.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cancel() {
        // Create temporary object
        ConnectedThread r;
        // Synchronize a copy of the ConnectedThread
        Log.d(TAG, "Cancel connection");
        //perform the write
        try {
            mConnectedThread.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
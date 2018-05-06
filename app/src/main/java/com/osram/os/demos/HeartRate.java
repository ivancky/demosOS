package com.osram.os.demos;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.yc.peddemo.sdk.BLEServiceOperate;
import com.yc.peddemo.sdk.BloodPressureChangeListener;
import com.yc.peddemo.sdk.BluetoothLeService;
import com.yc.peddemo.sdk.DataProcessing;
import com.yc.peddemo.sdk.ICallback;
import com.yc.peddemo.sdk.ICallbackStatus;
import com.yc.peddemo.sdk.RateChangeListener;
import com.yc.peddemo.sdk.ServiceStatusCallback;
import com.yc.peddemo.sdk.SleepChangeListener;
import com.yc.peddemo.sdk.StepChangeListener;
import com.yc.peddemo.sdk.UTESQLOperate;
import com.yc.peddemo.sdk.WriteCommandToBLE;
import com.yc.peddemo.utils.CalendarUtils;
import com.yc.peddemo.utils.GlobalVariable;
import com.yc.pedometer.info.BPVOneDayInfo;
import com.yc.pedometer.info.RateOneDayInfo;
import com.yc.pedometer.info.SleepTimeInfo;
import com.yc.pedometer.info.SwimInfo;
import com.yc.pedometer.update.Updates;

import org.w3c.dom.Text;

import me.relex.circleindicator.CircleIndicator;

import static java.lang.Boolean.FALSE;


public class HeartRate extends AppCompatActivity implements OnClickListener,
		ICallback,ServiceStatusCallback {
    private TextView connect_status, rssi_tv, tv_steps, tv_distance,
            tv_calorie, tv_sleep, tv_deep, tv_light, tv_awake, show_result,
            tv_rate, tv_lowest_rate, tv_verage_rate, tv_highest_rate, tvStress, tvCCT;
    private EditText et_height, et_weight, et_sedentary_period;
    private Button btn_confirm, btn_sync_step, btn_sync_sleep, update_ble,
            read_ble_version, read_ble_battery, set_ble_time,
            bt_sedentary_open, bt_sedentary_close, btn_sync_rate,
            btn_rate_start, btn_rate_stop, unit, push_message_content;
    private DataProcessing mDataProcessing;
    //	private CustomProgressDialog mProgressDialog;
    private UTESQLOperate mySQLOperate;
    // private PedometerUtils mPedometerUtils;
    private WriteCommandToBLE mWriteCommand;
    private Context mContext;
    private SharedPreferences sp;
    private Editor editor;

    private final int UPDATE_STEP_UI_MSG = 0;
    private final int UPDATE_SLEEP_UI_MSG = 1;
    private final int DISCONNECT_MSG = 18;
    private final int CONNECTED_MSG = 19;
    private final int UPDATA_REAL_RATE_MSG = 20;
    private final int RATE_SYNC_FINISH_MSG = 21;
    private final int OPEN_CHANNEL_OK_MSG = 22;
    private final int CLOSE_CHANNEL_OK_MSG = 23;
    private final int TEST_CHANNEL_OK_MSG = 24;
    private final int OFFLINE_SWIM_SYNC_OK_MSG = 25;
    private final int UPDATA_REAL_BLOOD_PRESSURE_MSG = 29;
    private final int OFFLINE_BLOOD_PRESSURE_SYNC_OK_MSG = 30;

    private final long TIME_OUT = 120000;
    private boolean isUpdateSuccess = false;
    private int mSteps = 0;
    private float mDistance = 0f;
    private int mCalories = 0;

    private int mlastStepValue;
    private int stepDistance = 0;
    private int lastStepDistance = 0;

    private boolean isFirstOpenAPK = false;
    private int currentDay = 1;
    private int lastDay = 0;
    private String currentDayString = "20101202";
    private String lastDayString = "20101201";
    private static final int NEW_DAY_MSG = 3;
    protected static final String TAG = "MainActivity";
    private Updates mUpdates;
    private BLEServiceOperate mBLEServiceOperate;
    private BluetoothLeService mBluetoothLeService;
    // caicai add for sdk
    public static final String EXTRAS_DEVICE_NAME = "device_name";
    public static final String EXTRAS_DEVICE_ADDRESS = "device_address";
    private final int CONNECTED = 1;
    private final int CONNECTING = 2;
    private final int DISCONNECTED = 3;
    private int CURRENT_STATUS = DISCONNECTED;

    private String mDeviceName;
    private String mDeviceAddress;

    private int tempRate = 70;
    private int tempStatus;
    private long mExitTime = 0;

    private Button test_channel;
    private StringBuilder resultBuilder = new StringBuilder();

    private TextView swim_time, swim_stroke_count, swim_calorie, tv_low_pressure, tv_high_pressure;
    private Button btn_sync_swim, btn_sync_pressure, btn_start_pressure, btn_stop_pressure;

    private int high_pressure, low_pressure;
    private int tempBloodPressureStatus;

    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    //	private LineGraphSeries<DataPoint> mSeries1;
    private LineGraphSeries<DataPoint> mSeries2;
    private int graph2LastXValue = 0;

    Handler handler;
    FragmentPagerAdapter adapterViewPager;

    private Heart_Fragment1 m1stFragment;
    private Heart_Fragment2 m2ndFragment;
    private Heart_Fragment3 m3rdFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.heart_rate);
        getSupportActionBar().setTitle("Heart-Rate Sensing");

        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);

        final Handler handler = new Handler();
        mContext = getApplicationContext();
        sp = mContext.getSharedPreferences(GlobalVariable.SettingSP, 0);
        editor = sp.edit();
        mySQLOperate = new UTESQLOperate(mContext);
        mBLEServiceOperate = BLEServiceOperate.getInstance(mContext);
        mBLEServiceOperate.setServiceStatusCallback(this);
        //如果没在搜索界面提前实例BLEServiceOperate的话，下面这4行需要放到OnServiceStatuslt
        mBluetoothLeService = mBLEServiceOperate.getBleService();
        if (mBluetoothLeService != null) {
            mBluetoothLeService.setICallback(this);
        }

        mRegisterReceiver();
        mfindViewById();
        mWriteCommand = WriteCommandToBLE.getInstance(mContext);
        mUpdates = Updates.getInstance(mContext);
        mUpdates.setHandler(mHandler);// 获取升级操作信息

        Intent intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);

        mBLEServiceOperate.connect(mDeviceAddress);

        CURRENT_STATUS = CONNECTING;
        upDateTodaySwimData();

        // retrieve the intent from MainActivity - use to get the paired device
        final BluetoothDevice bluetoothDevice = getIntent().getExtras().getParcelable("btdevice");

//        maintain connection on Activity 2
        mBluetoothConnection = new BluetoothConnectionService(this);
        mBluetoothConnection.startClient(bluetoothDevice, MY_UUID_INSECURE);

//		GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
//		mSeries1 = new LineGraphSeries<>(generateData());
//		graph.addSeries(mSeries1);

        GraphView graph2 = (GraphView) findViewById(R.id.graph);
        mSeries2 = new LineGraphSeries<>();
        graph2.addSeries(mSeries2);
        graph2.getViewport().setXAxisBoundsManual(true);
        graph2.getViewport().setMinX(0);
        graph2.getViewport().setMaxX(10);
        graph2.getViewport().setYAxisBoundsManual(true);
        graph2.getViewport().setMinY(50);
        graph2.getViewport().setMaxY(150);
        graph2.getViewport().setScrollable(true); // enables horizontal scrolling
        graph2.getViewport().setScalable(true); //
        mSeries2.setColor(Color.RED);
        mSeries2.setDrawDataPoints(true);
        mSeries2.setDataPointsRadius(10);
        mSeries2.setThickness(8);
        graph2.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph2.getViewport().setDrawBorder(true);
        graph2.getGridLabelRenderer().setHorizontalLabelsVisible(FALSE);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                mWriteCommand
                        .sendRateTestCommand(GlobalVariable.RATE_TEST_START);
            }
        }, 5000);

//
    }

    private void mRegisterReceiver() {
        IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(GlobalVariable.READ_BATTERY_ACTION);
        mFilter.addAction(GlobalVariable.READ_BLE_VERSION_ACTION);
        registerReceiver(mReceiver, mFilter);
    }

    private void mfindViewById() {
//		graph  = (GraphView) findViewById(R.id.graph);
//		et_height = (EditText) findViewById(R.id.et_height);
//		et_weight = (EditText) findViewById(R.id.et_weight);
//		et_sedentary_period = (EditText) findViewById(R.id.et_sedentary_period);
        connect_status = (TextView) findViewById(R.id.connect_status);
        rssi_tv = (TextView) findViewById(R.id.rssi_tv);
//		tv_steps = (TextView) findViewById(R.id.tv_steps);
//		tv_distance = (TextView) findViewById(R.id.tv_distance);
//		tv_calorie = (TextView) findViewById(R.id.tv_calorie);
//		tv_sleep = (TextView) findViewById(R.id.tv_sleep);
//		tv_deep = (TextView) findViewById(R.id.tv_deep);
//		tv_light = (TextView) findViewById(R.id.tv_light);
//		tv_awake = (TextView) findViewById(R.id.tv_awake);
        tv_rate = (TextView) findViewById(R.id.tv_rate);
//		tv_lowest_rate = findViewById(R.id.tv_lowest_rate);
//		tv_verage_rate = findViewById(R.id.tv_verage_rate);
//		tv_highest_rate = findViewById(R.id.tv_highest_rate);
        tvStress = findViewById(R.id.tvStress);
        tvCCT = findViewById(R.id.tvCCT);
//		show_result = (TextView) findViewById(R.id.show_result);
//		btn_confirm = (Button) findViewById(R.id.btn_confirm);
//		bt_sedentary_open = (Button) findViewById(R.id.bt_sedentary_open);
//		bt_sedentary_close = (Button) findViewById(R.id.bt_sedentary_close);
//		btn_sync_step = (Button) findViewById(R.id.btn_sync_step);
//		btn_sync_sleep = (Button) findViewById(R.id.btn_sync_sleep);
//		btn_sync_rate = (Button) findViewById(R.id.btn_sync_rate);
//		btn_rate_start = (Button) findViewById(R.id.btn_rate_start);
//		btn_rate_stop = (Button) findViewById(R.id.btn_rate_stop);
//		btn_confirm.setOnClickListener(this);
//		bt_sedentary_open.setOnClickListener(this);
//		bt_sedentary_close.setOnClickListener(this);
//		btn_sync_step.setOnClickListener(this);
//		btn_sync_sleep.setOnClickListener(this);
//		btn_sync_rate.setOnClickListener(this);
//		btn_rate_start.setOnClickListener(this);
//		btn_rate_stop.setOnClickListener(this);
//		read_ble_version = (Button) findViewById(R.id.read_ble_version);
//		read_ble_version.setOnClickListener(this);
//		read_ble_battery = (Button) findViewById(R.id.read_ble_battery);
//		read_ble_battery.setOnClickListener(this);
//		set_ble_time = (Button) findViewById(R.id.set_ble_time);
//		set_ble_time.setOnClickListener(this);
//		update_ble = (Button) findViewById(R.id.update_ble);
//		update_ble.setOnClickListener(this);
//		et_height.setText(sp.getString(GlobalVariable.PERSONAGE_HEIGHT, "175"));
//		et_weight.setText(sp.getString(GlobalVariable.PERSONAGE_WEIGHT, "60"));

        mDataProcessing = DataProcessing.getInstance(mContext);
//		mDataProcessing.setOnStepChangeListener(mOnStepChangeListener);
//		mDataProcessing.setOnSleepChangeListener(mOnSleepChangeListener);
        mDataProcessing.setOnRateListener(mOnRateListener);
//		mDataProcessing.setOnBloodPressureListener(mOnBloodPressureListener);

//		Button open_alarm = (Button) findViewById(R.id.open_alarm);
//		open_alarm.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mWriteCommand.sendToSetAlarmCommand(1, GlobalVariable.EVERYDAY,
//						16, 25, true);
//			}
//		});
//		Button close_alarm = (Button) findViewById(R.id.close_alarm);
//		close_alarm.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mWriteCommand.sendToSetAlarmCommand(1, GlobalVariable.EVERYDAY,
//						16, 23, false);
//			}
//		});

        Log.d("onStepHandler", "main_mDataProcessing =" + mDataProcessing);

//		unit = (Button) findViewById(R.id.unit);
//		unit.setOnClickListener(this);
//		test_channel = (Button) findViewById(R.id.test_channel);
//		test_channel.setOnClickListener(this);
//		push_message_content = (Button) findViewById(R.id.push_message_content);
//		push_message_content.setOnClickListener(this);

//		btn_sync_swim = (Button) findViewById(R.id.btn_sync_swim);
//		btn_sync_swim.setOnClickListener(this);
//		swim_time = (TextView) findViewById(R.id.swim_time);
//		swim_stroke_count = (TextView) findViewById(R.id.swim_stroke_count);
//		swim_calorie = (TextView) findViewById(R.id.swim_calorie);

//		tv_low_pressure = (TextView) findViewById(R.id.tv_low_pressure);
//		tv_high_pressure = (TextView) findViewById(R.id.tv_high_pressure);
//		btn_sync_pressure =(Button) findViewById(R.id.btn_sync_pressure);
//		btn_start_pressure =(Button) findViewById(R.id.btn_start_pressure);
//		btn_stop_pressure =(Button) findViewById(R.id.btn_stop_pressure);
//		btn_sync_pressure.setOnClickListener(this);
//		btn_start_pressure.setOnClickListener(this);
//		btn_stop_pressure.setOnClickListener(this);
    }

    /**
     * 计步监听 在这里更新UI
     */
    private StepChangeListener mOnStepChangeListener = new StepChangeListener() {

        @Override
        public void onStepChange(int steps, float distance, int calories) {
            Log.d("onStepHandler", "steps =" + steps + ",distance =" + distance
                    + ",calories =" + calories);
            mSteps = steps;
            mDistance = distance;
            mCalories = calories;
            mHandler.sendEmptyMessage(UPDATE_STEP_UI_MSG);
        }

    };
    /**
     * 睡眠监听 在这里更新UI
     */
    private SleepChangeListener mOnSleepChangeListener = new SleepChangeListener() {

        @Override
        public void onSleepChange() {
            mHandler.sendEmptyMessage(UPDATE_SLEEP_UI_MSG);
        }

    };

    public static byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    public RateChangeListener mOnRateListener = new RateChangeListener() {

        @Override
        public void onRateChange(int rate, int status) {
            tempRate = rate;
            tempStatus = status;
            Log.i("BluetoothLeService", "Rate_tempRate =" + tempRate);
            mHandler.sendEmptyMessage(UPDATA_REAL_RATE_MSG);
//			String ttempRate = Integer.toString(tempRate);
//			byte[] bytes = ttempRate.getBytes(Charset.defaultCharset());
            mBluetoothConnection.write(tempRate);
            mSeries2.appendData(new DataPoint(graph2LastXValue, tempRate), true, 10000);
            graph2LastXValue += 1;

//			handler.post(new Runnable() {
//				public void run() {
//					int step = 42;
//					int CCT;
//					CCT = tempRate * step;
//					tvCCT.setText(CCT + " K");
//					if(tempRate <70){
//						tvStress.setText("Low");
//						tvStress.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorGreen));
//					}else if(tempRate > 69 && tempRate < 90){
//						tvStress.setText("Normal");
//						tvStress.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorBlack));
//					}
//					else{
//						tvStress.setText("High");
//						tvStress.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorRed));
//					}}
//			});

//			Fragment heart_fragment1 = new Heart_Fragment1();//Get Fragment Instance
//			Bundle data = new Bundle();//create bundle instance
//			data.putInt("key", tempRate);//put string to pass with a key value
//			heart_fragment1.setArguments(data);//Set bundle data to fragment

//			FragmentManager fm = getSupportFragmentManager();
//			Heart_Fragment1 fragment = (Heart_Fragment1) fm.findFragmentById(R.id.vpPager);
//			fragment.setStress(tempRate);

            Heart_Fragment1 fragment = (Heart_Fragment1) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":1");
            fragment.setStress(tempRate);

            Heart_Fragment2 fragment1 = (Heart_Fragment2) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":2");
            fragment1.setCCT(tempRate);

            Heart_Fragment3 fragment2 = (Heart_Fragment3) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
            fragment2.setMinMax(tempRate);
        }
    };
    private BloodPressureChangeListener mOnBloodPressureListener = new BloodPressureChangeListener() {

        @Override
        public void onBloodPressureChange(int hightPressure, int lowPressure,
                                          int status) {
            tempBloodPressureStatus = status;
            high_pressure = hightPressure;
            low_pressure = lowPressure;
            mHandler.sendEmptyMessage(UPDATA_REAL_BLOOD_PRESSURE_MSG);

        }

    };
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RATE_SYNC_FINISH_MSG:
                    UpdateUpdataRateMainUI(CalendarUtils.getCalendar(0));
                    Toast.makeText(mContext, "Rate sync finish", 0).show();
                    break;
                case UPDATA_REAL_RATE_MSG:
                    tv_rate.setText(tempRate + "  bpm");// 实时跳变
                    if (tempStatus == GlobalVariable.RATE_TEST_FINISH) {
                        UpdateUpdataRateMainUI(CalendarUtils.getCalendar(0));
                        Toast.makeText(mContext, "Rate test finish", 0).show();
                    }
                    break;
                case GlobalVariable.GET_RSSI_MSG:
                    Bundle bundle = msg.getData();
                    rssi_tv.setText(bundle.getInt(GlobalVariable.EXTRA_RSSI) + "");
                    break;
                case UPDATE_STEP_UI_MSG:
                    updateSteps(mSteps);
                    updateCalories(mCalories);
                    updateDistance(mDistance);

                    Log.d("onStepHandler", "mSteps =" + mSteps + ",mDistance ="
                            + mDistance + ",mCalories =" + mCalories);
                    break;
                case UPDATE_SLEEP_UI_MSG:
                    querySleepInfo();
                    Log.d("getSleepInfo", "UPDATE_SLEEP_UI_MSG");
                    break;
                case NEW_DAY_MSG:
                    mySQLOperate.updateStepSQL();
                    mySQLOperate.updateSleepSQL();
                    mySQLOperate.updateRateSQL();
                    mySQLOperate.isDeleteRefreshTable();
                    // resetValues();
                    break;
                case GlobalVariable.START_PROGRESS_MSG:
                    Log.i(TAG, "(Boolean) msg.obj=" + (Boolean) msg.obj);
                    isUpdateSuccess = (Boolean) msg.obj;
                    Log.i(TAG, "BisUpdateSuccess=" + isUpdateSuccess);
                    startProgressDialog();
                    mHandler.postDelayed(mDialogRunnable, TIME_OUT);
                    break;
                case GlobalVariable.DOWNLOAD_IMG_FAIL_MSG:
                    Toast.makeText(HeartRate.this, R.string.download_fail, 1)
                            .show();
//				if (mProgressDialog != null) {
//					mProgressDialog.dismiss();
//					mProgressDialog = null;
//				}
                    if (mDialogRunnable != null)
                        mHandler.removeCallbacks(mDialogRunnable);
                    break;
                case GlobalVariable.DISMISS_UPDATE_BLE_DIALOG_MSG:
                    Log.i(TAG, "(Boolean) msg.obj=" + (Boolean) msg.obj);
                    isUpdateSuccess = (Boolean) msg.obj;
                    Log.i(TAG, "BisUpdateSuccess=" + isUpdateSuccess);
//				if (mProgressDialog != null) {
//					mProgressDialog.dismiss();
//					mProgressDialog = null;
//				}
                    if (mDialogRunnable != null) {
                        mHandler.removeCallbacks(mDialogRunnable);
                    }

                    if (isUpdateSuccess) {
                        Toast.makeText(
                                mContext,
                                getResources().getString(
                                        R.string.ble_update_successful), 0).show();
                    }
                    break;
                case GlobalVariable.SERVER_IS_BUSY_MSG:
                    Toast.makeText(mContext,
                            getResources().getString(R.string.server_is_busy), 0)
                            .show();
                    break;
                case DISCONNECT_MSG:
                    connect_status.setText(getString(R.string.disconnect));
                    connect_status.setTextColor(Color.RED);
                    CURRENT_STATUS = DISCONNECTED;
                    Toast.makeText(mContext, "Disconnected", 0)
                            .show();

                    String lastConnectAddr0 = sp.getString(
                            GlobalVariable.LAST_CONNECT_DEVICE_ADDRESS_SP, "");
                    boolean connectResute0 = mBLEServiceOperate
                            .connect(lastConnectAddr0);
                    Log.i(TAG, "connectResute0=" + connectResute0);

                    break;
                case CONNECTED_MSG:
                    connect_status.setText(getString(R.string.connected));
                    connect_status.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorGreen));
                    mBluetoothLeService.setRssiHandler(mHandler);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (!Thread.interrupted()) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }
                                if (mBluetoothLeService != null) {
                                    mBluetoothLeService.readRssi();
                                }
                            }
                        }
                    }).start();
                    CURRENT_STATUS = CONNECTED;
                    Toast.makeText(mContext, "Connected", 0).show();
                    break;

                case GlobalVariable.UPDATE_BLE_PROGRESS_MSG: // (新) 增加固件升级进度
                    int schedule = msg.arg1;
                    Log.i("zznkey", "schedule =" + schedule);
//				if (mProgressDialog == null) {
//					startProgressDialog();
//				}
//				mProgressDialog.setSchedule(schedule);
                    break;
                case OPEN_CHANNEL_OK_MSG://打开通道OK
                    test_channel.setText(getResources().getString(R.string.open_channel_ok));
                    resultBuilder.append(getResources().getString(R.string.open_channel_ok) + ",");
                    show_result.setText(resultBuilder.toString());

                    mWriteCommand.sendAPDUToBLE(WriteCommandToBLE.hexString2Bytes(testKey1));
                    break;
                case CLOSE_CHANNEL_OK_MSG://关闭通道OK
                    test_channel.setText(getResources().getString(R.string.close_channel_ok));
                    resultBuilder.append(getResources().getString(R.string.close_channel_ok) + ",");
                    show_result.setText(resultBuilder.toString());
                    break;
                case TEST_CHANNEL_OK_MSG://通道测试OK
                    test_channel.setText(getResources().getString(R.string.test_channel_ok));
                    resultBuilder.append(getResources().getString(R.string.test_channel_ok) + ",");
                    show_result.setText(resultBuilder.toString());
                    mWriteCommand.closeBLEchannel();
                    break;

                case SHOW_SET_PASSWORD_MSG:
                    showPasswordDialog(GlobalVariable.PASSWORD_TYPE_SET);
                    break;
                case SHOW_INPUT_PASSWORD_MSG:
                    showPasswordDialog(GlobalVariable.PASSWORD_TYPE_INPUT);
                    break;
                case SHOW_INPUT_PASSWORD_AGAIN_MSG:
                    showPasswordDialog(GlobalVariable.PASSWORD_TYPE_INPUT_AGAIN);
                    break;
                case OFFLINE_SWIM_SYNC_OK_MSG:
                    upDateTodaySwimData();
                    Toast.makeText(HeartRate.this,
                            getResources().getString(R.string.sync_swim_finish), 0)
                            .show();
                    break;

                case UPDATA_REAL_BLOOD_PRESSURE_MSG:
                    tv_low_pressure.setText(low_pressure + "");// 实时跳变
                    tv_high_pressure.setText(high_pressure + "");// 实时跳变
                    if (tempBloodPressureStatus == GlobalVariable.BLOOD_PRESSURE_TEST_FINISH) {
                        UpdateBloodPressureMainUI(CalendarUtils.getCalendar(0));
                        Toast.makeText(mContext, getResources().getString(R.string.test_pressure_ok), 0).show();
                    }
                    break;
                case OFFLINE_BLOOD_PRESSURE_SYNC_OK_MSG:
                    UpdateBloodPressureMainUI(CalendarUtils.getCalendar(0));
                    Toast.makeText(HeartRate.this,
                            getResources().getString(R.string.sync_pressure_ok), 0)
                            .show();
                    break;
                default:
                    break;
            }
        }
    };

    /*
	 * 获取一天最新心率值、最高、最低、平均心率值
	 */
    public void UpdateUpdataRateMainUI(String calendar) {
        UTESQLOperate mySQLOperate = new UTESQLOperate(mContext);
        RateOneDayInfo mRateOneDayInfo = mySQLOperate
                .queryRateOneDayMainInfo(calendar);
        if (mRateOneDayInfo != null) {
            int currentRate = mRateOneDayInfo.getCurrentRate();
            int lowestValue = mRateOneDayInfo.getLowestRate();
            int averageValue = mRateOneDayInfo.getVerageRate();
            int highestValue = mRateOneDayInfo.getHighestRate();
            // current_rate.setText(currentRate + "");
            if (currentRate == 0) {
                tv_rate.setText("--");
            } else {
                tv_rate.setText(currentRate + "");
            }
//            if (lowestValue == 0) {
//                tv_lowest_rate.setText("--");
//            } else {
//                tv_lowest_rate.setText(lowestValue + "");
//            }
//            if (averageValue == 0) {
//                tv_verage_rate.setText("--");
//            } else {
//                tv_verage_rate.setText(averageValue + "");
//            }
//            if (highestValue == 0) {
//                tv_highest_rate.setText("--");
//            } else {
//                tv_highest_rate.setText(highestValue + "");
//            }
        } else {
            tv_rate.setText("--");
        }
    }

    /*
	 * 获取一天各测试时间点和心率值
	 */
    private void getOneDayRateinfo(String calendar) {
        UTESQLOperate mySQLOperate = new UTESQLOperate(mContext);
        List<RateOneDayInfo> mRateOneDayInfoList = mySQLOperate
                .queryRateOneDayDetailInfo(calendar);
        if (mRateOneDayInfoList != null && mRateOneDayInfoList.size() > 0) {
            int size = mRateOneDayInfoList.size();
            int[] rateValue = new int[size];
            int[] timeArray = new int[size];
            for (int i = 0; i < size; i++) {
                rateValue[i] = mRateOneDayInfoList.get(i).getRate();
                timeArray[i] = mRateOneDayInfoList.get(i).getTime();
                Log.d(TAG, "rateValue[" + i + "]=" + rateValue[i]
                        + "timeArray[" + i + "]=" + timeArray[i]);
            }
        } else {

        }
    }

    private void startProgressDialog() {
//		if (mProgressDialog == null) {
//			mProgressDialog = CustomProgressDialog
//					.createDialog(MainActivity.this);
//			mProgressDialog.setMessage(getResources().getString(
//					R.string.ble_updating));
//			mProgressDialog.setCancelable(false);
//			mProgressDialog.setCanceledOnTouchOutside(false);
//		}
//
//		mProgressDialog.show();
    }

    private Runnable mDialogRunnable = new Runnable() {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            // mDownloadButton.setText(R.string.suota_update_succeed);
//			if (mProgressDialog != null) {
//				mProgressDialog.dismiss();
//				mProgressDialog = null;
//			}
            mHandler.removeCallbacks(mDialogRunnable);
            if (!isUpdateSuccess) {
                Toast.makeText(HeartRate.this,
                        getResources().getString(R.string.ble_fail_update), 0)
                        .show();
                mUpdates.clearUpdateSetting();
            } else {
                isUpdateSuccess = false;
                Toast.makeText(
                        HeartRate.this,
                        getResources()
                                .getString(R.string.ble_update_successful), 0)
                        .show();
            }

        }
    };

    private void updateSteps(int steps) {
        stepDistance = steps - mlastStepValue;
        Log.d("upDateSteps", "stepDistance =" + stepDistance
                + ",lastStepDistance=" + lastStepDistance + ",steps =" + steps);
        if (stepDistance > 3 || stepDistance < 0) {
            if (tv_distance != null) {
                if (steps <= 0) {
                    tv_steps.setText("0");
                } else {
                    tv_steps.setText("" + steps);
                }
            }
        } else {

            switch (stepDistance) {
                case 0:
                    switch (lastStepDistance) {
                        case 0:
                            if (tv_steps != null) {
                                if (steps <= 0) {
                                    tv_steps.setText("0");
                                } else {
                                    try {
                                        Thread.sleep(400);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    tv_steps.setText("" + steps);
                                }
                            }
                            break;
                        case 1:
                            if (tv_steps != null) {
                                if (steps <= 0) {
                                    tv_steps.setText("0");
                                } else {
                                    tv_steps.setText("" + steps);
                                }
                            }
                            break;
                        case 2:
                            if (tv_steps != null) {
                                if (steps <= 0) {
                                    tv_steps.setText("0");
                                } else {
                                    try {
                                        Thread.sleep(400);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    tv_steps.setText("" + steps);
                                }
                            }
                            break;
                        case 3:
                            if (tv_steps != null) {
                                if (steps <= 0) {
                                    tv_steps.setText("0");
                                } else {
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                    tv_steps.setText("" + (steps - 1));
                                }
                            }
                            break;

                        default:
                            break;
                    }

                    break;
                case 1:
                    if (tv_steps != null) {
                        if (steps <= 0) {
                            tv_steps.setText("0");
                        } else {
                            tv_steps.setText("" + steps);
                        }
                    }
                    break;
                case 2:

                    if (tv_steps != null) {
                        if (steps <= 0) {
                            tv_steps.setText("0");
                        } else {
                            tv_steps.setText("" + (steps - 1));
                        }
                    }
                    break;
                case 3:
                    if (tv_steps != null) {
                        if (steps <= 0) {
                            tv_steps.setText("0");
                        } else {

                            tv_steps.setText("" + (steps - 2));
                        }
                    }
                    break;

                default:
                    break;
            }
        }
        mlastStepValue = steps;
        lastStepDistance = stepDistance;

    }

    private void updateCalories(int mCalories) {
        if (mCalories <= 0) {
            tv_calorie.setText(mContext.getResources().getString(
                    R.string.zero_kilocalorie));
        } else {
            tv_calorie.setText("" + (int) mCalories + " "
                    + mContext.getResources().getString(R.string.kilocalorie));
        }

    }

    private void updateDistance(float mDistance) {
        if (mDistance < 0.01) {
            tv_distance.setText(mContext.getResources().getString(
                    R.string.zero_kilometers));

        } else if (mDistance >= 100) {
            tv_distance.setText(("" + mDistance).substring(0, 3) + " "
                    + mContext.getResources().getString(R.string.kilometers));
        } else {
            tv_distance.setText(("" + (mDistance + 0.000001f)).substring(0, 4)
                    + " "
                    + mContext.getResources().getString(R.string.kilometers));
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        boolean ble_connecte = sp.getBoolean(GlobalVariable.BLE_CONNECTED_SP,
                false);
        if (ble_connecte) {
            connect_status.setText(getString(R.string.connected));
            connect_status.setTextColor(ContextCompat.getColor(HeartRate.this, R.color.colorGreen));
//            connect_status.setTextColor(Color.parseColor("#50f442")); // green
        } else {
            connect_status.setText(getString(R.string.disconnect));
            connect_status.setTextColor(Color.RED);
        }
        JudgeNewDayWhenResume();

    }

    private void JudgeNewDayWhenResume() {
        isFirstOpenAPK = sp.getBoolean(GlobalVariable.FIRST_OPEN_APK, true);
        editor.putBoolean(GlobalVariable.FIRST_OPEN_APK, false);
        editor.commit();
        lastDay = sp.getInt(GlobalVariable.LAST_DAY_NUMBER_SP, 0);
        lastDayString = sp.getString(GlobalVariable.LAST_DAY_CALLENDAR_SP,
                "20101201");
        Calendar c = Calendar.getInstance();
        currentDay = c.get(Calendar.DAY_OF_YEAR);
        currentDayString = CalendarUtils.getCalendar(0);

        if (isFirstOpenAPK) {
            lastDay = currentDay;
            lastDayString = currentDayString;
            editor = sp.edit();
            editor.putInt(GlobalVariable.LAST_DAY_NUMBER_SP, lastDay);
            editor.putString(GlobalVariable.LAST_DAY_CALLENDAR_SP,
                    lastDayString);
            editor.commit();
        } else {

            if (currentDay != lastDay) {
                if ((lastDay + 1) == currentDay || currentDay == 1) { // 连续的日期
                    mHandler.sendEmptyMessage(NEW_DAY_MSG);
                } else {
                    mySQLOperate.insertLastDayStepSQL(lastDayString);
                    mySQLOperate.updateSleepSQL();
                    // resetValues();
                }
                lastDay = currentDay;
                lastDayString = currentDayString;
                editor.putInt(GlobalVariable.LAST_DAY_NUMBER_SP, lastDay);
                editor.putString(GlobalVariable.LAST_DAY_CALLENDAR_SP,
                        lastDayString);
                editor.commit();
            } else {
                Log.d("b1offline", "currentDay == lastDay");
            }
        }

    }

    private void resetValues() {
        editor.putInt(GlobalVariable.YC_PED_UNFINISH_HOUR_STEP_SP, 0);
        editor.putInt(GlobalVariable.YC_PED_UNFINISH_HOUR_VALUE_SP, 0);
        editor.putInt(GlobalVariable.YC_PED_LAST_HOUR_STEP_SP, 0);
        editor.commit();
        tv_steps.setText("0");
        tv_calorie.setText(mContext.getResources().getString(
                R.string.zero_kilocalorie));
        tv_distance.setText(mContext.getResources().getString(
                R.string.zero_kilometers));
        tv_sleep.setText("0");
        tv_deep.setText(mContext.getResources().getString(
                R.string.zero_hour_zero_minute));
        tv_light.setText(mContext.getResources().getString(
                R.string.zero_hour_zero_minute));
        tv_awake.setText(mContext.getResources().getString(R.string.zero_count));

        tv_rate.setText("--");
        tv_lowest_rate.setText("--");
        tv_verage_rate.setText("--");
        tv_highest_rate.setText("--");
    }

    @Override
    public void onClick(View v) {
        boolean ble_connecte = sp.getBoolean(GlobalVariable.BLE_CONNECTED_SP,
                false);
        switch (v.getId()) {
//		case R.id.btn_confirm:
//			if (ble_connecte) {
//				String height = et_height.getText().toString();
//				String weight = et_weight.getText().toString();
//				if (height.equals("") || weight.equals("")) {
//					Toast.makeText(mContext, "身高或体重不能为空", 0).show();
//				} else {
//					int Height = Integer.valueOf(height);
//					int Weight = Integer.valueOf(weight);
//					mWriteCommand.sendStepLenAndWeightToBLE(Height, Weight, 5, 10000, true, true, 150);
//					// 设置步长，体重，灭屏时间5s,目标步数，抬手亮屏开关true为开，false为关；最高心率提醒，true为开，false为关；最后一个参数为最高心率提醒的值
//				}
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.bt_sedentary_open:
//			String period = et_sedentary_period.getText().toString();
//			if (period.equals("")) {
//				Toast.makeText(mContext, "Please input remind peroid", 0)
//						.show();
//			} else {
//				int period_time = Integer.valueOf(period);
//				if (period_time < 30) {
//					Toast.makeText(
//							mContext,
//							"Please make sure period_time more than 30 minutes",
//							0).show();
//				} else {
//					if (ble_connecte) {
//						mWriteCommand.sendSedentaryRemindCommand(
//								GlobalVariable.OPEN_SEDENTARY_REMIND,
//								period_time);
//					} else {
//						Toast.makeText(mContext,
//								getString(R.string.disconnect),
//								Toast.LENGTH_SHORT).show();
//					}
//				}
//			}
//			break;
//		case R.id.bt_sedentary_close:
//			if (ble_connecte) {
//				mWriteCommand.sendSedentaryRemindCommand(
//						GlobalVariable.CLOSE_SEDENTARY_REMIND, 0);
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.btn_sync_step:
//			if (ble_connecte) {
//				mWriteCommand.syncAllStepData();
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//
//			break;
//		case R.id.btn_sync_sleep:
//			if (ble_connecte) {
//				mWriteCommand.syncAllSleepData();
////				mWriteCommand.syncWeatherToBLE(mContext, "桂林市"); //测试天气接口
////				mWriteCommand.syncWeatherToBLE(mContext, "深圳市");
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.btn_sync_rate:
//			if (ble_connecte) {
//				mWriteCommand.syncAllRateData();
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
////		case R.id.btn_rate_start:
//			if (ble_connecte) {
//				mWriteCommand
//						.sendRateTestCommand(GlobalVariable.RATE_TEST_START);
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.btn_rate_stop:
//			if (ble_connecte) {
//				mWriteCommand
//						.sendRateTestCommand(GlobalVariable.RATE_TEST_STOP);
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;

//		case R.id.read_ble_version:
//			if (ble_connecte) {
//				mWriteCommand.sendToReadBLEVersion(); // 发送请求BLE版本号
//				// getOneDayRateinfo(CalendarUtils.getCalendar(0));
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.read_ble_battery:
//			if (ble_connecte) {
//				mWriteCommand.sendToReadBLEBattery();// 请求获取电量指令
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.set_ble_time:
//			if (ble_connecte) {
//				mWriteCommand.syncBLETime();
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.update_ble:
//			boolean ble_connected = sp.getBoolean(
//					GlobalVariable.BLE_CONNECTED_SP, false);
//
//			if (ble_connected) {
//				mWriteCommand.queryDeviceFearture();
//				if (isNetworkAvailable(mContext)) {
//					String localVersion = sp.getString(
//							GlobalVariable.IMG_LOCAL_VERSION_NAME_SP, "0");
//					if (!localVersion.equals("0")) {
//						int status = mUpdates.getBLEVersionStatus(localVersion);
//						if (status == GlobalVariable.OLD_VERSION_STATUS) {
//							updateBleDialog();// update remind
//						} else if (status == GlobalVariable.NEWEST_VERSION_STATUS) {
//							Toast.makeText(
//									mContext,
//									getResources().getString(
//											R.string.ble_is_newest), 0).show();
//						} else if (status == GlobalVariable.FREQUENT_ACCESS_STATUS) {
//							Toast.makeText(
//									mContext,
//									getResources().getString(
//											R.string.frequent_access_server), 0)
//									.show();
//						}
//					} else {
//						Toast.makeText(
//								mContext,
//								getResources().getString(
//										R.string.read_ble_version_first), 0)
//								.show();
//					}
//				} else {
//					Toast.makeText(
//							mContext,
//							getResources().getString(
//									R.string.confire_is_network_available), 0)
//							.show();
//
//				}
//			} else {
//				Toast.makeText(
//						mContext,
//						getResources().getString(
//								R.string.please_connect_bracelet), 0).show();
//			}
//			break;
            // case 11:
            // mWriteCommand.sendToSetAlarmCommand(1, (byte) 33, 12, 22, true);
            // break;

//		case R.id.unit:
//			boolean ble_connected3 = sp.getBoolean(
//					GlobalVariable.BLE_CONNECTED_SP, false);
//			if (ble_connected3) {
//				if (unit.getText()
//						.toString()
//						.equals(getResources()
//								.getString(R.string.metric_system))) {
//					editor.putBoolean(GlobalVariable.IS_METRIC_UNIT_SP, true);
//					editor.commit();
//					mWriteCommand.sendUnitToBLE();
//					unit.setText(getResources().getString(R.string.inch_system));
//				} else {
//					editor.putBoolean(GlobalVariable.IS_METRIC_UNIT_SP, false);
//					editor.commit();
//					mWriteCommand.sendUnitToBLE();
//					unit.setText(getResources().getString(
//							R.string.metric_system));
//				}
//			} else {
//				Toast.makeText(
//						mContext,
//						getResources().getString(
//								R.string.please_connect_bracelet), 0).show();
//			}
//
//			break;
//		case R.id.test_channel:
//			boolean ble_connected4 = sp.getBoolean(
//					GlobalVariable.BLE_CONNECTED_SP, false);
//			if (ble_connected4) {
//				if (test_channel.getText().toString().equals(getResources().getString(R.string.test_channel))
//						|| test_channel.getText().toString().equals(getResources().getString(R.string.test_channel_ok))
//						|| test_channel.getText().toString().equals(getResources().getString(R.string.close_channel_ok))) {
//					resultBuilder = new StringBuilder();
//					mWriteCommand.openBLEchannel();
//				} else {
//					Toast.makeText(
//							mContext,
//							getResources().getString(R.string.channel_testting),
//							0).show();
//				}
//			} else {
//				Toast.makeText(
//						mContext,
//						getResources().getString(
//								R.string.please_connect_bracelet), 0).show();
//			}
//
//			break;
//		case R.id.push_message_content:
//			if (ble_connecte) {
//				String pushContent = getResources().getString(R.string.push_message_content);//推送的内容
//				mWriteCommand.sendTextToBle(pushContent,GlobalVariable.TYPE_QQ);
//
////				mWriteCommand.sendTextToBle(pushContent,GlobalVariable.TYPE_WECHAT);
////				editor.putString(GlobalVariable.SMS_RECEIVED_NUMBER, "18045811234");//保存推送短信的号码,短信推送时，必须
////				editor.commit();
////				mWriteCommand.sendTextToBle(pushContent,GlobalVariable.TYPE_SMS);
////				mWriteCommand.sendTextToBle(pushContent,GlobalVariable.TYPE_PHONE);
//
//				show_result.setText(pushContent);
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.btn_sync_swim:
//			if (ble_connecte) {
//				mWriteCommand.syncAllSwimData();
//				show_result.setText(mContext.getResources().getString(R.string.sync_swim));
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.btn_sync_pressure:
//			if (ble_connecte) {
//				mWriteCommand.syncAllBloodPressureData();
//				show_result.setText(mContext.getResources().getString(R.string.sync_pressure));
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.btn_start_pressure:
//			if (ble_connecte) {
//				mWriteCommand.sendBloodPressureTestCommand(GlobalVariable.BLOOD_PRESSURE_TEST_START);
//				show_result.setText(mContext.getResources().getString(R.string.start_pressure));
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
//		case R.id.btn_stop_pressure:
//			if (ble_connecte) {
//				mWriteCommand.sendBloodPressureTestCommand(GlobalVariable.BLOOD_PRESSURE_TEST_STOP);
//				show_result.setText(mContext.getResources().getString(R.string.stop_pressure));
//			} else {
//				Toast.makeText(mContext, getString(R.string.disconnect),
//						Toast.LENGTH_SHORT).show();
//			}
//			break;
            default:
                break;
        }
    }


    public void onBackPressed(){
        mWriteCommand
                .sendRateTestCommand(GlobalVariable.RATE_TEST_STOP);
        super.onBackPressed();
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (CURRENT_STATUS == CONNECTING) {
            Builder builder = new Builder(this);
            builder.setMessage("Cancel connection?");
            builder.setTitle("Exit");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                                    .getDefaultAdapter();
                            if (mBluetoothAdapter == null) {
                                finish();
                            }
                            if (mBluetoothAdapter.isEnabled()) {
                                mBluetoothAdapter.disable();// 关闭蓝牙
                            }
                            finish();
                        }
                    });
            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private boolean updateBleDialog() {

        final AlertDialog alert = new Builder(this).setCancelable(
                false).create();
        alert.show();
        window = alert.getWindow();
        window.setContentView(R.layout.update_dialog_layout);
        Button btn_yes = (Button) window.findViewById(R.id.btn_yes);
        Button btn_no = (Button) window.findViewById(R.id.btn_no);
        TextView update_warn_tv = (TextView) window
                .findViewById(R.id.update_warn_tv);
        update_warn_tv.setText(getResources().getString(
                R.string.find_new_version_ble));

        btn_yes.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(mContext)) {
                    mUpdates.startUpdateBLE();
                } else {
                    Toast.makeText(
                            mContext,
                            getResources().getString(
                                    R.string.confire_is_network_available), 0)
                            .show();
                }

                alert.dismiss();
            }
        });
        btn_no.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mUpdates.clearUpdateSetting();
                alert.dismiss();
            }

        });
        return false;

    }

    /**
     * 获取今天睡眠详细，并更新睡眠UI CalendarUtils.getCalendar(0)代表今天，也可写成"20141101"
     * CalendarUtils.getCalendar(-1)代表昨天，也可写成"20141031"
     * CalendarUtils.getCalendar(-2)代表前天，也可写成"20141030" 以此类推
     */
    private void querySleepInfo() {
        SleepTimeInfo sleepTimeInfo = mySQLOperate.querySleepInfo(
                CalendarUtils.getCalendar(-1), CalendarUtils.getCalendar(0));
        int deepTime, lightTime, awakeCount, sleepTotalTime;
        if (sleepTimeInfo != null) {
            deepTime = sleepTimeInfo.getDeepTime();
            lightTime = sleepTimeInfo.getLightTime();
            awakeCount = sleepTimeInfo.getAwakeCount();
            sleepTotalTime = sleepTimeInfo.getSleepTotalTime();

            int[] colorArray = sleepTimeInfo.getSleepStatueArray();// 绘图中不同睡眠状态可用不同颜色表示，颜色自定义
            int[] timeArray = sleepTimeInfo.getDurationTimeArray();
            int[] timePointArray = sleepTimeInfo.getTimePointArray();

            Log.d("getSleepInfo", "Calendar=" + CalendarUtils.getCalendar(0)
                    + ",timeArray =" + timeArray + ",timeArray.length ="
                    + timeArray.length + ",colorArray =" + colorArray
                    + ",colorArray.length =" + colorArray.length
                    + ",timePointArray =" + timePointArray
                    + ",timePointArray.length =" + timePointArray.length);

            double total_hour = ((float) sleepTotalTime / 60f);
            DecimalFormat df1 = new DecimalFormat("0.0"); // 保留1位小数，带前导零

            int deep_hour = deepTime / 60;
            int deep_minute = (deepTime - deep_hour * 60);
            int light_hour = lightTime / 60;
            int light_minute = (lightTime - light_hour * 60);
            int active_count = awakeCount;
            String total_hour_str = df1.format(total_hour);

            if (total_hour_str.equals("0.0")) {
                total_hour_str = "0";
            }
            tv_sleep.setText(total_hour_str);
            tv_deep.setText(deep_hour + " "
                    + mContext.getResources().getString(R.string.hour) + " "
                    + deep_minute + " "
                    + mContext.getResources().getString(R.string.minute));
            tv_light.setText(light_hour + " "
                    + mContext.getResources().getString(R.string.hour) + " "
                    + light_minute + " "
                    + mContext.getResources().getString(R.string.minute));
            tv_awake.setText(active_count + " "
                    + mContext.getResources().getString(R.string.count));
        } else {
            Log.d("getSleepInfo", "sleepTimeInfo =" + sleepTimeInfo);
            tv_sleep.setText("0");
            tv_deep.setText(mContext.getResources().getString(
                    R.string.zero_hour_zero_minute));
            tv_light.setText(mContext.getResources().getString(
                    R.string.zero_hour_zero_minute));
            tv_awake.setText(mContext.getResources().getString(
                    R.string.zero_count));
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(GlobalVariable.READ_BLE_VERSION_ACTION)) {
                String version = intent
                        .getStringExtra(GlobalVariable.INTENT_BLE_VERSION_EXTRA);
                if (sp.getBoolean(BluetoothLeService.IS_RK_PLATFORM_SP, false)) {
                    show_result.setText("version=" + version + "," + sp.getString(GlobalVariable.PATH_LOCAL_VERSION_NAME_SP, ""));
                } else {
                    show_result.setText("version=" + version);
                }

            } else if (action.equals(GlobalVariable.READ_BATTERY_ACTION)) {
                int battery = intent.getIntExtra(
                        GlobalVariable.INTENT_BLE_BATTERY_EXTRA, -1);
                show_result.setText("battery=" + battery);

            }
        }
    };
    private Window window;

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onServerDiscorver", "about_onDestroy");
        GlobalVariable.BLE_UPDATE = false;
        mUpdates.unRegisterBroadcastReceiver();
        try {
            unregisterReceiver(mReceiver);
        } catch (Exception e) {
            // TODO: handle exception
        }


//		if (mProgressDialog != null) {
//			mProgressDialog.dismiss();
//			mProgressDialog = null;
//		}
        if (mDialogRunnable != null)
            mHandler.removeCallbacks(mDialogRunnable);

        mBLEServiceOperate.disConnect();
    }

    @Override
    public void OnResult(boolean result, int status) {
        // TODO Auto-generated method stub
        Log.i(TAG, "result=" + result + ",status=" + status);
        if (status == ICallbackStatus.OFFLINE_STEP_SYNC_OK) {
            // step snyc complete
        } else if (status == ICallbackStatus.OFFLINE_SLEEP_SYNC_OK) {
            // sleep snyc complete
        } else if (status == ICallbackStatus.SYNC_TIME_OK) {// after set time
            // finish, then(or delay 20ms) send
            // to read
            // localBleVersion
            // mWriteCommand.sendToReadBLEVersion();
        } else if (status == ICallbackStatus.GET_BLE_VERSION_OK) {// after read
            // localBleVersion
            // finish,
            // then sync
            // step
            // mWriteCommand.syncAllStepData();
        } else if (status == ICallbackStatus.DISCONNECT_STATUS) {
            mHandler.sendEmptyMessage(DISCONNECT_MSG);
        } else if (status == ICallbackStatus.CONNECTED_STATUS) {

            mHandler.sendEmptyMessage(CONNECTED_MSG);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mWriteCommand.sendToQueryPasswardStatus();

                }
            }, 150);
        } else if (status == ICallbackStatus.DISCOVERY_DEVICE_SHAKE) {
            // Discovery device Shake
        } else if (status == ICallbackStatus.OFFLINE_RATE_SYNC_OK) {
            mHandler.sendEmptyMessage(RATE_SYNC_FINISH_MSG);
        } else if (status == ICallbackStatus.SET_METRICE_OK) {// 设置公制单位成功

        } else if (status == ICallbackStatus.SET_METRICE_OK) {// 设置英制单位成功

        } else if (status == ICallbackStatus.SET_FIRST_ALARM_CLOCK_OK) {// 设置第1个闹钟OK

        } else if (status == ICallbackStatus.SET_SECOND_ALARM_CLOCK_OK) {// 设置第2个闹钟OK

        } else if (status == ICallbackStatus.SET_THIRD_ALARM_CLOCK_OK) {// 设置第3个闹钟OK

        } else if (status == ICallbackStatus.SEND_PHONE_NAME_NUMBER_OK) {//
            mWriteCommand.sendQQWeChatVibrationCommand(5);

        } else if (status == ICallbackStatus.SEND_QQ_WHAT_SMS_CONTENT_OK) {//
            mWriteCommand.sendQQWeChatVibrationCommand(1);

        } else if (status == ICallbackStatus.PASSWORD_SET) {
            Log.d(TAG, "没设置过密码，请设置4位数字密码");
            mHandler.sendEmptyMessage(SHOW_SET_PASSWORD_MSG);

        } else if (status == ICallbackStatus.PASSWORD_INPUT) {
            Log.d(TAG, "已设置过密码，请输入已设置的4位数字密码");
            mHandler.sendEmptyMessage(SHOW_INPUT_PASSWORD_MSG);

        } else if (status == ICallbackStatus.PASSWORD_AUTHENTICATION_OK) {
            Log.d(TAG, "验证成功或者设置密码成功");

        } else if (status == ICallbackStatus.PASSWORD_INPUT_AGAIN) {
            Log.d(TAG, "验证失败或者设置密码失败，请重新输入4位数字密码，如果已设置过密码，请输入已设置的密码");
            mHandler.sendEmptyMessage(SHOW_INPUT_PASSWORD_AGAIN_MSG);

        } else if (status == ICallbackStatus.OFFLINE_SWIM_SYNCING) {
            Log.d(TAG, "游泳数据同步中");
        } else if (status == ICallbackStatus.OFFLINE_SWIM_SYNC_OK) {
            Log.d(TAG, "游泳数据同步完成");
            mHandler.sendEmptyMessage(OFFLINE_SWIM_SYNC_OK_MSG);
        } else if (status == ICallbackStatus.OFFLINE_BLOOD_PRESSURE_SYNCING) {
            Log.d(TAG, "血压数据同步中");
        } else if (status == ICallbackStatus.OFFLINE_BLOOD_PRESSURE_SYNC_OK) {
            Log.d(TAG, "血压数据同步完成");
            mHandler.sendEmptyMessage(OFFLINE_BLOOD_PRESSURE_SYNC_OK_MSG);
        }
    }

    private final String testKey1 = "00a4040008A000000333010101";

    @Override
    public void OnDataResult(boolean result, int status, byte[] data) {
        StringBuilder stringBuilder = null;
        if (data != null && data.length > 0) {
            stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data) {
                stringBuilder.append(String.format("%02X", byteChar));
            }
            Log.i("testChannel", "BLE---->APK data =" + stringBuilder.toString());
        }
        if (status == ICallbackStatus.OPEN_CHANNEL_OK) {//打开通道OK
            mHandler.sendEmptyMessage(OPEN_CHANNEL_OK_MSG);
        } else if (status == ICallbackStatus.CLOSE_CHANNEL_OK) {//关闭通道OK
            mHandler.sendEmptyMessage(CLOSE_CHANNEL_OK_MSG);
        } else if (status == ICallbackStatus.BLE_DATA_BACK_OK) {//测试通道OK，通道正常
            mHandler.sendEmptyMessage(TEST_CHANNEL_OK_MSG);
        }
    }

    @Override
    public void OnServiceStatuslt(int status) {
        if (status == ICallbackStatus.BLE_SERVICE_START_OK) {
            if (mBluetoothLeService == null) {
                mBluetoothLeService = mBLEServiceOperate.getBleService();
                mBluetoothLeService.setICallback(this);
            }
        }
    }

    private static final int SHOW_SET_PASSWORD_MSG = 26;
    private static final int SHOW_INPUT_PASSWORD_MSG = 27;
    private static final int SHOW_INPUT_PASSWORD_AGAIN_MSG = 28;

    private boolean isPasswordDialogShowing = false;
    private String password = "";

    private void showPasswordDialog(final int type) {
        Log.d("CustomPasswordDialog", "showPasswordDialog");
        if (isPasswordDialogShowing) {
            Log.d("CustomPasswordDialog", "已有对话框弹出");
            return;
        }
//		CustomPasswordDialog.Builder builder = new CustomPasswordDialog.Builder(
//				HeartRate.this, mTextWatcher);
//		builder.setPositiveButton(
//				getResources().getString(R.string.confirm),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						 if (password.length()==4) {
//							 Log.d("CustomPasswordDialog", "密码是4位  password ="+password);
//							dialog.dismiss();
//							isPasswordDialogShowing =false;
//
//							mWriteCommand.sendToSetOrInputPassward(password,type);
//						}
//					}
//				});
//		builder.setNegativeButton(
//				getResources().getString(R.string.cancel),
//				new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						dialog.dismiss();
//						isPasswordDialogShowing =false;
//					}
//				});
//		builder.create().show();

//		if (type==GlobalVariable.PASSWORD_TYPE_SET) {
//			builder.setTittle(mContext.getResources().getString(R.string.set_password_for_band));
//		}else if (type==GlobalVariable.PASSWORD_TYPE_INPUT_AGAIN) {
//			builder.setTittle(mContext.getResources().getString(R.string.input_password_for_band_again));
//		}else {
//			builder.setTittle(mContext.getResources().getString(R.string.input_password_for_band));
//		}
//		isPasswordDialogShowing =true ;
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            password = s.toString();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };


    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";

    /**
     * 激活设备管理权限
     *
     * @return
     */
    private boolean isEnabled() {
        String pkgName = getPackageName();
        Log.w("ellison", "---->pkgName = " + pkgName);
        final String flat = Settings.Secure.getString(getContentResolver(),
                ENABLED_NOTIFICATION_LISTENERS);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName
                        .unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void upDateTodaySwimData() {
        // TODO Auto-generated method stub
        SwimInfo mSwimInfo = mySQLOperate.querySwimData(CalendarUtils.getCalendar(0));//传入日期，0为今天，-1为昨天，-2为前天。。。。
        if (mSwimInfo != null) {
            swim_time.setText(mSwimInfo.getSwimTime() + "");
            swim_stroke_count.setText(mSwimInfo.getSwimStrokeCount() + "");
            swim_calorie.setText(mSwimInfo.getCalories() + "");
        }
    }

    ;


    /*
	 * 获取一天最新心率值、最高、最低、平均心率值
	 */
    private void UpdateBloodPressureMainUI(String calendar) {
        UTESQLOperate mySQLOperate = new UTESQLOperate(mContext);
        List<BPVOneDayInfo> mBPVOneDayListInfo = mySQLOperate
                .queryBloodPressureOneDayInfo(calendar);
        if (mBPVOneDayListInfo != null) {
            int highPressure = 0;
            int lowPressure = 0;
            int time = 0;
            for (int i = 0; i < mBPVOneDayListInfo.size(); i++) {
                highPressure = mBPVOneDayListInfo.get(i).getHightBloodPressure();
                lowPressure = mBPVOneDayListInfo.get(i).getLowBloodPressure();
                time = mBPVOneDayListInfo.get(i).getBloodPressureTime();
            }
            Log.d("MySQLOperate", "highPressure =" + highPressure + ",lowPressure =" + lowPressure);
            // current_rate.setText(currentRate + "");
            if (highPressure == 0) {
                tv_high_pressure.setText("--");

            } else {
                tv_high_pressure.setText(highPressure + "");

            }
            if (lowPressure == 0) {
                tv_low_pressure.setText("--");
            } else {
                tv_low_pressure.setText(lowPressure + "");
            }

        } else {
            tv_high_pressure.setText("--");
            tv_low_pressure.setText("--");

        }
    }
//	public static class MyPagerAdapter extends FragmentPagerAdapter {
//		private static int NUM_ITEMS = 3;
//        private Map<Integer, String> mFragmentTags;
//        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
//
//		public MyPagerAdapter(FragmentManager fragmentManager) {
//			super(fragmentManager);
//            mFragmentTags = new HashMap<Integer, String>();
//		}
//
//		// Returns total number of pages
//		@Override
//		public int getCount() {
//			return NUM_ITEMS;
//		}
//
//
//		// Returns the fragment to display for that page
//		@Override
//		public Fragment getItem(int position) {
//			switch (position) {
//				case 0: // Fragment # 0 - This will show FirstFragment
//					return Heart_Fragment1.newInstance();
//				case 1: // Fragment # 0 - This will show FirstFragment different title
//					return Heart_Fragment2.newInstance(2, "Page # 2");
//				case 2: // Fragment # 1 - This will show SecondFragment
//					return Heart_Fragment3.newInstance(3, "Page # 3");
//				default:
//					return null;
//			}
//		}
//
//		// Returns the page title for the top indicator
//		@Override
//		public CharSequence getPageTitle(int position) {
//			return "Page " + position;
//		}
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            Object obj = super.instantiateItem(container, position);
//            if (obj instanceof Fragment) {
//                // record the fragment tag here.
//                Fragment f = (Fragment) obj;
//                String tag = f.getTag();
//                mFragmentTags.put(position, tag);
//            }
//            return obj;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            registeredFragments.remove(position);
//            super.destroyItem(container, position, object);
//        }
//
//        public Fragment getRegisteredFragment(int position) {
//            return registeredFragments.get(position);
//        }
//
//	}


    private class MyPagerAdapter extends FragmentPagerAdapter {
        // other code in your custom FragmentPagerAdapter...
        		private int NUM_ITEMS = 3;

		public MyPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

        		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

        @Override
        public Fragment getItem(int position) {
            // Do NOT try to save references to the Fragments in getItem(),
            // because getItem() is not always called. If the Fragment
            // was already created then it will be retrieved from the FragmentManger
            // and not here (i.e. getItem() won't be called again).
            switch (position) {
                case 0:
                    return new Heart_Fragment3();
                case 1:
                    return new Heart_Fragment1();
                case 2:
                    return new Heart_Fragment2();
                default:
                    // This should never happen. Always account for each position above
                    return null;
            }
        }

        // Here we can finally safely save a reference to the created
        // Fragment, no matter where it came from (either getItem() or
        // FragmentManger). Simply save the returned Fragment from
        // super.instantiateItem() into an appropriate reference depending
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment createdFragment = (Fragment) super.instantiateItem(container, position);
            // save the appropriate reference depending on position
            switch (position) {
                case 0:
                    m3rdFragment = (Heart_Fragment3) createdFragment;
                    break;
                case 1:
                    m1stFragment = (Heart_Fragment1) createdFragment;
                    break;
                case 2:
                    m2ndFragment = (Heart_Fragment2) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }

    public void someMethod() {
        // do work on the referenced Fragments, but first check if they
        // even exist yet, otherwise you'll get an NPE.

        if (m1stFragment != null) {
            // m1stFragment.doWork();
            m1stFragment.setStress(tempRate);
        }

        if (m2ndFragment != null) {
            // m2ndFragment.doSomeWorkToo();
        }
        if (m3rdFragment != null) {
            // m2ndFragment.doSomeWorkToo();
        }
    }
}


package com.osram.os.demos;

import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.UUID;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Horticulture extends AppCompatActivity implements View.OnClickListener {
    FragmentPagerAdapter adapterViewPager;
    private Horti_FragmentGraph mFragmentGraph;
    private Horti_FragmentPie mFragmentPie;

    Handler handler;
    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static final String TAG = "Horticulture";
    private SeekBar blueSeek, limeSeek, redSeek, farredSeek, purpleSeek;
    private TextView tvBlue, tvLime, tvRed, tvFarRed, tvPPF, tvBPF, tvPurple, tvReset;
    private Button btnPurple1, btnPurple2, btnWhite, btnReset;
    BluetoothDevice bluetoothDevice;

//    private LineGraphSeries<DataPoint> hSeries;
//    private int hgraphLastXValue = 0;
    double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};
    double redArray[] = {0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	1.04885769861551E-08, 	2.09813494031046E-08, 	1.04885769861551E-08, 	3.14699263892597E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	5.24428849307754E-08, 	8.39086158892406E-08, 	8.39086158892406E-08, 	1.15374346847706E-07, 	1.36393455127961E-07, 	1.67859186086426E-07, 	1.99324917044891E-07, 	2.51767801975667E-07, 	3.04210686906442E-07, 	3.77672680117472E-07, 	4.71985964376979E-07, 	5.9784888821084E-07, 	7.237118120447E-07, 	8.81040466837027E-07, 	1.09081200656013E-06, 	1.32198024333499E-06, 	1.63663755291964E-06, 	1.98276059346276E-06, 	2.42328082688127E-06, 	2.89526679125825E-06, 	3.47213852549678E-06, 	4.12284984171784E-06, 	4.82474541363134E-06, 	5.70578588046836E-06, 	6.75464357908387E-06, 	7.99229566345017E-06, 	9.51104161104543E-06, 	0.000011378008314581, 	1.36099774972348E-05, 	1.62405126053625E-05, 	1.92276593310195E-05, 	0.000022646935428506, 	2.63934551279606E-05, 	3.01903199969488E-05, 	3.39116671116366E-05, 	3.74232426866013E-05, 	0.000040326480796369, 	4.18284450207865E-05, 	4.08844730920325E-05, 	3.69995041763607E-05, 	3.08196346161181E-05, 	2.39894732827339E-05, 	1.77298905373965E-05, 	1.28338228002594E-05, 	9.20057973225523E-06, 	6.59521720889431E-06, 	4.71985964376979E-06, 	0.000003419695640566, 	2.49670086578435E-06, 	1.8568976696289E-06, 	1.38491170525192E-06, 	1.01739196765704E-06, 	7.97131850947786E-07, 	6.29314619169305E-07, 	4.92963118349289E-07, 	3.77672680117472E-07, 	3.04210686906442E-07, 	2.51767801975667E-07, 	1.99324917044891E-07, 	1.67859186086426E-07, 	1.36393455127961E-07, 	1.25904878141806E-07, 	9.43971928753957E-08, 	8.39086158892406E-08, 	8.39086158892406E-08, 	8.39086158892406E-08, 	5.24428849307754E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	4.19543079446203E-08, 	1.04885769861551E-08, 	3.14699263892597E-08, 	1.04885769861551E-08, 	0, 	0, 	2.09813494031046E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	2.09813494031046E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0};
    double red2Array[] = {8.64198895027624E-08, 	1.58388485024717E-07, 	1.58388485024717E-07, 	2.16020936318697E-07, 	1.15207327711544E-07, 	1.00813608607153E-07, 	2.88047106717069E-08, 	1.43994765920326E-08, 	2.88047106717069E-08, 	1.43994765920326E-08, 	0, 	2.88047106717069E-08, 	0, 	2.88047106717069E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	0, 	2.88047106717069E-08, 	0, 	0, 	0, 	2.88047106717069E-08, 	4.32041872637395E-08, 	1.43994765920326E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	4.32041872637395E-08, 	4.32041872637395E-08, 	0, 	1.43994765920326E-08, 	4.32041872637395E-08, 	0, 	0, 	0, 	0, 	0, 	2.88047106717069E-08, 	0, 	2.88047106717069E-08, 	0, 	0, 	0, 	1.43994765920326E-08, 	1.43994765920326E-08, 	2.88047106717069E-08, 	0, 	0, 	4.32041872637395E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	2.88047106717069E-08, 	2.88047106717069E-08, 	5.76324512939808E-08, 	4.32041872637395E-08, 	2.88047106717069E-08, 	0, 	1.43994765920326E-08, 	1.43994765920326E-08, 	2.88047106717069E-08, 	2.88047106717069E-08, 	1.43994765920326E-08, 	5.76324512939808E-08, 	5.76324512939808E-08, 	5.76324512939808E-08, 	1.43994765920326E-08, 	2.88047106717069E-08, 	4.32041872637395E-08, 	1.43994765920326E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	2.88047106717069E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	1.43994765920326E-08, 	2.88047106717069E-08, 	1.43994765920326E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	1.43994765920326E-08, 	4.32041872637395E-08, 	0, 	0, 	1.43994765920326E-08, 	5.76324512939808E-08, 	1.43994765920326E-08, 	0, 	0, 	2.88047106717069E-08, 	4.32041872637395E-08, 	1.43994765920326E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	2.88047106717069E-08, 	4.32041872637395E-08, 	4.32041872637395E-08, 	0, 	4.32041872637395E-08, 	4.32041872637395E-08, 	0, 	2.88047106717069E-08, 	5.76324512939808E-08, 	1.15207327711544E-07, 	1.00813608607153E-07, 	7.20261703983716E-08, 	1.15207327711544E-07, 	1.29601046815935E-07, 	1.43994765920326E-07, 	2.16020936318697E-07, 	2.30414655423088E-07, 	2.73595812736261E-07, 	3.16834544925851E-07, 	3.74409421343414E-07, 	4.17648153533004E-07, 	5.32855481244548E-07, 	6.48293108461762E-07, 	7.9223029950567E-07, 	9.79348647862751E-07, 	1.12328583890666E-06, 	1.32479790636813E-06, 	1.64145972666473E-06, 	1.92990985751672E-06, 	2.26096539691771E-06, 	2.66398953184065E-06, 	3.15395173015411E-06, 	3.74409421343414E-06, 	4.3923873218959E-06, 	5.22779877871474E-06, 	6.0511195114859E-06, 	7.13352718813609E-06, 	8.33684210526316E-06, 	9.76469904041873E-06, 	1.13595231171852E-05, 	1.31501017737714E-05, 	1.52803722012213E-05, 	1.74567025298052E-05, 	0.000019540913056121, 	2.20051177667927E-05, 	2.46362896190753E-05, 	2.73020063972085E-05, 	2.96683338179703E-05, 	3.23340505961035E-05, 	3.55697586507706E-05, 	3.90990985751672E-05, 	4.24845013085199E-05, 	4.56511195114859E-05, 	4.93934864786275E-05, 	5.30149462052922E-05, 	5.56633905205002E-05, 	5.73042744984007E-05, 	5.69703402151788E-05, 	5.41088688572259E-05, 	0.00004848380343123, 	4.10739168362896E-05, 	3.27773771445188E-05, 	2.53559755742949E-05, 	1.90112241930794E-05, 	1.40712997964525E-05, 	1.01389357371329E-05, 	7.3292817679558E-06, 	5.37173596975865E-06, 	4.00375690607735E-06, 	2.92365222448386E-06, 	2.21778423960454E-06, 	1.72839779005525E-06, 	1.29601046815935E-06, 	1.0657109624891E-06, 	8.64198895027624E-07, 	6.77080546670544E-07, 	4.89616749054958E-07, 	4.17648153533004E-07, 	3.60015702239023E-07, 	2.44808374527479E-07, 	2.44808374527479E-07, 	2.30414655423088E-07, 	2.16020936318697E-07, 	2.01627217214306E-07, 	1.72839779005525E-07, 	1.58388485024717E-07, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0};
    double blueArray[] = {3.34229684351914E-07, 	1.67152003581822E-07, 	1.48571300649205E-07, 	1.48571300649205E-07, 	1.29990597716588E-07, 	1.29990597716588E-07, 	1.48571300649205E-07, 	1.48571300649205E-07, 	1.67152003581822E-07, 	1.67152003581822E-07, 	1.48571300649205E-07, 	1.48571300649205E-07, 	1.67152003581822E-07, 	2.22819789567943E-07, 	2.22819789567943E-07, 	2.59981195433177E-07, 	2.78561898365794E-07, 	2.97142601298411E-07, 	3.52810387284531E-07, 	4.27133199014999E-07, 	4.64294604880233E-07, 	5.3854309379897E-07, 	6.12791582717708E-07, 	6.87114394448175E-07, 	8.16807700917842E-07, 	9.46872621446161E-07, 	1.03977613610925E-06, 	1.18842175957018E-06, 	1.33706738303112E-06, 	1.5414551152899E-06, 	1.83874636221178E-06, 	2.24677859861204E-06, 	2.89710320125364E-06, 	3.76965301096933E-06, 	5.10672039400045E-06, 	7.11269308260577E-06, 	9.87750167897918E-06, 	1.36828296395791E-05, 	1.87813745242892E-05, 	2.51954331766286E-05, 	3.29844638459816E-05, 	4.21558988135214E-05, 	0.000052256368927692, 	6.22825162301321E-05, 	7.03985672710992E-05, 	7.38174166107007E-05, 	7.11492276695769E-05, 	6.27284531005149E-05, 	5.13421983434072E-05, 	4.04687709872398E-05, 	3.18101634206402E-05, 	2.55373181105888E-05, 	2.08698455339154E-05, 	1.71239758226998E-05, 	1.38686366689053E-05, 	1.10518021043206E-05, 	8.62887844190732E-06, 	6.75965972688605E-06, 	5.31110812625923E-06, 	4.27133199014999E-06, 	3.49094246698008E-06, 	2.84136109245579E-06, 	2.32110141034251E-06, 	1.85732706514439E-06, 	1.48571300649205E-06, 	1.24416386836803E-06, 	9.84034027311395E-07, 	8.16807700917842E-07, 	6.68533691515559E-07, 	5.3854309379897E-07, 	5.01381687933736E-07, 	4.27133199014999E-07, 	3.52810387284531E-07, 	2.97142601298411E-07, 	2.78561898365794E-07, 	2.22819789567943E-07, 	2.22819789567943E-07, 	2.04239086635326E-07, 	1.48571300649205E-07, 	1.48571300649205E-07, 	1.48571300649205E-07, 	1.48571300649205E-07, 	1.29990597716588E-07, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	5.57123796731587E-08, 	1.85732706514439E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	5.57123796731587E-08, 	3.71391090217148E-08, 	1.85732706514439E-08, 	5.57123796731587E-08, 	1.85732706514439E-08, 	7.42782180434296E-08, 	7.42782180434296E-08, 	5.57123796731587E-08, 	3.71391090217148E-08, 	5.57123796731587E-08, 	7.42782180434296E-08, 	5.57123796731587E-08, 	3.71391090217148E-08, 	5.57123796731587E-08, 	5.57123796731587E-08, 	0, 	1.85732706514439E-08, 	3.71391090217148E-08, 	1.85732706514439E-08, 	0, 	0, 	3.71391090217148E-08, 	0, 	0, 	3.71391090217148E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	3.71391090217148E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0};
    double whiteArray[] = {1.36533333333333E-07, 	0.000000077, 	7.23333333333333E-08, 	7.75333333333333E-08, 	0.0000000774, 	7.06666666666667E-08, 	0.0000000804, 	0.0000000754, 	7.54666666666667E-08, 	0.0000000722, 	0.0000000658, 	0.00000006446, 	6.02333333333333E-08, 	0.0000000678, 	6.62333333333333E-08, 	6.89333333333333E-08, 	7.43333333333333E-08, 	0.0000000884, 	9.47333333333333E-08, 	1.11933333333333E-07, 	1.28533333333333E-07, 	0.0000001546, 	1.94733333333333E-07, 	2.37866666666667E-07, 	3.00466666666667E-07, 	3.87666666666667E-07, 	0.0000004888, 	6.38666666666667E-07, 	8.32666666666667E-07, 	0.000001096, 	1.41333333333333E-06, 	1.81733333333333E-06, 	2.32866666666667E-06, 	2.97733333333333E-06, 	3.82266666666667E-06, 	0.000004802, 	6.12133333333333E-06, 	7.68666666666667E-06, 	9.79333333333333E-06, 	1.21733333333333E-05, 	0.00001486, 	1.72266666666667E-05, 	1.87733333333333E-05, 	0.00001908, 	1.81533333333333E-05, 	1.62533333333333E-05, 	1.41333333333333E-05, 	1.21866666666667E-05, 	1.08133333333333E-05, 	0.0000098, 	0.00000896, 	8.15333333333333E-06, 	7.35333333333333E-06, 	0.000006572, 	5.97733333333333E-06, 	5.55666666666667E-06, 	5.35333333333333E-06, 	0.000005274, 	5.29466666666667E-06, 	5.36733333333333E-06, 	5.48133333333333E-06, 	5.65466666666667E-06, 	0.000005826, 	6.06333333333333E-06, 	6.33066666666667E-06, 	6.58066666666667E-06, 	6.91333333333333E-06, 	7.15333333333333E-06, 	7.43333333333333E-06, 	0.00000768, 	0.00000796, 	8.18666666666667E-06, 	8.41333333333333E-06, 	0.00000862, 	8.84666666666667E-06, 	9.08666666666667E-06, 	0.00000926, 	9.47333333333333E-06, 	9.65333333333333E-06, 	0.00000984, 	0.00001, 	1.01733333333333E-05, 	1.03266666666667E-05, 	1.04733333333333E-05, 	1.05866666666667E-05, 	1.07066666666667E-05, 	0.00001084, 	1.09066666666667E-05, 	0.00001098, 	1.10466666666667E-05, 	1.10533333333333E-05, 	0.00001108, 	1.10866666666667E-05, 	1.10933333333333E-05, 	1.10466666666667E-05, 	1.09933333333333E-05, 	0.00001096, 	1.09133333333333E-05, 	1.08266666666667E-05, 	0.00001076, 	0.00001066, 	1.05666666666667E-05, 	1.04666666666667E-05, 	1.03733333333333E-05, 	1.02933333333333E-05, 	1.01866666666667E-05, 	1.00933333333333E-05, 	9.99333333333333E-06, 	0.00000992, 	9.84666666666667E-06, 	0.0000098, 	9.75333333333333E-06, 	9.68666666666667E-06, 	9.66666666666667E-06, 	9.65333333333333E-06, 	0.00000964, 	9.63333333333333E-06, 	0.00000962, 	9.65333333333333E-06, 	0.00000966, 	0.00000968, 	9.72666666666667E-06, 	9.76666666666667E-06, 	9.81333333333333E-06, 	9.89333333333333E-06, 	0.00000992, 	0.00000998, 	1.00133333333333E-05, 	1.00666666666667E-05, 	0.00001006, 	1.00933333333333E-05, 	1.01066666666667E-05, 	1.01066666666667E-05, 	0.0000101, 	1.00866666666667E-05, 	0.00001006, 	0.00001, 	9.93333333333333E-06, 	0.00000982, 	9.74666666666667E-06, 	9.63333333333333E-06, 	9.50666666666667E-06, 	9.36666666666667E-06, 	9.21333333333333E-06, 	9.06666666666667E-06, 	0.00000888, 	0.0000087, 	0.0000085, 	0.00000828, 	8.11333333333333E-06, 	7.86666666666667E-06, 	7.63333333333333E-06, 	7.42666666666667E-06, 	7.18666666666667E-06, 	6.97333333333333E-06, 	6.72666666666667E-06, 	6.50866666666667E-06, 	6.26333333333333E-06, 	6.02466666666667E-06, 	5.77266666666667E-06, 	5.56933333333333E-06, 	5.36533333333333E-06, 	0.000005136, 	0.000004914, 	4.70933333333333E-06, 	4.50266666666667E-06, 	4.29533333333333E-06, 	0.00000409, 	3.90933333333333E-06, 	3.72533333333333E-06, 	3.53266666666667E-06, 	0.000003364, 	3.20066666666667E-06, 	0.000003032, 	0.00000287, 	2.72533333333333E-06, 	2.58466666666667E-06, 	2.45333333333333E-06, 	2.31933333333333E-06, 	2.19266666666667E-06, 	2.07266666666667E-06, 	1.97066666666667E-06, 	1.85933333333333E-06, 	0.000001758, 	1.64133333333333E-06, 	1.56066666666667E-06, 	0.000001468, 	0.000001394, 	1.31933333333333E-06, 	1.23466666666667E-06, 	1.15933333333333E-06, 	1.08733333333333E-06, 	1.02733333333333E-06, 	0.000000956, 	0.0000009, 	0.000000844, 	8.00666666666667E-07, 	7.53333333333333E-07, 	7.04666666666667E-07, 	0.0000006644, 	6.24866666666667E-07, 	5.89533333333333E-07, 	5.50133333333333E-07, 	5.19533333333333E-07, 	4.87866666666667E-07, 	4.59133333333333E-07, 	4.34133333333333E-07, 	4.06733333333333E-07, 	0.0000003832, 	3.59666666666667E-07, 	0.000000341, 	3.16266666666667E-07, 	0.0000003028, 	0.0000002814, 	0.0000002668, 	0.0000002514, 	2.33466666666667E-07, 	2.24933333333333E-07, 	2.09333333333333E-07, 	1.97933333333333E-07, 	1.87666666666667E-07, 	1.75666666666667E-07, 	1.65466666666667E-07, 	1.60066666666667E-07, 	0.0000001486, 	1.42466666666667E-07, 	1.38066666666667E-07, 	0.000000125, 	0.0000001208, 	1.13533333333333E-07, 	1.07266666666667E-07, 	0.000000105, 	9.14666666666667E-08, 	0.000000092, 	8.96666666666667E-08, 	8.10666666666667E-08, 	0.000000105, 	9.14666666666667E-08, 	0.000000092, 	8.96666666666667E-08, 	8.10666666666667E-08};
    double purpleArray[] = {5.77333333333333E-08, 	6.45733333333333E-08, 	0.00000006352, 	0.00000004708, 	5.86133333333333E-08, 	0.00000007476, 	6.02533333333333E-08, 	5.35333333333333E-08, 	5.12933333333333E-08, 	3.93866666666667E-08, 	5.27066666666667E-08, 	5.56666666666667E-08, 	0.00000005032, 	4.74266666666667E-08, 	5.44133333333333E-08, 	0.00000004884, 	5.98933333333333E-08, 	5.78533333333333E-08, 	0.00000006724, 	8.13466666666667E-08, 	8.10133333333333E-08, 	8.48533333333333E-08, 	9.08133333333333E-08, 	0.00000012244, 	1.49066666666667E-07, 	1.91866666666667E-07, 	0.0000002492, 	3.17466666666667E-07, 	4.24533333333333E-07, 	5.74666666666667E-07, 	7.60266666666667E-07, 	0.000001022, 	1.37066666666667E-06, 	0.000001824, 	0.000002452, 	3.12266666666667E-06, 	4.10266666666667E-06, 	0.00000518, 	6.64933333333333E-06, 	8.32133333333333E-06, 	1.04213333333333E-05, 	1.28973333333333E-05, 	1.54533333333333E-05, 	0.000018, 	1.95733333333333E-05, 	0.00002012, 	1.91733333333333E-05, 	0.00001704, 	0.00001452, 	0.000011936, 	9.99466666666667E-06, 	0.00000836, 	7.15733333333333E-06, 	6.05066666666667E-06, 	5.10933333333333E-06, 	4.17333333333333E-06, 	3.34933333333333E-06, 	2.68533333333333E-06, 	0.00000218, 	1.81066666666667E-06, 	0.000001496, 	0.000001268, 	1.05133333333333E-06, 	0.0000008764, 	7.26933333333333E-07, 	6.09066666666667E-07, 	5.01066666666667E-07, 	0.0000004188, 	0.000000344, 	3.06533333333333E-07, 	0.0000002676, 	0.000000238, 	0.0000001996, 	1.82666666666667E-07, 	0.0000001596, 	1.38533333333333E-07, 	1.37733333333333E-07, 	0.00000012368, 	1.15586666666667E-07, 	1.06453333333333E-07, 	0.00000010364, 	0.00000010428, 	9.50666666666667E-08, 	1.00213333333333E-07, 	9.92666666666667E-08, 	9.45733333333333E-08, 	9.24266666666667E-08, 	0.00000009552, 	1.00706666666667E-07, 	1.03106666666667E-07, 	0.00000011224, 	0.00000012924, 	1.46133333333333E-07, 	1.60266666666667E-07, 	0.0000001832, 	0.0000002128, 	2.54933333333333E-07, 	0.0000003012, 	3.72133333333333E-07, 	4.51733333333333E-07, 	0.0000005452, 	6.82933333333333E-07, 	8.39733333333333E-07, 	1.01826666666667E-06, 	0.0000012468, 	0.000001552, 	1.87466666666667E-06, 	2.28933333333333E-06, 	2.77866666666667E-06, 	3.34933333333333E-06, 	0.000004004, 	4.78933333333333E-06, 	5.66266666666667E-06, 	0.000006652, 	7.78266666666667E-06, 	9.03733333333333E-06, 	1.03733333333333E-05, 	0.000011884, 	1.34666666666667E-05, 	1.51066666666667E-05, 	1.68133333333333E-05, 	1.86266666666667E-05, 	2.04666666666667E-05, 	0.0000224, 	0.00002412, 	2.59866666666667E-05, 	0.00002768, 	2.92933333333333E-05, 	3.08666666666667E-05, 	3.23066666666667E-05, 	0.0000336, 	0.00003464, 	3.57333333333333E-05, 	0.00003652, 	3.72666666666667E-05, 	0.00003788, 	3.82533333333333E-05, 	3.85466666666667E-05, 	3.86533333333333E-05, 	3.86266666666667E-05, 	3.85866666666667E-05, 	0.00003828, 	3.80133333333333E-05, 	0.00003748, 	3.69466666666667E-05, 	3.64266666666667E-05, 	0.00003576, 	3.48933333333333E-05, 	3.40133333333333E-05, 	3.32266666666667E-05, 	3.22133333333333E-05, 	0.00003136, 	3.03333333333333E-05, 	0.00002932, 	0.00002832, 	2.73466666666667E-05, 	2.62933333333333E-05, 	2.52933333333333E-05, 	0.00002432, 	2.33333333333333E-05, 	0.0000224, 	2.14266666666667E-05, 	2.04666666666667E-05, 	1.95733333333333E-05, 	1.86266666666667E-05, 	1.77733333333333E-05, 	1.68666666666667E-05, 	1.60533333333333E-05, 	1.52666666666667E-05, 	0.00001448, 	1.37333333333333E-05, 	0.000013036, 	0.000012344, 	0.000011664, 	1.11013333333333E-05, 	1.04613333333333E-05, 	9.90933333333333E-06, 	9.34533333333333E-06, 	8.86533333333333E-06, 	8.28533333333333E-06, 	7.85333333333333E-06, 	0.000007384, 	0.00000698, 	6.55733333333333E-06, 	6.17733333333333E-06, 	0.000005844, 	5.44266666666667E-06, 	0.00000516, 	4.86133333333333E-06, 	0.00000452, 	4.28133333333333E-06, 	4.01866666666667E-06, 	3.76933333333333E-06, 	3.53733333333333E-06, 	3.31466666666667E-06, 	0.000003104, 	0.00000292, 	0.000002732, 	2.60133333333333E-06, 	2.42933333333333E-06, 	0.000002292, 	2.14133333333333E-06, 	2.00933333333333E-06, 	1.89333333333333E-06, 	1.78266666666667E-06, 	0.000001664, 	0.00000156, 	0.000001472, 	1.38266666666667E-06, 	0.000001308, 	1.22493333333333E-06, 	0.000001158, 	0.0000010812, 	0.0000010024, 	9.48933333333333E-07, 	8.78933333333333E-07, 	8.32666666666667E-07, 	7.86666666666667E-07, 	7.37866666666667E-07, 	6.90266666666667E-07, 	6.51866666666667E-07, 	6.28266666666667E-07, 	6.02266666666667E-07, 	0.000000558, 	5.34933333333333E-07, 	5.06533333333333E-07, 	0.0000004908, 	4.55066666666667E-07, 	0.0000004188, 	0.0000003984, 	3.78933333333333E-07, 	3.62533333333333E-07, 	3.35866666666667E-07, 	3.27466666666667E-07, 	3.16666666666667E-07, 	0.0000002872, 	0.0000005438, 	0.0000005038, 	0.0000004912, 	0.000000475, 	0.0000004308};

    int bluei = 0, redi = 0, whitei =0 , red2i = 0, purplei = 0;

    // PPF calculation
    double PPF = 0, BPF = 0;
    double PPFconst = 8.359 * 0.001;
    int PPFrangemin = 20, PPFrangemax = 170;
    int BPFrangemin = 0, BPFrangemax = 235;
    double bluePPF, greenPPF, redPPF;
    int bluePPFrangemin = 20, getBluePPFrangemax = 70, redPPFrangemin = 120, redPPFrangemax = 170, greenPPFrangemin = 71, greenPPFrangemax = 119;

    GraphView graph;
    LineGraphSeries<DataPoint> series;       //an Object of the PointsGraphSeries for plotting scatter graphs


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    // update lights
                    int j = 170; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    j = 187; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    j = 204; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    j = 221; //0xDD
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    j = 238; //0xEE
                    mBluetoothConnection.write(j);
                    mBluetoothConnection.write(0);
                    handler.post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Horticulture.this, Horticulture_recipe.class);
                        intent.putExtra("btdevice", bluetoothDevice); // maintain BT connection
                        startActivity(intent);
                    }
                });
                    mBluetoothConnection.cancel();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_horticulture);
//        getSupportActionBar().setTitle("Create your own recipes");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);
        handler= new Handler(Looper.getMainLooper());
//        maintain connection on this Activity
        bluetoothDevice = getIntent().getExtras().getParcelable("btdevice");
        mBluetoothConnection = new BluetoothConnectionService(Horticulture.this);
        mBluetoothConnection.startClient(bluetoothDevice, MY_UUID_INSECURE);
//        btnPurple1 = findViewById(R.id.btn1);
//        btnPurple2 = findViewById(R.id.btn2);
//        btnWhite = findViewById(R.id.btn3);
//        btnReset = findViewById(R.id.btnReset);
//        btnPurple1.setOnClickListener(this);
//        btnPurple2.setOnClickListener(this);
//        btnWhite.setOnClickListener(this);
//        btnReset.setOnClickListener(this);
//        tvReset = findViewById(R.id.tv_highest_rate);
//        tvReset.setOnClickListener(this);
        tvPPF = findViewById(R.id.tvPPF);
        tvBPF = findViewById(R.id.tvBPF);
        blueSeek = findViewById(R.id.blueSeek);
        tvBlue = findViewById(R.id.tvBlue);
        blueSeek.setMax(255);
        blueSeek.setProgress(0);
        tvBlue.setText("0");
        limeSeek = findViewById(R.id.limeSeek);
        tvLime = findViewById(R.id.tvLime);
        limeSeek.setMax(255);
        limeSeek.setProgress(0);
        tvLime.setText("0");
        redSeek = findViewById(R.id.redSeek);
        tvRed = findViewById(R.id.tvRed);
        redSeek.setMax(255);
        redSeek.setProgress(0);
        tvRed.setText("0");
        farredSeek = findViewById(R.id.farredSeek);
        tvFarRed = findViewById(R.id.tvFarRed);
        farredSeek.setMax(255);
        farredSeek.setProgress(0);
        tvFarRed.setText("0");
        purpleSeek = findViewById(R.id.purpleSeek);
        tvPurple = findViewById(R.id.tvPurple);
        purpleSeek.setMax(255);
        purpleSeek.setProgress(0);
        tvPurple.setText("0");

        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);
        // zero all channels
//        int j = 170; //0xDD
//        mBluetoothConnection.write(j);
//        mBluetoothConnection.write(0);
//        j = 187; //0xDD
//        mBluetoothConnection.write(j);
//        mBluetoothConnection.write(0);
//        j = 204; //0xDD
//        mBluetoothConnection.write(j);
//        mBluetoothConnection.write(0);
//        j = 221; //0xDD
//        mBluetoothConnection.write(j);
//        mBluetoothConnection.write(0);
//        j = 238; //0xEE
//        mBluetoothConnection.write(j);
//        mBluetoothConnection.write(0);


        blueSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        bluei = i;
                        tvBlue.setText(String.valueOf(i));
                    }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph fragment = (Horti_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                fragment.updateGraph(0, bluei);

                int j = 187; // 0xBB
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(bluei);
                Log.d(TAG, "write: Writing to outputstream: Blue");
                try {
                    //set time in milix
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        limeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                whitei = i;
                tvLime.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph fragment = (Horti_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                fragment.updateGraph(1, whitei);
                int j = 204; // 0xCC
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(whitei);
                Log.d(TAG, "write: Writing to outputstream: Lime" );
                try {
                    //set time in mili
                    Thread.sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        redSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                redi = i;
                tvRed.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph fragment = (Horti_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                fragment.updateGraph(2, redi);
                int j = 170; // 0xAA
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(redi);
                Log.d(TAG, "write: Writing to outputstream: Red" );
                try {
                    //set time in mili
                    Thread.sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        farredSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                red2i = i;
                tvFarRed.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph fragment = (Horti_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                fragment.updateGraph(3, red2i);
                int j = 221; //0xDD
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(red2i);
                Log.d(TAG, "write: Writing to outputstream: Far Red" );
                try {
                    //set time in mili
                    Thread.sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        purpleSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                purplei = i;
                tvPurple.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph fragment = (Horti_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                fragment.updateGraph(4, purplei);
                int j = 238; //0xEE
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(purplei);
                Log.d(TAG, "write: Writing to outputstream: Purple" );
                try {
                    //set time in mili
                    Thread.sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
//

    }

//public DataPoint[] data(){
//    int n = 235;
//    DataPoint[] values = new DataPoint[n];     //creating an object of type DataPoint[] of size 'n'
//    for(int i = 0; i < n; i++){
//        DataPoint v = new DataPoint(waveArray[i], graphArray[i]);
//        values[i] = v;
//    }
//    return values;
//}

public void updateGraph() {
    handler.post(new Runnable() {
        public void run() {
            for (int z = 0; z < 235; z++) {
                graphArray[z] = (blueArray[z] * bluei) + (whiteArray[z] * whitei) + (redArray[z] * redi) + (red2Array[z] * red2i) + (purpleArray[z] * purplei);
            }
            for (int y = bluePPFrangemin; y < getBluePPFrangemax; y++){
                bluePPF = (PPFconst * graphArray[y] * waveArray [y]) + bluePPF;
            }
            for (int y = greenPPFrangemin; y < greenPPFrangemax; y++){
                greenPPF = (PPFconst * graphArray[y] * waveArray [y]) + greenPPF;
            }
            for (int y = redPPFrangemin; y < redPPFrangemax; y++){
                redPPF = (PPFconst * graphArray[y] * waveArray [y]) + redPPF;
            }
            bluePPF = bluePPF * 100;
            greenPPF = greenPPF * 100;
            redPPF = redPPF * 100;
            Horti_FragmentPie fragment1 = (Horti_FragmentPie) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":1");
            fragment1.updatePie((int)bluePPF, (int)greenPPF, (int)redPPF);

            for(int x = PPFrangemin; x <= PPFrangemax;  x++) {
                PPF = (PPFconst * graphArray[x] * waveArray [x]) + PPF;
            }
            for(int x = BPFrangemin; x <= BPFrangemax;  x++) {
                BPF = (PPFconst * graphArray[x] * waveArray [x]) + BPF;
            }
            PPF = (PPF/255) * 120; // scale to max current
            BPF = (BPF/255) * 120;
            tvPPF.setText(String.format("%.2f", PPF));
            tvBPF.setText(String.format("%.2f", BPF));
            PPF = 0;
            BPF = 0;
            bluePPF = 0;
            redPPF = 0;
            greenPPF = 0;
        }
    });
}

    @Override
    public void onClick(View view) { // reset all
        handler.post(new Runnable() {
            public void run() {
                PPF = 0;
                BPF = 0;
                bluePPF = 0;
                redPPF = 0;
                greenPPF = 0;
                tvPPF.setText(String.format("%.2f", PPF));
                tvBPF.setText(String.format("%.2f", BPF));
                Horti_FragmentPie fragment1 = (Horti_FragmentPie) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":1");
                fragment1.updatePie(0, 0, 0);
                Horti_FragmentGraph fragment = (Horti_FragmentGraph) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":0");
                fragment.updateGraph(0, 0);
                fragment.updateGraph(1, 0);
                fragment.updateGraph(2, 0);
                fragment.updateGraph(3, 0);
                fragment.updateGraph(4, 0);
                redSeek.setProgress(0);
                blueSeek.setProgress(0);
                limeSeek.setProgress(0);
                farredSeek.setProgress(0);
                purpleSeek.setProgress(0);
                int j = 170; //0xDD
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(0);
                j = 187; //0xDD
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(0);
                j = 204; //0xDD
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(0);
                j = 221; //0xDD
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(0);
                j = 238; //0xEE
                mBluetoothConnection.write(j);
                mBluetoothConnection.write(0);
            }
        });
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        // other code in your custom FragmentPagerAdapter...
        private int NUM_ITEMS = 2;

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
                    return new Horti_FragmentGraph();
                case 1:
                    return new Horti_FragmentPie();
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
                    mFragmentGraph = (Horti_FragmentGraph) createdFragment;
                    break;
                case 1:
                    mFragmentPie = (Horti_FragmentPie) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }
}



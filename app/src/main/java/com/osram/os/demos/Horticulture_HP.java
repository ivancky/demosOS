package com.osram.os.demos;

import android.bluetooth.BluetoothAdapter;
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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.UUID;

public class Horticulture_HP extends AppCompatActivity implements View.OnClickListener {
    FragmentPagerAdapter adapterViewPager;
    private Horti_FragmentGraph_HP mFragmentGraph;
    private Horti_FragmentPie mFragmentPie;

    Handler handler;
    BluetoothConnectionService mBluetoothConnection;
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    public static final String TAG = "Horticulture";
    private SeekBar blueSeek, limeSeek, redSeek, farredSeek;
    private TextView tvPPF, tvBPF, tvReset;
    private Button btnPurple1, btnPurple2, btnWhite, btnReset;
    BluetoothDevice bluetoothDevice;

//    private LineGraphSeries<DataPoint> hSeries;
//    private int hgraphLastXValue = 0;
    double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};
    double redArray[] = {2.87062058342679E-08, 	1.43502317223115E-08, 	2.87062058342679E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	2.87062058342679E-08, 	5.7406669278891E-08, 	2.87062058342679E-08, 	2.87062058342679E-08, 	7.17798705597816E-08, 	2.87062058342679E-08, 	4.30564375565794E-08, 	2.87062058342679E-08, 	0, 	2.87062058342679E-08, 	2.87062058342679E-08, 	0, 	0, 	5.7406669278891E-08, 	5.7406669278891E-08, 	2.87062058342679E-08, 	4.30564375565794E-08, 	4.30564375565794E-08, 	2.87062058342679E-08, 	0, 	0, 	2.87062058342679E-08, 	0, 	0, 	0, 	0, 	0, 	2.87062058342679E-08, 	0, 	0, 	0, 	2.87062058342679E-08, 	0, 	2.87062058342679E-08, 	0, 	2.87062058342679E-08, 	0, 	0, 	0, 	4.30564375565794E-08, 	2.87062058342679E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	2.87062058342679E-08, 	2.87062058342679E-08, 	1.43502317223115E-08, 	0, 	0, 	0, 	0, 	0, 	2.87062058342679E-08, 	1.43502317223115E-08, 	5.7406669278891E-08, 	1.43502317223115E-08, 	2.87062058342679E-08, 	4.30564375565794E-08, 	4.30564375565794E-08, 	1.43502317223115E-08, 	2.87062058342679E-08, 	5.7406669278891E-08, 	1.43502317223115E-08, 	1.43502317223115E-08, 	1.43502317223115E-08, 	0, 	0, 	0, 	0, 	0, 	2.87062058342679E-08, 	0, 	2.87062058342679E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	2.87062058342679E-08, 	2.87062058342679E-08, 	0, 	1.43502317223115E-08, 	1.43502317223115E-08, 	4.30564375565794E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	2.87062058342679E-08, 	1.43502317223115E-08, 	4.30564375565794E-08, 	4.30564375565794E-08, 	4.30564375565794E-08, 	5.7406669278891E-08, 	8.6135844671738E-08, 	7.17798705597816E-08, 	1.00491818783694E-07, 	1.14790368999203E-07, 	1.72214265447028E-07, 	1.72214265447028E-07, 	2.58350110118766E-07, 	3.01418032454635E-07, 	3.44428530894056E-07, 	4.44920349677751E-07, 	5.7406669278891E-07, 	6.89086757373904E-07, 	8.6135844671738E-07, 	1.00491818783694E-06, 	1.23403953466377E-06, 	1.53551499101485E-06, 	1.90877031792571E-06, 	2.32509356717245E-06, 	2.87062058342679E-06, 	3.44428530894056E-06, 	4.33435570388186E-06, 	5.31056194349489E-06, 	6.73582305332991E-06, 	8.21161719203902E-06, 	1.02386807366473E-05, 	1.27308778424829E-05, 	1.55963302752294E-05, 	1.91738390239289E-05, 	2.31820269959871E-05, 	2.77012876464309E-05, 	3.23411384794152E-05, 	3.76700760697734E-05, 	4.41360068097985E-05, 	5.09349961492211E-05, 	5.60916620502358E-05, 	0.000056665901014714, 	4.98726540649363E-05, 	3.85199497372012E-05, 	2.69662617718988E-05, 	1.79679371985245E-05, 	1.18637770061207E-05, 	7.94172487873424E-06, 	5.55461350339814E-06, 	3.84625258407534E-06, 	2.6696369458594E-06, 	1.8800583697018E-06, 	1.39195524989529E-06, 	1.04798611017281E-06, 	8.18290524381511E-07, 	6.46018835038035E-07, 	5.31056194349489E-07, 	3.87496453229925E-07, 	2.87062058342679E-07, 	2.29638161894853E-07, 	2.29638161894853E-07, 	1.72214265447028E-07, 	1.14790368999203E-07, 	1.14790368999203E-07, 	1.14790368999203E-07, 	1.43502317223115E-07, 	1.14790368999203E-07, 	1.00491818783694E-07, 	8.6135844671738E-08, 	8.6135844671738E-08, 	7.17798705597816E-08, 	8.6135844671738E-08, 	8.6135844671738E-08, 	7.17798705597816E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	4.30564375565794E-08, 	0, 	0, 	4.30564375565794E-08, 	1.43502317223115E-08, 	4.30564375565794E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	4.30564375565794E-08, 	4.30564375565794E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	2.87062058342679E-08, 	4.30564375565794E-08, 	5.7406669278891E-08, 	7.17798705597816E-08, 	5.7406669278891E-08, 	5.7406669278891E-08, 	2.87062058342679E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0};
    double red2Array[] = {0, 	6.54656493970503E-09, 	6.54656493970503E-09, 	1.30905112534342E-08, 	0, 	0, 	1.96370761931392E-08, 	6.54656493970503E-09, 	0, 	0, 	0, 	1.30905112534342E-08, 	0, 	1.30905112534342E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	1.30905112534342E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	1.96370761931392E-08, 	1.30905112534342E-08, 	6.54656493970503E-09, 	1.30905112534342E-08, 	1.96370761931392E-08, 	2.61810225068683E-08, 	2.61810225068683E-08, 	2.61810225068683E-08, 	2.61810225068683E-08, 	2.61810225068683E-08, 	2.61810225068683E-08, 	2.61810225068683E-08, 	3.27328246985251E-08, 	5.23725195176402E-08, 	5.23725195176402E-08, 	7.20122143367553E-08, 	7.85587792764603E-08, 	8.51053442161653E-08, 	1.11265417715227E-07, 	1.37451677474047E-07, 	1.63637937232867E-07, 	1.89824196991687E-07, 	2.35650151569622E-07, 	2.88048857347021E-07, 	3.40421376864661E-07, 	4.12433591201417E-07, 	4.97538935417582E-07, 	5.82644279633747E-07, 	7.07029013488143E-07, 	8.44506877221948E-07, 	9.94816008237576E-07, 	1.1584801317302E-06, 	1.38106333968017E-06, 	1.62328624244926E-06, 	1.89824196991687E-06, 	2.25175647666094E-06, 	2.65004948759259E-06, 	3.11616491129959E-06, 	3.68702537404187E-06, 	4.36786812777119E-06, 	5.13250691272874E-06, 	6.00974661464922E-06, 	6.93935883608733E-06, 	7.93443670692249E-06, 	8.93475182970942E-06, 	1.00214816097005E-05, 	1.11605839092091E-05, 	1.23913381178737E-05, 	1.36220923265382E-05, 	1.50073454677798E-05, 	1.64423525025631E-05, 	1.81523152648141E-05, 	1.97941937516921E-05, 	2.16481809426166E-05, 	0.000023258635917784, 	2.48010066175785E-05, 	2.58536942598831E-05, 	2.59505834209907E-05, 	2.47303037162297E-05, 	2.19886023194812E-05, 	1.82622975558011E-05, 	1.42557998127017E-05, 	1.06551890958639E-05, 	7.77731914836957E-06, 	5.70336737547102E-06, 	4.13742904189358E-06, 	3.02451300214372E-06, 	2.22557021690212E-06, 	1.65601906714778E-06, 	1.23703891100666E-06, 	9.42443488719936E-07, 	7.07029013488143E-07, 	5.43364889995517E-07, 	4.18980156141122E-07, 	3.20781682045546E-07, 	2.61810225068683E-07, 	2.16010456750507E-07, 	1.70184502172572E-07, 	1.50544807353457E-07, 	1.24358547594637E-07, 	9.81722878358166E-08, 	7.85587792764603E-08, 	7.20122143367553E-08, 	5.23725195176402E-08, 	5.23725195176402E-08, 	5.23725195176402E-08, 	5.23725195176402E-08, 	3.27328246985251E-08, 	2.61810225068683E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0};
    double blueArray[] = {0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	2.05456407081543E-08, 	2.05456407081543E-08, 	4.10912814163085E-08, 	1.23306704130106E-07, 	1.43844129867965E-07, 	1.64381555605824E-07, 	1.64381555605824E-07, 	2.25993832819402E-07, 	2.87606110032979E-07, 	3.28680961508697E-07, 	4.10912814163085E-07, 	4.93062517114522E-07, 	5.75212220065958E-07, 	6.57444072720346E-07, 	8.21497029514364E-07, 	9.44721583941519E-07, 	1.12955841558225E-06, 	1.23306704130106E-06, 	1.47951615015537E-06, 	1.68489040753396E-06, 	2.01348921933971E-06, 	2.50638743704833E-06, 	3.26627218934911E-06, 	4.35557725048516E-06, 	5.93695903230031E-06, 	8.11474765754289E-06, 	1.11723596013954E-05, 	1.54359291845749E-05, 	2.10631838367483E-05, 	2.80048337361447E-05, 	3.67044872787018E-05, 	4.69814151179265E-05, 	5.77676711154501E-05, 	6.80445989546748E-05, 	7.66292429130999E-05, 	8.12296262783804E-05, 	8.08188777636232E-05, 	7.50026787946615E-05, 	0.000065251509054326, 	5.37423356708297E-05, 	4.30217994356673E-05, 	3.45685950019645E-05, 	2.80623385282107E-05, 	2.33962354005691E-05, 	1.93955448668341E-05, 	1.59616872834641E-05, 	1.28646434821949E-05, 	1.02276380174538E-05, 	8.05313538032931E-06, 	6.28691676687343E-06, 	5.07438715131023E-06, 	4.17074041884443E-06, 	3.43057159525199E-06, 	2.77337397164049E-06, 	2.23940090245616E-06, 	1.7670401104854E-06, 	1.41790387294179E-06, 	1.21170811853369E-06, 	9.85796435417237E-07, 	8.21497029514364E-07, 	6.57444072720346E-07, 	5.13599942852381E-07, 	4.31450239900944E-07, 	3.49218387246556E-07, 	3.28680961508697E-07, 	2.6706868429512E-07, 	2.46531258557261E-07, 	2.05456407081543E-07, 	1.84918981343683E-07, 	1.43844129867965E-07, 	1.43844129867965E-07, 	1.02687128689296E-07, 	8.21497029514364E-08, 	8.21497029514364E-08, 	8.21497029514364E-08, 	8.21497029514364E-08, 	8.21497029514364E-08, 	8.21497029514364E-08, 	8.21497029514364E-08, 	8.21497029514364E-08, 	4.10912814163085E-08, 	2.05456407081543E-08, 	2.05456407081543E-08, 	6.16369221244628E-08, 	0, 	2.05456407081543E-08, 	2.05456407081543E-08, 	4.10912814163085E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	2.05456407081543E-08, 	4.10912814163085E-08, 	4.10912814163085E-08, 	2.05456407081543E-08, 	0, 	0, 	2.05456407081543E-08, 	2.05456407081543E-08, 	2.05456407081543E-08, 	2.05456407081543E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	2.05456407081543E-08, 	2.05456407081543E-08, 	4.10912814163085E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	4.10912814163085E-08, 	0, 	0, 	0, 	0, 	4.10912814163085E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	4.10912814163085E-08, 	2.05456407081543E-08, 	2.05456407081543E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	8.21497029514364E-08, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0};
    double whiteArray[] = {1.08770227078766E-08, 	1.95786408741779E-08, 	2.17540454157533E-08, 	2.39294499573286E-08, 	2.61048544989039E-08, 	2.61048544989039E-08, 	2.82802590404793E-08, 	2.82802590404793E-08, 	2.61048544989039E-08, 	3.26310681236299E-08, 	3.48064726652052E-08, 	3.69818772067806E-08, 	4.56834953730819E-08, 	5.22097089978079E-08, 	5.43851135393832E-08, 	6.30867317056845E-08, 	6.96129453304105E-08, 	7.83145634967118E-08, 	8.70161816630131E-08, 	9.57177998293144E-08, 	1.08770227078766E-07, 	1.26173463411369E-07, 	1.47927508827122E-07, 	1.65330745159725E-07, 	1.97961813283355E-07, 	2.37119095031711E-07, 	2.87153399487943E-07, 	3.58941749359929E-07, 	4.59010358272394E-07, 	5.98236248933215E-07, 	7.67917803176091E-07, 	1.00764738365769E-06, 	0.000001293930621329, 	1.67941230609615E-06, 	2.14233839254338E-06, 	2.7088137351696E-06, 	3.41712545390652E-06, 	4.28119613782024E-06, 	5.27753141786174E-06, 	6.40874177948092E-06, 	7.50949647751803E-06, 	8.34659214511622E-06, 	8.68247460633545E-06, 	8.41185428136348E-06, 	7.54169246473335E-06, 	6.37393530681571E-06, 	5.24272494519654E-06, 	4.32470422865175E-06, 	3.67208286617915E-06, 	3.21959872153148E-06, 	2.87153399487943E-06, 	2.53478137184357E-06, 	2.21021101424053E-06, 	1.93001890928563E-06, 	1.69855586606202E-06, 	1.52713398818588E-06, 	1.40792181930755E-06, 	1.33569838852725E-06, 	1.31220401947824E-06, 	1.3052427249452E-06, 	1.3139443431115E-06, 	1.36354356665942E-06, 	1.44446861560602E-06, 	1.58195418263358E-06, 	1.74641476597667E-06, 	1.9665657055841E-06, 	2.21630214695694E-06, 	2.49562409009522E-06, 	2.81062266771532E-06, 	3.13258253986847E-06, 	3.47020532472096E-06, 	3.81304908047323E-06, 	4.14458073260931E-06, 	4.48568416472833E-06, 	4.80764403688147E-06, 	5.11829180541843E-06, 	5.41849763215583E-06, 	5.67954617714487E-06, 	5.94755601666695E-06, 	6.19381181077327E-06, 	6.40613129403102E-06, 	6.60887899730585E-06, 	6.75680650613297E-06, 	6.90473401496009E-06, 	7.05005103833732E-06, 	7.18318579628173E-06, 	7.30326812697669E-06, 	7.38332301410666E-06, 	7.45554644488696E-06, 	7.53560133201693E-06, 	7.55996586288258E-06, 	7.6182667045968E-06, 	7.63392961729614E-06, 	0.000007653073177262, 	7.66351511906156E-06, 	7.68091835539417E-06, 	7.6643852808782E-06, 	7.6704764135946E-06, 	7.64002075001255E-06, 	7.6008634682642E-06, 	7.56170618651584E-06, 	7.53125052293378E-06, 	7.47033919576968E-06, 	7.40333673588915E-06, 	7.33981492327516E-06, 	7.27281246339464E-06, 	7.21364145986379E-06, 	7.12488495456751E-06, 	7.03264780200472E-06, 	6.95259291487475E-06, 	6.84382268779598E-06, 	6.71938954801787E-06, 	6.61758061547215E-06, 	6.50881038839338E-06, 	6.38437724861527E-06, 	6.26081427065379E-06, 	6.13203032179253E-06, 	5.9867132984153E-06, 	5.86924145317023E-06, 	5.73871718067571E-06, 	5.60210177546478E-06, 	5.45591459027092E-06, 	5.30189594872739E-06, 	5.16441038169983E-06, 	5.03388610920531E-06, 	4.88769892401145E-06, 	4.74064157700095E-06, 	4.61359795177295E-06, 	4.45957931022942E-06, 	4.3203534195686E-06, 	4.19417995615723E-06, 	0.00000404886293278, 	3.90267574758614E-06, 	3.76780066600847E-06, 	3.64771833531351E-06, 	3.51110293010258E-06, 	3.38231898124132E-06, 	3.26310681236299E-06, 	3.13693334895162E-06, 	3.02381231278971E-06, 	2.90895095299453E-06, 	2.78016700413327E-06, 	2.6888000133871E-06, 	2.5730684917753E-06, 	2.46429826469653E-06, 	2.36684014123396E-06, 	2.26938201777138E-06, 	2.16844324704229E-06, 	2.08838835991231E-06, 	1.99093023644974E-06, 	1.90391405478673E-06, 	1.81428738767382E-06, 	1.74467444234341E-06, 	1.67332117337974E-06, 	1.5941364480664E-06, 	1.52713398818588E-06, 	1.44881942468917E-06, 	1.39051858297495E-06, 	1.3226459612778E-06, 	1.25738382503054E-06, 	1.20256363058284E-06, 	1.14600311250188E-06, 	1.08944259442092E-06, 	1.04419417995616E-06, 	9.91984470958349E-07, 	9.48476380126843E-07, 	9.07578774745227E-07, 	8.6146019846383E-07, 	8.17952107632323E-07, 	7.78794825883967E-07, 	7.46163757760337E-07, 	7.11357285095132E-07, 	6.74375407888352E-07, 	6.41744339764722E-07, 	6.04762462557941E-07, 	5.74306798975887E-07, 	5.54728158101709E-07, 	5.26447899061229E-07, 	5.09044662728627E-07, 	4.74238190063421E-07, 	4.59010358272394E-07, 	4.30730099231915E-07, 	4.06800649274586E-07, 	3.87222008400408E-07, 	3.71994176609381E-07, 	3.50240131193628E-07, 	3.39363108485751E-07, 	3.21959872153148E-07, 	3.00205826737395E-07, 	2.91504208571094E-07, 	2.71925567696916E-07, 	2.61048544989039E-07, 	2.41469904114861E-07, 	2.34943690490135E-07, 	2.26242072323834E-07, 	2.17540454157533E-07, 	2.0013721782493E-07, 	1.91435599658629E-07, 	1.78383172409177E-07, 	1.71856958784451E-07, 	1.6315534061815E-07, 	1.56629126993424E-07, 	1.47927508827122E-07, 	1.39225890660821E-07, 	1.37050486119246E-07, 	1.23998058869794E-07, 	1.21822654328218E-07, 	1.15296440703492E-07, 	1.13121036161917E-07, 	1.06594822537191E-07, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0, 	0};

    int bluei = 0, redi = 0, whitei =0 , red2i = 0;

    // PPF calculation
    double PPF = 0, BPF = 0;
    double PPFconst = 8.359 * 0.001;
    int PPFrangemin = 20, PPFrangemax = 170;
    int BPFrangemin = 0, BPFrangemax = 235;
    double bluePPF, greenPPF, redPPF, red2PPF;
    int bluePPFrangemin = 20, getBluePPFrangemax = 70, redPPFrangemin = 121, redPPFrangemax = 170, greenPPFrangemin = 71, greenPPFrangemax = 120, red2PPFrangemin = 171, red2PPFrangemax = 234;
    int maxcurrent = 100; //100mA

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
                    handler.post(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Horticulture_HP.this, Horticulture_recipe_HP.class);
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
        setContentView(R.layout.activity_horticulture_hp);
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
        mBluetoothConnection = new BluetoothConnectionService(Horticulture_HP.this);
        mBluetoothConnection.startClient(bluetoothDevice, MY_UUID_INSECURE);

        tvPPF = findViewById(R.id.tvPPF);
        tvBPF = findViewById(R.id.tvBPF);
        blueSeek = findViewById(R.id.blueSeek);
        blueSeek.setMax(255);
        blueSeek.setProgress(0);
        limeSeek = findViewById(R.id.limeSeek);
        limeSeek.setMax(255);
        limeSeek.setProgress(0);
        redSeek = findViewById(R.id.redSeek);
        redSeek.setMax(255);
        redSeek.setProgress(0);
        farredSeek = findViewById(R.id.farredSeek);
        farredSeek.setMax(255);
        farredSeek.setProgress(0);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        blueSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        bluei = i;
//                        tvBlue.setText(String.valueOf(i));
                    }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph_HP fragment = (Horti_FragmentGraph_HP) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
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
//                tvLime.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph_HP fragment = (Horti_FragmentGraph_HP) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
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
//                tvRed.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph_HP fragment = (Horti_FragmentGraph_HP) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
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
//                tvFarRed.setText(String.valueOf(i));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                updateGraph();
                Horti_FragmentGraph_HP fragment = (Horti_FragmentGraph_HP) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
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

    }

public void updateGraph() {
    handler.post(new Runnable() {
        public void run() {
            for (int z = 0; z < 235; z++) {
                graphArray[z] = (blueArray[z] * bluei) + (whiteArray[z] * whitei) + (redArray[z] * redi) + (red2Array[z] * red2i);
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
            for (int y = red2PPFrangemin; y < red2PPFrangemax; y++){
                red2PPF = (PPFconst * graphArray[y] * waveArray [y]) + red2PPF;
            }
            bluePPF = bluePPF * 100;
            greenPPF = greenPPF * 100;
            redPPF = redPPF * 100;
            red2PPF = red2PPF * 100;

            Horti_FragmentPie fragment1 = (Horti_FragmentPie) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":1");
            fragment1.updatePie((int)bluePPF, (int)greenPPF, (int)redPPF, (int)red2PPF);

            for(int x = PPFrangemin; x <= PPFrangemax;  x++) {
                PPF = (PPFconst * graphArray[x] * waveArray [x]) + PPF;
            }
            for(int x = BPFrangemin; x <= BPFrangemax;  x++) {
                BPF = (PPFconst * graphArray[x] * waveArray [x]) + BPF;
            }
            PPF = (PPF/255) * maxcurrent; // scale to max current
            BPF = (BPF/255) * maxcurrent;
            tvPPF.setText(String.format("%.2f", PPF));
            tvBPF.setText(String.format("%.2f", BPF));
            PPF = 0;
            BPF = 0;
            bluePPF = 0;
            redPPF = 0;
            greenPPF = 0;
            red2PPF = 0;
        }
    });
}


    public void onBackPressed(){
        Intent intent = new Intent(Horticulture_HP.this, MainActivity.class);
//        intent.putExtra("btdevice", bluetoothDevice); // maintain BT connection
        startActivity(intent);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
        finish();
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
                fragment1.updatePie(0, 0, 0, 0);
                Horti_FragmentGraph_HP fragment = (Horti_FragmentGraph_HP) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":0");
                fragment.updateGraph(0, 0);
                fragment.updateGraph(1, 0);
                fragment.updateGraph(2, 0);
                fragment.updateGraph(3, 0);
                redSeek.setProgress(0);
                blueSeek.setProgress(0);
                limeSeek.setProgress(0);
                farredSeek.setProgress(0);
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
                    return new Horti_FragmentGraph_HP();
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
                    mFragmentGraph = (Horti_FragmentGraph_HP) createdFragment;
                    break;
                case 1:
                    mFragmentPie = (Horti_FragmentPie) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }
}



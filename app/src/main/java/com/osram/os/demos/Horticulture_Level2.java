package com.osram.os.demos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.neo.arcchartview.ArcChartView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class Horticulture_Level2 extends AppCompatActivity implements View.OnClickListener {
    FragmentPagerAdapter adapterViewPager;
    private Horti_FragmentGraph_Level2 mFragmentGraph;
    private Horti_FragmentPie mFragmentPie;
    private ArcChartView myArcChartView;
    private static OkHttpClient client;
    Handler handler;
    private Button btnReset;

    double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};
    double vegeArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	2.14447401042936E-06,	2.14447401042936E-06,	8.36306728785701E-05,	0.000190803526357704,	0.000352751356190959,	0.000595164630517503,	0.000973593743087047,	0.0013766836711885,	0.00207837285539538,	0.00312963545564736,	0.00436903211253451,	0.00598596805875036,	0.00827789849194784,	0.011215586362785,	0.0150761480519812,	0.0199955993977794,	0.0256523328574182,	0.0326183776571532,	0.0405378045006475,	0.0495504427543192,	0.059452904248833,	0.0700799360966039,	0.0814569618187987,	0.0920585701454027,	0.101490696498329,	0.10679150066163,	0.10638472432296,	0.0991135972692219,	0.0871899658419383,	0.0734231291300533,	0.0608639096735968,	0.0506182306433297,	0.043410662392509,	0.0377412171722867,	0.0331014245593246,	0.0291480670178692,	0.0255506387727505,	0.0224871044721372,	0.0202116993276982,	0.0186735762970998,	0.0177456177745073,	0.0172752826329194,	0.0173642649570036,	0.0179490059438427,	0.0187117115788502,	0.0197667877072772,	0.0211905048926245,	0.0228303220078905,	0.0248006448983265,	0.0271268970850993,	0.0291099317361188,	0.0314234721623081,	0.0336226067432464,	0.0358980118876853,	0.0381225699897905,	0.0400293340773092,	0.0419233864042444,	0.0437284564070955,	0.0454191205646954,	0.0469826671164607,	0.048546213668226,	0.0497284074024876,	0.0509741599396665,	0.0522707595191792,	0.0533004121264393,	0.054291929451949,	0.0552580232562919,	0.056567334596388,	0.0574571578372301,	0.0582198634722376,	0.0591859572765804,	0.0602156098838405,	0.0611817036881833,	0.0619952563655246,	0.0627579620005321,	0.0636350734807907,	0.0643215085522974,	0.0651350612296387,	0.0659231903858131,	0.0666477607390702,	0.06730877228941,	0.0680079191215002,	0.0684782542630882,	0.0689867246864265,	0.069444348067431,	0.0697875656031843,	0.0700672243360204,	0.0702706125053557,	0.0704104418717738,	0.0705375594776083,	0.070334171308273,	0.070194341941855,	0.0700799360966039,	0.0698257008849347,	0.069418924546264,	0.0690248599681768,	0.0684401189813378,	0.0677155486280807,	0.0671053841200747,	0.0661647138368988,	0.0651350612296387,	0.0641562556647124,	0.0629994854516178,	0.061702885872105,	0.060342727489675,	0.059357928277625,	0.0596018272788298,	0.0594199243695431,	0.0600067546172812,	0.0608168072106201,	0.0620804269108429,	0.0638723512106696,	0.0668271912586106,	0.0704623885928257,	0.0759418225901951,	0.0820039628113947,	0.088895541117558,	0.0980945555608845,	0.10909147783569,	0.123671243835389,	0.141971648163392,	0.16524496982675,	0.194192471422247,	0.230964897432344,	0.276364586009418,	0.33331292997475,	0.401569024406325,	0.479654252722872,	0.565801339024128,	0.657002203105216,	0.760407167363263,	0.874742240444475,	0.974800855194462,	1,	0.903946613371293,	0.71453754865302,	0.508868649773338,	0.344123257678793,	0.230028812169688,	0.156340402436224,	0.108352283756896,	0.0766483648777972,	0.0550935792508475,	0.0404355889263226,	0.0306546406914361,	0.0237596241541776,	0.0184147274221608,	0.0146773829120604,	0.0120114132735365,	0.0097339350685353,	0.00825665073212393,	0.00749612521606517,	0.00709062005345286,	0.00671435194018251,	0.00636350734807907,	0.0059592733615251,	0.00563639464270527,	0.00528555005060183,	0.00493470545849839,	0.00463852143690382,	0.00442369268304338,	0.00418089805589933,	0.00388344285824641,	0.00366861410438597,	0.00345251417446719,	0.0032364142445484,	0.00302158549068796,	0.00280548556076918,	0.00261608032807565,	0.00248260684194934,	0.00234659100370634,	0.00218642282035477,	0.0021317622498459,	0.00194362819321073,	0.00180888353102607,	0.00170083356606668,	0.00153812303059842,	0.00148473363614789,	0.00140337836841376,	0.00124270171463885,	0.00116121932929889,	0.00107973694395892,	0.00100041555791814,	0.000892111357747081,	0.000865289542915985,	0.000783807157576019,	0.000756985342744923,	0.000676647015857468,	0.000621859327742764,	0.00054152100085531,	0.000514699186024214,	0.000433216800684248,	0.000433216800684248,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double flowerArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	4.1864941236325E-06,	4.1864941236325E-06,	0.000163265825959563,	0.000372491267313123,	0.000688649744699478,	0.00116189481249836,	0.00190067329537056,	0.00268759521985418,	0.00405744984714828,	0.0061097501673878,	0.00852933035146705,	0.0116859518839274,	0.0161603140089478,	0.021895339450391,	0.0294320215212101,	0.0390358936364785,	0.0500791057586864,	0.0636783872035626,	0.0791388841746536,	0.0967335749491375,	0.116065400214755,	0.136811749280296,	0.159022254559793,	0.179718971211302,	0.19813259681732,	0.208480955143074,	0.207686836518556,	0.193491966105291,	0.170214363924098,	0.143338411725556,	0.118819999193553,	0.0988181363384981,	0.0847473469603142,	0.0736793186310901,	0.0646214030701781,	0.0569035626881407,	0.0498805761025568,	0.0438998702116532,	0.0394577691557539,	0.036455008106794,	0.0346434249946116,	0.0337252253350123,	0.0338989387841257,	0.0350404843068708,	0.0365294567278426,	0.038589201910187,	0.0413686170960011,	0.0445699078010906,	0.0484164198886012,	0.0529577857725653,	0.0568291140670921,	0.06134566374404,	0.0656388675578422,	0.0700809686137415,	0.0744238048415761,	0.0781462358940057,	0.0818438507394191,	0.0853677521357191,	0.0886683076688734,	0.0917207011318656,	0.0947730945948579,	0.0970810018473643,	0.0995129901349516,	0.102044243250604,	0.104054356018916,	0.105990020166179,	0.10787605189941,	0.110432121222078,	0.112169255713212,	0.113658228134184,	0.115544259867415,	0.117554372635727,	0.119440404368958,	0.121028641617995,	0.122517614038967,	0.124229932323084,	0.125570007501959,	0.127158244750996,	0.128696849586,	0.130111373385923,	0.131401816150765,	0.132766707536656,	0.133684907196255,	0.134677555476903,	0.135570938929486,	0.136240976518924,	0.13678693307328,	0.137183992385539,	0.137456970662717,	0.137705132732879,	0.13730807342062,	0.137035095143442,	0.136811749280296,	0.136315425139972,	0.135521306515454,	0.134752004097952,	0.133610458575207,	0.132195934775284,	0.131004756838506,	0.129168357519308,	0.127158244750996,	0.125247396810748,	0.122989121972274,	0.120457868856622,	0.117802534705889,	0.11563767422391,	0.114659752850021,	0.113092487623611,	0.112300408351967,	0.111701402909181,	0.111259743433688,	0.111606746755441,	0.113014437606568,	0.115024192929794,	0.11893339483086,	0.123250077990869,	0.128475650542293,	0.136015579979259,	0.145111939376,	0.158065390940385,	0.174414848685792,	0.195618431288978,	0.222571587426077,	0.257026257944394,	0.299976552594405,	0.354323710715577,	0.419696092956388,	0.494704923402809,	0.578106498701713,	0.666440995112456,	0.766563391165731,	0.877916404278187,	0.97552484016433,	1,	0.906505513508363,	0.722156912513603,	0.522047089944551,	0.362669916785161,	0.252881159068634,	0.18293973201689,	0.138208740453995,	0.110567960244636,	0.0931541403149818,	0.0830206207145898,	0.079177805709121,	0.0788767341220834,	0.0813282677850354,	0.0873036082973718,	0.0964496780451996,	0.107314656574414,	0.121017884074292,	0.136321688636244,	0.153254975756973,	0.170319009830168,	0.189082877067145,	0.208479212997936,	0.229802444885505,	0.250923741764982,	0.27513916505024,	0.300050655436293,	0.329983168184905,	0.358829709809489,	0.391105686207652,	0.419417466077046,	0.446548049504488,	0.464838271772556,	0.466186950105857,	0.443958850393458,	0.394819763179699,	0.32825648431072,	0.256531364857368,	0.192126062243497,	0.140774725699486,	0.103425268318929,	0.0753298128077927,	0.0553017321158902,	0.0407658371347553,	0.0305246616025377,	0.022910466633228,	0.017351508821981,	0.0129903185972511,	0.00991687431473675,	0.00754751071253233,	0.00558863190625127,	0.00448721351853464,	0.00351188161784522,	0.0026434067466781,	0.00213693203448247,	0.00156364513108565,	0.00105717041889001,	0.00100480822208583,	0.000845736335112008,	0.000845736335112008,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double seedingArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	2.10041516399114E-06,	2.10041516399114E-06,	8.19124562175324E-05,	0.000186883412042128,	0.000345503976293741,	0.000582936798921548,	0.000953590974570726,	0.00158005948114182,	0.00319397297187003,	0.00515086033910501,	0.00821729792978054,	0.0125817123338679,	0.0180694085264011,	0.0269702937612617,	0.0388644999195626,	0.0550231600469699,	0.0772439838856716,	0.106320267613227,	0.146466692348447,	0.196150241831222,	0.259404893404652,	0.336029952773571,	0.428147496258663,	0.532904172432162,	0.656127865134641,	0.784625893241303,	0.907727787641268,	0.995561477663995,	1,	0.913891608847995,	0.76100201515497,	0.590377592604184,	0.454186488955358,	0.359503580986106,	0.296898432342437,	0.248946783315423,	0.20647017901652,	0.167701098931053,	0.132111823403013,	0.102390528703564,	0.0808594132173715,	0.0664952468473835,	0.0566208191208577,	0.050241799232114,	0.044503126574224,	0.0399828954093397,	0.0367402746692868,	0.0344083724005532,	0.0333264379195652,	0.0330572171529682,	0.0331450632466354,	0.0342537413264578,	0.0354794998642078,	0.0367820979761095,	0.0382659713434675,	0.0394385798673176,	0.0410620581555589,	0.0428300424666836,	0.0444859714341455,	0.0460173944642042,	0.047548817494263,	0.0487067227121123,	0.0499268808986631,	0.0511968414601753,	0.0522053395531408,	0.0531764858648853,	0.0541227309891493,	0.0554051421444017,	0.0562766837062238,	0.0570237193306427,	0.0579699644549066,	0.0589784625478721,	0.0599247076721361,	0.0607215456715162,	0.0614685812959351,	0.0623276722640169,	0.0630000043259939,	0.0637968423253741,	0.0645687791372736,	0.0652784629804715,	0.0659258938549679,	0.0666106765106852,	0.0670713484790769,	0.0675693722286895,	0.0680175936033408,	0.0683537596343293,	0.0686276726966163,	0.0688268821964613,	0.0689638387276048,	0.0690883446650079,	0.0688891351651629,	0.0687521786340194,	0.0686401232903566,	0.0683911114155503,	0.0679926924158602,	0.0676067240099104,	0.0670339966978559,	0.066324312854658,	0.0657266843551229,	0.0648053404183396,	0.0637968423253741,	0.0628381466073698,	0.0617051425770011,	0.060435182015489,	0.0591029684852753,	0.0579763080154373,	0.0572424985660075,	0.0562534656798123,	0.0555320133550147,	0.0548668389642807,	0.0541588382821519,	0.0538059297838542,	0.0537828933451192,	0.053940481309457,	0.0547665878673845,	0.0556750340641785,	0.0569206824398047,	0.0589611758566041,	0.0614558527030603,	0.0653609854123368,	0.0703231428423893,	0.0769088827033248,	0.0854882633746687,	0.0965310593757144,	0.110440328728891,	0.128203063667638,	0.149649308017907,	0.174254813810338,	0.201608600061656,	0.23061236845818,	0.263517587351605,	0.300132428486453,	0.332136023891356,	0.339657630578503,	0.307617017561916,	0.245122086283136,	0.177393268197549,	0.123083460001412,	0.0852523183869662,	0.0607293429607277,	0.0445224209885408,	0.0337316491487366,	0.0262419654944773,	0.0209666296001906,	0.0173806889477207,	0.0146796886926822,	0.0125254510905199,	0.0109715886206595,	0.00981897632530752,	0.00872347729276715,	0.00792492117937099,	0.00734211512866375,	0.00694494118834771,	0.00657640361363438,	0.00623276722640169,	0.00583683834545967,	0.00552059326445567,	0.00517695687722298,	0.00483332048999028,	0.00454322165584094,	0.00433280662162962,	0.0040950002811896,	0.00380365638766623,	0.00359324135345491,	0.00338158125986955,	0.0031699211662842,	0.00295950613207287,	0.00274784603848752,	0.00256233219175683,	0.00243160095748352,	0.00229837960446215,	0.00214150212333418,	0.00208796457025083,	0.00190369578289416,	0.00177171948924682,	0.00166588944245415,	0.00150652184257811,	0.00145422934886879,	0.00137454554893078,	0.00121717004405319,	0.00113736173817777,	0.00105755343230236,	0.00097986172736279,	0.000873782668695307,	0.000847511915903242,	0.000767703610027823,	0.000741432857235758,	0.000662745104796967,	0.00060908304577621,	0.000530395293337419,	0.000504124540545354,	0.000424316234669935,	0.000424316234669935,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};

    int Floweri = 0, Vegei = 0, Seedi = 0;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horticulture_level2);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        ViewPager vpPager = (ViewPager) findViewById(R.id.vpPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        vpPager.setCurrentItem(0);
        handler= new Handler(Looper.getMainLooper());
        client = new OkHttpClient();

        myArcChartView = findViewById(R.id.arc_chart_view_level2);
        myArcChartView.setSectionValue(0, 0);
        myArcChartView.setSectionValue(1, 0);
        myArcChartView.setSectionValue(2, 0);

        /** set colors */
        myArcChartView.setFilldeColor(0, this.getResources().getColor(R.color.colorDeepBlue));
        myArcChartView.setUnFilldeColor(0, this.getResources().getColor(R.color.colorDeepBlue_));
        myArcChartView.setFilldeColor(1, this.getResources().getColor(R.color.colorMint));
        myArcChartView.setUnFilldeColor(1, this.getResources().getColor(R.color.colorEQWhite_));
        myArcChartView.setFilldeColor(2, this.getResources().getColor(R.color.colorHyperRed));
        myArcChartView.setUnFilldeColor(2,this.getResources().getColor(R.color.colorHyperRed_));

        final TextView tv_type = findViewById(R.id.tv_type);
        final TextView tv_intensity = findViewById(R.id.tv_intensity);

        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        myArcChartView.setListener(new ArcChartView.AcvListener() {
            @Override
            public void onSectionsIconClicked(int i) {
//                Toast.makeText(getApplication().getBaseContext(), Integer.toString(i),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartSettingSectionValue(int i, int i1) {
//                Toast.makeText(getApplication().getBaseContext(), Integer.toString(i1),Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onContinueSettingSectionValue(int i, int i1) {

            }
            @Override
            public void onFinishedSettingSectionValue(int i, int i1) {
                switch(i) {
                    case 0:
                        myArcChartView.setSectionValue(1, 0);
                        myArcChartView.setSectionValue(2, 0);
                        Seedi = i1 * 340;
                        updateGraph(1);
                        Horti_FragmentGraph_Level2 fragment = (Horti_FragmentGraph_Level2) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                        fragment.updateGraph(0, Seedi);
                        fragment.updateGraph(1, 0);
                        fragment.updateGraph(2, 0);
                        sendMessage("$" + Integer.toString(i1));
                        switch (i1) {
                            case 0:
                                tv_type.setText("Recipe");
                                tv_intensity.setText("Intensity");
                                break;
                            case 1:
                                tv_type.setText("Seeding");
                                tv_intensity.setText("Low (3.28 umol/J)");
                                break;
                            case 2:
                                tv_type.setText("Seeding");
                                tv_intensity.setText("Typical (3.07 umol/J)");
                                break;
                            case 3:
                                tv_type.setText("Seeding");
                                tv_intensity.setText("High (2.77 umol/J)");
                                break;
                            default: break;
                        }
                        break;
                    case 1:
                        myArcChartView.setSectionValue(0, 0);
                        myArcChartView.setSectionValue(2, 0);
                        Vegei = i1 * 340;
                        updateGraph(2);
                        Horti_FragmentGraph_Level2 fragment2 = (Horti_FragmentGraph_Level2) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                        fragment2.updateGraph(1, Vegei);
                        fragment2.updateGraph(0, 0);
                        fragment2.updateGraph(2, 0);
                        sendMessage("%" + Integer.toString(i1));
                        switch (i1) {
                            case 0:
                                tv_type.setText("Recipe");
                                tv_intensity.setText("Intensity");
                                break;
                            case 1:
                                tv_type.setText("Vegetative");
                                tv_intensity.setText("Low (3.59 umol/J)");
                                break;
                            case 2:
                                tv_type.setText("Vegetative");
                                tv_intensity.setText("Typical (3.42 umol/J)");
                                break;
                            case 3:
                                tv_type.setText("Vegetative");
                                tv_intensity.setText("High (3.04 umol/J)");
                                break;
                            default: break;
                        }
                        break;
                    case 2:
                        myArcChartView.setSectionValue(1, 0);
                        myArcChartView.setSectionValue(0, 0);
                        Floweri = i1 * 340;
                        updateGraph(3);
                        Horti_FragmentGraph_Level2 fragment3 = (Horti_FragmentGraph_Level2) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                        fragment3.updateGraph(2, Floweri);
                        fragment3.updateGraph(1, 0);
                        fragment3.updateGraph(0, 0);
                        sendMessage("^" + Integer.toString(i1));
                        switch (i1) {
                            case 0:
                                tv_type.setText("Recipe");
                                tv_intensity.setText("Intensity");
                                break;
                            case 1:
                                tv_type.setText("Flowering");
                                tv_intensity.setText("Low (3.21 umol/J)");
                                break;
                            case 2:
                                tv_type.setText("Flowering");
                                tv_intensity.setText("Typical (3.06 umol/J)");
                                break;
                            case 3:
                                tv_type.setText("Flowering");
                                tv_intensity.setText("High (2.80 umol/J)");
                                break;
                            default: break;
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

public void updateGraph(final int indicator) {
    handler.post(new Runnable() {
        public void run() {

//            for (int z = 0; z < 235; z++) {
//                graphArray[z] = (flowerArray[z] * Floweri)  + (vegeArray[z] * Vegei) + (seedingArray[z] * Seedi);
//            }
//            for (int y = bluePPFrangemin; y < getBluePPFrangemax; y++){
//                bluePPF = (PPFconst * graphArray[y] * waveArray [y]) + bluePPF;
//            }
//            for (int y = greenPPFrangemin; y < greenPPFrangemax; y++){
//                greenPPF = (PPFconst * graphArray[y] * waveArray [y]) + greenPPF;
//            }
//            for (int y = redPPFrangemin; y < redPPFrangemax; y++){
//                redPPF = (PPFconst * graphArray[y] * waveArray [y]) + redPPF;
//            }
//            for (int y = red2PPFrangemin; y < red2PPFrangemax; y++){
//                red2PPF = (PPFconst * graphArray[y] * waveArray [y]) + red2PPF;
//            }
//            bluePPF = bluePPF * 100;
//            greenPPF = greenPPF * 100;
//            redPPF = redPPF * 100;
//            red2PPF = red2PPF * 100;

            switch(indicator){
                case 1: // seeding
                    bluePPF = 5170;
                    redPPF = 3100;
                    greenPPF = 1660;
                    red2PPF = 70;
                    break;
                case 2: // vegetative
                    bluePPF = 780;
                    redPPF = 7490;
                    greenPPF = 1660;
                    red2PPF = 30;
                    break;
                case 3: // flowering
                    bluePPF = 830;
                    redPPF = 4810;
                    greenPPF = 1770;
                    red2PPF = 2590;
                    break;
                default: break;
            }

            Horti_FragmentPie fragment1 = (Horti_FragmentPie) getSupportFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":1");
            fragment1.updatePie((int)bluePPF, (int)greenPPF, (int)redPPF, (int)red2PPF);
            bluePPF = 0;
            redPPF = 0;
            greenPPF = 0;
            red2PPF = 0;
        }
    });
}

    public void onBackPressed(){
        Intent intent = new Intent(Horticulture_Level2.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) { // reset all
        handler.post(new Runnable() {
            public void run() {
                /** Set everything back to 0 **/
                handler.removeCallbacksAndMessages(null);
                myArcChartView.setSectionValue(0, 0);
                myArcChartView.setSectionValue(1, 0);
                myArcChartView.setSectionValue(2, 0);

                sendMessage("$0");
                sendMessage("%0");
                sendMessage("^0");

//                sendMessage("-"); // send reset command to ESP

                Horti_FragmentPie fragment1 = (Horti_FragmentPie) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":1");
                fragment1.updatePie(0, 0, 0, 0);
                Horti_FragmentGraph_Level2 fragment = (Horti_FragmentGraph_Level2) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.vpPager + ":0");
                fragment.updateGraph(0, 0);
                fragment.updateGraph(1, 0);
                fragment.updateGraph(2, 0);
                fragment.updateGraph(3, 0);
                fragment.updateGraph(4, 0);
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
                    return new Horti_FragmentGraph_Level2();
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
                    mFragmentGraph = (Horti_FragmentGraph_Level2) createdFragment;
                    break;
                case 1:
                    mFragmentPie = (Horti_FragmentPie) createdFragment;
                    break;
            }
            return createdFragment;
        }
    }

    public static final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }
        @Override
        public void onMessage(WebSocket webSocket, String text) {
//            output("Receiving : " + text);
        }
        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
//            output("Receiving bytes : " + bytes.hex());
        }
        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
        }
        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//            output("Error : " + t.getMessage());
        }
    }

    public static void sendMessage(String msg){
        Request request = new Request.Builder().url("ws://192.168.1.1:81/").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        WebSocket ws = client.newWebSocket(request, listener);
        ws.send(msg);
    }
}



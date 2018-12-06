package com.osram.os.demos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Horti_recipe_fragment_seedlings_s5 extends Fragment implements View.OnClickListener{
    Handler handler;


    TextView tvPPFD, tvRB, tvEfficacy, tvApplications, tvPower, tvDescription, tvRecipe;
//    String PPFD, RB, Efficacy, Applications, Power;
double graphArray[] = {0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    double waveArray[] = {360,	362,	364,	366,	368,	370,	372,	374,	376,	378,	380,	382,	384,	386,	388,	390,	392,	394,	396,	398,	400,	402,	404,	406,	408,	410,	412,	414,	416,	418,	420,	422,	424,	426,	428,	430,	432,	434,	436,	438,	440,	442,	444,	446,	448,	450,	452,	454,	456,	458,	460,	462,	464,	466,	468,	470,	472,	474,	476,	478,	480,	482,	484,	486,	488,	490,	492,	494,	496,	498,	500,	502,	504,	506,	508,	510,	512,	514,	516,	518,	520,	522,	524,	526,	528,	530,	532,	534,	536,	538,	540,	542,	544,	546,	548,	550,	552,	554,	556,	558,	560,	562,	564,	566,	568,	570,	572,	574,	576,	578,	580,	582,	584,	586,	588,	590,	592,	594,	596,	598,	600,	602,	604,	606,	608,	610,	612,	614,	616,	618,	620,	622,	624,	626,	628,	630,	632,	634,	636,	638,	640,	642,	644,	646,	648,	650,	652,	654,	656,	658,	660,	662,	664,	666,	668,	670,	672,	674,	676,	678,	680,	682,	684,	686,	688,	690,	692,	694,	696,	698,	700,	702,	704,	706,	708,	710,	712,	714,	716,	718,	720,	722,	724,	726,	728,	730,	732,	734,	736,	738,	740,	742,	744,	746,	748,	750,	752,	754,	756,	758,	760,	762,	764,	766,	768,	770,	772,	774,	776,	778,	780,	782,	784,	786,	788,	790,	792,	794,	796,	798,	800,	802,	804,	806,	808,	810,	812,	814,	816,	818,	820,	822,	824,	826,	828,	830};
    //  FSW 5000K
    double redArray[] = {0.000000012,	1.92036199095023E-08,	2.03981900452489E-08,	2.03981900452489E-08,	2.15987933634992E-08,	1.68024132730015E-08,	1.92036199095023E-08,	2.27993966817496E-08,	1.92036199095023E-08,	1.44012066365008E-08,	1.80030165912519E-08,	1.80030165912519E-08,	1.56018099547511E-08,	1.92036199095023E-08,	1.68024132730015E-08,	1.92036199095023E-08,	1.92036199095023E-08,	1.92036199095023E-08,	2.03981900452489E-08,	0.000000024,	0.000000024,	2.64012066365008E-08,	2.88024132730015E-08,	3.47993966817496E-08,	3.96018099547511E-08,	0.000000048,	6.11764705882353E-08,	7.68024132730015E-08,	9.71945701357466E-08,	1.27179487179487E-07,	1.72790346907994E-07,	2.23227752639517E-07,	2.92790346907994E-07,	3.85218702865762E-07,	5.07873303167421E-07,	6.6606334841629E-07,	8.68174962292609E-07,	1.12820512820513E-06,	1.48355957767722E-06,	1.95052790346908E-06,	2.53212669683258E-06,	3.2211161387632E-06,	3.90165912518854E-06,	4.47963800904977E-06,	4.76742081447964E-06,	4.6552036199095E-06,	4.23831070889894E-06,	3.70075414781297E-06,	3.20060331825038E-06,	2.86093514328808E-06,	2.61297134238311E-06,	2.43378582202112E-06,	2.25822021116139E-06,	2.06033182503771E-06,	1.85218702865762E-06,	1.66938159879336E-06,	1.54087481146305E-06,	1.47450980392157E-06,	1.45037707390649E-06,	1.44856711915535E-06,	1.46726998491704E-06,	1.48355957767722E-06,	1.51372549019608E-06,	1.55656108597285E-06,	1.59819004524887E-06,	1.65128205128205E-06,	1.71221719457014E-06,	1.77616892911011E-06,	1.84434389140271E-06,	1.89502262443439E-06,	0.000001963197586727,	2.00904977375566E-06,	2.06636500754148E-06,	0.000002110407239819,	2.16229260935143E-06,	2.2105580693816E-06,	2.26003016591252E-06,	2.30165912518854E-06,	2.34389140271493E-06,	2.39034690799397E-06,	2.43378582202112E-06,	2.4579185520362E-06,	2.5025641025641E-06,	2.53815987933635E-06,	2.5659125188537E-06,	2.5894419306184E-06,	2.6184012066365E-06,	2.63770739064857E-06,	2.64856711915535E-06,	2.66666666666667E-06,	2.66907993966817E-06,	2.67511312217195E-06,	2.68657616892911E-06,	2.6763197586727E-06,	2.67209653092006E-06,	2.67511312217195E-06,	2.65942684766214E-06,	2.63650075414781E-06,	2.62262443438914E-06,	2.60392156862745E-06,	2.58039215686275E-06,	2.55746606334842E-06,	2.54057315233786E-06,	2.51402714932127E-06,	2.48808446455505E-06,	2.47300150829563E-06,	2.45309200603318E-06,	2.43619909502262E-06,	2.40844645550528E-06,	2.39758672699849E-06,	2.37526395173454E-06,	2.36621417797888E-06,	2.3553544494721E-06,	2.34147812971342E-06,	2.33846153846154E-06,	2.33182503770739E-06,	2.32820512820513E-06,	2.32699849170437E-06,	2.32156862745098E-06,	2.33363499245852E-06,	2.34027149321267E-06,	2.34328808446456E-06,	2.35595776772247E-06,	2.36199095022624E-06,	2.37586726998492E-06,	2.3843137254902E-06,	2.3867269984917E-06,	2.39698340874811E-06,	2.40482654600302E-06,	2.41206636500754E-06,	2.41447963800905E-06,	2.41447963800905E-06,	2.40904977375566E-06,	2.40482654600302E-06,	2.40180995475113E-06,	2.37828054298643E-06,	2.38310708898944E-06,	2.36078431372549E-06,	2.33303167420815E-06,	2.31251885369532E-06,	2.28114630467572E-06,	2.25399698340875E-06,	2.22021116138763E-06,	2.18280542986425E-06,	2.15022624434389E-06,	2.10377073906486E-06,	2.06033182503771E-06,	2.00482654600302E-06,	1.96259426847662E-06,	1.90346907993967E-06,	1.85218702865762E-06,	1.79788838612368E-06,	1.75324283559578E-06,	1.69773755656109E-06,	1.64283559577677E-06,	1.58974358974359E-06,	1.52820512820513E-06,	1.47752639517345E-06,	1.43046757164404E-06,	1.36953242835596E-06,	1.31764705882353E-06,	1.26696832579186E-06,	1.20965309200603E-06,	1.1710407239819E-06,	1.11372549019608E-06,	1.0630467571644E-06,	1.01779788838612E-06,	9.74358974358974E-07,	9.25490196078431E-07,	8.77224736048266E-07,	8.37405731523379E-07,	7.94570135746606E-07,	7.58371040723982E-07,	7.19155354449472E-07,	6.76923076923077E-07,	6.47963800904977E-07,	6.1659125188537E-07,	5.81779788838612E-07,	5.46244343891403E-07,	5.21809954751131E-07,	4.910407239819E-07,	4.65641025641026E-07,	4.40422322775264E-07,	4.17616892911011E-07,	3.93604826546003E-07,	3.66033182503771E-07,	3.47993966817496E-07,	3.27601809954751E-07,	3.0841628959276E-07,	2.9158371040724E-07,	2.73604826546003E-07,	2.55625942684766E-07,	2.43619909502262E-07,	2.2920060331825E-07,	2.12428355957768E-07,	2.00422322775264E-07,	1.8841628959276E-07,	1.77616892911011E-07,	1.66817496229261E-07,	1.57224736048265E-07,	1.4763197586727E-07,	1.39185520361991E-07,	1.33212669683258E-07,	1.23619909502262E-07,	1.17586726998492E-07,	1.10407239819005E-07,	1.04434389140271E-07,	9.59879336349925E-08,	9.12217194570136E-08,	8.6395173453997E-08,	7.92156862745098E-08,	7.43891402714932E-08,	7.08295625942685E-08,	6.60030165912519E-08,	6.11764705882353E-08,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    // Purple
    double red2Array[] = {6.61960784313725E-09,	1.32392156862745E-08,	1.10352941176471E-08,	9.92941176470588E-09,	8.82745098039216E-09,	1.10352941176471E-08,	1.21372549019608E-08,	1.10352941176471E-08,	9.92941176470588E-09,	1.10352941176471E-08,	1.21372549019608E-08,	9.92941176470588E-09,	1.10352941176471E-08,	8.82745098039216E-09,	8.82745098039216E-09,	9.92941176470588E-09,	1.10352941176471E-08,	1.21372549019608E-08,	1.21372549019608E-08,	1.43450980392157E-08,	1.21372549019608E-08,	1.43450980392157E-08,	1.76549019607843E-08,	1.8756862745098E-08,	2.20666666666667E-08,	2.64823529411765E-08,	3.64117647058824E-08,	4.96470588235294E-08,	6.95294117647059E-08,	0.000000096,	1.32392156862745E-07,	1.83137254901961E-07,	2.57098039215686E-07,	3.4756862745098E-07,	4.71372549019608E-07,	6.1921568627451E-07,	8.09803921568628E-07,	1.0556862745098E-06,	1.37372549019608E-06,	1.78627450980392E-06,	2.27411764705882E-06,	2.79686274509804E-06,	3.22745098039216E-06,	3.46313725490196E-06,	3.3556862745098E-06,	2.95254901960784E-06,	2.40980392156863E-06,	1.9121568627451E-06,	1.54549019607843E-06,	1.28352941176471E-06,	1.09882352941176E-06,	9.34901960784314E-07,	7.73333333333333E-07,	0.00000062,	4.83137254901961E-07,	3.77333333333333E-07,	3.0121568627451E-07,	2.47137254901961E-07,	2.07411764705882E-07,	1.69921568627451E-07,	1.44549019607843E-07,	1.18039215686275E-07,	9.26666666666667E-08,	7.61176470588235E-08,	6.29019607843137E-08,	5.29803921568627E-08,	4.41176470588235E-08,	3.86156862745098E-08,	3.19960784313725E-08,	2.75843137254902E-08,	2.53764705882353E-08,	2.20666666666667E-08,	1.8756862745098E-08,	1.65490196078431E-08,	1.32392156862745E-08,	1.32392156862745E-08,	1.21372549019608E-08,	1.10352941176471E-08,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	8.82745098039216E-09,	9.92941176470588E-09,	1.32392156862745E-08,	1.32392156862745E-08,	1.43450980392157E-08,	1.76549019607843E-08,	2.09647058823529E-08,	2.53764705882353E-08,	2.97921568627451E-08,	3.53058823529412E-08,	4.41176470588235E-08,	5.29803921568627E-08,	6.61960784313726E-08,	8.16470588235294E-08,	1.02627450980392E-07,	1.25764705882353E-07,	1.5556862745098E-07,	1.93098039215686E-07,	2.30588235294118E-07,	2.82470588235294E-07,	3.40941176470588E-07,	4.09411764705882E-07,	4.92156862745098E-07,	5.76078431372549E-07,	6.90588235294118E-07,	8.08627450980392E-07,	9.49019607843137E-07,	1.09450980392157E-06,	0.00000126,	1.43137254901961E-06,	1.61607843137255E-06,	1.80862745098039E-06,	2.01019607843137E-06,	0.00000222,	2.42941176470588E-06,	2.62588235294118E-06,	2.83411764705882E-06,	3.04509803921569E-06,	3.22941176470588E-06,	3.40588235294118E-06,	3.57058823529412E-06,	3.73058823529412E-06,	3.87725490196078E-06,	0.000004,	4.10588235294118E-06,	4.19607843137255E-06,	4.27843137254902E-06,	4.34117647058824E-06,	4.37254901960784E-06,	0.0000044,	4.4078431372549E-06,	4.41176470588235E-06,	4.39607843137255E-06,	4.36470588235294E-06,	4.31764705882353E-06,	4.25882352941176E-06,	4.20392156862745E-06,	4.13725490196078E-06,	4.05098039215686E-06,	3.96078431372549E-06,	3.87294117647059E-06,	3.77921568627451E-06,	3.66823529411765E-06,	3.56078431372549E-06,	3.44549019607843E-06,	3.32666666666667E-06,	3.22392156862745E-06,	3.12392156862745E-06,	3.00549019607843E-06,	2.8878431372549E-06,	2.78392156862745E-06,	2.66666666666667E-06,	2.55529411764706E-06,	2.4443137254902E-06,	2.34352941176471E-06,	2.24509803921569E-06,	2.13607843137255E-06,	2.03450980392157E-06,	1.93843137254902E-06,	1.83725490196078E-06,	1.74313725490196E-06,	1.65960784313725E-06,	1.5721568627451E-06,	1.49960784313726E-06,	1.41686274509804E-06,	1.34156862745098E-06,	1.26235294117647E-06,	1.19607843137255E-06,	1.13411764705882E-06,	1.07333333333333E-06,	1.01294117647059E-06,	9.54509803921569E-07,	8.98039215686275E-07,	8.44313725490196E-07,	7.95686274509804E-07,	7.52549019607843E-07,	7.07058823529412E-07,	6.62745098039216E-07,	6.26666666666667E-07,	5.87843137254902E-07,	5.55294117647059E-07,	5.2078431372549E-07,	4.85490196078431E-07,	4.54509803921569E-07,	4.25882352941176E-07,	3.99607843137255E-07,	3.77333333333333E-07,	3.51960784313726E-07,	3.29921568627451E-07,	3.10039215686275E-07,	2.92392156862745E-07,	2.76941176470588E-07,	2.63686274509804E-07,	2.44941176470588E-07,	2.29490196078431E-07,	2.12941176470588E-07,	2.00823529411765E-07,	1.89764705882353E-07,	1.78745098039216E-07,	1.69921568627451E-07,	1.57764705882353E-07,	1.45647058823529E-07,	1.37921568627451E-07,	1.29098039215686E-07,	1.22470588235294E-07,	1.15843137254902E-07,	1.0921568627451E-07,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    // Lime
    double blueArray[] = {5.29411764705882E-09,	1.58823529411765E-08,	1.69411764705882E-08,	5.29411764705882E-09,	4.23529411764706E-09,	8.47058823529412E-09,	2.11764705882353E-09,	1.16470588235294E-08,	1.58823529411765E-08,	1.05882352941176E-08,	1.90588235294118E-08,	1.27058823529412E-08,	4.23529411764706E-09,	1.37647058823529E-08,	1.05882352941176E-08,	1.37647058823529E-08,	1.27058823529412E-08,	7.41176470588235E-09,	6.35294117647059E-09,	1.69411764705882E-08,	1.90588235294118E-08,	2.43529411764706E-08,	4.23529411764706E-08,	5.08235294117647E-08,	6.03529411764706E-08,	8.89411764705882E-08,	0.000000108,	1.33411764705882E-07,	1.67294117647059E-07,	1.95882352941176E-07,	2.32941176470588E-07,	2.73176470588235E-07,	3.08117647058824E-07,	3.30352941176471E-07,	3.50470588235294E-07,	3.68509803921569E-07,	3.89686274509804E-07,	4.29019607843137E-07,	4.52549019607843E-07,	4.56078431372549E-07,	4.29803921568627E-07,	3.68509803921569E-07,	2.91176470588235E-07,	2.27647058823529E-07,	1.67294117647059E-07,	1.32352941176471E-07,	0.000000108,	1.05882352941176E-07,	9.74117647058823E-08,	8.47058823529412E-08,	7.30588235294118E-08,	7.30588235294118E-08,	6.88235294117647E-08,	6.67058823529412E-08,	6.98823529411765E-08,	7.62352941176471E-08,	0.00000009,	9.84705882352941E-08,	1.10117647058824E-07,	1.28117647058824E-07,	1.57764705882353E-07,	0.000000198,	2.45647058823529E-07,	3.07058823529412E-07,	3.92941176470588E-07,	4.96862745098039E-07,	6.07843137254902E-07,	7.54901960784314E-07,	9.13725490196078E-07,	1.09372549019608E-06,	1.29294117647059E-06,	1.50235294117647E-06,	1.72823529411765E-06,	1.96941176470588E-06,	2.21529411764706E-06,	2.45098039215686E-06,	2.69098039215686E-06,	2.88941176470588E-06,	3.08901960784314E-06,	3.27843137254902E-06,	3.41372549019608E-06,	3.56078431372549E-06,	3.66392156862745E-06,	3.77725490196078E-06,	3.86823529411765E-06,	3.93333333333333E-06,	3.98039215686275E-06,	4.02745098039216E-06,	4.07843137254902E-06,	4.12156862745098E-06,	4.14117647058824E-06,	4.18039215686275E-06,	4.2078431372549E-06,	4.19607843137255E-06,	4.21960784313725E-06,	4.1921568627451E-06,	4.17647058823529E-06,	4.16470588235294E-06,	4.15686274509804E-06,	4.14117647058824E-06,	4.06666666666667E-06,	4.0156862745098E-06,	3.98039215686275E-06,	3.94901960784314E-06,	3.90941176470588E-06,	3.84901960784314E-06,	3.78666666666667E-06,	3.73450980392157E-06,	3.66509803921569E-06,	3.58862745098039E-06,	3.51137254901961E-06,	3.43725490196078E-06,	3.35098039215686E-06,	3.28901960784314E-06,	3.21607843137255E-06,	3.1356862745098E-06,	3.05921568627451E-06,	2.96352941176471E-06,	2.85686274509804E-06,	2.76352941176471E-06,	2.67764705882353E-06,	2.61019607843137E-06,	2.53921568627451E-06,	2.45019607843137E-06,	2.38235294117647E-06,	2.28705882352941E-06,	2.21607843137255E-06,	2.13803921568627E-06,	2.05647058823529E-06,	1.98235294117647E-06,	1.89843137254902E-06,	1.83294117647059E-06,	1.7556862745098E-06,	1.69411764705882E-06,	1.62235294117647E-06,	1.55019607843137E-06,	1.48745098039216E-06,	0.00000144,	1.36470588235294E-06,	1.30862745098039E-06,	1.25372549019608E-06,	1.19333333333333E-06,	1.1521568627451E-06,	1.10117647058824E-06,	1.05176470588235E-06,	1.00156862745098E-06,	9.64313725490196E-07,	9.17647058823529E-07,	8.72549019607843E-07,	8.32941176470588E-07,	7.93333333333333E-07,	7.60392156862745E-07,	7.14901960784314E-07,	6.7843137254902E-07,	6.45882352941176E-07,	6.24705882352941E-07,	5.92941176470588E-07,	5.61960784313725E-07,	5.44313725490196E-07,	5.18039215686275E-07,	4.92549019607843E-07,	4.67058823529412E-07,	4.47843137254902E-07,	4.22352941176471E-07,	4.10980392156863E-07,	3.8121568627451E-07,	3.54745098039216E-07,	3.45176470588235E-07,	3.31411764705882E-07,	3.12352941176471E-07,	2.93294117647059E-07,	2.81647058823529E-07,	0.00000027,	2.53058823529412E-07,	2.46705882352941E-07,	2.24470588235294E-07,	2.21294117647059E-07,	2.12823529411765E-07,	2.04352941176471E-07,	1.89529411764706E-07,	1.76823529411765E-07,	1.66235294117647E-07,	1.65176470588235E-07,	1.56705882352941E-07,	1.46117647058824E-07,	1.32352941176471E-07,	0.000000126,	1.24941176470588E-07,	1.15411764705882E-07,	1.15411764705882E-07,	1.04823529411765E-07,	9.52941176470588E-08,	9.74117647058823E-08,	8.89411764705882E-08,	8.36470588235294E-08,	8.15294117647059E-08,	0.000000072,	6.98823529411765E-08,	6.77647058823529E-08,	6.24705882352941E-08,	6.35294117647059E-08,	5.71764705882353E-08,	6.03529411764706E-08,	5.08235294117647E-08,	5.50588235294118E-08,	5.50588235294118E-08,	4.87058823529412E-08,	4.34117647058824E-08,	4.02352941176471E-08,	3.60039215686275E-08,	3.49411764705882E-08,	3.38823529411765E-08,	3.28235294117647E-08,	3.07058823529412E-08,	2.85882352941176E-08,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    // white
    double whiteArray[] = {5.65490196078431E-09,	7.54147812971343E-09,	1.50769230769231E-08,	9.4238310708899E-09,	1.41357466063348E-08,	1.50769230769231E-08,	1.13122171945701E-08,	1.69653092006033E-08,	1.41357466063348E-08,	1.50769230769231E-08,	1.41357466063348E-08,	1.50769230769231E-08,	1.69653092006033E-08,	1.50769230769231E-08,	1.9788838612368E-08,	1.9788838612368E-08,	1.9788838612368E-08,	2.16772247360483E-08,	2.82714932126697E-08,	2.73303167420814E-08,	2.82714932126697E-08,	3.76953242835596E-08,	4.42956259426848E-08,	4.9948717948718E-08,	5.56018099547511E-08,	6.59426847662142E-08,	7.54147812971342E-08,	9.52036199095023E-08,	1.10286576168929E-07,	1.34781297134238E-07,	1.6868778280543E-07,	2.11101055806938E-07,	2.62926093514329E-07,	3.41176470588235E-07,	4.3052790346908E-07,	5.53061840120664E-07,	6.99849170437406E-07,	9.00150829562594E-07,	1.18009049773756E-06,	1.5843137254902E-06,	2.14057315233786E-06,	2.79336349924585E-06,	3.40995475113122E-06,	3.7236802413273E-06,	3.64524886877828E-06,	3.22413273001508E-06,	2.69441930618401E-06,	2.2552036199095E-06,	1.99396681749623E-06,	1.82081447963801E-06,	1.66636500754148E-06,	1.50769230769231E-06,	1.32609351432881E-06,	1.13122171945701E-06,	9.72549019607843E-07,	8.57918552036199E-07,	7.8974358974359E-07,	7.57767722473605E-07,	7.43288084464555E-07,	7.3604826546003E-07,	7.34841628959276E-07,	7.55354449472097E-07,	7.86726998491704E-07,	8.39819004524887E-07,	9.10407239819005E-07,	9.94268476621418E-07,	1.08597285067873E-06,	1.18250377073906E-06,	1.27963800904977E-06,	1.38823529411765E-06,	1.48536953242836E-06,	1.56440422322775E-06,	1.66636500754148E-06,	1.74419306184012E-06,	1.83227752639517E-06,	1.89622926093514E-06,	1.95656108597285E-06,	2.01930618401207E-06,	2.07058823529412E-06,	2.11945701357466E-06,	2.16772247360483E-06,	2.20814479638009E-06,	2.24253393665158E-06,	2.27511312217195E-06,	2.2974358974359E-06,	2.32458521870287E-06,	2.35052790346908E-06,	2.36923076923077E-06,	2.39939668174962E-06,	2.42956259426848E-06,	2.45671191553544E-06,	2.47058823529412E-06,	2.49230769230769E-06,	0.000002510407239819,	2.54841628959276E-06,	2.57194570135747E-06,	2.59064856711916E-06,	2.61538461538462E-06,	2.64615384615385E-06,	2.66726998491704E-06,	2.69864253393665E-06,	2.71794871794872E-06,	2.7499245852187E-06,	2.77466063348416E-06,	2.79698340874811E-06,	2.81508295625943E-06,	2.84645550527903E-06,	2.87541478129713E-06,	2.89049773755656E-06,	2.91221719457014E-06,	2.93453996983409E-06,	2.95444947209653E-06,	2.9659125188537E-06,	2.98280542986425E-06,	2.99909502262443E-06,	3.00995475113122E-06,	3.00935143288084E-06,	3.02262443438914E-06,	3.0184012066365E-06,	3.01779788838612E-06,	3.00935143288084E-06,	3.00814479638009E-06,	2.98642533936652E-06,	2.97435897435897E-06,	2.97616892911011E-06,	2.94238310708899E-06,	2.91764705882353E-06,	2.88868778280543E-06,	2.8422322775264E-06,	2.78793363499246E-06,	2.74751131221719E-06,	2.70889894419306E-06,	2.65701357466063E-06,	2.58401206636501E-06,	2.52006033182504E-06,	2.45610859728507E-06,	2.38069381598793E-06,	2.31251885369532E-06,	2.24193061840121E-06,	2.16168929110106E-06,	2.08929110105581E-06,	2.02654600301659E-06,	1.94630467571644E-06,	1.88114630467572E-06,	1.80754147812971E-06,	1.73092006033183E-06,	1.65490196078431E-06,	1.58612368024133E-06,	1.51070889894419E-06,	1.44012066365008E-06,	1.37797888386124E-06,	1.30920060331825E-06,	1.2422322775264E-06,	1.18250377073906E-06,	1.12217194570136E-06,	1.07390648567119E-06,	1.00754147812971E-06,	9.50829562594269E-07,	9.06787330316742E-07,	8.57918552036199E-07,	8.11463046757164E-07,	7.62594268476621E-07,	7.19758672699849E-07,	6.76923076923077E-07,	6.38914027149321E-07,	6.02413273001508E-07,	5.69230769230769E-07,	5.43589743589744E-07,	5.08898944193062E-07,	4.78793363499246E-07,	4.47481146304676E-07,	4.22202111613876E-07,	3.97707390648567E-07,	3.66636500754148E-07,	3.51553544494721E-07,	3.3079939668175E-07,	3.09140271493213E-07,	2.92187028657617E-07,	2.69562594268477E-07,	2.55384615384615E-07,	2.3843137254902E-07,	2.21478129713424E-07,	2.08265460030166E-07,	1.99819004524887E-07,	1.80935143288084E-07,	1.74358974358974E-07,	1.63981900452489E-07,	1.51734539969834E-07,	1.44193061840121E-07,	1.31945701357466E-07,	1.29110105580694E-07,	1.16862745098039E-07,	1.06485671191554E-07,	1.01779788838612E-07,	9.99095022624435E-08,	9.52036199095023E-08,	8.57918552036199E-08,	8.19909502262444E-08,	7.54147812971342E-08,	7.16138763197587E-08,	6.87782805429864E-08,	6.22021116138763E-08,	5.84313725490196E-08,	5.74901960784314E-08,	5.18371040723982E-08,	5.27782805429864E-08,	4.42956259426848E-08,	4.5236802413273E-08,	4.71251885369533E-08,	3.86425339366516E-08,	3.58129713423831E-08,	3.29834087481146E-08,	3.01598793363499E-08,	2.26184012066365E-08,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};
    // FSW 4000K
    double purpleArray[] = {7.39064856711915E-09,	1.31402714932127E-08,	1.47812971342383E-08,	1.72488687782805E-08,	1.31402714932127E-08,	1.47812971342383E-08,	1.64283559577677E-08,	1.31402714932127E-08,	1.1499245852187E-08,	1.31402714932127E-08,	1.39607843137255E-08,	1.31402714932127E-08,	1.31402714932127E-08,	1.31402714932127E-08,	1.31402714932127E-08,	1.1499245852187E-08,	1.31402714932127E-08,	1.31402714932127E-08,	1.56018099547511E-08,	1.80693815987934E-08,	1.72488687782805E-08,	2.05309200603318E-08,	2.62805429864253E-08,	3.20301659125189E-08,	3.69592760180995E-08,	4.68114630467572E-08,	6.07541478129713E-08,	7.80090497737557E-08,	1.00995475113122E-07,	1.29773755656109E-07,	1.74962292609351E-07,	2.24193061840121E-07,	2.95686274509804E-07,	3.79426847662142E-07,	4.90497737556561E-07,	6.35897435897436E-07,	8.18099547511312E-07,	1.06003016591252E-06,	1.37737556561086E-06,	1.77556561085973E-06,	2.23046757164404E-06,	2.70286576168929E-06,	3.06485671191554E-06,	3.26636500754148E-06,	3.19638009049774E-06,	2.9236802413273E-06,	2.54600301659125E-06,	2.21297134238311E-06,	1.96621417797888E-06,	1.80030165912519E-06,	1.67601809954751E-06,	1.55656108597285E-06,	1.43288084464555E-06,	1.29110105580694E-06,	1.17164404223228E-06,	1.08838612368024E-06,	1.04977375565611E-06,	1.03589743589744E-06,	1.04494720965309E-06,	1.07028657616893E-06,	1.10045248868778E-06,	1.13665158371041E-06,	1.18009049773756E-06,	1.22714932126697E-06,	1.28325791855204E-06,	1.34841628959276E-06,	1.40935143288084E-06,	1.47269984917044E-06,	1.53061840120664E-06,	1.59095022624434E-06,	1.63921568627451E-06,	1.70075414781297E-06,	1.75384615384615E-06,	1.79849170437406E-06,	1.84615384615385E-06,	1.89562594268477E-06,	1.93906485671192E-06,	1.98552036199095E-06,	2.03438914027149E-06,	2.07963800904977E-06,	2.11825037707391E-06,	2.15927601809955E-06,	2.19185520361991E-06,	2.22684766214178E-06,	2.2684766214178E-06,	2.29260935143288E-06,	2.32699849170437E-06,	2.35113122171946E-06,	2.37043740573152E-06,	2.39577677224736E-06,	2.41447963800905E-06,	2.42956259426848E-06,	2.43197586726998E-06,	2.43559577677225E-06,	0.000002436802413273,	2.4446455505279E-06,	2.43861236802413E-06,	2.43197586726998E-06,	2.43257918552036E-06,	2.4156862745098E-06,	2.40060331825038E-06,	2.39819004524887E-06,	2.38914027149321E-06,	2.37526395173454E-06,	2.36440422322775E-06,	2.35414781297134E-06,	2.34570135746606E-06,	2.33906485671192E-06,	2.34389140271493E-06,	2.33665158371041E-06,	2.33001508295626E-06,	2.3342383107089E-06,	2.33785822021116E-06,	2.34389140271493E-06,	2.35475113122172E-06,	2.3710407239819E-06,	2.38190045248869E-06,	2.39577677224736E-06,	2.40844645550528E-06,	2.43740573152338E-06,	2.45248868778281E-06,	2.47481146304676E-06,	2.51342383107089E-06,	2.54539969834088E-06,	2.5659125188537E-06,	2.59366515837104E-06,	2.62322775263952E-06,	2.64977375565611E-06,	2.6684766214178E-06,	2.68476621417798E-06,	2.70045248868778E-06,	2.71432880844646E-06,	2.72518853695324E-06,	2.73303167420815E-06,	2.73544494720965E-06,	2.73303167420815E-06,	2.71734539969834E-06,	2.71191553544495E-06,	2.69321266968326E-06,	2.67390648567119E-06,	2.65098039215686E-06,	2.62262443438914E-06,	2.58642533936652E-06,	2.54539969834088E-06,	2.51644042232278E-06,	2.47239819004525E-06,	2.42051282051282E-06,	2.36862745098039E-06,	2.31794871794872E-06,	2.25761689291101E-06,	2.19185520361991E-06,	2.1393665158371E-06,	2.07300150829563E-06,	2.00603318250377E-06,	1.9499245852187E-06,	1.88657616892911E-06,	1.82986425339367E-06,	1.76772247360483E-06,	1.70316742081448E-06,	1.63740573152338E-06,	1.57285067873303E-06,	1.51432880844646E-06,	1.44796380090498E-06,	1.39426847662142E-06,	1.33453996983409E-06,	1.27601809954751E-06,	1.21689291101056E-06,	1.1577677224736E-06,	1.10467571644042E-06,	1.05641025641026E-06,	1.0027149321267E-06,	9.526395173454E-07,	9.06184012066365E-07,	8.62141779788839E-07,	8.21719457013575E-07,	7.75867269984917E-07,	7.31825037707391E-07,	6.95625942684766E-07,	6.57616892911011E-07,	6.25037707390649E-07,	5.88054298642534E-07,	5.57526395173454E-07,	5.23981900452489E-07,	4.93453996983409E-07,	4.68114630467572E-07,	4.3921568627451E-07,	4.18883861236802E-07,	3.91613876319759E-07,	3.70558069381599E-07,	3.4920060331825E-07,	3.30135746606335E-07,	3.07993966817496E-07,	2.90739064856712E-07,	2.71010558069382E-07,	2.52126696832579E-07,	2.36561085972851E-07,	2.26666666666667E-07,	2.11885369532428E-07,	1.9710407239819E-07,	1.87269984917044E-07,	1.7737556561086E-07,	1.65067873303167E-07,	1.56862745098039E-07,	1.46184012066365E-07,	1.36349924585219E-07,	1.29773755656109E-07,	1.22352941176471E-07,	1.1577677224736E-07,	1.06787330316742E-07,	1.0184012066365E-07,	9.68929110105581E-08,	9.0316742081448E-08,	8.45852187028658E-08,	7.88536953242836E-08,	7.47511312217195E-08,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0,	0};

    // define recipe here
    int bluei = 0, redi = 255, whitei = 0 , red2i = 0, purplei = 0;

    // PPF calculation
    double PPF = 0, BPF = 0;
    double PPFconst = 8.359 * 0.001;
    int PPFrangemin = 20, PPFrangemax = 170;
    int BPFrangemin = 0, BPFrangemax = 235;
    double bluePPF, greenPPF, redPPF, red2PPF;
    int bluePPFrangemin = 20, getBluePPFrangemax = 70, redPPFrangemin = 121, redPPFrangemax = 170, greenPPFrangemin = 71, greenPPFrangemax = 120, red2PPFrangemin = 171, red2PPFrangemax = 234;

    Button tvRecipes;

    public Horti_recipe_fragment_seedlings_s5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Horti_recipe_fragment_growth.
     */
    // TODO: Rename and change types and number of parameters
    public static Horti_recipe_fragment_seedlings_s5 newInstance(String param1, String param2) {
        Horti_recipe_fragment_seedlings_s5 fragment = new Horti_recipe_fragment_seedlings_s5();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler= new Handler(Looper.getMainLooper());
        updateRequirements("Plants on the shop floor need sufficient light to continue growing at a slower pace before being bought. This light should also be of high quality to attract customers");
                updateGraph();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horti_recipe_description, container, false);
        tvDescription = view.findViewById(R.id.tvDescription);
        tvRecipes = view.findViewById(R.id.tvRecipes);
        tvRecipes.setOnClickListener(this);
        updateLights();
        return view;
    }


    public void updateRequirements( final String Description){
        handler.post(new Runnable() {
            public void run() {
            tvDescription.setText(Description);
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
                Horti_FragmentPie fragment1 = (Horti_FragmentPie) getFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":1");
                fragment1.updatePie((int)bluePPF, (int)greenPPF, (int)redPPF, (int)red2PPF);
                Horti_FragmentGraph_s5 fragment = (Horti_FragmentGraph_s5) getFragmentManager().findFragmentByTag("android:switcher:"+R.id.vpPager+":0");
                fragment.updateGraph(0, bluei);
                fragment.updateGraph(1, whitei);
                fragment.updateGraph(2, redi);
                fragment.updateGraph(3, red2i);

                bluePPF = 0;
                redPPF = 0;
                greenPPF = 0;
                red2PPF = 0;
            }
        });
    }
    public void updateLights() {
    // update lights
        handler.post(new Runnable() {
            public void run() {
                int j = 187; //0xD
                Horticulture_recipe_s5.mBluetoothConnection.write(j);
                Horticulture_recipe_s5.mBluetoothConnection.write(bluei);
                j = 204; //0xDD
                Horticulture_recipe_s5.mBluetoothConnection.write(j);
                Horticulture_recipe_s5.mBluetoothConnection.write(whitei);
                j = 170; //0xDD
                Horticulture_recipe_s5.mBluetoothConnection.write(j);
                Horticulture_recipe_s5.mBluetoothConnection.write(redi);
                j = 221; //0xDD
                Horticulture_recipe_s5.mBluetoothConnection.write(j);
                Horticulture_recipe_s5.mBluetoothConnection.write(red2i);
            }
        });
    }

    @Override
    public void onClick(View view) {
        // get fragment manager
        FragmentManager fm = getFragmentManager();
        // replace frame layout with fragment
        FragmentTransaction ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.slide_down_back, R.anim.fade_out);
        ft.replace(R.id.frameLayout, new Horti_recipe_fragment_container_s5());
        ft.commit();
    }
}

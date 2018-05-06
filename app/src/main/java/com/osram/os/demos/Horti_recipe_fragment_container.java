package com.osram.os.demos;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


public class Horti_recipe_fragment_container extends Fragment {
    Handler handler;
    BluetoothConnectionService mBluetoothConnection;
    BluetoothDevice bluetoothDevice;


    public Horti_recipe_fragment_container() {
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
    public static Horti_recipe_fragment_container newInstance(String param1, String param2) {
        Horti_recipe_fragment_container fragment = new Horti_recipe_fragment_container();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler= new Handler(Looper.getMainLooper());
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_horti_recipe_container, container, false);

        final GridView gridView = (GridView) view.findViewById(R.id.gridview);
        final GridViewAdapter gridAdapter = new GridViewAdapter(getContext(), plants);
        gridView.setAdapter(gridAdapter);



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                switch (position) {
                    case 0: // growth
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                        ft.setCustomAnimations(R.anim.slide_up, R.anim.fade_out);
                        ft.replace(R.id.frameLayout, new Horti_recipe_fragment_growth());
                        ft.commit();
                        ft.addToBackStack(null);
                        handler.post(new Runnable() {
                            public void run() {
                                Horticulture_recipe.tvTitle.setText("Growth");
                                Horticulture_recipe.tvColors.setText("Deep Blue + Hyper Red");
                            }
                        });
                        break;
                    case 1: // Maintenance
                        final FragmentTransaction ft2 = getFragmentManager().beginTransaction();
//                        ft2.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                        ft2.replace(R.id.frameLayout, new Horti_recipe_fragment_maintainance());
                        ft2.commit();
                        ft2.addToBackStack(null);
                        handler.post(new Runnable() {
                            public void run() {
                                Horticulture_recipe.tvTitle.setText("Maintenance");
                                Horticulture_recipe.tvColors.setText("Full Spectrum White");
                            }
                        });
                        break;
                    case 2: // full solution
                        final FragmentTransaction ft3 = getFragmentManager().beginTransaction();
//                        ft3.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                        ft3.replace(R.id.frameLayout, new Horti_recipe_fragment_full());
                        ft3.commit();
                        ft3.addToBackStack(null);
                        handler.post(new Runnable() {
                            public void run() {
                                Horticulture_recipe.tvTitle.setText("Full Solution");
                                Horticulture_recipe.tvColors.setText("DB + HR + FSW + FR");
                            }
                        });
                        break;
                    case 3: // flowering
                        final FragmentTransaction ft4 = getFragmentManager().beginTransaction();
//                        ft4.setCustomAnimations(R.anim.slide_up, R.anim.slide_down);
                        ft4.replace(R.id.frameLayout, new Horti_recipe_fragment_flowering());
                        ft4.commit();
                        ft4.addToBackStack(null);
                        handler.post(new Runnable() {
                            public void run() {
                                Horticulture_recipe.tvTitle.setText("Flowering");
                                Horticulture_recipe.tvColors.setText("Hyper Red + Far Red");
                            }
                        });
                        break;
                    default:
                        break;
                }
                gridAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }



    private Demos[] plants = {
            new Demos(R.string.growth, R.drawable.plant_growth),
            new Demos(R.string.maintainance, R.drawable.plant_maintainance),
            new Demos(R.string.complete, R.drawable.complete),
            new Demos(R.string.flowering, R.drawable.flowering),
//            new Demos(R.string.mizuna, R.drawable.mizuna),
//            new Demos(R.string.potatoes, R.drawable.potatoes),
//            new Demos(R.string.avocado, R.drawable.avocado),
//            new Demos(R.string.spinach, R.drawable.spinach)
    };



}

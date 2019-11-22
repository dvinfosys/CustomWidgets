package com.dvinfosys.WidgetsExample.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.SpinWheel.SpinWheelView;
import com.dvinfosys.widgets.SpinWheel.SpinWheeltem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinWheelFragment extends Fragment {

    List<SpinWheeltem> data = new ArrayList<>();
    private SpinWheelView spinWheelView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v=inflater.inflate(R.layout.fragment_spin_wheel, container, false);
        spinWheelView = v.findViewById(R.id.swv_example);
        setData();
        v.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = getRandomIndex();
                spinWheelView.startSpinWheelViewWithTargetIndex(index);
            }
        });
        spinWheelView.setSpinWheelItemSelectedListener(new SpinWheelView.SpinWheelItemSelectedListener() {
            @Override
            public void SpinWheelItemSelected(int index) {
                Toast.makeText(getContext(), data.get(index).topText, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

    private void setData() {
        SpinWheeltem spinItem1 = new SpinWheeltem();
        spinItem1.topText = "100";
        spinItem1.icon = R.drawable.test1;
        spinItem1.color = 0xffFFF3E0;
        data.add(spinItem1);

        SpinWheeltem spinItem2 = new SpinWheeltem();
        spinItem2.topText = "200";
        spinItem2.icon = R.drawable.test2;
        spinItem2.color = 0xffFFE0B2;
        data.add(spinItem2);

        SpinWheeltem spinItem3 = new SpinWheeltem();
        spinItem3.topText = "300";
        spinItem3.icon = R.drawable.test3;
        spinItem3.color = 0xffFFCC80;
        data.add(spinItem3);

        //////////////////
        SpinWheeltem spinItem4 = new SpinWheeltem();
        spinItem4.topText = "400";
        spinItem4.icon = R.drawable.test4;
        spinItem4.color = 0xffFFF3E0;
        data.add(spinItem4);

        SpinWheeltem spinItem5 = new SpinWheeltem();
        spinItem5.topText = "500";
        spinItem5.icon = R.drawable.test5;
        spinItem5.color = 0xffFFE0B2;
        data.add(spinItem5);

        SpinWheeltem spinItem6 = new SpinWheeltem();
        spinItem6.topText = "600";
        spinItem6.icon = R.drawable.test6;
        spinItem6.color = 0xffFFCC80;
        data.add(spinItem6);

        SpinWheeltem spinItem7 = new SpinWheeltem();
        spinItem7.topText = "700";
        spinItem7.icon = R.drawable.test7;
        spinItem7.color = 0xffFFF3E0;
        data.add(spinItem7);

        SpinWheeltem spinItem8 = new SpinWheeltem();
        spinItem8.topText = "800";
        spinItem8.icon = R.drawable.test8;
        spinItem8.color = 0xffFFE0B2;
        data.add(spinItem8);


        SpinWheeltem spinItem9 = new SpinWheeltem();
        spinItem9.topText = "900";
        spinItem9.icon = R.drawable.test9;
        spinItem9.color = 0xffFFCC80;
        data.add(spinItem9);
        ////////////////////////

        SpinWheeltem spinItem10 = new SpinWheeltem();
        spinItem10.topText = "1000";
        spinItem10.icon = R.drawable.test5;
        spinItem10.color = 0xffFFE0B2;
        data.add(spinItem10);

        SpinWheeltem spinItem11 = new SpinWheeltem();
        spinItem11.topText = "2000";
        spinItem11.icon = R.drawable.test8;
        spinItem11.color = 0xffFFE0B2;
        data.add(spinItem11);

        SpinWheeltem spinItem12 = new SpinWheeltem();
        spinItem12.topText = "3000";
        spinItem12.icon = R.drawable.test4;
        spinItem12.color = 0xffFFE0B2;
        data.add(spinItem12);

        spinWheelView.setData(data);
        spinWheelView.setRound(getRandomRound());
    }

    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size() - 1) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

}

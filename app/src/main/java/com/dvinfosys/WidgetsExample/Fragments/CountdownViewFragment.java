package com.dvinfosys.WidgetsExample.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dvinfosys.WidgetsExample.R;
import com.dvinfosys.widgets.CountdownView.CountdownView;

public class CountdownViewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_countdown_view, container, false);

        CountdownView mCvCountdownViewTest1 = v.findViewById(R.id.cv_countdownViewTest1);
        mCvCountdownViewTest1.setTag("test1");
        long time1 = (long) 5 * 60 * 60 * 1000;
        mCvCountdownViewTest1.start(time1);

        CountdownView mCvCountdownViewTest2 = v.findViewById(R.id.cv_countdownViewTest2);
        mCvCountdownViewTest2.setTag("test2");
        long time2 = (long) 30 * 60 * 1000;
        mCvCountdownViewTest2.start(time2);

        CountdownView cvCountdownViewTest211 = v.findViewById(R.id.cv_countdownViewTest211);
        cvCountdownViewTest211.setTag("test21");
        long time211 = (long) 30 * 60 * 1000;
        cvCountdownViewTest211.start(time211);

        CountdownView mCvCountdownViewTest21 = v.findViewById(R.id.cv_countdownViewTest21);
        mCvCountdownViewTest21.setTag("test21");
        long time21 = (long) 24 * 60 * 60 * 1000;
        mCvCountdownViewTest21.start(time21);

        CountdownView mCvCountdownViewTest22 = v.findViewById(R.id.cv_countdownViewTest22);
        mCvCountdownViewTest22.setTag("test22");
        long time22 = (long) 30 * 60 * 1000;
        mCvCountdownViewTest22.start(time22);

        CountdownView mCvCountdownViewTest3 = v.findViewById(R.id.cv_countdownViewTest3);
        long time3 = (long) 9 * 60 * 60 * 1000;
        mCvCountdownViewTest3.start(time3);

        CountdownView mCvCountdownViewTest4 = v.findViewById(R.id.cv_countdownViewTest4);
        long time4 = (long) 150 * 24 * 60 * 60 * 1000;
        mCvCountdownViewTest4.start(time4);

        CountdownView cv_convertDaysToHours = v.findViewById(R.id.cv_convertDaysToHours);
        // long timeConvertDaysToHours = (long) 150 * 24 * 60 * 60 * 1000;
        cv_convertDaysToHours.start(time4);

        CountdownView mCvCountdownViewTest6 = v.findViewById(R.id.cv_countdownViewTest6);
        long time6 = (long) 2 * 60 * 60 * 1000;
        mCvCountdownViewTest6.start(time6);

        final CountdownView mCvCountdownViewTest5 = v.findViewById(R.id.cv_countdownViewTest5);
        new AsyncTask<Void, Long, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                long time = 0;
                while (true) {
                    try {
                        Thread.sleep(1000);
                        time += 1000;
                        publishProgress(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            protected void onProgressUpdate(Long... values) {
                super.onProgressUpdate(values);
                mCvCountdownViewTest5.updateShow(values[0]);
            }
        }.execute();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("CountdownView Example");
    }

}

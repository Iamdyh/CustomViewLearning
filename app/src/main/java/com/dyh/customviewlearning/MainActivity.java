package com.dyh.customviewlearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.dyh.customviewlearning.bean.AbilityBean;
import com.dyh.customviewlearning.bean.PieData;
import com.dyh.customviewlearning.customview.CustomAbilityMapView;
import com.dyh.customviewlearning.customview.CustomBaseView;
import com.dyh.customviewlearning.customview.CustomPieView;
import com.dyh.customviewlearning.customview.CustomTitleView;
import com.dyh.customviewlearning.customview.CustomVolumControlBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        /*CustomPieView view = new CustomPieView(this);

        setContentView(view);

        ArrayList<PieData> datas = new ArrayList<>();
        PieData pieData = new PieData("Android", 60);
        PieData pieData2 = new PieData("Java", 40);
        PieData pieData3 = new PieData("iOS", 30);
        PieData pieData4 = new PieData("C/C++", 20);
        PieData pieData5 = new PieData("PHP", 20);
        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);
        view.setData(datas);*/

        //setContentView(R.layout.activity_customtitleview);
        //setContentView(R.layout.activity_customimagetitleview);
        //setContentView(R.layout.activity_customcircleprogressbarview);

        //setContentView(R.layout.activity_customvolumtorview);

        CustomAbilityMapView view = new CustomAbilityMapView(this);
        view.setData(new AbilityBean(10,20,40,80,90,30,50));
        setContentView(view);
    }
}

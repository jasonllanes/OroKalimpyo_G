package sldevs.cdo.orokalimpyo.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import sldevs.cdo.orokalimpyo.R;
import sldevs.cdo.orokalimpyo.firebase.firebase_crud;

public class home_fragment extends Fragment {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView tvName,tvRecycable,tvBiodegradable,tvResidual,tvSpecialWaste;
    Button btnShow;
    ProgressBar loading;
    firebase_crud fc;
    AnyChartView anyChartView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        loading = v.findViewById(R.id.progress_bar);

        anyChartView = v.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(v.findViewById(R.id.progress_bar));

        loading.setVisibility(View.GONE);


        fc = new firebase_crud();

        tvName = v.findViewById(R.id.tvName);
        tvRecycable = v.findViewById(R.id.tvRecycable);
        tvBiodegradable = v.findViewById(R.id.tvBiodegradable);
        tvResidual = v.findViewById(R.id.tvResidual);
        tvSpecialWaste = v.findViewById(R.id.tvSpecialWaste);

        btnShow = v.findViewById(R.id.btnShow);



        fc.retrieveName(getActivity(),getContext(),mAuth.getUid(),tvName);
        fc.retrieveTotalContribution(getActivity(),getContext(), mAuth.getUid(), tvRecycable,tvBiodegradable,tvResidual,tvSpecialWaste);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anyChartView.setVisibility(View.VISIBLE);
                loading.setVisibility(View.VISIBLE);
                btnShow.setVisibility(View.GONE);
                Pie pie = AnyChart.pie();

                pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                    @Override
                    public void onClick(Event event) {
                        Toast.makeText(getContext(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
                    }
                });

                List<DataEntry> data = new ArrayList<>();
            data.add(new ValueDataEntry("Recyclable", Double.parseDouble(tvRecycable.getText().toString())));
            data.add(new ValueDataEntry("Biodegradable", Double.parseDouble(tvBiodegradable.getText().toString())));
            data.add(new ValueDataEntry("Residual", Double.parseDouble(tvResidual.getText().toString())));
            data.add(new ValueDataEntry("Special Waste", Double.parseDouble(tvSpecialWaste.getText().toString())));
//                data.add(new ValueDataEntry("Recyclable", 1.0));
//                data.add(new ValueDataEntry("Biodegradable", 2.0));
//                data.add(new ValueDataEntry("Residual", 55.0));
//                data.add(new ValueDataEntry("Special Waste", 23.0));


                pie.data(data);


                pie.labels().position("outside");

                pie.legend().title().enabled(true);
                pie.legend().title()
                        .text("Legend")
                        .padding(0d, 0d, 10d, 0d);

                pie.legend()
                        .position("center-bottom")
                        .itemsLayout(LegendLayout.HORIZONTAL)
                        .align(Align.CENTER);

                anyChartView.setChart(pie);
            }
        });






        return v;

    }
}
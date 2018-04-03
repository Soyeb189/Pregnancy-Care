package com.muktadir.pregnancycare;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Status extends AppCompatActivity {

    EditText heightInput, weightInput;
    TextView bmiResult;
    Button calculateBmi, recordBmi;
    LinearLayout graphLayout;
    private LineChart bmiChart;
    private Double BMIRating;
    private String BMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        heightInput = (EditText)findViewById(R.id.bmi_height);
        weightInput = (EditText)findViewById(R.id.bmi_weight);
        bmiResult = (TextView)findViewById(R.id.bmi_output);
        calculateBmi = (Button)findViewById(R.id.calculate_bmi);
        recordBmi = (Button)findViewById(R.id.add_to_record_bmi);
        graphLayout = (LinearLayout)findViewById(R.id.graph_layout);


        bmiChart = (LineChart)findViewById(R.id.bmi_chart);
        bmiChart.setDragEnabled(true);
        bmiChart.setScaleEnabled(false);

        recordBmi.setVisibility(View.GONE);
        graphLayout.setVisibility(View.GONE);

        //BMI graph In=mplementation
        SharedPreferences bmiData = getSharedPreferences("bmiRecord", Context.MODE_PRIVATE);
        String bmiString = bmiData.getString("bmiString","");

        if(bmiString!=""){
            graphLayout.setVisibility(View.VISIBLE);
            setBmiGraph();
        }



        calculateBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking input
                if(!heightInput.getText().toString().equals("")&&!weightInput.getText().toString().equals("")){
                    Double height = Double.parseDouble(heightInput.getText().toString());
                    Double weight = Double.parseDouble(weightInput.getText().toString());

                    //validating data
                    if(height>=106&&height<213&&weight>=25&&weight<120){
                        BMIRating=weight/((height/100)*(height/100));
                        if(BMIRating>=100||BMIRating<=10){
                            Toast.makeText(Status.this, "Invalid Input!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{
                            BMI=new DecimalFormat("##.##").format(BMIRating);
                            if(BMI.length()==4){
                                BMI=BMI+"0";
                            }
                            if(BMIRating<18.5)
                            {
                                bmiResult.setText("Underweight" + "("+BMI+")");
                            }
                            else if(BMIRating>=18.5&&BMIRating<=24.9)
                            {
                                bmiResult.setText("Normal weight"+"("+BMI+")");
                            }
                            else if(BMIRating>=25.0&&BMIRating<=29.9)
                            {
                                bmiResult.setText("Overweight"+"("+BMI+")");
                            }
                            else if(BMIRating>=30.0)
                            {
                                bmiResult.setText("Obese"+"("+BMI+")");
                            }
                            recordBmi.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        Toast.makeText(Status.this, "Invalid Input!", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(Status.this, "Please enter both height & weight!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        recordBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting Date
                Calendar cal=Calendar.getInstance();
                SimpleDateFormat month_date = new SimpleDateFormat("MMM-dd");
                String month_name = month_date.format(cal.getTime());

                String entry = month_name+BMI;

                SharedPreferences bmiData = getSharedPreferences("bmiRecord", Context.MODE_PRIVATE);
                SharedPreferences.Editor bmiEditor = bmiData.edit();

                String bmiString = bmiData.getString("bmiString","");

                if(bmiString==""){
                    bmiEditor.putString("bmiString",entry);
                    bmiEditor.commit();
                }
                else{
                    if(bmiString.length()==165){
                        bmiString=bmiString.substring(11,165)+entry;
                    }
                    else {
                        bmiString=bmiString+entry;
                    }
                    bmiEditor.putString("bmiString", bmiString);
                    bmiEditor.commit();
                }
                recordBmi.setVisibility(View.GONE);
                Toast.makeText(Status.this, "Data Successfully recorded!", Toast.LENGTH_SHORT).show();
                graphLayout.setVisibility(View.VISIBLE);
                setBmiGraph();
            }
        });
    }

    private void setBmiGraph() {
        String[] bmiDates = new String[15];
        ArrayList<Entry> bmiVals = new ArrayList<>();
        SharedPreferences bmiData = getSharedPreferences("bmiRecord", Context.MODE_PRIVATE);
        String bmiString = bmiData.getString("bmiString","");
        int strLength = bmiString.length();
        int i=0;
        int j=0;

        while(i<strLength){

            bmiDates[j] = bmiString.substring(i, 6+i);
            float bmiVal = Float.parseFloat(bmiString.substring(6+i, 11+i));
            bmiVals.add(new Entry(j, bmiVal));
            j++;
            i+=11;
        }

        LineDataSet bmiDataSet;
        bmiDataSet = new LineDataSet(bmiVals, "BMI");
        bmiDataSet.setColor(Color.MAGENTA);
        bmiDataSet.setLineWidth(2f);
        bmiDataSet.setValueTextColor(Color.BLUE);

        LineData bmiChartData = new LineData(bmiDataSet);
        bmiChart.setData(bmiChartData);
        bmiChart.getAxisRight().setEnabled(false);
        bmiChart.getAxisLeft().setTextSize(8f);
        bmiChart.getXAxis().setTextSize(8f);
        YAxis bmiYxxis = bmiChart.getAxisLeft();
        bmiYxxis.setAxisMaximum(35f);
        bmiYxxis.setAxisMinimum(10f);
        XAxis bpXaxis = bmiChart.getXAxis();
        bpXaxis.setValueFormatter(new xAxisValueFormatter(bmiDates));

        LimitLine upperLimit = new LimitLine(24.9f, "Overweight");
        upperLimit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        upperLimit.setLineColor(Color.GREEN);
        upperLimit.setLineWidth(2f);

        LimitLine lowerLimit = new LimitLine(18.5f, "Underweight");
        lowerLimit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        lowerLimit.setLineColor(Color.GREEN);
        lowerLimit.setLineWidth(2f);

        bmiYxxis.removeAllLimitLines();
        bmiYxxis.addLimitLine(upperLimit);
        bmiYxxis.addLimitLine(lowerLimit);
        bmiChart.animateX(1000);
    }

    //xAxis value formatter for graphs
    public class  xAxisValueFormatter implements IAxisValueFormatter {

        private String[] mValues;
        public xAxisValueFormatter(String[] values){
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }
}

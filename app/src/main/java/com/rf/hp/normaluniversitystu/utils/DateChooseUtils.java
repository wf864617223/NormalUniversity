package com.rf.hp.normaluniversitystu.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * 这是一个用来选择日期的Utils
 * Created by hp
 */
public class DateChooseUtils {
    public Calendar calendar;// 用来装日期的
    public DatePickerDialog dialog;
    Context context;
    TextView et_getData;

    public DateChooseUtils(Context context, TextView et_getData) {
        this.context = context;
        this.et_getData = et_getData;
    }

    public void getdate() {
        calendar = Calendar.getInstance();
        dialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        System.out.println("年-->" + year + "月-->"
                                + monthOfYear + "日-->" + dayOfMonth);
                        et_getData.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}

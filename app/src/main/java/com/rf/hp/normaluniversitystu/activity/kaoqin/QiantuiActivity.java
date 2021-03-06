package com.rf.hp.normaluniversitystu.activity.kaoqin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.GroundOverlay;
import com.amap.api.maps2d.model.GroundOverlayOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.activity.MainActivity;
import com.rf.hp.normaluniversitystu.bean.LoginBean;
import com.rf.hp.normaluniversitystu.bean.QiandaotuiAddBean;
import com.rf.hp.normaluniversitystu.utils.GsonTools;
import com.rf.hp.normaluniversitystu.utils.HttpContent;
import com.rf.hp.normaluniversitystu.utils.HttpUtil;
import com.rf.hp.normaluniversitystu.utils.SharePreferInfoUtils;
import com.rf.hp.normaluniversitystu.utils.T;
import com.rf.hp.normaluniversitystu.utils.Utils;
import com.rf.hp.normaluniversitystu.view.PrgDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 签退界面
 */
public class QiantuiActivity extends AppCompatActivity implements LocationSource, AMapLocationListener {

    @Bind(R.id.tv_qiandao)
    TextView tvQiandao;
    @Bind(R.id.toolbar_qiandao_qiantui)
    Toolbar toolbarQiandaoQiantui;
    @Bind(R.id.map)
    MapView mMapView;
    @Bind(R.id.et_input_code)
    EditText etInputCode;
    @Bind(R.id.btn_qiandao)
    Button btnQiandao;
    @Bind(R.id.tv_kaoqin_proname)
    TextView tvKaoqinProname;
    @Bind(R.id.tv_project_teacher)
    TextView tvProjectTeacher;
    @Bind(R.id.tv_project_class)
    TextView tvProjectClass;
    @Bind(R.id.tv_project_time)
    TextView tvProjectTime;

    private AMap aMap;
    private TextView tvAdd;
    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private String address;
    private AlertDialog myDialog = null;
    Context context;
    private double latitude;
    private double longitude;
    private String curriculumId;
    private PrgDialog prgDialog;
    private float signofflognitude;
    private float signofflatitude;
    private String signOfflatitude;
    private String signOfflongitude;
    private String TAG = "QIANTUIACTIVITY";
    private boolean countRange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_qiandao);
        //x.view().inject(this);
        ButterKnife.bind(this);
        context = QiantuiActivity.this;
        initView();
        mMapView.onCreate(savedInstanceState);
        initData();
        init();


    }

    private void setFalseMap(){
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(signofflatitude,signofflognitude),17));
        //设置图片的显示区域
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(new LatLng(signofflatitude, signofflognitude))
                .include(new LatLng(signofflatitude, signofflognitude)).build();
        GroundOverlay groundoverlay = aMap.addGroundOverlay(new GroundOverlayOptions()
                .anchor(0.5f, 0.5f).transparency(0.1f)
                .image(BitmapDescriptorFactory.fromResource(R.drawable.location_marker))
                .positionFromBounds(bounds));
        LatLng position = groundoverlay.getPosition();
    }
    private void initData() {
        Bundle bundle = getIntent().getExtras();
        curriculumId = bundle.getString("curriculumId");
        String curriculumName = bundle.getString("curriculumName") + "";
        String classroom = bundle.getString("classroom") + "";
        String teachName = bundle.getString("teachName") + "";
        String startEndTime = bundle.getString("startEndTime") + "";
        String[] splitData = startEndTime.split(" ");
        LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
        String token = resultBean.getToken();

        HttpUtil.doHttp(HttpContent.GET_QIANTUI_ADD + curriculumId + "&currDate=" + splitData[0] + "&token=" + token, null, new HttpUtil.IHttpResult() {
            @Override
            public void onSuccess(String result) {
                QiandaotuiAddBean qiandaotuiAddBean = GsonTools.getBean(result,QiandaotuiAddBean.class);
                int status = qiandaotuiAddBean.getStatus();
                if(status == 0){
                    QiandaotuiAddBean.ResultBean result1 = qiandaotuiAddBean.getResult();
                    //纬度
                    signOfflatitude = result1.getSignOfflatitude();
                    //经度
                    signOfflongitude = result1.getSignOfflongitude();
                    if(!"".equals(signOfflatitude)||!"".equals(signOfflongitude)){
                        signofflognitude = Float.parseFloat(signOfflongitude);
                        signofflatitude = Float.parseFloat(signOfflatitude);
                    }
                    if(!TextUtils.isEmpty(latitude+"")&&!TextUtils.isEmpty(longitude+"")){
                        countRange = countRange();
                        if(countRange){
                            setFalseMap();
                        }
                    }
                }else{
                    String message = qiandaotuiAddBean.getMessage();
                    if("Invalid Token".equals(message)){
                        T.ShowToast(context,"账号在其他地方登录");
                    }else{
                        T.ShowToast(context, message);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
        });
        tvKaoqinProname.setText(curriculumName);
        tvProjectClass.setText("课程："+classroom);
        tvProjectTeacher.setText("老师："+teachName);
        tvProjectTime.setText("时间："+startEndTime);
    }

    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
    }

    private void setUpMap() {
        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0,0,0,0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        /*myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色*/
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setLocationSource(this);// 设置定位监听

        // 设置地图可视缩放大小
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        //这里可以将接口的经纬度放进去
        LatLng latLng = new LatLng(signofflognitude, signofflatitude);
        MarkerOptions otMarkerOptions = new MarkerOptions();
        otMarkerOptions.position(latLng);
        otMarkerOptions.visible(true);//设置可见
        //otMarkerOptions.title("芜湖市").snippet("芜湖市：31.383755, 118.438321");//里面的内容自定义
        otMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        otMarkerOptions.draggable(true);
        aMap.addMarker(otMarkerOptions);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setZoomGesturesEnabled(false);
        aMap.getUiSettings().setScaleControlsEnabled(false);
        aMap.getUiSettings().setScrollGesturesEnabled(true);
        // aMap.setMyLocationType()
    }

    private void initView() {
        toolbarQiandaoQiantui.setTitle("");
        setSupportActionBar(toolbarQiandaoQiantui);
        toolbarQiandaoQiantui.setNavigationIcon(R.mipmap.icon_reg_back);
        toolbarQiandaoQiantui.setTitleTextColor(getResources().getColor(R.color.white));
        toolbarQiandaoQiantui.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvQiandao.setText("签退");
        etInputCode.setHint("输入签退码");
        btnQiandao.setText("签退");
        btnQiandao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginBean.ResultBean resultBean = SharePreferInfoUtils.readUserInfo(context);
                String token = resultBean.getToken();
                String studentId = resultBean.getStudentId();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String nowTime = format.format(date);
                String inputCode = etInputCode.getText().toString();
                String rollcallType = "签退";
                if(TextUtils.isEmpty(inputCode)||TextUtils.isEmpty(latitude+"")||TextUtils.isEmpty(longitude+"")){
                    T.ShowToast(context,"参数不能为空");
                    return;
                }
                HashMap<String,String> params = new HashMap<String, String>();
                params.put("studentId",studentId);
                params.put("curriculumId",curriculumId);
                params.put("rollcallTime",nowTime);
                params.put("rollcallSignCode",inputCode);
                if(countRange){
                    params.put("rollcallLongitude",signOfflongitude+"");
                    params.put("rollcallLatitude",signOfflatitude+"");
                }else{
                    params.put("rollcallLongitude",longitude+"");
                    params.put("rollcallLatitude",latitude+"");
                }

                params.put("rollcallType",rollcallType);
                params.put("token",token);
                prgDialog = new PrgDialog(context);
                HttpUtil.doHttp(HttpContent.SAVE_QIANDAO_INFO, params, new HttpUtil.IHttpResult() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            int status = jsonObject.optInt("status");
                            if(status == 0){
                                JSONObject jsonObject1 = jsonObject.optJSONObject("result");
                                String rollcallStudentType = jsonObject1.optString("rollcallStudentType");
                                String rollcallStudentTime = jsonObject1.optString("rollcallStudentTime");
                                prgDialog.closeDialog();
                                myDialog = new AlertDialog.Builder(context, R.style.CustomProgressDialog).create();
                                myDialog.show();
                                Window alertWindow = myDialog.getWindow();
                                WindowManager.LayoutParams lParams = alertWindow.getAttributes();
                                myDialog.getWindow().setContentView(R.layout.alertdialog_kaoqin_qiandao);
                                myDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                                TextView tvStatus = (TextView) myDialog.getWindow().findViewById(R.id.tv_dialog_qianstatus);
                                tvStatus.setText("签退结果:"+rollcallStudentType);
                                TextView tvQianTime = (TextView) myDialog.getWindow().findViewById(R.id.tv_dialog_qiantime);
                                /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Date curDate = new Date(System.currentTimeMillis());//获取当前时间*/
                                tvQianTime.setText("签退时间:" + rollcallStudentTime);
                                myDialog.getWindow().findViewById(R.id.btn_alert_ok).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent = new Intent(context, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }else{
                                String message = jsonObject.optString("message");
                                prgDialog.closeDialog();
                                if("Invalid Token".equals(message)){
                                    T.ShowToast(context,"账号在其他地方登录");
                                }else{
                                    T.ShowToast(context, message);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        prgDialog.closeDialog();
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        T.ShowToast(context,ex.getMessage());
                        prgDialog.closeDialog();
                    }
                });

            }
        });
    }

    /**
     * 判断定位的经纬度和从服务器上取到的经纬度的距离
     *
     */
    private boolean countRange(){
        double range = 0;
        if(!signOfflatitude.equals("")&&!signOfflongitude.equals("")&&!TextUtils.isEmpty(latitude+"")&&!TextUtils.isEmpty(longitude+"")){
            range = Utils.GetDistance(signofflognitude, signofflatitude, longitude, latitude);
        }
        Log.i(TAG,"距离为 range="+range);
        int lenght = SharePreferInfoUtils.readLenght(context);
        if(range>lenght*5){
            //返回true表示要使用服务器的地址
            return true;
        }
        //返回false表示要使用定位的地址
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        ButterKnife.unbind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0) {

                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                int locationType = aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //获取纬度
                latitude = aMapLocation.getLatitude();
                //获取经度
                longitude = aMapLocation.getLongitude();
                float accuracy = aMapLocation.getAccuracy();//获取精度信息
                System.out.println("定位来源" + locationType + "纬度" + latitude + "经度" + longitude + "精度信息" + accuracy);
                address = aMapLocation.getAddress();
                System.out.println("地址" + address);
                mlocationClient.stopLocation();
                if(!TextUtils.isEmpty(signOfflatitude)&&!TextUtils.isEmpty(signOfflongitude)){
                    countRange = countRange();
                    if(countRange){
                        setFalseMap();
                    }
                }
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                //Toast.makeText(QiantuiActivity.this, errText, Toast.LENGTH_SHORT).show();
                Log.e("AmapErr", errText);
                System.out.println("定位失败"+errText);
            }
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(getApplication());
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {

        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}

package com.rf.hp.normaluniversitystu.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.rf.hp.normaluniversitystu.R;
import com.rf.hp.normaluniversitystu.utils.T;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 学习界面
 * Created by hp on 2016/7/15.
 */
//@ContentView(R.layout.frag_learn)
public class LearnFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @Bind(R.id.btn_model1)
    RadioButton btnModel1;
    @Bind(R.id.btn_model2)
    RadioButton btnModel2;
    @Bind(R.id.rg_learn)
    RadioGroup rgLearn;

    private InfoShareFragment infoShareFragment;
    private OnLineLearnFragment onLineLearnFragment;
    private OnFragmentInteractionListener mListener;
    private FragmentTransaction transaction;


    public LearnFragment() {

    }

    public static LearnFragment newInstance(String param1, String param2) {
        LearnFragment learnFragment = new LearnFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        learnFragment.setArguments(args);
        return learnFragment;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //View view = x.view().inject(this, inflater, container);
        View view = inflater.inflate(R.layout.frag_learn, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        transaction = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        infoShareFragment = new InfoShareFragment();
        onLineLearnFragment = new OnLineLearnFragment();
        //FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fl_show_frag,infoShareFragment);
        transaction.add(R.id.fl_show_frag,onLineLearnFragment);
        transaction.commit();
        transaction.hide(infoShareFragment);
        transaction.hide(onLineLearnFragment);
        btnModel1.setChecked(true);
        if (btnModel1.isChecked()) {
            transaction = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            //infoShareFragment = new InfoShareFragment();
            //transaction.replace(R.id.fl_show_frag, infoShareFragment);
            transaction.show(infoShareFragment);
            transaction.hide(onLineLearnFragment);
            transaction.commit();
        }
        rgLearn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.btn_model1:
                        transaction = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        //infoShareFragment = new InfoShareFragment();
                        //transaction.replace(R.id.fl_show_frag, infoShareFragment);
                        transaction.show(infoShareFragment);
                        transaction.hide(onLineLearnFragment);
                        transaction.commit();
                        break;
                    case R.id.btn_model2:
                        transaction = getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                        //onLineLearnFragment = new OnLineLearnFragment();
                        //transaction.replace(R.id.fl_show_frag, onLineLearnFragment);
                        transaction.show(onLineLearnFragment);
                        transaction.hide(infoShareFragment);
                        transaction.commit();
                        break;
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}

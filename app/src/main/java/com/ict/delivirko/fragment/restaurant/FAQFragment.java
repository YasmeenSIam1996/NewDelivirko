package com.ict.delivirko.fragment.restaurant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.ResponseQuestions;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Module.restaurant.Questions;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;
import com.ict.delivirko.adapter.FAQAdapter;

import java.util.List;
import java.util.Vector;

public class FAQFragment extends Fragment {

    RecyclerView rvFaq;
    FAQAdapter adapter;
    List<Questions> list;

    public FAQFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);


        list = new Vector<>();

        rvFaq = view.findViewById(R.id.rvFaq);
        adapter = new FAQAdapter(getContext(), list);
        rvFaq.setAdapter(adapter);
        rvFaq.setLayoutManager(new LinearLayoutManager(getActivity()));
        Questions();
        return view;
    }


    public void Questions() {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().Questions(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseQuestions responseQuestions = (ResponseQuestions) result;
                if (responseQuestions != null) {
                    if (responseQuestions.isStatus()) {
                        list.clear();
                        list.addAll(responseQuestions.getQuestions());
                        adapter.notifyDataSetChanged();
                    } else {
                        Constants.showDialog(getActivity(), responseQuestions.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Object result) {
                if (result != null) {
                    ResponseError responseError = (ResponseError) result;
                    Constants.showDialog(getActivity(), responseError.getMessage());

                }
            }

            @Override
            public void onFinish() {
                Constants.removeProgressDialog();
            }

            @Override
            public void OnError(String message) {
                Constants.showDialog(getActivity(), message);
            }
        });
    }


}

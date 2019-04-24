package com.ict.delivirko.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ict.delivirko.API.ResponseConditions;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConditionsFragment extends Fragment {

    private TextView tvConditions;

    public ConditionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conditions, container, false);
        tvConditions = view.findViewById(R.id.tvConditions);
        Conditions();
        return view;
    }

    public void Conditions() {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().Conditions(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseConditions responseConditions = (ResponseConditions) result;
                if (responseConditions != null) {
                    if (responseConditions.isStatus()) {

                        tvConditions.setText(responseConditions.getConditions().getValue());
                    } else {
                        Constants.showDialog(getActivity(), responseConditions.getMessage());
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

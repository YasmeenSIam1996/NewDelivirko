package com.ict.delivirko.fragment.restaurant;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ict.delivirko.API.ResponseContact;
import com.ict.delivirko.API.ResponseError;
import com.ict.delivirko.API.UserAPI;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.R;
import com.ict.delivirko.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {
    private TextView tvContactMobile, tvContactEmail, tvContactWhats;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        tvContactMobile = view.findViewById(R.id.tvContactMobile);
        tvContactEmail = view.findViewById(R.id.tvContactEmail);
        tvContactWhats = view.findViewById(R.id.tvContactWhats);
        Contacts();
        tvContactMobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT < 23) {
                    phoneCall(getActivity(), "" + tvContactMobile.getText().toString());
                } else {
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                        phoneCall(getActivity(), "" + tvContactMobile.getText().toString());
                    } else {
                        final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                        //Asking request Permissions
                        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 9);
                    }
                }
            }
        });
        tvContactEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                /* Create the Intent */
                final Intent emailIntent = new Intent(Intent.ACTION_SEND);

                /* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{tvContactEmail.getText().toString()});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Text");

                /* Send it off to the Activity-Chooser */
                getActivity().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        });
        tvContactWhats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contact = tvContactWhats.getText().toString(); // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = getActivity().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(getActivity(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    public void Contacts() {
        Constants.showSimpleProgressDialog(getActivity(), getResources().getString(R.string.Loading));

        new UserAPI().Contact(new UniversalCallBack() {
            @Override
            public void onResponse(Object result) {
                ResponseContact responseContact = (ResponseContact) result;
                if (responseContact != null) {
                    if (responseContact.isStatus()) {
                        tvContactMobile.setText(responseContact.getContactsList().get(1).getValue());
                        tvContactEmail.setText(responseContact.getContactsList().get(2).getValue());
                        tvContactWhats.setText(responseContact.getContactsList().get(0).getValue());
                    } else {
                        Constants.showDialog(getActivity(), responseContact.getMessage());
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


    private void phoneCall(Context context, String telNum) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + telNum));
            context.startActivity(callIntent);
        } else {
            Constants.showDialog((Activity) context, getResources().getString(R.string.permission));
        }
    }

}

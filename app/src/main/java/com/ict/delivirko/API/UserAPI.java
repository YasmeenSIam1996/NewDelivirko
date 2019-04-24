package com.ict.delivirko.API;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ict.delivirko.App.ApplicationController;
import com.ict.delivirko.Interfaces.UniversalCallBack;
import com.ict.delivirko.Interfaces.UniversalStringCallBack;
import com.ict.delivirko.Module.restaurant.MakeOrder;
import com.ict.delivirko.Utils.Constants;

import java.util.HashMap;
import java.util.Map;


public class UserAPI {

    public void Login(final String URL, final String phone, final String lat, final String lng, final String Password, final UniversalCallBack callBack) {
        String url = URL;
        Log.d("LOGIN_DRIVER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("LOGIN_DRIVER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseSign responseObject = gson.fromJson(response.toString(), ResponseSign.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lang", ApplicationController.langNum + "");
                params.put("phone", phone);
                params.put("password", Password);

                if (URL.equals(Constants.LOGIN_DRIVER)) {
                    params.put("lat", lat);
                    params.put("lng", lng);
                }

                return params;
            }

        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void ForgetPass(final String email, final UniversalCallBack callBack) {
        String url = Constants.FORGET_PASS;
        Log.d("FORGET_PASS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FORGET_PASS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);

                return params;
            }

        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void setFcm(final String fcm_token, final UniversalCallBack callBack) {
        String url = Constants.SET_FCM;
        Log.d("fcm_token: ", url);
        Log.d("fcm_token: ", fcm_token);

        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("fcm_token: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fcm_token", fcm_token);

                return params;
            }

        };

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void logout(final UniversalCallBack callBack) {
        String url = Constants.LOGOUT;
        Log.d("LOGOUT: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("SET_FCM_TOKEN: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void companyData(final UniversalCallBack callBack) {
        String url = Constants.COMPANY_DATA;
        Log.d("COMPANY_DATA: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("COMPANY_DATA: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseNearestCompany responseObject = gson.fromJson(response.toString(), ResponseNearestCompany.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer1", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void driverData(final UniversalCallBack callBack) {
        String url = Constants.DRIVER_DATA;
        Log.d("DRIVER_DATA: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DRIVER_DATA: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseNearestDriver responseObject = gson.fromJson(response.toString(), ResponseNearestDriver.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);

                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                Log.d("DRIVER_DATA", bearer);

                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void createCompanyOrder(final UniversalCallBack callBack) {
        String url = Constants.CREATE_ORDER;
        Log.d("CREATE_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CREATE_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOrderTemp responseObject = gson.fromJson(response.toString(), ResponseOrderTemp.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void cancelOrder(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.CANCEL_ORDER;
        Log.d("CANCEL_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CANCEL_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void myOrders(final String status, final String week, final UniversalCallBack callBack) {
        String url = Constants.MY_ORDER;
        Log.d("MY_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MY_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOrders responseObject = gson.fromJson(response.toString(), ResponseOrders.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("week", week);
                params.put("status", status);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void orderDetails(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.ORDER_DETAILS;
        Log.d("ORDER_DETAILS: ", url);
        Log.d("ORDER_DETAILS: ", order_id);

        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ORDER_DETAILS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOrderDetails responseObject = gson.fromJson(response.toString(), ResponseOrderDetails.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void Contact(final UniversalCallBack callBack) {
        String url = Constants.CONTACT;

        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CONTACT: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseContact responseObject = gson.fromJson(response.toString(), ResponseContact.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void Questions(final UniversalCallBack callBack) {
        String url = Constants.QUESTIONS;

        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("QUESTIONS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseQuestions responseObject = gson.fromJson(response.toString(), ResponseQuestions.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void FreeTrips(final UniversalCallBack callBack) {
        String url = Constants.FREE_TRIPS;

        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FREE_TRIPS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseFreeTrips responseObject = gson.fromJson(response.toString(), ResponseFreeTrips.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void getOrderData(final UniversalCallBack callBack) {
        String url = Constants.GET_COMPANY_DATA;
        Log.d("GET_COMPANY_DATA: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET_COMPANY_DATA: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseMakeOrder responseObject = gson.fromJson(response.toString(), ResponseMakeOrder.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    Log.e("GET_COMPANY_DATA", e.getMessage());
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void makeOrder(final MakeOrder makeOrder, final UniversalCallBack callBack) {
        String url = Constants.MAKE_ORDER;
        Log.d("MAKE_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("MAKE_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOrderId responseObject = gson.fromJson(response.toString(), ResponseOrderId.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("order_id", makeOrder.getOrder_id());
                params.put("name", makeOrder.getName());
                params.put("phone", makeOrder.getPhone());
                params.put("place", makeOrder.getPlace());
                params.put("street", makeOrder.getStreet());
                params.put("building_no", makeOrder.getBuilding_no());
                params.put("apartment_no", makeOrder.getApartment_no());
                params.put("floor_no", makeOrder.getFloor_no());
                params.put("to_lat", makeOrder.getTo_lat());
                params.put("to_lng", makeOrder.getTo_lng());
                params.put("price", makeOrder.getPrice());
                params.put("shipping", makeOrder.getShipping());
                params.put("payment", makeOrder.getPayment());
                params.put("free_trip", makeOrder.getFree_trip());
                params.put("place_id", makeOrder.getPlace_id());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void trackingOrders(final UniversalCallBack callBack) {
        String url = Constants.TRACKING_ORDER;
        Log.d("TRACKING_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TRACKING_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseTrackingOrders responseObject = gson.fromJson(response.toString(), ResponseTrackingOrders.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void trackingOrderData(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.TRACKING_ORDER_DATA;
        Log.d("TRACKING_ORDER_DATA: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TRACKING_ORDER_DATA: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseTrackingOrderData responseObject = gson.fromJson(response.toString(), ResponseTrackingOrderData.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void trackingOrderDetails(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.TRACKING_ORDER_DETAILS;
        Log.d("TRACKING_ORDER_DETAILS", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TRACKING_ORDER_DETAILS", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseTrackingOrderDetails responseObject = gson.fromJson(response.toString(), ResponseTrackingOrderDetails.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void TimeOrderTracking(final String link, final UniversalStringCallBack callBack) {
        Log.d("TimeOrderTracking: ", link);

        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.GET, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TimeOrderTracking: ", response);
                try {
                    callBack.onFinish();
//                    Gson gson = new Gson();
                    callBack.onResponse(response);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        });

        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void AcceptOrder(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.ACCEPT_ORDER;
        Log.d("DRIVER_DATA: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DRIVER_DATA: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void RejectOrder(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.REJECT_ORDER;
        Log.d("DRIVER_DATA: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DRIVER_DATA: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void StartTravel(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.START_TRAVEL;
        Log.d("START_TRAVEL: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("START_TRAVEL: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void FinishOrder(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.FINISH_ORDER;
        Log.d("FINISH_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("FINISH_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void ReturnOrder(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.RETURN_ORDER;
        Log.d("RETURN_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RETURN_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void CancelTravel(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.CANCEL_TRAVEL;
        Log.d("CANCEL_TRAVEL: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CANCEL_TRAVEL: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void OrderOnTheWay(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.ORDER_ON_THE_WAY;
        Log.d("ORDER_ON_THE_WAY: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ORDER_ON_THE_WAY: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOnTheWay responseObject = gson.fromJson(response.toString(), ResponseOnTheWay.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void CheckStatus(final UniversalCallBack callBack) {
        String url = Constants.CHECK_STATUS;
        Log.d("CHECK_STATUS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CHECK_STATUS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObjectStatus responseObject = gson.fromJson(response.toString(), ResponseObjectStatus.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void UpdateProfile(String url, final String phone, final String email, final String newPass, final String oldPass, final UniversalCallBack callBack) {
        Log.d("CANCEL_TRAVEL: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("UPDATE_PROFILE: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseObject responseObject = gson.fromJson(response.toString(), ResponseObject.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("email", email);
                params.put("old_password", oldPass);
                params.put("new_password", newPass);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }


    public void Conditions(final UniversalCallBack callBack) {
        String url = Constants.CONDITION;
        Log.d("CONDITION: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("CONDITION: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseConditions responseObject = gson.fromJson(response.toString(), ResponseConditions.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void PilotOrderDetails(final String order_id, final UniversalCallBack callBack) {
        String url = Constants.PILOT_ORDER_DETAILS;
        Log.d("PILOT_ORDER_DETAILS: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PILOT_ORDER_DETAILS: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseMyOrderDetails responseObject = gson.fromJson(response.toString(), ResponseMyOrderDetails.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    Log.e("PILOT_ORDER_DETAILS", e.getMessage());
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("order_id", order_id);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void BillDriver(final String week, final UniversalCallBack callBack) {
        String url = Constants.DRIVER_BILL;
        Log.d("DRIVER_BILL: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DRIVER_BILL: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponsePilotBill responseObject = gson.fromJson(response.toString(), ResponsePilotBill.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("week", week);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void BillRest(final String week, final UniversalCallBack callBack) {
        String url = Constants.COMPANY_BILL;
        Log.d("COMPANY_BILL: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("COMPANY_BILL: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseBillResData responseObject = gson.fromJson(response.toString(), ResponseBillResData.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("week", week);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void ReportBillRest(final String date, final UniversalCallBack callBack) {
        String url = Constants.REPORT_BILL;
        Log.d("REPORT_BILL: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("REPORT_BILL: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseReportOrder responseObject = gson.fromJson(response.toString(), ResponseReportOrder.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("date", date);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    public void PilotOrder(final String week, final String status, final UniversalCallBack callBack) {
        String url = Constants.DRIVER_ORDER;
        Log.d("DRIVER_ORDER: ", url);
        VolleyStringRequest stringRequest = new VolleyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("DRIVER_ORDER: ", response);
                try {
                    callBack.onFinish();
                    Gson gson = new Gson();
                    ResponseOrderPilot responseObject = gson.fromJson(response.toString(), ResponseOrderPilot.class);
                    callBack.onResponse(responseObject);
                } catch (JsonSyntaxException e) {
                    callBack.OnError("Server Connection error try again later");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                callBack.onFinish();
                showMessage(error, callBack);

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("week", week);
                params.put("status", status);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
                Log.e("Bearer", sharedPreferences.getString("UserToken", ""));
                String bearer = "Bearer ".concat(sharedPreferences.getString("UserToken", ""));
                Map<String, String> headersSys = super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headersSys.remove("Authorization");
                headers.put("Accept", "application/json");
                headers.put("Authorization", bearer);
                headers.putAll(headersSys);
                return headers;
            }


        };
        VolleySingleton.getInstance().addToRequestQueue(stringRequest, "");

    }

    private void showMessage(VolleyError error, UniversalCallBack callBack) {
        String message = null;
        Log.d("onErrorResponse", error.toString() + "");
        String json = null;
        Log.d("error.getMessage()", error.getMessage() + "");
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
            callBack.OnError(message);
        } else {
            try {
                Gson gson = new Gson();
                ResponseError ErrorMsg = gson.fromJson(error.getMessage(), ResponseError.class);
                callBack.onFailure(ErrorMsg);
            } catch (JsonSyntaxException e) {
                callBack.OnError("Server Connection error try again later");
            }
        }
    }

    private void showMessage(VolleyError error, UniversalStringCallBack callBack) {
        String message = null;
        Log.d("onErrorResponse", error.toString() + "");
        String json = null;
        Log.d("error.getMessage()", error.getMessage() + "");
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
            callBack.OnError(message);
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
            callBack.OnError(message);
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
            callBack.OnError(message);
        } else {
            try {
                Gson gson = new Gson();
                ResponseError ErrorMsg = gson.fromJson(error.getMessage(), ResponseError.class);
                callBack.onFailure(ErrorMsg.getMessage());
            } catch (JsonSyntaxException e) {
                callBack.OnError("Server Connection error try again later");
            }
        }
    }

}

package com.ict.delivirko.API;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class VolleyStringRequest extends Request<String> {

    Response.Listener<String> successListener;

    public VolleyStringRequest(int method, String url, Response.Listener<String> successListener, Response.ErrorListener listener) {
        super(method, url, listener);
        this.successListener = successListener;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        try {
            String s = URLEncoder.encode(response, "ISO-8859-1");
            response = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        successListener.onResponse(response);
    }

}

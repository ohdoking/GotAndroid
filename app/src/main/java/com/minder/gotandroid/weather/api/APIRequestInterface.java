package com.minder.gotandroid.weather.api;

import java.io.IOException;
import java.net.MalformedURLException;

import com.minder.gotandroid.weather.common.PlanetXSDKConstants.HttpMethod;
import com.minder.gotandroid.weather.common.PlanetXSDKException;
import com.minder.gotandroid.weather.common.RequestBundle;
import com.minder.gotandroid.weather.common.RequestListener;
import com.minder.gotandroid.weather.common.ResponseMessage;

/***
 *
 * @author lhjung
 * @since 2012.05.25
 *
 */
interface APIRequestInterface {

    public ResponseMessage request(RequestBundle bundle) throws PlanetXSDKException;

    public ResponseMessage request(RequestBundle bundle, HttpMethod httpMethod) throws PlanetXSDKException;

    public ResponseMessage request(RequestBundle bundle, String url, HttpMethod httpMethod) throws PlanetXSDKException;

    public void request(RequestBundle bundle, RequestListener requestListener) throws PlanetXSDKException ;
    public void request(RequestBundle bundle, HttpMethod httpMethod, RequestListener requestListener) throws PlanetXSDKException ;
    public void request(RequestBundle bundle, String url, HttpMethod httpMethod, RequestListener requestListener) throws PlanetXSDKException ;


}
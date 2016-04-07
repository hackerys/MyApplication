package com.jansen.myapplication.net;

import android.content.Context;

import com.jansen.myapplication.R;

import org.xutils.http.RequestParams;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created Jansen on 2016/3/30.
 */
public class CommonParams extends RequestParams {
    private Context mContext;

    public CommonParams(String uri, Context mContext) {
        super(uri);
        this.mContext = mContext;
        this.setSslSocketFactory(createSslSocketFactory());
    }

    /**
     * 使用自己签订的ssl证书
     */
    public SSLSocketFactory createSslSocketFactory() {
        SSLContext context = null;
        try {
            KeyStore ts = KeyStore.getInstance("BKS");
            ts.load(mContext.getResources().openRawResource(R.raw.test), "123456".toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance("X509");
            tmf.init(ts);
            TrustManager[] tm = tmf.getTrustManagers();
            context = SSLContext.getInstance("SSL");
            context.init(null, tm, null);
            return context.getSocketFactory();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        if (context != null) {
            return context.getSocketFactory();
        } else {
            return null;
        }
    }

}

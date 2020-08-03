package wiki.IceCream.yuq.demo.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @program: YuQ-Mirai-Demo
 * @author: Gorsiner
 * @create: 2020-06-22 15:34
 **/
public class TrustAllTrustManager implements javax.net.ssl.TrustManager,javax.net.ssl.X509TrustManager{

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}

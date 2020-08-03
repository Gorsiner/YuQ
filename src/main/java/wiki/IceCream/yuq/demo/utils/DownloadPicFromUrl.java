package wiki.IceCream.yuq.demo.utils;

import sun.misc.BASE64Encoder;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;

/**
 * @program: YuQ-Mirai-Demo
 * @author: Gorsiner
 * @create: 2020-06-13 16:41
 **/
public class DownloadPicFromUrl {

    public static void main(String[] args) {
        File jpg = DownloadPicFromUrl.getFileByUrl("https://uploadbeta.com/api/pictures/random/?key=%E6%8E%A8%E5%A5%B3%E9%83%8E", "jpg");
        System.out.println(jpg);
    }
    //链接url下载图片
    public static File getFileByUrl(String fileUrl, String suffix) {


       ByteArrayOutputStream outStream =new ByteArrayOutputStream();

       BufferedOutputStream stream =null;

       InputStream inputStream =null;

       File file =null;

           try {

                URL imageUrl =new URL(fileUrl);

                 HttpURLConnection conn =(HttpURLConnection)imageUrl.openConnection();

                 conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

                inputStream = conn.getInputStream();

                byte[] buffer =new byte[1024];

                 int len =0;

                while( (len=inputStream.read(buffer)) != -1 ){

                 outStream.write(buffer, 0, len);

                 }

                file = File.createTempFile("pattern", "." + suffix);



                 FileOutputStream fileOutputStream =new FileOutputStream(file);

                 stream =new BufferedOutputStream(fileOutputStream);

                stream.write(outStream.toByteArray());

              } catch (Exception e) {
                       e.printStackTrace();

              } finally {

                    try {

                    if (inputStream !=null) inputStream.close();

                     if (stream !=null) stream.close();

                    outStream.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

            }
              return file;
            }
}

package classesAndManagers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.*;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;

public class AppHttpClient {
    /** The time it takes for our client to timeout */
    public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds

    /** Single instance of our HttpClient */
    private static HttpClient mHttpClient;

    /**
     * Get our single instance of our HttpClient object.
     *
     * @return an HttpClient object with connection parameters set
     */
    private static HttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new DefaultHttpClient();
            final HttpParams params = mHttpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
        }
        return mHttpClient;
    }

    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     * @param url The web address to post the request to
     * @param postParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
    public static void executeHttpPost(String url, ArrayList<NameValuePair> postParameters) throws Exception {
    	HttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
    	httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
    	client.execute(httpPost);
    	
    }
    
    /**
     * Performs an HTTP Post request to the specified url with the
     * specified parameters.
     * @param url The web address to post the request to
     * @param postParameters The parameters to send via the request
     * @return The result of the request
     * @throws Exception
     */
    public static String executeHttpPostWithReturnValue(String url, ArrayList<NameValuePair> postParameters) throws Exception {
    	HttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
    	httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
    	HttpResponse httpResponse = client.execute(httpPost);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        StringBuffer buffer = new StringBuffer("");
        String line = "";
        String NL = System.getProperty("line.separater");
        while ((line = bufferedReader.readLine()) != null) {
        	buffer.append(line + NL);
        }
        String page = buffer.toString();
        bufferedReader.close();
    	return page;
    }

    /**
     * Performs an HTTP Post request (of bitmap file) to the specified url with the
     * @param url The web address to post the request to
     * @param bitmap file to be send
     * @throws exception
     */
    public static void executeImageHttpPost(String url, Bitmap bitmapFile, String deviceID) throws Exception {
    	HttpClient client = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmapFile.compress(CompressFormat.JPEG, 100, bos);
        byte[] data = bos.toByteArray();
        ByteArrayBody bab = new ByteArrayBody(data, "screenshot.JPEG");
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("deviceID", new StringBody(deviceID));
        entity.addPart("image", bab);
        httpPost.setEntity(entity);
    	client.execute(httpPost);
    }

}

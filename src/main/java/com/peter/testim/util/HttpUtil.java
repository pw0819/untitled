package com.peter.testim.util;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Cheeatahmail API client in Java 07/2006
 * <p/>
 * In order to use this class effectively, you should read the CheetahMail API Documentation. This will tell you more about specific methods, and also
 * give you general advice for writing applications that use the CheetahMail API.
 * <p/>
 * For this specific class, you generally want to do the following:
 * <p/>
 * [Edit cc.properties to include username/password/failure directory]
 * <p/>
 * APIClient apiClient = new APIClient(); HashMap params = new HashMap; params.put(key1, value1); String response =
 * apiClient.callMethodHandler(methodName, params);
 * <p/>
 * [do something with response]
 * <p/>
 * See APIClientExamples.java for more examples.
 *
 * @author Brian Seitz, bseitz@cheetahmail.com
 */

@SuppressWarnings("deprecation")
public class HttpUtil {

    private static final ClassLoader classLoader = HttpUtil.class.getClassLoader();
    private static final String ENCODING = "utf-8";
    // private String[] fileUploadServices = { "load1", "unsub1" };
    private HttpClient client = new DefaultHttpClient();

    /**
     * Interface for callMethod that automatically passes handles numRetries. This will throw any exception back to the caller, except when retrying.
     *
     * @param name   of the service
     * @param params list of post parameters to pass
     * @throws Exception
     */
    public String callMethod(String name, Map<String, String> params) throws Exception {
        return callMethod(name, params, null);
    }

    /**
     * The real callMethod with the retryAttemptsLeft argument exposed.
     */
    public String callMethod(String uri, Map<String, String> params, File file) throws Exception {

        String response = null;
        try {
            HttpPost post = makePostMethod(uri);
            addParameters(post, params, file);
            addFilePost(post, params, file);
            HttpResponse httpResponse = client.execute(post);
            response = readResponse(httpResponse, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private void addFilePost(HttpPost post, Map<String, String> params, String[] field, File file) throws Exception {
        if (file == null)
            return;
        MultipartEntityBuilder meb = MultipartEntityBuilder.create();
        this.createParts(params, field, file, meb);
        post.setEntity(meb.build());
    }

    private void createParts(Map<String, String> params, String[] fields, File file, MultipartEntityBuilder meb) throws Exception {
        this.createParts(params, file, fields, "file", meb);
    }

    /**
     * Add the file post things to this method.
     */
    private void addFilePost(HttpPost post, Map<String, String> params, File file) throws Exception {
        if (file == null)
            return;
        MultipartEntityBuilder meb = MultipartEntityBuilder.create();
        this.createParts(params, file, "htmlfile", meb);
        post.setEntity(meb.build());
    }

    /**
     * Create all parts of multipart post.
     */
    private void createParts(Map<String, String> params, File file, MultipartEntityBuilder meb) throws Exception {
        this.createParts(params, file, "file", meb);
    }

    private void createParts(Map<String, String> params, File file, String partname, MultipartEntityBuilder meb) throws Exception {
        this.createParts(params, file, null, partname, meb);
    }

    /**
     * Create all parts of multipart post.
     */
    private void createParts(Map<String, String> params, File file, String[] fields, String partname, MultipartEntityBuilder meb) throws Exception {
        if (params != null && !params.isEmpty()) {
            Set<String> keys = params.keySet();
            for (String key : keys) {
                meb.addTextBody(key, params.get(key));
            }
        }
        if (fields != null && fields.length > 0) {
            for (String field : fields) {
                meb.addTextBody("field", field);
            }
        }
        if (file != null) {
            FileBody fileBody = new FileBody(file);
            meb.addPart(partname, fileBody);
        }
    }

    /**
     * Add all members of params to post method's parameters.
     *
     * @throws UnsupportedEncodingException
     */
    private void addParameters(HttpPost post, Map<String, String> params, File file) throws UnsupportedEncodingException {
        if (file != null)
            return;
        //
        Set<String> keys = params.keySet();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String key : keys) {
            list.add(new BasicNameValuePair(key, params.get(key)));
        }
        post.setEntity(new UrlEncodedFormEntity(list, ENCODING));
    }

    /**
     * Read method response using a stream.
     */
    private String readResponseStream(HttpResponse httpResponse) throws IOException {
        String response = EntityUtils.toString(httpResponse.getEntity(), ENCODING);
        return response;
    }

    /**
     * Read a response that possible has a file post.
     *
     * @param method
     * @param file   File post associated, or null.
     * @return String of method response
     * @throws IOException
     */
    private String readResponse(HttpResponse method, File file) throws IOException {

        int returnCode = method.getStatusLine().getStatusCode();
        if (returnCode != HttpStatus.SC_OK)
            return null;

        // If we're okay, read the response
        String response = readResponseStream(method);

        return response;
    }

    /**
     * Read a response that has no file post.
     */
    private String readResponse(HttpResponse httpResponse) throws IOException {
        return readResponse(httpResponse, null);
    }

    /**
     * Make a PostMethod using this URI. Adds some APIClient customizations, like retries.
     */
    private HttpPost makePostMethod(String uri) throws Exception {
        HttpPost post = new HttpPost(uri);
        return post;
    }
}

package PropertyManager.PM;

import okhttp3.*;

import java.io.IOException;
import static org.junit.Assert.*;

public class HttpClient {

    private int port;
    private String prefixUrl = "http://localhost:";
    private String api = "/api/v1/";
    private String baseUrl;
    private final OkHttpClient httpClient = new OkHttpClient();

    public HttpClient(int port){
        this.port = port;
        baseUrl = prefixUrl + port + api;
    }

    private Response attemptRequest(Request request) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            //The following lines are used to replicate the response, because the response
            //through okhttp is sent as a stream, and isn't stored in memory.
            ResponseBody body = response.body();
            if(body == null) {
                return null;
            }

            assertTrue(body != null); //Extra precaution, also to shut intellij up
            String bodyString = body.string();
            MediaType contentType = body.contentType();

            return response.newBuilder().body(ResponseBody.create(contentType, bodyString)).build();
        }
    }

    public Response sendGet(String suffix) throws IOException {

        Request request = new Request.Builder()
                .url(baseUrl + suffix)
                .addHeader("custom-key", "mkyong")  // add request headers
                .addHeader("User-Agent", "OkHttp Bot") // No use for our api
                .build();

        return attemptRequest(request);
    }

    public Response sendPost(String suffix, FormBody formBody) throws IOException {

        Request request = new Request.Builder()
                .url(baseUrl + suffix)
                .addHeader("User-Agent", "OkHttp Bot")
                .post(formBody)
                .build();

        return attemptRequest(request);
    }

    public Response sendPut(String suffix, FormBody formBody) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + suffix)
                .addHeader("User-Agent", "OkHttp Bot")
                .put(formBody)
                .build();

        return attemptRequest(request);
    }

    public Response sendDelete(String suffix) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + suffix)
                .addHeader("User-Agent", "OkHttp Bot")
                .delete()
                .build();
        return attemptRequest(request);
    }

}

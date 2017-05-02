package rs.ac.ftn.uns.utils;

import android.net.Uri;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkHelper {

    protected static OkHttpClient client = new OkHttpClient();
    protected static MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ;

    protected static String GITHUB_BASE_URL="https://api.github.com/search/repositories";
    protected static String PARAM_QUERY="q";

    public static String httpGetRequest(String uri) throws IOException{

        Request request = new Request.Builder().url(uri).build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    //TODO change the GITHUB_BASE_URL url, provide a JSON :)
    public static String postHttpRequest(String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(GITHUB_BASE_URL).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String buildUri(String str){
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, str).build();

        return builtUri.toString();
    }
}

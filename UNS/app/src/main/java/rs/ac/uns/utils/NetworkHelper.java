package rs.ac.uns.utils;

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


    protected static final String FETCH_DATA_BASE_URL="https://fn-mobile-api.uns.ac.rs/api/fetchbulk";
    protected static final String FETCH_DATA_EXTENSION="voter_id";

    protected static final String POST_VOTE_DATA_URL ="https://fn-mobile-api.uns.ac.rs/api/vote";

    protected static final String GET_VOTES_RESULTS_URL="https://fn-mobile-api.uns.ac.rs/api/vote/results";

    public static String fetchData(String android_id) throws IOException{

        Uri builtUri = Uri.parse(FETCH_DATA_BASE_URL).buildUpon()
                .appendQueryParameter(FETCH_DATA_EXTENSION, android_id).build();

        Request request = new Request.Builder().url(builtUri.toString()).build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    public static String getVotesResult() throws IOException{

        Request request = new Request.Builder().url(GET_VOTES_RESULTS_URL).build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    public static String postVote(String json) throws IOException{
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(POST_VOTE_DATA_URL).post(body).build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String buildVoteJSON(String candidateId, String voterId){
        String JSON =   "{" +
                            "\"candidateId\": \"" + candidateId + "\"," +
                            "\"voterId\":\"" + voterId + "\"" +
                        "}";

        return JSON;
    }
}

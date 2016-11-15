package com.crescentflare.viewletcreatorexample;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 * The API singleton to access retrofit
 */
public class Api
{
    // ---
    // Constants
    // ---

    public static final String HASH_HEADER = "content-hash";


    // ---
    // Members
    // ---

    private Retrofit retrofit;
    private OnlineJsonService onlineJson;


    // ---
    // Singleton instance
    // ---

    public static Api instance = new Api();

    private Api()
    {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new HashingInterceptor()).build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        onlineJson = retrofit.create(OnlineJsonService.class);
    }


    // ---
    // Obtain service
    // ---

    public static OnlineJsonService service()
    {
        return instance.onlineJson;
    }


    // ---
    // Online json interface
    // ---

    public interface OnlineJsonService
    {
        @GET
        Call<Map<String, Object>> onlineJson(@Url String url, @Header(HASH_HEADER) String hash);
    }


    // ---
    // Hashing interceptor
    // ---

    private static class HashingInterceptor implements Interceptor
    {
        private static String bytesToHex(byte[] bytes)
        {
            final char[] hexArray = "0123456789abcdef".toCharArray();
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++)
            {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = hexArray[v >>> 4];
                hexChars[j * 2 + 1] = hexArray[v & 0x0F];
            }
            return new String(hexChars);
        }

        @Override
        public Response intercept(Chain chain) throws IOException
        {
            String expectedHash = chain.request().header(HASH_HEADER);
            if (expectedHash != null)
            {
                Response response = chain.proceed(chain.request());
                byte[] bytes = response.body().bytes();
                try
                {
                    MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                    final byte[] digest = messageDigest.digest(bytes);
                    String responseHash = bytesToHex(digest);
                    if (responseHash.equals(expectedHash))
                    {
                        return response.newBuilder().body(ResponseBody.create(MediaType.parse("text/plain"), new byte[0])).code(204).build();
                    }
                    return response.newBuilder().body(ResponseBody.create(response.body().contentType(), bytes)).addHeader(HASH_HEADER, responseHash).build();
                }
                catch (NoSuchAlgorithmException e)
                {
                    throw new IOException(e);
                }
            }
            return chain.proceed(chain.request());
        }
    }
}

package com.crescentflare.viewletcreatorexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.crescentflare.viewletcreator.ViewletCreator;
import com.crescentflare.viewletcreator.ViewletLoader;
import com.crescentflare.viewletcreator.utility.ViewletMapUtil;

import java.lang.ref.WeakReference;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Url;

/**
 * Synchronizes (and optionally polls) a JSON file from the mock server and notify for updates
 */
public class LiveJson
{
    // ---
    // Members
    // ---

    private Map<String, Object> data = null;
    private WeakReference<LiveJsonListener> listener = null;
    private String fileName = null;
    private String currentHash = "_none_";
    private boolean obtainingData = false;
    private boolean enablePolling = false;


    // ---
    // Initialization
    // ---

    public LiveJson(String fileName)
    {
        this.fileName = fileName;
        if (this.fileName == null)
        {
            this.fileName = "";
        }
        loadData();
    }


    // ---
    // Configure
    // ---

    public boolean isPollingEnabled()
    {
        return enablePolling;
    }

    public void setPollingEnabled(boolean enablePolling)
    {
        this.enablePolling = enablePolling;
        if (enablePolling && listener != null && listener.get() != null)
        {
            loadData();
        }
    }

    public void setListener(LiveJsonListener listener)
    {
        this.listener = listener != null ? new WeakReference<>(listener) : null;
        if (enablePolling && this.listener != null && this.listener.get() != null)
        {
            loadData();
        }
    }


    // ---
    // Handle data
    // ---

    public Map<String, Object> getData()
    {
        return data;
    }

    private void loadData()
    {
        if (!obtainingData)
        {
            obtainingData = true;
            Api.service().onlineJson(Settings.instance.getServerAddress() + "/layouts_android/" + fileName, currentHash).enqueue(new Callback<Map<String, Object>>()
            {
                @Override
                public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response)
                {
                    obtainingData = false;
                    if (response.code() != 204 && response.body() != null)
                    {
                        currentHash = response.headers().get(Api.HASH_HEADER);
                        data = response.body();
                        if (data != null)
                        {
                            if (listener != null && listener.get() != null)
                            {
                                listener.get().jsonUpdated(LiveJson.this);
                            }
                        }
                    }
                    if (listener != null && listener.get() != null && enablePolling)
                    {
                        schedulePoll();
                    }
                }

                @Override
                public void onFailure(Call<Map<String, Object>> call, Throwable t)
                {
                    obtainingData = false;
                    if (listener != null && listener.get() != null)
                    {
                        listener.get().jsonFailed(LiveJson.this);
                        if (enablePolling)
                        {
                            schedulePoll();
                        }
                    }
                }

                public void schedulePoll()
                {
                    new Handler().postDelayed(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            loadData();
                        }
                    }, 1000);
                }
            });
        }
    }


    // ---
    // Update listener
    // ---

    public interface LiveJsonListener
    {
        void jsonUpdated(LiveJson liveJson);
        void jsonFailed(LiveJson liveJson);
    }
}

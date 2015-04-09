package ca.ubc.eece281.feedr;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by John on 4/8/2015.
 * Copied from com.pedropombeiro.sparkwol
 */
public class SparkServiceProvider {
    public static SparkService createSparkService(RequestInterceptor requestInterceptor) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint("https://api.spark.io")
                .setRequestInterceptor(requestInterceptor)
                .build();
        return restAdapter.create(SparkService.class);
    }
}

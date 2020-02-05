package jj.app.mobii.world.retrofit
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import jj.app.mobii.world.BuildConfig

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var retrofit: Retrofit? = null

    private val requestHeader: OkHttpClient
        get() {
            val okHttpClient = OkHttpClient()
            val builder = okHttpClient.newBuilder()
            builder.connectTimeout(60, TimeUnit.SECONDS)
            builder.readTimeout(60, TimeUnit.SECONDS)
            builder.writeTimeout(60, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(logging)
            }
            return builder.build()
        }

   public fun getClient(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ApiUrl.BASE_URL)
                .client(requestHeader)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}

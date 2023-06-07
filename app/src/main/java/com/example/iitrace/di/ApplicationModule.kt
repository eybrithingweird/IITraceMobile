package com.example.iitrace.di

import android.content.Context
import com.example.iitrace.network.data.IITraceAPI
import com.example.iitrace.util.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import javax.inject.Singleton
import javax.net.ssl.*


@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        gson: Gson,
        okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideAuthyService(retrofit: Retrofit.Builder): IITraceAPI {
        return retrofit
            .build()
            .create(IITraceAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideInterceptor(
        @ApplicationContext context: Context,
    ): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

//        val cf: CertificateFactory = CertificateFactory.getInstance("X.509")
//        val cert: InputStream = context.resources.openRawResource(R.raw.development)
//        val ca: Certificate
//        ca = try {
//            cf.generateCertificate(cert)
//        } finally {
//            cert.close()
//        }
//
//        // creating a KeyStore containing our trusted CAs
//        val keyStoreType = KeyStore.getDefaultType()
//        val keyStore = KeyStore.getInstance(keyStoreType)
//        keyStore.load(null, null)
//        keyStore.setCertificateEntry("ca", ca)
//
//        // creating a TrustManager that trusts the CAs in our KeyStore
//        val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
//        val tmf = TrustManagerFactory.getInstance(tmfAlgorithm)
//        tmf.init(keyStore)
//
//        class NullHostNameVerifier : HostnameVerifier {
//            override fun verify(hostname: String, session: SSLSession?): Boolean {
//                Log.i("RestUtilImpl", "Approving certificate for $hostname")
//                return true
//            }
//        }
//
//        val sslContext = SSLContext.getInstance("TLS")
//        sslContext.init(null, tmf.trustManagers, null)
//
//        val trustManagerFactory: TrustManagerFactory =
//            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
//        trustManagerFactory.init(null as KeyStore?)
//        val trustManagers: Array<TrustManager> = trustManagerFactory.getTrustManagers()
//        check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
//            "Unexpected default trust managers:" + Arrays.toString(
//                trustManagers
//            )
//        }
//        val trustManager: X509TrustManager = trustManagers[0] as X509TrustManager

//        val dispatcher = Dispatcher()
//        dispatcher.maxRequests = 1

        //START OF UNSAFE
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

//        return builder.build()

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .dispatcher(dispatcher)
            .retryOnConnectionFailure(false)
//            .hostnameVerifier(NullHostNameVerifier())
//            .sslSocketFactory(sslContext.socketFactory, trustManager)
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(HostnameVerifier { hostname, session -> true })
            .build()
    }

}
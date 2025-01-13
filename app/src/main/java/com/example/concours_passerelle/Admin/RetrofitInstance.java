package com.example.concours_passerelle.Admin;

import com.example.concours_passerelle.Candidate.CandidatApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "http://10.0.2.2:8082/api/notes/";
    private static final String ANNONCE_BASE_URL = "http://10.0.2.2:8082/api/annonces/";  // Annonces URL
    private static final String preinscrit_BASE_URL = "http://10.0.2.2:8082/api/inscrits/";
    private static final String detail_BASE_URL = "http://10.0.2.2:8082/PasserelleApi/candidate/";// liste preinscrits  URL
    // Declare static fields without initialization
    private static OkHttpClient client;
    private static AnnonceApi annonceApi;
    private static PreinscritApi preinscritApi;
    private static CandidatApi detailApi;


    private static NoteApi noteApi;
    private static String ANOTHER_BASE_URL = "http://10.0.2.2:8082/api/notes/filiere/";
    public static NoteApi api;

    // Initialize client, if not already done
    static OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        okhttp3.Request request = chain.request().newBuilder()
                                .addHeader("Authorization", " Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXJpaGFiMTIzQGdtYWlsLmNvbSIsImV4cCI6MTczNzU2NDIzN30.11KI0082IVW1urPep2fOAyqMUj7V7h7hdMFxOSIAMpoG-MF96enHPA2POV7rthcn1j-lqgBicycpL74ykpKFFA")
                                .build();
                        return chain.proceed(request);
                    })
                    .build();
        }
        return client;
    }

    // Initialize and get the NoteApi
    public static NoteApi getApi() {
        if (noteApi == null) {
            noteApi = createApi(BASE_URL);
        }
        return noteApi;
    }

    // Initialize and get the AnnonceApi
    public static AnnonceApi getAnnonceApi() {
        if (annonceApi == null) {
            annonceApi = createApiForAnnonce();
        }
        return annonceApi;
    }

    public static CandidatApi getDetailApi() {
        if (detailApi == null) {
            detailApi = createApiForDetails();
        }
        return detailApi;
    }


    public static PreinscritApi getPreinscritApi() {
        if (preinscritApi == null) {
            preinscritApi = createApiForPreinscrit();
        }
        return preinscritApi;
    }

    // Helper method to create Retrofit instance for NoteApi
    private static NoteApi createApi(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(NoteApi.class);
    }

    // Helper method to create Retrofit instance for AnnonceApi
    private static AnnonceApi createApiForAnnonce() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ANNONCE_BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(AnnonceApi.class);
    }
    // Helper method to create Retrofit instance for AnnonceApi
    private static PreinscritApi createApiForPreinscrit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(preinscrit_BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(PreinscritApi.class);
    }

    private static CandidatApi  createApiForDetails() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(detail_BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(CandidatApi.class);
    }



}

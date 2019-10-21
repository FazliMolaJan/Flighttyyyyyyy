package com.aliumujib.flightyy.data.remote.api.utils;

/**
 * Copy and paste coding made possible by
 *
 * https://gist.github.com/G00fY2/a67d2e724bb3929c9a89c17a6f2c5ada
 *
 *
 * provides an elegant way of handling RxJava errors instead of adding onErrorResumeNext to each remote call.
 * */


import android.util.Log;
import com.aliumujib.flightyy.data.remote.models.LufthansaApiError;
import com.google.gson.Gson;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

@SuppressWarnings("unused")
public class CustomRxJava2CallAdapterFactory extends CallAdapter.Factory {

    private final RxJava2CallAdapterFactory originalFactory;
    private final Gson gson = new Gson();

    public static CustomRxJava2CallAdapterFactory create() {
        return new CustomRxJava2CallAdapterFactory(null);
    }

    public static CallAdapter.Factory createWithScheduler(Scheduler scheduler) {
        return new CustomRxJava2CallAdapterFactory(scheduler);
    }

    private CustomRxJava2CallAdapterFactory(Scheduler scheduler) {
        if (scheduler == null) {
            originalFactory = RxJava2CallAdapterFactory.create();
        } else {
            originalFactory = RxJava2CallAdapterFactory.createWithScheduler(scheduler);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(originalFactory.get(returnType, annotations, retrofit), gson);
    }

    private static class RxCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final CallAdapter<R, Object> wrapped;
        private Gson gson;

        RxCallAdapterWrapper(CallAdapter<R, Object> wrapped, Gson gson) {
            this.wrapped = wrapped;
            this.gson = gson;
        }

        @Override
        public Type responseType() {
            return wrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Object adapt(Call<R> call) {
            Object object = wrapped.adapt(call);

            if (object instanceof Observable<?>) {
                return ((Observable<?>) object).onErrorResumeNext(
                        (ObservableSource) throwable -> Observable.error(transformException((Throwable) throwable, call)));
            } else if (object instanceof Single<?>) {
                return ((Single<?>) object).onErrorResumeNext(throwable -> Single.error(transformException(throwable, call)));
            } else if (object instanceof Completable) {
                return ((Completable) object).onErrorResumeNext(
                        throwable -> Completable.error(transformException((Throwable) throwable, call)));
            } else if (object instanceof Maybe<?>) {
                return ((Maybe<?>) object).onErrorResumeNext(
                        (MaybeSource) throwable -> Maybe.error(transformException((Throwable) throwable, call)));
            } else if (object instanceof Flowable<?>) {
                return ((Flowable<?>) object).onErrorResumeNext(throwable -> {
                    return Flowable.error(transformException(throwable, call));
                });
            }
            return object;
        }

        private Throwable transformException(Throwable throwable, Call call) {
            // We can also access the initial call.request().url() e.g. to detect which route caused an exception
            if (throwable instanceof HttpException) {
                LufthansaApiError apiError = null;
                Response<?> response = ((HttpException) throwable).response();
                if (response.errorBody() != null) {
                    try {
                        apiError = gson.fromJson(response.errorBody().string(), LufthansaApiError.class);
                    } catch (Exception e) {
                        Log.d(CustomRxJava2CallAdapterFactory.class.getSimpleName(), e.getMessage());
                    }
                    if (apiError != null) {
                        return new Throwable(apiError.getProcessingErrors().getProcessingError().getDescription());
                    }
                }
            }
            return throwable;
        }
    }
}
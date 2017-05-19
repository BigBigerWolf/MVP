# MVP
A News Demo For MVP used RxJava+Retrofit2+Dagger2。
### 简介
	对Dagger2的简单运用，配合Mvp实现最大程度解藕。
	简单封装RxJava配合Retrofit2，完成便利的网络请求。
### 封装后的网络请求
    Observable<BaseResponse<NewsInfo>> observable = httpManager.getApiService().getNews(getCacheControl(), newsType, Constant.KEY);
        return httpManager.getSubscribtion(observable, new ResponseSubscriber<NewsInfo>(callBack) {
            @Override
            public void onSuccess(NewsInfo newsInfo) {
                callBack.onSucess(newsInfo);
            }
        });

### 自定义异常，实现Rx重试机制
    /**
     * 实现Rx重试机制
     */
    private static class RetryMechanism implements Func1<Observable<? extends Throwable>, Observable<?>> {
        @Override
        public Observable<?> call(Observable<? extends Throwable> observable) {
            return observable.flatMap(new Func1<Throwable, Observable<?>>() {
                @Override
                public Observable<?> call(Throwable throwable) {
                    if (throwable instanceof ExceptionManger.ServerException) {
                        ExceptionManger.ServerException serverException = (ExceptionManger.ServerException) throwable;
                        if (serverException.code == Constant.TOKENTIMEOUT) {
                            // TODO: 2017/3/31  在这里可以启用token过期，静默登陆机制；
                        }
                    }
                    return Observable.error(throwable);
                }
            });
        }
    }
### 重写解析请求Gson工厂，处理异常
### 自定义Subscriber实现失败请求的统一处理
### 配置请求，响应[拦截打印器](https://github.com/ihsanbal/LoggingInterceptor)
### 封装debug模式的[Logger](https://github.com/orhanobut/logger)
### RecyclerView配合[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)实现简洁的adapter
	

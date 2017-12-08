# MVP
A News Demo For MVP used RxJava+Retrofit2+Dagger2。

![](https://github.com/itkong/MVP/blob/master/Pictures/p1.png) ![](https://github.com/itkong/MVP/blob/master/Pictures/p2.png) 


### 简介
	对Dagger2的简单运用，配合Mvp实现最大程度解藕。
	简单封装RxJava配合Retrofit2，完成便利的网络请求。
### 封装后的网络请求
    ApiService apiService = requestManager.createApiService(ApiService.class);
        Observable<BaseResponse<NewsInfo>> observable = apiService.getNews(newsType, Constant.KEY);
        return requestManager
                .setConnectTimeout(10)
                .setReadTimeout(10)
                .setWriteTimeout(10)
                .getDisposable(observable, new ResponseSubscriber<BaseResponse<NewsInfo>>(callBack) {
                    @Override
                    public void onSuccess(BaseResponse<NewsInfo> newsInfoBaseResponse) {
                        callBack.onSucess(newsInfoBaseResponse.getResult());
                    }
                });
### 二次处理observable
	public <T> Disposable getDisposable(Observable<BaseResponse<T>> observable, final ResponseSubscriber<BaseResponse<T>> 	responseObserver) {
		return observable.subscribeOn(Schedulers.io())
			.unsubscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.flatMap(new Function<BaseResponse<T>, Observable<BaseResponse<T>>>() {
			    @Override
			    public Observable<BaseResponse<T>> apply(@NonNull BaseResponse<T> tBaseResponse) throws Exception {
				int result = tBaseResponse.getError_code();
				if (result == SUCCESS) {
				    return Observable.just(tBaseResponse);
				} else {
				    throw new ExceptionManger.ServerException(tBaseResponse.getError_code(), tBaseResponse.getReason());
				}
			    }
			}).onErrorResumeNext(new Function<Throwable, ObservableSource<BaseResponse<T>>>() {
			    @Override
			    public ObservableSource<BaseResponse<T>> apply(@NonNull Throwable throwable) throws Exception {
				return Observable.error(throwable);
			    }
			}).subscribe(new Consumer<BaseResponse<T>>() {
			    @Override
			    public void accept(BaseResponse<T> t) throws Exception {
				if (responseObserver != null) responseObserver.onNext(t);
			    }
			}, new Consumer<Throwable>() {
			    @Override
			    public void accept(Throwable throwable) throws Exception {
				if (responseObserver != null) responseObserver.onError(throwable);
			    }
			}, new Action() {
			    @Override
			    public void run() throws Exception {
				if (responseObserver != null) responseObserver.onComplete();
			    }
			}, new Consumer<Disposable>() {
			    @Override
			    public void accept(Disposable disposable) throws Exception {
				if (responseObserver != null) responseObserver.onSubscribe(disposable);
			    }
			});
	    }
### 封装ExceptionManager抓取各种网络异常
### 自定义Observer实现失败请求的统一处理
### 自定义请求拦截器，检测请求，响应等信息
	@Override
	    public Response intercept(@NonNull Chain chain) throws IOException {
		Request originalRequest = chain.request();
		String originalUrl = originalRequest.url().toString();
		LogUtil.d(originalUrl);
		RequestBody originalRequestBody = originalRequest.body();
		/** requestParams **/
		String paramsStr = "";
		if (originalRequestBody != null) {
		    Buffer buffer = new Buffer();
		    originalRequestBody.writeTo(buffer);
		    Charset charset = Charset.forName("UTF-8");
		    paramsStr = buffer.readString(charset);
		}
		/** request--responseTime **/
		long startTime = System.nanoTime();
		Response response = chain.proceed(chain.request());
		int code = response.code();
		long chainTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);

		ResponseBody responseBody = response.body();
		if (responseBody != null) {
		    /** string()调用后会关闭response流，需重新构建response **/
		    String subtype = null;
		    ResponseBody body;
		    MediaType contentType = responseBody.contentType();
		    if (contentType != null) {
			subtype = contentType.subtype();
		    }
		    if (subtype != null && (subtype.contains("json")
			    || subtype.contains("xml")
			    || subtype.contains("plain")
			    || subtype.contains("html"))) {
			/** responseString **/
			String bodyString = responseBody.string();
			LogUtil.d(bodyString);
			body = ResponseBody.create(contentType, bodyString);
		    } else {
			return response;
		    }
		    return response.newBuilder().body(body).build();
		} else {
		    return response;
		}
	    }
### 配置请求，响应[拦截打印器](https://github.com/ihsanbal/LoggingInterceptor)
### 封装debug模式的[Logger](https://github.com/orhanobut/logger)
### RecyclerView配合[BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)实现简洁的adapter
### 解决webview造成的内存泄漏
	

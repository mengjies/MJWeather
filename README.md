# MJWeather

##MJ天气是一款天气预报软件，基于MVP架构，采用各主流框架实现。

### 开源框架
* [`Mvp` Google's official Mvp architecture project（Dagger branch）](https://github.com/googlesamples/android-architecture/tree/todo-mvp-dagger/)
* [`Dagger2`](https://github.com/google/dagger)
* [`Rxjava2`](https://github.com/ReactiveX/RxJava)
* [`RxAndroid`](https://github.com/ReactiveX/RxAndroid)
* [`RxPermissions2`](https://github.com/tbruyelle/RxPermissions)
* [`Okhttp3`](https://github.com/square/okhttp)
* [`Retrofit2`](https://github.com/square/retrofit)
* [`Gson`](https://github.com/google/gson)
* [`Litepal`](https://github.com/LitePalFramework/LitePal)
* [`Glide`](https://github.com/bumptech/glide)
* [`glide-transformations`](https://github.com/wasabeef/glide-transformations)
* [`EventBus`](https://github.com/greenrobot/EventBus)
* [`Butterknife`](https://github.com/JakeWharton/butterknife)
* [`Timber`](https://github.com/JakeWharton/timber)
* [`Logger`](https://github.com/orhanobut/logger)
* [`LeakCanary`](https://github.com/square/leakcanary)
* [`BlockCanary`](https://github.com/markzhai/AndroidPerformanceMonitor)
* [`bugly`](https://bugly.qq.com/v2/)
* [`tinker`](https://github.com/Tencent/tinker)
* [`CheckStyle`](https://github.com/checkstyle/checkstyle)
* [`FindBugs`](https://github.com/findbugsproject/findbugs)
* [`PMD`](https://github.com/pmd/pmd)
* [`Lint`](http://tools.android.com/tips/lint)
* [`StrictMode`](https://developer.android.com/reference/android/os/StrictMode.html)
* [``]()

### 项目结构

```Java
-com.mj.weather
    + common          //公共模块
    + module1         //业务1模块
    - weather         //天气预报模块
        + activity          //Activity全局的控制者，负责创建View和Presenter的实例
        + component         //Dagger构建对象图@Component模块
        + contract          //契约类，用于统一管理View和Presenter的接口
        - model             //MVP中的Model层
            + db                //数据库
            + http              //网络
            + repository        //Model层中的Data Repository模块
        + module            //Dagger@Module模块
        + presenter         //MVP中的Presenter层
        - view              //MVP中的View层
            + adapter
            + fragment
            + ...
    + module2           //业务2模块
    + ...
```
    
## 截图
<img src="screenshot/main.png" width="40%">
<img src="screenshot/citylist.png" width="40%">

<img src="screenshot/login.png" width="40%">
<img src="screenshot/singin.png" width="40%">

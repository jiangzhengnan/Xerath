English | [简体中文](./README.md) |<br />


### Xerath
Xerath is an Android Aop framework that uses the [custom Annotations]+ASM + Gradle Transform API to implement a powerful, easy to develop, and efficient way to reduce code duplication.<br />

The compiler is designed to make global changes to fulfill special requirements such as method time statistics, exception collection, interception, dynamic proxies, and so on.<br />

The core idea is based on AOP Programming, AOP is the abbreviation of Aspect Oriented Programming, meaning: Aspect Oriented Programming.<br />

This project is the application and actual combat of AOP idea on Android.<br />
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_bg.png" width="50%"/><br />

### 1.Introduction method
Once uploaded to Maven, you can clone projects and actively rely on them<br/>

### 2.Basic usage
1.Statistical method time-consuming<br/>
Add the @CalculateTime annotation to the target method<br/>
```
    @CalculateTime
    public static void CalculateTimeMethod() {
        //do something
    }
```
2.Limit method calls<br/>
Add the @Xerath_LimitCall (time = 1000L) annotation to the target method, where time is the time threshold for frequent calls<br/>
```
    @Xerath_LimitCall(time = 1000L)
    public static void doubleClick() {
        //do something
    }
```
3.Try-catch the exception<br/>
Add the @TryCatch annotation to the target method<br/>
```
    @TryCatch
    public static void tryCatchMethod() {
        int a = 1 / 0;
    }
```

### 3.Feature List (in addition)
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_func.jpg" width="30%" /><br />



</br>
## License

    Copyright 2021, Jiang Zhengnan

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

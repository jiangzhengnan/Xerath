English | [简体中文](./README.md) |<br />


### Xerath
Xerath is an Android Aop framework that uses the [custom Annotations]+ASM + Gradle Transform API to implement a powerful, easy to develop, and efficient way to reduce code duplication.<br />

The compiler is designed to make global changes to fulfill special requirements such as method time statistics, exception collection, interception, dynamic proxies, and so on.<br />

The core idea is based on AOP Programming, AOP is the abbreviation of Aspect Oriented Programming, meaning: Aspect Oriented Programming.<br />

This project is the application and actual combat of AOP idea on Android.<br />
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_bg.png" width="50%"/><br />
### Get started
Clone the project to the local directory, execute 

```
sh upload.sh
```
Can successfully run.

### introduction method
After uploading to Maven, you can now actively rely on XerathLib to your own projects.



### Feature List (in addition)
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_func.jpg" width="30%">


### Instructions
1. Limit method calls to 
 frequently
Add @xerath_limitCall (time = 1000L) annotation to target method, where time is the time threshold for frequent calls 

```
@Xerath_LimitCall(time = 1000L)
public static void doubleClick() {
//do something
}
```

 

Add @xerath_trycatch annotation to target method 

```
@TryCatch
public static void tryCatchMethod() {
int a = 1 / 0;
}
```
3. Statistical method time-consuming 

Add @xerath_calculateTime annotation to target method 

```
@CalculateTime
public static void CalculateTimeMethod() {
//do something
}
```


 

Add @xerath_collectParams 
 to the target method
```
@Xerath_CollectParams
public static String testParams(boolean boolParam, byte byteParam, char charParam, short shortParam, int intParam,  long longParam,
float floatParam, double doubleParam, String stringParam, int[] intArrParam, JSONObject json) {
String result = boolParam + " " + byteParam + " " + charParam + " " + shortParam + " " + intParam + " " +
longParam + " " + floatParam + " " + doubleParam + " " + stringParam + " " + intArrParam.length + json.toString();
return result;
}
```


Add the @xerath_poptoast annotation to the target method, where STR is the content of the Toast to be displayed 

```
@xerath_poptoast (STR = "test Toast")
public static void popToast() {
//do something
}
```

### Contribute
1. The End of the Road
2. Create a Feat_xxx branch
3. Submit code
4. Create a Pull Request

# # thanks
- [ByteX](https://github.com/bytedance/ByteX)
- [Hunter](https://github.com/Leaking/Hunter/blob/master)
- [AopArms](https://github.com/AICareless/AopArms)

## Backlog requirements
- [x] increases method to remove the function (see: https://mp.weixin.qq.com/s/npT9MW4TQWH - code fKsC_3NCQ deleted without side effects)
- [x] Adds effective control (global, package class name) range
- [x] Added the rapid deployment function for third-party applications to rapidly deploy ASM plug-ins
- [x] added package size optimization function - precompile, statistical optimization before and after the size comparison
- [x] Adds package size optimization - Deletes unused code
- [x] added packet size optimization function - method inline optimization
- [x] Added package size optimization function - static variable optimization

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

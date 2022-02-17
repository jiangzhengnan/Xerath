English | [简体中文](./README.md) |<br />


### Xerath
![Groovy](https://img.shields.io/badge/language-Groovy-green.svg)
![Java](https://img.shields.io/badge/language-Java-red.svg)
![visitors](https://visitor-badge.laobi.icu/badge?page_id=jiangzhengnan.xerath.read.me)
</br>
Xerath is a Gradle Transform with [custom annotations]+[ASM](https://asm.ow2.io/) +[Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api)
Implementation of a powerful set of Android Aop framework, easy to develop, and can effectively reduce duplication of code. 

The compiler is designed to make global changes to fulfill special requirements such as method time statistics, exception collection, interception, dynamic proxies, and so on. 

The core idea is based on AOP Programming, AOP is the abbreviation of Aspect Oriented Programming, meaning: Aspect Oriented Programming. 

This project for the use of AOP thought on Android and actual combat, functions of the standard part aspectj-based open source projects [AopArms] (https://github.com/AICareless/AopArms) < br / >
🔧 continuous development, welcome to build! 



<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_bg.png" width="50%">


### Get started
Clone this project to the local, in the root directory to open the terminal to execute the following command, you can successfully run! 

```
sh upload.sh
```


### introduction method
Once you upload to Maven, you can now actively rely on XerathLib for your own projects. 


### Feature List (in addition)
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_func.jpg" width="30%">


### Instructions
1. Limit method calls to 
 frequently
Add the @xerath_limitCall (time = 1000L) annotation to the target method, where time is the time threshold for frequent calls. 

```
@Xerath_LimitCall(time = 1000L)
public static void doubleClick() {
//do something
}
```

 

Add the @xerath_trycatch annotation to the target method. 

```
@TryCatch
public static void tryCatchMethod() {
int a = 1 / 0;
}
```


Add @xerath_methodremove annotation to the target method, where removeMethods pass in the target method to be removed. 

```
@Xerath_MethodRemove(
removeMethods = {
"android/util/Log|d|(Ljava/lang/String; Ljava/lang/String;) I",
"android/widget/Toast|makeText|(Landroid/content/Context; Ljava/lang/CharSequence; I)Landroid/widget/Toast;" .
"android/widget/Toast|()V"
}
)
public static void tryRemoveMethod(Context context) {
Log.d("nangua","nangua");
Toast.makeText(context,"",Toast.LENGTH_SHORT).show();
}
```
4. The pop-up Toast < br / >
Add the @xerath_poptoast annotation to the target method, where STR is the content of the Toast that needs to be displayed. 

```
@xerath_poptoast (STR = "test Toast")
public static void popToast() {
//do something
}
```
5. Statistical method time-consuming 

Add the @xerath_calculateTime annotation to the target method. 

```
@CalculateTime
public static void CalculateTimeMethod() {
//do something
}
```


 

Add the @xerath_CollectParams annotation to the target method. 

```
@Xerath_CollectParams
public static String testParams(boolean boolParam, byte byteParam, char charParam, short shortParam, int intParam,  long longParam,
float floatParam, double doubleParam, String stringParam, int[] intArrParam, JSONObject json) {
String result = boolParam + " " + byteParam + " " + charParam + " " + shortParam + " " + intParam + " " +
longParam + " " + floatParam + " " + doubleParam + " " + stringParam + " " + intArrParam.length + json.toString();
return result;
}
```


 


8. Call link statistics 

Add the @xerath_callchain annotation to the target method that needs to trace the calling link. 

```
@Xerath_CallChain
public void doSomeThing() {
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

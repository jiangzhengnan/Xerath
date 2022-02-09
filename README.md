简体中文 | [English](./README.en.md) |<br />
### Xerath
![Groovy](https://img.shields.io/badge/language-Groovy-green.svg)
![Java](https://img.shields.io/badge/language-Java-red.svg)
![visitors](https://visitor-badge.laobi.icu/badge?page_id=jiangzhengnan.xerath.read.me)
</br>
Xerath 是一个通过 [自定义注解]+[ASM](https://asm.ow2.io/) + [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api)
实现的一套功能强大，方便开发，并且能够有效减少重复代码的Android Aop 框架。<br/>
旨在编译器进行全局性的修改，来完成一些诸如方法耗时统计，异常收集，拦截，动态代理等特殊需求。<br/>
核心思想基于AOP编程，AOP为Aspect Oriented Programming的缩写，意为：面向切面编程。<br/>
本项目为AOP思想在Android上的运用与实战，功能部分对标基于AspectJ的开源项目[AopArms](https://github.com/AICareless/AopArms)<br/>
<br />
🔧持续开发中，欢迎共建!<br />
<br />
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_bg.png" width="50%"/><br />

### 快速上手
clone 本工程到本地以后，在根目录执行 <br />
```
sh upload.sh 
```
即可成功跑起来。

### 引入方式
待上传到Maven,目前可以主动依赖 XerathLib 到你自己的工程中<br />
<br/>

### 功能列表(补充中)
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_func.jpg" width="30%" /><br />

### 使用说明
1.限制方法频繁调用<br/>
在目标方法处增加 @Xerath_LimitCall(time = 1000L) 注解,其中time为频繁调用的时间阈值<br/>
```
    @Xerath_LimitCall(time = 1000L)
    public static void doubleClick() {
        //do something
    }
```
2.方法try-catch异常捕获<br/>
在目标方法处增加 @Xerath_TryCatch 注解<br/>
```
    @TryCatch
    public static void tryCatchMethod() {
        int a = 1 / 0;
    }
```
3.统计方法耗时<br/>
在目标方法处增加 @Xerath_CalculateTime 注解<br/>
```
    @CalculateTime
    public static void CalculateTimeMethod() {
        //do something
    }
```

4.方法入参和返回值统计<br/>
在目标方法处增加 @Xerath_CollectParams <br/>
```
    @Xerath_CollectParams
    public static String testParams(boolean boolParam, byte byteParam, char charParam, short shortParam, int intParam, long longParam,
                                    float floatParam, double doubleParam, String stringParam, int[] intArrParam, JSONObject json) {
        String result = boolParam + " " + byteParam + " " + charParam + " " + shortParam + " " + intParam + " " +
                longParam + " " + floatParam + " " + doubleParam + " " + stringParam + " " + intArrParam.length + json.toString();
        return result;
    }
```
5 .弹出Toast<br/>
在目标方法处增加@Xerath_PopToast注解,其中str为需要显示Toast的内容<br/>
```
    @Xerath_PopToast(str = "测试Toast")
    public static void popToast() {
        //do something
    }
```

### 参与贡献
1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request

## 鸣谢
- [ByteX](https://github.com/bytedance/ByteX) 
- [Hunter](https://github.com/Leaking/Hunter/blob/master)
- [AopArms](https://github.com/AICareless/AopArms)

## 待完成需求
- [x] 增加方法移除功能(参考:https://mp.weixin.qq.com/s/npT9MW4TQWH--fKsC_3NCQ 删除无副作用代码)
- [x] 增加生效控制(全局，包类名)范围 
- [x] 增加快速部署功能，可供第三方应用进行快速部署ASM插件 
- [x] 增加包size优化功能-预编译，统计优化前后大小对比
- [x] 增加包size优化功能-删除unused代码
- [x] 增加包size优化功能-方法内联优化
- [x] 增加包size优化功能-静态变量优化

### License

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



简体中文 | [English](./README.en.md) |<br />
### Xerath
Xerath 是一个通过 [自定义注解]+[ASM](https://asm.ow2.io/) + [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api)
实现。实现的一套功能强大，方便开发，并且能够有效减少重复代码的Android Aop 框架。<br/>
旨在编译器进行全局性的修改，来完成一些诸如方法耗时统计，异常收集，拦截，动态代理等特殊需求。<br/>
AOP为Aspect Oriented Programming的缩写，意为：面向切面编程。本项目为AOP思想在Android上的运用与实战，功能对标基于AspectJ的开源项目[AopArms](https://github.com/AICareless/AopArms)<br/>
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_bg.png" width="50%"/><br />

### 2.引入方式
待上传到Maven,目前可以clone项目然后主动依赖
<br/>

### 3.基本使用
1.统计方法耗时<br/>
在目标方法上增加@CalculateTime注解<br/>
```
    @CalculateTime
    public static void CalculateTimeMethod() {
        //do something
    }
```
2.try-catch异常收集
在目标方法上增加@TryCatch注解<br/>
```
    @TryCatch
    public static void tryCatchMethod() {
        int a = 1 / 0;
    }
```

### 4.功能列表(暂定)
<img src="https://github.com/jiangzhengnan/Xerath/blob/master/app/src/main/res/raw/ic_func.jpg" width="30%" /><br />

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



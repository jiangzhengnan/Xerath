./gradlew clean
# 发布核心组件
./gradlew :XerathLib:uploadArchives
# 编译
./gradlew :app:assembleDebug
# 安装
./gradlew :app:installDebug
# 推到手机
adb shell am start -n com.ng.xerath/com.ng.xerath.MainActivity


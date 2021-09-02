./gradlew clean
# 先发布core
./gradlew :XerathCore:uploadArchives
# 再发布gradle组件
./gradlew :XerathLib:uploadArchives
# 再整体build
./gradlew build
# 再运行
./gradlew installDebug


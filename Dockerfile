# ベースイメージとしてOpenJDKを使用
# FROM openjdk:17-jdk-alpine

# # 作業ディレクトリの作成
# WORKDIR /app

# # MavenでビルドしたJARファイルをコンテナにコピー
# COPY build/libs/gootscatalog-0.0.1-SNAPSHOT.war app.war

# # アプリケーションを実行
# ENTRYPOINT ["java", "-jar", "app.war"]
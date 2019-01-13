FROM openjdk:8-jdk-alpine

# ----
# Install Maven
RUN apk add --no-cache curl tar bash
ARG MAVEN_VERSION=3.3.9
ARG USER_HOME_DIR="/root"
ARG MAVEN_CONFIG_DIR=$USER_HOME_DIR/.m2
RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$MAVEN_CONFIG_DIR"
# speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
ENTRYPOINT ["/usr/bin/mvn"]

# TODO: Install appengine-sdk?
#https://storage.googleapis.com/appengine-sdks/featured/appengine-java-sdk-1.9.71.zip

# ----
# Settings
ARG SERVE_SCRIPT_PATH=docker/services/serve.sh
ARG MAVEN_SETTINGS_PATH=docker/maven/settings.xml
ARG SERVER_CONTEXT_DIR
ARG APP_DIR=/usr/src/app

# ----
# Configuration

# Copy Maven Configuration to .m2
COPY $MAVEN_SETTINGS_PATH $MAVEN_CONFIG_DIR

# Make Folders
RUN mkdir -p $APP_DIR
WORKDIR $APP_DIR

# install maven dependency packages (keep in image)
COPY $SERVER_CONTEXT_DIR/pom.xml $APP_DIR
RUN mvn -T 1C install && rm -rf target

# Copy serve.sh
COPY SERVE_SCRIPT_PATH $APP_DIR

# Expose app src files
VOLUME ["$APP_DIR/src"];

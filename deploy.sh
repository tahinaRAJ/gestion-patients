#!/bin/bash
set -e

TOMCAT_WEBAPPS="/opt/tomcat11/webapps"
APP_NAME="gestion-patients"

echo "1) Build du WAR..."
mvn -q clean package

WAR_FILE="target/${APP_NAME}.war"

if [ -d "$TOMCAT_WEBAPPS" ]; then
    cp -f "$WAR_FILE" "$TOMCAT_WEBAPPS/"
    echo "WAR deploye vers $TOMCAT_WEBAPPS"
else
    echo "Repertoire Tomcat non trouve : $TOMCAT_WEBAPPS"
    echo "WAR genere : $WAR_FILE (a copier manuellement dans webapps/)"
fi

echo ""
echo "Testez : http://localhost:8080/$APP_NAME/login"

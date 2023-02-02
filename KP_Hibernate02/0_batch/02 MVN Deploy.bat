@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set M2_HOME=c:\tools\apache-maven-3.8.5
pushd %cd%
cd ..

GOTO EXTERNAL_TOMCAT
::GOTO EMBEDDED_JETTY

:EXTERNAL_TOMCAT
::set SKIP_TESTS=-DskipTests
call %M2_HOME%\bin\mvn -f pom.xml %SKIP_TESTS% clean war:exploded tomcat7:redeploy
pause
GOTO END

:EMBEDDED_JETTY
:: *** run under an embedded Jetty server ***
call %M2_HOME%\bin\mvn -f pom.xml clean jetty:run
pause
GOTO END

:END
popd
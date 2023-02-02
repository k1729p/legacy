@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set M2_HOME=c:\tools\apache-maven-3.8.4
set CATALINA_HOME=c:\tools\apache-tomcat-9.0.20
pushd %cd%
cd ..
del /F /Q logs\*
del /F /Q %CATALINA_HOME%\logs\*
if exist %CATALINA_HOME%\conf\Catalina\localhost\kp_hibernate02.xml del /F /Q %CATALINA_HOME%\conf\Catalina\localhost\kp_hibernate02.xml
if exist %CATALINA_HOME%\webapps\kp_hibernate02 rmdir /S /Q %CATALINA_HOME%\webapps\kp_hibernate02
if exist %CATALINA_HOME%\webapps\kp_hibernate02.war del /F /Q %CATALINA_HOME%\webapps\kp_hibernate02.war
if exist %CATALINA_HOME%\work\Catalina\localhost\kp_hibernate02 rmdir /S /Q %CATALINA_HOME%\work\Catalina\localhost\kp_hibernate02
call %CATALINA_HOME%\bin\startup.bat
popd
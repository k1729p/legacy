@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-12
set M2_HOME=c:\tools\apache-maven-3.8.5
pushd %cd%
cd ..
rmdir /S /Q logs
mkdir logs
::set SKIP_TESTS=-DskipTests
call %M2_HOME%\bin\mvn clean package %SKIP_TESTS%
call %M2_HOME%\bin\mvn exec:java -Dexec.mainClass="kp.company.MainEntrance"
pause
popd
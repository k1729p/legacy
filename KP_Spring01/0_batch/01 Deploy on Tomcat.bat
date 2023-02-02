@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK1.8.0_102
set ANT_HOME=c:\tools\apache-ant-1.10.1
set BUILDFILE=..\build.xml
set TARGETS=clean deploy

call %ANT_HOME%\bin\ant.bat -buildfile %BUILDFILE% %TARGETS%
pause
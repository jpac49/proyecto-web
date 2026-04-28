@echo off
REM variables personalizadas para el ordenador de casa

REM Directorio actual (C:\temp\Tomcat)
set CATALINA_BASE=%cd%

REM Ruta real de tu JDK de Microsoft
set JAVA_HOME=C:\Program Files\Microsoft\jdk-21.0.9.10-hotspot

REM Ruta real de tu Tomcat
set CATALINA_HOME=C:\tomcat9\apache-tomcat-9.0.117

REM Actualizar el PATH para que Windows encuentre 'java' y los comandos de Tomcat
SET PATH=%JAVA_HOME%\bin;%CATALINA_HOME%\bin;%PATH%

REM Configurar CLASSPATH (Incluye el punto para la carpeta actual y la librería de servlets)
SET CLASSPATH=.;%CATALINA_HOME%\lib\servlet-api.jar;%CLASSPATH%

echo Configuración de casa cargada correctamente:
echo JAVA_HOME definido en: %JAVA_HOME%
echo CATALINA_HOME definido en: %CATALINA_HOME%
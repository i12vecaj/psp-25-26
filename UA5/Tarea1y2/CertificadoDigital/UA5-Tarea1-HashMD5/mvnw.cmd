@ECHO OFF
SETLOCAL

set "BASEDIR=%~dp0"
if "%BASEDIR:~-1%"=="\" set "BASEDIR=%BASEDIR:~0,-1%"

set "WRAPPER_DIR=%BASEDIR%\.mvn\wrapper"
set "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
set "WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"

if not exist "%WRAPPER_DIR%" mkdir "%WRAPPER_DIR%"

if not exist "%WRAPPER_JAR%" (
  powershell -NoProfile -ExecutionPolicy Bypass -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -UseBasicParsing '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'"
  if errorlevel 1 (
    echo Error: failed to download maven-wrapper.jar
    exit /b 1
  )
)

java -classpath "%WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%BASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*
ENDLOCAL

@echo off 
cls



if %1=="" ( 
    echo AYUDITA 1
    exit /b
)

if %1==-h ( 
    echo AYUDITA 2
    exit /b
)



Call :strlen %1 _length
Echo String %1 is %_length% characters long


findstr /m "%1" developers.csv

if %errorlevel%==0 (

    echo Huray! Your developer USERNAME [ %1 ] was found on developers.csv file.  Creating your username folder and branch in repo:
    echo %1\UA1
    echo %1\UA2
    echo %1\UA3
    echo %1\UA4
    echo %1\UA5

) else (

    echo Weird! Your developer USERNAME [ %1 ] is not on developers.csv file ...

)






goto:eof
:strlen  StrVar  [RtnVar]
  setlocal EnableDelayedExpansion
  set "s=#!%~1!"
  set "len=0"
  for %%N in (4096 2048 1024 512 256 128 64 32 16 8 4 2 1) do (
    if "!s:~%%N,1!" neq "" (
      set /a "len+=%%N"
      set "s=!s:~%%N!"
    )
  )
  endlocal&if "%~2" neq "" (set %~2=%len%) else echo %len%
exit /b
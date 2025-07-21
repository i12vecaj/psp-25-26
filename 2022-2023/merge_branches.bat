@echo off
cls

for /f "usebackq tokens=1-3 delims=;" %%a in ("developers.csv") do (
      echo "ID: %%a USERNAME: %%b EMAIL: %%c"
    git checkout main  
    git merge %%b
    )

rem git checkout main
rem git merge jeff/feature2
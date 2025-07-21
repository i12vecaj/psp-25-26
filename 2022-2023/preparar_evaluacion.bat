@echo off
cls

cd psp-22-23

rem Creo Rama evaluacion
git branch evaluacion
rem Cambio a Rama evaluacion
git checkout evaluacion
rem Me traigo los cambios de la rama main
git rebase main

rem for /l %%x in(START,STEP,FINISH) do (
for /l %%x in (1, 1, 27) do (
	echo Integrating dev_%%x	
	git merge origin/dev_%%x
	
	if !ERRORLEVEL! NEQ 0 (
		echo "Tratamos de arreglarlo ..."
		git add .
		
		if !ERRORLEVEL! NEQ 0 (
			for /F "tokens=*" %%m in ('git diff --name-only') do (
				git rm -f %%m
			)
			git add .
		)
		
		git commit -m "Hay que estar más atentos!" 
		echo "A ver qué sale de aquí ..."
	) else (
		echo "Go go go !"
	)
)

cd ..
echo "A CORREGIR"

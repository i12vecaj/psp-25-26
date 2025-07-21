#!/bin/bash

ACTUAL_PATH=`pwd`
REPO_PATH="/home/daniel/DEV/psp-22-23/"
BRANCHS_PREFFIX="dev_"
LAST_DEV=28

clear
figlet "GIT Merger"

cd $REPO_PATH

git pull
git branch -d evaluacion
git checkout -b evaluacion

for i in {2..27}
do
    echo "Merging branches: $BRANCHS_PREFFIX$i -> evaluacion"
    echo
    git merge --squash --no-commit origin/$BRANCHS_PREFFIX$i
    if [ $? -eq 0 ]; then
	    echo "Execution OK"
    else
	    echo "\nExecution FAILED in $BRANCHS_PREFFIX$i ... Stopping"
        git stash clear
        git checkout main
        git branch -d evaluacion
        cd $ACTUAL_PATH
        exit
    fi
done

cd $ACTUAL_PATH

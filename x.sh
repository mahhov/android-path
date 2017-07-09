#!/bin/bash

echo usage: ./x.sh n toBranch fromBranch
if test "$#" -ne 3; then
    echo "Illegal number of parameters"
	exit
fi

n=$1
branch=$2
from=$3

git co $from
git co -B ${from}.tmp

log=$(git log --pretty=oneline --abbrev-commit -n $n)
IFS=$'\n' read -rd '' -a lines <<<"$log"
declare -a commits

for (( i=0; i<$n; i++ ))
do
	commits[$i]=${lines[i]:8}
	echo ${lines[i]}
done

for (( i=0; i<$n; i++ ))
do
   git reset head~
   git stash
done

git co $2

for (( i=$n-1; i>=0; i-- ))
do
   git stash pop
   git add .
   git commit -m "${commits[$i]}"
done

git branch -d ${from}.tmp
#!/bin/bash

opt=$1
msg=$2
b=$3  #brnach

case $opt in
st)
    git status
    ;;
a)
    git add .
    ;;
cm)
    git commit -m "$msg" 
    ;;
l)
    git log --oneline 
    ;;
#ir pra brnach 
ck)
    git checkout $b 
    ;;
#veficia em qual brnache eu estou
b)
    git branch 
    ;;
p)
    git push  origin $b 
    ;;
*)
    echo "nao existe "
    ;;
esac

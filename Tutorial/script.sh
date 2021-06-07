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



#########################################################
# BUILd
#########################################################
#!/bin/bash


##################################################################################
#   PRE REQUISITOS                                                               #
##################################################################################
# Fonts
# install apt-get install figlet
# Variável de Ambiente - JAVA_HOME

set -e

clear

sh utils/titles/title_build.sh

# shellcheck disable=SC2116
cr=$(echo $'\n> ')
cr=${cr%.}

# shellcheck disable=SC2162
read -p "Deseja criar Aplicativo  [S/N]: $cr" CONTINUE

# shellcheck disable=SC1009
if [ "$CONTINUE" = "s" ]
then
    cd ..

    printf "\n\nCleaning ...\n"
    ./gradlew clean

    printf "\n\nCleaning build cache ...\n"
    ./gradlew cleanBuildCache

    printf "\n\nCreate Apk...\n"
    ./gradlew assembleDebug

    printf "\n\nCopying...\n"

    RELEASE_DIR="app/src/release"

    if [ -d "$RELEASE_DIR" ]
    then
      printf "\n\nDeleting path release...\n"
      rm -Rf "$RELEASE_DIR"
    fi

    mkdir -m777 "app/src/release" && cp -Rf "app/build/outputs/apk/debug/app-debug.apk" "app/src/release"

    printf "\n\nCreate apk is success\n"

else
  printf "\n\nOPERCAO CANCELADA"
fi








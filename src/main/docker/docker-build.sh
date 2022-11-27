#!/bin/bash

CURTAG=$1
if [ -z ${CURTAG} ]; then 
	echo "No tag name given.. takeing the last..";
	CURTAG=`git describe --abbrev=0 --tags`;
	CURTAG="${CURTAG/v/}"
	echo "... $CURTAG"
	if [ -z ${CURTAG} ]; then 
		echo "No tags exist.. create one in order to continue..";
		exit
	fi	
fi


DOCKER_IMAGE="docker.smartplatform.io/json-simulator:$CURTAG"
echo "Run build of a new docker image: $DOCKER_IMAGE"
CMD="docker build . -f Dockerfile -t $DOCKER_IMAGE"

read -n1 -p "Continue (y/n)?" choice
echo""
case "$choice" in 
  n|N ) exit;;
esac

echo "......"
echo $CMD
$CMD

read -n1 -p "Continue (y/n)?" choice
echo ""
case "$choice" in 
  y|Y ) docker push $DOCKER_IMAGE;;
  n|N ) echo "skip";;
  * ) echo "invalid";;
esac

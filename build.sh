#!/bin/bash

case $1 in
'clean') {
	./gradlew clean -q;
};;
'build') {
	./gradlew clean -q;
	./gradlew build -q -x test;
};;
'buildNeeded') {
	./gradlew clean -q;
	if [[ -n $2 ]]; then
		for module in $2; do
			./gradlew :service-$module:buildNeeded -q -x test;
		done
	else	
		./gradlew buildNeeded -q -x test;
	fi
};;
'DistTar') {
	./gradlew clean -q;
	./gradlew DistTar -q -x test;
	
};;
*) {
	./gradlew clean -q;
	echo $"Usage:$0 {clean | build | buildNeeded | DistTar | help}"
};;
esac 
#!/bin/bash\

cd /opt/workspace/java/big-data-base;
./gradlew clean :service-006:DistTar -q -x test;

cd service-006/build/distributions;
scp service-006.tar cat@node101:/home/cat;
scp service-006.tar cat@node102:/home/cat;
scp service-006.tar cat@node103:/home/cat;

cd /opt/workspace/java/big-data-base;
./gradlew clean -q -x test;

ssh cat@node101 "cd /home/cat; rm -rf service-006; tar xf service-006.tar; rm service-006.tar;"
ssh cat@node102 "cd /home/cat; rm -rf service-006; tar xf service-006.tar; rm service-006.tar;"
ssh cat@node103 "cd /home/cat; rm -rf service-006; tar xf service-006.tar; rm service-006.tar;"
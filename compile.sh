#!/bin/bash

if [ -d "target" ]; then
    rm -rf ./target
fi

# Build target for project
mkdir -p ./target/WEB-INF/classes
mkdir -p ./target/WEB-INF/lib
mkdir -p ./target/assets
mkdir -p ./target/controller

cp -R lib/* ./target/WEB-INF/lib
cp ./web.xml ./target/WEB-INF/
cp ./config.xml ./target/WEB-INF/classes/

# Copy Front-end
cp -R assets/* ./target/assets
rm -rf ./target/assets/scss
cp -R view/* ./target/
cp -R controller/* ./target/controller

# Compile all java files
find src -name '*.java' > file.txt
grep -i '.java' file.txt > sources.txt
javac -source 8 -target 8 -parameters -cp './target/WEB-INF/lib/*' -d ./target/WEB-INF/classes/ @sources.txt
if [ $? -ne 0 ]; then
    exit $?
fi

rm -f file.txt
rm -f sources.txt
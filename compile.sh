#! /bin/bash

# Compilation en ijava de tous les codes sources
javac -cp lib/*:. src/*.java -d build/ && (echo "Compilation réussie !" ; exit 0) || (echo -e "\nÉchec de compilation..." ; exit 1)

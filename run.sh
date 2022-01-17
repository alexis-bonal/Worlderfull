#! /bin/bash

# Dimensions de l'écran dans un fichier
lines=$(tput lines)
columns=$(tput cols)
echo "$lines;$columns" > ressources/dim.csv

# Éxecution du jeu
cd build
java -cp ../lib/*:. Worlderfull

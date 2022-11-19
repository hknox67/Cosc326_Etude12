# Etude 12
@author Connor Dobson
@author Ariana van Lith
@author Akshay Menon
@author Hayden Knox
@author Luka Didham

This program reads in a file containing IBM system/360-format floating points in either single or double precision,
and it converts it to a modern day IEEE 754 format that is also either single or double precision.
This programs utilises the fileinputstream and fileoutputstream to take in a file and write to a new file,
or it uses args to read a file and to output to a file.

## How the work was split
Luka and Akshay worked together to set up the file input and output systems.
Hayden had come up with the variables names and set up the prompt in the main method.
Ariana and Connor worked together on the floating number, double number and single number methods.
But everyone helped each other when they got stuck on their assigned methods.

## How to compile
To compile the program, simply type:
"javac Test.java"

## How to run
There are 2 ways to run the program, using args or using a prompt from the terminal aksing for a file

1) if using args, first put in the file name with a .bin filetype, followed by its precision number, followed by the name of the file
you want to output to with a .bin filetype, followed by its precision number.

Example: 
"java Test inputFile.bin 1 outputFile.bin 2"

2) if using the prompt from the terminal, run "java Test" and then follow the prompts and enter the information accordingly

Example:
	
Input file name: "inputFile.bin"
Select precision number: "choose 1 or 2"
Output file name: "outputFile.bin"
Select precision number: "choose 1 or 2"
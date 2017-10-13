#!/bin/bash

# Converts a from PowerPoint created PDF file into SVG files.
# (every page get's converted to one SVG file)
#
# Works only if you have Inkscape and PDFtk installed and in the environment variables!
#
#
# Originally made by Alain Pannetier - https://stackoverflow.com/a/34119481/7827128
#


# change directory into the directory where this script is
cd $(dirname "$0")

# get the pdf file name over the first given parameter
inputPdf=$1

# count the pages of the pdf
pageCnt=$(pdftk $inputPdf dump_data | grep NumberOfPages | cut -d " " -f 2)
echo "$inputPdf opened >> counted $pageCnt pages"

# convert every page to a seperate .svg file
for i in $(seq 1 $pageCnt); do
    echo "converting page $i to ${inputPdf%%.*}_${i}.svg..."
    # create for every page a pdf file
    pdftk ${inputPdf} cat $i output ${inputPdf%%.*}_${i}.pdf
    # convert the file with inkscape
    inkscape --without-gui "--file=${inputPdf%%.*}_${i}.pdf" "--export-plain-svg=${inputPdf%%.*}_${i}.svg"
    # remove the created pdf file
    rm "${inputPdf%%.*}_${i}.pdf"
done

# Do not close the window -> In case of bugs/failures show the history of everything => Debugging
read -n1 -r -p "Press any key to continue..." key

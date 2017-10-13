#!/bin/bash

# Converts a from PowerPoint created PDF file into SVG files.
# (every page get's converted to one SVG file)
#
#
# Works only if you have Inkscape and PDFtk installed and in the environment variables!
# > Inkscape: https://inkscape.org/en/release/0.92.2/
# > PDFtk: https://www.pdflabs.com/tools/pdftk-the-pdf-toolkit/
#
#
# PDF to SVG part originally made by Alain Pannetier
# > https://stackoverflow.com/a/34119481/7827128


# change directory into the directory where this script is
# > TheMarko, James Ko | https://stackoverflow.com/a/242550/7827128
cd $(dirname "$0")


# find all .pdf files and do this for every found file
# > devnull | https://stackoverflow.com/a/19852970/7827128
find -type f -name "*.pdf" | while read pdf; do

	# get the current pdf file name
	inputPdf=$pdf

	# get the pdf file name
	# > Petesh | https://stackoverflow.com/a/965072/7827128
	filename=$(basename "$pdf")
	filename="${filename%.*}"

	# count the pages of the pdf width pdftk
	pageCnt=$(pdftk $inputPdf dump_data | grep NumberOfPages | cut -d " " -f 2)
	echo "$inputPdf opened >> counted $pageCnt pages"

	# convert every page to a seperate .svg file
	for i in $(seq 1 $pageCnt); do
		echo "converting page $i to ${filename}_${i}.svg..."
		# create for every page a pdf file
		pdftk ${inputPdf} cat $i output "${filename}_${i}.pdf"
		# convert the file with inkscape
		inkscape --without-gui "--file=${filename}_${i}.pdf" "--export-plain-svg=${filename}_${i}.svg"
		# remove the created pdf file
		rm "${filename}_${i}.pdf"
	done
done


# Do not close the window -> In case of bugs/failures show the history of everything => Debugging
read -n1 -r -p "Press any key to continue..." key

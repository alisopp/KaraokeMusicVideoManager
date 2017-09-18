<?php

// var buttom = document.querySelector("button");
// var dataArtist = buttom.getAttribute("data-artist");
// var dataTitle = buttom.getAttribute("data-title");

//echo "index:", $_REQUEST['index'];
//echo "title:", $dataTitle;
//echo "artist:", $dataArtist;

$array = array("index", "title", "artist");
$number = 0

echo "<ul>"

// get the number, title and artist that was clicked
foreach (explode(",", $_REQUEST['index']) as $line) {
	echo "<li>", $array[$number], ":", $line, "</li>";
	$number++;
};

echo "</ul>"

?>
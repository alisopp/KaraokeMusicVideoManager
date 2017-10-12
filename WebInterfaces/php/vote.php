<?php

# Get url parameter
$fileName = $_GET['file'];
$type = $_GET['type'];

$path = ".";

# Get file
$str = file_get_contents($path . "/" . $fileName);
$json = json_decode($str, true);

# Get file contents
$votes = $json['votes'];

if ($type == 'inc') {
	$votes = $votes + 1;
} elseif ($type == 'dec') {
	$votes = $votes - 1;
} elseif ($type == 'get') {
	echo $votes;
	return;
}

# write new vote number
$newData = array('song'=> $json['song'], 'title'=> $json['title'], 'artist'=> $json['artist'], 'author'=> $json['author'], 'comment'=> $json['comment'], 'time'=> $json['time'], 'created-locally'=> $json['created-locally'], 'votes'=> $votes);
$newJsonString = json_encode($newData);
file_put_contents($fileName, $newJsonString);

# echo written vote number
echo $votes;

?>
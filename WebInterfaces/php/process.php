<?php
# Get current time in UNIX format
$date = new DateTime();
$current_time = $date->getTimestamp();

# Place all information in one array/dictonary
$content = array('song'=> intval($_GET["song"]), 'title'=> $_GET["title"], 'artist'=> $_GET["artist"], 'author'=> $_GET["author"], 'comment'=> $_GET["comment"], 'time'=> $current_time, 'created-locally'=> false, 'votes'=> 0);

# calculate file name -> search for all .json files
$files = array_diff(glob("*.json"), array("ipBook.json"));
$highest_number = 0;
foreach ($files as $filename) {
	$withoutExt = preg_replace('/\\.[^.\\s]{3,4}$/', '', $filename);
	echo "<script type='text/javascript'>alert('" . $filename . " and without extension " . $withoutExt . "');</script>";
	;
	if ($highest_number < intval($withoutExt)) {
		$highest_number = intval($withoutExt);
	}
}

$file_name = ($highest_number + 1) . ".json";


# Write JSON file with the timestamp as filename
$fp = fopen($file_name, 'w') or die("<script type='text/javascript'>alert('fopen failed!');location.href='../html/html_party_live.html';</script>");
fwrite($fp, json_encode($content)) or die("<script type='text/javascript'>alert('fwrite failed!');location.href='../html/html_party_live.html';</script>");
fclose($fp) or die("<script type='text/javascript'>alert('fclose failed!');location.href='../html/html_party_live.html';</script>");

# Redirect the user to the main list
# "link-begin"
echo "<script>location.href='../html/html_party_live.html';</script>";
# "link-end"
?>
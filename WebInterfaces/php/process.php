<?php
# Get current time in UNIX format
$date = new DateTime();
$current_time = $date->getTimestamp();

# Place all information in one array/dictonary
$content = array('song'=> $_GET["song"], 'title'=> $_GET["title"], 'artist'=> $_GET["artist"], 'author'=> $_GET["author"], 'comment'=> $_GET["comment"], 'time'=> $current_time, 'created-locally'=> false);

# Write JSON file with the timestamp as filename
$fp = fopen($current_time . '.json', 'w');
fwrite($fp, json_encode($content));
fclose($fp);

# Redirect the user to the main list
echo "<script> location.href='party.html'; </script>";
?>
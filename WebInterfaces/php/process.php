<?php
# Get current time in UNIX format
$date = new DateTime();
$current_time = $date->getTimestamp();

# Place all information in one array/dictonary
$content = array('song'=> intval($_GET["song"]), 'title'=> $_GET["title"], 'artist'=> $_GET["artist"], 'author'=> $_GET["author"], 'comment'=> $_GET["comment"], 'time'=> $current_time, 'created-locally'=> false, 'votes'=> 0);

# Write JSON file with the timestamp as filename
$fp = fopen($current_time . '.json', 'w') or die("<script type='text/javascript'>alert('fopen failed!');location.href='../html/html_party_live.html';</script>");
fwrite($fp, json_encode($content)) or die("<script type='text/javascript'>alert('fwrite failed!');location.href='../html/html_party_live.html';</script>");
fclose($fp) or die("<script type='text/javascript'>alert('fclose failed!');location.href='../html/html_party_live.html';</script>");

# Redirect the user to the main list
# "link-begin"
echo "<script>location.href='../html/html_party_live.html';</script>";
# "link-end"
?>
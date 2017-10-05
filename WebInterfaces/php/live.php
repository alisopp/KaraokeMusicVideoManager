<?php

# open the directory with the playlist entries and get every file but the php files
$path = "php";
$files = array_diff(scandir($path), array('..', '.',"form.php","process.php"));

# count the elements
$counter = 1;

foreach ($files as $file) {

	# get json data
	$str = file_get_contents($path . "/" . $file);
	$json = json_decode($str, true);
	$song = $json['song'];
	$title = $json['title'];
	$artist = $json['artist'];
	$author = $json['author'];
	$comment = $json['comment'];
	$time = date("Y-m-d H:i:s", $json['time']);
	$created = $json['created-locally'];

	# write live element
	echo "<div class=\"live-element\">";
	# >>> open song and artist
	echo "<div class= \"song-and-artist\">";
	echo "<div class=\"number\">" . $counter . "</div>";
	echo "<div class=\"song-title\">" . $title . "</div>";
	echo "<div class=\"song-artist\">" . "&nbsp;from " . $artist . "</div>";
	echo "</div>";
	# <<< closing upvotes
	# >>> open author and comment
	echo "<div class=\"author-and-comment\">";
	echo "<div class=\"author\">" . "<p class=\"author-text\">" . $author . "</p>" . "</div>";
	echo "<div class=\"scroll-comment\">" . "<marquee behavior=\"scroll\" direction=\"left\" scrollamount=\"5\">" . $comment . "</marquee>" . "</div>";
	echo "</div>";
	# <<< closing author and comment
	echo "</div>";

	# element number + 1
	$counter++;
}
?>
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
	# >>> open upvote counter
	echo "<div class= \"upvote-counter\">" . "##" . "</div>";
	# <<< closing upvote counter
	# >>> open song and artist
	echo "<div class= \"song-and-artist\">";
	echo "<div class=\"playlist-number\">" . "#" . $counter . "</div>";
	echo "<div class=\"song-title\">" . $title . "</div>";
	echo "<div class=\"song-artist\">" . "from " . $artist . "</div>";
	echo "</div>";
	# <<< closing song and artist
	# >>> open author and comment
	echo "<div class=\"author-and-comment\">";
	echo "<div class=\"author\">" . "<p class=\"author-text\">" . $author . "</p>" . "</div>";
	echo "<div class=\"scroll-comment\">" . "<marquee behavior=\"scroll\" direction=\"left\" scrollamount=\"5\">" . $comment . "</marquee>" . "</div>";
	echo "</div>";
	# <<< closing author and comment
	# >>> open interaction comment and vote button
	echo "<p class=\"text-beta\">" . $comment . "</p>";
	echo "<div class=\"upvotes\">" . "<div class=\"upvote-button\">" . "<button class=\"upvote-button-button\">Vote</button>" . "</div>" . "</div>";
	echo "</div>";
	# <<< closing interaction comment and vote button

	# element number + 1
	$counter++;
}
?>
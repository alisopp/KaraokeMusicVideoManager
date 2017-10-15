<?php

# open the directory with the playlist entries and get every file but the php files
$path = "./";
$files = array_diff(scandir($path), array('..', '.',"form.php","process.php", "live.php", "vote.php", "ipBook.json"));

# create array for final array and values for sorting
$allData = array();
$allTimestamps = array();
$allVotes = array();
$allAuthors = array();
$allTitles = array();
$allArtists = array();

# count the elements
$counter = 1;
# placeholder for ID/POSITION
$counterString = '~##counter##~';

# do for every found .json file
foreach ($files as $file) {

	# get json data
	$str = file_get_contents($path . "/" . $file);
	$json = json_decode($str, true);
	$song = $json['song'];
	$title = $json['title'];
	$artist = $json['artist'];
	$author = $json['author'];
	$comment = $json['comment'];
	$time = $json['time'];
	$readableTime = date("H:i", $time);
	$created = $json['created-locally'];
	$votes = $json['votes'];
	
	# get live element string data
	$liveElement = "";
	# write live element
	$liveElement = '<div class="live-element">';
	# >>> open upvote counter
	$liveElement .= '<div class="upvote-counter" id="vote' . $counterString . '">' . $votes . '</div>';
	# <<< closing upvote counter
	# >>> open song and artist
	$liveElement .= '<div class="song-and-artist">';
	$liveElement .= '<div class="playlist-number">' . '#' . $counterString . '</div>';
	$liveElement .= '<div class="song-title">' . $title . '</div>';
	$liveElement .= '<div class="song-artist">' . 'from ' . $artist . '</div>';
	$liveElement .= '</div>';
	# <<< closing song and artist
	# >>> open author and comment
	$liveElement .= '<div class="author-and-comment">';
	$liveElement .= '<div class="author">' . '<p class="author-text">' . $author . ' - ' . $readableTime . '</p>' . '</div>';
	$liveElement .= '<div class="scroll-comment">' . '<marquee behavior="scroll" direction="left" scrollamount="5">' . $comment . '</marquee>' . '</div>';
	$liveElement .= '</div>';
	# <<< closing author and comment
	# >>> open interaction comment and vote button
	$liveElement .= '<p class="text-beta">' . $comment . '</p>';
	$liveElement .= '<div class="upvotes">' . '<div class="upvote-button">' . '<button class="upvote-button-button" id="' . $counterString . '">Vote</button>' . '</div>' . '</div>';
	$liveElement .= '<div class="hidden-filename" id="file' . $counterString . '">' . $file . '</div>';
	$liveElement .= '</div>';
	# <<< closing interaction comment and vote button
	
	# push the string and the current unixtime to an array
	array_push($allData, $liveElement);
	array_push($allTimestamps, $time);
	array_push($allVotes, $votes);
	array_push($allTitles, $title);
	array_push($allArtists, $artist);
	array_push($allAuthors, $author);
}

# sort the string array after the unixtime array with the oldest date on top
# and then sort the array after votes and more
array_multisort($allVotes, SORT_DESC, SORT_NUMERIC, $allTimestamps, SORT_ASC, SORT_NUMERIC, $allTitles, SORT_ASC, SORT_STRING, $allArtists, SORT_ASC, SORT_STRING, $allAuthors, SORT_ASC, SORT_STRING, $allData);

# print the sorted array
foreach($allData as $key => $value) {
	
	# print the current string but add an ID and POSITION number
	echo str_replace($counterString, $counter, $value);
	
	# count on for next ID/POSITION
	$counter++;
}

?>
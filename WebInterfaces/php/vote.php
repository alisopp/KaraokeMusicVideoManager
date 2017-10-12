<?php

# Get url parameter
$fileName = $_GET['file'];
$type = $_GET['type'];

$path = ".";

# Get file
$str = file_get_contents($path . "/" . $fileName);
$json = json_decode($str, true);

# Get vote file contents
$votes = $json['votes'];

echo "bisherige votes: " . $votes;


# Check if only the votes are asked
if ($type == 'get') {
	echo $votes;
	return;
}

# If not get the IP addresses
$ipAddressBook = "ipBook.json";

# and the current IP addresse
$ip_address = $_SERVER['REMOTE_ADDR'];

# If decrimination create a new helping array
if ($type == 'dec') {
	$newArrayJson = array();
}

# walk through all entries of the IP addresses
if (file_exists($ipAddressBook)) {

	# get the file content
	$inp = file_get_contents($ipAddressBook);
	$tempArray = json_decode($inp);

	foreach ($tempArray as $v) {
		# if song number and ip address can be found
		if (($v->song === $json['song'] && $v->ip === $ip_address) && $v->original_time === $json['time']) {
			
			# and incremination stopp everything here - no upvote allowed!
			if ($type == 'inc') {
				echo $votes;
				return;
			# and decrimination remove the upvote because one was contained
			} elseif ($type == 'dec') {
				$votes = $votes - 1;
			}

		# if decrimination save all not 
		} elseif ($type == 'dec') {
			array_push($newArrayJson, array('song'=> $v->song, 'ip'=> $v->ip, 'original_time'=> $v->original_time, 'unixtime'=> $v->unixtime));
		}
	}
} else {
	$tempArray = array();
}

# if nothing was found and incremination
if ($type == 'inc') {

	# add the vote number + 1
	$votes = $votes + 1;

	# add IP addresse with time and song number to the IP addresses json file
	$date = new DateTime();
	array_push($tempArray, array('song'=> $json['song'], 'ip'=> $_SERVER['REMOTE_ADDR'], 'original_time'=> $json['time'], 'unixtime'=> $date->getTimestamp()));
} elseif ($type == 'dec') {
	$tempArray = $newArrayJson;
}

# save IP addresse changes to file
$jsonData = json_encode($tempArray);
file_put_contents($path . "/" . $ipAddressBook, $jsonData);

echo "write votes: " . $votes . " to " . $fileName;

# then write the new vote number to file
$newData = array('song'=> $json['song'], 'title'=> $json['title'], 'artist'=> $json['artist'], 'author'=> $json['author'], 'comment'=> $json['comment'], 'time'=> $json['time'], 'created-locally'=> $json['created-locally'], 'votes'=> $votes);
$newJsonString = json_encode($newData);

$fp = fopen($path . "/" . $fileName, 'w') or die("<script type='text/javascript'>alert('fopen failed!');location.href='../html/html_party_live.html';</script>");
fwrite($fp,  $newJsonString) or die("<script type='text/javascript'>alert('fwrite failed!');location.href='../html/html_party_live.html';</script>");
fclose($fp) or die("<script type='text/javascript'>alert('fclose failed!');location.href='../html/html_party_live.html';</script>");

#file_put_contents($path . "/" . $fileName, $newJsonString);

# and last but not least return the vote number
echo "neue votes: " . $votes;

?>
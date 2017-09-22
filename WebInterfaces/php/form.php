<?php
# Place posted information in an array
$myarray = explode(",", $_REQUEST['index']);
# 0 = index
# 1 = title
# 2 = artist
?>

<form action="process.php">
	<fieldset>
		<legend>Personal information:</legend>

		<input type="hidden" name="song" value="<?php echo $myarray[0]; ?>">
		<input type="hidden" name="title" value="<?php echo $myarray[1]; ?>">
		<input type="hidden" name="artist" value="<?php echo $myarray[2]; ?>">

		Name/s:<br>
		<input type="text" name="author" placeholder="Enter your name/s..."><br>

		Comment:<br>
		<input type="text" name="comment" placeholder="Enter your comment..."><br><br>

		<input type="submit" value="Submit">
	</fieldset>
</form>
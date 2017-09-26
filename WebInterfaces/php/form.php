<!--
<?php
# Place posted information in an array
$myarray = explode(",", $_REQUEST['index']);
# 0 = index
# 1 = title
# 2 = artist
?>
-->
<!DOCTYPE html>
<html>

<head>
	<!-- Scale text on for example mobile devices: -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Link to CSS file: -->
	<link rel="stylesheet" href="../css/styles_static.css">
	<link rel="stylesheet" href="../css/styles_searchable.css">
	<link rel="stylesheet" href="../css/styles_party.css">
	<link rel="stylesheet" href="../css/php-form.css">
	<!-- Link to JS file: -->
	<script src="../js/w3.js"></script>
	<!-- Custom head tag -->
</head>

<body>

	<!-- Floating button with link to current playlist -->
	<a href="#" onclick="history.back();" class="float">
		<img style="padding-top:15px" width="30px" height="30px" src="data:image/svg+xml,%3Csvg%20fill%3D%22%23FFFFFF%22%20height%3D%2248%22%20viewBox%3D%220%200%2024%2024%22%20width%3D%2248%22%20xmlns%3D%22http%3A//www.w3.org/2000/svg%22%3E%0A%20%20%20%20%3Cpath%20d%3D%22M0%200h24v24H0z%22%20fill%3D%22none%22/%3E%0A%20%20%20%20%3Cpath%20d%3D%22M20%2011H7.83l5.59-5.59L12%204l-8%208%208%208%201.41-1.41L7.83%2013H20v-2z%22/%3E%0A%3C/svg%3E" />
	</a>
	<div class="label-container">
		<div class="label-text">Back to the list</div>
	</div>

	<!-- Section that contains the table with all found music video -->
	<section class="form-bg">

		<form action="process.php" class="submit-form">
			<p class="form-title">Submit this song to the playlist:</p>
			<p class="song-title">
				<?php echo $myarray[1]; ?>
			</p>
			<p class="artist-title">from
				<?php echo $myarray[2]; ?>
			</p>

			<input type="hidden" name="song" value="<?php echo $myarray[0]; ?>">
			<input type="hidden" name="title" value="<?php echo $myarray[1]; ?>">
			<input type="hidden" name="artist" value="<?php echo $myarray[2]; ?>">
			<input class="requiered-button" type="text" name="author" placeholder="Your name/s..." required><br>
			<input type="text" name="comment" placeholder="Your comment..."><br>
			<br>
			<input class="submit-button" type="submit" value="Submit">
		</form>

	</section>
</body>

</html>

<?php
# Place posted information in an array
$myarray = explode(",", $_REQUEST['index']);
# 0 = index
# 1 = artist
# 2 = title
?>
<!-- "before-html"-->
<!DOCTYPE html>
<html>

<head>
	<!-- Scale text on for example mobile devices: -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<!-- Link to CSS file: -->
	<link rel="stylesheet" href="../css/styles_php_form.css">
	<!-- Link to JS file: -->
	<script src="../js/w3.js"></script>
	<!-- Custom head tag -->

	<!-- "custom-head-begin" -->

	<!-- BG color -->
	<meta name="theme-color" content="#24a204" />

	<!-- "custom-head-end" -->
</head>

<body>
	<!-- "body-begin" -->

	<!-- Floating button with link to current playlist -->
	<!-- "floating-button-begin" -->
	<a href="#" onclick="history.back();" class="floating-button"></a>
	<!-- "floating-button-end" -->

	<!-- Section that contains the table with all found music video -->
	<section class="form-bg">

		<form action="process.php" class="submit-form">
			<p class="form-title">
				<!-- "before-title" -->
				Submit this song to the playlist:</p>
				<!-- "after-title" -->
			<p class="song-title"><?php echo $myarray[2]; ?></p>
			<p class="artist-title">
				<!-- "before-artist" -->
				from&nbsp;
				<!-- "after-artist" -->
				<?php echo $myarray[1]; ?></p>

			<input type="hidden" name="song" value="<?php echo $myarray[0]; ?>">
			<input type="hidden" name="title" value="<?php echo $myarray[2]; ?>">
			<input type="hidden" name="artist" value="<?php echo $myarray[1]; ?>">
			<!-- "before-input" -->
			<input class="requiered-button" type="text" name="author" placeholder="Your name/s..." required><br>
			<input type="text" name="comment" placeholder="Your comment..."><br>
			<!-- "after-input" -->
			<br>
			<!-- "before-submit" -->
			<input class="submit-button" type="submit" value="Submit">
			<!-- "after-submit" -->
		</form>

	</section>
	<!-- "body-end" -->
</body>

</html>

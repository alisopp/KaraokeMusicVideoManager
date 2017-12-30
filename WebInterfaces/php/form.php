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
	<a id="quickfix" href="#" class="floating-button"></a>
	<!--onclick="document.getElementById('my_form').submit();"-->
	<!-- "floating-button-end" -->

	<!-- Section that contains the table with all found music video -->
	<section class="form-bg">

		<form action="process.php" class="submit-form" id="my_form">
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
			<input class="submit-button" onclick="history.back();" value="Go Back">
			<!-- "after-submit" -->
		</form>

	</section>
	<script>
	document.getElementById("quickfix").addEventListener("click", function () {
    var bool = true;

    var form = document.forms["my_form"];

    var inputs = form.getElementsByTagName("input");
    for (var i = 0, max = inputs.length; i < max; i++) {
        if (!inputs[i].checkValidity()) {
            bool = false;
        }
    }

    if (bool) {
        form.submit();
    } else {
        alert("Please check your inputs - data is not complete!");
    }

	});
	</script>
	<!-- "body-end" -->
</body>

</html>

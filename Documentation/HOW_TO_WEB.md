# WEB INTERFACE

Instruction on how you can edit/create the website data of this project from the source `.html`, `.css` and `.php` files.

<br>

## Setup

### `HTML` and `CSS` (and `JS`)

To view the documents for editing you could use your default text editor and open the `.html` file with your default web browser.

The recommended workflow is though to install the Open Source editor [brackets](http://brackets.io/) and open with it the folder [`html`](..\WebInterfaces\html).

Now you cannot only automatically auto format the `.html`/`.css` files on save thanks to a Plugin named *Beautify* (click on the Lego icon on the upper right of the window) but also live view your changes in your browser while editing by clicking the flash icon on the upper right of the window.

### `PHP`

To view and test `.php` files I recommend the same editor but you need to have installed a local server creator program like [XAMPP](https://www.apachefriends.org/download.html).

Copy the content of the cloned repository in the directory `htdocs`, start the program (a normal Apache Server) and visit in your browser the local website `127.0.0.1/WebInterfaces/filename.php`.

You can also visit the `.html` file with the demo party song list and get when you click a song right to the `form.php` file and from there to the `process.php` file for testing it all.

<br>

## Where are all files?

### Edit `CSS`

In the folder [`css`](..\WebInterfaces\css) are all styles for every `html` counter part document.

### Edit `HTML`

In the folder [`html`](..\WebInterfaces\html) are all `html` documents.

### Edit `PHP`

In the folder [`php`](..\WebInterfaces\php) are all `php` documents.

<br>

## How to update the changes to the project?

All scripts are converted into `.json` files for the Java application with one Python 3 script.

*(Install Python 3 from https://www.python.org/downloads/ if you not already have)*

Just execute the python script script [`create_website_resources.py`](../WebInterfaces/create_website_resources.py) and nearly instantly all `.json` files that hold the information should be updated.

---

:warning:

If you add new elements and something is not working after you updated the data look into the Python script and add over in `.php` and `.html` files needed tags like there already are. Then go into the Java class which currently creates the web document text and add there the new tag at the right position.

---
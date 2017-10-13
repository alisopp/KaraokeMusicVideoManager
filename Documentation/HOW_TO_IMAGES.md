# IMAGE RECOURCES

Instruction on how you can edit/create every image of this project from the source vector graphics (and how to change them).

<br>

## Setup

You need to have installed:

- Python 3.* from https://www.python.org/downloads/

  - Install additional the Python Imaging Library Pillow with

    ````
    $ pip install Pillow
    ````

- Inkscape from https://inkscape.org/en/release/0.92.2/

  - After the installation Inkscape should already be usable over the command line without adding it to the environment variables, but just check it or add it there if it doesn't work.

<br>

## Create/Update all images

To create or update all images for the project there is one Python script in which everything is contained (menu icons, installer icons, web favicons):

Run the script [`create_image_resources.py`](../ImageResources/create_image_resources.py) in the [`ImageResources`](../ImageResources) folder.

*(Wait some seconds -> Inkscape needs it's time)*

------

⚠

IF you get any problems with Inkscape on Windows add the path to the `bin` folder of the `Inkscape` folder in `ProgramFiles` to the Windows environment variables.

Also be sure to use the newest available version of Inkscape because the old one had bugs and could not handle command line use very well on Windows.

---

<br>

## Current source files from which everything get's created

All images are based on the images:

* [`installer.svg`](../ImageResources/installer.svg) the installer page image source
* [`logo.svg`](../ImageResources/logo.svg) the installer icon/favicon/program icon image source
* All the `.svg` images in the folder [`icons`](../ImageResources/icons) are the image source for all the other program icons

<br>

## How to edit/create the source images?

The original workflow is that the vector images get created/edited in Microsoft PowerPoint (which is not free!):

* [`installer.pptx`](../ImageResources/ProjectFiles/installer.pptx) in this presentation is the installer page
* [`logo.pptx`](../ImageResources/ProjectFiles/logo.pptx) in this presentation is the logo
* [`icons.pptx`](../ImageResources/ProjectFiles/icons.pptx) in this presentation are all the other

After editing/creating every presentation get's converted to a `.pdf` file (within PowerPoint).

Then the script [`convertEveryPdfToSvg.sh`](../ImageResources/ProjectFiles/convertEveryPdfToSvg.sh) makes out of all existing `.pdf` files in the same folder for every page a `.svg` vector image thanks to [Inkscape](https://inkscape.org/en/release/) and [PDFtk](https://www.pdflabs.com/tools/pdftk-the-pdf-toolkit/).

When the script is finished you only need to rename the pages and put them to their specific needed directory.

---

If you want to edit the images you also can just edit them with a vector graphics editor/creator program like [Inkscape](https://inkscape.org/en/release/) and overwrite/save the vector graphic in the specific needed directory.

<br>

## How to change locations add another source?

If you want to do this you need to edit the Python 3 script [`create_image_resources.py`](../ImageResources/create_image_resources.py).
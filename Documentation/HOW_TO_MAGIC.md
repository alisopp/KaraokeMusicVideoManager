# Update/Compile everything

Instruction on how you can use the ***master*** shell script to compile and update every part of this project.

<br>

## The *master* script that does it all

The script that compiles everything [build_everything.sh](../build_everything.sh) can be simply controlled with the script [build_run_configuration.sh](../build_run_configuration.sh) (the master script):

```shell
# Setup your own build run configuration.
# add the following parameters behind the shell script to:
# -> img = update images
# -> web = update web interface
# -> win = create a windows installer

. build_everything.sh img web win
```

Change the parameter in the last line to the things you want to compile on executing/double clicking of the [build_run_configuration.sh](../build_run_configuration.sh).

If you just want to get the runnable `.jar` file remove all parameter (`img web win`).

If you want to update the images before creating the `.jar` add the parameter `img` (the same with all the other parameter).

## The parts/parameter of the *master* script

Here the software/the steps that are needed to do each part/thing:

### Compile only the Java project (create the runnable `.jar`)

```shell
. build_everything.sh
```

You need to have installed:

- Python 3.* from https://www.python.org/downloads/
  - No special packages are needed

* Install Maven
  * Download the `Binary zip archieve` from https://maven.apache.org/download.cgi
  * Extract the `.zip` file to a directory of your computer
  * Now open the extracted folder `apache-maven-x.x.x` (3.5.0 at this time)
  * Open the folder `bin`
  * Copy the file path of this folder
  * Search (On Windows) for `environment variables` and click enter
  * Click the button at the bottom right named `Environment Variables ... `
  * Click below the bottom table the button `Edit` after you have selected the row that starts with `Path`
  * Click in the new window the button `New`
  * Enter in the new row your copied path to the `bin` folder
  * Click OK (3 times)
  * Open a console and enter `mvn -v` to test if maven can be used (reload the console if it was opened)

#### [>> Learn more about the Java compiler script here](/HOW_TO_JAVA.md)

### Update the Images and Icons too

```shell
. build_everything.sh img
```

You need to have installed:

- Python 3.* from https://www.python.org/downloads/
  - Install additional the Python Imaging Library Pillow  with `$ pip install Pillow`
- Inkscape from https://inkscape.org/en/release/0.92.2/

#### [>> Learn more about the images and icons creator script here](/HOW_TO_WEB.md)

### Update all Web Interfaces too

```shell
. build_everything.sh web
```

You need to have installed:

- Python 3.* from https://www.python.org/downloads/
  - No special packages are needed

#### [>> Learn more about the web interface data creator script here](/HOW_TO_WEB.md)

### Create a Windows installer too

```shell
. build_everything.sh win
```

You need to have installed:

* Python 3.* from https://www.python.org/downloads/
  - No special packages are needed
* NSIS from http://nsis.sourceforge.net/Download
  * Add the NSIS function to the command line if they not already can be used (check the command `makensis`) with adding it to the [Windows] environment variables
  * Add to the `Path` entry in the upper table of the window with clicking the button `Edit` after selecting it the path to the `bin` folder of NSIS (for me this was `C:\Program Files (x86)\NSIS\Bin`)

#### [>> Learn more about the Windows installer creator script here](/HOW_TO_NSIS.md)

<br>

---

:warning:

If a command can't be used check before you ask a question if you really can not find the program folder of the command in the environmental variables.

If you have any problems with just set environmental variables (NSIS, Inkscape and Maven was it for me) please restart your computer.

In my case this worked on two of two devices. After the restart everything works great and commands like `makensis`, `inkscape` and `mvn` get instantly recognized!

---

<br>

I wish you success! :smile:
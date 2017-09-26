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

* Install Python 3.* from https://www.python.org/downloads/
  * No special packages are needed
  * In Windows automatic addition to the environment variables
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

#### Do only this outside of the master shell script:

Go into the cloned repository folder and open the folder `DesktopClient`:

* Double click / or execute with the terminal the shell script [build_project.sh](../DesktopClient/build_project.sh)

---

:warning:

IF you get a notification like `probably you run a JRE -> Compile error` you have no Java JDK or you have not activated it:

1. Download and Install it from here if you haven't already:
   http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. After that open once again the environment variables if you are on Windows
   - Select below the bottom table the button `New`
     - Enter the variable name `JAVA_HOME`
     - Then click the button `Browse Directory...`
       - Browse to the directory of the installed `jdk` folder
         (This was the place on my hard drive: `C:\Program Files\Java\jdk1.8.0_144`)
       - Click OK (3 times)
3. Run the script again (after a restart of all terminals and eventually of the whole computer).

---

### Update the Images and Icons too

```shell
. build_everything.sh img
```

- Install Python 3.* packages
  - Image manipulation with `pip install Pillow`
- Install Inkscape
  - Download the installer from https://inkscape.org/en/release/0.92.2/
  - After the installation should Inkscape already be usable over the command line without adding it to the environment variables

#### Do only this outside of the master shell script:

Run the script [create_image_ressources.py](../ImageResources/create_image_ressources.py) in the `ImageResources` folder.
*(Wait some seconds -> Inkscape needs it's time)*

------

⚠

IF you get any problems with Inkscape add the path to the `bin` folder of the `Inkscape` folder in `ProgramFiles` to the Windows environment variables.

Also be sure to use the newest available version of Inkscape because the old one had bugs and could not handle command line use very well on Windows.

---

### Update all Web Interfaces too

```shell
. build_everything.sh web
```

Not much more needed - Python handles everything in seconds.

#### Do only this outside of the main shell script:

Run the script [create_website_page_resources.py](../WebInterfaces/create_website_page_resources.py) in the `WebInterfaces` folder.

### Create a Windows installer too

```shell
. build_everything.sh win
```

- Install NSIS
  - Download and install NSIS from here http://nsis.sourceforge.net/Download
  - After installing it open once again the Windows environment variables
  - Add to the `Path` entry in the upper table with clicking the button `Edit` after selecting it the path to the `bin` folder of NSIS (for me this was `C:\Program Files (x86)\NSIS\Bin`)

#### Do only this outside of the master shell script:

Run the script [build_windows_installer.py](../WindowsInstaller/build_windows_installer.py) in the folder `WindowsInstaller`.

<br>

---

:warning:

If you have any problems with just set environmental variables (Maven, NSIS was it for me) please restart your computer.

In my case this worked two times. After the restart everything works great and commands like `makensis` and `mvn` get instantly recognized!

---

<br>

I wish you success! :smile: :luck: :strong:
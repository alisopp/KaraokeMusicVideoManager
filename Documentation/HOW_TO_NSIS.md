# WINDOWS INSTALLER

Everything about the Windows installer of this project. Read about how to create it and edit it.

<br>

## Compile the script/Create the `installer.exe`

1. Install [Nullsoft Scriptable Install System](https://sourceforge.net/projects/nsis/) from their official site: http://nsis.sourceforge.net/Download
2. Find the folder `WindowsInstaller` in your Windows Explorer
3. Make a right mouse click on the installer script `create_windows_installer.nsi` 
4. Click `Compile NSIS Script`
5. Wait some seconds an the installer is created.

---

:warning:

Before you can do this you need to export the main Java project to a runnable `.jar` file with the name `MusicVideoManager.jar` and save it to the main directory of this repository.

(You can also just download the portable version from [GitHub Releases](https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager/releases) and copy it there).

---

<br>

## How to edit the NSIS (`.nsi`) script?

You can edit the script with every text editor you have.

Text editors like [Notepad++](https://notepad-plus-plus.org/) support syntax highlighting of this script language which is the reason I use it for the installer script.

<br>

## How to edit the images and icons of the installer?

Right now all the icons and images are based on vector graphic templates in the directory [`ImageResources`](../ImageResourcesi).

Change there the vector graphic images [`installer.svg`](../ImageResources/installer.svg) (the installer page image) and [`logo.svg`](../ImageResources/logo.svg) (the icon image) and run the Python script [`create_image_ressources.py`](../ImageResources/create_image_ressources.py) to automatically update the icons and images of this installer.

### [>> Learn more about the images and icons creator script here](HOW_TO_IMAGES.md)

<br>

## How to use NSIS over the command line?

You can use NSIS over the command line either by default or if it does not work:

- After installing it open the [Windows] environment variables
- Add to the `Path` entry in the upper table with clicking the button `Edit` after selecting it the path to the `bin` folder of NSIS (for me this was `C:\Program Files (x86)\NSIS\Bin`)

​:arrow_right_hook:  To compile any `.nsi` script over the command line you can now use the command

```
$ makensis path/to/script.nsi
```

:arrow_right_hook: Also you should now be able to run the python script [build_windows_installer.py](../WindowsInstaller/build_windows_installer.py) (install Python 3.* from https://www.python.org/downloads/therefore to run this script obviously too)

---

:warning:

You need to execute the command `makensis path/to/script.nsi` in the folder  [`WindowsInstaller`](../WindowsInstaller) to get all the resources because of relative paths. If you execute the [build_windows_installer.py](../WindowsInstaller/build_windows_installer.py) script Python automatically does that for you unimportant from where you start it.

---

<br>

## Summary

If you want to make changes you need to edit/look into:

* [`create_windows_installer.nsi`](../WindowsInstaller/create_windows_installer.nsi) to change parts of the installer (pages/behavior)

* [`create_image_ressources.py`](../ImageResources/create_image_ressources.py) update un/install icon/ un/installer page image based on the following source images:
  * [`installer.svg`](../ImageResources/installer.svg) installer page image
  * [`logo.svg`](../ImageResources/logo.svg) icon image

* [`build_windows_installer.py`](../ImageResources/create_image_ressources.py) to compile the before created `jar` file and add after the export a version number to the installer (also it renames the `jar` file before running the NSIS script)


Documentation of NSIS script language:

* [Modern User Interface](http://nsis.sourceforge.net/Docs/Modern%20UI%202/Readme.html)
* [Examples Modern UI](http://nsis.sourceforge.net/Examples/Modern%20UI/)
* [Main Documentation](http://nsis.sourceforge.net/Docs/)
* [Examples](http://nsis.sourceforge.net/Category:Code_Examples)
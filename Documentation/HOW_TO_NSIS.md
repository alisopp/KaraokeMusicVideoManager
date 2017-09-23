# WINDOWS INSTALLER

Coming soon

<br>

## Compile the script/Create the `installer.exe`

1. Install [Nullsoft Scriptable Install System](https://sourceforge.net/projects/nsis/) from their official site: http://nsis.sourceforge.net/Download
2. Find the folder `WindowsInstaller` in your Windows Explorer
3. Make a right mouse click on the installer script `create_windows_installer.nsi` 
4. Click `Compile NSIS Script`
5. Wait some seconds an the installer is created.

---

Attention:

Before you can do this you need to export the main Java project to a runnable `.jar` file with the name `MusicVideoManager.jar` and save it to the main directory of this repository.

(You can also just download the portable version from [GitHub Releases](https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager/releases) and copy it there).

---

<br>

## How to edit the NSIS (`.nsi`) script?

You can edit the script with every text editor you have.

Text editors like [Notepad++](https://notepad-plus-plus.org/) support syntax highlighting of this script language which is the reason I use it for the installer script.

<br>

## How to edit the NSIS (`.nsi`) script images/icons?

Right now all the icons and images are based on vector graphic templates in the directory `ImageResources`.

Change there the vector graphic images and run the Python 3 script `create_image_ressources.py` and all images are changed.

---

Attention:

To execute the Python 3 script you first need to have installed a [Python 3.*](https://www.python.org/downloads/) version and a current version of [Inkscape](https://inkscape.org/en/release/0.92.2/windows/64-bit/) that is not from the Windows store.

You also need to install the Python image library Pillow via `pip install Pillow`.

---

<br>

## Summary

If you want to make changes you need to edit/look into:

* [`create_windows_installer.nsi`](WindowsInstaller/create_windows_installer.nsi)
  to change parts of the installer (pages/behavior)
* [`create_image_ressources.py`](ImageResources/create_image_ressources.py)
  to change to a different un/install icon/ un/installer page image
  * [`installer.svg`](ImageResources/installer.svg)
    installer page image
  * [`logo.svg`](ImageResources/logo.svg)
    icon image
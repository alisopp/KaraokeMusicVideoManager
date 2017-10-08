# My Setup/Programs I use

This is my hardware and the programs I use for this project.

<br>

## Hardware Base

This is the hardware base + operating system on which I develop this project:

| Part             | My Desktop                       | My Laptop       |
| ---------------- | -------------------------------- | --------------- |
| Operating System | 64bit Windows 10 Creators Update | "               |
| Processor        | i7 2600                          | i5 6200U        |
| Ram              | 16GB  DDR3                       | 8GB DDR3        |
| Storage          | SSD                              | "               |
| Graphics Card    | Nvidia GTX 560 (1GB)             | HD Graphics 620 |

I added this so that you can assure you can run/develop this project without any problems if you have specs like this.

Obviously I know you can use a potato to make changes or compile anything - I just tried to open the project once on my Raspberry Pi 3 which just took like 10 minutes (+ anything else besides python scrips are really slow and not so much fun).

If you want you can open the project with whatever computer you want. I just can completely assure you that if you have specs like this you can edit/compile/change/export/run anything and any program down below without having to wait long or your computer being a pain in the ass during doing something.

<br>

## Software

I try to use mostly cross platform and free programs - this means with a high percentage you can use the following programs on whatever platform you are:

| File Format or Use Case                 | Main Program/s I use                     | Other Programs                           | Comment                                  |
| --------------------------------------- | ---------------------------------------- | ---------------------------------------- | ---------------------------------------- |
| `.git`  (Version Control)               | [GitShell](https://git-scm.com/downloads), [GitHubDesktop](https://desktop.github.com/) |                                          | GitHubDesktop is sadly not for Linux.    |
| Java Maven Project (`pom.xml`, `.java`) | [Eclipse Oxygen](https://www.eclipse.org/downloads/) |                                          | Main program. Use `Import` project, `Existing Maven Projects` to import the project into it. |
| `.md` (Markdown)                        | [Typora](https://typora.io/)             | [Markdown Viewer](https://chrome.google.com/webstore/detail/markdown-viewer/ckkdlimhmcjmikdlpkmbgfkaikojcbjk) (Chrome extension) | With the chrome extension you can view local Markdown files with Chrome. |
| `.py` (Python [3])                      | [Visual Studio Code](https://code.visualstudio.com/) | [PyCharm Community](https://www.jetbrains.com/pycharm/download/#section=windowsPy) | For [OpenCV](http://opencv-python-tutroals.readthedocs.io/en/latest/index.html) I use PyCharm Community because Visual Studio Code doesn't support it. |
| `.html`, `.json`, `.css`, `.js`, `.xml` | [Brackets](http://brackets.io/)          |                                          | With an auto formatter and a great syntax highlighter this is a great editor for the web interfaces. |
| Test `.html` documents                  | [Chrome](https://www.google.com/chrome/index.html) | [Firefox [Nightly]](https://www.mozilla.org/en-US/firefox/channel/desktop/) | Double click and view the file.          |
| Test `.php` documents or the server     | [XAMPP](https://www.apachefriends.org/download.html) |                                          | Obviously in combination with Chrome and Firefox (127.0.0.1). |
| `.fxml` (JavaFx)                        | [Scene Builder [Gluon]](http://gluonhq.com/products/scene-builder/) |                                          | Obviously in combination with Eclipse Oxygen. |
| `.nsi` (NSIS)                           | [Notepad ++](https://notepad-plus-plus.org/), [NSIS](http://nsis.sourceforge.net/Download) |                                          | Notepad ++ is sadly only for Windows but you can use any text editor to edit the `.nsi` file - Notepad ++ has just a supported `.nsi` syntax highlighter. |
| `.svg` (Vector Graphics)                | [Inkscape](https://inkscape.org/en/)     | [Chrome](https://www.google.com/chrome/index.html) as graphics viewer | This program is also used when converting the vector graphics to `.ico`, `.png` and `.bmp` files + compressing the `.svg` file. |
| `.sh` (Shell)                           | [GitShell](https://git-scm.com/downloads) to run | [Visual Studio Code](https://code.visualstudio.com/) to edit |                                          |
| `.pptx` (PowerPoint)                    | [PowerPoint](https://products.office.com/en/powerpoint) |                                          | This software is not needed and not free -> I just used it to create the logo and installer images. You can simply edit the already exported `.svg` vector graphics. |

<br>

## Server

My test server is a Raspberry Pi 3 with 32GB Flash (class 10) storage and [Raspbian](https://www.raspberrypi.org/downloads/raspbian/) (flash it on the MicroSD card with [Etcher](https://etcher.io/)). [Network connection works both via integrated WLAN and LAN port]

To control it I use SSH over [Putty](http://www.putty.org/) or on my Android device [ConnectBot](https://play.google.com/store/apps/details?id=org.connectbot&hl=en).

To transfer files I mainly use [Filezilla](https://filezilla-project.org/).

To get graphical remote access I use [Microsoft Remote Desktop](https://www.microsoft.com/en-us/store/p/microsoft-remote-desktop/9wzdncrfj3ps) on Windows and on Android [Microsoft Remote Desktop](https://play.google.com/store/apps/details?id=com.microsoft.rdc.android&hl=en).
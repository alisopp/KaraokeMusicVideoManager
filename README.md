# MavenRewriteJavaMusicVideoManager
temporary till rewrite is as ready as the last upload of the default thing

## How to open/edit this project?

### My Software and Hardware Setup

You can use whatever setup you like but this is mine on Windows 10 Pro 64bit with a Samsung 850 SSD and an intel i7 2600 with 16GB Ram:

**The following Software can be installed on Linux, Mac OS X and Windows!**

- [Eclipse IDE for Java Developers (64bit, version 4.7.0 'Oxygen')](https://www.eclipse.org/downloads/)
  - Eclipse Marketplace plugins:
    - [e(fx)clips (2.4.0)](http://marketplace.eclipse.org/content/efxclipse)
    - optional [EclEmma Java Code Coverage (3.3.0)](http://marketplace.eclipse.org/content/eclemma-java-code-coverage)
  - Eclipse *Install new Software* plugins:
    - [ObjectAid UML Explorer](http://www.objectaid.com/update/current)
  - Eclipse custom settings (`Window > Prefrences`):
    - `Java > Editor > Save Actions`: Activated to format all lines of the source code on save and organize all imports plus additional actions. I use the Formatter `Eclipse [built-in]`.
    - `JavaFx`: insert in the SceneBuilder exectuable path the path of the Gluon `SceneBuilder.exe` on your computer (Search for it - right click open file location two times and then copy the path).
    - optional: `General > Apperance > Colors and Fonts`: Set `Java Editor Text Font` with `Edit` to [Fira Code](https://github.com/tonsky/FiraCode) with `Regular` and the size of 14.
- optional: [Scene Builder (64bit, version 8.3.0, from Gluon)](http://gluonhq.com/products/scene-builder/) (not the one from Oracle because they gave up developing it)
- optional: [GitHub Desktop](https://desktop.github.com/) with [Git](https://git-scm.com/downloads)
  if you wanna use the client on Linux there isn't an official version yet, but some people forked it and report of it's working -> Look here if you are interested: https://github.com/gengjiawen/desktop

### How I open the project

- First I clone it via GitHub Desktop (click clone in the web browser on the GitHub page of this project - GitHub Desktop automatically opens)
- I start Eclipse and click in the menu bar:
  - `File > Import`
  - search for `Existing Maven Projects`
  - click next after you selected it
  - then browse to the cloned folder and click `OK`
  - select the project and click `Finish`
  - now you are able to edit/open/compile the project
- Tipp: If you wanna edit the GUI with the SceneBuilder make a right click on any `.fxml` file and click `Open with SceneBuilder`

### How I export the project

To generate a working runnable JAR file (`xxx.jar`) of this Maven project I needed to do follow the instructions of [Jason C](https://stackoverflow.com/users/616460/jason-c) and [ItamarG3](https://stackoverflow.com/users/3625036/itamarg3) on [stackoverflow](https://stackoverflow.com/a/18218809/7827128):

*Try creating a new launch configuration from scratch:*

1. *`Run` -> `Run Configurations...`*
2. *Right click "Java Application" in the list on the left and select `New`*
3. *On the right enter a descriptive name.*
4. *In the Main tab browse for your project and search for and select the appropriate main class.*
5. *Close the dialog.*

If you now click under `File` `Export` and then search and select `Runnable JAR file`. After that select your just now entered descriptive name under `Launch configuration:`, select `Package required libaries into generated JAR` and click `Finish`.

Like [c0der](https://stackoverflow.com/a/43454265/7827128) I had the problem that my just entered descriptive name didn't showed up under `Launch configuration:`. But a simple restart of the Eclipse IDE fixed this for me.
# JAVA / MAVEN

Instruction on how you can compile/create a runnable jar of the Java program and edit the Java Program over the command line and a Java IDE.

<br>

## Setup

You need to have installed (or something similar to this):

- [Eclipse IDE for Java Developers (64bit, version 4.7.0 'Oxygen')](https://www.eclipse.org/downloads/)
  - Eclipse Marketplace plugins:
    - [e(fx)clips (2.4.0)](http://marketplace.eclipse.org/content/efxclipse)
  - ***not-needed:*** Eclipse custom settings (`Window > Prefrences`):
    - `Java > Editor > Save Actions`: Activated to format all lines of the source code on save and organize all imports plus additional actions. Use the Formatter `Eclipse [built-in]`.
    - `JavaFx`: insert in the Scene Builder executable path the path of the Gluon `SceneBuilder.exe` on your computer (Search for it - right click open file location two times and then copy the path - you need obviously to install it before you can do this step).
    - ***not-needed:***  `General > Apperance > Colors and Fonts`: Set `Java Editor Text Font` with `Edit` to [Fira Code](https://github.com/tonsky/FiraCode) with `Regular` and the size of 14.
- ***not-needed:*** [Scene Builder (64bit, version 8.3.0, from Gluon)](http://gluonhq.com/products/scene-builder/)
  (not the one from Oracle because they gave up developing it?)

<br>

## How to open the project

- First clone it via GitHub Desktop (click clone in the web browser on the GitHub page of this project - GitHub Desktop automatically opens) or just use the command line
- Start Eclipse and click in the menu bar:
  - `File > Import`
  - search for `Existing Maven Projects`
  - click next after you selected it
  - then browse to the cloned folder and click `OK`
  - select the project and click `Finish`
  - now you are able to edit/open/compile the project
- Tipp: If you wanna edit the GUI with the SceneBuilder make a right click on any `.fxml` file and click `Open with SceneBuilder`

<br>

## How to export the project

### Within Eclipse

To generate a working runnable JAR file (`xxx.jar`) of this Maven project I needed to do follow the instructions of [Jason C](https://stackoverflow.com/users/616460/jason-c) and [ItamarG3](https://stackoverflow.com/users/3625036/itamarg3) on [stackoverflow](https://stackoverflow.com/a/18218809/7827128)

*Try creating a new launch configuration from scratch:*

1. *`Run` -> `Run Configurations...`*
2. *Right click "Java Application" in the list on the left and select `New`*
3. *On the right enter a descriptive name.*
4. *In the Main tab browse for your project and search for and select the appropriate main class.*
5. *Close the dialog.*

If you now click in the menu bar of eclipse `File` select in the new menu `Export` and then search and select `Runnable JAR file`. After that select your just now entered descriptive name next to the label `Launch configuration:`, select `Package required libaries into generated JAR` and click `Finish`.

Like [c0der](https://stackoverflow.com/a/43454265/7827128) I had the problem that my just entered descriptive name didn't showed up under `Launch configuration:`. But a simple restart of the Eclipse IDE fixed this for me.

### Command line

You can also export the project over the command line thanks to Maven.

To do this you need to have installed Maven:

- Download the `Binary zip archieve` from <https://maven.apache.org/download.cgi>
- Extract the `.zip` file to a directory of your computer
- Now open the extracted folder `apache-maven-x.x.x` (3.5.0 at this time)
- Open the folder `bin`
- Copy the file path of this folder
- Search (on Windows) for `environment variables` and click enter
- Click the button at the bottom right named `Environment Variables ...`
- Click below the bottom table the button `Edit` after you have selected the row that starts with `Path`
- Click in the new window the button `New`
- Enter in the new row your copied path to the `bin` folder
- Click OK (3 times)
- Open a console and enter `mvn -v` to test if maven can be used (reload the console if it was opened or restart your computer)

Now you can compile the whole Java project by using the command `mvn clean install` in the folder [`DesktopClient`](../DesktopClient) or run the shell script [`build_project.sh`](../DesktopClient/build_project.sh) which also correctly renames the exported `jar` file. (Python 3 is therefore needed).

To change how and what get's exported you need to edit the maven [`pom.xml`](../DesktopClient/pom.xml) file.
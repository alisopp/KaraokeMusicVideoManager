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

To generate a working runnable JAR file (`xxx.jar`) of this Maven project I needed to do follow the instructions of [Jason C](https://stackoverflow.com/users/616460/jason-c) and [ItamarG3](https://stackoverflow.com/users/3625036/itamarg3) on [stackoverflow](https://stackoverflow.com/a/18218809/7827128)::grinning::grinning:

*Try creating a new launch configuration from scratch:*

1. *`Run` -> `Run Configurations...`*
2. *Right click "Java Application" in the list on the left and select `New`*
3. *On the right enter a descriptive name.*
4. *In the Main tab browse for your project and search for and select the appropriate main class.*
5. *Close the dialog.*

If you now click in the menu bar of eclipse `File` select in the new menu `Export` and then search and select `Runnable JAR file`. After that select your just now entered descriptive name next to the label `Launch configuration:`, select `Package required libaries into generated JAR` and click `Finish`.

Like [c0der](https://stackoverflow.com/a/43454265/7827128) I had the problem that my just entered descriptive name didn't showed up under `Launch configuration:`. But a simple restart of the Eclipse IDE fixed this for me.

### How to run the jar

##### Windows XP,7,10

On Windows there seems to be no problem at all as long as you have Java (a version from the last 1-2 years) installed.

If not install it over here: https://www.java.com/en/download/

Find the runnable JAR file (`xxx.jar`) in the explorer and double click it to run.

*(NSI Installer will follow as soon as the program is ready then you can install it just like a normal Windows program)*

#### Linux

Probably you can run it there too, but if not (like I had the problem on my Raspberry Pi 3 with Raspbian) you need to have Java installed (if not use this `sudo apt-get install oracle-java8-jdk`) and additional openjfx (because of the JavaFx GUI). This is not a problem just install it like this:

```
$ sudo apt-get install openjfx
```

Then you can run it over the console with the following command:

```
$ java -jar path/to/file.jar
```



## Questions/Problems/Ideas?

If you still have any kind of questions, problems or great ideas send me an email, open an issue, open a pull-request.

Have fun using this program!

 :smiley:



## Answered Questions:

### I am on Windows and can't run `.jar` files with a double click

I had the same problem after an update to Java 8 - my final solution:

1. Uninstall all Java things (All updates, JRE's, JDK's)

   Windows 10:

   * Search in Cortana for `apps` and press enter
   *  Search in the list of new window for `java`
   * Click every entry with `Java SDK/UPDATE/JRE/...` and click the `Uninstall` button

2. Delete all environmental variables of Java

   Windows 10:

   - Search in Cortana for `environmental variables` and press enter
   - Edit the system and user `PATH` that nowhere is anything with `Java` or `Oracle`
   - Now download the newest [Java SDK or JRE](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) and install it

3. **Tada - `.jar` files can be executed :D**

4. If you now have problems with Eclipse don't explode :sweat:

   * Find the folder where Eclipse has it's program files (my path: `/user/myusername/eclipse/eclipse-ide-version/eclipse`)

   * Open with a text editor like notepad++ the file `eclipse.ini`

   * Find in this file the line:

     ```
     -vm
     C:/Program Files/Java/jdk1.7.0_yourversion/jre/bin
     ```

     * Now open your Windows explorer and go into the folder `C:/Program Files/Java/`

       * Open the folder with SDK or JRE (both works - mine was named `jdk1.8.0_144`)
       * Open the folder `jre` and then the folder `bin`
       * Now copy the path (click on the path bar and `CRTL + C`)

     * Select the line below `-vm` and press `CRTL + V` to overwrite the *old* path: 

       ```
       -vm
       [copy the path in this line to overwrite the old path]
       ```

5. **Even Eclipse should now start again without any problems :smile:**
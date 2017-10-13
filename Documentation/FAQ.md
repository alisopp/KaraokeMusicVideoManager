# F*requently* A*sked* Q*uestions*

Here are all the *serious and good* question with answers that were received and didn't belong to the other big documents in this project.

<br>

## Answered questions:

### :arrow_right_hook: What is a `.jar` file?

A `.jar` file or **J**ava **AR**chive is similar to any package/archive file format you've probably encountered. If you a `.jar` file with a program like `7zip` you can see that it`s just a package where all the images, code and anything else is contained.

Thanks to the Java Runtime on your specific operating system this package can be interpreted like the program was written natively for your operating system. Through this cool thing projects like this can support many operating systems at once (Linux, Mac OS, Windows for example).

The only thing you need is a installed Java Runtime on your operating system and you are good to go :smile:.

---

### :arrow_right_hook: Is the `.jar` file a virus?

Nope, it isn't.

**How can you be sure?**

This is the beauty of Open Source projects: Download all the source files (`git clone`) and follow these [instructions](HOW_TO_JAVA.md) to make the `.jar` file yourself.

Everything that goes into the `.jar` archive is public. If you find something or anybody else just ask and I'll explain.

---

### :arrow_right_hook: How to run the `.jar` file?

#### :arrow_right: Windows XP, 7, 10

On Windows there seems to be no problem at all as long as you have Java (a version from the last 1-2 years) installed. If not install the official Java Runtime over here: https://www.java.com/en/download/

Find the runnable JAR file (`xxx.jar`) in the Windows Explorer and double click it to run.

**Still can't run the `.jar` file?**

[Look here: I am on Windows and can't run `.jar` files with a double click](#00001) 

#### :arrow_right: Linux

Probably you can run it there too, but if not (like I had the problem on my Raspberry Pi 3 with Raspbian) you need to have Java installed:

```
$ sudo apt-get install oracle-java8-jdk
```

And additional openjfx (because of the JavaFx GUI):

```
$ sudo apt-get install openjfx
```

Then you can run it over the console with the following command/or a double click:

```
$ java -jar path/to/file.jar
```

<br>

## Answered 'technical' questions:

### :arrow_right_hook: What specs should my computer have to open and change this project?

Technically you could open this project with a potato but I recommend something like my laptop when you absolutely just wanna use the tools to program and not to wait for them:

[Link to my setup (hardware)](SETUP.md#hardware-base)

### :arrow_right_hook: What program should I use to edit/change/export xyz?

Here a list where I not only show my hardware setup but below it also all the programs I currently use to maintain this project:

[Link to my setup (programs)](SETUP.md#software)

### :arrow_right_hook: I am on Windows and can't run `.jar` files with a double click

<a id="00001"></a>I had the same problem after an update to Java 8 - my final solution:

1. Uninstall all Java things (All updates, JRE's, JDK's)

   * Search in Cortana for `apps` and press enter
   * Search in the list of new window for `java`
   * Click every entry with `Java SDK/UPDATE/JRE/...` and click the `Uninstall` button

2. Delete all environmental variables of Java

   - Search in Cortana for `environmental variables` and press enter
   - Edit the system and user `PATH` that nowhere is anything with `Java` or `Oracle`
   - Now download the newest [Java SDK or JRE](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html) and install it

3. **Tada - `.jar` files can be executed :D**

4. If you now have problems with Eclipse don't overreact :sweat:

   * Find the folder where Eclipse has it's program files (my path: `/user/myusername/eclipse/eclipse-ide-version/eclipse`)

   * Open with a text editor like [Notepad++](https://notepad-plus-plus.org/) the file `eclipse.ini`

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

<br>

## Still have a Question/Problem/Idea?

If you still have any kind of questions, problems or great ideas open an issue or open a pull-request.

Have fun using this program!

😃
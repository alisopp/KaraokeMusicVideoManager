# MavenRewriteJavaMusicVideoManager
temporary till rewrite is as ready as the last upload of the default thing

<br>

## Links to the big parts of this project (Coming Soon)

### [How to open/edit/export the main Java project?](Documentation/HOW_TO_JAVA.md)

### [How to open/edit the web interfaces?](Documentation/HOW_TO_WEB.md)

### [How to open/edit the logo/any images?](Documentation/HOW_TO_IMAGES.md)

### [How to open/edit/compile the Windows installer?](Documentation/HOW_TO_NSIS.md)

### [Frequently asked questions](Documentation/FAQ.md)

<br>

## What is the MusicVideoManager about?

With the MusicVideoManager you can manage your Karaoke evening/event.

1. Run the portable or for Windows even installable program.
2. Choose the folders with your music videos.
3. Export from the software a interactive website with the list of all music video files to a SFTP server (like a Raspberry Pi 3.
4. Let anyone vote/choose what they want to sing next or make a random choice.
5. Watch and control a playlist that is created out of these choices on your computer and run each video with one click.
6. Have fun!

*There are many more things to find and use but this is the main function of the program.*

<br>

## Which music video files will be found?

Each file is right now in this format or it will not be found (there is an option to find wrong formatted files):

|  Artist   | " - " |           Title            | `.avi`/`.mp4`/`.mkv`/`.wmv`/`.mov`/`.mpg` |
| :-------: | :---: | :------------------------: | :--------------------------------------: |
| Green Day |   -   | Boulevard Of Broken Dreams |                   .mp4                   |

<br>

## How can I download/install/run the program?

### General

The program runs on every computer that has Java (a Java Runtime from the last one/two years) installed:

:arrow_right_hook: You can download the portable runnable `.jar` file from [GitHub Releases](https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager/releases) (the latest release).

Just double click the file and the program should be running.

### Windows Instructions

If you have Java not installed or an old version install the newest and securest version form the official website: https://www.java.com/en/download/

* Find the `.jar` file in the Windows Explorer and double click it to start the program.
* For the Windows Operating System we even built an installer for the program:
  * You can download the executable Windows installer `.exe` file from [GitHub Releases](https://github.com/AnonymerNiklasistanonym/KaraokeMusicVideoManager/releases).
  * Double click it after you found it in the Windows Explorer and follow the instructions
  * After the installation is finished you will find three new entries in a folder in your start menu:
    * one will bring you right here to this website
    * one will launch the program
    * one will uninstall the program

### Linux

If you have Java not installed or an old version install the newest and securest version form the official repository:

```
$ sudo apt-get install oracle-java8-jdk
```

Because this program uses a Java Library named JavaFx you additional need to install this too:

 ```
$ sudo apt-get install openjfx
 ```

Now you can run the program with the following command through the command line:

```
$ java -jar path/to/downloaded/file.jar
```

<br>

## Questions/Problems/Ideas?

If you still have any kind of questions, problems or great ideas send me an email, open an issue, open a pull-request.

Have fun using this program!

 :smiley:

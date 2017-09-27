# SETUP A HTTP HOME SERVER

Coming soon: Nothing was tested, this tutorial was not yet tested.

---

**Everything here will be explained on the basis of a Raspberry Pi 3. You can use obviously also other computer because of simple server solution exist for every operating system and architecture.**

---

<br>

## You need:

* a Raspberry Pi 3
* a 16GB (or bigger) Micro SD card
* a 2A USB 'Charger' (from your phone for example)
* a USB to Micro USB cable

<br>

## Setup the Raspberry Pi 3

Read more here on how to enable options and more:
https://github.com/AnonymerNiklasistanonym/RaspiForBeginners

I recommend the default operating system Raspian which is based on Debian.

<br>

## Setup the actual Server

---

You can learn more here (my main source):
https://www.raspberrypi.org/documentation/remote-access/web-server/nginx.md

---

### 1. Install NGINX

Enter this command in the command line of your Raspberry Pi:

```
$ sudo apt-get install nginx
```

### 2. Start the NGINX

Enter this command after the installation to activate the server:

```
$ sudo /etc/init.d/nginx start
```

### 3. Configure/Test the website

#### Let's test if it really works:

* Open the browser on your Raspberry Pi and enter `http://localhost/` in the search
* You should now see a website with the title `Welcome to nginx!`
* When you even want to test if this works across your whole home network enter `hostname -I` to get the IP address of the Raspberry Pi and enter this IP address on any other with the home network connected device
* You should see the same website as you've seen before

#### Change the website and test it again:

- The server's default content location on the Raspberry Pi is `/var/www/html`
- Go into this folder (`cd /var/www/html`) and open with a text editor the file `index.html` (`nano ndex.nginx-debian.html`)
- Now change the welcome text at the top to `42` (Save changes with `Ctrl` + `O`)
- Do everything from "Let's test if it really works:" again and check if everywhere now the title is 42

### 4. Enable/Install PHP

Enter this command to install PHP on your Raspberry Pi:

```
$ sudo apt-get install php5-fpm
```

Now to enable PHP for the NGINX server you need to go into the NGINX directory:

```
$ cd /etc/nginx/sites-enabled
```

And open in it the file `default`:

```
$ sudo nano default
```

now find in this document the line where it say `index index.php index.html index.htm;`.
Then change the line to:

```
index index.php index.html index.htm;
```

Now search in the same document for these lines:

```
# pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
#
# location ~ \.php$ {
```

If you found them remove from the following lines the `#` at the begin:

```
location ~ \.php$ {
    include snippets/fastcgi-php.conf;
    fastcgi_pass unix:/var/run/php5-fpm.sock;
}
```

#### Now reload NGINX:

Reload the new configuration of the server with the command:

```
$ sudo /etc/init.d/nginx reload
```

#### Test if PHP works:

To test if PHP works as it is intended to do go back to the server contents directory:

````
$ cd /var/www/html/
````

In there rename the default `ndex.nginx-debian.html` file (the one where you changed the title before):

```
sudo mv index.nginx-debian.html index.php
```

Now this is a PHP file. To test if PHP in it works to edit again the title in the new `index.php` file to:

```php
<?php echo phpinfo(); ?>
```

Save with `Ctrl` + `O` and test again with `localhost` and other devices in your home network over the IP-Address of the Pi.

If everything works PHP was correctly and successfully enabled.

### 5. Activate Gzip for a faster distribution

For big files the distribution can be speeded up with a clever technique that  enables the server to compress the website and all it's files without data loss. This compression is named Gzip.

If this is activated every website will be compressed by the  server and then send to a device that supports it and will save you really, really much bandwidth (and shorten the website loading time).

#### 1. Enable Gzip

Most likely Gzip is already working???? Test this!

But to check it open with a text editor the file `/etc/nginx/nginx.conf`:

```
$ cd /etc/nginx
$ sudo nano nginx.conf
```

Search for the lines:

```
gzip on;
gzip_disable "msie6";

gzip_vary on;
gzip_proxied any;
gzip_comp_level 6;
gzip_buffers 16 8k;
gzip_http_version 1.1;
gzip_types text/plain text/css application/json application/x-javascript text/xml application/xml application/xml+rss text/javascript;
```

Important are here the lines `gzip on;` which activates Gzip and `gzip_types ...` which are the file types that should be compressed.

Be sure that Gzip is `on` and to add to `gzip_types` `text/php` (`text/html` is default activated).

#### 2. Test in your browser if Gzip works

Now open browser

F12

Look into headers

blablabla -> add when the time has come

<br>

## Create a user that only has access to the server root directory

So that nothing can ever go wrong the user we want to connect to the music video manager (in case there are bugs or anything else) should only have write rights in the server directory.

With this there is no danger that this program will ever *destroy* the server.

<br>

## Get the IP address

One step :arrow_right: before you can start you need to know (besides the password and the installed server's directory) the IP address of it. This is not really a problem through the command `hostname -I`:

```
$ hostname -I
192.168.0.42 xx:xx:xx......
```

I've already x'd the second part of the output because we only need to know the first part: `192.168.0.42`.

This number is the address of your router in your home network.

Write everything on a paper and you can start:

| IP address   | Username                   | Password                                 |
| ------------ | -------------------------- | ---------------------------------------- |
| 192.168.0.42 | your just jet created user | the password to your just jet created user |


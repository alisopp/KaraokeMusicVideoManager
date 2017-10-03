#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
With executing this script you convert the current logo (logo.svg) to all
the needed png and ico files.
To convert the svg file you need to have inkscape installed on your system.
'''

import ntpath
import os
import subprocess
from shutil import copyfile

from PIL import Image, ImageEnhance

# current directory
DIR_PATH = os.path.dirname(os.path.realpath(__file__))

# inkscape.exe directory
DIR_INKSCAPE = r"C:\Program Files\Inkscape"

# set inkscape command
COMMAND_INKSCAPE = 'inkscape'


def convert_svg_2_png(input_filename, output_filename, width=None, height=None):
    """Convert a svg file to a png file."""

    if not os.path.isabs(input_filename):
        source_file = os.path.abspath(input_filename)
    else:
        source_file = input_filename
    if not os.path.isabs(output_filename):
        output_file = os.path.abspath(output_filename)
    else:
        output_file = output_filename

    # change directory to the inkscape directory
    # os.chdir(DIR_INKSCAPE)

    if width is None and height is None:
        pro = subprocess.Popen(
            [COMMAND_INKSCAPE, "-f", source_file, "-e", output_file], stdout=subprocess.PIPE)
    elif height is None:
        pro = subprocess.Popen([COMMAND_INKSCAPE, "-f", source_file, "-e",
                                output_file, "--export-width=" + str(width)],
                               stdout=subprocess.PIPE)
    elif width is None:
        pro = subprocess.Popen([COMMAND_INKSCAPE, "-f", source_file, "-e", output_file,
                                "--export-height=" + str(height)], stdout=subprocess.PIPE)
    else:
        pro = subprocess.Popen([COMMAND_INKSCAPE, "-f", source_file, "-e", output_file,
                                "--export-width=" + str(width), "--export-height=" + str(height)],
                               stdout=subprocess.PIPE)
    while pro.poll() is None:
        print('', end='')
        # wait till the process is ready

    print(pro.communicate()[0].decode("utf-8"))

    print("- \"" + source_file + "\" was converted to \"" + output_file + "\"")

    # os.chdir(DIR_PATH)


def create_png_favicons(favicon_directory_path, source_file):
    """Creates all the html favicons."""

    # create the directory if there is no such directory
    if not os.path.exists(favicon_directory_path):
        os.makedirs(favicon_directory_path)

    # all the sizes we want:
    favicon_sizes = [16, 32, 48, 64, 94, 128, 160, 180, 194, 256, 512]

    for favicon_size in favicon_sizes:
        output_path = os.path.join(
            favicon_directory_path,
            "favicon-" + str(favicon_size) + "x" + str(favicon_size) + ".png")
        convert_svg_2_png(source_file, output_path, favicon_size, favicon_size)


def create_installer_icons(icon_directory_path, source_file):
    """Creates all the ico icons."""

    # create the directory if there is no such directory
    if not os.path.exists(icon_directory_path):
        os.makedirs(icon_directory_path)

    output_name = "icon"
    output_path_png = os.path.join(icon_directory_path, output_name + ".png")
    output_path_ico = os.path.join(
        icon_directory_path, output_name + "_install.ico")
    output_path_ico_2 = os.path.join(
        icon_directory_path, output_name + "_uninstall.ico")
    output_path_ico_3 = os.path.join(
        icon_directory_path, output_name + ".ico")

    convert_svg_2_png(source_file, output_path_png, 255, 255)

    img = Image.open(output_path_png)
    img.save(output_path_ico)

    print("- \"" + output_path_png + "\" was converted to " + output_path_ico)

    img.save(output_path_ico_3)

    print("- \"" + output_path_png + "\" was converted to " + output_path_ico_3)

    make_grey(img).save(output_path_ico_2)

    print("- \"" + output_path_png + "\" was converted to " + output_path_ico_2)

    os.remove(output_path_png)


def create_installer_pages(pages_directory_path, source_file):
    """Creates all the ico icons."""

    # create the directory if there is no such directory
    if not os.path.exists(pages_directory_path):
        os.makedirs(pages_directory_path)

    output_name = "picture_left_"
    output_path_inst_png = os.path.abspath(os.path.join(
        pages_directory_path, output_name + "installer.png"))
    output_path_inst = os.path.abspath(os.path.join(
        pages_directory_path, output_name + "installer.bmp"))
    output_path_uninst = os.path.abspath(os.path.join(
        pages_directory_path, output_name + "uninstaller.bmp"))

    convert_svg_2_png(source_file, output_path_inst_png, 164, 314)

    img = Image.open(output_path_inst_png)
    img.save(output_path_inst)

    print("- \"" + output_path_inst_png +
          "\" was converted to " + output_path_inst)

    make_grey(img).save(output_path_uninst)

    print("- \"" + output_path_inst_png +
          "\" was converted to " + output_path_uninst)

    os.remove(output_path_inst_png)


def create_program_icon(icon_directory_path, source_file):
    """Creates all the ico icons."""

    # create the directory if there is no such directory
    if not os.path.exists(icon_directory_path):
        os.makedirs(icon_directory_path)

    output_name = "icon"
    output_path_png = os.path.abspath(os.path.join(
        icon_directory_path, output_name + ".png"))
    output_path_ico = os.path.abspath(os.path.join(
        icon_directory_path, output_name + ".ico"))

    convert_svg_2_png(source_file, output_path_png, 255, 255)

    img = Image.open(output_path_png)
    img.save(output_path_ico)

    print("- \"" + output_path_png + "\" was converted to " + output_path_ico)

    os.remove(output_path_png)


def make_grey(source_image):
    """Add a slight greyscale to the image."""

    # Found here:
    # https://stackoverflow.com/questions/16070078/change-saturation-with-imagekit-pil-or-pillow/16070333#16070333

    return ImageEnhance.Color(source_image).enhance(0.2)


def copy_svg_icon(destination_file, source_file):

    # check if the source exists
    if os.path.exists(source_file):
        # check if the destination exists
        copyfile(source_file, destination_file)
        print("- \"" + source_file + "\" was copied to " + destination_file)


def create_menu_icons(destination_directory, source_file_directory):

    # check if the source exists
    if os.path.exists(source_file_directory):
         # create the directory if there is no such directory
        if not os.path.exists(destination_directory):
            os.makedirs(destination_directory)
        # check if the destination is a directory
        if os.path.isdir(source_file_directory):
            for file in os.listdir(source_file_directory):
                file = os.path.join(source_file_directory, file)
                if not os.path.isabs(file):
                    file = os.path.abspath(file)
                filename, file_extension = os.path.splitext(file)
                if file_extension == ".svg":
                    convert_svg_2_png(file, os.path.join(
                        destination_directory, ntpath.basename(filename) + ".png"), 15, 15)
    print("All menu icons were created")


if __name__ == '__main__':

    create_png_favicons(r"..\DesktopClient\res\websites\favicons", "logo.svg")
    create_installer_pages(
        r"..\WindowsInstaller\pictures", "installer.svg")
    create_installer_icons(r"..\WindowsInstaller\icons", "logo.svg")
    create_program_icon(r"..\DesktopClient\res\websites\favicons", "logo.svg")
    copy_svg_icon(
        r"..\DesktopClient\res\websites\favicons\favicon.svg", "logo.svg")
    create_menu_icons(r"..\DesktopClient\res\images\menu", "menu")

    print("Ready!")

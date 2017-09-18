#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
With executing this script you convert all the template files in this folder as they are to
this moment into usable templates for the Java Desktop Client.
That means simply said: Change something like css in the template, double click the script
and all your changes (css, additional things) are saved in the Java project.
'''

import os

# Think about duming all the information into Json Files -> faster to load, smaller Filesizes -> need to be implemented into the java thing though

def convert_basic_html():
    """Save changes to the static html template."""

    # read the template in this folder
    with open("html_music_video_list_static.html") as file:
        content = file.readlines()

    # Algorithm that converts the template into the before the table and after the table
    BEGIN_CONTENT = content
    END_CONTENT = content

    # convert the template into the java res folder
    DIR_PATH = os.path.join(os.pardir, "DesktopClient/res/website_data")
    FILE_PATH_BEGIN = os.path.join(DIR_PATH, "static_html_page_begin.html")
    FILE_PATH_END = os.path.join(DIR_PATH, "static_html_page_end.html")

    with open(FILE_PATH_BEGIN, 'w') as outfile:
        outfile.write(BEGIN_CONTENT)
    with open(FILE_PATH_END, 'w') as outfile:
        outfile.write(END_CONTENT)


def convert_searchable_html():
    """Save changes to the searchable html template."""


def convert_party_html():
    """Save changes to the party html template."""


def convert_party_php_form():
    """Save changes to the php add to playlist form template."""


if __name__ == '__main__':

    # do all convertions:
    convert_basic_html()
    convert_searchable_html()
    convert_party_html()
    convert_party_php_form()

    print("Ready!")

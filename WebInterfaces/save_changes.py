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

    # create empty walking string
    walking_string = ""
    # create empty dictionary
    json_list = {}

    # walk through all lines of the html email
    for x in content:
        # delete all the not important things like newlines, tabs, etc. from each string
        x = x.strip()
        # check if this is not an empty line
        if x is not "":
            # if this is found in the comments - add a new entry to the dictionary
            if x.find("top-title-begin") != -1:
                json_list['head'] = walking_string
                walking_string = ""
                # etc.
            elif x.find("top-title-end") != -1:
                json_list['title-body'] = walking_string
                walking_string = ""
            elif x.find("top-title-2-begin") != -1:
                json_list['top'] = walking_string
                walking_string = ""
            elif x.find("top-title-2-end") != -1:
                walking_string = ""
            elif x.find("table-placeholder-begin") != -1:
                json_list['top2'] = walking_string
                walking_string = ""
            elif x.find("tr-nice-tag-open-begin") != -1:
                walking_string = ""
            elif x.find("tr-nice-tag-open-end") != -1:
                json_list['tr-start'] = walking_string
                walking_string = ""
            elif x.find("td-nice-tag-open-begin") != -1:
                walking_string = ""
            elif x.find("td-nice-tag-open-end") != -1:
                json_list['td-start'] = walking_string
                walking_string = ""
            elif x.find("td-nice-tag-close-begin") != -1:
                walking_string = ""
            elif x.find("td-nice-tag-close-end") != -1:
                json_list['td-end'] = walking_string
                walking_string = ""
            elif x.find("tr-nice-tag-close-begin") != -1:
                walking_string = ""
            elif x.find("tr-nice-tag-close-end") != -1:
                json_list['tr-end'] = walking_string
                walking_string = ""
            elif x.find("strike-tag-open-begin") != -1:
                walking_string = ""
            elif x.find("strike-tag-open-end") != -1:
                json_list['strike-start'] = walking_string
                walking_string = ""
            elif x.find("strike-tag-close-begin") != -1:
                walking_string = ""
            elif x.find("strike-tag-close-end") != -1:
                json_list['strike-end'] = walking_string
                walking_string = ""
            elif x.find("table-placeholder-end") != -1:
                walking_string = ""
            elif x.find("link-plan-placeholder-begin") != -1:
                walking_string += "<a href=\""
                json_list['middle'] = walking_string
                walking_string = ""
            elif x.find("link-plan-placeholder-end") != -1:
                walking_string = ""
            elif x.find("linktext-plan-placeholder-begin") != -1:
                walking_string = "\">" + walking_string
                json_list['space-between-link-and-text'] = walking_string
                walking_string = ""
            elif x.find("linktext-plan-placeholder-end") != -1:
                json_list['linktext-plan'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-01-placeholder-begin") != -1:
                json_list['bottom'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-01-placeholder-end") != -1:
                json_list['bottom-text-01'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-02-placeholder-begin") != -1:
                json_list['bottom-text-space-01'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-02-placeholder-end") != -1:
                json_list['bottom-text-02'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-03-placeholder-begin") != -1:
                json_list['bottom-text-space-02'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-03-placeholder-end") != -1:
                json_list['bottom-text-03'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-04-placeholder-begin") != -1:
                json_list['bottom-text-space-03'] = walking_string
                walking_string = ""
            elif x.find("bottom-text-04-placeholder-end") != -1:
                json_list['bottom-text-04'] = walking_string
                walking_string = ""
            else:
                walking_string += x
    # add the rest of the content to "html-end"
    json_list['end'] = walking_string



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

    FILE_PATH_JSON = os.path.join(DIR_PATH, "static_html_page.json")

    # convert the dictionary to a JSON file
    with open(FILE_PATH_JSON, 'w') as outfile:
        # through indent=2 each entry will be on a new line
        # source: https://stackoverflow.com/a/17055135/7827128
        json.dump(json_list, outfile, indent=2, ensure_ascii=False)


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

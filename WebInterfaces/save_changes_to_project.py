#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
With executing this script you convert all the template files in this folder as they are to
this moment into usable templates for the Java Desktop Client.
That means simply said: Change something like css in the template, double click the script
and all your changes (css, additional things) are saved in the Java project.
'''

import json
import os

PATH_HTML = "html"
PATH_PHP = "php"
PATH_CSS = "css"
PATH_JS = "js"


def convert_basic_html(output_path):
    """Save changes to the static html template in the java project."""

    # set the path to the html and css template
    file_path_html = os.path.join(PATH_HTML, "html_static.html")
    file_path_css = os.path.join(PATH_CSS, "styles_static.css")

    # read the static html template
    with open(file_path_html) as file:
        content_html = file.readlines()

    # create an empty "walking" string
    walking_string = ""

    # create an empty dictonary for the json file
    json_static = {}

    # walk through all lines of the html email
    for line in content_html:
        # strip whitespaces, tabs and paragraphs from line
        line = line.strip()
        # check if the current line has any content
        if line is not "":
            # check if line is a comment
            if line.startswith('<!--'):
                # check if the css file link was read
                if "Link to CSS file" in line:
                    # this means we are at the end of the template head
                    # so let's add the css to the head
                    walking_string += convert_css_to_string(file_path_css)
                    # add then everything to the json dictonary
                    json_static['head'] = walking_string
                # check if the default head was read
                elif "Custom head tag" in line:
                    # clean/reset the walking string
                    walking_string = ""
                # check if the example table was reached
                elif "Example table begin" in line:
                    # add everything read to the json dictonary
                    json_static['body-begin'] = walking_string
                elif "Example table end" in line:
                    # clean/reset the walking string because we do not want the table
                    walking_string = ""
            else:
                # text must be a normal line - add it to the "walking" string
                walking_string += line
    # when all lines are read add the reremaining content to the json dictonary
    json_static['end'] = walking_string

    # save the json dictionary in a json file
    with open(output_path, 'w') as outfile:
        json.dump(json_static, outfile)

    print("- Json file exported: " + output_path)


def convert_searchable_html(output_path):
    """Save changes to the searchable html template."""

    # set the path to the html and css template + js file
    file_path_html = os.path.join(PATH_HTML, "html_searchable.html")
    file_path_css_1 = os.path.join(PATH_CSS, "styles_static.css")
    file_path_css_2 = os.path.join(PATH_CSS, "styles_searchable.css")
    file_path_js = os.path.join(PATH_JS, "w3.js")

    # read the static html template
    with open(file_path_html) as file:
        content_html = file.readlines()

    # create an empty "walking" string
    walking_string = ""

    # create an empty dictonary for the json file
    json_static = {}

    # walk through all lines of the html email
    for line in content_html:
        # strip whitespaces, tabs and paragraphs from line
        line = line.strip()
        # check if the current line has any content
        if line is not "":
            # check if line is a comment
            if line.startswith('<!--'):
                # check if the css file link was read
                if "Link to CSS file" in line:
                    # let's add the css to the head
                    walking_string += convert_css_to_string(
                        file_path_css_1, True, False)
                    walking_string += convert_css_to_string(
                        file_path_css_2, False, True)
                # check if the js file link was read
                elif "Custom head tag" in line:
                    # this means we are at the end of the template head
                    # so let's add the js to the head
                    walking_string += convert_js_to_string(file_path_js)
                    # add then everything to the json dictonary
                    json_static['head'] = walking_string
                # check if the default head was read
                elif "Custom head tag" in line:
                    # clean/reset the walking string
                    walking_string = ""
                # check if the example table was reached
                elif "Example table begin" in line:
                    # add everything read to the json dictonary
                    json_static['body-begin'] = walking_string
                elif "Example table end" in line:
                    # clean/reset the walking string because we do not want the table
                    walking_string = ""
            else:
                # text must be a normal line - add it to the "walking" string
                walking_string += line
    # when all lines are read add the reremaining content to the json dictonary
    json_static['end'] = walking_string

    # save the json dictionary in a json file
    with open(output_path, 'w') as outfile:
        json.dump(json_static, outfile)

    print("- Json file exported: " + output_path)


def convert_party_html(output_path):
    """Save changes to the party html template."""

    # set the path to the html and css template + js file
    file_path_html = os.path.join(PATH_HTML, "html_party.html")
    file_path_css_1 = os.path.join(PATH_CSS, "styles_static.css")
    file_path_css_2 = os.path.join(PATH_CSS, "styles_searchable.css")
    file_path_css_3 = os.path.join(PATH_CSS, "styles_party.css")
    file_path_js = os.path.join(PATH_JS, "w3.js")

    # read the static html template
    with open(file_path_html) as file:
        content_html = file.readlines()

    # create an empty "walking" string
    walking_string = ""

    # create an empty dictonary for the json file
    json_static = {}

    # walk through all lines of the html email
    for line in content_html:
        # strip whitespaces, tabs and paragraphs from line
        line = line.strip()
        # check if the current line has any content
        if line is not "":
            # check if line is a comment
            if line.startswith('<!--'):
                # check if the css file link was read
                if "Link to CSS file" in line:
                    # let's add the css to the head
                    walking_string += convert_css_to_string(
                        file_path_css_1, True, False)
                    walking_string += convert_css_to_string(
                        file_path_css_2, False, False)
                    walking_string += convert_css_to_string(
                        file_path_css_3, False, True)
                # check if the js file link was read
                elif "Custom head tag" in line:
                    # this means we are at the end of the template head
                    # so let's add the js to the head
                    walking_string += convert_js_to_string(file_path_js)
                    # add then everything to the json dictonary
                    json_static['head'] = walking_string
                # check if the default head was read
                elif "Custom head tag" in line:
                    # clean/reset the walking string
                    walking_string = ""
                # check if the example table was reached
                elif "Example table begin" in line:
                    # add everything read to the json dictonary
                    json_static['body-begin'] = walking_string
                elif "Example table end" in line:
                    # clean/reset the walking string because we do not want the table
                    walking_string = ""
            else:
                # text must be a normal line - add it to the "walking" string
                walking_string += line
    # when all lines are read add the reremaining content to the json dictonary
    json_static['end'] = walking_string

    # save the json dictionary in a json file
    with open(output_path, 'w') as outfile:
        json.dump(json_static, outfile)

    print("- Json file exported: " + output_path)


def convert_party_php_forms(output_path):
    """Save changes to the php add to playlist form template."""

    # set the path to the php files
    file_path_php_form = os.path.join(PATH_PHP, "form.php")
    file_path_php_process = os.path.join(PATH_PHP, "process.php")

    # read the static html template
    with open(file_path_php_form) as file:
        content_php_form = file.readlines()

    with open(file_path_php_process) as file:
        content_php_process = file.readlines()

     # create an empty "walking" string
    walking_string = ""

    # create an empty dictonary for the json file
    json_php_forms = {}

    # walk through all lines of the php form
    for line in content_php_form:
        # strip whitespaces, tabs and paragraphs from line
        line = line.strip()
        # check if there even is a line
        if line is not "":
            # check if line is a comment
            if not line.startswith('#'):
                walking_string += line
    # when all lines are read add the reremaining content to the json dictonary
    json_php_forms['form'] = walking_string

    walking_string = ""

    # walk through all lines of the php process document
    for line in content_php_process:
        # strip whitespaces, tabs and paragraphs from line
        line = line.strip()
        # check if there even is a line
        if line is not "":
            # check if line is a comment
            if not line.startswith('#'):
                walking_string += line
    # when all lines are read add the reremaining content to the json dictonary
    json_php_forms['process'] = walking_string

    # save the json dictionary in a json file
    with open(output_path, 'w') as outfile:
        json.dump(json_php_forms, outfile)

    print("- Json file exported: " + output_path)


def convert_css_to_string(css_filename, begin_tag=True, end_tag=True):
    """Convert the content of a .css file to a single line string."""

    # read the css file
    with open(css_filename) as file:
        content_css = file.readlines()

    # create walking string
    walking_css_string = ""

    if begin_tag:
        # create an empty "walking" string with the opening style tag
        walking_css_string = "<style>"
    # walk through all lines of the html email
    for line in content_css:
        # strip whitespaces, tabs and paragraphs from line
        line = line.strip()
        # check if the line is neither empty nor a comment
        if line is not "" and not line.startswith("/*"):
            # if yes save content in the walking string
            walking_css_string += line
    if end_tag:
        # add the final closing style tag
        walking_css_string += "</style>"

    # return the "real/important" content of the css file as a string
    return walking_css_string


def convert_js_to_string(js_filename):
    """Convert the content of a .css file to a single line string."""

    # read the css file
    with open(js_filename) as file:
        content_js = file.readlines()

    # create an empty "walking" string with the opening style tag
    walking_js_string = "<script>"
    # walk through all lines of the html email
    for line in content_js:
        # strip whitespaces, tabs and paragraphs from line
        line = line.strip()
        # check if the line is neither empty nor a comment
        if line is not "" and not line.startswith("/*") and not line.startswith("//"):
            # if yes save content in the walking string
            walking_js_string += line
    # add the final closing style tag
    walking_js_string += "</script>"

    # return the "real/important" content of the css file as a string
    return walking_js_string


if __name__ == '__main__':

    # do all convertions:

    MAIN_OUTPUT_DIRECTORY = os.path.join(
        os.pardir, "DesktopClient/res/websites")

    # static website data
    STATIC_OUTPUT_PATH = os.path.join(
        MAIN_OUTPUT_DIRECTORY, "html_page_static.json")
    convert_basic_html(STATIC_OUTPUT_PATH)

    # searchable website data
    SEARCHABLE_OUTPUT_PATH = os.path.join(
        MAIN_OUTPUT_DIRECTORY, "html_page_searchable.json")
    convert_searchable_html(SEARCHABLE_OUTPUT_PATH)

    # party website data
    PARTY_OUTPUT_PATH = os.path.join(
        MAIN_OUTPUT_DIRECTORY, "html_page_party.json")
    convert_party_html(PARTY_OUTPUT_PATH)

    # party php websites data
    PARTY_PHP_OUTPUT_PATH = os.path.join(
        MAIN_OUTPUT_DIRECTORY, "php_pages_party.json")
    convert_party_php_forms(PARTY_PHP_OUTPUT_PATH)

    print("Ready!")

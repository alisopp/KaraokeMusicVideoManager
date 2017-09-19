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


def convert_basic_html():
    """Save changes to the static html template in the java project."""

    # set the path to the html and css template
    template_dir_path = "list_static"
    file_path_html = os.path.join(template_dir_path, "html_static.html")
    file_path_css = os.path.join(template_dir_path, "styles_static.css")

    # set the path to the (output-) folder in the java project
    project_dir_path = os.path.join(
        os.pardir, "DesktopClient/res/website_data")
    file_path_json = os.path.join(project_dir_path, "html_page_static.json")

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
    with open(file_path_json, 'w') as outfile:
        json.dump(json_static, outfile)

    print("- Json file exported: " + file_path_json)


def convert_searchable_html():
    """Save changes to the searchable html template."""

    # set the path to the html and css template + js file
    template_dir_path = "list_searchable"
    file_path_html = os.path.join(template_dir_path, "html_searchable.html")
    file_path_css = os.path.join(template_dir_path, "styles_searchable.css")
    file_path_js = os.path.join("libraries", "w3.js")

    # set the path to the (output-) folder in the java project
    project_dir_path = os.path.join(
        os.pardir, "DesktopClient/res/website_data")
    file_path_json = os.path.join(
        project_dir_path, "html_page_searchable.json")

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
                    walking_string += convert_css_to_string(file_path_css)
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
    with open(file_path_json, 'w') as outfile:
        json.dump(json_static, outfile)

    print("- Json file exported: " + file_path_json)


def convert_party_html():
    """Save changes to the party html template."""


def convert_party_php_form():
    """Save changes to the php add to playlist form template."""


def convert_css_to_string(css_filename):
    """Convert the content of a .css file to a single line string."""

    # read the css file
    with open(css_filename) as file:
        content_css = file.readlines()

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
    convert_basic_html()
    convert_searchable_html()
    convert_party_html()
    convert_party_php_form()

    print("Ready!")

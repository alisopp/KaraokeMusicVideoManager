#!/usr/local/bin/python3

"""
Create an image with text
 - uses Pillow (pip install Pillow)
 - uses OpenCv (pip install opencv-python)
 """

import random
import cv2
from PIL import Image, ImageDraw, ImageFont
import os


def random_color():
    """Creates an random color - https://stackoverflow.com/a/28999469/7827128"""

    return (random.randint(0,255), random.randint(0,255), random.randint(0,255))


def create_image(filename, size=(1280, 720), message="Text", bg_color=random_color(), number=-1, text_color="white"):
    """Creates a image .png file with the name of the given string - """

    # load a font
    font = ImageFont.truetype("FiraCode-Regular.ttf", 50)
    font2 = ImageFont.truetype("FiraCode-Regular.ttf", 30)

    # create an image
    img = Image.new("RGB", size, bg_color)

    # create a draw object
    draw = ImageDraw.Draw(img)

    width_2, height_2 = draw.textsize(message, font=font)
    position_text = ((size[0] - width_2) / 2, (size[1] - height_2) / 2)
    draw.text(position_text, message, fill=text_color, font=font)

    if number != -1:
        width_2, height_2 = draw.textsize(">> Demo file #" + str(number), font=font2)
        position_text = ((size[0] - width_2) / 2, (size[1] - height_2) / 2 + 100)
        draw.text(position_text, ">> Demo file #" + str(number), fill=text_color, font=font2)

    draw = ImageDraw.Draw(img)
    img.save(filename)


def create_video(images, filename):
    """Creates a video file out of images"""

    # Determine the width and height from the first image
    frame = cv2.imread(images[0])
    #cv2.imshow('video', frame)
    height, width, channels = frame.shape

    # Define the codec and create VideoWriter object
    fourcc = cv2.VideoWriter_fourcc(*'XVID')  # Be sure to use lower case
    out = cv2.VideoWriter(filename, fourcc, 1.0, (width, height))

    for image in images:
        for y in range(3):
            image_path = image
            frame = cv2.imread(image_path)

            out.write(frame)  # Write out frame to video

            #cv2.imshow('video', frame)
            if (cv2.waitKey(1) & 0xFF) == ord('q'):  # Hit `q` to exit
                break

    # Release everything if job is finished
    out.release()
    cv2.destroyAllWindows()

    print("The output video is {}".format(filename))


if __name__ == '__main__':

    if not os.path.exists("demo_files"):
        os.makedirs("demo_files")

    number_of_files = 101

    number_of_zeroes = "%0" + str(len(str(number_of_files - 1))) + "d"
    for x in range(number_of_files):
        x = number_of_zeroes % x
        images = [("demo_files/start", "Hi :)", x), ("demo_files/end", "Bye!", x)]
        bg_color = random_color()

        for image in images:
            create_image(image[0] + ".png", (1280, 720), image[1], bg_color, image[2])

        create_video([x[0] + ".png" for x in images], "demo_files/Example Artist #" + str(x) + " - Example Title #" + str(x) + ".avi")

        for image in images:
            os.remove(image[0] + ".png")

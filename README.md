# Virtual Assistant for the Visually Impaired Individuals

### Overview

Smartphones have become an integral part of the modern world. Increasing
popularity, computing power and easy availability have opened new ways to utilize
these devices. Due to these aspects we can use these devices for the betterment of
society in various ways such as for the visually impaired.

The aim of this project is to provide an android application which can help guide the
visually challenged. Using this application, the accompaniment of another person will
not be necessary. This application will detect objects in the users surrounding and
alert them whenever required.

This will be done using the smartphone camera and implemented using TensorFlow
lite and MobileNet SSD which will carry out the object detection process. Since the
primary user will be visually impaired, the app will be utilized using voice commands
and output will also be voice synthesized.

## Build the demo using Android Studio

### Prerequisites

*   You need an Android device and Android development environment with minimum
    API 21.

*   Android Studio 3.2 or later.

### Building

*   Open Android Studio, and from the Welcome screen, select Open an existing
    Android Studio project.

*   From the Open File or Project window that appears, navigate to and select
    the tensorflow-lite/examples/object_detection/android directory from
    wherever you cloned the TensorFlow Lite sample GitHub repo. Click OK.

*   If it asks you to do a Gradle Sync, click OK.

*   You may also need to install various platforms and tools, if you get errors
    like "Failed to find target with hash string 'android-21'" and similar.
    Click the `Run` button (the green arrow) or select `Run > Run 'android'`
    from the top menu. You may need to rebuild the project using `Build >
    Rebuild` Project.

*   If it asks you to use Instant Run, click Proceed Without Instant Run.

*   Also, you need to have an Android device plugged in with developer options
    enabled at this point. See
    **[here](https://developer.android.com/studio/run/device)** for more details
    on setting up developer devices.


### Model used

NAVIGATION AND DENOMINATION READER
TensorFlow Object Detection API
Creating accurate machine learning models capable of localizing and identifying
multiple objects in a single image remains a core challenge in computer vision. The
TensorFlow Object Detection API is an open source framework built on top of
TensorFlow that makes it easy to construct, train and deploy object detection models.
MultiBoxTracker: A tracker that handles non-max suppression and matches existing
objects to new detections.
Detector: Generic interface for interacting with different recognition engines.
TensorFlow Lite
TensorFlow Lite is a set of tools to help developers run TensorFlow models on
mobile, embedded, and IoT devices. It enables on-device machine learning inference
with low latency and a small binary size.
ImageUtils: Utility class for manipulating images.

TensorFlow Lite consists of two main components:-
TensorFlow Lite interpreter, which runs specially optimized models on many
different hardware types, including mobile phones, embedded Linux devices, and
microcontrollers.
TensorFlow Lite converter: The TensorFlow Lite converter takes a TensorFlow
model and generates a TensorFlow Lite model (an optimized FlatBuffer format
identified by the .tflite file extension).

Virtual Assistant for Visually System Design Chapter 4
Impaired People

BEProjectReportITP09For2021Page 27 of 61
Camera2 API:
android.hardware.camera2.CameraAccessException: CameraAccessException is
thrown if a camera device could not be queried or opened by the CameraManager, or
if the connection to an opened CameraDevice is no longer valid.

android.hardware.camera2.CameraCharacteristics: The properties describing a
CameraDevice. These properties are fixed for a given CameraDevice, and can be
queried through the CameraManager interface with
CameraManager.getCameraCharacteristics. CameraCharacteristics objects are
immutable.

android.hardware.camera2.CameraManager: A system service manager for
detecting, characterizing, and connecting to CameraDevices.

android.hardware.camera2.params.StreamConfigurationMap: Immutable class
to store the available stream configurations to set up Surfaces for creating a capture
session with CameraDevice.createCaptureSession. This is the authoritative list for all 
output formats (and sizes respectively for that format) that are supported by a camera
device. This also contains the minimum frame durations and stall durations for each
format/size combination that can be used to calculate effective frame rate when
submitting multiple captures.

android.media.Image: A single complete image buffer to use with a media source
such as a MediaCodec or a CameraDevice. This class allows for efficient direct
application access to the pixel data of the Image through one or more ByteBuffers.
Each buffer is encapsulated in an image. Plane that describes the layout of the pixel

Virtual Assistant for Visually System Design Chapter 4
Impaired People

BEProjectReportITP09For2021Page 28 of 61
data in that plane. Due to this direct access, and unlike the Bitmap class, Images are
not directly usable as UI resources.

android.media.Image.Plane: A single color plane of image data. The number and
meaning of the planes in an Image are determined by the format of the Image. Once
the Image has been closed, any access to the the plane&#39;s ByteBuffer will fail

android.media.ImageReader:The ImageReader class allows direct application
access to image data rendered into a Surface.

android.media.ImageReader.OnImageAvailableListener: Callback interface for
being notified that a new image is available. The onImageAvailable is called per
image basis, that is, callback fires for every new frame available from ImageReader.

4.3.2 EMERGENCY SMS SERVICE
import android.location.Address; : A class representing an Address, i.e, a set of
Strings describing a location. 

import android.location.Geocoder; : A class for handling geocoding and reverse
geocoding. Geocoding is the process of transforming a street address or other
description of a location into a (latitude, longitude) coordinate.

import android.location.Location; : A data class representing a geographic
location.A location may consist of a latitude, longitude, timestamp, and other
information such as bearing, altitude and velocity.

import android.location.LocationManager; :This class provides access to the
system location services. These services allow applications to obtain periodic updates

Virtual Assistant for Visually System Design Chapter 4
Impaired People

BEProjectReportITP09For2021Page 29 of 61
of the device&#39;s geographical location, or to be notified when the device enters the
proximity of a given geographical location.

import android.telephony.SmsManager; : Manages SMS operations such as
sending data, text, and pdu SMS messages. On accepting the permissions the
SmsManager checks the active phone number via which the sms will be sent. This is
typically used for devices that support multiple active subscriptions at once.

For Google Location Link:-
The user will be directed to google maps to track the current location of the visually
impaired user. With the assistance of google maps the time required to locate the
visually impaired individual is reduced.

*****

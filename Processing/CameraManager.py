import ParkingSpot
import BoundingBox

from PIL import Image
from ultralytics import YOLO

import json

"""
CameraManager is an image processing unit assigned to a singular physical camera.

It is designed to watch over a set of designated spots, and uses image processing from YOLOv8
to turn images from the camera into parseable data to analyze the spots.

This class is only designed to handle cameras that are stationary and at a fixed angle.
"""
class CameraManager:
    camera = None # Not yet implemented
    parking_spots = None
    model = None

    def __init__(self, camera):
        """
        Constructs a CameraManager object. It is initialized with a pretrained YOLOv8 model.

        Parameters:
            camera (Camera) : camera handler used to interface with a real camera.
        """
        self.camera = camera
        self.parking_spots = []
        self.model = YOLO("yolov8n.pt") # load a pre-trained model

    def process_image(self, image):
        """
        Processes an image to determine where cars are. Then, automatically updates list of parking spots
        to account for which ones are vacant or occupied.

        Parameter:
            image (String) : File directory path to an image (.png).
        """
        result = self.model(image)
        car_boxes = []

        # Takes each car Box detected by YOLOv8 into a BoundingBox
        for box in result[0].boxes:
            center = (box.xywh[0][0], box.xywh[0][1])
            width = box.xywh[0][2]
            height = box.xywh[0][3]

            car_boxes.append(
                BoundingBox.BoundingBox(
                    (center[0] + width / 2, center[1] + height / 2), # top right
                    (center[0] - width / 2, center[1] + height / 2), # top left
                    (center[0] - width / 2, center[1] - height / 2), # bottom left
                    (center[0] + width / 2, center[1] - height / 2)  # bottom right
                )
            )

        # Check every spot against every detected car to see if the spot is taken
        for spot in self.parking_spots:
            spot.is_vacant = True # resets status prior to checking

            for car in car_boxes:
                if spot.bounds.is_intersecting(car) or spot.bounds.contains(car) or car.contains(spot.bounds):
                    spot.is_vacant = False
                    break
        return
    
    def set_parking_spots(self, json_file):
        """
        Reads a JSON file containing information on where parking spots are, and updates the parking_spot data accordingly.

        This object WILL be altered by running this function.

        Parameters:
            json_file (String) : The file directory path to a JSON file that can be opened.
        """
        self.parking_spots = [] # Resets list prior to working with it

        with open(json_file, mode="r", encoding="utf-8") as read_file:
            data = json.load(read_file) # Dictionary of mappings read from the file

        for shape in data["shapes"]:
            points = shape["points"]
            bounds = BoundingBox.BoundingBox(points[0], points[1], points[2], points[3])
            self.parking_spots.append(ParkingSpot.ParkingSpot(bounds, True))
        return
    
    def tally(self):
        """
        Calculates the total number of vacant spots and the total number of spots.

        Returns:
            A pair of (int, int) in the order (vacant spots, total spots).
        """
        vacant_spots = 0
        total_spots = 0

        for spot in self.parking_spots:
            if spot.is_vacant:
                vacant_spots += 1
            total_spots += 1
        
        return (vacant_spots, total_spots)

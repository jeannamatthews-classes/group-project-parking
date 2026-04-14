import ParkingSpot
import BoundingBox

from PIL import Image
from ultralytics import YOLO

import json

class CameraManager:
    camera = None # Not yet implemented
    parking_spots = None
    model = None

    def __init__(self, camera):
        self.camera = camera
        self.parking_spots = []
        self.model = YOLO("yolov8n.pt") # load a pre-trained model

    def process_image(self, image):
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

        for spot in self.parking_spots:
            spot.is_vacant = True # resets status prior to checking

            for car in car_boxes:
                if spot.bounds.is_intersecting(car) or spot.bounds.contains(car) or car.contains(spot.bounds):
                    spot.is_vacant = False
                    break

        return
    
    # Reads a JSON file to get parking spot positions
    def set_parking_spots(self, json_file):
        self.parking_spots = []

        with open(json_file, mode="r", encoding="utf-8") as read_file:
            data = json.load(read_file) # Dictionary of mappings from file

        for shape in data["shapes"]:
            points = shape["points"]
            bounds = BoundingBox.BoundingBox(points[0], points[1], points[2], points[3])
            self.parking_spots.append(ParkingSpot.ParkingSpot(bounds, True))

        return
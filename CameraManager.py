import ParkingSpot
import BoundingBox

from ultralytics import YOLO
import json

"""
CameraManager is an image processing unit assigned to a singular physical camera.

It watches a set of predefined parking spots and uses YOLOv8
to determine which spots are occupied.
"""
class CameraManager:

    def __init__(self, camera=None):
        self.camera = camera
        self.parking_spots = []
        self.model = YOLO("yolov8n.pt")  # load pretrained model

    def process_image(self, image_path):
        """
        Processes an image and updates parking spot occupancy.

        Parameters:
            image_path (str): Path to image file

        Returns:
            dict: {vacant, used, total}
        """
        results = self.model(image_path)

        car_boxes = []

        # Extract ONLY car detections
        for box in results[0].boxes:
            cls = int(box.cls[0])

            if cls != 2:  # class 2 = car
                continue

            # YOLO gives center x,y and width,height
            cx, cy, w, h = box.xywh[0]

            car_boxes.append(
                BoundingBox.BoundingBox(
                    (cx + w / 2, cy + h / 2),  # top right
                    (cx - w / 2, cy + h / 2),  # top left
                    (cx - w / 2, cy - h / 2),  # bottom left
                    (cx + w / 2, cy - h / 2)   # bottom right
                )
            )

        # Check each parking spot
        for spot in self.parking_spots:
            spot.is_vacant = True  # reset

            for car in car_boxes:
                if (
                    spot.bounds.is_intersecting(car)
                    or spot.bounds.contains(car)
                    or car.contains(spot.bounds)
                ):
                    spot.is_vacant = False
                    break

        stats = self.tally()
        return stats

    def set_parking_spots(self, json_file):
        """
        Loads parking spot definitions from LabelMe JSON.
        """
        self.parking_spots = []

        with open(json_file, "r", encoding="utf-8") as f:
            data = json.load(f)

        for shape in data["shapes"]:
            if shape.get("label") != "spot":
                continue

            pts = shape["points"]
            points = [(p[0], p[1]) for p in pts]

            bounds = BoundingBox.BoundingBox(
                points[0],
                points[1],
                points[2],
                points[3]
            )

            self.parking_spots.append(
                ParkingSpot.ParkingSpot(bounds, True)
            )

    def tally(self):
        vacant = 0
        total = len(self.parking_spots)

        for spot in self.parking_spots:
            if spot.is_vacant:
                vacant += 1

        used = total - vacant

        return {
            "used": used,
            "vacant": vacant,
            "total": total
        }
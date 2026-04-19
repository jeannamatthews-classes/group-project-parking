"""
ParkingSpot is a representation of a parking spot for where it appears on an image, and
can store data on its vacancy status.
"""
class ParkingSpot:
    bounds = None       # BoundingBox depicting the location of the ParkingSpot on the image
    is_vacant = None    # Whether or not the spot is currently occupied

    def __init__(self, bounds, is_vacant):
        """
        Constructs a ParkingSpot object.

        Parameters:
            bounds (BoundingBox) : the bounds defining the location of the spot.
            is_vacant (boolean) : True/False whether or not the spot is vacant.
        """
        self.bounds = bounds
        self.is_vacant = is_vacant
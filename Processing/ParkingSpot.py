class ParkingSpot:
    bounds = None
    is_vacant = None

    def __init__(self, bounds, is_vacant):
        self.bounds = bounds
        self.is_vacant = is_vacant
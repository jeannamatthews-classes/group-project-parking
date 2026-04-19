"""
ParkingLot is the highest order hierarchical class for parking spot detection.

It represents the parking lot in its entirety, and can be split into one or more sublots
for organizational purposes.
"""
class ParkingLot:
    name = ""       # Display name
    id = ""         # Unique identifier for queries
    sublots = []    # List of sublots

    def __init__(self, name):
        """
        Constructs a ParkingLot object.

        Parameters:
            name (String) : the name of the lot.
        """
        self.name = name
        self.id = name # ID is assumed to be the same as the name

    def __init__(self, name, sublots):
        """
        Constructs a ParkingLot object.

        Parameters:
            name (String) : the name of the lot.
            sublots ([Sublot]) : a list of all Sublots.
        """
        self.name = name
        self.id = name # ID is assumed to be the same as the name
        self.sublots = sublots

    def __init__(self, name, id, sublots):
        """
        Constructs a ParkingLot object.

        Parameters:
            name (String) : the name of the lot.
            id (String) : the identifier of the lot.
            sublots ([Sublot]) : a list of all Sublots.
        """
        self.name = name
        self.id = id
        self.sublots = sublots
"""
Sublot is a subset of a ParkingLot.

It serves as a way to group several CameraManagers and have them work together to cover the whole sublot.
"""
class Sublot:
    name = ""       # Display name
    id = ""         # Unique identifier for queries
    managers = []   # List of camera managers

    def __init__(self, name):
        """
        Constructs a Sublot object.

        Parameters:
            name (String) : the name of the sublot.
        """
        self.name = name
        self.id = name # ID is automatically assumed to be name

    def __init__(self, name, id, managers):
        """
        Constructs a Sublot object.

        Parameters:
            name (String) : the name of the sublot.
            id (String) : the identifier of the sublot.
            managers ([CameraManager]) : a list of all CameraManagers.
        """
        self.name = name
        self.id = id
        self.managers = managers

    def tally(self):
        """
        Calculates the total number of vacant spots and the total number of spots.

        Returns:
            A pair of (int, int) in the order (vacant spots, total spots).
        """
        vacant_spots = 0
        total_spots = 0

        # Adds up the information from each of the managers it controls
        for manager in self.managers:
            result = manager.tally()
            vacant_spots += result[0]
            total_spots += result[1]

        return (vacant_spots, total_spots)
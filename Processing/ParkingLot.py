class ParkingLot:
    name = ""       # Display name
    id = ""         # Unique identifier for queries
    sublots = []    # List of sublots

    def __init__(self, name, id, sublots):
        self.name = name
        self.id = id
        self.sublots = sublots

    def __init__(self, name, sublots):
        self.name = name
        self.id = name
        self.sublots = sublots
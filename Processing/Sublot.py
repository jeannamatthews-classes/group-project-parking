class Sublot:
    name = ""       # Display name
    id = ""         # Unique identifier for queries
    managers = []   # List of camera managers

    def __init__(self, name):
        self.name = name

    def __init__(self, name, managers):
        self.name = name
        self.managers = managers
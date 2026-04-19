"""
A BoundingBox is a quadilateral polygon. It consists of exactly four points forming an enclosed shape.
"""
class BoundingBox:
    # Points (x,y)
    top_right = None
    top_left = None
    bottom_left = None
    bottom_right = None

    x_min = None
    x_max = None
    y_min = None
    y_max = None

    def __init__(self, point1, point2, point3, point4):
        """
        Constructs a BoundingBox object. Points can be passed in any order.

        Parameters:
            point1 (double, double) : The first of the four points.
            point2 (double, double) : The second of the four points.
            point3 (double, double) : The third of the four points.
            point4 (double, double) : The fourth of the four points.
        """
        points = [point1, point2, point3, point4]
        best_fit = None

        # Find which point is top right
        for point in points:
            if(best_fit == None):
                best_fit = point
            
            else:
                if(point[0] >= best_fit[0] and point[1] >= best_fit[1]):
                    best_fit = point

        self.top_right = best_fit
        points.remove(best_fit)

        #### Quick interlude to deduce the minimum and max ranges of the bounds ####

        # Initialize ranges to first point found
        self.x_min = best_fit[0]
        self.x_max = best_fit[0]
        self.y_min = best_fit[1]
        self.y_max = best_fit[1]

        # Iterate through all points to find correct x and y ranges
        for point in points:
            if(point[0] < self.x_min):
                self.x_min = point[0]

            if(point[0] > self.x_max):
                self.x_max = point[0]

            if(point[1] < self.y_min):
                self.y_min = point[1]

            if(point[1] > self.y_max):
                self.y_max = point[1]

        ############################################################################

        best_fit = None

        # Find which point is top left
        for point in points:
            if(best_fit == None):
                best_fit = point
            
            else:
                if(point[0] <= best_fit[0] and point[1] >= best_fit[1]):
                    best_fit = point

        self.top_left = best_fit
        points.remove(best_fit)
        best_fit = None

        # Find which point is bottom left
        for point in points:
            if(best_fit == None):
                best_fit = point
            
            else:
                if(point[0] <= best_fit[0] and point[1] <= best_fit[1]):
                    best_fit = point

        self.bottom_left = best_fit
        points.remove(best_fit)
        best_fit = None

        # bottom right is the remaining point
        self.bottom_right = points[0]

    def is_intersecting(self, bounds):
        """
        Determines whether these bounds have any intersection with the given bounds

        Parameters:
            bounds (BoundingBox) : the bounds with which to compare.

        Returns:
            True if the given bounds intersect, False otherwise.
        """
        x_overlap = ((self.x_min <= bounds.x_min and self.x_max >= bounds.x_min) 
                     or (self.x_min <= bounds.x_max) and self.x_max >= bounds.x_max)
        y_overlap = ((self.y_min <= bounds.y_min and self.y_max >= bounds.y_min)
                     or (self.y_min <= bounds.y_max and self.y_max >= bounds.y_max))
        
        return x_overlap and y_overlap
    
    def contains(self, bounds):
        """
        Determines whether these bounds completely contain the given bounds.

        Parameters:
            bounds (BoundingBox) : the bounds with which to compare.

        Returns:
            True if the given bounds reside entirely within this object, False otherwise.
        """
        return (self.x_min <= bounds.x_min
                and self.x_max >= bounds.x_max
                and self.y_min <= bounds.y_min
                and self.y_max >= bounds.y_max)
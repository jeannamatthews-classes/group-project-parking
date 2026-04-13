from flask import Flask, jsonify
import random
from datetime import datetime

app = Flask(__name__)

# @app.route('/parking-status', methods=['GET'])
# def parking_status():
#     spots = []
#     available = 0

#     for spot_id in ["A1", "A2", "A3", "A4", "A5"]: #random loop through 5 IDs
#         occupied = random.choice([True, False]) #randomly decide if occupied
#         if not occupied:
#             available += 1 #increase count if not occupied
#         spots.append({"id": spot_id, "occupied": occupied}) #adding spot data

#     return jsonify({ #returning JSON data
#         "lot_name": "Campus Lot A",
#         "available_spots": available,
#         "spots": spots

#     })
app.route('/parking-status', methods=['GET'])
def parking_status():

    spot_ids = ["A1", "A2", "A3", "A4", "A5"]

    spots = []
    open_count = 0

    for spot_id in spot_ids:
        occupied = random.choice([True, False])
        confidence = round(random.uniform(0.80, 0.99), 2)

        if not occupied:
            open_count += 1

        spots.append({
            "spot_id": spot_id,
            "occupied": occupied,
            "confidence": confidence
        })

    total_spots = len(spot_ids)
    closed_count = total_spots - open_count

    response = {
        "location": "Clarkson University",
        #"timestamp": datetime.utcnow().isoformat() + "Z",
        "sublocations": [
            {
                "name": "Upper Cheel",
                "camera_id": "UC_CAM_1",
                "summary": {
                    "total_spots": total_spots,
                    "open_spots": open_count,
                    "closed_spots": closed_count
                },
                "spots": spots
            }
        ]
    }

    return jsonify(response)



if __name__ == '__main__': #local host on port 5000
    app.run(host='0.0.0.0', port=5000)
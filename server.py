from flask import Flask, jsonify
import random

app = Flask(__name__)

@app.route('/parking-status', methods=['GET'])
def parking_status():
    spots = []
    available = 0

    for spot_id in ["A1", "A2", "A3", "A4", "A5"]: #random loop through 5 IDs
        occupied = random.choice([True, False]) #randomly decide if occupied
        if not occupied:
            available += 1 #increase count if not occupied
        spots.append({"id": spot_id, "occupied": occupied}) #adding spot data

    return jsonify({ #returning JSON data
        "lot_name": "Campus Lot A",
        "available_spots": available,
        "spots": spots
    })

if __name__ == '__main__': #local host on port 5000
    app.run(host='0.0.0.0', port=5000)
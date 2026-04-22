from flask import Flask, jsonify
import mysql.connector

app = Flask(__name__)

# -------------------------
# DB CONNECTION
# -------------------------
def get_db():
    return mysql.connector.connect(
        host="localhost",
        user="root",
        password="pword",
        database="parking_app"
    )

# -------------------------
# GET FULL LOT STATUS
# -------------------------
@app.route("/parking-status", methods=["GET"])
def parking_status():
    try:
        db = get_db()
        cursor = db.cursor(dictionary=True)

        cursor.execute("""
            SELECT 
                l.camera_id,
                l.lot_name,
                l.latitude,
                l.longitude,
                l.total_spaces,
                COALESCE(s.used_spaces, 0) AS used_spaces
            FROM parking_lots l
            LEFT JOIN parking_status s
            ON l.camera_id = s.camera_id
        """)

        rows = cursor.fetchall()

        # format response for Android
        result = []
        for row in rows:
            total = row["total_spaces"]
            used = row["used_spaces"]

            result.append({
                "camera_id": row["camera_id"],
                "lot_name": row["lot_name"],
                "latitude": row["latitude"],
                "longitude": row["longitude"],
                "total_spaces": total,
                "used_spaces": used,
                "available_spots": total - used
            })

        cursor.close()
        db.close()

        return jsonify(result)

    except Exception as e:
        return jsonify({
            "error": str(e)
        }), 500


# -------------------------
# HEALTH CHECK
# -------------------------
@app.route("/", methods=["GET"])
def home():
    return jsonify({
        "status": "API running",
        "endpoints": [
            "/parking-status"
        ]
    })


# -------------------------
# RUN SERVER
# -------------------------
if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5001, debug=True)
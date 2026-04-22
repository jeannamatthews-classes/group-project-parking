from flask import Flask, request, jsonify
from ultralytics import YOLO
from CameraManager import CameraManager
import tempfile
import mysql.connector

app = Flask(__name__)

model = YOLO("yolov8n.pt")

camera_manager = CameraManager(camera=None)
camera_manager.set_parking_spots("spots.json")


@app.route("/process", methods=["POST"])
def process_image():
    try:
        # -------------------------
        # 1. READ IMAGE
        # -------------------------
        file = request.files["image"]
        file_bytes = file.read()

        with tempfile.NamedTemporaryFile(suffix=".jpg", delete=False) as tmp:
            tmp.write(file_bytes)
            tmp_path = tmp.name

        # -------------------------
        # 2. CAMERA MANAGER PIPELINE
        # -------------------------
        camera_manager.process_image(tmp_path)
        stats = camera_manager.tally()

        used_spaces = stats["used"]
        total_spaces = stats["total"]
        vacant_spaces = stats["vacant"]

        # -------------------------
        # 3. DB CONNECTION
        # -------------------------
        db = mysql.connector.connect(
            host="localhost",
            user="root",
            password="pword",
            database="parking_app"
        )
        cursor = db.cursor()

        cursor.execute("""
            INSERT INTO parking_status (camera_id, used_spaces)
            VALUES (%s, %s)
            ON DUPLICATE KEY UPDATE
                used_spaces = VALUES(used_spaces)
        """, (1, used_spaces))

        db.commit()
        cursor.close()
        db.close()

        # -------------------------
        # 4. RESPONSE
        # -------------------------
        return jsonify({
            "used": used_spaces,
            "vacant": vacant_spaces,
            "total": total_spaces
        })

    except Exception as e:
        print("SERVER ERROR:", e)
        return jsonify({"error": str(e)}), 500


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000, debug=True)
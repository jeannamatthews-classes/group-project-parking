import mysql.connector

db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="pword",
    database="parking_app"
)

cursor = db.cursor()

with open("test.jpg", "rb") as f:
    binary_data = f.read()

cursor.execute("""
INSERT INTO image_queue (camera_id, image, status)
VALUES (1, %s, 'pending')
""", (binary_data,))

db.commit()

print("Image inserted successfully")
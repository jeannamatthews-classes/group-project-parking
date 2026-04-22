import mysql.connector
from PIL import Image
import io

db = mysql.connector.connect(
    host="localhost",
    user="root",
    password="pword",
    database="parking_app"
)

cursor = db.cursor()

cursor.execute("""
SELECT id, image FROM image_queue
WHERE status = 'pending'
ORDER BY id ASC
LIMIT 1
""")

job = cursor.fetchone()

if not job:
    print("No jobs available")
    exit()

job_id = job[0]
image_data = job[1]

# Convert bytes → image
image = Image.open(io.BytesIO(image_data))

# Show image
image.show()
import time
import requests

URL = "http://localhost:5000/process"
INTERVAL = 45

#images and their annoatated data
LOTS = [
    ("img1.jpg", "test1.json"),
    ("img2.jpg", "test2.json"),
    ("img3.png", "test3.json")
]

print("Demo Started...")

for image, lot_data in LOTS:
    print(f"Sending {image}")

    try:
        with open(image, "rb") as img:
            lot = {"image": img}
            data = {"json_file": lot_data}
            response = requests.post(URL, files=lot, data=data)

        print(f"Status: {response.status_code}")
        print(response.text)
    
    except Exception as e:
        print(f"Error: {e}")

    time.sleep(INTERVAL)

print("Demo Complete.")

        
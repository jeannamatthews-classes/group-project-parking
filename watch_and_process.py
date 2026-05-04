import os
import time
import requests

WATCH_FOLDER = r"C:\Parking_Images"
URL = "http://localhost:5000/process"

JSON_MAP = {
    "img1.jpg" : "test1.json",
    "img2.jpg" : "test2.json",
    "img3.png" : "test3.json"
}

seen = set()

def wait_for_complete_file(path):
    prev_size =-1
    while True:
        size = os.path.getsize(path)
        if size == prev_size:
            break
        prev_size = size
        time.sleep(0.5)

print("Watcher started....")

while True:
    
    for filename in os.listdir(WATCH_FOLDER):

        if filename in seen:
            continue

        if filename not in JSON_MAP:
            continue

        image_path = os.path.join(WATCH_FOLDER, filename)
        json_filename = JSON_MAP[filename]
        json_path = os.path.join(WATCH_FOLDER, json_filename)

        if not os.path.exists(json_path):
            print(f"Waiting for JSON for {filename}")
            continue

        wait_for_complete_file(image_path)

        print(f"Processing {filename} with {json_filename}...")

        try:
            with open(image_path, "rb") as img:
                files = {"image": img}
                data = {"json_file": json_path}
                response = requests.post(URL, files=files, data=data)

            print(f"Done: {filename} -> Status {response.status_code}")
            print(response.text)

            seen.add(filename)

        except Exception as e:
            print(f"Error Processing {filename}: {e}")

    time.sleep(5)
            
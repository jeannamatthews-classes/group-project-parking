#run tests by changing the .jpg 
import requests
import sys

url = "http://localhost:5000/process"

image_file = sys.argv[1]
json_file = sys.argv[2]

with open(image_file, "rb") as img:
    files = {"image" : img}
    data = {"json_file" : json_file}
    response = requests.post(url, files=files, data=data)




print(response.status_code)
print(response.text)

import requests

url = "http://localhost:5000/process"

files = {"image": open("josh.jpg", "rb")}

response = requests.post(url, files=files)


print(response.status_code)
print(response.text)

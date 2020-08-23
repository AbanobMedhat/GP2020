import machine
import time
from machine import Pin, PWM
from umqtt.simple import MQTTClient
from time import sleep
import random
import json
import network
import ussl

# WiFi connection information
wifiSSID = 'Not_found'
wifiPassword = 'MOHAMED@AHMED?ESLAM!2006'

# turn off the WiFi Access Point
ap_if = network.WLAN(network.AP_IF)
ap_if.active(False)

# connect the ESP32 device to the WiFi network
wifi = network.WLAN(network.STA_IF)
wifi.active(True)
wifi.connect(wifiSSID, wifiPassword)

# wait until the device is connected to the WiFi network
MAX_ATTEMPTS = 20
attempt_count = 0
while not wifi.isconnected() and attempt_count < MAX_ATTEMPTS:
    attempt_count += 1
    time.sleep(1)

if attempt_count == MAX_ATTEMPTS:
    print('could not connect to the WiFi network')
    sys.exit()

# Connect to Thingsboard server using port 8883 as secure port 
def connect():
  # Device Token
  username="nSKYuguLUV2Iijjp6JWB"
  # Host IP
  broker=  "18.191.196.152"
  # Required Destination of Data
  topic = "v1/devices/me/telemetry"
  Mqtt_CLIENT_ID = "ABSE"
  PASSWORD=""
  # SSL Certificate Path in Esp32
  ssl_params = {'cert':'mqttserver.pub.pem'}
  client = MQTTClient(client_id=Mqtt_CLIENT_ID, server=broker, port=8883, user=username, 
    password=PASSWORD, keepalive=10000, ssl=True, ssl_params=ssl_params)
  try:
    client.connect()
  except OSError:
    print('Connection failed')
    sys.exit()
  data = dict()
  # Data Sent
  data["TempData"] = 60
  data2=json.dumps(data)#convert it to json

  print('connection finished')
  client.publish(topic,data2)
  print("Done")
  time.sleep(5)
connect()
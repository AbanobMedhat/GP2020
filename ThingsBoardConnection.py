import machine
import time
from machine import Pin, PWM
from umqtt.simple import MQTTClient
from time import sleep
import random
import json
import network

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


def connect():
  username="07XKgbiYxCZZh02O7TGt"
  broker=  "demo.thingsboard.io"
  topic = "v1/devices/me/telemetry"
  Mqtt_CLIENT_ID = "ABSE"
  PASSWORD=""
  client = MQTTClient(client_id=Mqtt_CLIENT_ID, server=broker, port=1883, user=username, password=PASSWORD, keepalive=10000)
  try:
    client.connect()
  except OSError:
    print('Connection failed')
    sys.exit()
  data = dict()
  data["TempData"] = 60
  data2=json.dumps(data)#convert it to json

  print('connection finished')
  client.publish(topic,data2)
  print("Done")
  time.sleep(5)
connect()

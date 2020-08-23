import machine
import time
from machine import Pin, PWM
from umqtt.simple import MQTTClient
from time import sleep
import random
import json
import network
#################MQTT###################
           

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
  data["horaaaaaaaaay"] = 60
  data2=json.dumps(data)#convert it to json

  print('connection finished')
  client.publish(topic,data2)
  print("kkkkkkkkkkkkkkkkkk")
  time.sleep(5)
#print("Sending OFF")
connect()


'''
import machine
import time
from machine import Pin, PWM
from umqtt.simple import MQTTClient
from time import sleep
import random
import json
import network
#################MQTT###################
           

def connect():
  username="07XKgbiYxCZZh02O7TGt"
  #broker=  "http://demo.thingsboard.io/"
  broker=  "demo.thingsboard.io"
  topic = "v1/devices/me/telemetry"
#  client = MQTTClient(username,broker)
  
#  try:
#    print("uuuuuuuuuu")
#    client.connect()
#  except OSError:
#    print('Connection failed')
#    sys.exit()
client = MQTTClient("mqtt_client", "YOUR_TB_IP", user="DEVICE_ACCESS_TOKEN", password="", port=1883)
try:
    client.connect()
except OSError:
    print('Connection failed')
    sys.exit()
  data = dict()
  data["see"] = 15
  data2=json.dumps(data)#convert it to json

  print('connection finished')
  client.publish(topic,data2)
  print("kkkkkkkkkkkkkkkkkk")
  time.sleep(5)
#print("Sending OFF")
connect()
'''
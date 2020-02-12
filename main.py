from time import sleep
from machine import Pin
while True:
	P2=Pin(2,Pin.OUT)
	P2.off()
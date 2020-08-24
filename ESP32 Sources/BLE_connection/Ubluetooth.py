from ubluetooth import BLE, UUID, FLAG_NOTIFY, FLAG_READ, FLAG_WRITE
from micropython import const
import utime

_IRQ_CENTRAL_CONNECT                 = const(1 << 0)
_IRQ_CENTRAL_DISCONNECT              = const(1 << 1)
_IRQ_GATTS_WRITE                     = const(1 << 2)
_IRQ_GATTS_READ_REQUEST              = const(1 << 3)
_IRQ_SCAN_RESULT                     = const(1 << 4)
_IRQ_SCAN_COMPLETE                   = const(1 << 5)
_IRQ_PERIPHERAL_CONNECT              = const(1 << 6)
_IRQ_PERIPHERAL_DISCONNECT           = const(1 << 7)
_IRQ_GATTC_SERVICE_RESULT            = const(1 << 8)
_IRQ_GATTC_CHARACTERISTIC_RESULT     = const(1 << 9)
_IRQ_GATTC_DESCRIPTOR_RESULT         = const(1 << 10)
_IRQ_GATTC_READ_RESULT               = const(1 << 11)
_IRQ_GATTC_WRITE_STATUS              = const(1 << 12)
_IRQ_GATTC_NOTIFY                    = const(1 << 13)
_IRQ_GATTC_INDICATE                  = const(1 << 14)
###################################for_scaning################################ 
def adv_decode(adv_type, data):
    i = 0
    while i + 1 < len(data):
        if data[i + 1] == adv_type:
            return data[i + 2:i + data[i] + 1]
        i += 1 + data[i]
    return None

def adv_decode_name(data):
    n = adv_decode(0x09, data)
    if n:
        return n.decode('utf-8')
    return data
###################################for_scaning################################ 
#interrupt ISR
def bt_irq(event, data):
  if event == _IRQ_SCAN_RESULT:
    # A single scan result.
    addr_type, addr, connectable, rssi, adv_data = data
    print(addr_type, addr, adv_decode_name(adv_data))
  elif event == _IRQ_SCAN_COMPLETE:
    # Scan duration finished or manually stopped.
    print('scan complete')
    ##########################################################
  elif event == _IRQ_PERIPHERAL_CONNECT:
    # A successful gap_connect().
    conn_handle, addr_type, addr = data
    utime.sleep(2)
    #print(conn_handle, addr_type,addr)
    #bt.gattc_discover_services(conn_handle)
    #bt.gattc_discover_descriptors(0,768,65535)
    #bt.gattc_discover_characteristics(0,256,274)
    bt.gattc_discover_characteristics(0,16,32)
    #bt.gattc_read(0, 770)
    #utime.sleep(5)
    print('connect complete...')
    utime.sleep(2)
    #########################################
  elif event == _IRQ_PERIPHERAL_DISCONNECT:
    # Connected peripheral has disconnected.
    conn_handle, addr_type, addr = data
    ###############################
  elif event == _IRQ_GATTC_SERVICE_RESULT:
    # Called for each service found by gattc_discover_services().
    conn_handle, start_handle, end_handle, uuid = data
    print(conn_handle, start_handle, end_handle, uuid)
    print('discover service...')
    utime.sleep(1)
    #bt.gattc_discover_characteristics(0, start_handle, end_handle)
    
                         ##########################
  elif event == _IRQ_GATTC_CHARACTERISTIC_RESULT:
    # Called for each characteristic found by gattc_discover_services().
    conn_handle, def_handle, value_handle, properties, uuid = data
    print(conn_handle,def_handle,value_handle,properties, uuid)
    print('discover char ...')
    utime.sleep(1)
    bt.gattc_read(0,value_handle)
    #bt.gattc_discover_descriptors(0,256,274)
    #################################
  elif event == _IRQ_GATTC_DESCRIPTOR_RESULT:
    # Called for each descriptor found by gattc_discover_descriptors().
    conn_handle, dsc_handle, uuid = data
    print(conn_handle, dsc_handle, uuid)
    print('discover descript  ...')
    utime.sleep(2)
    bt.gattc_read(conn_handle, dsc_handle)
    ##############################
  elif event == _IRQ_GATTC_READ_RESULT:
    # A gattc_read() has complete
    print("el data=.....")
    utime.sleep(2)
    conn_handle, value_handle, char_data = data
    print(char_data)
    ############################aren't used in the code###############
  elif event == _IRQ_GATTC_WRITE_STATUS:
    # A gattc_write() has completed.
    conn_handle, value_handle, status = data
  elif event == _IRQ_GATTC_NOTIFY:
    # A peripheral has sent a notify request.
    print("Notify...")
    conn_handle, value_handle, notify_data = data
  elif event == _IRQ_GATTC_INDICATE:
    # A peripheral has sent an indicate request.
    conn_handle, value_handle, notify_data = data

# Scan for 10s (at 100% duty cycle)
def bt_irq2(event, data):
  t=5
bt = BLE()
bt.active(True)
#bt.irq(handler=bt_irq)
bt.irq(handler=bt_irq2)
bt.gap_connect(0,b'\xe0$\x81\x1b\xc1\x01',2000)



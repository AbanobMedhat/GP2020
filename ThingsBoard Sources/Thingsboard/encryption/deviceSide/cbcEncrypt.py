# ESP32 Micropython implementation of cryptographic
#
# reference:
# https://pycryptodome.readthedocs.io/en/latest/src/cipher/classic.html#cbc-mode
# https://docs.micropython.org/en/latest/library/ucryptolib.html
 
import uos
from ucryptolib import aes
 
MODE_ECB = 1
MODE_CBC = 2
MODE_CTR = 6
BLOCK_SIZE = 16
 
# key size must be 16 or 32
# key = uos.urandom(32)
key = b'I_am_32bytes=256bits_key_padding'

###################################################
#             AES CBC Cryptographic               #
###################################################
 
# Generate iv with HW random generator 
iv = uos.urandom(BLOCK_SIZE)
cipher = aes(key,MODE_CBC,iv)
 
ct_bytes = iv + cipher.encrypt(plaintext)
print ('AES-CBC encrypted:', ct_bytes)
 
iv = ct_bytes[:BLOCK_SIZE]
cipher = aes(key,MODE_CBC,iv)
decrypted = cipher.decrypt(ct_bytes)[BLOCK_SIZE:]
print('AES-CBC decrypted:', decrypted)
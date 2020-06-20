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
#             AES ECB Cryptographic               #
###################################################
cipher = aes(key, MODE_ECB)
 
plaintext = 'This is AES cryptographic'
print('Plain Text:', plaintext)
 
# Padding plain text with space 
pad = BLOCK_SIZE - len(plaintext) % BLOCK_SIZE
plaintext = plaintext + " "*pad
 
encrypted = cipher.encrypt(plaintext)
print('AES-ECB encrypted:', encrypted )
 
cipher = aes(key,1) # cipher has to renew for decrypt 
decrypted = cipher.decrypt(encrypted)
print('AES-ECB decrypted:', decrypted)

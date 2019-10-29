#!/usr/bin/python3
# socket_echo_client.py

import socket
import sys

# Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Connect the socket to the port where the server is listening
server_address = ('localhost', 4242)
print('connecting to {} port {}'.format(*server_address))
sock.connect(server_address)

try:

    # f = sock.makefile()

    print('blabla')
    l = sock.recv(42)
    print(l)
    sock.sendall(l)
    # print('blabla')

finally:
    print('closing socket')
    sock.close()


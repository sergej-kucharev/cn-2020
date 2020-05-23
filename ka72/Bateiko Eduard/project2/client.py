from socket import AF_INET, socket, SOCK_STREAM
from threading import Thread
import json


def receive():
    while True:
        recieve_msg = client_socket.recv(BUFSIZE).decode("utf-8")
        print(recieve_msg)

        if recieve_msg == "block":
            send_block()

        elif recieve_msg == 'quit':
            client_socket.close()
            break

        elif recieve_msg == 'result':
            receive_result()
        else:
            message = input("Enter message:\t")
            client_socket.send(bytes(message, 'utf-8'))


def receive_result():
    recieve_massage = client_socket.recv(BUFSIZE).decode('utf-8').replace("'", '"')
    data = json.loads(recieve_massage)
    print(f"YOU RESULT:\n{data}")

    client_socket.send(b"ok")


def send_block():
    name = input("Enter name:\t")
    amount = int(input("Enter amount:\t"))
    to_whom = input("Enter to_whom:\t")

    message = {"name" : name, "amount" : amount, "to_whom": to_whom}
    # client_socket.send(bytes(message, 'utf-8'))
    client_socket.send(bytes(str(message), 'utf-8'))


HOST = '127.0.0.1'
PORT = 33001

BUFSIZE = 1024
ADDR = (HOST, PORT)
client_socket = socket(AF_INET, SOCK_STREAM)
client_socket.connect(ADDR)

receive_thread = Thread(target=receive)
receive_thread.start()
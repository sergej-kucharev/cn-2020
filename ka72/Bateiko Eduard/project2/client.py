from socket import AF_INET, socket, SOCK_STREAM
from threading import Thread

def receive():
    while True:
        recieve_msg = client_socket.recv(BUFSIZ).decode("utf-8")
        print(recieve_msg)

        if recieve_msg == "block":
            send_block()
        else:
            message = input("Enter message:\t")
            client_socket.send(bytes(message, 'utf-8'))

def send_block(event=None):
    name = input("Enter name:\t")
    amount = int(input("Enter amount:\t"))
    to_whom = input("Enter to_whom:\t")

    message = "{\'name\': " + f"\'{name}\', " + \
              "\'amount\': " + f"\'{amount}\' ," + \
              "\'to_whom\': " + f"\'{to_whom}\'" + "}"

    client_socket.send(bytes(message, 'utf-8'))

def on_closing(event=None):
    pass


HOST = '127.0.0.1'
PORT = 33000

# if not PORT:
#     PORT = 33001
# else:
#     PORT = int(PORT)

BUFSIZ = 1024
ADDR = (HOST, PORT)
client_socket = socket(AF_INET, SOCK_STREAM)
client_socket.connect(ADDR)

receive_thread = Thread(target=receive)
receive_thread.start()
from socket import AF_INET, socket, SOCK_STREAM
from threading import Thread

clients = {}
addresses = {}

HOST = ''
PORT = 33000
BUFSIZ = 1024
ADDR = (HOST, PORT)
SERVER = socket(AF_INET, SOCK_STREAM)
SERVER.bind(ADDR)


def accept_incoming_connections():
    while True:
        client, client_addres = SERVER.accept()
        print(f"{client}:{client_addres} has connected.")
        client.send(bytes("Greetings from the cave\n!" +
                          "Now type your name and press enter!", "utf8"))
        addresses[client] = client_addres
        Thread(target=handle_client, args=(client,)).start()


def handle_client(client):
    name = client.recv(BUFSIZ).decode("utf-8")
    welcome = f'welcome {name}! If you want to quit? type {"{quit}"} to exit.'
    client.send(bytes(welcome, 'utf-8'))
    msg = f"{name} has joined the chat!"
    broadcast(bytes(msg, 'utf-8'))
    clients[client] = name

    while True:
        try:
            msg = client.recv(BUFSIZ)
        except OSError:
            continue

        if msg != bytes("{quit}", "utf-8"):
            broadcast(msg, f"{name}:\t")
        else:
            client.send(bytes("{quit}", "utf-8"))
            client.close()
            del clients[client]
            broadcast(bytes(f"{name} has left the chat.", "utf-8"))
            break


def broadcast(msg, prefix=""):
    for sock in list(clients):
        try:
            sock.send(bytes(prefix, "utf-8") + msg)

        except BrokenPipeError:
            print("BrokenPipeError---------------------")
            sock.close()
            del clients[sock]


if __name__ == "__main__":
    SERVER.listen(5)
    print("Waiting for connection...")
    ACCEPT_THREAD = Thread(target=accept_incoming_connections)
    ACCEPT_THREAD.start()
    ACCEPT_THREAD.join()
    SERVER.close()

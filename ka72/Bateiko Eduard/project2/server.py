from socket import AF_INET, socket, SOCK_STREAM
from threading import Thread
import block
import json

clients = {}
addresses = {}

HOST = ''
PORT = 33000
BUFSIZE = 1024 ** 2
ADDR = (HOST, PORT)
SERVER = socket(AF_INET, SOCK_STREAM)
SERVER.bind(ADDR)

line_with_question = "What fo you want:\n If you want to check blocks integrity press 1\n" \
                          "If you want to create transaction press 2\n"

def accept_connections():
    while True:
        client, client_address = SERVER.accept()
        print(f"{client}:{client_address} has connected.")
        client.send(bytes(f"Hello dear friend!!\n{line_with_question}", 'utf-8'))

        addresses[client] = client_address
        Thread(target=something_doing, args=(client,)).start()

def add_block(client):
    recieve_massage = client.recv(BUFSIZE).decode('utf-8').replace("'", '"')
    data = json.loads(recieve_massage)
    print(f"DATA:\n{data}\ntype:\t{type(data)}")

    block.write_block(name=data['name'], amount=int(data['amount']),
                      to_whom=data['to_whom'])


def something_doing(client):
    print("i am there")
    while True:

        recieve_massage = client.recv(BUFSIZE).decode('utf-8')

        if recieve_massage == "0":
            client.send("Thanks for ...")
            client.close()
            del clients[client]
            break

        elif recieve_massage == '1':
            print("PRESS 1")
            message = 'block'
            client.send(bytes(message,'utf-8'))
            add_block(client)
            client.send(bytes(f'DONE\n{line_with_question}', 'utf-8'))

        elif recieve_massage == '2':
            print("PRESS 2")
            message = 'PRESS 2'
            client.send(bytes(message, 'utf-8'))

        else:
            message = "You not enter some code: '1' '2' or '0'" + line_with_question
            client.send(bytes(message))


if __name__ == "__main__":
    SERVER.listen(5)
    print("wainting connection...")
    ACCEPT_THREAD = Thread(target=accept_connections)
    ACCEPT_THREAD.start()
    ACCEPT_THREAD.join()
    SERVER.close()
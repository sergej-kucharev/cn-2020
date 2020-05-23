from socket import AF_INET, socket, SOCK_STREAM, SHUT_RDWR
from threading import Thread
import block
import json

clients = {}
addresses = {}
threads = []

HOST = ''
PORT = 33001
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

        clients[client] = client_address
        new_thread = Thread(target=something_doing, args=(client,))
        new_thread.start()
        threads.append(new_thread)

def add_block(client):
    recieve_massage = client.recv(BUFSIZE).decode('utf-8').replace("'", '"')
    data = json.loads(recieve_massage)

    block.write_block(name=data['name'], amount=int(data['amount']),
                      to_whom=data['to_whom'])


def something_doing(client):
    while True:

        recieve_massage = client.recv(BUFSIZE).decode('utf-8')

        if recieve_massage == "0":
            print(f"CLIENT WAS CLOSED:\n{clients[client]}")
            client.send(b"quit")
            del clients[client]
            client.close()

            break

        elif recieve_massage == '1':
            message = 'block'
            client.send(bytes(message,'utf-8'))
            add_block(client)
            client.send(bytes(f'DONE\n{line_with_question}', 'utf-8'))

        elif recieve_massage == '2':
            result = block.check_integrity()
            client.send(b"result")
            client.send(bytes(str(result), 'utf-8'))

        else:
            message = "You not enter some code: '1' '2' or '0'\n" + line_with_question
            client.send(bytes(message, 'utf-8'))


def close_server():
    while True:
        message = input("Enter action:\t")
        if message == 'close':
            for client in list(clients):
                print(f"CLIENT WAS CLOSED:\n{clients[client]}")
                client.send(b"quit")
                del clients[client]
                client.close()

            # for thread_ in threads:
            #     thread_.close()
            SERVER.close()
            SERVER.shutdown(SHUT_RDWR)
            return


if __name__ == "__main__":
    SERVER.listen(5)
    print("Waiting connection...")

    ACCEPT_THREAD = Thread(target=accept_connections)
    # CLOSE_SERVER = Thread(target=close_server)

    ACCEPT_THREAD.start()
    # CLOSE_SERVER.start()

    ACCEPT_THREAD.join()

    SERVER.close()
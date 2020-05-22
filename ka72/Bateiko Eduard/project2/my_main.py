import block

def input_date():
    name = input("Enter name:\t")
    amount = int(input("Enter amount:\t"))
    to_whom = input("Enter to_whom:\t")

    block.write_block(name=name, amount=amount, to_whom=to_whom)


def cheack():

    while True:
        cheak = input("enter 0 ,1, 2:\t")

        if cheak == '0':
            break
        elif cheak == '1':
            input_date()
        elif cheak == '2':
            result = block.check_integrity()
            print(result)


if __name__ == "__main__":
    print("hello")
    cheack()
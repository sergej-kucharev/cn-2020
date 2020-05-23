import json
import os
import hashlib


blockchain_dir = os.curdir + '/blockchain/'


def get_hash(filename):

    file = open(blockchain_dir + filename, 'rb').read()
    return hashlib.md5(file).hexdigest()


def get_files():
    files = os.listdir(blockchain_dir)
    files = [int(x) for x in files]
    return sorted(files)


def check_integrity():
    # read hash previous block
    # calculate hash prev block
    # and equal data was got

    files = get_files()
    result = []

    for file in files[1:]:
        f = open(blockchain_dir + str(file))
        hash_prev_block = json.load(f)['hash']

        prev_file = str(file - 1)
        actual_hash = get_hash(prev_file)
        # print(actual_hash)

        if hash_prev_block == actual_hash:
            res = 'ok'
        else:
            res = 'Corrupted'
        result.append({'block': prev_file, 'result' : res})

    return result


def write_block(name, amount, to_whom, prev_hash=""):

    files = get_files()

    prev_file = files[-1]
    file_name = str(prev_file + 1)

    prev_hash = get_hash(str(prev_file))

    data = {"name": name,
            'amount': amount,
            "to_whom": to_whom,
            "hash": prev_hash}

    with open(blockchain_dir + file_name, 'w') as file:
        json.dump(data, file, indent=4, ensure_ascii=False) # write in file 'data'


def main():
    write_block(name='ed', amount=200, to_whom="lilia")


if __name__ == "__main__":
    main()

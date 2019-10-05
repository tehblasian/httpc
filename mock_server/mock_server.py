from flask import Flask, request
import sys

app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def test():
    if request.method == 'GET':
        return 'Successful Get Request'

    if request.method == 'POST':
        print(request.form, file=sys.stderr)
        print(request.data, file=sys.stderr)
        print(request.json, file=sys.stderr)
        return 'Successful Post Request'

if __name__ == '__main__':
    app.run()
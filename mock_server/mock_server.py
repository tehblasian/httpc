from flask import Flask, request
app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def test():
    if request.method == 'GET':
        return 'Successful Get Request'

    if request.method == 'POST':
        return 'Successful Post Request'

if __name__ == '__main__':
    app.run()